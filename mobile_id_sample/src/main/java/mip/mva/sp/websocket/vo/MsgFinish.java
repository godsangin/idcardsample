package mip.mva.sp.websocket.vo;

import mip.mva.sp.config.ConfigBean;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.websocket.vo
 * @FileName    : MsgFinish.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 5. 31.
 * @Description : finish 메세지 VO
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 5. 31.    Min Gi Ju        최초생성
 */
public class MsgFinish {

	private String msg = ConfigBean.FINISH;
	private String trxcode;

	/**
	 * 생성자
	 * 
	 * @param trxcode 거래코드
	 */
	public MsgFinish(String trxcode) {
		this.trxcode = trxcode;
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

}
