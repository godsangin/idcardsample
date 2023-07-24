package mip.mva.sp.comm.vo;

import java.io.Serializable;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.vo
 * @FileName    : T530VO.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : App to App 시작용 VO
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
public class T530VO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Command */
	private String cmd;
	/** 모드 */
	private String mode;
	/** 서비스코드 */
	private String svcCode;
	/** 프로파일 포함 여부 */
	private Boolean includeProfile;

	/** Base64로 인코딩된 M200 메세지 */
	private String m200Base64;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

	public Boolean getIncludeProfile() {
		return includeProfile;
	}

	public void setIncludeProfile(Boolean includeProfile) {
		this.includeProfile = includeProfile;
	}

	public String getM200Base64() {
		return m200Base64;
	}

	public void setM200Base64(String m200Base64) {
		this.m200Base64 = m200Base64;
	}

}
