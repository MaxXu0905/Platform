package com.ailk.sets.grade.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;

import com.ailk.sets.grade.grade.common.GradeConst;

public class MyRefClassLoader extends URLClassLoader {

	private String root;

	public static MyRefClassLoader getInstance(String root) {
		URLClassLoader classLoader = (URLClassLoader) Thread.currentThread()
				.getContextClassLoader();
		MyRefClassLoader instance = new MyRefClassLoader(classLoader.getURLs(), classLoader);
		instance.setRoot(root);
		return instance;
	}

	public MyRefClassLoader(URL[] urls, URLClassLoader parent) {
		super(urls, parent);
	}

	public void setRoot(String root) {
		this.root = root;
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> clazz = null;

		try {
			clazz = super.findClass(name);
		} catch (ClassNotFoundException ex) {
			if (clazz == null)
				clazz = _findClass(name);

			if (clazz == null)
				throw ex;
		}

		return clazz;
	}

	private Class<?> _findClass(String name) throws ClassNotFoundException {
		Class<?> clazz = null;
		String filePath = root + GradeConst.REFERENCE_PREFIX + File.separator
				+ name.replace(".", File.separator) + ".class";
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					filePath));
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			int size, SIZE = 4096;
			byte[] buffer = new byte[SIZE];
			while ((size = fileInputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, size);
			}
			fileInputStream.close();
			byte[] classBytes = outputStream.toByteArray();
			outputStream.close();

			clazz = defineClass(name, classBytes, 0, classBytes.length);
		} catch (Exception ex) {
			throw new ClassNotFoundException(name);
		}

		return clazz;
	}
}
