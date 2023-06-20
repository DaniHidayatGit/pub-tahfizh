package com.tugasakhir.util.jwt;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    public static JwtTokenResponse getUserData(HttpServletRequest request) {
        return (JwtTokenResponse) request.getAttribute("User-Data");
    }

    public static String getUserToken(HttpServletRequest request) {
        return request.getHeader("Authorization").replace("Bearer ", "");
    }
}
