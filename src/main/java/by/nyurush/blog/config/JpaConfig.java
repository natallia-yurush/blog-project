package by.nyurush.blog.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@ComponentScan("by.nyurush.blog")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("by.nyurush.blog")
public class JpaConfig {

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("db.driver")));
        dataSource.setUrl(Objects.requireNonNull(environment.getProperty("db.url")));
        dataSource.setUsername(Objects.requireNonNull(environment.getProperty("db.username")));
        dataSource.setPassword(Objects.requireNonNull(environment.getProperty("db.password")));

        return dataSource;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(Objects.requireNonNull(environment.getProperty("db.entitling.packages.to.scan")));

        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());

        return entityManagerFactoryBean;
    }


    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }


    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", Objects.requireNonNull(environment.getProperty("db.hibernate.dialect")));
        properties.put("hibernate.show_sql", Objects.requireNonNull(environment.getProperty("db.hibernate.show_sql")));
        properties.put("hibernate.hbm2ddl.auto", Objects.requireNonNull(environment.getProperty("db.hibernate.hbm2ddl.auto")));
        properties.put("hibernate.enable_lazy_load_no_trans", true);

        return properties;
    }

}