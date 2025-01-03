package ec.com.sofka;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application for FinancesPro.
 */
@SpringBootApplication
public class FinancesProApplication {

  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.configure().directory("./").load();
    System.setProperty("SPRING_MONGODB_URI", dotenv.get("SPRING_MONGODB_URI"));
    SpringApplication.run(FinancesProApplication.class, args);
  }
}
