function loadVerifyCode(){
	
	res = post('/fruit/user/getPassCode.do');
	
	if('000004' == res.code){
		$('#sessionId').val(res.sessionId);
		$('#passCode').attr('src', 'data:image/jpg;base64, ' + res.passCode);
	}
}

function bindVerifyCodeInput(){
	var otxt=document.getElementById("passCodeInput");
	if(document.all){
		otxt.onpropertychange=function(){
			checkVerifyCode();
		}
	}else{
		otxt.oninput=function(){
			checkVerifyCode();
		}
	}
}

function checkVerifyCode(){
	
	var passCode = $('#passCodeInput').val();
	if(passCode.match(/^\d{5}$/)){
		var data = {
			sessionId : $('#sessionId').val(),
			passCode : passCode
		}
		
		res = post('/fruit/user/checkPassCode.do', data);
		if('000004' != res.code){
			alert(res.msg);
		}
	}
}

function post(url, data){
	
	var response;
	
	$.ajax({
		url : url,
		async : false,
		type : 'post',
		dataType : 'json',
		contentType : 'json',
		data : JSON.stringify(data),
		success : function(res){
			response = res;
		}
	});
	
	return response;
}