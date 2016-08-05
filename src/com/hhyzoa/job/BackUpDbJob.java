package com.hhyzoa.job;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhyzoa.util.PropertiesUtil;

/**
 * 定时备份数据库数据
 * @author lizhibin
 *
 */
public class BackUpDbJob implements Job {
	
	private Logger log = LoggerFactory.getLogger(BackUpDbJob.class);
//	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Calendar c = Calendar.getInstance();
		log.info(String.format("[%s]数据库备份开始.......", c.getTime()));
		this.runBat();
		log.info(String.format("[%s]数据库备份结束.......", c.getTime()));
	}
	
	public void runBat() {
//		String batScript = PropertiesUtil.getString("DB_BACKUP_SCRIPT");//数据库备份脚本 
//		log.info(String.format("数据库备份脚本：%s", batScript));
//		StringBuilder sb = new StringBuilder();
//		sb.append("cmd /c ").append(batScript);
//		log.info(String.format("待执行命令为%s", sb.toString()));
		try {  
//			Process ps = Runtime.getRuntime().exec(sb.toString()); 
            Process ps = Runtime.getRuntime().exec("cmd /c J:\\db_backup\\mysqldump.bat"); //可以执行
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
}
