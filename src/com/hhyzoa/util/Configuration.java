package com.hhyzoa.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取Poperties配置文件工具类
 * @author lizhibin
 *
 */
public class Configuration {
	
	private Properties properties;
    private FileInputStream inputFile;
    private FileOutputStream outputFile;
    
    /**
     * 初始化Configuration类
     */
    public Configuration()
    {
        properties = new Properties();
    }
    
   

	/**
     * 初始化Configuration类
     * @param filePath 要读取的配置文件的路径+名称
     */
    public Configuration(String filePath)
    {
        properties = new Properties();
        try {
            inputFile = new FileInputStream(filePath);
            properties.load(inputFile);
            inputFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("装载文件--->失败!");
            ex.printStackTrace();
        }
    }//end ReadConfigInfo(...)
    
    /**
     * 初始化Configuration类
     * @param in 输入流，通常是通过getResourceAsStream(filePath)或者getResource(filePath)得到的
     * filePath可以是"/filename",这里的/代表web发布根路径下WEB-INF/classes
     * 
     */
    public Configuration(InputStream in) {
    	properties = new Properties();
    	try {
    		properties.load(in);
    		in.close();
    	}catch (FileNotFoundException ex) {
            System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("装载文件--->失败!");
            ex.printStackTrace();
        }
    }
    
    /**
     * 重载函数，得到key的值
     * @param key 取得其值的键
     * @return key的值
     */
    public String getValue(String key)
    {
        if(properties.containsKey(key)){
            String value = properties.getProperty(key);//得到某一属性的值
            return value;
        }
        else 
            return "";
    }//end getValue(...)
    
    /**
     * 重载函数，得到key的值
     * @param fileName properties文件的路径+文件名
     * @param key 取得其值的键
     * @return key的值
     */
    public String getValue(String fileName, String key)
    {
        try {
            String value = "";
            inputFile = new FileInputStream(fileName);
            properties.load(inputFile);
            inputFile.close();
            if(properties.containsKey(key)){
                value = properties.getProperty(key);
                return value;
            }else
                return value;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }//end getValue(...)
    
    /**
     * 清除properties文件中所有的key和其值
     */
    public void clear()
    {
        properties.clear();
    }//end clear();
    
    /**
     * 改变或添加一个key的值，当key存在于properties文件中时该key的值被value所代替，
     * 当key不存在时，该key的值是value
     * @param key 要存入的键
     * @param value 要存入的值
     */
    public void setValue(String key, String value)
    {
        properties.setProperty(key, value);
    }//end setValue(...)
    
    /**
     * 将更改后的文件数据存入指定的文件中，该文件可以事先不存在。
     * @param fileName 文件路径+文件名称
     * @param description 对该文件的描述
     */
    public void saveFile(String fileName, String description)
    {
        try {
            outputFile = new FileOutputStream(fileName);
            properties.store(outputFile, description);
            outputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }//end saveFile(...)
    
    public static void main(String[] args)
    {
        InputStream in = Configuration.class.getResourceAsStream("/conf/common_config.properties");
    	
    	Configuration rc = new Configuration(in);//相对路径
        
        rc.setValue("PAGE_SIZE", "30");
        
        rc.saveFile("F:\\MyEclipse 6.5\\Spring2.5\\hhyz_oa\\src\\conf\\common_config.properties", "haha");
        
        String pageSize = rc.getValue("PAGE_SIZE");

        System.out.println("PAGE_SIZE = " + pageSize);

        
    }//end main()
    

	public FileInputStream getInputFile() {
		return inputFile;
	}

	public void setInputFile(FileInputStream inputFile) {
		this.inputFile = inputFile;
	}

	public FileOutputStream getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(FileOutputStream outputFile) {
		this.outputFile = outputFile;
	}



	public Properties getProperties() {
		return properties;
	}



	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
