package com.practice.h2practice.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.practice.h2practice.dao.db2", sqlSessionFactoryRef = "db2SqlSessionFactory")
public class Db2Configuration {

    @Bean(name = "db2DataSource")
    @ConfigurationProperties(prefix = "spring.db2.datasource")
    public DataSource db2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "db2DataSourceInitializer")
    public DataSourceInitializer db2DataSourceInitializer(@Qualifier("db2DataSource")DataSource db1DataSource, ApplicationContext applicationContext) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(applicationContext.getResource("classpath:db2/schema.sql"));
        resourceDatabasePopulator.addScript(applicationContext.getResource("classpath:db2/data.sql"));
        DataSourceInitializer db1DataSourceInitializer = new DataSourceInitializer();
        db1DataSourceInitializer.setDataSource(db1DataSource);
        db1DataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return db1DataSourceInitializer;
    }

    @Bean(name = "db2SqlSessionFactory")
    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource db2DataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(db2DataSource);
        return sqlSessionFactoryBean.getObject();
    }
}
