let CAM_FLG = false;

$(function() {
	// 샘플입력 버튼 클릭시
	$('#sampleInputBtn').click(function() {
		$('#svcCode').val('example.1');
		$('#branchName').val('논현역점');
		$('#deviceId').val('123456789');
	});
	
	// 초기화 버튼 클릭시
	$('#resetBtn').click(function() {
		$('#form')[0].reset();
		
		fnResetTrxsts();
		
		camConnect();
		
		CAM_FLG = true;
	});

	// 거래상태 조회
	$('#trxstsBtn').click(function() {
		fnGetTrxsts();
	});
	
	$('#resetBtn').trigger('click');
});

function camConnect() {
	let video = document.createElement("video");
	let canvasElement = document.getElementById("canvas");
	let canvas = canvasElement.getContext("2d");
	let loadingMessage = document.getElementById("loadingMessage");
	let outputContainer = document.getElementById("output");
	let outputMessage = document.getElementById("outputMessage");
	let outputData = document.getElementById("outputData");
	let outputData2 = document.getElementById("outputData2");
	
	let qrCodeDivWidth = document.getElementById("qrCodeDiv").clientWidth;
	
	function drawLine(begin, end, color) {
		canvas.beginPath();
		canvas.moveTo(begin.x, begin.y);
		canvas.lineTo(end.x, end.y);
		canvas.lineWidth = 4;
		canvas.strokeStyle = color;
		canvas.stroke();
	}
	
	// 카메라 사용시
	navigator.mediaDevices.getUserMedia({ video: { facingMode: "environment" } }).then(function(stream) {
		video.srcObject = stream;
		video.setAttribute("playsinline", true);      // iOS 사용시 전체 화면을 사용하지 않음을 전달
		video.play();
		
		requestAnimationFrame(tick);
	});
	
	function tick() {
		if (CAM_FLG) {
			loadingMessage.innerText = "⌛ 스캔 기능을 활성화 중입니다."
			
			if (video.readyState === video.HAVE_ENOUGH_DATA) {
				loadingMessage.hidden = true;
				canvasElement.hidden = false;
				outputContainer.hidden = false;
				
				let width, height;
				
				if (qrCodeDivWidth > video.videoWidth) {
					width = video.videoWidth;
					height = video.videoHeight;
				} else {
					width = qrCodeDivWidth;
					height = qrCodeDivWidth / video.videoWidth * video.videoHeight;
				}
				
				width = width - 20;
				height = height - 20;
				
				// 읽어들이는 비디오 화면의 크기
				canvasElement.width = width;
				canvasElement.height = height;
				canvasElement.style = 'width: ' + width + 'px; height: ' + height + 'px';
				canvas.drawImage(video, 0, 0, canvasElement.width, canvasElement.height);
				
				let imageData = canvas.getImageData(0, 0, canvasElement.width, canvasElement.height);
				let code = jsQR(imageData.data, imageData.width, imageData.height, {
					inversionAttempts : "dontInvert",
				});
			
				// QR코드 인식에 성공한 경우
				if(code) {
					// 인식한 QR코드의 영역을 감싸는 사용자에게 보여지는 테두리 생성
					drawLine(code.location.topLeftCorner, code.location.topRightCorner, "#FF0000");
					drawLine(code.location.topRightCorner, code.location.bottomRightCorner, "#FF0000");
					drawLine(code.location.bottomRightCorner, code.location.bottomLeftCorner, "#FF0000");
					drawLine(code.location.bottomLeftCorner, code.location.topLeftCorner, "#FF0000");
					
					outputMessage.hidden = true;
					outputData.parentElement.hidden = false;
					// QR코드 메시지 출력
					outputData.innerHTML = code.data ? 'Base64 Data<br><br>' + code.data + '<br><br>':'';
					outputData2.innerHTML = code.data ? 'Base64 Decode<br><br>' + atob(code.data):''; //base64에서 디코드된 문자열
					
					TRX_CODE = JSON.parse(atob(code.data)).trxcode;
					
					fnQrInfoRelay(code.data); // QR 정보전달
					
					CAM_FLG = false;
				}
				// QR코드 인식에 실패한 경우
				else {
					outputMessage.hidden = false;
					outputData.parentElement.hidden = true;
				}
			}
			
			requestAnimationFrame(tick);
		}
	}
}

// QR 정보전달
function fnQrInfoRelay(qrData) {
	if((qrData || '') == '') {
		alert('QR 코드를 스캔한 데이터가 없습니다.');
		
		return;
	}
	
	let cmd = $('#cmd').val();
	let svcCode = $('#svcCode').val();
	let branchName = $('#branchName').val();
	let deviceId = $('#deviceId').val();
	let m120Base64 = qrData;
	
	let errMsg = new StringBuffer();

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
	
	let param = {
		  url: contextPath + '/qrcpm/start'
		, dataType: 'json'
		, data: JSON.stringify({
			  'cmd': cmd
			, 'svcCode': svcCode
			, 'branchName': branchName
			, 'deviceId': deviceId
			, 'm120Base64': m120Base64
		})
		, contentType: 'application/json; charset=utf-8'
		, type: 'POST'
		, success: function(data) {
			let resultData = JSON.parse(Base64.decode(data.data));
			
			$('#qrResultTag').val(JSON.stringify(resultData));
			
			if(data.result && (resultData.errmsg || '') == '') {
				TRX_CODE = JSON.parse(Base64.decode(resultData.m120Base64)).trxcode;
			}
			
			fnGetTrxsts();
		}
		, error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
		}
	};
	
	$.ajax(param);
}
