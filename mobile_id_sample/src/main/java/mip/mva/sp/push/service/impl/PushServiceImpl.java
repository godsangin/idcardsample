package mip.mva.sp.push.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.JsonSyntaxException;

import mip.mva.sp.comm.enums.MipErrorEnum;
import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.service.DirectService;
import mip.mva.sp.comm.util.Base64Util;
import mip.mva.sp.comm.util.HttpUtil;
import mip.mva.sp.comm.vo.M200VO;
import mip.mva.sp.comm.vo.PushVO;
import mip.mva.sp.comm.vo.T540VO;
import mip.mva.sp.config.ConfigBean;
import mip.mva.sp.push.service.PushService;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.push.service.impl
 * @FileName    : PushServiceImpl.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : 푸시 인터페이스 검증 처리 ServiceImpl
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@Service
public class PushServiceImpl implements PushService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PushServiceImpl.class);

	/** 커스텀 프로퍼티 */
	private final ConfigBean configBean;
	/** Direct 검증 Service */
	private final DirectService directService;

	/**
	 * 생성자
	 * 
	 * @param configBean 커스텀 프로퍼티
	 * @param directService Direct 검증 Service
	 */
	public PushServiceImpl(ConfigBean configBean, DirectService directService) {
		this.configBean = configBean;
		this.directService = directService;
	}

	/**
	 * 푸시 시작
	 * 
	 * @MethodName : start
	 * @param t540 푸시 정보
	 * @return 푸시 정보 + Base64로 인코딩된 M200 메시지
	 * @throws SpException
	 */
	@Override
	public T540VO start(T540VO t540) throws SpException {
		LOGGER.debug("t540 : {}", ConfigBean.gson.toJson(t540));

		String svcCode = t540.getSvcCode();
		String mode = t540.getMode();
		String name = t540.getName();
		String birth = t540.getBirth();
		String telno = t540.getTelno();

		String serverDomain = configBean.getPushServerDomain();
		String msCode = configBean.getPushMsCode();
		String pushType = configBean.getPushType();
		Boolean includeProfile = configBean.getIncludeProfile();

		// M200 메시지 생성
		M200VO m200 = directService.getM200(mode, svcCode, includeProfile);

		String data = Base64Util.encode(ConfigBean.gson.toJson(m200));

		t540.setM200Base64(data);

		PushVO push = new PushVO();

		push.setMscode(msCode);
		push.setPushType(pushType);
		push.setName(name);
		push.setBirth(birth);
		push.setTelno(telno);
		push.setData(data);

		// 푸시 요청 원문 생성
		Map<String, Object> pushMap = new HashMap<String, Object>();

		pushMap.put("data", Base64Util.encode(ConfigBean.gson.toJson(push)));

		LOGGER.debug("pushMap : {}", ConfigBean.gson.toJson(pushMap));

		String pushResult = HttpUtil.executeHttpPost(serverDomain, ConfigBean.gson.toJson(pushMap));

		LOGGER.debug("pushResult : {}", pushResult);

		try {
			push = ConfigBean.gson.fromJson(pushResult, PushVO.class);

			if (!push.getResult()) {
				throw new SpException(MipErrorEnum.UNKNOWN_ERROR, m200.getTrxcode(), push.getErrmsg());
			}
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, m200.getTrxcode(), "pushResult");
		}

		return t540;
	}

}
