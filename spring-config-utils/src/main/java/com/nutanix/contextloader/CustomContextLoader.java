package com.nutanix.contextloader;

import com.nutanix.classloader.CustomClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.MalformedURLException;

@Slf4j
public class CustomContextLoader{

  private DefaultListableBeanFactory beanFactory;
  private AnnotationConfigApplicationContext parentCtx;

  public CustomContextLoader(DefaultListableBeanFactory beanFactory, AnnotationConfigApplicationContext parentCtx) {
    this.beanFactory = beanFactory;
    this.parentCtx = parentCtx;
  }

  public AnnotationConfigApplicationContext loadContext(String classpath) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(beanFactory);
    try {
      ctx.setParent(this.parentCtx);
      ctx.setClassLoader(CustomClassLoader.classLoaderForPath(classpath, ClassLoader.getSystemClassLoader()));
      ctx.refresh();
    }
    catch (MalformedURLException e) {
      log.error("Failed", e);
    }

    return ctx;
  }
    /*
    Tests:
    loadBeans
    findBeansFromMulitpleConfigClass
    getBeanfromContext
    findParentBeanFromChild
     */





}
