import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpComponents {

    public static HttpURLConnection getConnection(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");

        return con;
    }

    public static void setCookies(HttpURLConnection con, Map<String, String> cookies) {
        StringBuffer sb = new StringBuffer();

        cookies.forEach((k, v) -> sb.append(k).append("=").append(v).append(";"));

        con.setRequestProperty("Cookie", sb.toString());
    }

    public static String send(HttpURLConnection con, String request) throws IOException {
        OutputStream os = con.getOutputStream();
        os.write(request.getBytes());
        os.flush();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }
}
