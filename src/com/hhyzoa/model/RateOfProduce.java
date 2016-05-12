package com.hhyzoa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 生产出品率包含出油率、出饼率
 * @author lizhibin
 *
 */
@Entity(name="RATE_OF_PRODUCE")
public class RateOfProduce {
	
	private Integer id;					//主键
	private Date date;					//日期
	private Double numOfCans;			//油桶数
	private Double numOfPackages;		//饼件数
	private Double rateOfOil;			//出油率
	private Double rateOfCake;			//出饼率
	private Integer numOfMachines;		//机器数，1为单机生产，2为双机生产
	private String numOfMachinesStr;	//页面显示机器数字符串
	private Integer flag;				//0为单次的出品率，1为日平均数，2为月平均数，默认值为0
	
	private Double cansMultiply;		//油桶重量
	private Double packagesMultiply;	//饼袋重量/饼规格
	private Double rateMultiply;;		//出品率基数 -- 0.945 (原料总重量(即当天蓖麻重量) = (当天油的重量 + 当天饼的重量)/0.945)
	
	private Double capacity;			//当日油的容积
	private Double oilRate;				//油的比重
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="DATE")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name="NUM_OF_CANS")
	public Double getNumOfCans() {
		return numOfCans;
	}
	public void setNumOfCans(Double numOfCans) {
		this.numOfCans = numOfCans;
	}
	
	@Column(name="NUM_OF_PACKAGES")
	public Double getNumOfPackages() {
		return numOfPackages;
	}
	public void setNumOfPackages(Double numOfPackages) {
		this.numOfPackages = numOfPackages;
	}
	
	@Column(name="RATE_OF_OIL")
	public Double getRateOfOil() {
		return rateOfOil;
	}
	public void setRateOfOil(Double rateOfOil) {
		this.rateOfOil = rateOfOil;
	}
	
	@Column(name="RATE_OF_CAKE")
	public Double getRateOfCake() {
		return rateOfCake;
	}
	public void setRateOfCake(Double rateOfCake) {
		this.rateOfCake = rateOfCake;
	}
	
	@Column(name="NUM_OF_MACHINES")
	public Integer getNumOfMachines() {
		return numOfMachines;
	}
	public void setNumOfMachines(Integer numOfMachines) {
		this.numOfMachines = numOfMachines;
	}
	
	@Column(name="FLAG")
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	@Transient
	public String getNumOfMachinesStr() {
		return numOfMachinesStr;
	}
	public void setNumOfMachinesStr(String numOfMachinesStr) {
		this.numOfMachinesStr = numOfMachinesStr;
	}
	
	@Column(name="CANS_MULTIPLY")
	public Double getCansMultiply() {
		return cansMultiply;
	}
	public void setCansMultiply(Double cansMultiply) {
		this.cansMultiply = cansMultiply;
	}
	
	@Column(name="PACKAGES_MULTIPLY")
	public Double getPackagesMultiply() {
		return packagesMultiply;
	}
	public void setPackagesMultiply(Double packagesMultiply) {
		this.packagesMultiply = packagesMultiply;
	}
	
	@Column(name="RATE_MULTIPLY")
	public Double getRateMultiply() {
		return rateMultiply;
	}
	public void setRateMultiply(Double rateMultiply) {
		this.rateMultiply = rateMultiply;
	}
	
	@Column(name="CAPACITY")
	public Double getCapacity() {
		return capacity;
	}
	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}
	
	@Column(name="OIL_RATE")
	public Double getOilRate() {
		return oilRate;
	}
	public void setOilRate(Double oilRate) {
		this.oilRate = oilRate;
	}

}
