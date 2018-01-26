import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author zhuruisong on 2017/6/5
 * @since 1.0
 */
@SpringBootApplication(scanBasePackages =
        {
                "com.daishumovie.dao","com.daishumovie.utils"})

public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class,args);
    }

}
