package com.nutanix.testconfig.test1;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ConfigClass1 {

  @Bean(name = "bean1")
  public String config1Bean1(){ return "A string";}

  @Bean(name = "bean2")
  public List<Integer> config1Bean2(){
    return new ArrayList<>();
  }

  @Bean(name = "bean5")
  public String config1Bean3(){
    return "Also a bean";
  }
}
