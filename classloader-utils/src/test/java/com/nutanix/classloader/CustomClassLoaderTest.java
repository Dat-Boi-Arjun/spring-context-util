package com.nutanix.classloader;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class CustomClassLoaderTest {

  private String path1 = System.getProperty("testDir1");
  private String path2 = System.getProperty("testDir2");

  @Test
  public void mustFindClassWhenLoadedByCustomClassLoader() throws ClassNotFoundException, MalformedURLException {
    CustomClassLoader cl2 = CustomClassLoader.classLoaderForPath(path2, ClassLoader.getSystemClassLoader());
    Assert.assertNotNull(cl2);
    Class<?> expectedClass = cl2.findClass("TestClass2");
    Assert.assertNotNull(expectedClass);
  }

  @Test
  public void shouldFindDifferentClassesWithDifferentClassLoaders() throws ClassNotFoundException, MalformedURLException {
    CustomClassLoader cl1 = CustomClassLoader.classLoaderForPath(path1, ClassLoader.getSystemClassLoader());
    CustomClassLoader cl2 = CustomClassLoader.classLoaderForPath(path2, ClassLoader.getSystemClassLoader());
    Class<?> cl1Class = cl1.findClass("TestClass1");
    Class<?> cl2Class = cl2.findClass("TestClass2");
    Assert.assertNotSame(cl1Class, cl2Class);

  }

  @Test(expectedExceptions = ClassNotFoundException.class)
  public void shouldNotFindClassesWithGivenDirectory() throws ClassNotFoundException, MalformedURLException {
    CustomClassLoader cl1 = CustomClassLoader.classLoaderForPath(path1, ClassLoader.getSystemClassLoader());
    CustomClassLoader cl2 = CustomClassLoader.classLoaderForPath(path2, ClassLoader.getSystemClassLoader());
    cl1.findClass("TestClass2");
    cl2.findClass("TestClass1");
  }

  @Test
  public void allClassLoadersLoadTheSameClass() throws ClassNotFoundException, MalformedURLException {
    Class<?> ClassV1 = Class.forName(this.getClass().getName());
    Class<?> ClassV2 = Class.forName(
        this.getClass().getName(),
        true, CustomClassLoader.classLoaderForPath(System.getProperty("baseDir"), ClassLoader.getSystemClassLoader()));
    Class<?> ClassV3 = Class.forName(this.getClass().getName(), true, ClassLoader.getSystemClassLoader());

    Assert.assertSame(ClassV1, ClassV2);
    Assert.assertSame(ClassV2, ClassV3);
  }
}