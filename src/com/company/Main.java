package com.company;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("add file path");
            System.exit(1);
        }
        unzip(args[0],args[1]);
    }

    //传入一个压缩文件或目录
    public static void unzip(String filePath,String outputDir) {
        //压缩文件则创建文件夹
        if (filePath.matches("[.zip]")) {
            File dir = new File(outputDir);
            // create output directory if it doesn't exist
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(filePath);
            ZipInputStream zip = new ZipInputStream(fis);
            //如果是压缩文件
            ZipEntry entry = zip.getNextEntry();
            while (entry != null) {
                String fileName = entry.getName();
                File newFile = new File(outputDir + File.separator + fileName);
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
