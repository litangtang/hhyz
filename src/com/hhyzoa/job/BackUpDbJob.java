package com.hhyzoa.job;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时备份数据库数据
 * @author lizhibin
 *
 */
public class BackUpDbJob implements Job {
	
	private Logger log = LoggerFactory.getLogger(BackUpDbJob.class);
	private String dbPropertiesPath = "/conf/jdbc.properties";
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Calendar c = Calendar.getInstance();
		log.info(String.format("[%s]数据库备份开始.......", c.getTime()));
		this.runBat();
		log.info(String.format("[%s]数据库备份结束.......", c.getTime()));
	}
	
	public void runBat() {
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String curDate = sdf.format(date);
		String fileName = "hhyz_" + curDate + ".sql";
		log.info(String.format("当前时间：[%s],数据库备份文件名:[%s]", curDate, fileName));
		//组装备份脚本命令
		StringBuilder sb = new StringBuilder("cmd /c ");
		String path = BackUpDbJob.class.getResource("").getPath();//获取当前类路径:/D:/Develop/tomcat/webapps/hhyz_oa/WEB-INF/classes/com/hhyzoa/job/
		log.info("当前类路径：" + path);
		
		Properties properties = loadProperties(dbPropertiesPath);
		String dbUrl = properties.getProperty("jdbc.url");
		String dbName = dbUrl.substring(dbUrl.lastIndexOf("/") + 1);
		String username = properties.getProperty("jdbc.username");
		String password = properties.getProperty("jdbc.password");
		String dumpPath = properties.getProperty("jdbc.dumpPath");//E:\\db_backup\\
		log.info(String.format("数据库为[%s],用户名为[%s],密码为[%s],备份路径[%s]", dbName, username, password, dumpPath));
		
		sb.append(dumpPath)
		  .append("mysqldump.exe ")
		  .append("-u").append(username).append(" ")
		  .append("-p").append(password).append(" ")
		  .append(dbName).append(" > ")
		  .append(dumpPath).append(fileName);
		log.info(String.format("待执行命令为%s", sb.toString()));
		try {  
//			Process ps = Runtime.getRuntime().exec("cmd /c E:\\dbbackup\\mysqldump.exe -uroot -p123456 hhyz > E:/dbbackup/hhyz_%file_name%.sql");//可以执行 
//			Process ps = Runtime.getRuntime().exec("cmd /c E:\\dbbackup\\mysqldump.bat"); //可以执行
			Process ps = Runtime.getRuntime().exec(sb.toString());
//			String[] cmdStr = {"d:\\mysqldump.exe -uroot -pmysql hhyz>D:\\db_backup\\hhyz_20160115.sql"};
            InputStream in = ps.getInputStream();  
            int c;  
            while ((c = in.read()) != -1) {  
                System.out.print(c);// 如果你不需要看输出，这行可以注销掉  
            }  
            in.close();  
            ps.waitFor();  
  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
	}
	
	//加载jdbc.properties
	public Properties loadProperties(String path) {
		String classPath = this.getClass().getResource("/").getPath();
		log.info("获取项目根路径：" + classPath);
		Properties properties = new Properties();
		InputStream is = this.getClass().getResourceAsStream(path);
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
