import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by aldm on 24.02.2016.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("libraryDAO")
@EnableJpaRepositories("libraryDAO")
public class SpringConfig {
}
