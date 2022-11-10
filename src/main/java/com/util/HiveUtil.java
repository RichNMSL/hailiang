package com.util;

import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class HiveUtil {

    //获取表字段
    public static List<String> getFieldByTableName(String dbTableName) throws SQLException, ClassNotFoundException {
        Connection conn = JdbcUtilDev.getConn();
        Statement stat = JdbcUtilDev.getStmt(conn);
        List<String> fields = new ArrayList<>();
        ResultSet res = stat.executeQuery("desc " + dbTableName);
        while (res.next()) {
            String col_name = res.getString("col_name");
            if (StringUtils.isNotBlank(col_name) && !col_name.contains("#")) {
                fields.add(col_name.toUpperCase());
            }
        }
        JdbcUtilDev.closeFunc(conn,stat);
        return fields;
    }
    //执行sql
    public static  void  updateTablebyTableName(String sql) throws SQLException, ClassNotFoundException {
        Connection conn = JdbcUtilDev.getConn();
        Statement stat = JdbcUtilDev.getStmt(conn);
        stat.executeUpdate(sql);
        JdbcUtilDev.closeFunc(conn,stat);
    }

    //查询数据库
    public static  void  queryTablebyTableName(String sql) throws SQLException, ClassNotFoundException {
        Connection conn = JdbcUtilDev.getConn();
        Statement stat = JdbcUtilDev.getStmt(conn);
        stat.executeQuery(sql);
        JdbcUtilDev.closeFunc(conn,stat);
    }

    //生成ddl
    public static String  getDDLByTableName(String database,String dbTableName) throws SQLException, ClassNotFoundException {
        Connection conn = JdbcUtilPrd.getConn();
        Statement stat = JdbcUtilPrd.getStmt(conn);
        List<String> fields = new ArrayList<>();
        ResultSet res = stat.executeQuery("desc " + database+"."+dbTableName);
        StringBuffer sb=new StringBuffer();
        StringBuffer column=new StringBuffer();

        sb.append("CREATE TABLE "+database+"."+dbTableName +"(\n");

        while (res.next()) {
            String col_name = res.getString("col_name");
            String data_type = res.getString("data_type");
            if (StringUtils.isNotBlank(col_name) && !col_name.contains("#")) {
                column.append( col_name+" "+data_type+",\n");

            }
        }
        sb.append(column.substring(0,column.lastIndexOf(",")));
        sb.append(")\n");
        sb.append("ROW FORMAT SERDE\n");
        sb.append("'org.apache.hadoop.hive.ql.io.orc.OrcSerde'\n");
        sb.append("STORED AS INPUTFORMAT\n");
        sb.append("  'org.apache.hadoop.hive.ql.io.orc.OrcInputFormat'\n");
        sb.append(" OUTPUTFORMAT\n");
        sb.append("   'org.apache.hadoop.hive.ql.io.orc.OrcOutputFormat'\n");
        sb.append(" LOCATION\n");
        sb.append("    'obs://obs-datalake-dev/"+database+".db/"+dbTableName+"'\n");
        JdbcUtilPrd.closeFunc(conn,stat);
        return sb.toString();
    }

    //執行ddl
    public static  void  createTablebyTableName(String sql) throws SQLException, ClassNotFoundException {
        Connection conn = JdbcUtilDev.getConn();
        Statement stat = JdbcUtilDev.getStmt(conn);
        stat.execute(sql);
        JdbcUtilDev.closeFunc(conn,stat);
    }

}

