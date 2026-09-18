package com.test.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
// 1. 自动扫描指定包下的组件
@ComponentScan(basePackages = "com.example")
// 2. 加载类路径下的属性配置文件
@PropertySource("classpath:application.properties")
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    // 动态读取配置文件中的各项参数
    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${jdbc.driver-class-name}")
    private String driverClassName;

    /**
     * 配置 DBCP2 数据源 Bean
     * destroyMethod = "close" 极其重要,确保 JVM 退出或容器销毁时,DBCP2 线程池能优雅关闭
     */
    /*@Bean(destroyMethod = "close")
    public DataSource dataSource() {
        logger.info("正在初始化数据库连接池 (Apache Commons DBCP2)...");

        BasicDataSource basicDataSource = new BasicDataSource();

        // 基础连接配置
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setUsername(jdbcUsername);
        basicDataSource.setPassword(jdbcPassword);
        basicDataSource.setDriverClassName(driverClassName);

        // DBCP2 核心核心调优参数
        basicDataSource.setInitialSize(5);      // 初始连接数
        basicDataSource.setMaxTotal(20);        // 最大活动连接数
        basicDataSource.setMaxIdle(10);         // 最大空闲连接数
        basicDataSource.setMinIdle(5);          // 最小空闲连接数
        basicDataSource.setMaxWaitMillis(2000); // 获得连接的最大等待毫秒数

        // 维持连接可用性的心跳配置(防止数据库单方面断开长连接）
        basicDataSource.setTestOnBorrow(true);
        basicDataSource.setValidationQuery("SELECT 1");

        return basicDataSource;
    }*/

    /**
     * 配置全局公共异步线程池 Bean
     */
    @Bean(name = "globalTaskExecutor")
    public Executor globalTaskExecutor() {
        logger.info("正在初始化全局异步线程池...");

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(20);
        threadPoolTaskExecutor.setQueueCapacity(500);
        threadPoolTaskExecutor.setThreadNamePrefix("app-async-");

        // 拒绝策略:由调用者线程直接执行
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 容器关闭时,等待任务执行完毕再销毁
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setAwaitTerminationSeconds(60);

        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
