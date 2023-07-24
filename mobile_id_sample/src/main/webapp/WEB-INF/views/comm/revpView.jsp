<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<title>SP 테스트 화면 - VP 재검증</title>
		
		<%@include file="../comm/header.jsp" %>
		
		<script src="<c:url value='/static/js/comm/revp.js' />"></script>
	</head>
	
	<body>
		<div class="wrap">
	    	<%@include file="../comm/menu.jsp" %>

			<form id="form" name="form">
				<div class="box-group">
					<h1 class="box-tit">VP 재검증 - 부인방지</h1>
					
					<p>UI 설명: VP 정보를 입력 한 후 재검증 요청을 실행 한다.</p>
					
					<div class="box-group">
						<button type="button" class="btn" id="reqBtn">재검증 요청</button>
						<button type="button" class="btn" id="resetBtn">초기화</button>
					</div>
					
					<div class="box-group">
						<div class="box-line">
							<label for="svcCode">서비스 코드(선택)</label>
							<input type="text" list="svcCodes" id="svcCode" name="svcCode" placeholder="example.1" />
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
							<label for="vp">VP 정보</label>
							<textarea id="vp" name="vp" required></textarea>
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
	</body>
</html>