package com.nutanix.contextloader;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomContextLoaderTest {

  private DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
  private AnnotationConfigApplicationContext parentCtx = new AnnotationConfigApplicationContext(beanFactory);

  private CustomContextLoader ctxLoader = new CustomContextLoader(this.beanFactory, this.parentCtx);
  private String configClassDir = System.getProperty("configClassDir");

  /*
    Tests:
    loadBeans
    findBeansFromMultipleConfigClass
    getBeanfromContext
    findParentBeanFromChild
  */

  @Test
  public void mustLoadBeans(){
    AnnotationConfigApplicationContext ctx = ctxLoader.loadContext(this.configClassDir);
    Assert.assertNotNull(ctx);
  }

  //config classes need to be turned into jars using maven dependency plugin
}
