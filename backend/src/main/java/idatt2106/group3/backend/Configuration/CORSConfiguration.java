package idatt2106.group3.backend.Configuration;

import java.util.Collections;

import org.springframework.web.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures CORS to correct localhost port
 */
@Configuration
public class CORSConfiguration {
    
    /**
	 * Allows CORS to frontend application
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedOrigins("http://localhost:3000")
				.allowedOrigins("https://localhost:8443")
				.allowedOrigins("http://localhost:8080");
			}
		};
	}

// 	@Bean
// 	public FilterRegistrationBean<CorsFilter> corsFilter() {
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         CorsConfiguration config = new CorsConfiguration();
//         config.setAllowCredentials(true);
//         config.setAllowedOrigins(Collections.singletonList("*"));
//         config.addAllowedHeader("*");
//         config.addAllowedMethod("*");
//         source.registerCorsConfiguration("/**", config);
//         FilterRegistrationBean<CorsFilter> filterRegistration = new FilterRegistrationBean<>(new CorsFilter(source));
//         filterRegistration.setOrder(Ordered.HIGHEST_PRECEDENCE);
//         return filterRegistration;
// }
}
