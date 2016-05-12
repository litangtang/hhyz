package com.hhyzoa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 蓖麻籽进销存统计POJO
 * @author lizhibin
 *
 */
@Entity(name="BUY_SELL_SAVE")
public class BuySellSave {
	
	private Integer id ;			//主键
	private Date date ;				//日期
	private Double buyAmount ;		//当月购进数量
	private Double buyPrice ;		//当月单价
	private Double buyMoney ;		//当月购进金额
	private Double invoiceAmount ;	//当月开票数量
	private Double invoicePrice ;	//当月开票单价
	private Double invoiceMoney ;	//当月开票金额
	private Double balanceAmount ;	//当月结余数量
	private Double balanceMoney ;	//当月结余金额
	private Double averagePrice ;	//当月平均单价
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name="BUY_AMOUNT")
	public Double getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(Double buyAmount) {
		this.buyAmount = buyAmount;
	}
	
	@Column(name="BUY_PRICE")
	public Double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	@Column(name="BUY_MONEY")
	public Double getBuyMoney() {
		return buyMoney;
	}
	public void setBuyMoney(Double buyMoney) {
		this.buyMoney = buyMoney;
	}
	
	@Column(name="INVOICE_AMOUNT")
	public Double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	
	@Column(name="INVOICE_PRICE")
	public Double getInvoicePrice() {
		return invoicePrice;
	}
	public void setInvoicePrice(Double invoicePrice) {
		this.invoicePrice = invoicePrice;
	}
	
	@Column(name="INVOICE_MONEY")
	public Double getInvoiceMoney() {
		return invoiceMoney;
	}
	public void setInvoiceMoney(Double invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
	}
	
	@Column(name="BALANCE_AMOUNT")
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	@Column(name="BALANCE_MONEY")
	public Double getBalanceMoney() {
		return balanceMoney;
	}
	public void setBalanceMoney(Double balanceMoney) {
		this.balanceMoney = balanceMoney;
	}
	
	@Column(name="AVERAGE_PRICE")
	public Double getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(Double averagePrice) {
		this.averagePrice = averagePrice;
	}
	
	
	
	

}
