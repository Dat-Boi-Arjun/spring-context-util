package com.nutanix.classloader;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

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

    URL[] urls = new URL[jars.length];

    for (int i = 0; i < jars.length; i++) {
      urls[i] = jars[i].toURI().toURL();
    }

    // create a custom classloader instance with these folders in an array and return it.
    return new CustomClassLoader(urls, parentCl);
  }

  @Override
  public Class<?> findClass(String name) throws ClassNotFoundException {
    //need to implement method with input directory
    Class<?> foundClass = super.findClass(name);
    if (foundClass == null) {
      try {
        foundClass = super.findClass(name);
      }
      catch (ClassNotFoundException ex) {
        this.parentCl.loadClass(name);
      }
    }
    return foundClass;
  }

}
