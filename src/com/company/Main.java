package com.company;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String zipFilePath="/home/user/hello1.zip";
        //String zipFilePath="/Users/huangxiaofeng/IdeaProjects/learn-java/practices/Java教程/10.Java快速入门.1255883772263712/10.Java简介.1255876875896416/40.使用IDE练习插件.1266092093733664/hello.zip";
        String destDir="";
        unzip(zipFilePath,destDir);
    }
    //传入已经解压好的文件夹
    //打包好的文件夹
    public static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zip = new ZipInputStream(fis);
            //如果是压缩文件
            ZipEntry entry = zip.getNextEntry();
            while (entry != null) {
                String fileName = entry.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to " + newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                System.out.println("mkdir " + newFile.getAbsolutePath());
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zip.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zip.closeEntry();
                entry = zip.getNextEntry();
            }
            //close last ZipEntry
            zip.closeEntry();
            zip.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
