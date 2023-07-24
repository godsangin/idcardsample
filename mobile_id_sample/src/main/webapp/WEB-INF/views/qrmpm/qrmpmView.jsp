<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<title>SP 테스트 화면 - QR-MPM(direct mode)</title>
		
		<%@include file="../comm/header.jsp" %>
		
		<script src="<c:url value='/static/js/qrmpm.js' />"></script>
	</head>
	
	<body>
		<div class="wrap">
	    	<%@include file="../comm/menu.jsp" %>
	
			<form id="form" name="form">
				<div class="box-group">
					<h1 class="box-tit">QR-MPM</h1>
					
					<p>UI 설명: 응대 장치에서 SP 서버로 QR정보요청 후 신분증 앱에서 QR 스캔 후 업무처리 완료 후 서비스 제공 전까지 진행한다.</p>
					
					<div class="box-group">
						<button type="button" class="btn" id="sampleInputBtn">샘플입력</button>
						<button type="button" class="btn" id="qrInfoReqBtn">QR정보요청</button>
						<button type="button" class="btn" id="trxstsBtn">거래상태조회</button>
						<button type="button" class="btn" id="resetBtn">초기화</button>
					</div>
					
					<div class="box-group">
						<input type="hidden" id="cmd" name="cmd" value="510" />
						
						<div class="box-line">
							<label for="mode">Mode</label>
							<select id="mode" name="mode" required>
								<option value="direct">direct</option>
								<option value="proxy">proxy</option>
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
						
						<div class="box-line">
							<label for="branchName">지점명</label>
							<input type="text" id="branchName" name="branchName" placeholder="논현역점" />
						</div>
						
						<div class="box-line">
							<label for="deviceId">기기ID</label>
							<input type="text" id="deviceId" name="deviceId" placeholder="123456789" />
						</div>
					</div>
				</div>
			</form>
		
			<div class="box-group">
				<div class="box-inner" id="qrCodeDiv">
					<h1 class="box-tit">QR</h1>
					
					<div class="qr-area" id="qrCodeArea">QR 코드 영역</div>
				</div>
				
				<%@include file="../comm/trxsts.jsp" %>
			</div>
		</div>
	</body>
</html>