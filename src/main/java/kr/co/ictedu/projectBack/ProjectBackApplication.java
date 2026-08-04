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
		@Configuration
		public WebMvcConfigurer crosConfigurer() {
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					System.out.println("Cros Allow Origin 실행!");
					registry.addMapping("/**") // 모든 API 경로에 대해
                .allowedOrigins(
                    "https://stockfront-production.up.railway.app", // Railway 프론트 주소
                    "http://localhost:3000" // 로컬 개발용 (필요시)
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*")
                .allowCredentials(true) // 쿠키나 인증 헤더 포함 허용
                .maxAge(3600);
				}
			};
		}
}
