$(function(){
	login_logout();
	
	$("#logout").bind('click', function(){
		logout();
	});
	$("#login").bind('click', function(){
		login();
	});
	$("#logon").bind('click', function(){
		logon();
	});
	$("#username").bind('click', function(){
		user_center();
	})
});

function loadVerifyCode() {

	res = post('/fruit/user/getPassCode.do');

	if ('000004' == res.code) {
		$('#sessionId').val(res.sessionId);
		$('#passCode').attr('src', 'data:image/jpg;base64, ' + res.passCode);
	} else {
		alert(res.msg);
	}
}

function post(url, data) {

	var response;

	$.ajax({
		url : url,
		async : false,
		type : 'post',
		dataType : 'json',
		contentType : 'application/json;charset=utf-8',
		data : JSON.stringify(data),
		success : function(res) {
			response = res;
		}
	});

	return response;
}

function load_value_unit(elem) {
	$(elem).combobox({
		editable : false,
		valueField : 'id',
		textField : 'value',
		data : [ {
			id : 'kilogram',
			value : '千克'
		}, {
			id : 'number',
			value : '个'
		}, {
			id : 'box',
			value : '箱'
		}, {
			id : 'case',
			value : '框'
		}, {
			id : 'bag',
			value : '袋'
		} ]
	});
}

function load_package_type(elem) {
	$(elem).combobox({
		editable : false,
		valueField : 'id',
		textField : 'value',
		data : [ {
			id : 'in_bulk',
			value : '散装'
		}, {
			id : 'in_box',
			value : '箱装'
		}, {
			id : 'in_case',
			value : '框装'
		}, {
			id : 'in_bag',
			value : '袋装'
		} ]
	});
}

function logout(){
	var res = post("/fruit/user/logout.do", {token : $.cookie("token")});
	if(res != undefined){
		if(res.code == '000004'){
			$.cookie("token", "", {path : "/"});
			console.log($.cookie("token"));
			window.location.href = "/fruit/index.html";
		}else{
			alert(res.msg);
		}
	}
}

function login_logout(){
	var token = $.cookie("token");
	if(token != undefined && token != ""){
		var user = post("/fruit/user/getUserInfo.do", {token:token});
		if(user != undefined){
			if(user.code == '000004'){
				$("#login").html("");
				$("#logon").html("");
				$("#username").html(user.username);
				$("#logout").html(" 注销");
			}else{
				alert(user.msg);
			}
		}
	}else{
		$("#login").html("登录");
		$("#logon").html(" 注册");
		$("#username").html("");
		$("#logout").html("");
	} 
}

function login(){
	window.location.href = "/fruit/view/user/login.html";
}

function logon(){
	window.location.href = "/fruit/view/user/register.html";
}

function user_center(){
	window.location.href = "/fruit/view/user/center.html"
}

function confirm_order(order_id){
	var res = post("/fruit/order/confirmOrder.do", {order_id:order_id,token:$.cookie('token')});
	
	if(res != undefined){
		if(res.code == '000004'){
			if(res.user_type == 'S'){
				window.location.href = "/fruit/view/seller/unproccessed_order_list.html";
			}else if(res.user_type == 'B'){
				window.location.href = "/fruit/view/buyer/unproccessed_order_list.html";
			}
		}
		alert(res.msg);
	}
}

function cancel_order(order_id){
	var res = post("/fruit/order/cancelOrder.do", {order_id:order_id,token:$.cookie('token')});
	
	if(res != undefined){
		if(res.code == '000004'){
			if(res.user_type == 'S'){
				window.location.href = "/fruit/view/seller/unproccessed_order_list.html";
			}else if(res.user_type == 'B'){
				window.location.href = "/fruit/view/buyer/unproccessed_order_list.html";
			}
		}
		alert(res.msg);
	}
}