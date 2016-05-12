<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<base href="<%=basePath%>">
		<!-- 页面所有路径以项目路径为基础即以WebRoot为基础 -->
		<title>增加往来</title>

		<link type="text/css" href="styles/themes/base/jquery.ui.all.css"
			rel="stylesheet" />
		<link href="css/elson.css" rel="stylesheet" type="text/css" />
		<link href="css/elsonLink.css" rel="stylesheet" type="text/css" />
		<link href="css/elsonTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="js/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="js/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript"
			src="js/ui/jquery.ui.datepicker-zh-CN.js"></script>
		<script type="text/javascript" src="js/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="js/tableMain.js"></script>
		<script type="text/javascript" src="js/validForm.js"></script>
		<script language="javascript">
		$(function() {
			$("table.maintable tr:nth-child(odd)").addClass("ymaintext");
			$("table.maintable tr:nth-child(even)").addClass("zmaintext");
			$("table.maintable tr:not(:last) td:first-child").attr("align","right");
			
			$('#date').datepicker({
				changeMonth: true,
				changeYear: true
			});

			//送货金额根据数量和单价自动算出，即送货金额=单价*数量
			$("#carriage").focus(function(){
				var amount = $("#amount").val();
				var price = $("#price").val();
				if(null != amount && "" != amount && null != price && "" != price) {
					var temp = price*amount;//计算送货金额=单价*数量
					//var tempNum = new Number(temp);
					$("#carriage").attr("value",numFormat(temp,2));//格式化数据，保留两位小数
				}
			});

			var from = $("#from").val();
			
			$("#submitBtn").click(function() {
				if("trade_1" == from || "trade_2" == from) {
					var selectClientId = document.getElementById("selectClientId");
					if(selectClientId.value == -1) {
						alert("请选择客户");
						return false;
					}
				}
				
				var date = document.getElementById("date");
				if(!validateNull(date,'日期不能为空')) {
					return false;
				}

				var packages = document.getElementById("packages");
				if(!validateNumber(packages,'件数必须为数字')) {
					return false;
				}

				var amount = document.getElementById("amount");
				if(!validateNumber(amount,'数量必须为数字')) {
					return false;
				}

				var price = document.getElementById("price");
				if(!validateNumber(price,'单价必须为数字')) {
					return false;
				}

				var carriage = document.getElementById("carriage");
				if(!validateNumber(carriage,'送货金额必须为数字')) {
					return false;
				}

				var payment = document.getElementById("payment");
				if(!validateNumber(payment,'付款金额必须为数字')) {
					return false;
				}

				document.forms[0].action = "trade/tradeAdd.action";
				document.forms[0].submit();
			});

			$("#backBtn").click(function() {
				//history.go(-1);
				
				if("trade_1" == from) {
					document.forms[0].action = "trade/listAll.action?tradeFlag=1";
					document.forms[0].submit();
				}else if("trade_2" == from) {
					document.forms[0].action = "trade/listAll.action?tradeFlag=2";
					document.forms[0].submit();
				}else if("client_1" == from) {
					document.forms[0].action = "client/listAll.action?searchType=1";
					document.forms[0].submit();
				}else if("client_2" == from) {
					document.forms[0].action = "client/listAll.action?searchType=2";
					document.forms[0].submit();
				}
			});
			
		});
	</script>
	</head>
	<%
		String from = (String)request.getAttribute("from");//来源即从哪里跳转过来
		if(null == from) {
			from = "trade_1";//默认是从原料往来跳转过来
		}
		
		String tradeFlag = (String)request.getAttribute("tradeFlag");
		if(null == tradeFlag) {
			tradeFlag = "1";//0为所有往来1为原料往来 2为销售往来
		}
	%>
	<body>
		<center>

			<div>
				<form action="" method="post">
					<input type="hidden" name="clientId" value="<s:property value='clientId'/>">
					<input type="hidden" id="from" name="from" value="<%=from %>"/>
					<!-- 第一行：top -->
					<table width="100%" border="0" align="left" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="25" height="50" background="img/main01.png">
								&nbsp;
							</td>
							<td background="img/main02.png">
								<span id="maintop"></span><span class="mainAction">当前路径：添加 - <%=(tradeFlag.equals("1")?"原料往来":"销售往来") %></span>
							</td>
							<td width="29" background="img/main03.png">
								&nbsp;
							</td>
						</tr>
						<!-- 第二行：content -->
						<tr>
							<td background="img/main04.png">
								&nbsp;
							</td>
							<td align="center" valign="top" bgcolor="#FEFCFC" height="500">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="maintop">
									<tr>
										<td height="30">
											<img src="img/titleR1.png" width="28" height="28"
												class="mainmin">
											<span class="maintoptext">添加信息</span><span
												class="maintopbutt"></span>
										</td>
									</tr>
								</table>
								<table width="60%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td height="5"></td>
									</tr>
								</table>
								<table class="maintable" width="600" border="0" align="center" cellpadding="0" cellspacing="0">
										<tr>
											<td>
												客户：
											</td>
											<td>
												<s:if test="'client_1' == #request.from || 'client_2' == #request.from || 'client_0' == #request.from">
													<label>
														<s:property value="clientName" />
													</label>
												</s:if>
												<s:elseif test="'trade_1' == #request.from || 'trade_2' == #request.from">
													<s:select list="#request.clientMap" id="selectClientId"
														name="selectClientId" headerKey="-1" headerValue="--请选择客户--" value="#request.selectClientId"/>
												</s:elseif>
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												摘要：
											</td>
											<td>
												<input type="text" id="abst" name="trade.abst" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												日期：
											</td>
											<td>
												<input type="text" id="date" name="trade.date"
													class="ui-widget-content ui-corner-all" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												件数：
											</td>
											<td>
												<input type="text" id="packages" name="trade.packages" />&nbsp;(件)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												数量：
											</td>
											<td>
												<input type="text" id="amount" name="trade.amount" />&nbsp;(kg)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												单价：
											</td>
											<td>
												<input type="text" id="price" name="trade.price" />&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												<%=(tradeFlag.equals("1")?"送货金额":"贷款金额") %>：
											</td>
											<td>
												<input type="text" id="carriage" name="trade.carriage" />&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												<%=(tradeFlag.equals("1")?"付款金额":"收回金额") %>：
											</td>
											<td>
												<input type="text" id="payment" name="trade.payment" />&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										<!--
										<tr>
											<td>
												借贷：
											</td>
											<td>
												<input type="text" id="isLoan" name="trade.isLoan" />
											</td>
											<td>&nbsp;</td>
										</tr>
										  
										<tr>
											<td>
												余额：
											</td>
											<td>
												<input type="text" id="balance" name="trade.balance" />&nbsp;(元)
											</td>
											<td>&nbsp;</td>
										</tr>
										-->
										<tr>
											<td>
												核对情况：
											</td>
											<td>
												<input type="text" id="verify" name="trade.verify" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												备注：
											</td>
											<td>
												<input type="text" id="remark" name="trade.remark" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td colspan="2" align="center">
												<input type="button" value="增加" id="submitBtn" />
												<input type="button" value="返回" id="backBtn" />
											</td>
										</tr>

								</table>
							</td>
							<td background="img/main05.png">&nbsp;</td>
						</tr>
					</table>
					<s:token />
				</form>
			</div>
		</center>
	</body>
</html>
