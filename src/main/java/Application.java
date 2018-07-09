import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ServletComponentScan
@SpringBootApplication
@ComponentScan("spring_boot_app")
public class Application {

    public Application() { }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
