package main.java;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ConfigClass1 {

  @Bean
  public String config1Bean1(){
    return "This is a bean";
  }

  @Bean
  public ArrayList<Integer> config1Bean2(){
    return new ArrayList<>();
  }
}
