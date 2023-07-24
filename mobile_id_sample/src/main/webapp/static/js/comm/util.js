/**
 * Util
 * util.js
 */

$(function() {
	// Image to hex 변환 버튼 클릭시
	$('#imageToHexBtn').click(function() {
		fnImageToHex();
	});

	// Hex to image 변환 버튼 클릭시
	$('#hexToImageBtn').click(function() {
		fnHexToImage();
	});
	
	// Image to base64 변환 버튼 클릭시
	$('#imageToBase64Btn').click(function() {
		fnImageToBase64();
	});

	// Base64 to image 변환 버튼 클릭시
	$('#base64ToImageBtn').click(function() {
		fnBase64ToImage();
	});
	
	// Json to base64 변환 버튼 클릭시
	$('#jsonToBase64Btn').click(function() {
		fnJsonToBase64();
	});

	// Base64 to json 변환 버튼 클릭시
	$('#base64ToJsonBtn').click(function() {
		fnBase64ToJson();
	});
});

// 변환
function fnImageToHex() {
	let errMsg = new StringBuffer();
	let files = $('#imageToHexFile')[0].files;
	
	if (files.length == 0) {
		errMsg.append('파일을 선택해주세요.');
	}
	
	if (errMsg.toString() != '') {
		alert(errMsg.toString('\n'));
		
		return;
	}
	
	let reader = new FileReader();
	
	reader.readAsArrayBuffer(files[0]);
	
	reader.onloadend = (e) => {
		if (e.target.readyState == FileReader.DONE) {
			let b = e.target.result;
			let u = new Uint8Array(b);
			let hs = Array.from(u, function(a) {
				return ('0' + (a & 0xff).toString(16)).slice(-2);
			}).join('');
			
			$('#hexString1').text(hs);
			$('#hexString2').text(hs);
		}
	}
}

// 변환
function fnHexToImage() {
	let errMsg = new StringBuffer();
	
	if ($('#hexString2').val() == '') {
		errMsg.append('파일을 선택해주세요.');
	}
	
	if (errMsg.toString() != '') {
		alert(errMsg.toString('\n'));
		
		return;
	}
	
	let hex = $('#hexString2').val().replace(/[^A-Fa-f0-9]/g, '');
	
	if (hex.length % 2) {
		alert('자리수가 맞지 않습니다.');
		
		return;
	}
	
	let b = new Array();
	
	for (let i = 0; i < hex.length / 2; i++) {
		let h = hex.substr(i * 2, 2);
		
		b[i] = parseInt(h, 16);
	}
	
	let ba = new Uint8Array(b);
	
	$('#hexImageFile').css('display', 'block');
	$('#hexImageFile').attr('src', URL.createObjectURL(new Blob([ba], {'type': 'application/octet-stream'})));
}

// Image to base64 변환
function fnImageToBase64() {
	let errMsg = new StringBuffer();
	let files = $('#imageToBase64File')[0].files;
	
	if (files.length == 0) {
		errMsg.append('파일을 선택해주세요.');
	}
	
	if (errMsg.toString() != '') {
		alert(errMsg.toString('\n'));
		
		return;
	}
	
	let reader = new FileReader();
	
	reader.readAsDataURL(files[0]);
	
	reader.onload = () => {
		let base64 = reader.result.replace(/^data.*\,/, '').replace(/\+/g, '-').replace(/\//g, '_');
		
		$('#base64String1').text(base64);
		$('#base64String2').text(base64);
	}
}

// Base64 to image 변환
function fnBase64ToImage() {
	let errMsg = new StringBuffer();
	
	if ($('#base64String2').val() == '') {
		errMsg.append('데이터를 입력해주세요.');
	}
	
	if (errMsg.toString() != '') {
		alert(errMsg.toString('\n'));
		
		return;
	}
	
	let base64 = $('#base64String2').val().replace(/\-/g, '+').replace(/\_/g, '/');
	
	$('#base64ImageFile').css('display', 'block');
	$('#base64ImageFile').attr('src', 'data:application/octet-stream;base64,' + base64);
}

// Json to base64 변환
function fnJsonToBase64() {
	let errMsg = new StringBuffer();
	
	if ($('#jsonToBase64String1').val() == '') {
		errMsg.append('데이터를 입력해주세요.');
	}
	
	if (errMsg.toString() != '') {
		alert(errMsg.toString('\n'));
		
		return;
	}
	
	let base64 = btoa(unescape(encodeURIComponent($('#jsonToBase64String1').val()))).replace(/^data.*\,/, '').replace(/\+/g, '-').replace(/\//g, '_');
	
	$('#base64ToJsonString1').val(base64);
	$('#base64ToJsonString2').val(base64);
}

// Base64 to json 변환
function fnBase64ToJson() {
	let errMsg = new StringBuffer();
	
	if ($('#base64ToJsonString2').val() == '') {
		errMsg.append('데이터를 입력해주세요.');
	}
	
	if (errMsg.toString() != '') {
		alert(errMsg.toString('\n'));
		
		return;
	}
	
	let base64 = decodeURIComponent(escape(atob($('#base64ToJsonString2').val().replace(/\-/g, '+').replace(/\_/g, '/'))));
	
	$('#jsonToBase64String2').val(base64);
}
