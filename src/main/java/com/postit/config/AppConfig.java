package com.postit.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan("com.postit")
public class AppConfig {

  @Bean
  public LocalSessionFactoryBean sessionFactory() {

    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("com.postit");
    sessionFactory.setHibernateProperties(hibernateProperties());

    return sessionFactory;
  }

  @Bean
  public DataSource dataSource() {

    BasicDataSource dataSource = new BasicDataSource();

    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl("jdbc:postgresql://localhost:5432/postit");

    return dataSource;
  }

  private final Properties hibernateProperties() {

    Properties hibernateProperties = new Properties();

    hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    hibernateProperties.setProperty("hibernate.current_session_context_class", "thread");
    // hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create"); // create-drop update
    hibernateProperties.setProperty("hibernate.show_sql", "true");

    return hibernateProperties;
  }

  @Bean
  public HibernateTransactionManager getTransactionManager() {

    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory().getObject());
    return transactionManager;
  }

}
