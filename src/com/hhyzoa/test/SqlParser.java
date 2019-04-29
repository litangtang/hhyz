package com.hhyzoa.test;

import com.hhyzoa.util.StringUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Bill on 2018/5/22.
 */
public class SqlParser {

    private static final String INSERT_TRADE_PREFIX = "INSERT INTO hhyz.trade (id,client_id,`date`,abst,packages,amount,price,carriage,payment,is_loan,balance,verify,remark,flag,`level`) VALUES";
    private static final String PRODUCT_SELL_INSERT_PREFIX = "INSERT INTO hhyz.t_product_sell (id, client_name, busi_type, packages, count, price, amount, `day`, `month`, `year`, packages_accu, count_accu, amount_accu, remark) values";
    private static final String PRODUCT_ARRANGE_INSERT_PREFIX = "INSERT INTO hhyz.t_product_arrange (id,`year`,`month`,`day`,client_name,packages,count,price,amount,arrive_date,is_send,remark) VALUES";
    private static String source = "/Users/Bill/Desktop/hhyz/localhost/";
    private static String target = "/Users/Bill/Desktop/hhyz/aliyun/";

    public static void main(String[] args) throws Exception{
        generateInsertSql(INSERT_TRADE_PREFIX, source+"trade_201805270855.sql", target+"trade.sql");
        generateInsertSql(PRODUCT_SELL_INSERT_PREFIX, source+"t_product_sell_201805270855.sql", target+"t_product_sell.sql");
        generateInsertSql(PRODUCT_ARRANGE_INSERT_PREFIX, source+"t_product_arrange_201805270856.sql",target+"t_product_arrange.sql");
    }

    public static void generateInsertSql(String insertPrefix, String filePath, String target) throws Exception{
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = "";
            while ((line = br.readLine()) != null) {
                if(StringUtil.isEmpty(line))
                    continue;
                else if(StringUtil.trim(line).startsWith(";"))
                    continue;
                else if(StringUtil.trim(line).startsWith(",")){
                    line = StringUtil.trim(line).substring(1);
                }
                sb.append(insertPrefix).append(line).append(";\n");
            }
            br.close();
            System.out.println(sb.toString());

            BufferedWriter bw = new BufferedWriter(new FileWriter(target));
            bw.write(sb.toString());
            bw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
