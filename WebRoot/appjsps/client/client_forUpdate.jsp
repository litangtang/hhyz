<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
		<title>更新客户信息</title>

		<link href="css/elson.css" rel="stylesheet" type="text/css" />
		<link href="css/elsonLink.css" rel="stylesheet" type="text/css" />
		<link href="css/elsonTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="js/tableMain.js"></script>
		<script type="text/javascript" src="js/validForm.js"></script>
		<script language="javascript">
		$(function() {
			$("table.maintable tr:nth-child(odd)").addClass("ymaintext");
			$("table.maintable tr:nth-child(even)").addClass("zmaintext");
			$("table.maintable tr:not(:last) td:first-child").attr("align","right");
			
			$("#submitBtn").click(function() {
				var name = document.getElementById("name");
				if(!validateNull(name,'客户名称不能为空')) {
					return false;
				}

				var companyName = document.getElementById("companyName");
				if(!validateNull(companyName,'公司名称不能为空')) {
					return false;
				}

				var officePhone = document.getElementById("officePhone");
				if(!validateNull(officePhone,'办公电话不能为空')) {
					return false;
				}else{
					if(!validateNumber(officePhone,'办公电话必须为数字')) {
						return false;
					}
				}

				var mobilePhone = document.getElementById("mobilePhone");
				if(!validateNumber(mobilePhone,'手机号必须为数字')) {
					return false;
				}

				var fax = document.getElementById("fax");
				if(!validateNumber(fax,'传真号必须为数字')) {
					return false;
				}
				
				document.forms[0].action = "client/update.action";
				document.forms[0].submit();
			});

			$("#backBtn").click(function() {
				var from = $("#from").val();
				if("client_1" == from) {
					document.forms[0].action = "client/listAll.action?searchType=1";
					document.forms[0].submit();
				}else if("client_2" == from) {
					document.forms[0].action = "client/listAll.action?searchType=2";
					document.forms[0].submit();
				}else if("client_0" == from) {
					document.forms[0].action = "client/listAll.action?searchType=0";
					document.forms[0].submit();
				}
			});
			
		});
	</script>
  </head>
  <%
  		String from = (String)request.getAttribute("from");
  %>
  <body>
   	<center>

			<div>
				<form action="" method="post">
					<input type="hidden" name="from" id="from" value="<%=from%>"/>
					<input type="hidden" name="clientId" id="clientId" value="<s:property value='client.id'/> "/>
					<!-- 第一行：top -->
					<table width="100%" border="0" align="left" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="25" height="50" background="img/main01.png">
								&nbsp;
							</td>
							<td background="img/main02.png">
								<span id="maintop"></span><span class="mainAction">当前路径：</span>
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
											<span class="maintoptext">添加客户</span><span
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
								<!-- 增加表格主体 -->
								<table class="maintable" width="600" border="0" align="center" cellpadding="0" cellspacing="0">
										<tr>
											<td>
												客户类型：
											</td>
											<td>
												<s:select name="client.type" id="type" list="#{'1':'原料客户','2':'销售客户'}"  label="客户类型："/>
											</td>
											<td><span id="star">*</span></td>
										</tr>
										<tr>
											<td>
												客户名称：
											</td>
											<td>
												<input type="text" id="name" name="client.name" value="<s:property value='client.name'/>" />
											</td>
											<td><span id="star">*</span></td>
										</tr>
										<tr>
											<td>
												公司名称：
											</td>
											<td>
												<input type="text" id="companyName" name="client.companyName" value="<s:property value='client.companyName'/>" />
											</td>
											<td><span id="star">*</span></td>
										</tr>
										
										<tr>
											<td>
												公司地址：
											</td>
											<td>
												<input type="text" id="companyAddr" name="client.companyAddr" value="<s:property value='client.companyAddr'/>" />
											</td>
											<td>&nbsp;</td>
										</tr>
										
										<tr>
											<td>
												办公电话：
											</td>
											<td>
												<input type="text" id="officePhone" name="client.officePhone" value="<s:property value='client.officePhone'/>" />
											</td>
											<td><span id="star">*</span></td>
										</tr>
										
										<tr>
											<td>
												手机：
											</td>
											<td>
												<input type="text" id="mobilePhone" name="client.mobilePhone" value="<s:property value='client.mobilePhone'/>" />
											</td>
											<td>&nbsp;</td>
										</tr>
										
										<tr>
											<td>
												传真：
											</td>
											<td>
												<input type="text" id="fax" name="client.fax" value="<s:property value='client.fax'/>" />
											</td>
											<td>&nbsp;</td>
										</tr>
										
										<tr>
											<td>
												备注：
											</td>
											<td>
												<input type="text" id="remark" name="client.remark" value="<s:property value='client.remark'/>" />
											</td>
											<td>&nbsp;</td>
										</tr>
										
										<tr>
											<td colspan="2" align="center">
												<input type="button" value="修改" id="submitBtn" />
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
