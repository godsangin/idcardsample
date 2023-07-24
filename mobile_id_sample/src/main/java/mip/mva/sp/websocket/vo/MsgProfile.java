package mip.mva.sp.websocket.vo;

import mip.mva.sp.config.ConfigBean;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.websocket.vo
 * @FileName    : MsgProfile.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 5. 31.
 * @Description : profile 메세지 VO
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 5. 31.    Min Gi Ju        최초생성
 */
public class MsgProfile {

	private String msg = ConfigBean.PROFILE;
	private String trxcode;
	private String profile;
	private String image;
	private Boolean ci;

	/**
	 * 생성자
	 * 
	 * @param trxcode 거래코드
	 * @param profile Base64로 인코딩된 프로파일
	 * @param image Base64로 인코딩된 이미지
	 * @param ci CI 제출여부
	 */
	public MsgProfile(String trxcode, String profile, String image, Boolean ci) {
		this.trxcode = trxcode;
		this.profile = profile;
		this.image = image;
		this.ci = ci;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTrxcode() {
		return trxcode;
	}

	public void setTrxcode(String trxcode) {
		this.trxcode = trxcode;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getCi() {
		return ci;
	}

	public void setCi(Boolean ci) {
		this.ci = ci;
	}

}
