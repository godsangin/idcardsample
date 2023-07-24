package mip.mva.sp.websocket.proc.noncpm;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mip.mva.sp.comm.enums.ProxyErrorEnum;
import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.service.ProxyService;
import mip.mva.sp.comm.util.SpringUtil;
import mip.mva.sp.comm.vo.VP;
import mip.mva.sp.comm.vo.WsInfoVO;
import mip.mva.sp.config.ConfigBean;
import mip.mva.sp.websocket.vo.MsgError;
import mip.mva.sp.websocket.vo.MsgFinish;
import mip.mva.sp.websocket.vo.MsgVp;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.websocket.proc.noncpm
 * @FileName    : NonCpmVp.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 5. 31.
 * @Description : Non CPM 검증 메세지 처리 Class
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 5. 31.    Min Gi Ju        최초생성
 */
public class NonCpmVp {

	private static final Logger LOGGER = LoggerFactory.getLogger(NonCpmVp.class);

	/**
	 * 검증 메세지 처리
	 * 
	 * @MethodName : procVp
	 * @param message vp 메세지
	 * @param session WebSocket 세션
	 * @param wsInfo WebSocket WebSocket 정보
	 * @throws SpException
	 */
	public void procVp(String message, Session session, WsInfoVO wsInfo) throws SpException {
		LOGGER.debug("message : {}", message);

		String trxcode = null;
		String sendMsg = null;

		try {
			MsgVp msgVp = ConfigBean.gson.fromJson(message, MsgVp.class);

			trxcode = msgVp.getTrxcode();
			
			String request = msgVp.getRequest();
			VP vp = msgVp.getVp();
			
			if (trxcode == null) {
				throw new SpException(ProxyErrorEnum.MISSING_MANDATORY_ITEM, null, "trxcode");
			}

			if (request == null) {
				throw new SpException(ProxyErrorEnum.MISSING_MANDATORY_ITEM, trxcode, "request");
			}

			if (vp == null) {
				throw new SpException(ProxyErrorEnum.MISSING_MANDATORY_ITEM, trxcode, "vp");
			}

			if (!trxcode.equals(msgVp.getTrxcode())) {
				throw new SpException(ProxyErrorEnum.TRXCODE_NOT_FOUND, trxcode);
			}

			ProxyService proxyService = (ProxyService) SpringUtil.getBean(ProxyService.class);

			proxyService.verifyVP(trxcode, vp);

			MsgFinish msgFinish = new MsgFinish(trxcode);

			sendMsg = ConfigBean.gson.toJson(msgFinish);
		} catch (SpException e) {
			LOGGER.error(e.getMessage(), e);

			MsgError msgError = new MsgError(wsInfo.getTrxcode(), e.getErrcode(), e.getErrmsg());

			sendMsg = ConfigBean.gson.toJson(msgError);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			MsgError msgError = new MsgError(wsInfo.getTrxcode(), ProxyErrorEnum.UNKNOWN_ERROR.getCode(), ProxyErrorEnum.UNKNOWN_ERROR.getMsg());

			sendMsg = ConfigBean.gson.toJson(msgError);
		}

		LOGGER.debug("sendMsg : {}", sendMsg);

		try {
			session.getRemote().sendString(sendMsg);
			session.close();
			
			wsInfo.setResult(sendMsg);
			wsInfo.setStatus(ConfigBean.VP);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(ProxyErrorEnum.UNKNOWN_ERROR, trxcode, "sendString");
		}
	}

}
