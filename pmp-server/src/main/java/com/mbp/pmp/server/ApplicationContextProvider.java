package com.mbp.pmp.server;

import org.springframework.context.ApplicationContext;

public class ApplicationContextProvider {
    private static ApplicationContext applicationContext;

    private static ApplicationContextProvider instance = new ApplicationContextProvider();

    private ApplicationContextProvider() {
    }

    public static ApplicationContextProvider getInstance() {
        return instance;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
}
