package com.nutanix.classloader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class CustomClassLoader extends URLClassLoader {

  private ClassLoader parentCl;

  private CustomClassLoader(URL[] indir, ClassLoader parentCl) {
    super(indir);
    this.parentCl = parentCl;
  }

  @SuppressWarnings("WeakerAccess")
  public static CustomClassLoader classLoaderForPath(String basePath) {
    // collect jar artifacts from this location and then form the URLs to include
    File baseDir = new File(basePath);
    // if baseDir is not a directory - throw IllegalArgumentException

    // else - get all the jars from this folder. collect them in an array

    // create a custom classloader instance with these folders in an array and return it.

    return null;
  }

  @Override
  public Class<?> findClass(String name) throws ClassNotFoundException{
    //need to implement method with input directory
    Class<?> foundClass = super.findClass(name);
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
