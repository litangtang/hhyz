package com.hhyzoa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 销售记录
 * @author Bill
 *
 */
@Entity(name="t_product_sell")
public class ProductSell {
	
	private Integer id;
	private String clientName;		//客户名称
	private Integer busiType;		//1-蓖麻饼，2-精炼一级油，3-国标二级油
	private Integer packages;		//件数
	private Double count;			//数量
	private Double amount;			//金额
	private Double price;			//单价
	private Integer day;			//月
	private Integer month;			//日
	private Integer year;			//年
	private String remark;			//备注
	
	private Date sellDate; 			//销售日期,service里解析年月日存库
	private Integer packagesChange;	//件数增量,不存库
	private Double countChange;		//数量增量,不存库
	private Double amountChange;		//金额增量,不存库
	
	private Integer packagesAccu;	//件数累计
	private Double countAccu;		//数量累计
	private Double amountAccu;		//金额累计
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="busi_type")
	public Integer getBusiType() {
		return busiType;
	}
	public void setBusiType(Integer busiType) {
		this.busiType = busiType;
	}
	
	@Column(name="packages")
	public Integer getPackages() {
		return packages;
	}
	public void setPackages(Integer packages) {
		this.packages = packages;
	}
	
	@Column
	public Double getCount() {
		return count;
	}
	public void setCount(Double count) {
		this.count = count;
	}
	
	@Column
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Column
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
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
	
	@Column(name="packages_accu")
	public Integer getPackagesAccu() {
		return packagesAccu;
	}
	public void setPackagesAccu(Integer packagesAccu) {
		this.packagesAccu = packagesAccu;
	}
	
	@Column(name="count_accu")
	public Double getCountAccu() {
		return countAccu;
	}
	public void setCountAccu(Double countAccu) {
		this.countAccu = countAccu;
	}
	
	@Column(name="amount_accu")
	public Double getAmountAccu() {
		return amountAccu;
	}
	public void setAmountAccu(Double amountAccu) {
		this.amountAccu = amountAccu;
	}
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Transient
	public Date getSellDate() {
		return sellDate;
	}
	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}
	
	@Transient
	public Integer getPackagesChange() {
		return packagesChange;
	}
	public void setPackagesChange(Integer packagesChange) {
		this.packagesChange = packagesChange;
	}
	
	@Transient
	public Double getCountChange() {
		return countChange;
	}
	public void setCountChange(Double countChange) {
		this.countChange = countChange;
	}
	
	@Transient
	public Double getAmountChange() {
		return amountChange;
	}
	public void setAmountChange(Double amountChange) {
		this.amountChange = amountChange;
	}
}
