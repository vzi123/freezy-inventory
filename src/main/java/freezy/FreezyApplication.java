package freezy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"freezy.*"})
public class FreezyApplication {

	public static void main(String[] args) {
		SpringApplication.run(freezy.FreezyApplication.class, args);
	}

}
