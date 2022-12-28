package ch.asinfotrack.webapinotification.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private String accessToken;

    public TokenInterceptor(@Value("${secret.accesstoken}") String accessToken) {
        this.accessToken = accessToken;
    }



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //String accessToken = "test";

        System.out.println(request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/") + 1));

        if (request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/") + 1).equals("messages")) {
            return true;
        } else {
            System.out.println(accessToken + " : " + request.getParameter("token"));
            String submittedToken = request.getParameter("token");
            return submittedToken.equals(accessToken);
        }


    }
}
