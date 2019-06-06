package com.xfpay.utils.common;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CsvUtils {

    private String charset = "GBK";

    private String separator = ",";

    private String lineBreaks = "\r\n";

    private String fileSuffix =".csv";

    private File csvFile;

    private int bufferSize = 8192;

    public static CsvUtils build(String[] headers) throws IOException {
        return new CsvUtils(headers);
    }

    private CsvUtils(String[] headers) throws IOException {
        // 定义文件名格式并创建
        csvFile = File.createTempFile("temp", ".csv");
        buildHead(headers);
    }

    /**
     * 生成头部
     * @param headers
     * @throws IOException
     */
    private void buildHead(String[] headers) {
        BufferedWriter csvFileOutputStream = null;
        try {
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(csvFile), charset), bufferSize);
            for (int i = 0; i < headers.length; i++) {
                csvFileOutputStream.write(headers[i]);
                if(headers.length-i>1){
                    csvFileOutputStream.write(separator);
                }
            }
            csvFileOutputStream.write(lineBreaks);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (csvFileOutputStream != null) {
                    csvFileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void buildContent(Object t,String[] fields,BufferedWriter csvFileOutputStream) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Class clazz = t.getClass();
        for (int i = 0; i < fields.length; i++) {
            String filedName = toUpperCaseFirstOne(fields[i]);
            Method method = clazz.getMethod(filedName);
            method.setAccessible(true);
            Object obj = method.invoke(t);
            String content = String.valueOf(obj);
            if (content == null || content.equals("null"))
                content = "";

            csvFileOutputStream.write(content);

            if (fields.length - i > 1) {
                csvFileOutputStream.write(separator);
            }
        }
        csvFileOutputStream.write(lineBreaks);
    }

    /**
     * 追加内容
     *
     * @param exportData 源数据List
     * @param fields  字段列表
     */
    public void pushContent(List exportData, String[] fields) {
        BufferedWriter csvFileOutputStream = null;
        try {
            // UTF-8使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(csvFile,true), charset), bufferSize);
            // 写入文件内容
            for (int j = 0; exportData != null && !exportData.isEmpty()
                    && j < exportData.size(); j++) {
                Object t = exportData.get(j);
                buildContent(t,fields,csvFileOutputStream);
            }
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }finally {
            try {
                if (csvFileOutputStream != null) {
                    csvFileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File flush() throws IOException {
        return this.csvFile;
    }

    /**
     * 删除临时文件
     *
     */
    public void deleteFile() {
        csvFile.delete();
    }

    /**
     * 将第一个字母转换为大写字母并和get拼合成方法
     *
     * @param origin
     * @return
     */
    private String toUpperCaseFirstOne(String origin) {
        StringBuilder sb = new StringBuilder(origin);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, "get");
        return sb.toString();
    }
}
