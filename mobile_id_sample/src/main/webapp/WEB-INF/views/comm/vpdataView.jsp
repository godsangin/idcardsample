<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<title>SP 테스트 화면 - VP 정보 조회</title>
		
		<%@include file="../comm/header.jsp" %>
		
		<script src="<c:url value='/static/js/comm/vpdata.js' />"></script>
	</head>
	
	<body>
		<div class="wrap">
		    <div class="top-wrap">
		    	<%@include file="../comm/menu.jsp" %>

				<form id="form" name="form">
					<div class="box-group">
						<h1 class="box-tit">VP 복호화 & CI 조회</h1>
						
						<p>UI 설명: VP 정보를 입력 한 후 VP복호화 요청 또는 CI조회 요청을 실행 한다.</p>
						
						<div class="box-group">
							<button type="button" class="btn" id="reqVpDataBtn">VP복호화 요청</button>
							<button type="button" class="btn" id="reqCiBtn">CI조회 요청</button>
							<button type="button" class="btn" id="resetBtn">초기화</button>
						</div>
						
						<div class="box-group">
							<div class="box-line">
								<label for="vp">VP 정보</label>
								<textarea id="vp" name="vp"></textarea>
							</div>
						</div>
					</div>
				</form>
				
				<div class="box-group">
					<h1 class="box-tit">요청결과</h1>
					
					<div class="box-line">
						<textarea id="resultTag"></textarea>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>