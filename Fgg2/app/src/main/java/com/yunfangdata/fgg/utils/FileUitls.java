package com.yunfangdata.fgg.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * 
 * 文件管理类
 * 
 * 2015年11月25日 10:28:18 
 *
 */
public class FileUitls {

	public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
	public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
	public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
	public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

	//	/**
	//	 * 删除文件或者文件夹
	//	 * @param filePath  路径
	//	 */
	//	public static void deleteFile(String filePath) {
	//		File file = new File(filePath);
	//		if (file.exists()) { // 判断文件是否存在
	//			if (file.isFile()) { // 判断是否是文件
	//				file.delete(); // delete()方法 你应该知道 是删除的意思;
	//			} else if (file.isDirectory()) { // 否则如果它是一个目录
	//				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
	//				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
	//					FileUitls.deleteFile(files[i].getPath()); // 把每个文件 用这个方法进行迭代
	//				}
	//			}
	//			file.delete();
	//		} else {
	//			Log.e("获取文件大小", "文件不存在！!");
	//		}
	//	}

	/**
	 * 删除单个文件
	 * @param   filePath    被删除文件的文件名
	 * @return 文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 删除文件夹以及目录下的文件
	 * @param   filePath 被删除目录的文件路径
	 * @return  目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String filePath) {
		boolean flag = false;
		//如果filePath不以文件分隔符结尾，自动添加文件分隔符
		if (!filePath.endsWith(File.separator)) {
			filePath = filePath + File.separator;
		}
		File dirFile = new File(filePath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		File[] files = dirFile.listFiles();
		//遍历删除文件夹下的所有文件(包括子目录)
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				//删除子文件
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {
				//删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		//删除当前空目录
		return dirFile.delete();
	}

	/**
	 *  根据路径删除指定的目录或文件，无论存在与否
	 *@param filePath  要删除的目录或文件
	 *@return 删除成功返回 true，否则返回 false。
	 */
	public boolean DeleteFolder(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile()) {
				// 为文件时调用删除文件方法
				return deleteFile(filePath);
			} else {
				// 为目录时调用删除目录方法
				return deleteDirectory(filePath);
			}
		}
	}

	/**
	 * 获取文件指定文件的指定单位的大小
	 * 
	 * @param filePath
	 *            文件路径
	 * @param sizeType
	 *            获取大小的类型1为B、2为KB、3为MB、4为GB
	 * @return double值的大小
	 */
	public static double getFileOrFilesSize(String filePath, int sizeType) {
		File file = new File(filePath);
		long blockSize = 0;
		try {
			if (file.isDirectory()) {
				blockSize = getFileSizes(file);
			} else {
				blockSize = getFileSize(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("获取文件大小", "获取失败!");
		}
		return FormetFileSize(blockSize, sizeType);
	}

	/**
	 * 调用此方法自动计算指定文件或指定文件夹的大小
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 计算好的带B、KB、MB、GB的字符串
	 */
	public static String getAutoFileOrFilesSize(String filePath) {
		if (filePath != null && !filePath.isEmpty()) {

			File file = new File(filePath);
			long blockSize = 0;
			try {
				if (file.isDirectory()) {
					blockSize = getFileSizes(file);
				} else {
					blockSize = getFileSize(file);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("获取文件大小", "获取失败!");
			}
			return FormetFileSize(blockSize);

		} else {
			return null;
		}
	}

	/**
	 * 获取指定文件大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	private static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
			Log.e("获取文件大小", "文件不存在!");
		}
		return size;
	}

	/**
	 * 获取指定文件夹
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	private static long getFileSizes(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSizes(flist[i]);
			} else {
				size = size + getFileSize(flist[i]);
			}
		}
		return size;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return
	 */
	private static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		String wrongSize = "0B";
		if (fileS == 0) {
			return wrongSize;
		}
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}

	/**
	 * 转换文件大小,指定转换的类型
	 * 
	 * @param fileS
	 * @param sizeType
	 * @return
	 */
	private static double FormetFileSize(long fileS, int sizeType) {
		DecimalFormat df = new DecimalFormat("#.00");
		double fileSizeLong = 0;
		switch (sizeType) {
		case SIZETYPE_B:
			fileSizeLong = Double.valueOf(df.format((double) fileS));
			break;
		case SIZETYPE_KB:
			fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
			break;
		case SIZETYPE_MB:
			fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
			break;
		case SIZETYPE_GB:
			fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
			break;
		default:
			break;
		}
		return fileSizeLong;
	}
}
