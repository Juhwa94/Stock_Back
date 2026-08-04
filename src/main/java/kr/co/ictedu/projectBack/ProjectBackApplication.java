package kr.co.ictedu.projectBack;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProjectBackApplication {

	@Value("${cors.allowed-origins}")
	private String[] allowedOrigins;

	public static void main(String[] args) {
		SpringApplication.run(ProjectBackApplication.class, args);
	}
	// spring boot에서 비동기식 외부 접속[Cros Allow Origin]을 허용해주기 위한 설정
	// 빈으로 등록 - 스프링 컨테이너가 관리할 객체 <bean ~

	public WebMvcConfigurer crosConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				System.out.println("Cros Allow Origin 실행!");
				registry.addMapping("/**") // 모든 API 엔드포인트에 적용
						.allowedOriginPatterns(
								"https://stockfront-production.up.railway.app",
								"http://localhost:3000",
								"http://localhost:5173")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
						.allowedHeaders("*")
						.allowCredentials(true)
						.maxAge(3600);
			}
		};
	}
}
