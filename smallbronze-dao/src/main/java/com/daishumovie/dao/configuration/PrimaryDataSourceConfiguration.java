package com.daishumovie.dao.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 数据源配置1</><br/>
 * Date: 2017/3/23<br/>
 *
 * @author ZRS
 * @since JDK8
 */
@Configuration
//为了支持注解事务，增加了@EnableTransactionManagement注解，并且反回了一个PlatformTransactionManagerBean
@EnableTransactionManagement
@MapperScan(basePackages ="com.daishumovie.dao.mapper.smallbronze",sqlSessionFactoryRef = "primarySqlSessionFactory")
@Slf4j
public class PrimaryDataSourceConfiguration {

    @Bean(name="primaryDataSource",destroyMethod = "close")
    @ConfigurationProperties(prefix="hikari.datasource.primary")
    @Primary
    public DataSource primaryDataSource() {
        log.info("-------------------- primaryDataSource init ---------------------");
        return new HikariDataSource();
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public PlatformTransactionManager primaryTransactionManager() {
        log.info("-------------------- primaryTransactionManager init ---------------------");
        return new DataSourceTransactionManager(primaryDataSource());
    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper/smallbronze/*.xml"));
        sessionFactory.setTypeAliasesPackage("com.daishumovie.dao.model.smallbronze");

        log.info("-------------------- primarySqlSessionFactory init ---------------------");
        return sessionFactory.getObject();
    }

    @Bean(name="primarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        log.info("-------------------- primarySqlSessionTemplate init ---------------------");
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}