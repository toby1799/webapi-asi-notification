package ch.asinfotrack.webapinotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WebapiNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebapiNotificationApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/messages").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
				registry.addMapping("/notifications").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
				registry.addMapping("/notification").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
			}
		};
	}

}
