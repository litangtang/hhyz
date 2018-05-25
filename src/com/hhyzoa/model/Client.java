package com.hhyzoa.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


/**
 * 客户
 * @author lizhibin
 *
 */
@Entity(name="Client")
public class Client {

	private Integer id;
	private String name;
	private String companyName;
	private Integer type;//1为原料客户 2为销售客户 与trade.flag一一对应
	private String typeStr;//页面展示类别

	private String companyAddr;
	private String officePhone;
	private String mobilePhone;
	private String fax;
	private String remark;
	//2017-02-27 增加count，modtime
	private Integer count;
	private Date modtime;



	private Set<Trade> trades; //与往来一对多

	@OneToMany(mappedBy="client",
			cascade={CascadeType.REMOVE}
			//fetch=FetchType.EAGER
	)
	public Set<Trade> getTrades() {
		return trades;
	}
	public void setTrades(Set<Trade> trades) {
		this.trades = trades;
	}

	private Set<CostAccounting> costAccountings; //与成本核算一对多

	@OneToMany(mappedBy="client",
			cascade={CascadeType.REMOVE}
	)
	public Set<CostAccounting> getCostAccountings() {
		return costAccountings;
	}
	public void setCostAccountings(Set<CostAccounting> costAccountings) {
		this.costAccountings = costAccountings;
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name="COMPANY_NAME")
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	@Transient
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}


	public static Map<Integer,String> getTypeString() {
		Map<Integer,String> typeStrMap = new HashMap<Integer,String>();
		typeStrMap.put(Integer.valueOf(1), "原料客户");
		typeStrMap.put(Integer.valueOf(2), "销售客户");
		return typeStrMap;

	}

	@Column(name="COMPANY_ADDR")
	public String getCompanyAddr() {
		return companyAddr;
	}
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	@Column(name="OFFICE_PHONE")
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	@Column(name="MOBILE_PHONE")
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name="FAX")
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="count")
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name="modtime")
	public Date getModtime() {
		return modtime;
	}
	public void setModtime(Date modtime) {
		this.modtime = modtime;
	}

}
