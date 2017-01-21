package com.krm.voteplateform.common.utils;

import java.io.File;

import com.krm.crypto.cryptohandle;
import com.krm.crypto.utils.fileutil;

/**
 * 数据源配置文件加密方法
 * @author Parker
 *
 */
public class EncryptUtil
{
	public static void main(String[] args) throws Exception
	{
		cryptohandle p = new cryptohandle("sm2");
		String pubk = "04F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE205EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A";

//		String path=Thread.currentThread().getContextClassLoader().getResource("").toString();  
//        path=path.replace("/", File.separatorChar+"")
//        		.replace("file:"+File.separatorChar, "")
//        		.replace("classes"+File.separatorChar, "")
//        		.replace("%20", " ")					//配置文件所在的路径中有空格需要替换
//        		.replace("build"+File.separatorChar, ""); 
//        path+="resources"+File.separatorChar;
        String path = "D:\\Projects\\audit\\resources\\";
        System.out.println(path);  
		byte[] a = fileutil.filetoByte(path+"resources1.properties");
		byte[] b = p.encrypt(pubk, a);
		fileutil.bytetoFile(path+"temp.properties", b);
		System.out.println("文件加密完毕。");
		fileutil.deleteFile(path+"resources.properties");
		File file = new File(path+"temp.properties");
		file.renameTo(new File(path+"resources.properties"));
	}
}
