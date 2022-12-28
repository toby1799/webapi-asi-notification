package ch.asinfotrack.webapinotification.config;

import ch.asinfotrack.webapinotification.controller.TokenInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private String accessToken;

    public WebConfig(@Value("${secret.accesstoken}") String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor(accessToken));
    }

}
