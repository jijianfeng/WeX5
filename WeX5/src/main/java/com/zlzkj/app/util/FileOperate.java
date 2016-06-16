package com.zlzkj.app.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileOperate {

	/**
	 * 按文件名读取txt文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readTxtFileByName(String fileName) {

		FileReader fr = null;
		BufferedReader br = null;

		String path = System.getProperty("user.dir");
		String filePath = path + "\\..\\invoice_file\\" + fileName;

		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);

			StringBuffer sb = new StringBuffer();
			while (br.readLine() != null) {
				sb.append(br.readLine());
			}

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close(); // 关闭文件
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 按文件名、内容写入txt文件
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void writeTxtFile(String fileName, StringBuffer content) {

		File file = null;
		FileWriter fw = null;

		String path = System.getProperty("user.dir");
		String filePath = path + "\\..\\invoice_file\\" + fileName;
		try {
			file = new File(filePath);
			fw = new FileWriter(file);
			fw.append(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null)
					fw.close(); // 关闭文件
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 按文件名、内容写入txt文件
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void zipTxtFile(String txtFileName, String zipFileName, boolean isFinishDelete) {

		String path = System.getProperty("user.dir");
		String txtfilePath = path + "\\..\\invoice_file\\" + txtFileName;
		String zipfilePath = path + "\\..\\invoice_file\\" + zipFileName;
		
		byte[] buf = new byte[1024];
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfilePath));

			FileInputStream in = new FileInputStream(txtfilePath);
			out.putNextEntry(new ZipEntry(txtFileName));
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.closeEntry();
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (isFinishDelete)
		{
			File file = new File(txtfilePath);
			file.delete();
		}
	}
	public static boolean deleteFile(String filePath){
		//String filePath="E:\\tomcat\\webapps\\"+workname+"\\"+ss;
        //System.out.println(filePath);
		File file = new File(filePath);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            file.delete();
            return true;
        }
	}
}
