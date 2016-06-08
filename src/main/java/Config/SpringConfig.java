package Config;

import DAO.LibraryRepository;
import Initializers.AppInitializer;
import Model.Library;
import Rest.AppController;
import Services.LibraryService;
import Utils.TestContentProducer;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

/**
 * Created by aldm on 24.02.2016.
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {LibraryRepository.class})
@EnableTransactionManagement
@ComponentScan(basePackageClasses = {LibraryRepository.class, Library.class, LibraryService.class, AppController.class, TestContentProducer.class, SecurityConfig.class, AppInitializer.class})
@EnableWebMvc
public class SpringConfig extends WebMvcAutoConfiguration {

    private static final String PROP_DATABASE_DRIVER = "hibernate.connection.driver_class";
    private static final String PROP_DATABASE_PASSWORD = "hibernate.connection.password";
    private static final String PROP_DATABASE_URL = "hibernate.connection.url";
    private static final String PROP_DATABASE_USERNAME = "hibernate.connection.username";
    private static final String PROP_ENTITYMANAGER_PACKAGES_TO_SCAN = "Model";

    private org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();

    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class, args);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(configuration.getProperty(PROP_DATABASE_DRIVER));
        dataSource.setUrl(configuration.getProperty(PROP_DATABASE_URL));
        dataSource.setUsername(configuration.getProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(configuration.getProperty(PROP_DATABASE_PASSWORD));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(PROP_ENTITYMANAGER_PACKAGES_TO_SCAN);

        entityManagerFactoryBean.setJpaProperties(configuration.getProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }
}
