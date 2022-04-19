package group825.vetapp2;

import group825.vetapp2.database.DatabaseConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VetappApplication {

	public static void main(String[] args) {
		DatabaseConnection.initialize();
		SpringApplication.run(VetappApplication.class, args);
//		DatabaseConnection.close();
	}

}
