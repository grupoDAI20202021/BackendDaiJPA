package daibackend.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
	@Value( "${allowed.origin}")
	private String allowedOrigin;
	private final long MAX_AGE_SECS = 3600;

	@Bean
	public WebMvcConfigurer getCorsConfiguration(){
	return new WebMvcConfigurer() {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("http://127.0.0.1:5500")
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.allowedHeaders("*")
				.allowCredentials(true)
				.maxAge(MAX_AGE_SECS);
		}
	};
	}
	}
