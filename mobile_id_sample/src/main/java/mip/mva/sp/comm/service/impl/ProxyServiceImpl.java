package mip.mva.sp.comm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import mip.mva.sp.comm.enums.MipErrorEnum;
import mip.mva.sp.comm.enums.PresentTypeEnum;
import mip.mva.sp.comm.enums.TrxStsCodeEnum;
import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.service.MipDidVpService;
import mip.mva.sp.comm.service.MipZkpVpService;
import mip.mva.sp.comm.service.ProxyService;
import mip.mva.sp.comm.service.SvcService;
import mip.mva.sp.comm.service.TrxInfoService;
import mip.mva.sp.comm.vo.TrxInfoSvcVO;
import mip.mva.sp.comm.vo.TrxInfoVO;
import mip.mva.sp.comm.vo.VP;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.service.impl
 * @FileName    : ProxyServiceImpl.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 5. 31.
 * @Description : Proxy 검증 ServiceImpl
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 5. 31.    Min Gi Ju        최초생성
 */
@Service
public class ProxyServiceImpl implements ProxyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProxyServiceImpl.class);

	/** 서비스 Service */
	private final SvcService svcService;
	/** 거래정보 Service */
	private final TrxInfoService trxInfoService;
	/** VP 검증 Service */
	private final MipDidVpService mipDidVpService;
	/** 영지식 VP 검증 Service */
	private final MipZkpVpService mipZkpVpService;

	/**
	 * 생성자
	 * 
	 * @param svcService 서비스 Service
	 * @param trxInfoService 거래정보 Service
	 * @param mipDidVpService VP 검증 Service
	 * @param mipZkpVpService 영지식 VP 검증 Service
	 */
	public ProxyServiceImpl(SvcService svcService, TrxInfoService trxInfoService, MipDidVpService mipDidVpService, MipZkpVpService mipZkpVpService) {
		this.svcService = svcService;
		this.trxInfoService = trxInfoService;
		this.mipDidVpService = mipDidVpService;
		this.mipZkpVpService = mipZkpVpService;
	}

	/**
	 * DID Assertion 생성
	 * 
	 * @MethodName : makeDIDAssertion
	 * @param nonce Nonce
	 * @return DID Assertion
	 * @throws SpException
	 */
	@Override
	public String makeDIDAssertion(String nonce) throws SpException {
		LOGGER.debug("nonce : {}", nonce);

		return mipDidVpService.makeDIDAssertion(nonce);
	}

	/**
	 * Profile 요청
	 * 
	 * @MethodName : getProfile
	 * @param trxcode 거래코드
	 * @return Base64로 인코딩된 Profile
	 * @throws SpException
	 */
	@Override
	public String getProfile(String trxcode) throws SpException {
		LOGGER.debug("trxcode : {}", trxcode);

		TrxInfoSvcVO trxInfoSvc = svcService.getTrxInfoSvc(trxcode);

		if (ObjectUtils.isEmpty(trxInfoSvc)) {
			throw new SpException(MipErrorEnum.SP_TRXINFO_NOT_FOUND, trxcode);
		}
		
		Integer presentType = trxInfoSvc.getPresentType();
		String profile = null;

		if (PresentTypeEnum.DID_VP.getVal() == presentType) {
			profile = mipDidVpService.getProfile(trxInfoSvc);
		} else if (PresentTypeEnum.ZKP_VP.getVal() == presentType) {
			profile = mipZkpVpService.getProfile(trxInfoSvc);
		} else {
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_PRESENT_TYPE, trxcode);
		}

		return profile;
	}

	/**
	 * VP 검증
	 * 
	 * @MethodName : verifyVP
	 * @param trxcode 거래코드
	 * @param vp VP 정보
	 * @return 검증 결과
	 * @throws SpException
	 */
	@Override
	public Boolean verifyVP(String trxcode, VP vp) throws SpException {
		LOGGER.debug("trxcode : {}", trxcode);

		TrxInfoSvcVO trxInfoSvc = svcService.getTrxInfoSvc(trxcode);

		if (ObjectUtils.isEmpty(trxInfoSvc)) {
			throw new SpException(MipErrorEnum.SP_TRXINFO_NOT_FOUND, trxcode);
		}
		
		Integer presentType = trxInfoSvc.getPresentType();
		Boolean result = false;

		if (PresentTypeEnum.DID_VP.getVal() == presentType) {
			result = mipDidVpService.verifyVP(trxcode, vp);
		} else if (PresentTypeEnum.ZKP_VP.getVal() == presentType) {
			result = mipZkpVpService.verifyVP(trxcode, vp);
		} else {
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_PRESENT_TYPE, trxcode);
		}

		return result;
	}

	/**
	 * 오류 전송
	 * 
	 * @MethodName : sendError
	 * @param trxcode 거래코드
	 * @param errmsg 오류 메세지
	 * @throws SpException
	 */
	@Override
	public void sendError(String trxcode, String errmsg) throws SpException {
		TrxInfoVO trxInfo = new TrxInfoVO();

		trxInfo.setTrxcode(trxcode);
		trxInfo.setTrxStsCode(TrxStsCodeEnum.VERIFY_ERR.getVal());
		trxInfo.setErrorCn(errmsg);

		trxInfoService.modifyTrxInfo(trxInfo);
	}

	/**
	 * VP 재검증
	 * 
	 * @MethodName : reVerifyVP
	 * @param svcCode 서비스코드
	 * @param vp VP 정보
	 * @return 검증 결과
	 * @throws SpException
	 */
	@Override
	public Boolean reVerifyVP(String svcCode, VP vp) throws SpException {
		Boolean result = null;

		Integer presentType = vp.getPresentType();

		if (PresentTypeEnum.DID_VP.getVal() == vp.getPresentType()) {
			result = mipDidVpService.reVerifyVP(svcCode, vp);
		} else if (PresentTypeEnum.ZKP_VP.getVal() == presentType) {
			result = mipZkpVpService.reVerifyVP(svcCode, vp);
		} else {
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_PRESENT_TYPE);
		}

		return result;
	}

}
