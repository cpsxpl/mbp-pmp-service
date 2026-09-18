package com.mbp.eng.sql;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * Created by Mr.cpsxpl
 */
public class Generate {
    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir("mbp-dao/src/main/out");
        globalConfig.setFileOverride(true);
        globalConfig.setActiveRecord(true);
        globalConfig.setEnableCache(false); // XML 二级缓存
        globalConfig.setBaseResultMap(true); // XML ResultMap
        globalConfig.setBaseColumnList(false); // XML columList
        globalConfig.setAuthor("cpsxpl");
        // 自定义文件命名,注意 %s 会自动填充表实体属性!
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换[可选]
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型:" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换,如果不是你要的效果请自定义返回、非如下直接返回.
                return super.processTypeConvert(fieldType);
            }
        });
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root123ABC");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/common-admin?characterEncoding=utf8");
        generator.setDataSource(dataSourceConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel); // 表名生成策略
        strategyConfig.setInclude(new String[]{"rc_user"}); // 需要生成的表
        // strategyConfig.setExclude(new String[]{"test"}); // 排除生成的表
        generator.setStrategy(strategyConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.mbp.test");
        packageConfig.setModuleName(null);
        packageConfig.setEntity("domain");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setMapper("dao");
        packageConfig.setXml("sqlmap");
        packageConfig.setController("controller");
        generator.setPackageInfo(packageConfig);
        //执行
        generator.execute();
    }
}
