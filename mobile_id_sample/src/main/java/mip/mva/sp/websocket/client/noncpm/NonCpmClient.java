package mip.mva.sp.websocket.client.noncpm;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mip.mva.sp.comm.vo.WsInfoVO;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.websocket.client.noncpm
 * @FileName    : NonCpmClient.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 5. 31.
 * @Description : Non CPM 클라이언트 Class
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 5. 31.    Min Gi Ju        최초생성
 */
public class NonCpmClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(NonCpmClient.class);

	private final WsInfoVO wsInfo;

	/**
	 * 생성자
	 * 
	 * @param wsInfo 웹소켓 정보
	 */
	public NonCpmClient(WsInfoVO wsInfo) {
		this.wsInfo = wsInfo;
	}

	/**
	 * 웹소켓 시작
	 * 
	 * @MethodName : start
	 */
	public void start() {
		WebSocketClient client = new WebSocketClient();
		NonCpmClientMsgHandler socket = new NonCpmClientMsgHandler(wsInfo);

		try {
			client.start();

			URI echoUri = new URI(wsInfo.getConnUrl());

			ClientUpgradeRequest request = new ClientUpgradeRequest();

			client.connect(socket, echoUri, request);

			LOGGER.debug("connecting to: {}", echoUri);

			socket.awaitClose(wsInfo.getTimeout(), TimeUnit.SECONDS);
		} catch (Exception e) {
			LOGGER.error("websocket connectiong fail", e);
		} finally {
			try {
				client.stop();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
