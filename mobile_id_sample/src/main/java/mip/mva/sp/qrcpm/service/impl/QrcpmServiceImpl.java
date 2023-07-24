package mip.mva.sp.qrcpm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.google.gson.JsonSyntaxException;

import mip.mva.sp.comm.enums.MipErrorEnum;
import mip.mva.sp.comm.enums.TrxStsCodeEnum;
import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.service.TrxInfoService;
import mip.mva.sp.comm.util.Base64Util;
import mip.mva.sp.comm.vo.M120VO;
import mip.mva.sp.comm.vo.T520VO;
import mip.mva.sp.comm.vo.TrxInfoVO;
import mip.mva.sp.comm.vo.WsInfoVO;
import mip.mva.sp.config.ConfigBean;
import mip.mva.sp.qrcpm.service.QrcpmService;
import mip.mva.sp.websocket.client.cpm.CpmClient;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.qrcpm.service.impl
 * @FileName    : QrcpmServiceImpl.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : QR-CPM 인터페이스 검증 처리 ServiceImpl
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@Service
public class QrcpmServiceImpl implements QrcpmService {

	private static final Logger LOGGER = LoggerFactory.getLogger(QrcpmServiceImpl.class);

	/** 커스텀 프로퍼티 */
	private final ConfigBean configBean;
	/** 거래정보 Service */
	private final TrxInfoService trxInfoService;

	/**
	 * 생성자
	 * 
	 * @param configBean 커스텀 프로퍼티
	 * @param trxInfoService 거래정보 Service
	 */
	public QrcpmServiceImpl(ConfigBean configBean, TrxInfoService trxInfoService) {
		this.configBean = configBean;
		this.trxInfoService = trxInfoService;
	}

	/**
	 * QR-CPM 시작
	 * 
	 * @MethodName : start
	 * @param t520 QR-CPM 정보
	 * @return QR-CPM 정보
	 * @throws SpException
	 */
	@Override
	public T520VO start(T520VO t520) throws SpException {
		LOGGER.debug("t520 : {}", ConfigBean.gson.toJson(t520));

		M120VO m120 = null;

		try {
			String m120Str = Base64Util.decode(t520.getM120Base64());

			m120 = ConfigBean.gson.fromJson(m120Str, M120VO.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, null, "m120");
		}

		String svcCode = t520.getSvcCode();
		String branchName = t520.getBranchName();
		String deviceId = t520.getDeviceId();

		String mode = m120.getMode();
		String host = m120.getHost();
		//업무망 사용시
		//String host = m120.getHost().contains("mva01.mobileid.go.kr") ? "ws://mva01.mobileid.go.kr:9090/proxyServer":"ws://mva02.mobileid.go.kr:9090/proxyServer";
		String trxcode = m120.getTrxcode();

		if (ObjectUtils.isEmpty(trxcode)) {
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "t520.trxcode");
		}

		if (ObjectUtils.isEmpty(svcCode)) {
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "t520.svcCode");
		}

		String trxStsCode = TrxStsCodeEnum.SERCIVE_REQ.getVal();
		Integer timeout = configBean.getProxyConnTimeOut();

		// 거래상태코 등록
		TrxInfoVO trxInfo = new TrxInfoVO();

		trxInfo.setTrxcode(trxcode);
		trxInfo.setSvcCode(svcCode);
		trxInfo.setMode(mode);
		trxInfo.setTrxStsCode(trxStsCode);
		trxInfo.setBranchName(t520.getBranchName());
		trxInfo.setDeviceId(t520.getDeviceId());

		LOGGER.debug("trxInfo : {}", ConfigBean.gson.toJson(trxInfo));

		// 거래코드 등록
		trxInfoService.registTrxInfo(trxInfo);

		WsInfoVO wsInfo = new WsInfoVO();

		wsInfo.setConnUrl(host);
		wsInfo.setTrxcode(trxcode);
		wsInfo.setTimeout(timeout);
		wsInfo.setSvcCode(svcCode);
		wsInfo.setBranchName(branchName);
		wsInfo.setDeviceId(deviceId);

		CpmClient client = new CpmClient(wsInfo);

		client.start();

		return t520;
	}

}
