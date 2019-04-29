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
		<title>增加出品率</title>

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

			
			$("#submitBtn").click(function() {
				var numOfMachines = document.getElementById("numOfMachines");
				if(numOfMachines.value == -1) {
					alert("请选择机器个数");
					return false;
				}
				
				var date = document.getElementById("date");
				if(!validateNull(date,'日期不能为空')) {
					return false;
				}

				var oilRate = document.getElementById("oilRate");
				if(!validateNull(oilRate,'比重不能为空')) {
					return false;
				}else if(!validateNumber(oilRate,'比重必须为数字')) {
					return false;
				}
				
				
				var capacity = document.getElementById("capacity");
				if(!validateNull(capacity,'容积不能为空')) {
					return false;
				}else if(!validateNumber(capacity,'容积必须为数字')) {
					return false;
				}

				var packagesMultiply = document.getElementById("packagesMultiply");
				if(!validateNull(packagesMultiply,'饼规格不能为空')) {
					return false;
				}else if(!validateNumber(packagesMultiply,'饼规格必须为数字')) {
					return false;
				}

				var numOfPackages = document.getElementById("numOfPackages");
				if(!validateNull(numOfPackages,'饼件数不能为空')) {
					return false;
				}else if(!validateNumber(numOfPackages,'饼件数必须为数字')) {
					return false;
				}

				document.forms[0].action = "rate/add.action";
				document.forms[0].submit();
			});

			$("#backBtn").click(function() {
					document.forms[0].action = "rate/listAll.action";
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
								<span id="maintop"></span><span class="mainAction">当前路径：增加一条出品率</span>
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
												机器数：
											</td>
											<td>
												<s:select list="#request.NUM_OF_MACHINES_MAP" id="numOfMachines"
													name="rateOfProduce.numOfMachines" headerKey="-1" headerValue="--请选择机器个数--"/>
											</td>
											<td><span id="star">*</span></td>
										</tr>
										<tr>
											<td>
												日期：
											</td>
											<td>
												<input type="text" id="date" name="rateOfProduce.date"
													class="ui-widget-content ui-corner-all" />
											</td>
											<td><span id="star">*</span></td>
										</tr>
										<tr>
											<td>
												比重：
											</td>
											<td>
												<input type="text" id="oilRate" name="rateOfProduce.oilRate" class="ui-widget-content ui-corner-all"/>
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												容积：
											</td>
											<td>
												<input type="text" id="capacity" name="rateOfProduce.capacity" class="ui-widget-content ui-corner-all"/>&nbsp;&nbsp;L
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>
												饼规格：
											</td>
											<td>
												<input type="text" id="packagesMultiply" name="rateOfProduce.packagesMultiply" class="ui-widget-content ui-corner-all"/>&nbsp;&nbsp;kg
											</td>
											<td><span id="star">*</span></td>
										</tr>
										
										<tr>
											<td>
												饼件数：
											</td>
											<td>
												<input type="text" id="numOfPackages" name="rateOfProduce.numOfPackages" class="ui-widget-content ui-corner-all"/>&nbsp;&nbsp;件
											</td>
											<td><span id="star">*</span></td>
										</tr>
										<tr>
											<td colspan="3" align="center">
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
