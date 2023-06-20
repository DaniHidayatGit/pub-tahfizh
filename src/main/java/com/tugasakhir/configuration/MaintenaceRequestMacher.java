package com.tugasakhir.configuration;


import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MaintenaceRequestMacher implements RequestMatcher {
    public static Boolean isMaintenance = false;

    @Override
    public boolean matches(HttpServletRequest request) {
        return isMaintenance;
    }
}
