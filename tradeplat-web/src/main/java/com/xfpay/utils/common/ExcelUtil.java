/******************************************************************************
 * Copyright (C) 2017 ShenZhen RuiFu Technology Co., Ltd.
 * All Rights Reserved.
 * 本软件为深圳睿付科技有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件代码.
 *****************************************************************************/

package com.xfpay.utils.common;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WCF 文件导出excel
 */
public class ExcelUtil {

    /**
     * 每次缓存条数到内存，其余写到磁盘
     */
    private static final int bufferLineSize = 200; //缓存条数无需过高默认100

    /**
     * 每个sheet里的数据行数
     */
    private static final int sheetPageSize = 300;

    /**
     * sheet名字  sheet+i的形式
     */
    private static final String sheetName = "sheet";

    
    private static final String EXCEL_XLS = "xls";    
    private static final String EXCEL_XLSX = "xlsx";    

    /**
     * @param header
     * @param listdata
     * @param format   support xls and xlsx
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static File excelExport(String[] header, List<List<Object>> listdata, String format) throws IOException, InvalidFormatException {
        String fileSuffix = ".xlsx"; //defalut xlsx
        if (!StringUtils.isEmpty(format)) {
            if (format.equals("xls")) fileSuffix = ".xls";
        }
        File tmpFile = File.createTempFile("excel_tmp", fileSuffix);
        FileOutputStream out = new FileOutputStream(tmpFile);
        Workbook workbook;
        if (!StringUtils.isEmpty(format) && format.equals("xlsx")) {
            workbook = new SXSSFWorkbook(bufferLineSize);// 每次缓存1000条到内存，其余写到磁盘。 for excel 2007
        } else {
            workbook = new HSSFWorkbook(); //for excel 2003
        }
        int totalLineSize = listdata.size(); //数据总条数

        int sheetTotal = (totalLineSize / sheetPageSize) + ((totalLineSize % sheetPageSize == 0) ? 0 : 1); //创建sheet总数
        // 创建单元格样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        Font font1 = workbook.createFont();
        font1.setFontName("仿宋_GB2312");
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        cellStyle.setFont(font1);

        for (int i = 0; i < sheetTotal; i++) {
            Sheet sheet = workbook.createSheet(sheetName + (i + 1)); //first sheet is sheet 1
            int length;
            if ((i + 1) * sheetPageSize > totalLineSize) {
                length = totalLineSize % sheetPageSize;
            } else {
                length = sheetPageSize;
            }
            for (int k = 0; k < length + 1; k++) {
                // 创建行
                Row row = sheet.createRow(k);
                if (k == 0) {// 首行头数据
                    for (int n = 0; n < header.length; n++) {// 首行头数据
                        sheet.setColumnWidth(n, 15 * 256);
                        Cell cell = row.createCell(n);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(header[n]);
                    }
                } else {//listdata数据
                    List<Object> linedata = listdata.get((i * sheetPageSize) + k - 1);
                    for (int n = 0; n < linedata.size(); n++) {
                        row.createCell(n).setCellValue(linedata.get(n) + "");
                    }
                }
                //write to disk
                if (k % bufferLineSize == 0) ((SXSSFSheet) sheet).flushRows();  //如果到达缓存条数将内存数据添加到disk
            }


        }
        workbook.write(out);
        workbook.close();
        out.flush();
        out.close();
        return tmpFile;
    }

    
    /**
     * 读取表格内容，并注入list集合。
     * @author : fbq
     * @param: file:文件对象
     * 			objName：从左至右的对象名称。即使内容为空也需要对象名称。与表格的目录对应：如从左至右的目录名称为：创建时间，更新时间，那么list内容可以为：createTime,updateTime...
     * @return:如果Sheet的数量为0，会返回null
     */
    public static List<Map<String, Object>>  excelRead(File file,List<String> objName){
    	List<Map<String, Object>> list=new ArrayList<>();
        try {    
            // 同时支持Excel 2003、2007    
            File excelFile = file; // 创建文件对象    
            FileInputStream in = new FileInputStream(excelFile); // 文件流    
            checkExcelVaild(excelFile);    
            Workbook workbook = getWorkbok(in,excelFile);   
            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
            if (sheetCount==0) {
				return null;
			}
            Sheet sheet = workbook.getSheetAt(0);   // 遍历第三个Sheet    
            // 为跳过第一行目录设置count    
            int count = 0;  
            for (Row row : sheet) {//遍历行数
                try {  
                    // 跳过第一行的目录    
                    if(count < 1 ) {  
                        count++;    
                        continue;    
                    }  
                    //如果当前行没有数据，跳出循环    
                    if(row.getCell(0)==null||row.getCell(0).toString().equals("")){    
                    	 continue;
                    }  
                    int end = row.getLastCellNum();
                    if (end!=objName.size()) throw new Exception("对象名长度和表格目录数不一致，请核对表格数与对象名！");
                    Map<String,Object> map=new HashMap<>();
                    for (int i = 0; i < end; i++) { 
                        Cell cell = row.getCell(i);  
                        if(cell == null) { 
                        	map.put(objName.get(i), null);
//                            System.out.print("null" + "\t");  
                            continue;  
                        }  
                        Object obj = getValue(cell);  
                        map.put(objName.get(i), obj.toString());
//                        System.out.print(obj.toString() + "\t");  
                    }  
                    list.add(map);
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }

            //关流
            workbook.close();
        } catch (Exception e) {    
            e.printStackTrace();    
        }
		return list;  
    
    	
    }
    //初始化Excel工具
    public void initExcelUtil(){

    }

    //初始化表头样式
    public static void initHeaderStyle(){

    }

    
    /**  
     * 判断Excel的版本,获取Workbook  
     * @param in  
     * @param filename  
     * @return  
     * @throws IOException  
     */    
    public static Workbook getWorkbok(InputStream in,File file) throws IOException{    
        Workbook wb = null;    
        if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003    
            wb = new HSSFWorkbook(in);    
        }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010    
            wb = new XSSFWorkbook(in);    
        }    
        return wb;    
    } 
    
    /**  
     * 判断文件是否是excel  
     * @throws Exception   
     */    
    public static void checkExcelVaild(File file) throws Exception{    
        if(!file.exists()){    
            throw new Exception("文件不存在");    
        }    
        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){    
            throw new Exception("文件不是Excel");    
        }    
    }  
   
    
    private static Object getValue(Cell cell) {  
        Object obj = null;  
        switch (cell.getCellTypeEnum()) {  
            case BOOLEAN:  
                obj = cell.getBooleanCellValue();   
                break;  
            case ERROR:  
                obj = cell.getErrorCellValue();   
                break;  
            case NUMERIC:  
                obj = cell.getNumericCellValue();   
                break;  
            case STRING:  
                obj = cell.getStringCellValue();   
                break;  
            default:  
                break;  
        }  
        return obj;  
    }  
}
