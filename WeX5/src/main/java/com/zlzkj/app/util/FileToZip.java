package com.zlzkj.app.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
public class FileToZip {
    public static void zipFiles(File[] srcfile,File zipfile){
        byte[] buf=new byte[1024];
        try {
            //ZipOutputStream�ࣺ����ļ����ļ��е�ѹ��
            ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipfile));
            for(int i=0;i<srcfile.length;i++){
                FileInputStream in=new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while((len=in.read(buf))>0){
                    out.write(buf,0,len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            System.out.println("ѹ�����.");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * ����:��ѹ��
     * <p>���� ���Ǳ� Jul 16, 2010 12:42:20 PM
     * @param zipfile����Ҫ��ѹ�����ļ�
     * @param descDir����ѹ���Ŀ��Ŀ¼
     */
    public static void unZipFiles(File zipfile,String descDir){
        try {
            ZipFile zf=new ZipFile(zipfile);
            for(Enumeration entries=zf.entries();entries.hasMoreElements();){
                ZipEntry entry=(ZipEntry) entries.nextElement();
                String zipEntryName=entry.getName();
                InputStream in=zf.getInputStream(entry);
                OutputStream out=new FileOutputStream(descDir+zipEntryName);
                byte[] buf1=new byte[1024];
                int len;
                while((len=in.read(buf1))>0){
                    out.write(buf1,0,len);
                }
                in.close();
                out.close();
                System.out.println("��ѹ�����.");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**����:
     * <p>���� ���Ǳ� Jul 16, 2010 10:58:15 AM
     * @param args
     */
    public static void main(String[] args) {
        //2��Դ�ļ�
        File f1=new File("E:\\����.xls");
        File f2=new File("E:\\jjf20150726102005.xls");
        File[] srcfile={f1,f2};
        //ѹ������ļ�
        File zipfile=new File("E:\\export.zip");
        FileToZip.zipFiles(srcfile, zipfile);
        //��Ҫ��ѹ�����ļ�
        File file=new File("E:\\export.zip");
        //��ѹ���Ŀ��Ŀ¼
        //String dir="E:\\export\\";
        //FileToZip.unZipFiles(file, dir);
    }

}
