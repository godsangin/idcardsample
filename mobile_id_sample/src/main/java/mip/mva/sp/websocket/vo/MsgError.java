package mip.mva.sp.websocket.vo;

import mip.mva.sp.config.ConfigBean;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.websocket.vo
 * @FileName    : MsgError.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 5. 31.
 * @Description : error 메세지 VO
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 5. 31.    Min Gi Ju        최초생성
 */
public class MsgError {

	private String msg = ConfigBean.ERROR;
	private String trxcode;
	private Integer errcode;
	private String errmsg;

	/**
	 * 생성자
	 * 
	 * @param trxcode 거래코드
	 * @param errcode 오류코드
	 * @param errmsg 오류메세지
	 */
	public MsgError(String trxcode, Integer errcode, String errmsg) {
		this.trxcode = trxcode;
		this.errcode = errcode;
		this.errmsg = errmsg;
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

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
