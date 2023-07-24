<%@page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<title>SP 테스트 화면 - QR-CPM</title>
		
		<%@include file="../comm/header.jsp" %>
		
		<script src="<c:url value='/static/js/qrcpm.js' />"></script>
	</head>
	
	<body>
		<div class="wrap">
			<%@include file="../comm/menu.jsp" %>
			
			<form id="form" name="form">
				<div class="box-group">
					<h1 class="box-tit">QR-CPM</h1>
					
					<p>UI 설명 : 응대 장치에서 신분증 앱의 QR 코드를 스캔 한 QR 정보를 중계 서버로 전달하고, 서비스 제공 전까지 대기한다.</p>
					
					<div class="box-group">
						<button type="button" class="btn" id="sampleInputBtn">샘플입력</button>
						<button type="button" class="btn" id="trxstsBtn">거래상태조회</button>
						<button type="button" class="btn" id="resetBtn">초기화</button>
					</div>
					
					<div class="box-group">
						<input type="hidden" id="cmd" name="cmd" value="520" />
						
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
					
					<div class="box-group">
						<div class="box-inner" id="qrCodeDiv">
							<h1 class="box-tit">QR 스캐너</h1>
							
							<div class="box" id="frame">
								<div id="loadingMessage">
									🎥 비디오 스트림에 액세스 할 수 없습니다<br />웹캠이 활성화되어 있는지 확인하십시오
								</div>
								
								<canvas id="canvas"></canvas>
							</div>
						</div>
						
						<div class="box-inner">
							<h1 class="box-tit">QR 스캔결과뷰</h1>
							
							<div class="box" id="output">
								<p id="outputMessage">QR코드를 카메라에 노출시켜 주세요</p>
								<p id="outputData"></p>
								<p id="outputData2"></p>
							</div>
						</div>
					</div>
				</div>
			</form>
			
			<div class="box-group">
				<div class="box-inner" id="qrResulstDiv">
					<h1 class="box-tit">QR 응답결과</h1>
					
					<div class="box-line">
						<label for="qrResultTag">결과</label>
						<textarea id="qrResultTag"></textarea>
					</div>
				</div>
			
				<%@include file="../comm/trxsts.jsp" %>
			</div>
		</div>
	</body>
</html>