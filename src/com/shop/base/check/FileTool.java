package com.shop.base.check;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;



/**
 * @title:文件操作工具类
 * @description:
 * @author sfs
 * @date 2013-03-27
 */
public class FileTool {

	public FileTool() {
	}

	/**
	 * 
	 * @param filename,context
	 *            向文件名为filename的文件写入内容context！
	 * @return int
	 * 
	 */
	public static int writeFile(String filename, String context)
			throws IOException {
		Writer writer = null;
		try {

			writer = getBufferedWriter(filename);
			writer.write(context);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != writer) {
					writer.flush();
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return 1;
	}

	/**
	 * 
	 * @param path，filename,context
	 *            向路径为path，文件名为filename的文件写入内容context！
	 * @return int
	 * 
	 */
	public static int writeFile(String path, String filename, String context)
			throws IOException {
		Writer writer = null;
		try {
			writer = getBufferedWriter(path, filename);
			writer.write(context);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != writer) {
					writer.flush();
					writer.close();
				}
			} catch (IOException e) {
			}
		}
		return 1;
	}

	/**
	 * 
	 * @param filename,context，append
	 *            向文件名为filename的文件写入内容context！
	 * @param append
	 *            如果为true ，向文件追加内容context
	 * @return int
	 * 
	 */
	public static int writeFile(String filename, boolean append, String context)
			throws IOException {
		Writer writer = null;
		try {
			writer = getBufferedWriter(filename, append);
			writer.write(context);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != writer) {
					writer.flush();
					writer.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return 1;
	}

	/**
	 * 
	 * @param path
	 *            ，filename,context，append 向路径为path，文件名为filename的文件写入内容context！
	 * @param append
	 *            如果为true ，向文件追加内容context
	 * @return int
	 * 
	 */
	public static int writeFile(String path, String filename, boolean append,
			String context) throws IOException {
		Writer writer = null;
		try {
			writer = getBufferedWriter(path, filename, append);
			writer.write(context);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != writer) {
					writer.flush();
					writer.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return 1;
	}

	/**
	 * 
	 * 
	 * @param path
	 *            ,filename
	 * 
	 * @return writer
	 * 
	 */
	public static BufferedWriter getBufferedWriter(String path, String filename)
			throws FileNotFoundException {
		BufferedWriter writer = null;
		File fpath = new File(path);
		if (!fpath.isDirectory()) {
			fpath.mkdir();
		}
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path + "\\" + filename),"UTF-8"));

		} catch (FileNotFoundException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return writer;
	}

	/**
	 * 
	 * 
	 * @param path
	 *            ,filename
	 * @return writer
	 * 
	 */
	public static BufferedWriter getBufferedWriter(String path,
			String filename, boolean append) throws FileNotFoundException {
		BufferedWriter writer = null;
		File fpath = new File(path);
		if (!fpath.isDirectory()) {
			fpath.mkdir();
		}
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path + "\\" + filename, append),"UTF-8"));

		} catch (FileNotFoundException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return writer;
	}

	/**
	 * 
	 * @param filename
	 * @return writer
	 */
	public static BufferedWriter getBufferedWriter(String filename)
			throws FileNotFoundException {

		BufferedWriter writer = null;
		try {
			File file = createNewFile(filename);

			writer = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream(file),"UTF-8"));

		} catch (FileNotFoundException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return writer;
	}

	public static boolean writeXmlFile(String fileName, String context)
			throws IOException {
		
		BufferedWriter writer = null;
		
		try {
			File file = new File(fileName);

			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file),"UTF-8"));

			writer.write(context);
			
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != writer) {
					writer.flush();
					writer.close();
				}
			} catch (IOException e) {
				
			}
		}

		return true;
	}

	/**
	 * 
	 * @param filename
	 * @return outputStream
	 */
	public static OutputStream getOutStream(String filename)
			throws FileNotFoundException {
		OutputStream out = null;
		try {
			out = new FileOutputStream(filename);

		} catch (FileNotFoundException e) {
			throw e;
		}
		return out;
	}

	/**
	 * 
	 * @param filename,append
	 * @return writer
	 */
	public static BufferedWriter getBufferedWriter(String filename,
			boolean append) throws FileNotFoundException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename, append),"UTF-8"));

		} catch (FileNotFoundException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return writer;
	}

	public static String readFile(String filename) throws IOException {
		BufferedReader reader = getBufferedReader(filename);
		StringBuffer sb = new StringBuffer();
		try {
			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\r\n");
				line = reader.readLine();
			}
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (null != reader) {
				reader.close();
			}
		}

		return sb.toString();
	}
	
	public static String searchFile(String filename,String pattern) throws IOException {
		String line="";
		BufferedReader reader = getBufferedReader(filename);
		
		try {
			line = reader.readLine();
			while (line != null) {
				if(line.indexOf(pattern)>=0){
					break;
				}
				line = reader.readLine();
			}
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (null != reader) {
				reader.close();
			}
		}

		return line;
	}


	public static String readFile(String path, String filename)
			throws IOException {
		BufferedReader reader = getBufferedReader(path, filename);
		StringBuffer sb = new StringBuffer();
		try {
			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (null != reader) {
				reader.close();
			}
		}

		return sb.toString();
	}

	/**
	 * 
	 * @param filename
	 * @return reader
	 */
	public static BufferedReader getBufferedReader(String filename)
			throws FileNotFoundException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename),"UTF-8"));

		} catch (FileNotFoundException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return reader;
	}

	/**
	 * 
	 * @param path,filename
	 * @return reader
	 */
	public static BufferedReader getBufferedReader(String path, String filename) {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(path + "\\" + filename),"UTF-8"));

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return reader;
	}

	/*
	 * 删除文件夹 和下面所有 文件
	 */
	public static void deleteFile(File file_del) {
		if (file_del.isFile()) {
			file_del.delete();
			return;
		}
		File[] files = file_del.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteFile(files[i]);
				}
				files[i].delete();
			}
		}
		file_del.delete();
	}

	/**
	 * 创建文件 如果存在 则不创建
	 * 
	 * @param pathName
	 * @return
	 */
	public static File createNewFile(String pathName) {

		File file = new File(pathName);
		if (file.exists()) {
			return file;
		}
		try {
			createNewDir(file.getParent());
			file.createNewFile();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static File createNewDir(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file;
		}
		file.mkdirs();
		return file;
	}

	public static void main(String[] args) {
		String name = "D:\\src\\org\\xample\\dept\\view\\DeptView.java";
		File file = new File(name);
		if (!file.exists()) {
			boolean isDir = file.getParentFile().mkdirs();
			System.err.println(isDir);
			boolean isFile;
			try {
				isFile = file.createNewFile();
				System.err.println(isFile);

			} catch (IOException e) {
				
				e.printStackTrace();
			}

		}
	}
}