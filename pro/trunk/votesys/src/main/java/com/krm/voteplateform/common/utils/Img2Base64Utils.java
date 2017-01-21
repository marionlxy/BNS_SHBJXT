package com.krm.voteplateform.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将图片转换为Base64
 * 
 * @author JohnnyZhang
 */
public abstract class Img2Base64Utils {

	private static final Logger logger = LoggerFactory.getLogger(Img2Base64Utils.class);

	/**
	 * 图像转Base64编码
	 * 
	 * @param imagePath 图像地址
	 * @return
	 */
	public static String imageToBase64(String imagePath) {
		String result = "";
		byte[] data = null;
		InputStream in = null;
		try {
			in = new FileInputStream(imagePath);
			data = new byte[in.available()];
			in.read(data);
			// 对字节数组Base64编码
			result = new String(Base64.encodeBase64(data));// 返回Base64编码过的字节数组字符串
		} catch (FileNotFoundException e) {
			logger.error("转换Base64图像时无法找到文件[路径:" + imagePath + "]", e);
		} catch (IOException e) {
			logger.error("转换Base64图像时出现IO异常[路径:" + imagePath + "]", e);
		} finally {
			IOUtils.closeQuietly(in);
		}
		return result;
	}

	/**
	 * 将Base64字符串生成文件
	 * 
	 * @param imgStr
	 * @param imgFilePath
	 * @return
	 */
	public static boolean genBase64ToImage(String imgStr, String imgFilePath) {
		boolean flag = false;
		if (imgStr == null) // 图像数据为空
			return flag;
		// 生成jpeg图片
		OutputStream out = null;
		try {
			byte[] b = Base64.decodeBase64(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			flag = true;
		} catch (FileNotFoundException e) {
			logger.error("Base64转换图像时无法找到文件[路径:" + imgFilePath + "]", e);
		} catch (IOException e) {
			logger.error("Base64转换图像时出现IO异常[路径:" + imgFilePath + "]", e);
		} finally {
			IOUtils.closeQuietly(out);
		}
		return flag;
	}
}
