package com.nutanix.classloader;

import java.net.URL;
import java.net.URLClassLoader;

public class CustomClassLoader extends URLClassLoader {

  private URLClassLoader parentCl;

  public CustomClassLoader(URL[] indir, URLClassLoader parentCl) {
    super(indir);
    this.parentCl = parentCl;
  }

  @Override
  public Class<?> findClass(String name) throws ClassNotFoundException{
    //need to implement method with input directory
    Class<?> foundClass = super.loadClass(name);
    if (foundClass == null) {
      try {
        foundClass = super.findClass(name);
      }
      catch (ClassNotFoundException ex){
        this.parentCl.loadClass(name);
      }
    }
     return foundClass;
  }

  
  
}
