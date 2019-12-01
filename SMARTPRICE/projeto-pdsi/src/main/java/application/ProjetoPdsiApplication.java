package application;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@ComponentScan(basePackages = { "application.*" })
@EntityScan(basePackages = { "application.models" })
@EnableJpaRepositories(basePackages = { "application.repositories" })

@SpringBootApplication
@EnableTransactionManagement
public class ProjetoPdsiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoPdsiApplication.class, args);
	}

	@Bean
	public FixedLocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}

}
