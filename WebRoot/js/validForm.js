//valid form area is not null
function validateNull(obj,alt){
	var text = trim(obj.value);
	if(text == ""){
		alert(alt);
		obj.focus();
		return false;
	}
	return true;
}

function isInteger(obj, alt) {
	reg = /^[-+]?\d+$/;
	if (!reg.test(obj.value)) {
		alert(alt);
		obj.focus();
		return false;
	}
	return true;
}

//get string length
function getStringLength(str) {
	if(str == null || str==""){
		return 0;
	}
	var l = 0,i=0;
	var sourcestr = new String(str);
	while(i < sourcestr.length) {
		charCode = sourcestr.charCodeAt(i);
		if (charCode > 255 || charCode < 0) {
			l = l + 2;
		} else {
			l = l + 1;
		}
		i = i+1;
	}
	return l;
}

//valid form area's value length
function validateLength(obj,alt,maxLength){
	if(getStringLength(obj.value) > maxLength){
		alert(alt);
		obj.focus();
		return false;
	}
	return true;
}

function validateTelNumber(comp, alt) {
	var text = trim(comp.value);
	if (text == "") {
		return true;
	}
	var myReg = /^[-0-9]{0,}$/;
	if (myReg.test(comp.value) == false) {
		alert(alt);
		comp.focus();
		return false;
	}
	return true;
}

//批量校验textarea的长度
function validateTextarea(maxLength) {
	var tas = document.getElementsByTagName("textarea");
	var i=0,j = 0;
	while(i < tas.length){
		if(null != tas[i].value && "" != tas[i].value) {
			if(!validateLength(tas[i],"内容太长",maxLength))
			{
				j = j+1;
				break;
			}
		}
		i= i+1;
	}
	if(j > 0) {
		return false;
	}

	return true;
}

//valid number
function validateNumber(comp, alt) {
	var text = trim(comp.value);
	if (text == "") {
		return true;
	}
	
	var myReg = /^\d+\.?\d{0,4}$/; //IE支持，火狐不支持
	if (myReg.test(comp.value) == false) {
		alert(alt);
		comp.focus();
		return false;
	}
	return true;
}

//valid password
function validatePassword(comp, alt) {
	var text = trim(comp.value);
	if (text == "") {
		return true;
	}
	var myReg = /^[a-z0-9A-Z]{0,}$/;
	if (myReg.test(comp.value) == false) {
		alert(alt);
		comp.focus();
		return false;
	}
	return true;
}


function validateEmail(email, alt, separator) {
	var mail = trim(email.value);
	if (mail == "") {
		return true;
	}
	var em;
	var myReg = /^[_a-z0-9]+@([_a-z0-9]+\.)+[a-z0-9]{1,}$/;
	if (separator == null) {
		if (myReg.test(email.value) == false) {
			alert(alt);
			email.focus();
			return false;
		}
	} else {
		em = email.value.split(separator);
		for (i = 0; i < em.length; i++) {
			em[i] = em[i].trim();
			if (em[i].length > 0 && myReg.test(em[i]) == false) {
				alert(alt);
				email.focus();
				return false;
			}
		}
	}
	return true;
}



function trim(str) {
	if (str == null) {
		return "";
	}
	if (str.length == 0) {
		return "";
	}
	var i = 0, j = str.length - 1, c;
	for (; i < str.length; i++) {
		c = str.charAt(i);
		if (c != " ") {
			break;
		}
	}
	for (; j > -1; j--) {
		c = str.charAt(j);
		if (c != " ") {
			break;
		}
	}
	if (i > j) {
		return "";
	}
	return str.substring(i, j + 1);
}


function clearText(){
	var col = document.getElementsByTagName("INPUT");
	for(var i = 0;i < col.length;i++){
		if(col[i].type != 'hidden' && col[i].type != 'button' && col[i].type != 'submit'){
			col[i].value="";
		}		
	}
}

//格式化金额，保留两位小数
function numFormat(money,decimalDigits) {
	var moneyFormat = new Number(money);
	return moneyFormat.toFixed(decimalDigits);
}

//只能选择一条记录
function getSingleSelectCheckbox(name) {
	//有多少个checkbox
	var rows = document.getElementsByName(name);
	var selectCount = 0;
	for(i=0; i<rows.length; i++) {
		if(rows[i].checked) {
			selectCount = selectCount + 1;
		}
	}
	if(selectCount == 0) {
		alert("请选择一条记录");
		return false;
	}else if(selectCount > 1) {
		alert("只能选择一条记录");
		return false;
	}
	
	return true;
}

//至少选择一条记录
function getSelectChectboxAtLeast(name) {
	//有多少个checkbox
	var rows = document.getElementsByName(name);
	var selectCount = 0;
	for(i=0; i<rows.length; i++) {
		if(rows[i].checked) {
			selectCount = selectCount + 1;
		}
	}
	if(selectCount == 0) {
		alert("至少选择一条记录");
		return false;
	}
	
	return true;
}

