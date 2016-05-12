package com.hhyzoa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 发货安排
 * @author Bill
 *
 */
@Entity(name="t_product_arrange")
public class ProductArrange {
	
	private Integer id;
	private Integer day;			//月
	private Integer month;			//日
	private Integer year;			//年
	private String clientName;		//订货单位
	private Integer packages;		//件数
	private Float count;			//数量,吨数
	private Float price;			//单价
	private Float amount;			//金额
	private String arriveDate;		//到货时间
	private Integer isSend;			//是否发货，0-未发货，1-已发货 
	private String remark;			//备注
	
	private Date recordDate; 		//记录日期,service里解析年月日存库
	
	private Integer packageAccu;	//件数累计,不存库
	private Float countAccu;		//数量累计,不存库
	private Float amountAccu;		//金额累计,不存库
	
	private String packageAccuStr;	//件数累计,取消科学计数法展示,不存库
	private String countAccuStr ;//数量累计累计,取消科学计数法展示,不存库
	private String amountAccuStr;	//金额累计,取消科学计数法展示,不存库
	
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="packages")
	public Integer getPackages() {
		return packages;
	}
	public void setPackages(Integer packages) {
		this.packages = packages;
	}
	
	@Column
	public Float getCount() {
		return count;
	}
	public void setCount(Float count) {
		this.count = count;
	}
	
	@Column
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	@Column
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	@Column
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	
	@Column
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	@Column
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	@Column(name="client_name")
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	@Column(name="arrive_date")
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	
	@Column(name="is_send")
	public Integer getIsSend() {
		return isSend;
	}
	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Transient
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
	@Transient
	public Integer getPackageAccu() {
		return packageAccu;
	}
	public void setPackageAccu(Integer packageAccu) {
		this.packageAccu = packageAccu;
	}
	
	@Transient
	public Float getCountAccu() {
		return countAccu;
	}
	public void setCountAccu(Float countAccu) {
		this.countAccu = countAccu;
	}
	
	@Transient
	public Float getAmountAccu() {
		return amountAccu;
	}
	public void setAmountAccu(Float amountAccu) {
		this.amountAccu = amountAccu;
	}
	
	@Transient
	public String getPackageAccuStr() {
		return packageAccuStr;
	}
	public void setPackageAccuStr(String packageAccuStr) {
		this.packageAccuStr = packageAccuStr;
	}
	
	@Transient
	public String getAmountAccuStr() {
		return amountAccuStr;
	}
	public void setAmountAccuStr(String amountAccuStr) {
		this.amountAccuStr = amountAccuStr;
	}
	
	@Transient
	public String getCountAccuStr() {
		return countAccuStr;
	}
	public void setCountAccuStr(String countAccuStr) {
		this.countAccuStr = countAccuStr;
	}
	
	
}
