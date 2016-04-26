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
	var res = post('/fruit/user/logout.do', {token : $.cookie('token')});
	alert(res.msg);
	if(res.code == '000004'){
		window.location.href = '/fruit/view/user/login.html';
	}
}