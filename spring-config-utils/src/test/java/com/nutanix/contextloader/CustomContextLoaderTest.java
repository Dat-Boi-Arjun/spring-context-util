package com.nutanix.contextloader;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;


public class CustomContextLoaderTest {

  private final AnnotationConfigApplicationContext parentCtx = new AnnotationConfigApplicationContext();

  private final CustomContextLoader ctxLoader = new CustomContextLoader(this.parentCtx);
  private final String configClassDir = System.getProperty("configClassDir");

  /*
    Tests:
    loadBeans
    findBeansFromMultipleConfigClass
    getBeanfromContext
    findParentBeanFromChild
  */

  @Configuration
  private class ParentConfig {
    //dummy class for parent context
    public ParentConfig(){}
  }


  @Test
  public void mustLoadBeans() throws MalformedURLException{
    AnnotationConfigApplicationContext ctx = ctxLoader.loadContext(configClassDir);
    Assert.assertNotNull(ctx);
  }

  @Test
  public void mustfindBeansFromMultipleConfigClasses() throws MalformedURLException{
    AnnotationConfigApplicationContext ctx = ctxLoader.loadContext(configClassDir);
    Assert.assertTrue(ctx.containsBean("bean1"));
    Assert.assertTrue(ctx.containsBean("bean3"));
  }
}
