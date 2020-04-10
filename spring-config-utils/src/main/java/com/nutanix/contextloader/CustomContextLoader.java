package com.nutanix.contextloader;

import com.nutanix.classloader.CustomClassLoader;
import org.springframework.context.ApplicationContext;

import java.net.MalformedURLException;

public class CustomContextLoader{

  private ApplicationContext applicationContext;
  private String baseDir;//will be in separate modules

  public CustomContextLoader() {
    //take in fixture data
  }

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  private Class<?> loadBean() throws MalformedURLException, ClassNotFoundException {
    CustomClassLoader cl = CustomClassLoader.classLoaderForPath(baseDir, ClassLoader.getSystemClassLoader());
    Class<?> bean = cl.findClass("name of bean class");
    return bean;
  }

}
