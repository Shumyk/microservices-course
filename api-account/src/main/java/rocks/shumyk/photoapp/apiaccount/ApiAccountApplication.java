package rocks.shumyk.photoapp.apiaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiAccountApplication.class, args);
	}

}
