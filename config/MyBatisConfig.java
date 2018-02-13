package be.thibaulthelsmoortel.lotterymanagement.config;

import be.thibaulthelsmoortel.lotterymanagement.LMProperties;
import be.thibaulthelsmoortel.lotterymanagement.LMProperties.Database;
import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * Class configuring MyBatis.
 *
 * @author Thibault Helsmoortel
 */
@Configuration("MyBatisConfiguration")
@MapperScan(basePackages = { "be.thibaulthelsmoortel.lotterymanagement.mappers" })
public class MyBatisConfig {

    private final LMProperties.Database dbProps;

    @Autowired
    public MyBatisConfig(Database dbProps) {
        this.dbProps = dbProps;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl(dbProps.getUrl());
        dataSource.setUsername(dbProps.getUsername());
        dataSource.setPassword(dbProps.getPassword());
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        return sessionFactory;
    }
}