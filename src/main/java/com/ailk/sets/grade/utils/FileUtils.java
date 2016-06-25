package com.ailk.sets.grade.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.poi.util.IOUtils;

import com.ailk.sets.grade.grade.common.GradeConst;

public class FileUtils {

	/**
	 * 从文件中读取内容
	 * 
	 * @param filename
	 *            文件名
	 * @return 文件内容
	 */
	public static String getContent(String filename) {
		InputStream in = null;

		try {
			File file = new File(filename);
			if (!file.isFile())
				return null;

			in = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(in);

			return new String(bytes, GradeConst.ENCODING);
		} catch (Exception e) {
			return null;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	public static String getContent(InputStream in) {
		try {
			byte[] bytes = IOUtils.toByteArray(in);

			return new String(bytes, GradeConst.ENCODING);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把数据写入文件
	 * 
	 * @param filename
	 *            文件名
	 * @param content
	 *            内容
	 * @throws Exception
	 * @throws
	 */
	public static void setContent(String filename, String content)
			throws Exception {
		OutputStreamWriter out = null;

		try {
			out = new OutputStreamWriter(new FileOutputStream(filename),
					GradeConst.ENCODING);
			out.write(content);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 把数据写入文件
	 * 
	 * @param filename
	 *            文件名
	 * @param content
	 *            内容
	 * @throws Exception
	 */
	public static void setContent(String filename, byte[] content)
			throws Exception {
		FileOutputStream out = null;

		try {
			out = new FileOutputStream(new File(filename));
			out.write(content);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 对指定文件或目录进行打包
	 * 
	 * @param tos
	 *            tar目标输出
	 * @param file
	 *            输入文件或目录
	 * @param basePath
	 *            tar根目录
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void archive(TarArchiveOutputStream tos, File file,
			String basePath) throws FileNotFoundException, IOException {
		if (file.isDirectory())
			archiveDir(tos, file, basePath + File.separator + file.getName());
		else
			archiveFile(tos, file, basePath);
	}

	/**
	 * 对指定目录进行打包
	 * 
	 * @param tos
	 *            tar目标输出
	 * @param dir
	 *            输入目录
	 * @param basePath
	 *            tar根目录
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void archiveDir(TarArchiveOutputStream tos, File dir,
			String basePath) throws FileNotFoundException, IOException {
		File[] files = dir.listFiles();

		if (files.length < 1) {
			TarArchiveEntry entry = new TarArchiveEntry(basePath);

			tos.putArchiveEntry(entry);
			tos.closeArchiveEntry();
		}

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			archive(tos, file, basePath);
		}
	}

	/**
	 * 对指定文件进行打包
	 * 
	 * @param tos
	 *            tar目标输出
	 * @param file
	 *            输入文件
	 * @param basePath
	 *            tar根目录
	 * @throws IOException
	 */
	public static void archiveFile(TarArchiveOutputStream tos, File file,
			String basePath) throws IOException {
		TarArchiveEntry entry = new TarArchiveEntry(basePath + File.separator
				+ file.getName());

		entry.setSize(file.length());
		FileInputStream in = new FileInputStream(file);
		try {
			tos.putArchiveEntry(entry);
			IOUtils.copy(in, tos);
			tos.closeArchiveEntry();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 对指定文件进行打包
	 * 
	 * @param tos
	 *            tar目标输出
	 * @param in
	 *            输入流
	 * @filename 文件名
	 * @param basePath
	 *            tar根目录
	 * @throws IOException
	 */
	public static void archiveFile(TarArchiveOutputStream tos, InputStream in,
			String filename, String basePath) throws IOException {
		TarArchiveEntry entry = new TarArchiveEntry(basePath + File.separator
				+ filename);

		byte[] bytes = IOUtils.toByteArray(in);
		entry.setSize(bytes.length);

		tos.putArchiveEntry(entry);
		tos.write(bytes);
		tos.closeArchiveEntry();
	}

	/**
	 * 对指定内容进行打包
	 * 
	 * @param tos
	 *            tar目标输出
	 * @param data
	 *            输出内容
	 * @filename 文件名
	 * @param basePath
	 *            tar根目录
	 * @throws IOException
	 */
	public static void archiveFile(TarArchiveOutputStream tos, String data,
			String filename, String basePath) throws IOException {
		TarArchiveEntry entry = new TarArchiveEntry(basePath + File.separator
				+ filename);

		byte[] bytes = data.getBytes(GradeConst.ENCODING);
		entry.setSize(bytes.length);

		tos.putArchiveEntry(entry);
		tos.write(bytes);
		tos.closeArchiveEntry();
	}

	/**
	 * untar文件
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @throws Exception
	 */
	public static void dearchive(File srcFile, File destFile) throws Exception {
		TarArchiveInputStream tis = null;

		try {
			tis = new TarArchiveInputStream(new FileInputStream(srcFile));
			dearchive(destFile, tis);
		} finally {
			tis.close();
		}
	}

	/**
	 * untar文件
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destPath
	 *            目标文件
	 * @throws Exception
	 */
	public static void dearchive(File srcFile, String destPath)
			throws Exception {
		dearchive(srcFile, new File(destPath));
	}

	/**
	 * untar文件
	 * 
	 * @param destFile
	 *            目标目录
	 * @param tis
	 *            输入
	 * @throws Exception
	 */
	public static void dearchive(File destFile, TarArchiveInputStream tis)
			throws Exception {
		TarArchiveEntry entry = null;

		while ((entry = tis.getNextTarEntry()) != null) {
			String dir = destFile.getPath() + File.separator + entry.getName();
			File dirFile = new File(dir);

			// 文件检查
			File parentFile = dirFile.getParentFile();
			if (!parentFile.exists())
				parentFile.mkdirs();

			if (entry.isDirectory())
				dirFile.mkdir();
			else
				dearchiveFile(dirFile, tis);
		}
	}

	public static InputStream dearchive(String entryName,
			TarArchiveInputStream tis) throws Exception {
		TarArchiveEntry entry = null;

		while ((entry = tis.getNextTarEntry()) != null) {
			if (!entry.getName().equals(entryName))
				continue;

			if (entry.isDirectory())
				return null;

			return tis;
		}

		return null;
	}

	/**
	 * untar文件
	 * 
	 * @param destFile
	 *            目标文件
	 * @param tis
	 *            输入
	 * @throws Exception
	 */
	public static void dearchiveFile(File destFile, TarArchiveInputStream tis)
			throws Exception {
		FileOutputStream out = null;

		try {
			out = new FileOutputStream(destFile);
			IOUtils.copy(tis, out);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

	public static void chmod(String permission, String filename)
			throws IOException {
		Runtime.getRuntime().exec("chmod " + permission + " " + filename);
	}

}
