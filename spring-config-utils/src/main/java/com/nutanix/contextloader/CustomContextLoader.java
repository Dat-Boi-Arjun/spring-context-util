package com.nutanix.contextloader;

import com.nutanix.classloader.CustomClassLoader;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;

@Slf4j
@Data
public class CustomContextLoader {

  private AnnotationConfigApplicationContext parentCtx;

  public CustomContextLoader(AnnotationConfigApplicationContext parentCtx) {
    this.parentCtx = parentCtx;
  }

  public AnnotationConfigApplicationContext loadContext(String classpath) throws MalformedURLException{
    CustomClassLoader cl = CustomClassLoader.classLoaderForPath(classpath, ClassLoader.getSystemClassLoader());
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

    try (InputStream is = cl.getResourceAsStream("applicationContext.properties")){
      if (is != null){
        Properties properties = new Properties();
        properties.load(is);
        String baseConfigPackage = properties.getProperty("config.basePackage");
        ctx.setParent(this.parentCtx);
        ctx.setClassLoader(cl);
        ctx.scan(baseConfigPackage);
        ctx.refresh();

      }
      else {
        throw new IllegalArgumentException("Could not find bootstrap properties");
      }
    }
    catch (IOException e) {
      log.error("Failed", e);
    }

    return ctx;
  }


}
