/**
 * VP 복호화 & CI 조회
 * vpdata.js
 */

$(function() {
	// VP 복호화 요청 버튼 클릭시
	$('#reqVpDataBtn').click(function() {
		fnReq('/mip/vpdata');
	});
	
	// CI 조회 요청 버튼 클릭시
	$('#reqCiBtn').click(function() {
		fnReq('/mip/ci');
	});
	
	// 입력조건 초기화 버튼 클릭시
	$('#resetBtn').click(function() {
		$('#vp').val('');
		$('#resultTag').val('');
	});

});

// 재검증
function fnReq(url) {
	let vp = $('#vp').val();
	
	let errMsg = new StringBuffer();
	
	if ($('#vp').val().trim() == '') {
		errMsg.append('VP정보를 입력해주세요.');
	}
	
	if (errMsg.toString() != '') {
		alert(errMsg.toString('\n'));
		
		return;
	}

	let param = {
		  url: contextPath + url
		, dataType: 'json'
		, data: JSON.stringify({
			'data': Base64.encode(JSON.stringify({'vp': vp}))
		})
		, contentType: "application/json; charset=utf-8"
		, type: 'POST'
		, success: function(data) {
			console.log(data);
			
			if((data.errorMsg || '') == '') {
				let msg = '';
				
				msg = Base64.decode(data.data);
				
				$('#resultTag').text(msg);
			} else {
				alert(data.errorMsg);
			}
		}
		, error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
		}
	};
	
	$.ajax(param);
}
