package com.hhyzoa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * CREATE TABLE `t_invice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `invoice_comp` varchar(100) DEFAULT NULL COMMENT '开票单位',
  `count` Double DEFAULT NULL,
  `price` Double DEFAULT NULL,
  `orig_amt` Double DEFAULT NULL COMMENT '金额',
  `no_tax_amt` Double DEFAULT NULL COMMENT '不含税金额',
  `no_tax_accu` Double DEFAULT NULL COMMENT '不含税累计',
  `sell_tax` Double DEFAULT NULL COMMENT '销项税',
  `sell_tax_accu` Double DEFAULT NULL COMMENT '销项税累计',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
	)
 * 开票记录
 * @author Bill
 *
 */
@Entity(name="t_invoice")
public class Invoice {
	
	private Integer id;
	private Integer day;			//月
	private Integer month;			//日
	private Integer year;			//年
	private String invoiceComp;		//开票单位
	private Double count;			//数量
	private Double price;			//单价
	private Double origAmt;			//金额
	private Double noTaxAmt;		//不含税金额
	private Double noTaxAccu;		//不含税金额累计
	private Double sellTax;			//销项税
	private Double sellTaxAccu;		//销项税累计
	private String remark;			//备注
	
	private Date recordDate; 			//service里解析年月日存库
	private Double noTaxAmtChange;		//不含税金额增量
	private Double sellTaxChange;		//销项税增量
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	@Column(name="invoice_comp")
	public String getInvoiceComp() {
		return invoiceComp;
	}
	public void setInvoiceComp(String invoiceComp) {
		this.invoiceComp = invoiceComp;
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
	
	@Column(name="orig_amt")
	public Double getOrigAmt() {
		return origAmt;
	}
	public void setOrigAmt(Double origAmt) {
		this.origAmt = origAmt;
	}
	
	@Column(name="no_tax_amt")
	public Double getNoTaxAmt() {
		return noTaxAmt;
	}
	public void setNoTaxAmt(Double noTaxAmt) {
		this.noTaxAmt = noTaxAmt;
	}
	
	@Column(name="no_tax_accu")
	public Double getNoTaxAccu() {
		return noTaxAccu;
	}
	public void setNoTaxAccu(Double noTaxAccu) {
		this.noTaxAccu = noTaxAccu;
	}
	
	@Column(name="sell_tax")
	public Double getSellTax() {
		return sellTax;
	}
	public void setSellTax(Double sellTax) {
		this.sellTax = sellTax;
	}
	
	@Column(name="sell_tax_accu")
	public Double getSellTaxAccu() {
		return sellTaxAccu;
	}
	public void setSellTaxAccu(Double sellTaxAccu) {
		this.sellTaxAccu = sellTaxAccu;
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
	public Double getNoTaxAmtChange() {
		return noTaxAmtChange;
	}
	public void setNoTaxAmtChange(Double noTaxAmtChange) {
		this.noTaxAmtChange = noTaxAmtChange;
	}
	
	@Transient
	public Double getSellTaxChange() {
		return sellTaxChange;
	}
	public void setSellTaxChange(Double sellTaxChange) {
		this.sellTaxChange = sellTaxChange;
	}
}
