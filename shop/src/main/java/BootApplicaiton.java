import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author:ben.gu
 * @Date:2020/3/10 9:07 PM
 */
@SpringBootApplication
@ComponentScan("com.shop")
public class BootApplicaiton {


    public static void main(String[] args) {
        SpringApplication.run(BootApplicaiton.class, args);
    }


}
