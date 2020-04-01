package com.nutanix.classloader;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class CustomClassLoaderTest {

  private File path1 = new File(System.getProperty("testDir1"));
  private File path2 = new File(System.getProperty("testDir2"));

  private URL testClass1Location = path1.toURI().toURL();
  private URL testClass2Location = path2.toURI().toURL();

  private URL[] inDir1 = {testClass1Location};
  private URL[] inDir2 = {testClass2Location};

  URLClassLoader uCl1 = new URLClassLoader(inDir1);
  CustomClassLoader cl1 = new CustomClassLoader(inDir1, uCl1);

  URLClassLoader uCl2 = new URLClassLoader(inDir2);
  CustomClassLoader cl2 = new CustomClassLoader(inDir2, uCl2);

  public CustomClassLoaderTest() throws MalformedURLException {
    //need to handle the exception from converting the files to URLs
  }

  @Test
  public void mustFindClassWhenLoadedByChildAndParentClassLoaders() throws ClassNotFoundException {
    Class<?> cl1Class = cl1.findClass("main.TestClass1");
    Class<?> expected1 = uCl1.loadClass("main.TestClass1");
    Assert.assertSame(cl1Class, expected1);

    Class<?> cl2Class = cl2.findClass("main.TestClass2");
    Class<?> expected2 = uCl2.loadClass("main.TestClass2");
    Assert.assertSame(cl2Class, expected2);

  }

  @Test
  public void shouldFindDifferentClassesWithDifferentClassLoaders() throws ClassNotFoundException {
    Class<?> cl1Class = cl1.findClass("main.TestClass1");
    Class<?> cl2Class = cl2.findClass("main.TestClass1");
    Assert.assertNotSame(cl1Class, cl2Class);

  }

  @Test(expectedExceptions = ClassNotFoundException.class)
  public void shouldNotFindClassesWithGivenDirectory() throws ClassNotFoundException {
    cl1.findClass("main.TestClass2");
    cl2.findClass("main.TestClass1");
  }

  @Test
  public void allClassLoadersLoadTheSameClass() throws ClassNotFoundException, MalformedURLException {
    URL[] baseDir = {new File(System.getProperty("baseDir")).toURI().toURL()};
    Class<?> ClassV1 = Class.forName(this.getClass().getName());
    Class<?> ClassV2 = Class.forName(
        this.getClass().getName(),
        true, new CustomClassLoader(baseDir, new URLClassLoader(baseDir)));
    Class<?> ClassV3 = Class.forName(this.getClass().getName(), true, ClassLoader.getSystemClassLoader());

    Assert.assertSame(ClassV1, ClassV2);
    Assert.assertSame(ClassV2, ClassV3);
  }
}