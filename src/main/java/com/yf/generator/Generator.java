package com.yf.generator;

import com.yf.generator.domain.DataColumn;
import com.yf.generator.domain.DataRow;
import com.yf.generator.domain.DataTable;
import com.yf.generator.tools.DbHepler;
import com.yf.generator.tools.VelocityHelper;
import com.yf.generator.utils.Contants;

import java.util.List;

public class Generator {
    private static DbHepler helper = new DbHepler();

    /**
     * @param @param args   
     * @return void
     * @throws
     * @Title: main
     * @Description: 生成基础项目源码文件执行函数
     */
    public static void main(String[] args) {
        //查询数据库中所有表名称
        /*String sql_tableNames = "select name as '表名' from sys.tables";*/
        //for mysql
        String sql_tableNames = "select table_name from information_schema.tables where table_schema='" + Contants.GENERATE_DATABASENAME + "'";
        DataTable db_tableNames = helper.GetDataTable(sql_tableNames, null);
        int l_tableNames = db_tableNames.RowCount;
        System.out.println("在数据库中发现 " + l_tableNames + " 张表……");
        db_tableNames.PrintTable(db_tableNames);
        //表名列表
        List<DataRow> r_tableNames = db_tableNames.GetRow();
        for (DataRow r_tableName : r_tableNames) {
            for (DataColumn c_tableName : r_tableName.GetColumn()) {
                //表名
                String tableName = c_tableName.GetValue().toString();
                System.out.println("表名：" + tableName);
                //根据表名查询指定表的表结构信息
                //for sqlserver
                /*String sql_tableColumns = " SELECT a.name AS ColumnName, t.name AS Type, a.length AS Length, a.isnullable AS IsNullAble, cast(g.[value] as varchar(500)) AS Description "+
                                          " FROM "+
            							  " syscolumns a "+
            							  " LEFT JOIN systypes b ON a.xtype = b.xusertype "+
            							  " INNER JOIN sysobjects d ON a.id = d.id "+
            							  " AND d.xtype = 'U' "+
            							  " AND d.name <> 'dtproperties' "+
            							  " INNER JOIN systypes t ON a.xtype = t.xtype "+
            							  " LEFT JOIN sys.extended_properties g ON a.id = g.major_id "+
            							  " AND a.colid = g.minor_id "+
            							  " WHERE "+
            							  " d.[name] = '"+tableName+"' "+
            							  " ORDER BY a.id, a.colorder";*/
                //for mysql
                String sql_tableColumns = "select * from (select COLUMN_NAME as ColumnName," +
                        " DATA_TYPE as Type,IS_NULLABLE as IsNullAble, " +
                        " CHARACTER_MAXIMUM_LENGTH as Length,COLUMN_COMMENT as Description ," +
                        " COLUMN_KEY as ColumnKey ," +
                        " table_schema as DbName ," +
                        " table_name as TableName " +
                        "from information_schema.columns  where table_schema = '" + Contants.GENERATE_DATABASENAME + "' and table_name = '" + tableName +
                        "')k";

                DataTable db_tableColumns = helper.GetDataTable(sql_tableColumns, null);
                System.out.println(sql_tableColumns);
                int l_tableColumns = db_tableColumns.RowCount;
                System.out.println("------------------------------------------");
                System.out.println("");
                System.out.println("在表名 " + tableName + " 中发现 " + l_tableColumns + " 个字段……");
                db_tableColumns.PrintTable(db_tableColumns);

                try {

                    //生成实体类
                    new VelocityHelper(tableName, db_tableColumns)
                            .GenerateEntity()
                            .GenerateJpa()
                            .GenerateService()
                            .GenerateController();


                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
