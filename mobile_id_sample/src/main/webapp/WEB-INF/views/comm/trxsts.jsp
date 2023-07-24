<%@page language="java" pageEncoding="UTF-8"%>
<div class="box-inner" id="qrResDiv">
	<h1 class="box-tit">거래상태</h1>
	
	<div class="box-line">
		<label for="trxcodeTag">거래코드 : <span id="trxcodeTag"></span></label>
	</div>
	<div class="box-line">
		<label for="trxStsCodeTag">서비스 상태 : <span id="trxStsCodeTag"></span></label>
	</div>
	<div class="box-line">
		<label for="vpVerifyResultTag">VP 검증 결과 여부 : <span id="vpVerifyResultTag"></span></label>
	</div>
	<div class="box-line">
		<label for="regDtTag">서비스요청일시 : <span id="regDtTag"></span></label>
	</div>
	<div class="box-line">
		<label for="profileSendDtTag">profile 송신일시 : <span id="profileSendDtTag"></span></label>
	</div>
	<div class="box-line">
		<label for="vpReceptDtTag">VP 수신일시 : <span id="vpReceptDtTag"></span></label>
	</div>
	<div class="box-line">
		<label for="imgSendDtTag">이미지 송신일시 : <span id="imgSendDtTag"></span></label>
	</div>
	<div class="box-line">
		<label for="udtDtTag">최종 상태 : <span id="udtDtTag"></span></label>
	</div>
	<div class="box-line">
		<label for="vpTag">VP Data : <span id="vpTag"></span></label>
		<textarea id="vpArea"></textarea>
	</div>
</div>
