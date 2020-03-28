package com.nutanix.classloader;

import org.junit.Test;

import java.net.URL;
import java.net.URLClassLoader;

class CustomClassLoaderTest {

    private URL testClassLocation = TestClass.class.getProtectionDomain().getCodeSource().getLocation();
    private URL indir[] = {testClassLocation};
    private String TestClassFullyQualifiedName = TestClass.class.getName();
    URLClassLoader uCl = new URLClassLoader(indir);
    CustomClassLoader cl = new CustomClassLoader(indir, uCl);
    @Test
    void findClass() {
        try{
            assert cl.findClass(TestClassFullyQualifiedName).equals(uCl.loadClass(TestClassFullyQualifiedName);
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
}