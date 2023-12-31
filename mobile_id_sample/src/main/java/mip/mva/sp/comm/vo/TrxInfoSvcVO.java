package mip.mva.sp.comm.vo;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.vo
 * @FileName    : TrxInfoSvcVO.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : 거래 & 서비스정보 VO
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
public class TrxInfoSvcVO {

	/** 거래코드 */
	private String trxcode;
	/** 서비스코드 */
	private String svcCode;
	/** 모드 */
	private String mode;
	/** 장치ID */
	private String deviceId;
	/** 지점명 */
	private String branchName;
	/** Nonce */
	private String nonce;
	/** ZKP Nonce */
	private String zkpNonce;
	/** VpVerifyResult */
	private String vpVerifyResult;
	/** 거래상태코드 */
	private String trxStsCode;
	/** Profile 전송일자 */
	private String profileSendDt;
	/** BI 이미지 전송일자 */
	private String imgSendDt;
	/** VP 접수일자 */
	private String vpReceptDt;
	/** 오류내용 */
	private String errorCn;
	/** 등록일자 */
	private String regDt;
	/** 수정일자 */
	private String udtDt;
	/** 제공유형 */
	private Integer presentType;
	/** 암호화유형 */
	private Integer encryptType;
	/** 키유형 */
	private Integer keyType;
	/** 인증유형 */
	private String authType;
	/** SP명 */
	private String spName;
	/** 서비스명 */
	private String serviceName;
	/** 값 자체를 제출하는 영지식 증명 항목 리스트. ["zkpaddr","zkpsex","zkpasort"] 과 같이 JSON 배열로 정의해야 함 - zkpaddr: 주소(동 까지) - zkpsex: 성별 - zkpasort: 면허종별 */
	private String attrList;
	/** 검증을 위한 조건을 제시하여 조건에 맞음을 검증하는 영지식 증명 항목 리스트 [{"zkpbirth":{"type":"LE","value":"19"}}] 과 같이 JSON 배열로 정의해야 함 현재는 zkpbirth(생년월일) 밖에 없음 */
	private String predList;
	/** Callback URL */	
	private String callBackUrl;

	public String getTrxcode() {
		return trxcode;
	}

	public void setTrxcode(String trxcode) {
		this.trxcode = trxcode;
	}

	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getZkpNonce() {
		return zkpNonce;
	}

	public void setZkpNonce(String zkpNonce) {
		this.zkpNonce = zkpNonce;
	}

	public String getVpVerifyResult() {
		return vpVerifyResult;
	}

	public void setVpVerifyResult(String vpVerifyResult) {
		this.vpVerifyResult = vpVerifyResult;
	}

	public String getTrxStsCode() {
		return trxStsCode;
	}

	public void setTrxStsCode(String trxStsCode) {
		this.trxStsCode = trxStsCode;
	}

	public String getProfileSendDt() {
		return profileSendDt;
	}

	public void setProfileSendDt(String profileSendDt) {
		this.profileSendDt = profileSendDt;
	}

	public String getImgSendDt() {
		return imgSendDt;
	}

	public void setImgSendDt(String imgSendDt) {
		this.imgSendDt = imgSendDt;
	}

	public String getVpReceptDt() {
		return vpReceptDt;
	}

	public void setVpReceptDt(String vpReceptDt) {
		this.vpReceptDt = vpReceptDt;
	}

	public String getErrorCn() {
		return errorCn;
	}

	public void setErrorCn(String errorCn) {
		this.errorCn = errorCn;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getUdtDt() {
		return udtDt;
	}

	public void setUdtDt(String udtDt) {
		this.udtDt = udtDt;
	}

	public Integer getPresentType() {
		return presentType;
	}

	public void setPresentType(Integer presentType) {
		this.presentType = presentType;
	}

	public Integer getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(Integer encryptType) {
		this.encryptType = encryptType;
	}

	public Integer getKeyType() {
		return keyType;
	}

	public void setKeyType(Integer keyType) {
		this.keyType = keyType;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getAttrList() {
		return attrList;
	}

	public void setAttrList(String attrList) {
		this.attrList = attrList;
	}

	public String getPredList() {
		return predList;
	}

	public void setPredList(String predList) {
		this.predList = predList;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

}
