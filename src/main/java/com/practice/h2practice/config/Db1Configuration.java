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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * 각 데이터소스와 그를 받는 SqlSessionFactory가 다른 mapper를 스캔하도록 하자.
 * */
@Configuration
@MapperScan(basePackages = "com.practice.h2practice.dao.db1", sqlSessionFactoryRef = "db1SqlSessionFactory")
public class Db1Configuration {


    /**
     * default bean으로 사용하기 위해 @Primary 어노테이션을 작성했다.
     * @ConfigurationProperties를 사용하여 application.properties의 값을 가져온다.(자동설정)
     * 만약 직접 property 값을 가져와서 작업하고자 한다면 HikaryDataSource 객체를 생성하고 값을 세팅한 후 리턴하도록 하자.
     * */
    @Bean(name = "db1DataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.db1.datasource")
    public DataSource db1DataSource() {

        return DataSourceBuilder.create().build();
    }

    /**
     * 각 DB마다 schema.sql과 data.sql을 구분하여 초기화하기 위하여 작성한 Bean
     * 만약 하나의 DB만 사용한다면 root에 schema.sql과 data.sql을 작성하면 바로 적용.
     * 특정 위치를 지정하고싶다면 application.properties에 spring.datasource.shcema, spring.datasource.data 사용.
     * */
    @Bean(name = "db1DataSourceInitializer")
    @Primary
    public DataSourceInitializer db1DataSourceInitializer(@Qualifier("db1DataSource")DataSource db1DataSource, ApplicationContext applicationContext) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(applicationContext.getResource("classpath:db1/schema.sql"));
        resourceDatabasePopulator.addScript(applicationContext.getResource("classpath:db1/data.sql"));
        DataSourceInitializer db1DataSourceInitializer = new DataSourceInitializer();
        db1DataSourceInitializer.setDataSource(db1DataSource);
        db1DataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return db1DataSourceInitializer;
    }

    /**
     * Bean name을 작성할 필요는 없으나 명시적으로 눈에 들어오기 위해 작성했다. Qualifer역시 같은 이유다.
     * ApplicationContext를 주입받은 이유는 프로토타입이라 없지만 MyBatis의 config.xml을 리소스로 가져오기 위함이다.
     * 설정을 읽어야 한다면 applicationContext.getResources("classpath:/config/mybatis-config.xml")를 통해 가져오도록 하자.
     * 자바에서 설정해야 하는 것은 setMapperLocation이고 나머지는 xml에서 설정 가능하다.
     * */
    @Bean(name = "db1SqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("db1DataSource") DataSource datasource, ApplicationContext applicationContext) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(datasource);
        return sqlSessionFactoryBean.getObject();
    }

}
