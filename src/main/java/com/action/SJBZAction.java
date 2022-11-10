package com.action;


        import com.util.DgcExcelUtil;

        import java.util.*;

public class SJBZAction {

    //

    public static void main(String[] args) {
        String filePath = "src/main/resources/bbbb.xlsx";
        String filePathA = "src/main/resources/cccc.xlsx";
        String database=null;
        String sb2=null;
        String sb3=null;

                DgcExcelUtil excel = new DgcExcelUtil();
        Map map = excel.readExcel(filePath);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String GUIZEMINGCHENG="有效性_数据标准约束_50英文数字";

            if(entry.getKey().equals(GUIZEMINGCHENG)){
                List list= Arrays.asList(entry.getValue().split(","));
                System.out.println(list);
                //写入当儿
//                for(int i=1;i<list.size();i++){
//                     database= excel.readExcelDatabase(filePath,list.get(i).toString());
//                     sb2=  excel.readExcel2(filePath,list.get(i).toString());
//                     sb3=  excel.readExcel3(filePath,list.get(i).toString());
//                    database= database.substring(0,database.lastIndexOf(","));
//                    sb2=  sb2.substring(0,sb2.lastIndexOf(","));
//                    sb3= sb3.substring(0,sb3.lastIndexOf(",")) ;
//                    System.out.println(list.get(i));
//                    System.out.println(sb2);
//                    System.out.println(sb3);
//
//                }

                //多个
                for(int i=1;i<list.size();i++){
                    database= database+excel.readExcelDatabase(filePath,list.get(i).toString());
                    sb2= sb2+ excel.readExcel2(filePath,list.get(i).toString());
                    sb3= sb3+ excel.readExcel3(filePath,list.get(i).toString());
                    System.out.println(list.get(i));


                }
//                for(int i=10;i<list.size()&&i<20;i++) {
//                    database = database + excel.readExcelDatabase(filePath, list.get(i).toString());
//                    sb2 = sb2 + excel.readExcel2(filePath, list.get(i).toString());
//                    sb3 = sb3 + excel.readExcel3(filePath, list.get(i).toString());
//                    System.out.println(list.get(i));
//                }
//
//
//                   for(int i=20;i<list.size()&&i<30;i++){
//                    database= database+excel.readExcelDatabase(filePath,list.get(i).toString());
//                    sb2= sb2+ excel.readExcel2(filePath,list.get(i).toString());
//                    sb3= sb3+ excel.readExcel3(filePath,list.get(i).toString());
//                    System.out.println(list.get(i));


                //database= database.substring(0,database.lastIndexOf(","));
                database="dwi_hr,dwi_hr_secret";
                sb2=  sb2.substring(0,sb2.lastIndexOf(",")).substring(4,sb2.length()-1);
                sb3= sb3.substring(0,sb3.lastIndexOf(",")).substring(4,sb3.length()-1);;
                System.out.println(sb2);
                System.out.println(sb3);

               excel.writeExcel(  filePathA,GUIZEMINGCHENG,database,sb2,sb3);

            }
        }
    }

}
