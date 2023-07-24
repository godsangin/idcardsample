<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<title>SP 테스트 화면 - Web to App</title>
		
		<%@include file="../comm/header.jsp" %>
		
		<script src="<c:url value='/static/js/web2app.js' />"></script>
	</head>
	
	<body>
		<div class="wrap">
	    	<%@include file="../comm/menu.jsp" %>

			<form id="form" name="form">
				<div class="box-group">
					<h1 class="box-tit"><c:out value="Web to App(모바일용)" /></h1>
					
					<p>
						UI 설명: 모바일에서 M200 요청, App 호출 순으로 버튼을 클릭한 후 결과 처리 완료 및 서비스 제공 전까지 대기한다.
					</p>
					
					<div class="box-group">
						<button type="button" class="btn" id="sampleInputBtn">샘플입력</button>
						<button type="button" class="btn" id="m200ReqBtn">M200 요청</button>
						<button type="button" class="btn" id="appCallBtn">App 호출</button>
						<button type="button" class="btn" id="trxstsBtn">거래상태조회</button>
						<button type="button" class="btn" id="resetBtn">초기화</button>
					</div>
					
					<div class="box-group">
						<input type="hidden" id="cmd" name="cmd" value="530" />
						<input type="hidden" id="mode" name="mode" value="direct" />
						<input type="hidden" id="includeProfile" name="includeProfile" value="true" />
						
						<div class="box-line">
							<label for="os">OS</label>
							<select id="os" name="os" required>
								<option value="AOS">AOS</option>
								<option value="IOS">IOS</option>
							</select>
						</div>
						
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
					</div>
				</div>
			</form>
				
			<div class="box-group">
				<div class="box-inner" id="reqDataDiv">
					<h1 class="box-tit">App 호출 데이터</h1>
					
					<textarea id="reqDataArea" name="reqDataArea"></textarea>
				</div>
				
				<%@include file="../comm/trxsts.jsp" %>
			</div>
		</div>
	</body>
</html>