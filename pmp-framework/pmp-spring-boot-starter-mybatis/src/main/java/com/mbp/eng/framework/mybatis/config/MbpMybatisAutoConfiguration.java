package com.mbp.eng.framework.mybatis.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.extension.incrementer.DmKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.KingbaseKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.PostgreKeyGenerator;
import com.baomidou.mybatisplus.extension.parser.JsqlParserGlobal;
import com.baomidou.mybatisplus.extension.parser.cache.JdkSerialCaffeineJsqlParseCache;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbp.eng.framework.common.util.json.JsonUtils;
import com.mbp.eng.framework.mybatis.core.handler.DefaultDBFieldHandler;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * MyBaits 配置类
 */
@AutoConfiguration(before = MybatisPlusAutoConfiguration.class)
// 目的:先于 MyBatis Plus 自动配置,避免 @MapperScan 可能扫描不到 Mapper 打印 warn 日志
//MapperScan包的路径和主启动路径不一致或者重复扫描都会导致启动时WARN
//@MapperScan(value = "${mbp.info.base-package}", annotationClass = Mapper.class, lazyInitialization = "${mybatis.lazy-initialization:false}")
// Mapper 懒加载,目前仅用于单元测试
public class MbpMybatisAutoConfiguration {
    // ==========================================
    // 1. 基础数据源属性注入
    // ==========================================
    @Value("${spring.datasource.dbcp2.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.dbcp2.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.dbcp2.max-active}")
    private int maxActive;

    @Value("${spring.datasource.dbcp2.validation-query}")
    private String validationQuery;

    @Value("${spring.datasource.dbcp2.connection-properties}")
    private String connectionProperties;

    @Value("${spring.datasource.dbcp2.test-while-idle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.dbcp2.max-idle}")
    private int maxIdle;

    @Value("${spring.datasource.dbcp2.max-wait}")
    private long maxWait;

    @Value("${spring.datasource.dbcp2.min-idle}")
    private int minIdle;

    @Value("${spring.datasource.dbcp2.default-auto-commit}")
    private boolean defaultAutoCommit;

    @Value("${spring.datasource.dbcp2.time-between-eviction-runs-millis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.dbcp2.min-evictable-idle-time-millis}")
    private long minEvictableIdleTimeMillis;

    // ==========================================
    // 2. Bean 定义
    // ==========================================

