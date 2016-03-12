$(function(){
	$.ajax({
		url : '/fruit/user/getPassCode.do',
		type : 'post',
		dataType : 'json',
		contentType : 'application/json',
		success : function(res){
			if('000004' == res.code){
				$('#sessionId').val(res.sessionId);
				$('#passCode').attr('src', 'data:image/jpg;base64, ' + res.passCode);
			}
		}
	});
	
	var otxt=document.getElementById("passCodeInput");
	if(document.all){
		otxt.onpropertychange=function(){
			checkPassCode();
		}
	}else{
		otxt.oninput=function(){
			checkPassCode();
		}
	}
});

function checkPassCode(){
	
	var passCode = $('#passCodeInput').val();
	if(passCode.match(/^\d{5}$/)){
		var data = {
			sessionId : $('#sessionId').val(),
			passCode : passCode
		}
		
		$.ajax({
			url : '/fruit/user/checkPassCode.do',
			type : 'post',
			dataType : 'json',
			contentType : 'json',
			data : JSON.stringify(data),
			success : function(res){
				if('000004' != res.code){
					alert(res.msg);
				}
			}
		});
	}
}