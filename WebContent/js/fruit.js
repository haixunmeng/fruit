function loadVerifyCode(){
	
	res = post('/fruit/user/getPassCode.do');
	
	if('000004' == res.code){
		$('#sessionId').val(res.sessionId);
		$('#passCode').attr('src', 'data:image/jpg;base64, ' + res.passCode);
	}else{
		alert(res.msg);
	}
}

function post(url, data){
	
	var response;
	
	$.ajax({
		url : url,
		async : false,
		type : 'post',
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify(data),
		success : function(res){
			response = res;
		}
	});
	
	return response;
}