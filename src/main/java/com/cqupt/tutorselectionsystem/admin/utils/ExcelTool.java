package com.cqupt.tutorselectionsystem.admin.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouzhongzhong on 2022/3/17
 */
public class ExcelTool {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void exportExcelFile(List<Map<String, String>> mapList, String filePath) {
        //List的大小代表Excel中写入的行数，Map的大小代表Excel中的列数
        OutputStream out = null;//先定义输出流
        try {
            //获取总列数
            int columnCount = mapList.get(0).size();
            //获取总行数
            int rowCount = mapList.size();

            //读取Excel文件
            File excelFile = new File(filePath);
            Workbook workbook = getWorkbok(excelFile);//得到excel文件对象
            //得到第一个工作簿
            Sheet sheet = workbook.getSheetAt(0);
            /**
             * 把文件中原来的数据删掉，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();//得到工作簿中的最后一行行数
            for (int i = 1; i <= rowNumber; i++) {//从第二行开始删除
                Row row = sheet.getRow(i);
                sheet.removeRow(row);//删除此行
            }
            //创建文件输出流，输出Excel表格
            out = new FileOutputStream(filePath);
            workbook.write(out);
            /**
             * 往Excel中写入数据
             */
            //创建第一行
            Row row1 = sheet.createRow(0);
            for (int j = 0; j < mapList.size(); j++) {//遍历行
                //创建一行，作为属性名，在第二行开始写入数据
                Row row = sheet.createRow(j + 1);
                //得到插入数据行
                Map<String, String> dataMap = mapList.get(j);
                //遍历列，在一行内循环
                int lieShu = 0;
                for (String key : dataMap.keySet()) {
                    if (j == 0) {
                        //把属性写在第一列
                        Cell cell1 = row1.createCell(lieShu);
                        cell1.setCellValue(key);
                    }
                    String value = dataMap.get(key);//获取此列key对应的value
                    Cell cell = row.createCell(lieShu);
                    cell.setCellValue(value);
                    lieShu++;
                }
            }
            //创建文件输出流，输出Excel表格
            out = new FileOutputStream(filePath);
            workbook.write(out);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    /**
     * 判断Excel的版本,获取Workbook
     */
    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * 把List<Map<String, Object>>转换成List<Map<String, String>>类型
     */
    public static List<Map<String, String>> mapTransform(List<Map<String, Object>> mapList1) {
        List<Map<String, String>> mapList2 = new ArrayList<>();
        for (int i = 0; i < mapList1.size(); i++) {
            Map<String, Object> map1 = mapList1.get(i);
            Map<String, String> map2 = new HashMap<>();
            for (String key : map1.keySet()) {
                String value = map1.get(key) == null ? null : map1.get(key).toString();
                map2.put(key, value);
            }
            mapList2.add(map2);
        }
        return mapList2;
    }


    public static List<Map<String, Object>> importExcelFile(File file) throws Exception {
        //创建输入流，读取Excel
        InputStream inputStream = new FileInputStream(file.getAbsoluteFile());
        //得到Excel对象
        Workbook workbook = WorkbookFactory.create(inputStream);
        //定义两个list保存表格中的内容
        List<Map<String, Object>> outerList = new ArrayList<>();
        List<String> head = new ArrayList<>();
        Map<String, Object> innerMap = new HashMap<>();
        //得到工作簿
        Sheet sheet = workbook.getSheetAt(0);
        //得到行数
        int rowCount = sheet.getLastRowNum() + 1;
        //遍历行，从第二行开始遍历
        for (int i = 0; i < rowCount; i++) {
            innerMap = new HashMap<>();//清零
            Row row = sheet.getRow(i);//得到对应的行
            int colCount = row.getLastCellNum();//列数
            for (int j = 0; j < colCount; j++) {//遍历列
                Cell cell = row.getCell(j);//得到格子里的内容
                if (i == 0) {//得到列名
                    head.add(cell.getStringCellValue());
                } else {//得到除了第一行外的其他行
                    if (cell.getCellType() == CellType.NUMERIC) {//如果是数字类型
                        innerMap.put(head.get(j), cell.getNumericCellValue());
                    } else {//如果不是数字，统统转成字符
                        innerMap.put(head.get(j), cell.getStringCellValue());
                    }
                }

            }
            if (i != 0) {
                outerList.add(innerMap);
            }
        }

        return outerList;
    }
}

