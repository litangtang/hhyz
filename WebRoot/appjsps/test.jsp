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
		<title>增加客户</title>

		
		<script language="javascript">
			function validate() {

				var officePhone = document.getElementById("officePhone");
				var myReg = /^\d+\.?\d{0,2}$/;
				if (myReg.test(officePhone.value) == false) {
					alert("必须为数字");
					comp.focus();
					return false;
				}
				
				return true;
			}
		
			
		
	</script>
  </head>
  <body>
   	<center>

			<div>
				<form action="top.jsp" method="post">
								<!-- 增加表格主体 -->
								<table class="maintable" width="600" border="0" align="center" cellpadding="0" cellspacing="0">
										
										<tr>
											<td>
												办公电话：
											</td>
											<td>
												<input type="text" id="officePhone" name="client.officePhone" />
											</td>
											<td><span id="star">*</span></td>
										</tr>
										
										<tr>
											<td colspan="2" align="center">
												<input type="submit" value="增加" id="submitBtn" onclick="return validate()"/>
												<input type="button" value="返回" id="backBtn" />
											</td>
										</tr>
								</table>
					<s:token />
				</form>
			</div>
		</center>
  </body>
</html>
