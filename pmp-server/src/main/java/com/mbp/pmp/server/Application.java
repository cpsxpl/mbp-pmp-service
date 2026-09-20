package com.mbp.pmp.server;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.mbp.eng.framework.common.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${mbp.info.base-package}
@SpringBootApplication(scanBasePackages = {"${mbp.info.base-package}.server", "${mbp.info.base-package}.module", "com.kakarote.ids.provider"}, exclude = {KafkaAutoConfiguration.class})
/*
@EnableFeignClients(basePackages = {"${mbp.info.base-package}.module.work", "com.kakarote.ids.provider"})
@MapperScan(basePackages = {"${mbp.info.base-package}.module.mapper"})
@EnableMethodCache(basePackages = "${mbp.info.base-package}.module.work", order = -9999)
*/
@EnableCreateCacheAnnotation
@PropertySource(value = {"${important.properties.filepath}", /*"classpath:quartz.properties",*/ /*"classpath:spark.properties"*/}, encoding = "utf-8")
//@ImportResource(value = {"classpath:spring/applicationContext.xml"})
@EnableScheduling
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Value("${spring.messages.basename}")
    public String basename;

    @Value("${spring.messages.encoding.charset}")
    public String encoding;

    @Autowired
    ApplicationContext applicationContext;

    /*@Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory = new TomcatEmbeddedServletContainerFactory();
        return tomcatEmbeddedServletContainerFactory;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        synchronized (Application.class) {
            while (true) {
                try {
                    Application.class.wait();
                } catch (InterruptedException e) {
                    logger.error("mbp service interrupted ...");
                }
            }
        }
    }*/

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("locale/error");
        resourceBundleMessageSource.setDefaultEncoding(encoding);
        return resourceBundleMessageSource;
    }

    @Bean("mbpMessageSource")
    public MessageSource initMessageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("locale/mbp_error");
        resourceBundleMessageSource.setDefaultEncoding(encoding);
        return resourceBundleMessageSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doAfterStartup() {
        long time = System.currentTimeMillis();
        logger.info("Application.doAfterStartup time:{}", DateUtil.getFormatTime(time));
        ApplicationContextProvider.getInstance().setApplicationContext(applicationContext);
    }
}
