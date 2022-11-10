package com.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ExcelUtil {


    public  static Map readExcel(String filePath, String company, int systmIndex, int databaseIndex, int tableIndex, int columnIndex, int isSenIndex) {
        Map<String,String> map=new<String,String>HashMap();
        try {

            File excel = new File(filePath);
            if (excel.isFile() && excel.exists()) {
                Workbook wb = null;
                if (filePath.endsWith(".xls")) {
                    wb = new HSSFWorkbook(Files.newInputStream(Paths.get(filePath)));

                } else if (filePath.endsWith(".xlsx")) {
                    wb = new XSSFWorkbook(filePath);
                }
                Sheet sheet = wb.getSheetAt(0);
                for (Row row : sheet) {
                    String table = null;
                    if (isMergedRegion(sheet, row.getRowNum(), tableIndex)) {
                        table = getMergedRegionValue(sheet, row.getRowNum(), tableIndex);

                    } else {
                        if(row.getCell(tableIndex)!=null){
                            table = row.getCell(tableIndex).toString();
                        }

                    }
                    //是否脱敏
                    String isSen = null;
                    if(row.getCell(isSenIndex)!=null){
                        isSen = row.getCell(isSenIndex).toString();
                    }
                    if (isSen != null && isSen.equals("是")) {
                        map.put(table,"");
                    }

                }


                for (Row row : sheet) {
                    //系统名称
                   // String systm = row.getCell(systmIndex).toString();
                    //数据库名称
                   // String database = row.getCell(databaseIndex).toString();
                    //表名称
                    String table = null;
                    if (isMergedRegion(sheet, row.getRowNum(), tableIndex)) {
                        table = getMergedRegionValue(sheet, row.getRowNum(), tableIndex);

                    } else {
                        if(row.getCell(tableIndex)!=null){
                            table = row.getCell(tableIndex).toString();
                        }

                    }
                    //字段名称
                    String column=null;
                    if(row.getCell(columnIndex)!=null){
                         column = row.getCell(columnIndex).toString();

                    }
                    //是否脱敏
                    String isSen = null;
                    if(row.getCell(isSenIndex)!=null){
                        isSen = row.getCell(isSenIndex).toString();
                    }
                    if (isSen != null && isSen.equals("是") &&column!=null) {
                        String str=map.get(table);
                        map.put(table,str+column+",");
                    }

                }

            } else {
                System.out.println("找不到此文件" + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return map;
        }
    }


    public static Set readExcel(String filePath , int tableIndex,String prefix) {
        Set<String>set= new HashSet<String>();
        Map<String,String> map=new<String,String>HashMap();
        try {

            File excel = new File(filePath);
            if (excel.isFile() && excel.exists()) {
                Workbook wb = null;
                if (filePath.endsWith(".xls")) {
                    wb = new HSSFWorkbook(Files.newInputStream(Paths.get(filePath)));

                } else if (filePath.endsWith(".xlsx")) {
                    wb = new XSSFWorkbook(filePath);
                }
                Sheet sheet = wb.getSheetAt(0);
                for (Row row : sheet) {
                    String table = null;
                    if (isMergedRegion(sheet, row.getRowNum(), tableIndex)) {
                        table = getMergedRegionValue(sheet, row.getRowNum(), tableIndex);

                    } else {
                        if(row.getCell(tableIndex)!=null){
                            table = row.getCell(tableIndex).toString();
                        }

                    }
                    set.add(prefix+table);

                }

            } else {
                System.out.println("找不到此文件" + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return set;
        }
    }

    //判断是否合并单元格
    private static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    //获取合并单元格的值
    public  static String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return fCell.getStringCellValue();
                }
            }
        }
        return null;
    }

}
