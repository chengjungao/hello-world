package com.cheng.jungao.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 测试Java热部署
 * @author wolf
 *
 */
public class MyClassLoader {
	//使用URLClassLoader从磁盘加载jar
	private URLClassLoader extClassLoader;
	
	public MyClassLoader (String extClassPath) throws MalformedURLException {
		this.extClassLoader = new URLClassLoader(getJars (extClassPath),this.getClass().getClassLoader() );
	}
	
	//通过类名使用无参构造器实例化需要热部署的class的对象
	@SuppressWarnings("unchecked")
	public <T>T getInstance (String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException {
		Class<T> clazz =  (Class<T>) extClassLoader.loadClass (className);
		return (T) clazz.getConstructor();
	}
	
	//reloader extClassLoader，实现Class更新
	public boolean reload(String extClassPath) throws IOException {
		URLClassLoader newExtClassLoader = new URLClassLoader(getJars (extClassPath), this.getClass().getClassLoader() );
		URLClassLoader oldClassLoader = extClassLoader;
		extClassLoader = newExtClassLoader;
		oldClassLoader.close ();
		return true;
	}
	
	@SuppressWarnings ("deprecation")
	private URL[] getJars (String extClassPath) throws MalformedURLException {
		File dir = new File (extClassPath);
		File[] jars = dir.listFiles ();
		URL[] urls = new URL[jars.length];
		for (int i = 0; i < jars.length; i++) {
			urls[i] = jars[i].toURL();
		}
		return urls;
	}
}
