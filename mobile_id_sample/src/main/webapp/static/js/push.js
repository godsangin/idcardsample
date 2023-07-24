$(function() {
	// PUSH 요청 버튼 클릭시
	$('#pushReqBtn').click(function() {
		fnPushReq();
	});
	
	// 초기화 버튼 클릭시
	$('#resetBtn').click(function() {
		$('#form')[0].reset();
		
		fnResetTrxsts();
	});
	
	// 응답 상태 확인 버튼 클릭시
	$('#trxstsBtn').click(function() {
		fnGetTrxsts(TRX_CODE);
	});
});

// PUSH 요청
function fnPushReq() {
	let cmd = $('#cmd').val();
	let mode = $('#mode').val();
	let svcCode = $('#svcCode').val();
	let name = $('#name').val();
	let birth = $('#birth').val();
	let telno = $('#telno').val();
	
	let errMsg = new StringBuffer();
	
	if(svcCode.trim() == '') {
		errMsg.append('서비스 코드를 입력해주세요.');
	}
	
	if(name.trim() == '') {
		errMsg.append('이름을 입력해주세요.');
	}
	
	if(birth.trim() == '') {
		errMsg.append('생년월일을 입력해주세요.');
	}
	
	if(telno.trim() == '') {
		errMsg.append('전화번호를 입력해주세요.');
	}
	
	if(errMsg.toString() != '') {
		alert(errMsg.toString('\n'));
		
		return;
	}

	let param = {
		  url: contextPath + '/push/start'
		, dataType: 'json'
		, data: JSON.stringify({
			  'cmd': cmd
			, 'mode': mode
			, 'svcCode': svcCode
			, 'name': name
			, 'birth': birth
			, 'telno': telno
		})
		, contentType: "application/json; charset=utf-8"
		, type: 'POST'
		, success: function(data) {
			let resultData = JSON.parse(Base64.decode(data.data));
			
			$('#pushResultTag').val(JSON.stringify(resultData));
			
			if(data.result && (resultData.errmsg || '') == '') {
				TRX_CODE = JSON.parse(Base64.decode(resultData.m200Base64)).trxcode;
			}
			
			fnGetTrxsts();
		}
		, error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
		}
	};
	
	$.ajax(param);
}
