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

function add(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
}

function sub(arg1, arg2) {
	return add(arg1, -arg2);
}

function mul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length;
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", ""))
			/ Math.pow(10, m);
}

function div(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}