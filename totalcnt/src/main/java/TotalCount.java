import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TotalCount {

    private static final String BASE_URL = "https://v2cdn.velog.io/graphql";
    private static final int LIMIT = 40;
    private static String username = null;
    private static String accessToken = null;
    private static String refreshToken = null;

    public static void main(String[] args) throws IOException {
        parseArguments(args);

        if (username == null || accessToken == null || refreshToken == null) {
            System.out.println("username, accessToken, refreshToken is required.");
            return;
        }

        List<String> postIds;

        int totalCount = 0;
        int todayCount = 0;
        String last = null;

        do {
            postIds = getPostIds(last);

            last = postIds.get(postIds.size()-1);

            for (String postId : postIds) {
                HttpURLConnection con = HttpComponents.getConnection(BASE_URL);
                HttpComponents.setCookies(con, Map.of("access_token", accessToken, "refresh_token", refreshToken));

                String request = getPostCountRequest(postId);

                String response = HttpComponents.send(con, request);

                JSONObject data = new JSONObject(response);

                JSONObject stats = data.getJSONObject("data").getJSONObject("getStats");

                int total = Integer.parseInt(stats.get("total").toString());
                totalCount += total;

                if (!stats.getJSONArray("count_by_day").isEmpty()) {
                    int recent = Integer.parseInt(
                            stats.getJSONArray("count_by_day").getJSONObject(0).get("count").toString()
                    );

                    LocalDateTime recentDate = LocalDateTime.parse(
                            stats.getJSONArray("count_by_day").getJSONObject(0).get("day").toString(),
                            DateTimeFormatter.ISO_DATE_TIME
                    );

                    if (isToday(recentDate)) {
                        todayCount += recent;
                    }
                }
            }

        } while (postIds.size() == LIMIT);

        System.out.printf("total: %d, today: %d%n", totalCount, todayCount);
    }

    private static boolean isToday(LocalDateTime recentDate) {
        return recentDate.truncatedTo(ChronoUnit.DAYS).compareTo(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) == 0;
    }

    private static void parseArguments(String[] args) {
        for (int i = 0; i < args.length; i+=2) {
            switch (args[i]) {
                case "-u": {
                    username = args[i+1];
                    break;
                }
                case "-t": {
                    accessToken = args[i+1];
                    break;
                }
                case "-r": {
                    refreshToken = args[i+1];
                    break;
                }
            }
        }
    }

    private static List<String> getPostIds(String last) throws IOException {
        HttpURLConnection con = HttpComponents.getConnection(BASE_URL);

        String request = getPostsRequest(last);

        String response = HttpComponents.send(con, request);

        JSONObject data = new JSONObject(response);

        JSONArray posts = data.getJSONObject("data").getJSONArray("posts");

        List<String> postIds = new ArrayList<>();

        for (Object post : posts) {
            String id = new JSONObject(post.toString()).get("id").toString();
            postIds.add(id);
        }

        return postIds;
    }

    private static String getPostsRequest(String last) {
        JSONObject request = new JSONObject();

        JSONObject variables = new JSONObject();
        variables.put("username", username);
        variables.put("limit", LIMIT);
        if (last != null) {
            variables.put("cursor", last);
        }

        request.put("operationName", "Posts");
        request.put("variables", variables);
        request.put("query", "query Posts($cursor: ID, $username: String, $temp_only: Boolean, $tag: String, $limit: Int) { posts(cursor: $cursor, username: $username, temp_only: $temp_only, tag: $tag, limit: $limit) {id title}}");
        return request.toString();
    }

    private static String getPostCountRequest(String postId) {
        JSONObject request = new JSONObject();

        JSONObject variables = new JSONObject();
        variables.put("post_id", postId);

        request.put("operationName", "GetStats");
        request.put("variables", variables);
        request.put("query", "query GetStats($post_id: ID!) {getStats(post_id: $post_id) {total count_by_day {count day}}}");
        return request.toString();
    }
}
