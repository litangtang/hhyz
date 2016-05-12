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
		<title>添加产品核算记录</title>

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

			var materialOrigin = document.getElementById("materialOrigin");
			var date = document.getElementById("date");
			var materialPrice = document.getElementById("materialPrice");	
			var rateOfOil = document.getElementById("rateOfOil");
			var materialCost = document.getElementById("materialCost");	//
			var fcpz = document.getElementById("fcpz");
			var ejyz = document.getElementById("ejyz");	//
			var ejyJgf = document.getElementById("ejyJgf");
			var ejjsCost = document.getElementById("ejjsCost");	//
			var yjyShl = document.getElementById("yjyShl");
			var yjyJgf = document.getElementById("yjyJgf");
			var jlsh = document.getElementById("jlsh");	//
			var yjjsCost = document.getElementById("yjjsCost"); //
			var tax = document.getElementById("tax");
			var ttbz = document.getElementById("ttbz");
			var tqyf = document.getElementById("tqyf");
			var sellCost = document.getElementById("sellCost");	//
			var sellProfit = document.getElementById("sellProfit");	//
			var sellPrice = document.getElementById("sellPrice");
			var selectClientId = document.getElementById("selectClientId");
			var finalPrice = document.getElementById("finalPrice");

			//以下是需要自动计算值，同时提交时不能为空
			$("#materialUnitPrice").focus(function(){
				//原料单价 = 原料价格 / 2000
				if(null != materialPrice && "" != materialPrice ) {
					var temp = materialPrice.value / 2000 ;
					$("#materialUnitPrice").attr("value",numFormat(temp,3));//格式化数据，保留三位小数
				}
			});

			$("#materialCost").focus(function(){
				//原料成本= 原料价格 / 出油率
				if(null != materialPrice && "" != materialPrice && null != rateOfOil && "" != rateOfOil ) {
					var temp = materialPrice.value / rateOfOil.value ;
					$("#materialCost").attr("value",numFormat(temp,0));//格式化数据，保留0位小数
				}
			});

			$("#ejyz").focus(function(){
				//二级油值= 原料成本 - 副产品值
				if(null != materialCost && "" != materialCost && null != fcpz && "" != fcpz ) {
					var temp = materialCost.value - fcpz.value ;
					$("#ejyz").attr("value",numFormat(temp,0));//格式化数据，保留0位小数
				}
			});

			$("#ejjsCost").focus(function(){
				//二级净水成本(不带包装)= 二级油值 + 二级油加工费
				if(null != ejyz && "" != ejyz && null != ejyJgf && "" != ejyJgf ) {
					var temp = parseInt(ejyz.value) + parseInt(ejyJgf.value) ;
					$("#ejjsCost").attr("value",numFormat(temp,0));//格式化数据，保留0位小数
				}
			});

			$("#jlsh").focus(function(){
				//精炼损耗= 二级净水成本 * 一级油损耗率
				if(null != ejjsCost && "" != ejjsCost && null != yjyShl && "" != yjyShl ) {
					var temp = parseInt(ejjsCost.value) * parseFloat(yjyShl.value) ;
					$("#jlsh").attr("value",numFormat(temp,0));//格式化数据，保留0位小数
				}
			});
			

			$("#yjjsCost").focus(function(){
				//一级净水成本 = 二级净水成本 + 一级油加工费 + 精炼损耗
				if(null != ejjsCost && "" != ejjsCost && null != yjyJgf && "" != yjyJgf && null != jlsh && "" != jlsh) {
					var temp = parseInt(ejjsCost.value) + parseInt(yjyJgf.value) + parseInt(jlsh.value);
					$("#yjjsCost").attr("value",numFormat(temp,0));//格式化数据，保留0位小数
				}
			});

			$("#sellCost").focus(function(){
				//销售成本 = 一级净水成本 + 国地两税 + 铁桶包装 
				if(null != yjjsCost && "" != yjjsCost && null != tax && "" != tax && null != ttbz && "" != ttbz ) {
					var temp = parseInt(yjjsCost.value) + parseInt(tax.value) + parseInt(ttbz.value) ;
					$("#sellCost").attr("value",numFormat(temp,0));//格式化数据，保留0位小数
				}
			});

			$("#sellPrice").focus(function(){
				//销售价格(出厂价格) = 销售利润 + 销售成本
				if(null != sellProfit && "" != sellProfit && null != sellCost && "" != sellCost) {
					var temp = parseInt(sellProfit.value) + parseInt(sellCost.value);
					$("#sellPrice").attr("value",numFormat(temp,0));//格式化数据，保留0位小数
				}
			});

			$("#finalPrice").focus(function(){
				//送到价格  = 出厂价格 + 铁汽运费
				if(null != sellPrice && "" != sellPrice && null != tqyf && "" != tqyf) {
					var temp = parseInt(sellPrice.value) + parseInt(tqyf.value);
					$("#finalPrice").attr("value",numFormat(temp,0));//格式化数据，保留0位小数
				}
			});
			
			$("#submitBtn").click(function() {
				
				if(!validateNull(materialOrigin,'原料产地不能为空')) {
					return false;
				}

				if(!validateNull(date,'日期不能为空')) {
					return false;
				}
				
				if(!validateNull(materialPrice,'原料价格不能为空')) {
					return false;
				}else if(!validateNumber(materialPrice,'原料价格必须为数字')) {
					return false;
				}

				if(!validateNull(rateOfOil,'出油率不能为空')) {
					return false;
				}else if(!validateNumber(rateOfOil,'出油率必须为数字')) {
					return false;
				}

				if(!validateNull(materialCost,'原料成本不能为空')) {
					return false;
				}else if(!validateNumber(materialCost,'原料成本必须为数字')) {
					return false;
				}

				if(!validateNull(fcpz,'副产品值不能为空')) {
					return false;
				}else if(!validateNumber(fcpz,'副产品值必须为数字')) {
					return false;
				}

				if(!validateNull(ejyz,'二级油值不能为空')) {
					return false;
				}else if(!validateNumber(ejyz,'二级油值必须为数字')) {
					return false;
				}

				if(!validateNull(ejyJgf,'二级油加工费不能为空')) {
					return false;
				}else if(!validateNumber(ejyJgf,'二级油加工费必须为数字')) {
					return false;
				}

				if(!validateNull(ejjsCost,'二级净水成本不能为空')) {
					return false;
				}else if(!validateNumber(ejjsCost,'二级净水成本必须为数字')) {
					return false;
				}

				if(!validateNull(yjyShl,'一级油损耗率不能为空')) {
					return false;
				}else if(!validateNumber(yjyShl,'一级油损耗率必须为数字')) {
					return false;
				}
				
				
				if(!validateNull(yjyJgf,'一级油加工费不能为空')) {
					return false;
				}else if(!validateNumber(yjyJgf,'一级油加工费必须为数字')) {
					return false;
				}

				if(!validateNull(yjjsCost,'一级净水成本不能为空')) {
					return false;
				}else if(!validateNumber(yjjsCost,'一级净水成本必须为数字')) {
					return false;
				}
				
				if(!validateNull(jlsh,'精炼损耗不能为空')) {
					return false;
				}else if(!validateNumber(jlsh,'精炼损耗必须为数字')) {
					return false;
				}
				
				if(!validateNull(tax,'国地两税不能为空')) {
					return false;
				}else if(!validateNumber(tax,'国地两税必须为数字')) {
					return false;
				}
				
				if(!validateNull(ttbz,'铁桶包装不能为空')) {
					return false;
				}else if(!validateNumber(ttbz,'铁桶包装必须为数字')) {
					return false;
				}
				
				

				if(!validateNull(sellCost,'	销售成本不能为空')) {
					return false;
				}else if(!validateNumber(sellCost,'	销售成本必须为数字')) {
					return false;
				}

				if(!validateNull(sellProfit,'销售利润不能为空')) {
					return false;
				}else if(!validateNumber(sellProfit,'销售利润必须为数字')) {
					return false;
				}

				if(!validateNull(sellPrice,'出厂价格不能为空')) {
					return false;
				}else if(!validateNumber(sellPrice,'出厂价格必须为数字')) {
					return false;
				}

				if(!validateNull(tqyf,'铁汽运费不能为空')) {
					return false;
				}else if(!validateNumber(tqyf,'铁汽运费必须为数字')) {
					return false;
				}

				if(!validateNull(finalPrice,'送到价格不能为空')) {
					return false;
				}else if(!validateNumber(finalPrice,'送到价格必须为数字')) {
					return false;
				}
				

				if(null == selectClientId.value || -1 == selectClientId.value){
					alert("请选择供应单位");
					return false;
				}

				document.forms[0].action = "cost/add.action";
				document.forms[0].submit();
			});

			$("#backBtn").click(function() {
					document.forms[0].action = "cost/listAll.action";
					document.forms[0].submit();
			});
			
		});
	</script>
	</head>
	<%
		
	%>
	<body>
		<center>

			<div>
				<form action="" method="post">
					<!-- 第一行：top -->
					<table width="100%" border="0" align="left" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="25" height="50" background="img/main01.png">
								&nbsp;
							</td>
							<td background="img/main02.png">
								<span id="maintop"></span><span class="mainAction">当前路径：添加产品核算</span>
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
												原料产地：
											</td>
											<td>
												<input type="text" id="materialOrigin" name="costAccounting.materialOrigin"/>
											</td>
											<td>&nbsp;</td>
											<td>日  期：</td>
											<td><input type="text" id="date" name="costAccounting.date"/></td>
											<td></td>
										</tr>
										<tr>
											<td>
												原料价格：
											</td>
											<td>
												<input type="text" id="materialPrice" name="costAccounting.materialPrice" />
											</td>
											<td>&nbsp;</td>
											
											<td>
												原料单价： <!-- 原料单价 = 原料价格 / 2000 -->
											</td>
											<td>
												<input type="text" id="materialUnitPrice" name="costAccounting.materialUnitPrice" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												出油率：
											</td>
											<td>
												<input type="text" id="rateOfOil" name="costAccounting.rateOfOil" />
											</td>
											<td>&nbsp;</td>
											
											<td>
												原料成本： <!-- 原料成本= 原料价格 / 出油率 -->
											</td>
											<td>
												<input type="text" id="materialCost" name="costAccounting.materialCost" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												副产品值：
											</td>
											<td>
												<input type="text" id="fcpz" name="costAccounting.fcpz" />
											</td>
											<td>&nbsp;</td>
											
											<td>
												二级油值： <!-- 二级油值= 原料成本 - 副产品值 -->
											</td>
											<td>
												<input type="text" id="ejyz" name="costAccounting.ejyz" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												二级油加工费：
											</td>
											<td>
												<input type="text" id="ejyJgf" name="costAccounting.ejyJgf" />
											</td>
											<td>&nbsp;</td>
											
											<td>
												二级净水成本： <!-- 二级净水成本(不带包装)= 二级油值 + 二级油加工费 -->
											</td>
											<td>
												<input type="text" id="ejjsCost" name="costAccounting.ejjsCost" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												一级油损耗率：
											</td>
											<td>
												<input type="text" id="yjyShl" name="costAccounting.yjyShl" />
											</td>
											<td>&nbsp;</td>
											
											<td>
												精炼损耗：
											</td>
											<td>
												<input type="text" id="jlsh" name="costAccounting.jlsh" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												一级油加工费：
											</td>
											<td>
												<input type="text" id="yjyJgf" name="costAccounting.yjyJgf" />
											</td>
											<td>&nbsp;</td>
											
											<td>
												一级净水成本： <!--一级净水成本 = 二级净水成本 + 一级油加工费 + 精炼损耗  -->
											</td>
											<td>
												<input type="text" id="yjjsCost" name="costAccounting.yjjsCost" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												国地两税：
											</td>
											<td>
												<input type="text" id="tax" name="costAccounting.tax" />
											</td>
											<td>&nbsp;</td>
											
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												铁桶包装：
											</td>
											<td>
												<input type="text" id="ttbz" name="costAccounting.ttbz" />
											</td>
											<td>&nbsp;</td>
											
											<td>
												销售成本： <!-- 销售成本 = 一级净水成本 + 国地两税 + 铁桶包装 -->
											</td>
											<td>
												<input type="text" id="sellCost" name="costAccounting.sellCost" />
											</td>
											<td>&nbsp;</td>
										</tr>
										
										<tr>
											<td>
												销售利润：<!-- 销售利润 = 出厂价格 - 销售成本  -->
											</td>
											<td>
												<input type="text" id="sellProfit" name="costAccounting.sellProfit" />
											</td>
											<td>&nbsp;</td>
											<td>
												出厂价格：
											</td>
											<td>
												<input type="text" id="sellPrice" name="costAccounting.sellPrice" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												铁汽运费：
											</td>
											<td>
												<input type="text" id="tqyf" name="costAccounting.tqyf" />
											</td>
											<td>&nbsp;</td>
											
											<td>
												送到价格：
											</td>
											<td>
												<input type="text" id="finalPrice" name="costAccounting.finalPrice" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												供应单位：
											</td>
											<td>
												<s:select list="#request.clientMap" id="selectClientId"
														name="selectClientId" headerKey="-1" headerValue="--请选择供应单位--" value="#request.selectClientId"/>
											</td>
											<td>&nbsp;</td>
											
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td colspan="6" align="center">
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
