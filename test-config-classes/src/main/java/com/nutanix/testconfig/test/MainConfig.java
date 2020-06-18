package com.nutanix.testconfig.test;

import com.nutanix.testconfig.test1.ConfigClass1;
import com.nutanix.testconfig.test2.ConfigClass2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ConfigClass1.class, ConfigClass2.class})
public class MainConfig {
}
