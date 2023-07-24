$(function() {
	// 샘플입력 버튼 클릭시
	$('#sampleInputBtn').click(function() {
		$('#svcCode').val('example.1');
		$('#branchName').val('논현역점');
		$('#deviceId').val('123456789');
	});
	
	// QR 정보요청 버튼 클릭시
	$('#qrInfoReqBtn').click(function() {
		fnQrInfoReq();
	});
	
	// 초기화
	$('#resetBtn').click(function() {
		$('#form')[0].reset();
		
		fnResetTrxsts();
		
		$('#qrCodeArea').text('QR 코드 영역');
		
		TRX_CODE = '';
	});
	
	// 거래상태 조회
	$('#trxstsBtn').click(function() {
		fnGetTrxsts(TRX_CODE);
	});

});

// QR 정보요청
function fnQrInfoReq() {
	let cmd = $('#cmd').val();
	let mode = $('#mode').val();
	let svcCode = $('#svcCode').val();
	let branchName = $('#branchName').val();
	let deviceId = $('#deviceId').val();
	
	let errMsg = new StringBuffer();

	if(mode.trim() == '') {
		errMsg.append('Mode를 입력해주세요.');
	}
	
	if(svcCode.trim() == '') {
		errMsg.append('서비스 코드를 입력해주세요.');
	}
	
	if(branchName.trim() == '') {
		errMsg.append('지점명을 입력해주세요.');
	}
	
	if(deviceId.trim() == '') {
		errMsg.append('기기ID를 입력해주세요.');
	}
	
	if(errMsg.toString() != '') {
		alert(errMsg.toString('\n'));
		return;
	}

	TRX_CODE = '';
	
	let param = {
		  url: contextPath + '/qrmpm/start'
		, dataType: 'json'
		, data: JSON.stringify({
			  'cmd': cmd
			, 'mode': mode
			, 'svcCode': svcCode
			, 'branchName': branchName
			, 'deviceId': deviceId
		})
		, contentType: "application/json; charset=utf-8"
		, type: 'POST'
		, success: function(data) {
			let resultData = JSON.parse(Base64.decode(data.data));
			
			if(data.result && (resultData.errmsg || '') == '') {
				$('#qrCodeArea').empty();
				
				TRX_CODE = JSON.parse(Base64.decode(resultData.m200Base64)).trxcode;
				
				let qrCodeArea = document.getElementById("qrCodeArea");
				let width = qrCodeArea.clientWidth;
				let size = width > 300 ? 300:width;
				
				new QRCode(qrCodeArea, {
					  width: size
					, height: size
					, text: resultData.m200Base64
				});
				
				fnGetTrxsts();
			} else {
				alert(data.errmsg);
			}
		}
		, error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
		}
	};
	
	$.ajax(param);
}
