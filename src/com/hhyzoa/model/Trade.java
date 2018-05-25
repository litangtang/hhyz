package com.hhyzoa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 原料往来
 * @author lizhibin
 *
 */
@Entity(name="Trade")
public class Trade {
	
	private Integer id ;		//主键
	private Date date ;			//日期
	private String abst ;		//摘要
	private Integer packages ; 	//件数
	private Double amount ;		//数量
	private Double price ;		//单价
	private Double carriage ;	//送货金额
	private Double payment ;     //付款金额
	
	//欠别人即为贷，别人欠自己则为借
	//对于原料往来，送货金额-付款金额，余额为正则为2贷，为负则为1即借
	//对于销售往来，贷款金额-收款金额，余额为正则为1即借，为负则为2即贷
	private Integer isLoan ;	//借贷标志 1为借 2为贷0为平
	
	private String isLoanStr ;	//借贷标志字符串
	
	private Double balance ;		//余额
	private String verify ;		//核对情况
	private String remark ;		//备注
	private Integer flag ;		//1为原料往来 2为销售往来  与client.type一一对应
	private Integer level ;		//每增加一笔来往，level+1，针对某个客户,从1开始
	
	private Client client;		//客户
	
	/*********************************************************
	 * 			累计功能计算方法
	 * 不存库,遍历查询结果动态计算并赋值
	 * 
	 * *******************************************************
	 */
	private Integer packageAccu ; 	//件数累计
	private Double amountAccu;		//数量累计
	private Double balanceAccu ;		//余额累计
	
	private String packageAccuStr;	//件数累计,取消科学计数法展示
	private String amountAccuStr;		//数量累计,取消科学计数法展示
	private String balanceAccuStr ;		//余额累计,取消科学计数法展示
	
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
	
	@Column
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column
	public Integer getPackages() {
		return packages;
	}
	public void setPackages(Integer packages) {
		this.packages = packages;
	}
	
	@Column
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Column
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Column
	public Double getCarriage() {
		return carriage;
	}
	public void setCarriage(Double carriage) {
		this.carriage = carriage;
	}
	
	@Column
	public Double getPayment() {
		return payment;
	}
	public void setPayment(Double payment) {
		this.payment = payment;
	}
	
	@Column(name="IS_LOAN")
	public Integer getIsLoan() {
		return isLoan;
	}
	public void setIsLoan(Integer isLoan) {
		this.isLoan = isLoan;
	}
	
	@Column
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	@Column
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column
	public String getAbst() {
		return abst;
	}
	public void setAbst(String abst) {
		this.abst = abst;
	}
	
	@Column
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Transient
	public String getIsLoanStr() {
		return isLoanStr;
	}
	public void setIsLoanStr(String isLoanStr) {
		this.isLoanStr = isLoanStr;
	}
	
	@Transient
	public Integer getPackageAccu() {
		return packageAccu;
	}
	public void setPackageAccu(Integer packageAccu) {
		this.packageAccu = packageAccu;
	}
	
	@Transient
	public Double getAmountAccu() {
		return amountAccu;
	}
	public void setAmountAccu(Double amountAccu) {
		this.amountAccu = amountAccu;
	}
	
	@Transient
	public Double getBalanceAccu() {
		return balanceAccu;
	}
	public void setBalanceAccu(Double balanceAccu) {
		this.balanceAccu = balanceAccu;
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
	public String getBalanceAccuStr() {
		return balanceAccuStr;
	}
	public void setBalanceAccuStr(String balanceAccuStr) {
		this.balanceAccuStr = balanceAccuStr;
	}
			
	
}