    /**
     * 对应原 XML 中的 <bean id="dataSource" ...>
     */
    @Bean(name = "dataSource", destroyMethod = "close")
    //告诉 Spring,只有当容器里没有别的人注册过数据源时,才创建原生的这个
    //这样当 baomidou 抢先注册了 dataSource 后,框架自己的就会自动跳过,完美避免冲突!
    //@ConditionalOnMissingBean(DataSource.class)
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driverClassName);
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setMaxTotal(maxActive);
        basicDataSource.setValidationQuery(validationQuery);
        basicDataSource.setConnectionProperties(connectionProperties);
        basicDataSource.setTestWhileIdle(testWhileIdle);
        basicDataSource.setMaxIdle(maxIdle);
        basicDataSource.setMaxWaitMillis(maxWait);
        basicDataSource.setMinIdle(minIdle);
        basicDataSource.setDefaultAutoCommit(defaultAutoCommit);
        basicDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        basicDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        return basicDataSource;
    }

    @Bean(name = "dataSourceHikariMbpMybatisAutoConfiguration")
    public DataSource dataSourceHikari() {
        // 1.实例化现代高并发 HikariCP 核心数据源对象
        HikariDataSource hikariDataSource = new HikariDataSource();
        // 2.基础连接四大金刚参数
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(dbUrl);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        // 3.连接池核心数与容量参数
        // Hikari 没有 maxActive 或 maxTotal,必须使用 setMaximumPoolSize()
        hikariDataSource.setMaximumPoolSize(maxActive);
        // Hikari 没有 minIdle 之外的 maxIdle 冗余参数,因为它内部采用了更高效的常驻与伸缩策略.
        // 为了保护原汁原味的参数传导,直接锁定最小常驻空闲连接数
        hikariDataSource.setMinimumIdle(minIdle);
        // 如果外界传了不合规的值(<=0),直接强制兜底为生产级标配的 30秒(30000ms)
        long safeTimeout = maxWait <= 0 ? 30000 : maxWait;
        // 4.超时与心跳健康状态检查
        // DBCP 的 maxWait 纯数字代表物理阻塞等待.Hikari 升级为了 setConnectionTimeout()(单位毫秒),
        // 如果池满,线程等待指定毫秒数后安全抛错,绝对不拖垮 Tomcat 线程池.默认推荐 30000 毫秒(30秒)
        // 强行增加刚性防御:万一外界配了个 100,也必须保证不小于 Hikari 要求的 250ms
        hikariDataSource.setConnectionTimeout(Math.max(250, safeTimeout));
        // DBCP 靠 validationQuery 进行重度 SQL 心跳探测.
        // Hikari 底层是直接通过 JDBC 驱动在原生物理层发送高效的 ping 包检测连接活跃,性能极高.
        // 如果你的 validationQuery 变量配的是 "SELECT 1" 等标准语句,直接在这里通过该方法吃进去:
        hikariDataSource.setConnectionTestQuery(validationQuery);
        // 5.事务与连接生命周期高阶调优对齐
        // 对齐旧版的 setDefaultAutoCommit()
        hikariDataSource.setAutoCommit(defaultAutoCommit);
        // ==================== HikariCP 独有的内部新陈代谢调优 ====================
        // 老代码里的 timeBetweenEvictionRunsMillis(检测驱逐线程运行周期)和 minEvictableIdleTimeMillis(连接池中最小空闲时间),
        // 在现代 Hikari 体系中已经升级为了更加全自动且强悍的[连接新陈代谢自愈机制]:
        // a.一个连接在池子里保持空闲状态的最大时长.建议设为 10 分钟,到期自动回收并释放物理 socket 管道
        hikariDataSource.setIdleTimeout(600000);
        // b.线上防断连"核武器参数":一个连接的绝对最长生命周期(毫秒).
        // 强制设置 30 分钟(1800000毫秒).让连接每隔半小时在后台全自动"新陈代谢"刷新一次,
        // 100% 阻断由于数据库服务端(wait_timeout)单方面闪断引发的线上经典的"连接已被重置"严重红字报错!
        hikariDataSource.setMaxLifetime(1800000);
        // 6.数据库扩展驱动属性对齐 (处理连接属性字符串,如 ssl=true)
        // 针对老代码里传入的自定义配置字符串(如 connectionProperties),
        // 如果业务确实有特殊需求,直接作为参数加入 Hikari 的上下文中:
        if (connectionProperties != null && !connectionProperties.trim().isEmpty()) {
            hikariDataSource.addDataSourceProperty("connectionProperties", connectionProperties);
        }
        // 给连接池起一个显眼的框架专属名字,高并发线上发生死锁时,用 jstack 一眼就能精准捕获并定位它!
        hikariDataSource.setPoolName("MbpProxyHikariPool");
        return hikariDataSource;
    }

    /**
     * 对应原 XML 中的 <bean id="sessionFactory" ...>
     */
    @Bean(name = "sessionFactory")
    @ConditionalOnMissingBean(SqlSessionFactory.class)
    /*1. 允许应用层自定义配置(防止 Bean 重复定义冲突)
    如果使用该自研框架的某个具体业务项目有特殊需求,自己写了一个配置类并手动通过 @Bean 注入了一个特殊的 SqlSessionFactory(例如需要配置多数据源、复杂的拦截器插件、或者自定义类型转换器)。
    如果不加这个注解:
    Spring 容器启动时会同时加载业务项目定义的 SqlSessionFactory 和你框架里定义的 SqlSessionFactory,直接抛出 BeanDefinitionOverrideException(Bean 定义冲突崩溃)。
    加上这个注解后:Spring 看到业务项目自己已经造了一个 SqlSessionFactory,就会全自动跳过框架里这个默认的方法,完美实现“业务定制优先”。
    2. 完美兼容 MyBatis-Plus 的官方自动配置
    你们的项目引入了 MybatisPlusAutoConfiguration。当你的自动配置类运行之后,MyBatis-Plus 官方的自动配置类也会跟着启动。
    MyBatis-Plus 的源码在创建它自己的 SqlSessionFactory 时,类头上就带有 @ConditionalOnMissingBean(SqlSessionFactory.class) 注解。
    只要你先于它执行并创建了 SqlSessionFactory,加上这个注解能让团队内其他维护者一眼看出:“这个工厂是由我们 MBP 框架强行接管并提供的,后续官方的应该被跳过”。它明确了 Bean 的创建边界。
    3. 为"多数据源组件"留出兼容后路
    在企业级架构升级中,很多项目后续会引入类似 dynamic-datasource-spring-boot-starter(动态多数据源)或者原生的多数据源配置。
    多数据源框架通常会接管并批量创建多个 SqlSessionFactory。加上这个注解,可以让基础框架在遇到多数据源环境时优雅地自动隐退,而不会成为由于硬编码强制注入而导致项目卡死、报错的"绊脚石"。*/
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 注入数据源
        sqlSessionFactoryBean.setDataSource(dataSource);

        // 对应 <property name="configLocation" value="classpath:sqlmap-config.xml"/>
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setConfigLocation(pathMatchingResourcePatternResolver.getResource("classpath:sqlmap-config.xml"));

        // 对应 <property name="mapperLocations" value="classpath:sqlmap/*.xml"/>
        //sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources("classpath:sqlmap/*.xml"));
        // 对应 <property name="typeAliasesPackage" value="com.mbp.test.eng.domain"/>
        //sqlSessionFactoryBean.setTypeAliasesPackage("com.mbp.test.eng.domain");

        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 对应原 XML 中的 <bean id="sqlTemplate" scope="prototype">
     * 使用构造函数注入第一个参数 index="0" -> sqlSessionFactory
     */
    /*@Bean(name = "sqlTemplate")
    @Primary
    @Scope("prototype")
    public SqlSessionTemplate sqlTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }*/

    /**
     * 对应原 XML 中的 <bean id="sqlTemplate" scope="prototype">
     * 使用构造函数注入第一个参数 index="0" -> sqlSessionFactory
     * 更名为 sqlSessionTemplate 并打上 @Primary 终结多 Bean 抉择冲突
     */
    @Bean(name = {"sqlSessionTemplate", "sqlTemplate"})
    @Primary
    @Scope("prototype")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 对应原 XML 中的 <bean id="batchSqlTemplate" scope="prototype">
     * 传入 ExecutorType.BATCH 以开启批量执行模式
     */
    @Bean(name = "batchSqlTemplate")
    @Scope("prototype")
    public SqlSessionTemplate batchSqlTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }

    static {
        // 动态 SQL 智能优化支持本地缓存加速解析,更完善的租户复杂 XML 动态 SQL 支持,静态注入缓存
        JsqlParserGlobal.setJsqlParseCache(new JdkSerialCaffeineJsqlParseCache(
                (cache) -> cache.maximumSize(1024)
                        .expireAfterWrite(5, TimeUnit.SECONDS))
        );
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor()); // 分页插件
        // ↓↓↓ 按需开启,可能会影响到 updateBatch 的地方:例如说文件配置管理 ↓↓↓
        // mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor()); // 拦截没有指定条件的 update 和 delete 语句
        return mybatisPlusInterceptor;
    }

    @Bean
    public MetaObjectHandler defaultMetaObjectHandler() {
        //通用参数填充实现类;
        return new DefaultDBFieldHandler();
    }

    @Bean
    @ConditionalOnProperty(prefix = "mybatis-plus.global-config.db-config", name = "id-type", havingValue = "INPUT")
    public IKeyGenerator keyGenerator(ConfigurableEnvironment environment) {
        DbType dbType = IdTypeEnvironmentPostProcessor.getDbType(environment);
        if (dbType != null) {
            switch (dbType) {
                case POSTGRE_SQL:
                    return new PostgreKeyGenerator();
                case ORACLE:
                case ORACLE_12C:
                    return new OracleKeyGenerator();
                case H2:
                    return new H2KeyGenerator();
                case KINGBASE_ES:
                    return new KingbaseKeyGenerator();
                case DM:
                    return new DmKeyGenerator();
            }
        }
        // 找不到合适的 IKeyGenerator 实现类
        throw new IllegalArgumentException(StrUtil.format("DbType{} 找不到合适的 IKeyGenerator 实现类", dbType));
    }

    @Bean // 特殊:返回结果使用 Object 而不用 JacksonTypeHandler 的原因,避免因为 JacksonTypeHandler 被 mybatis 全局使用!
    public Object jacksonTypeHandler(List<ObjectMapper> objectMappers) {
        // 特殊:设置 JacksonTypeHandler 的 ObjectMapper!
        ObjectMapper objectMapper = CollUtil.getFirst(objectMappers);
        if (objectMapper == null) {
            objectMapper = JsonUtils.getObjectMapper();
        }
        JacksonTypeHandler.setObjectMapper(objectMapper);
        return new JacksonTypeHandler(Object.class);
    }
}
