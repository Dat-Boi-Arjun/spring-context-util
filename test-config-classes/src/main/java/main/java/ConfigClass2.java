package main.java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ConfigClass2 {

  @Bean
  public Integer config2Bean1(){
    return 5;
  }

  @Bean
  public HashMap<String, Double> config2Bean2(){
    return new HashMap<String, Double>();
  }
}
