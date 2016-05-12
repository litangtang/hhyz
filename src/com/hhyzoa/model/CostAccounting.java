package com.hhyzoa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 生产成本核算实体
 * @author lizhibin
 *
 */
@Entity(name="COST_ACCOUNTING")
public class CostAccounting {
	
	private Integer id;
	private String materialOrigin;		//原料产地
	private Double materialPrice;		//原料价格
	private Double materialUnitPrice;	//原料单价
	private Double rateOfOil;			//出油率
	private Integer materialCost;		//原料成本
	private Integer fcpz;				//副产品值
	private Integer ejyz;				//二级油值
	private Integer ejyJgf;				//二级油加工费
	private Integer ejjsCost;			//二级净水成本
	private Integer yjyJgf;				//一级油加工费
	private Double yjyShl;				//一级油损耗率
	private Integer jlsh;				//精炼损耗
	private Integer yjjsCost;			//一级净水成本
	private Integer tax;				//国地两税
	private Integer	ttbz;				//铁桶包装
	private Integer	tqyf;				//铁汽运费
//	private Integer clientId;			//供应单位ID,即销售客户id
//	private String clientName;			//供应单位名称，即销售客户名称
	private Integer sellCost;			//销售成本
	private Integer sellProfit;			//销售利润
	private Integer sellPrice;			//销售价格(出厂价),不包括铁汽运费
	private Integer finalPrice;			//送到价 = 出厂价 + 铁汽运费
	private String remark;				//备注
	private Date date;					//日期
	
	private Client client;		//客户
	
	@ManyToOne
	//@JoinColumn(name="CLIENT_ID")
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="MATERIAL_ORIGIN")
	public String getMaterialOrigin() {
		return materialOrigin;
	}
	public void setMaterialOrigin(String materialOrigin) {
		this.materialOrigin = materialOrigin;
	}
	
	@Column(name="RATE_OF_OIL")
	public Double getRateOfOil() {
		return rateOfOil;
	}
	public void setRateOfOil(Double rateOfOil) {
		this.rateOfOil = rateOfOil;
	}
	
	@Column(name="MATERIAL_COST")
	public Integer getMaterialCost() {
		return materialCost;
	}
	public void setMaterialCost(Integer materialCost) {
		this.materialCost = materialCost;
	}
	
	@Column
	public Integer getFcpz() {
		return fcpz;
	}
	public void setFcpz(Integer fcpz) {
		this.fcpz = fcpz;
	}
	
	@Column
	public Integer getEjyz() {
		return ejyz;
	}
	public void setEjyz(Integer ejyz) {
		this.ejyz = ejyz;
	}
	
	@Column(name="EJY_JGF")
	public Integer getEjyJgf() {
		return ejyJgf;
	}
	public void setEjyJgf(Integer ejyJgf) {
		this.ejyJgf = ejyJgf;
	}
	
	@Column(name="EJJS_COST")
	public Integer getEjjsCost() {
		return ejjsCost;
	}
	public void setEjjsCost(Integer ejjsCost) {
		this.ejjsCost = ejjsCost;
	}
	
	@Column(name="YJY_JGF")
	public Integer getYjyJgf() {
		return yjyJgf;
	}
	public void setYjyJgf(Integer yjyJgf) {
		this.yjyJgf = yjyJgf;
	}
	
	@Column
	public Integer getJlsh() {
		return jlsh;
	}
	public void setJlsh(Integer jlsh) {
		this.jlsh = jlsh;
	}
	
	@Column(name="YJJS_COST")
	public Integer getYjjsCost() {
		return yjjsCost;
	}
	public void setYjjsCost(Integer yjjsCost) {
		this.yjjsCost = yjjsCost;
	}
	
	@Column
	public Integer getTax() {
		return tax;
	}
	public void setTax(Integer tax) {
		this.tax = tax;
	}
	
	@Column
	public Integer getTtbz() {
		return ttbz;
	}
	public void setTtbz(Integer ttbz) {
		this.ttbz = ttbz;
	}
	
	@Column
	public Integer getTqyf() {
		return tqyf;
	}
	public void setTqyf(Integer tqyf) {
		this.tqyf = tqyf;
	}
	
	@Column(name="SELL_COST")
	public Integer getSellCost() {
		return sellCost;
	}
	public void setSellCost(Integer sellCost) {
		this.sellCost = sellCost;
	}
	
	@Column(name="SELL_PROFIT")
	public Integer getSellProfit() {
		return sellProfit;
	}
	public void setSellProfit(Integer sellProfit) {
		this.sellProfit = sellProfit;
	}
	
	@Column(name="SELL_PRICE")
	public Integer getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Integer sellPrice) {
		this.sellPrice = sellPrice;
	}
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="MATERIAL_PRICE")
	public Double getMaterialPrice() {
		return materialPrice;
	}
	public void setMaterialPrice(Double materialPrice) {
		this.materialPrice = materialPrice;
	}
	
	@Column(name="MATERIAL_UNIT_PRICE")
	public Double getMaterialUnitPrice() {
		return materialUnitPrice;
	}
	public void setMaterialUnitPrice(Double materialUnitPrice) {
		this.materialUnitPrice = materialUnitPrice;
	}
	
	@Column
	public Double getYjyShl() {
		return yjyShl;
	}
	public void setYjyShl(Double yjyShl) {
		this.yjyShl = yjyShl;
	}
	
	@Column
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name="FINAL_PRICE")
	public Integer getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Integer finalPrice) {
		this.finalPrice = finalPrice;
	}
	
	
//	@Column(name="CLIENT_ID")
//	public Integer getClientId() {
//		return clientId;
//	}
//	public void setClientId(Integer clientId) {
//		this.clientId = clientId;
//	}
	
//	@Transient
//	public String getClientName() {
//		return clientName;
//	}
//	public void setClientName(String clientName) {
//		this.clientName = clientName;
//	}

}
