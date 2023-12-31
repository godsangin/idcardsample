package mip.mva.sp.comm.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.raonsecure.omnione.core.data.iw.profile.EncryptKeyTypeEnum;
import com.raonsecure.omnione.core.data.iw.profile.EncryptTypeEnum;

import mip.mva.sp.comm.enums.AuthTypeEnum;
import mip.mva.sp.comm.enums.MipErrorEnum;
import mip.mva.sp.comm.enums.PresentTypeEnum;
import mip.mva.sp.comm.enums.RequestTypeEnum;
import mip.mva.sp.comm.enums.TrxStsCodeEnum;
import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.service.DirectService;
import mip.mva.sp.comm.service.MipDidVpService;
import mip.mva.sp.comm.service.MipZkpVpService;
import mip.mva.sp.comm.service.SvcService;
import mip.mva.sp.comm.service.TrxInfoService;
import mip.mva.sp.comm.util.Base64Util;
import mip.mva.sp.comm.util.Generator;
import mip.mva.sp.comm.vo.M200VO;
import mip.mva.sp.comm.vo.M310VO;
import mip.mva.sp.comm.vo.M320VO;
import mip.mva.sp.comm.vo.M400VO;
import mip.mva.sp.comm.vo.M900VO;
import mip.mva.sp.comm.vo.TrxInfoSvcVO;
import mip.mva.sp.comm.vo.TrxInfoVO;
import mip.mva.sp.comm.vo.VP;
import mip.mva.sp.config.ConfigBean;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.service.impl
 * @FileName    : DirectServiceImpl.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : Direct 검증 ServiceImpl
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@Service
public class DirectServiceImpl implements DirectService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DirectServiceImpl.class);

	/** 커스텀 프로퍼티 */
	private final ConfigBean configBean;
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
	 * @param configBean 커스텀 프로퍼티
	 * @param svcService 서비스 Service
	 * @param trxInfoService 거래정보 Service
	 * @param mipDidVpService VP 검증 Service
	 * @param mipZkpVpService 영지식 VP 검증 Service
	 */
	public DirectServiceImpl(ConfigBean configBean, SvcService svcService, TrxInfoService trxInfoService, MipDidVpService mipDidVpService,
			MipZkpVpService mipZkpVpService) {
		this.configBean = configBean;
		this.svcService = svcService;
		this.trxInfoService = trxInfoService;
		this.mipDidVpService = mipDidVpService;
		this.mipZkpVpService = mipZkpVpService;
	}

	/**
	 * M200 요청
	 * 
	 * @MethodName : getM200
	 * @param mode 모드
	 * @param svcCode 서비스코드
	 * @param includeProfile 프로파일 표함 여부
	 * @return M200 메세지
	 * @throws SpException
	 */
	@Override
	public M200VO getM200(String mode, String svcCode, Boolean includeProfile) throws SpException {
		LOGGER.debug("mode : {}, svcCode : {}, includeProfile : {}", mode, svcCode, includeProfile);

		String trxcode = Generator.genTrxcode();
		String profile = null;
		String image = configBean.getSpBiImageUrl();
		Boolean ci = configBean.getSpCi();
		String host = configBean.getSpServer();

		TrxInfoVO trxInfo = new TrxInfoVO();

		trxInfo.setSvcCode(svcCode);
		trxInfo.setTrxcode(trxcode);
		trxInfo.setMode(mode);

		trxInfoService.registTrxInfo(trxInfo);

		if (includeProfile) {
			M310VO m310 = new M310VO();
			
			m310.setTrxcode(trxcode);
			m310.setRequest(RequestTypeEnum.CMD_310_REQ.getRequest());
			
			profile = this.getProfile(m310).getProfile();
		}

		M200VO m200 = new M200VO();

		m200.setTrxcode(trxcode);
		m200.setMode(mode);
		m200.setProfile(profile);
		m200.setImage(image);
		m200.setCi(ci);
		m200.setHost(host);

		return m200;
	}

	/**
	 * Profile 요청
	 * 
	 * @MethodName : getProfile
	 * @param m310 M310 메세지
	 * @return M310 메세지 + Profile
	 * @throws SpException
	 */
	@Override
	public M310VO getProfile(M310VO m310) throws SpException {
		LOGGER.debug("m310 : {}", ConfigBean.gson.toJson(m310));

		this.validateM310(m310);

		String trxcode = m310.getTrxcode();

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
		
		LOGGER.debug("profile : {}", Base64Util.decode(profile));

		m310.setProfile(profile);

		return m310;
	}

	/**
	 * 이미지 요청
	 * 
	 * @MethodName : getImage
	 * @param m320 M320 메세지
	 * @return Base64로 인코딩된 Image Data
	 * @throws SpException
	 */
	@Override
	public String getImage(M320VO m320) throws SpException {
		LOGGER.debug("m320 : {}", ConfigBean.gson.toJson(m320));

		String image = configBean.getSpBiImageBase64();

		return image;
	}

	/**
	 * VP 검증
	 * 
	 * @MethodName : verifyVP
	 * @param m400 M400메세지
	 * @return 검증 결과
	 * @throws SpException
	 */
	@Override
	public Boolean verifyVP(M400VO m400) throws SpException {
		LOGGER.debug("m400 : {}", ConfigBean.gson.toJson(m400));

		this.validateM400(m400);

		String trxcode = m400.getTrxcode();
		VP vp = m400.getVp();

		this.validateVp(trxcode, vp);

		// M400 message sequence validation : M310 에서 0002 로 업데이트된 상태여야 함.
		TrxInfoVO trxInfo = trxInfoService.getTrxInfo(trxcode);

		if (!TrxStsCodeEnum.PROFILE_REQ.getVal().equals(trxInfo.getTrxStsCode())) {
			throw new SpException(MipErrorEnum.SP_MSG_SEQ_ERROR, trxcode, "stsCode != 0002");
		}

		Boolean result = null;

		Integer presentType = vp.getPresentType();

		// M400.vp.nonce or zkpNonce 의 값이 profile 생성 시 지정한 것과 동일한지 비교
		if (PresentTypeEnum.DID_VP.getVal() == presentType) {
			if (!vp.getNonce().equals(trxInfo.getNonce())) {
				throw new SpException(MipErrorEnum.SP_MISMATCHING_NONCE, trxcode, "mismatching nonce");
			}

			result = mipDidVpService.verifyVP(trxcode, vp);
		} else if (PresentTypeEnum.ZKP_VP.getVal() == presentType) {
			if (!vp.getZkpNonce().equals(trxInfo.getZkpNonce())) {
				throw new SpException(MipErrorEnum.SP_MISMATCHING_NONCE, trxcode, "mismatching zkpNonce");
			}

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
	 * @param m900 M900 메세지
	 * @throws SpException
	 */
	@Override
	public void sendError(M900VO m900) throws SpException {
		LOGGER.debug("m900 : {}", ConfigBean.gson.toJson(m900));
		
		TrxInfoVO trxInfo = new TrxInfoVO();

		trxInfo.setTrxcode(m900.getTrxcode());
		trxInfo.setTrxStsCode(TrxStsCodeEnum.VERIFY_ERR.getVal());
		trxInfo.setErrorCn(m900.getErrmsg());

		trxInfoService.modifyTrxInfo(trxInfo);
	}

	/**
	 * VP 재검증 - 부인방지
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

		if (PresentTypeEnum.DID_VP.getVal() == presentType) {
			result = mipDidVpService.reVerifyVP(svcCode, vp);
		} else if (PresentTypeEnum.ZKP_VP.getVal() == presentType) {
			result = mipZkpVpService.reVerifyVP(svcCode, vp);
		} else {
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_PRESENT_TYPE);
		}

		return result;
	}
	
	/**
	 * VP data 조회
	 * 
	 * @MethodName : getVPData
	 * @param vp VP
	 * @throws SpException
	 */
	@Override
	public String getVPData(VP vp) throws SpException {
		String vpData = null;
		Integer presentType = vp.getPresentType();
		
		if (PresentTypeEnum.DID_VP.getVal() == presentType) {
			vpData = mipDidVpService.getVPData(vp);
		} else {
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_PRESENT_TYPE);
		}
		return vpData;
	}
	
	@Override
	public String getVPData(String vp) throws SpException{
		return mipDidVpService.getPrivacy(vp).toString();
	}
	
	/**
	 * CI 조회
	 * 
	 * @MethodName : getCI
	 * @param vp VP
	 * @throws SpException
	 */
	@Override
	public String getCI(VP vp) throws SpException {
		String ci = null;
		Integer presentType = vp.getPresentType();
		
		if (PresentTypeEnum.DID_VP.getVal() == presentType) {
			ci = mipDidVpService.getCI(vp);
		} else {
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_PRESENT_TYPE);
		}
		
		return ci;
	}

	/**
	 * M310 메세지 확인
	 * 
	 * @MethodName : validateM310
	 * @param m310 M310 메세지
	 * @throws SpException
	 */
	private void validateM310(M310VO m310) throws SpException {
		String type = m310.getType();
		String version = m310.getVersion();
		String cmd = m310.getCmd();
		String request = m310.getRequest();
		String trxcode = m310.getTrxcode();

		if (ObjectUtils.isEmpty(type))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "m310.type");
		if (ObjectUtils.isEmpty(version))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "m310.version");
		if (ObjectUtils.isEmpty(cmd))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "m310.cmd");
		if (ObjectUtils.isEmpty(request))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "m310.request");
		if (ObjectUtils.isEmpty(trxcode))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "m310.trxcode");

		if (!RequestTypeEnum.CMD_310_REQ.getType().equals(type))
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_TYPE, trxcode, "m310.type");
		if (!RequestTypeEnum.CMD_310_REQ.getVersion().equals(version))
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_MSG_VERSION, trxcode, "m310.version");
		if (!RequestTypeEnum.CMD_310_REQ.getCmd().equals(cmd))
			throw new SpException(MipErrorEnum.SP_INVALID_DATA, trxcode, "m310.cmd");
		if (!RequestTypeEnum.CMD_310_REQ.getRequest().equals(request))
			throw new SpException(MipErrorEnum.SP_INVALID_DATA, trxcode, "m310.request");
	}

	/**
	 * M400 메세지 확인
	 * 
	 * @MethodName : validateM400
	 * @param m400 M400 메세지
	 * @throws SpException
	 */
	private void validateM400(M400VO m400) throws SpException {
		String type = m400.getType();
		String version = m400.getVersion();
		String cmd = m400.getCmd();
		String request = m400.getRequest();
		String trxcode = m400.getTrxcode();
		VP vp = m400.getVp();

		if (ObjectUtils.isEmpty(type))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "m400.type");
		if (ObjectUtils.isEmpty(version))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "m400.version");
		if (ObjectUtils.isEmpty(cmd))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "m400.cmd");
		if (ObjectUtils.isEmpty(request))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "m400.request");
		if (ObjectUtils.isEmpty(trxcode))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "trxcode");
		if (ObjectUtils.isEmpty(vp))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "m400.vp");

		if (!RequestTypeEnum.CMD_400_REQ.getType().equals(type))
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_TYPE, trxcode, "m400.type");
		if (!RequestTypeEnum.CMD_400_REQ.getVersion().equals(version))
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_MSG_VERSION, trxcode, "m400.version");
		if (!RequestTypeEnum.CMD_400_REQ.getCmd().equals(cmd))
			throw new SpException(MipErrorEnum.SP_INVALID_DATA, trxcode, "m400.cmd");
		if (!RequestTypeEnum.CMD_400_REQ.getRequest().equals(request))
			throw new SpException(MipErrorEnum.SP_INVALID_DATA, trxcode, "m400.request");
	}

	/**
	 * VP 정보 확인
	 * 
	 * @MethodName : validateVp
	 * @param trxcode 거래코드
	 * @param vp VP 정보
	 * @throws SpException
	 */
	private void validateVp(String trxcode, VP vp) throws SpException {
		Integer presentType = vp.getPresentType();
		Integer encryptType = vp.getEncryptType();
		Integer keyType = vp.getKeyType();
		String data = vp.getData();
		List<String> authType = vp.getAuthType();
		String did = vp.getDid();
		String type = vp.getType();
		String nonce = vp.getNonce();
		String zkpNonce = vp.getZkpNonce();

		if (ObjectUtils.isEmpty(presentType))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "vp.presentType");
		if (ObjectUtils.isEmpty(encryptType))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "vp.encryptType");
		if (ObjectUtils.isEmpty(keyType))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "vp.keyType");
		if (ObjectUtils.isEmpty(data))
			throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "vp.data");

		if (PresentTypeEnum.DID_VP.getVal() == presentType) {
			if (ObjectUtils.isEmpty(authType))
				throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "vp.authType");
			if (ObjectUtils.isEmpty(did))
				throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "vp.did");
			if (ObjectUtils.isEmpty(nonce))
				throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "vp.nonce");

			for (String strAuthType : vp.getAuthType()) {
				if (AuthTypeEnum.getEnum(strAuthType.toLowerCase()) == null) {
					throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_AUTH_TYPE, trxcode, "vp.authType");
				}
			}

			TrxInfoSvcVO trxInfoSvc = svcService.getTrxInfoSvc(trxcode);
			
			if (ObjectUtils.isEmpty(trxInfoSvc)) {
				throw new SpException(MipErrorEnum.SP_TRXINFO_NOT_FOUND, trxcode);
			}

			// authType validation
			/*
			 * ※ 일반인증: profile의 authType=null ※ 안심인증: profile의 authType='["pin", "face"]' ※
			 * pin, bio, face는 각각 대소문자 구별하지 않음 1) 일반인증으로 profile을 내린 경우 VP 검증 시
			 * M400.vp.authType 에 "pin" or "bio"를 포함하는지 확인 2) 안심인증으로 profile을 내린 경우 VP 검증 시
			 * M400.vp.authType 에 ["pin", "face"] 두 가지 모두를 포함하는지 확인
			 */
			if (!this.isAuthTypeValid(vp, trxInfoSvc.getAuthType())) {
				throw new SpException(MipErrorEnum.SP_MISMATCHING_AUTH_TYPE, trxcode, "vp.authType");
			}
		} else if (PresentTypeEnum.ZKP_VP.getVal() == presentType) {
			if (ObjectUtils.isEmpty(zkpNonce))
				throw new SpException(MipErrorEnum.SP_MISSING_MANDATORY_ITEM, trxcode, "vp.zkpNonce");
		} else {
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_PRESENT_TYPE, trxcode);
		}

		if (PresentTypeEnum.getEnum(presentType) == null)
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_PRESENT_TYPE, trxcode, "vp.presentType");
		if (EncryptTypeEnum.getEnum(encryptType) == null)
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_ENC_TYPE, trxcode, "vp.encryptType");
		if (EncryptKeyTypeEnum.getEnum(keyType) == null)
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_KEY_TYPE, trxcode, "vp.keyType");
		if (!"VERIFY".equals(type))
			throw new SpException(MipErrorEnum.SP_UNSUPPORTED_VP_TYPE, trxcode, "vp.type");
	}

	/**
	 * 인증유형 일치 여부
	 * 
	 * @MethodName : isAuthTypeValid
	 * @param vp VP 정보
	 * @param authType 인증유형
	 * @return 인증유형 일치 여부
	 */
	private Boolean isAuthTypeValid(VP vp, String authType) {
		Boolean authTypeValid = false;

		if (authType == null) {
			// 일반인증
			for (String strAuthType : vp.getAuthType()) {
				AuthTypeEnum authTypeEnum = AuthTypeEnum.getEnum(strAuthType);

				if (AuthTypeEnum.PIN == authTypeEnum || AuthTypeEnum.BIO == authTypeEnum) {
					authTypeValid = true;

					break;
				}
			}
		} else {
			// 안심인증
			Boolean isAuthTypePin = false;
			Boolean isAuthTypeFace = false;

			for (String strAuthType : vp.getAuthType()) {
				if (AuthTypeEnum.PIN == AuthTypeEnum.getEnum(strAuthType)) {
					isAuthTypePin = true;

					continue;
				}

				if (AuthTypeEnum.FACE == AuthTypeEnum.getEnum(strAuthType)) {
					isAuthTypeFace = true;
				}
			}

			if (isAuthTypePin && isAuthTypeFace) {
				authTypeValid = true;
			}
		}

		return authTypeValid;
	}

}
