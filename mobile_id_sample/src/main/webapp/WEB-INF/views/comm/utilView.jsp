<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<title>SP 테스트 화면 - Util</title>
		
		<%@include file="../comm/header.jsp" %>
		
		<style type="text/css">
			input[type="file"] {
				display: block;
			}
			
			img {
				display: none;
			}
		</style>
		
		<script src="<c:url value='/static/js/comm/util.js' />"></script>
	</head>
	
	<body>
		<div class="wrap">
	    	<%@include file="../comm/menu.jsp" %>

			<form id="imageToHexForm" name="imageToHexForm">
				<div class="box-group">
					<h1 class="box-tit">Image to hex</h1>
					
					<p>UI 설명: 대상 데이터를 입력한 후 변환을 실행 한다.</p>
					
					<div class="box-group">
						<button type="button" class="btn" id="imageToHexBtn">변환</button>
					</div>
					
					<div class="box-group">
						<div class="box-line">
							<input type="file" id="imageToHexFile" name="imageToHexFile" />
						</div>
						
						<div class="box-line">
							<label for="hexString1">Hex</label>
							<textarea id="hexString1" name="hexString1"></textarea>
						</div>
					</div>
				</div>
			</form>
			
			<form id="hexToImageForm" name="hexToImageForm">
				<div class="box-group">
					<h1 class="box-tit">Hex to image</h1>
					
					<p>UI 설명: Hex string을 입력 후 변환을 실행 한다.</p>
					
					<div class="box-group">
						<button type="button" class="btn" id="hexToImageBtn">변환</button>
					</div>
					
					<div class="box-group">
						<div class="box-line">
							<label for="hexString2">Hex string</label>
							<textarea id="hexString2" name="hexString2"></textarea>
						</div>
						
						<div class="box-line">
							<label for="hexImageFile">Image</label>
							<img id="hexImageFile" alt="Hex To Image">
						</div>
					</div>
				</div>
			</form>
			
			<form id="imageToBase64Form" name="imageToBase64Form">
				<div class="box-group">
					<h1 class="box-tit">Image to base64</h1>
					
					<p>UI 설명: 이미지 파일을 선택한 후 변환을 실행 한다.<br>변환한 base64 string은 application.properties 파일의 app.sp-bi-image-base64 항목에 적용 가능.</p>
					
					<div class="box-group">
						<button type="button" class="btn" id="imageToBase64Btn">변환</button>
					</div>
					
					<div class="box-group">
						<div class="box-line">
							<input type="file" id="imageToBase64File" name="imageToBase64File" />
						</div>
						
						<div class="box-line">
							<label for="base64String1">Base64 string</label>
							<textarea id="base64String1" name="base64String1"></textarea>
						</div>
					</div>
				</div>
			</form>
			
			<form id="base64ToImageForm" name="base64ToImageForm">
				<div class="box-group">
					<h1 class="box-tit">Base64 to image</h1>
					
					<p>UI 설명: Base64 string을 입력 후 변환을 실행 한다.</p>
					
					<div class="box-group">
						<button type="button" class="btn" id="base64ToImageBtn">변환</button>
					</div>
					
					<div class="box-group">
						<div class="box-line">
							<label for="base64String2">Base64 string</label>
							<textarea id="base64String2" name="base64String2"></textarea>
						</div>
						
						<div class="box-line">
							<label for="base64ImageFile">Image</label>
							<img id="base64ImageFile" alt="Base64 To Image">
						</div>
					</div>
				</div>
			</form>
			
			<form id="jsonToBase64Form" name="jsonToBase64Form">
				<div class="box-group">
					<h1 class="box-tit">Json to base64</h1>
					
					<p>UI 설명: JSON 데이터 입력 후 변환을 실행 한다.</p>
					
					<div class="box-group">
						<button type="button" class="btn" id="jsonToBase64Btn">변환</button>
					</div>
					
					<div class="box-group">
						<div class="box-line">
							<label for="jsonToBase64String1">Json</label>
							<textarea id="jsonToBase64String1" name="jsonToBase64String1"></textarea>
						</div>
						
						<div class="box-line">
							<label for="base64ToJsonString1">Base64 string</label>
							<textarea id="base64ToJsonString1" name="base64ToJsonString1"></textarea>
						</div>
					</div>
				</div>
			</form>
			
			<form id="base64ToJsonForm" name="base64ToJsonForm">
				<div class="box-group">
					<h1 class="box-tit">Base64 to json</h1>
					
					<p>UI 설명: Base64 string을 입력 후 변환을 실행 한다.</p>
					
					<div class="box-group">
						<button type="button" class="btn" id="base64ToJsonBtn">변환</button>
					</div>
					
					<div class="box-group">
						<div class="box-line">
							<label for="base64ToJsonString2">Base64 string</label>
							<textarea id="base64ToJsonString2" name="base64ToJsonString2"></textarea>
						</div>
						
						<div class="box-line">
							<label for="jsonToBase64String2">Json</label>
							<textarea id="jsonToBase64String2" name="jsonToBase64String2"></textarea>
						</div>
					</div>
				</div>
			</form>
		</div>
	</body>
</html>