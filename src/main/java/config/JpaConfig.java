package config;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import connectors.ConnectionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;


@Configuration
@EnableJpaRepositories(basePackages = "persistence")
@EnableTransactionManagement
public class JpaConfig implements ConnectionProperties {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPersistenceUnitName("java");
        emf.setPackagesToScan("model");
        emf.getJpaPropertyMap().put(HBM2DDL_AUTO, "update");

        return emf;
    }

    @Bean
    public DataSource getDataSource() {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setUser("1gb_monp");
        dataSource.setPassword("b7925a08yu");
        dataSource.setServerName("mssql4.1gb.ua");
        dataSource.setPortNumber(1433);
        dataSource.setDatabaseName("1gb_x_rozklad");
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setDatabasePlatform(DIALECT);
        vendorAdapter.setDatabase(Database.SQL_SERVER);
        return vendorAdapter;
    }

    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
