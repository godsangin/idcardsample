package mip.mva.sp.websocket.proc.noncpm;

import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.vo.WsInfoVO;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.websocket.proc.noncpm
 * @FileName    : NonCpmDefaultProc.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 5. 31.
 * @Description : Non CPM Default 메세지 처리 Class
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 5. 31.    Min Gi Ju        최초생성
 */
public class NonCpmDefaultProc {

	private static final Logger LOGGER = LoggerFactory.getLogger(NonCpmDefaultProc.class);

	public void procDefault(String message, Session session, WsInfoVO wsInfo) throws SpException {
		// default 처리를 위해 있는 class 로서 현재로서는 아무것도 하지 않고 들어온 메세지는 discard 된다.
		LOGGER.debug("message : {}", message);
	}

}
