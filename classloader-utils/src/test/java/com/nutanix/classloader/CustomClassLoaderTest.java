package com.nutanix.classloader;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomClassLoaderTest {

  private String path1 = System.getProperty("testDir1");
  private String path2 = System.getProperty("testDir2");

  @Test
  public void mustFindClassWhenLoadedByCustomClassLoader() throws ClassNotFoundException {
    CustomClassLoader cl1 = CustomClassLoader.classLoaderForPath(path1);
    Assert.assertNotNull(cl1);
    Class<?> expectedClass = cl1.findClass("main.TestClass1");
    Assert.assertNotNull(expectedClass);
  }

//  @Test
//  public void shouldFindDifferentClassesWithDifferentClassLoaders() throws ClassNotFoundException {
//    Class<?> cl1Class = cl1.findClass("main.TestClass1");
//    Class<?> cl2Class = cl2.findClass("main.TestClass1");
//    Assert.assertNotSame(cl1Class, cl2Class);
//
//  }
//
//  @Test(expectedExceptions = ClassNotFoundException.class)
//  public void shouldNotFindClassesWithGivenDirectory() throws ClassNotFoundException {
//    cl1.findClass("main.TestClass2");
//    cl2.findClass("main.TestClass1");
//  }
//
//  @Test
//  public void allClassLoadersLoadTheSameClass() throws ClassNotFoundException, MalformedURLException {
//    URL[] baseDir = {new File(System.getProperty("baseDir")).toURI().toURL()};
//    Class<?> ClassV1 = Class.forName(this.getClass().getName());
//    Class<?> ClassV2 = Class.forName(
//        this.getClass().getName(),
//        true, new CustomClassLoader(baseDir, new URLClassLoader(baseDir)));
//    Class<?> ClassV3 = Class.forName(this.getClass().getName(), true, ClassLoader.getSystemClassLoader());
//
//    Assert.assertSame(ClassV1, ClassV2);
//    Assert.assertSame(ClassV2, ClassV3);
//  }
}