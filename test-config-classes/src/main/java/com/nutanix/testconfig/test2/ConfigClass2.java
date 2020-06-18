package com.nutanix.testconfig.test2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ConfigClass2 {

  @Bean(name = "bean3")
  public Integer config2Bean1(){
    return 5;
  }

  @Bean(name = "bean4")
  public HashMap<String, Double> config2Bean2(){
    return new HashMap<>();
  }
}
