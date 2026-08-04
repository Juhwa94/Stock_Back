package kr.co.ictedu.projectBack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProjectBackApplication {

	// application.properties 또는 yml에 지정된 값이 있다면 읽어옵니다.
	@Value("${cors.allowed-origins:https://stockfront-production.up.railway.app,http://localhost:3000,http://localhost:5173}")
	private String[] allowedOrigins;

	public static void main(String[] args) {
		SpringApplication.run(ProjectBackApplication.class, args);
	}

	// ⭐ @Bean 어노테이션 추가 (스프링 컨테이너에 CORS 설정을 등록하기 위해 필수!)
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				System.out.println("CORS Allow Origin 설정 실행!");

				registry.addMapping("/**") // 모든 API 엔드포인트에 적용
						.allowedOriginPatterns(allowedOrigins) // @Value로 불러온 주소 목록 적용 (직접 문자열을 적어도 됩니다)
						.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
						.allowedHeaders("*")
						.allowCredentials(true)
						.maxAge(3600);
			}
		};
	}
}