package com.nutanix.classloader;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;


public class CustomClassLoaderTest {

  private String path1 = System.getProperty("testDir1");
  private String path2 = System.getProperty("testDir2");
  private String baseDir = System.getProperty("baseDir");
  private ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

  @DataProvider(name = "findsClass")
  public Object[][] findObjectsFromLoaders() throws MalformedURLException, ClassNotFoundException {
    CustomClassLoader cl1 = CustomClassLoader.classLoaderForPath(path1, systemClassLoader);
    CustomClassLoader cl2 = CustomClassLoader.classLoaderForPath(path2, systemClassLoader);

    Class<?> cl1Class = cl1.findClass("TestClass1");
    Class<?> cl2Class = cl2.findClass("TestClass2");

    Object[][] data = {{cl1, cl1Class}, {cl2, cl2Class}};
    return data;

  }

  @Test(dataProvider = "findsClass")
  public void mustFindClassWhenLoadedByCustomClassLoader(CustomClassLoader cl, Class<?> loadedClass){
    Assert.assertNotNull(cl);
    Assert.assertNotNull(loadedClass);
  }

  @Test
  public void shouldFindDifferentClassesWithDifferentClassLoaders() throws ClassNotFoundException, MalformedURLException {
    CustomClassLoader cl1 = CustomClassLoader.classLoaderForPath(path1, systemClassLoader);
    CustomClassLoader cl2 = CustomClassLoader.classLoaderForPath(path2, systemClassLoader);
    Class<?> cl1Class = cl1.findClass("TestClass1");
    Class<?> cl2Class = cl2.findClass("TestClass2");

    Assert.assertNotNull(cl1Class);
    Assert.assertNotNull(cl2Class);
    Assert.assertNotSame(cl1Class, cl2Class);

  }

  @Test(expectedExceptions = ClassNotFoundException.class)
  public void shouldNotFindClassesWithGivenDirectory() throws ClassNotFoundException, MalformedURLException {
    CustomClassLoader cl1 = CustomClassLoader.classLoaderForPath(path1, systemClassLoader);
    cl1.findClass("TestClass2");
  }

  @Test
  public void mustLoadSameClassAsAllParentClassLoaders() throws ClassNotFoundException, MalformedURLException {
    String className = this.getClass().getName();
    CustomClassLoader cl = CustomClassLoader.classLoaderForPath(baseDir, systemClassLoader);
    Class<?> ClassV1 = Class.forName(className);
    Class<?> ClassV2 = Class.forName(className, true, cl);
    Class<?> ClassV3 = Class.forName(className, true, systemClassLoader);

    Assert.assertSame(ClassV1, ClassV2);
    Assert.assertSame(ClassV2, ClassV3);
  }

  @Test
  public void mustLoadClassFromParentClassLoader() throws ClassNotFoundException, MalformedURLException {
    String className = this.getClass().getName();
    CustomClassLoader cl = CustomClassLoader.classLoaderForPath(baseDir, systemClassLoader);
    Class<?> fromChild = Class.forName(className, true, cl);

    Assert.assertSame(this.getClass(), fromChild);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldNotAcceptPathIfNotDirectory() throws IllegalArgumentException, MalformedURLException {
    String notDirectory = "CustomClassLoaderTest.java";
    CustomClassLoader.classLoaderForPath(notDirectory, systemClassLoader);
  }

  @Test
  public void shouldNotFindFilesIfNotJars() throws MalformedURLException{
    String pathWithoutJars = baseDir.concat("/classloader-utils/src");
    CustomClassLoader cl = CustomClassLoader.classLoaderForPath(pathWithoutJars, systemClassLoader);

    Assert.assertEquals(cl.getURLs().length, 0);
  }

  @Test
  public void mustFindDifferentClassesWithSameNameWithDifferentClassLoaders() throws ClassNotFoundException, MalformedURLException{
    CustomClassLoader cl1 = CustomClassLoader.classLoaderForPath(path1, systemClassLoader);
    CustomClassLoader cl2 = CustomClassLoader.classLoaderForPath(path2, systemClassLoader);
    Class<?> testClassDir1 = cl1.findClass("TestClass1");
    Class<?> testClassDir2 = cl2.findClass("TestClass1");

    Assert.assertNotNull(testClassDir1);
    Assert.assertNotNull(testClassDir2);
    Assert.assertNotSame(testClassDir1, testClassDir2);

  }
}