<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<title>SP 테스트 화면 - Push</title>
		
		<%@include file="../comm/header.jsp" %>
		
		<script src="<c:url value='/static/js/push.js' />"></script>
	</head>
	
	<body>
		<div class="wrap">
	    	<%@include file="../comm/menu.jsp" %>

			<form id="form" name="form">
				<div class="box-group">
					<h1 class="box-tit"><c:out value="PUSH" /></h1>
					
					<p>UI 설명: 서비스코드, 이름, 생년월일, 전화번호를 입력한 후 PUSH 요청을 한다. 결과 처리 완료 및 서비스 제공 전까지 대기한다.</p>
					
					<div class="box-group">
						<button type="button" class="btn" id="pushReqBtn">PUSH 요청</button>
						<button type="button" class="btn" id="trxstsBtn">거래상태 조회</button>
						<button type="button" class="btn" id="resetBtn">초기화</button>
					</div>
					
					<div class="box-group">
						<input type="hidden" id="cmd" name="cmd" value="540" />
						<input type="hidden" id="mode" name="mode" value="direct" />
						
						<div class="box-line">
							<label for="svcCode">서비스 코드</label>
							<input type="text" list="svcCodes" id="svcCode" name="svcCode" required placeholder="example.1" />
							<datalist id="svcCodes">
								<option value="example.1" />
								<option value="example.2" />
								<option value="zkp.1" />
								<option value="zkp.2" />
								<option value="zkp.3" />
								<option value="nm.spe.isf.1" />
								<option value="nm.spe.prt.1" />
								<option value="nm.spe.prt.2" />
								<option value="nm.spe.prt.3" />
								<option value="nm.any.prt.1" />
								<option value="nm.anyc.pt.1" />
								<option value="nm.spvc.prt1" />
								<option value="nm.any.isf.1" />
								<option value="nm.indepat.1" />
								<option value="nm.nationm.1" />
								<option value="nm.demmeri.1" />
								<option value="nm.smmerit.1" />
								<option value="nm.rewardp.1" />
								<option value="nm.defolia.1" />
								<option value="nm.applica.1" />
								<option value="nm.dischas.1" />
								<option value="nm.nationm.2" />
							</datalist>
						</div>
						<div class="box-line">
							<label for="name">이름</label>
							<input type="text" id="name" name="name" required placeholder="홍길동" />
						</div>
						<div class="box-line">
							<label for="birth">생년월일</label>
							<input type="date" id="birth" name="birth" required placeholder="19860601" />
						</div>
						<div class="box-line">
							<label for="telno">전화번호</label>
							<input type="tel" id="telno" name="telno" required placeholder="01020203030" pattern="[0-9]{3}-[0-9]{2}-[0-9]{3}" />
						</div>
					</div>
				</div>
			</form>
				
			<div class="box-group">
				<div class="box-inner" id="pushResulstDiv">
					<h1 class="box-tit">PUSH 응답결과</h1>
					
					<div class="box-line">
						<label for="pushResultTag">결과</label>
						<textarea id="pushResultTag"></textarea>
					</div>
				</div>
				
				<%@include file="../comm/trxsts.jsp" %>
			</div>
		</div>
	</body>
</html>