package com.nutanix.classloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class CustomClassLoader extends URLClassLoader {

  private ClassLoader parentCl;

  private CustomClassLoader(URL[] inDir, ClassLoader parentCl) {
    super(inDir);
    this.parentCl = parentCl;
  }

  @SuppressWarnings("WeakerAccess")
  public static CustomClassLoader classLoaderForPath(String basePath, ClassLoader parentCl) throws MalformedURLException {
    // collect jar artifacts from this location and then form the URLs to include
    File baseDir = new File(basePath);
    File[] jars;

    // if baseDir is not a directory - throw IllegalArgumentException
    if (!baseDir.isDirectory()) {
      throw new IllegalArgumentException();
    }

    // else - get all the jars from this folder. collect them in an array
    else {
      jars = baseDir.listFiles((dir, name) -> name.endsWith(".jar"));//tests if it is a jar file
    }

    URL[] urls = Arrays.stream(jars).map((File jar) -> {
      try{
        return jar.toURI().toURL();
      } catch (MalformedURLException ex){
        throw new RuntimeException();
      }
    }).toArray(URL[]::new);

    // create a custom classloader instance with these folders in an array and return it.
    return new CustomClassLoader(urls, parentCl);
  }

  @Override
  public Class<?> findClass(String name) throws ClassNotFoundException {
    Class<?> foundClass = null;
    try {
      foundClass = super.loadClass(name);
    }
    catch (ClassNotFoundException ex1) {
      try {
        foundClass = super.findClass(name);
      }
      catch (ClassNotFoundException ex2) {
        this.parentCl.loadClass(name);
      }
    }
    return foundClass;
  }

}
