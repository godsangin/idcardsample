package mip.mva.sp.comm.vo;

import java.io.Serializable;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.vo
 * @FileName    : T520VO.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : QR-CPM 시작용 VO
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
public class T520VO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** Command */
	private String cmd;
	/** 서비스코드 */
	private String svcCode;
	/** 지점명 */
	private String branchName;
	/** 장치ID */
	private String deviceId;

	/** Base64로 인코딩된 M120 메세지 */
	private String m120Base64;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getM120Base64() {
		return m120Base64;
	}

	public void setM120Base64(String m120Base64) {
		this.m120Base64 = m120Base64;
	}

}
