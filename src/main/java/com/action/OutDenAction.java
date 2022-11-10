package com.action;

import com.util.ExcelUtil;
import com.util.HiveUtil;

import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;

public class OutDenAction {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        String filePath = "src/main/resources/附件6-SaaS系统数据字典.xlsx";
        String company="";
        int systmIndex = 1;
        int databaseIndex = 3;
        int tableIndex = 5;
        int columnIndex = 7;
        int isSenIndex = 10;
        String database="sdi_hljy_we_saas";
        String prefix="sdi_";

        //obs數據複製



        //讀取excel在開發庫中創建表結構
        ExcelUtil excelUtil=new ExcelUtil();
        HiveUtil hiveUtil=new HiveUtil();

//        Set<String>set=excelUtil.readExcel(filePath,tableIndex,prefix);
//        for(Iterator it2 = set.iterator();it2.hasNext();){
//            try{
//
//               hiveUtil.createTablebyTableName( hiveUtil.getDDLByTableName(database ,it2.next().toString()));
//                System.out.println(it2.next().toString()+"----創建成功");
//
//            }catch (Exception e){
//                e.printStackTrace();
//                System.out.println(it2.next().toString()+"-----------------error");
//                continue;
//            }
//        }

        //脫敏
        Map<String,String>map=  excelUtil.readExcel(filePath,company,systmIndex,databaseIndex,tableIndex,columnIndex,isSenIndex);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            // System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            StringBuffer sb=new StringBuffer();
            try{
                sb.append("insert overwrite table "+database+"."+ prefix+entry.getKey()+"(");
                //拆分脱敏字段
                List<String>desenList= Arrays.asList(entry.getValue().toUpperCase().split(","));
                List<String>colList= hiveUtil.getFieldByTableName(database+".sdi_"+entry.getKey());
                System.out.println(colList);
                List<String>newList= new ArrayList<>();
                for(String col :colList){
                    if(desenList.contains(col)){
                        newList.add("null");

                    }else{
                        newList.add(col);

                    }
                }
                sb.append("select ");
                sb.append(newList.toString().replace("[","").replace("]",""));
                sb.append(" from "+database+"."+prefix+entry.getKey()+")");

                System.out.println(sb);
                //执行sql
                hiveUtil.updateTablebyTableName(sb.toString());
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }

        }










    }
}
