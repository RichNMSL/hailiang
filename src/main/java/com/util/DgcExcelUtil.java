package com.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DgcExcelUtil {


    public Map readExcel(String filePath) {
        Map<String, String> map = new <String, String>HashMap();
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

                    String bz = row.getCell(9).toString();
                    map.put(bz, "");

                }

                for (Row row : sheet) {
                    String bz = row.getCell(9).toString();
                    String bm = row.getCell(19).toString();
                    String str = map.get(bz);
                    map.put(bz, str + "," + bm);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return map;
        }
    }


    public String readExcel3(String filePath, String bz_a) {
        Map<String, String> map = new <String, String>HashMap();
        List list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        try {

            File excel = new File(filePath);

            if (excel.isFile() && excel.exists()) {
                Workbook wb = null;
                if (filePath.endsWith(".xls")) {
                    wb = new HSSFWorkbook(Files.newInputStream(Paths.get(filePath)));

                } else if (filePath.endsWith(".xlsx")) {
                    wb = new XSSFWorkbook(filePath);
                }
                Sheet sheet = wb.getSheetAt(1);

                for (Row row : sheet) {
                    String bz_b = row.getCell(8).toString();
                    if (bz_b != null && bz_b.equals(bz_a)) {
                        String database = row.getCell(3).toString();
                        String table = row.getCell(1).toString();
                        String column = row.getCell(4).toString();
                        sb.append(database + "." + table + "." + column + ":5,");

                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return sb.toString();
        }
    }

    public String readExcel2(String filePath, String bz_a) {
        Map<String, String> map = new <String, String>HashMap();
        List list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        try {

            File excel = new File(filePath);

            if (excel.isFile() && excel.exists()) {
                Workbook wb = null;
                if (filePath.endsWith(".xls")) {
                    wb = new HSSFWorkbook(Files.newInputStream(Paths.get(filePath)));

                } else if (filePath.endsWith(".xlsx")) {
                    wb = new XSSFWorkbook(filePath);
                }
                Sheet sheet = wb.getSheetAt(1);

                for (Row row : sheet) {
                    String bz_b = row.getCell(8).toString();
                    if (bz_b != null && bz_b.equals(bz_a)) {
                        String database = row.getCell(3).toString();
                        String table = row.getCell(1).toString();
                        String column = row.getCell(4).toString();
                        sb.append(database + "." + table + ",");

                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return sb.toString();
        }
    }


    public void writeExcel(String filePath, String bz_a,String databasese, String b2,String b3) {
        try {

            File excel = new File(filePath);
            FileInputStream fis = new FileInputStream(filePath);


            if (excel.isFile() && excel.exists()) {
                Workbook wb = null;
                if (filePath.endsWith(".xls")) {
                    wb = new HSSFWorkbook(Files.newInputStream(Paths.get(filePath)));

                } else if (filePath.endsWith(".xlsx")) {
                    wb = new XSSFWorkbook(fis);
                }

                Sheet sheet1 = wb.getSheetAt(0);

                Sheet sheet2 = wb.getSheetAt(1);
                //规则名称
                sheet1.getRow(1).getCell(1).setCellValue(bz_a);

                //规则名称
                sheet2.getRow(1).getCell(1).setCellValue(bz_a);
                //数据库
                sheet2.getRow(1).getCell(7).setCellValue(databasese);
                //表
                sheet2.getRow(1).getCell(10).setCellValue(b2);
                //字段
                sheet2.getRow(1).getCell(12).setCellValue(b3);

                FileOutputStream fos = new FileOutputStream(filePath);
                wb.write(fos);
                fis.close();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String readExcelDatabase(String filePath, String bz_a) {
        Set set = new HashSet();
        StringBuffer sb = new StringBuffer();
        try {

            File excel = new File(filePath);

            if (excel.isFile() && excel.exists()) {
                Workbook wb = null;
                if (filePath.endsWith(".xls")) {
                    wb = new HSSFWorkbook(Files.newInputStream(Paths.get(filePath)));

                } else if (filePath.endsWith(".xlsx")) {
                    wb = new XSSFWorkbook(filePath);
                }
                Sheet sheet = wb.getSheetAt(1);

                for (Row row : sheet) {
                    String bz_b = row.getCell(8).toString();
                    if (bz_b != null && bz_b.equals(bz_a)) {
                        String database = row.getCell(3).toString();
                        set.add(database);
                    }

                }
                Iterator it1 = set.iterator();
                while(it1.hasNext()){
                    sb.append(it1.next()+",");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return sb.toString();
        }
    }

}
