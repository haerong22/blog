package com.example.jwt.jwt;

public class JwtContext {

    private static final ThreadLocal<MemberInfo> CONTEXT = new ThreadLocal<>();

    public static MemberInfo getMemberInfo() {
        MemberInfo memberInfo = CONTEXT.get();
        return memberInfo == null ? new MemberInfo() : memberInfo;
    }

    public static void setMemberInfo(MemberInfo memberInfo) {
        CONTEXT.set(memberInfo);
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
