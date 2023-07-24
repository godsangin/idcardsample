package mip.mva.sp.comm.service.impl;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import com.google.gson.JsonSyntaxException;
import com.raonsecure.omnione.core.crypto.GDPCryptoHelperClient;
import com.raonsecure.omnione.core.data.did.v2.DIDs;
import com.raonsecure.omnione.core.data.iw.profile.CommonProfile;
import com.raonsecure.omnione.core.data.iw.profile.EncryptKeyTypeEnum;
import com.raonsecure.omnione.core.data.iw.profile.EncryptTypeEnum;
import com.raonsecure.omnione.core.data.iw.profile.Profile;
import com.raonsecure.omnione.core.data.iw.profile.result.VCVerifyProfileResult;
import com.raonsecure.omnione.core.data.rest.ResultJson;
import com.raonsecure.omnione.core.data.rest.ResultProfile;
import com.raonsecure.omnione.core.eoscommander.crypto.digest.Sha256;
import com.raonsecure.omnione.core.eoscommander.crypto.util.HexUtils;
import com.raonsecure.omnione.core.exception.IWException;
import com.raonsecure.omnione.core.key.IWKeyManagerInterface;
import com.raonsecure.omnione.core.key.IWKeyManagerInterface.OnUnLockListener;
import com.raonsecure.omnione.core.key.KeyManagerFactory;
import com.raonsecure.omnione.core.key.KeyManagerFactory.KeyManagerType;
import com.raonsecure.omnione.core.key.data.AESType;
import com.raonsecure.omnione.core.key.store.IWDIDFile;
import com.raonsecure.omnione.core.util.http.HttpException;
import com.raonsecure.omnione.core.zkp.ZkpConstants;
import com.raonsecure.omnione.core.zkp.data.CredentialDefinition;
import com.raonsecure.omnione.core.zkp.data.Proof;
import com.raonsecure.omnione.core.zkp.data.ProofRequest;
import com.raonsecure.omnione.core.zkp.data.proofrequest.AttributeInfo;
import com.raonsecure.omnione.core.zkp.data.proofrequest.PredicateInfo;
import com.raonsecure.omnione.core.zkp.data.schema.CredentialSchema;
import com.raonsecure.omnione.core.zkp.enums.PredicateType;
import com.raonsecure.omnione.core.zkp.revoc.data.dto.Identifiers;
import com.raonsecure.omnione.core.zkp.revoc.data.dto.ProofVerifyParam;
import com.raonsecure.omnione.core.zkp.util.BigIntegerUtil;
import com.raonsecure.omnione.sdk_server_core.api.ZKPApi;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.BlockChainException;
import com.raonsecure.omnione.sdk_server_core.blockchain.common.ServerInfo;
import com.raonsecure.omnione.sdk_server_core.data.IWApiBaseData;
import com.raonsecure.omnione.sdk_server_core.data.VcResult;
import com.raonsecure.omnione.sdk_server_core.data.response.ResultCode;
import com.raonsecure.omnione.sdk_server_core.data.response.SDKResponse;
import com.raonsecure.omnione.sdk_verifier.VerifyApi;
import com.raonsecure.omnione.sdk_verifier.api.data.SpProfileParam;
import com.raonsecure.omnione.sdk_verifier.api.data.VcVerifyProfileParam;

import mip.mva.sp.comm.enums.MipErrorEnum;
import mip.mva.sp.comm.enums.TrxStsCodeEnum;
import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.service.MipZkpVpService;
import mip.mva.sp.comm.service.SvcService;
import mip.mva.sp.comm.service.TrxInfoService;
import mip.mva.sp.comm.util.Base64Util;
import mip.mva.sp.comm.vo.TrxInfoSvcVO;
import mip.mva.sp.comm.vo.TrxInfoVO;
import mip.mva.sp.comm.vo.VP;
import mip.mva.sp.config.ConfigBean;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.service.impl
 * @FileName    : MipZkpVpServiceImpl.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : 
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@SuppressWarnings("unchecked")
@Service
public class MipZkpVpServiceImpl implements MipZkpVpService, InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(MipZkpVpServiceImpl.class);

	/** 커스텀 프로퍼티 */
	private final ConfigBean configBean;
	/** 서비스 Service */
	private final SvcService svcService;
	/** 거래정보 Service */
	private final TrxInfoService trxInfoService;

	/** 블록체인 서버정보 */
	private ServerInfo blockChainServerInfo;
	/** 키메니져 */
	private IWKeyManagerInterface keyManager;
	/** DID 파일 경로 */
	private String didFilePath;
	/** DID Document */
	private DIDs didDoc;
	/** API Basedata */
	private IWApiBaseData iWApiBaseData;

	/**
	 * 생성자
	 * 
	 * @param configBean 커스텀 프로퍼티
	 * @param svcService 서비스 Service
	 * @param trxInfoService 거래정보 Service
	 */
	public MipZkpVpServiceImpl(ConfigBean configBean, SvcService svcService, TrxInfoService trxInfoService) {
		this.configBean = configBean;
		this.svcService = svcService;
		this.trxInfoService = trxInfoService;
	}

	/**
	 * 초기 설정
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			LOGGER.debug("blockchainServerDomain : {}", configBean.getBlockchainServerDomain());
			LOGGER.debug("keyManagerPath : {}", configBean.getKeymanagerPath());
			LOGGER.debug("spKeyId : {}", configBean.getSpKeyId());
			LOGGER.debug("spDidPath : {}", configBean.getSpDidPath());

			blockChainServerInfo = new ServerInfo(configBean.getBlockchainServerDomain());

			File keyManagerFile = ResourceUtils.getFile(configBean.getKeymanagerPath());
			String keyManagerPath = keyManagerFile.getAbsolutePath();

			keyManager = KeyManagerFactory.getKeyManager(KeyManagerType.DEFAULT, keyManagerPath, configBean.getKeymanagerPassword().toCharArray());

			keyManager.unLock(configBean.getKeymanagerPassword().toCharArray(), new OnUnLockListener() {
				@Override
				public void onSuccess() {
					LOGGER.debug("[OMN] API keyManager onSuccess");
				}

				@Override
				public void onFail(int errCode) {
					LOGGER.error("[OMN] API keyManager onFail", errCode);
				}

				@Override
				public void onCancel() {
					LOGGER.error("[OMN] API keyManager onCancel");
				}
			});

			iWApiBaseData = new IWApiBaseData(blockChainServerInfo, keyManager, configBean.getSpKeyId(), configBean.getSpAccount());

			File didFile = ResourceUtils.getFile(configBean.getSpDidPath());

			didFilePath = didFile.getAbsolutePath();

			IWDIDFile iWDIDFile = new IWDIDFile(didFilePath);

			didDoc = iWDIDFile.getDataFromDIDsV2();
		} catch (Exception e) {
			LOGGER.error("[OMN] API Init Error - Check Log", e);
		}
	}

	/**
	 * Profile 요청
	 * 
	 * @MethodName : getProfile
	 * @param trxInfoSvc 거래 & 서비스정보
	 * @return Base64로 인코딩된 Profile
	 * @throws SpException
	 */
	@Override
	public String getProfile(TrxInfoSvcVO trxInfoSvc) throws SpException {
		LOGGER.debug("trxInfoSvc : {}", ConfigBean.gson.toJson(trxInfoSvc));

		ResultProfile resultJson = new ResultProfile();

		String trxcode = trxInfoSvc.getTrxcode();
		String nonce = trxInfoSvc.getNonce();
		String spName = trxInfoSvc.getSpName();
		String serviceName = trxInfoSvc.getServiceName();
		String callBackUrl = trxInfoSvc.getCallBackUrl();
		
		// 구간 암복호화로 RSA 사용하는 경우 nonce 설정하지 않아도 됨
		if (nonce == null) {
			byte[] tempNonce = null;

			try {
				tempNonce = new GDPCryptoHelperClient().generateNonce();
			} catch (IWException e) {
				LOGGER.error(e.getMessage(), e);
				
				throw new SpException(MipErrorEnum.UNKNOWN_ERROR, trxcode, e.getErrorMsg());
			}

			nonce = Sha256.from(tempNonce).toString();
			
			LOGGER.debug("nonce : {}", nonce);
		}

		// profile value setting
		Profile profile = new Profile();
		
		profile.setPresentType(2); // 2 = ZKP VP
		profile.setEncryptType(EncryptTypeEnum.AES_256);
		profile.setKeyType(EncryptKeyTypeEnum.ALGORITHM_RSA);
		profile.setSpName(spName);
		profile.setName(serviceName);
		profile.setCallBackUrl(callBackUrl);
		profile.setNonce(nonce);
		profile.setType("VERIFY");
		
		ProofRequest proofRequest = this.createProofRequest(trxInfoSvc);
		
		profile.setProofRequest(proofRequest);
		
		// 3번째 param 에는 서명값 생성에 사용할 키 아이디를 설정
		SpProfileParam spProfileParam = new SpProfileParam(blockChainServerInfo, keyManager, configBean.getSpKeyId(), null, profile, didDoc.getId(), configBean.getSpAccount(), true);
		
		// 구간 암호화로 RSA 암/복호화 또는 ECIES 암/복호화에 임시키를 사용하는 경우 설정
		if (profile.getKeyType() == EncryptKeyTypeEnum.ALGORITHM_RSA.getVal()) {
			try {
				spProfileParam.setEncPublicKey(keyManager.getPublicKey(configBean.getSpRsaKeyId()));
			} catch (IWException e) {
				LOGGER.error(e.getMessage(), e);
				
				throw new SpException(MipErrorEnum.UNKNOWN_ERROR, trxcode, e.getErrorMsg());
			}
		}
		
		String zkpProfileJson = null;

		try {
			zkpProfileJson = VerifyApi.makeZkpProfile(spProfileParam);
		} catch (BlockChainException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.UNKNOWN_ERROR, trxcode, e.getErrorMsg());
		}

		LOGGER.debug("profile zkpProfileJson: {}", zkpProfileJson);

		resultJson.setProfileJson(zkpProfileJson);
		resultJson.setResult(true);

		CommonProfile commonProfile = ConfigBean.gson.fromJson(Base64Util.decode(resultJson.getProfileBase64()), CommonProfile.class);

		TrxInfoVO trxInfo = new TrxInfoVO();

		trxInfo.setTrxcode(trxcode);
		trxInfo.setTrxStsCode(TrxStsCodeEnum.PROFILE_REQ.getVal());
		trxInfo.setZkpNonce(commonProfile.getProfile().getProofRequest().getNonce().toString());

		trxInfoService.modifyTrxInfo(trxInfo);

		return resultJson.getProfileBase64();
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
		LOGGER.debug("trxcode : {}, vp : {}", trxcode, ConfigBean.gson.toJson(vp));

		Boolean result = false;

		Integer encryptType = vp.getEncryptType();
		Integer keyType = vp.getKeyType();
		String type = vp.getType();
		String data = vp.getData();
		String zkpNonce = vp.getZkpNonce();

		// VP 검증 Start
		VCVerifyProfileResult vCVerifyProfileResult = new VCVerifyProfileResult();

		vCVerifyProfileResult.setEncryptType(encryptType);
		vCVerifyProfileResult.setKeyType(keyType);
		vCVerifyProfileResult.setType(type);
		vCVerifyProfileResult.setData(data);
		vCVerifyProfileResult.setZkpNonce(zkpNonce);

		EncryptKeyTypeEnum keyTypeEnum = EncryptKeyTypeEnum.getEnum(vCVerifyProfileResult.getKeyType());

		if (keyTypeEnum == EncryptKeyTypeEnum.ALGORITHM_RSA) {
			try {
				AESType aESType = vCVerifyProfileResult.getEncryptType() == 1 ? AESType.AES128 : AESType.AES256;

				byte[] vpDataByte = keyManager.rsaDecrypt(configBean.getSpRsaKeyId(), HexUtils.toBytes(vCVerifyProfileResult.getData()), aESType);

				data = new String(vpDataByte, StandardCharsets.UTF_8);

				LOGGER.debug("data : {}", data);
			} catch (IWException e) {
				LOGGER.error(e.getMessage(), e);
				
				throw new SpException(MipErrorEnum.UNKNOWN_ERROR, trxcode, "decData");
			}
		}

		Proof proof = ConfigBean.gson.fromJson(data, Proof.class);
		
		TrxInfoSvcVO trxInfoSvc = svcService.getTrxInfoSvc(trxcode);
		
		if (ObjectUtils.isEmpty(trxInfoSvc)) {
			throw new SpException(MipErrorEnum.SP_TRXINFO_NOT_FOUND, trxcode);
		}
		
		BigInteger verifierNonce = new BigInteger(zkpNonce);

		List<ProofVerifyParam> proofVerifyParams = new LinkedList<>();

		for (Identifiers identifiers : proof.getIdentifiers()) {
			String schemaId = identifiers.getSchemaId();
			CredentialSchema credentialSchema = (CredentialSchema) new ZKPApi().getCredentialSchema(iWApiBaseData, schemaId).getResultData();
			String credDefId = identifiers.getCredDefId();
			CredentialDefinition credentialDefinition = (CredentialDefinition) new ZKPApi().getCredentialDefinition(iWApiBaseData, credDefId).getResultData();
			ProofVerifyParam proofVerifyParam = new ProofVerifyParam.Builder().setSchema(credentialSchema).setCredentialDefinition(credentialDefinition).build();

			proofVerifyParams.add(proofVerifyParam);
		}

		ProofRequest proofRequest = this.createProofRequest(trxInfoSvc);
		SDKResponse sDKResponse = new ZKPApi().verifyProof(iWApiBaseData, proof, proofRequest, proofVerifyParams, verifierNonce);

		ResultJson resultJson = new ResultJson();

		if (!sDKResponse.isSuccess()) {
			resultJson.setResult(false);
			resultJson.setErrorCode(sDKResponse.getResultCode());
			resultJson.setErrorMsg(sDKResponse.getResultMsg());
		} else {
			resultJson.setResult(true);
		}

		if (resultJson != null && resultJson.isResult()) {
			result = true;
		}
		// VP 검증 End
		
		// Nonce 위변조 확인 Start
		TrxInfoVO curTrxInfo = trxInfoService.getTrxInfo(trxcode);
		
		if (curTrxInfo == null) {
			throw new SpException(MipErrorEnum.SP_TRXCODE_NOT_FOUND, trxcode);
		}
		
		String curVpVerifyResult = curTrxInfo.getVpVerifyResult();
		
		// 이미 verify 된 trx
		if ("Y".equals(curVpVerifyResult)) {
			throw new SpException(MipErrorEnum.SP_MSG_SEQ_ERROR, trxcode, "verifyResult == Y");
		}
		
		String profileNonce = curTrxInfo.getZkpNonce();
		
		LOGGER.debug("profileNonce : {}, vpNonce : {}", profileNonce, zkpNonce);
		
		if (ObjectUtils.isEmpty(zkpNonce) || zkpNonce.indexOf(profileNonce) == -1) {
			throw new SpException(MipErrorEnum.SP_MISMATCHING_NONCE, trxcode);
		}
		// Nonce 위변조 확인 End

		String vpVerifyResult = result ? "Y" : "N";

		TrxInfoVO trxInfo = new TrxInfoVO();

		trxInfo.setTrxcode(trxcode);
		trxInfo.setTrxStsCode(TrxStsCodeEnum.VERIFY_COM.getVal());
		trxInfo.setVpVerifyResult(vpVerifyResult);

		trxInfoService.modifyTrxInfo(trxInfo);

		return result;
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
		VCVerifyProfileResult vcVerifyProfileResult = new VCVerifyProfileResult();

		vcVerifyProfileResult.setEncryptType(vp.getEncryptType());
		vcVerifyProfileResult.setKeyType(vp.getKeyType());
		vcVerifyProfileResult.setType(vp.getType());
		vcVerifyProfileResult.setData(vp.getData());
		vcVerifyProfileResult.setZkpNonce(vp.getZkpNonce());

		// rsakeyId - 서명키 설정
		VcVerifyProfileParam vcVerifyParam = new VcVerifyProfileParam(blockChainServerInfo, keyManager, configBean.getSpKeyId(), configBean.getSpAccount(), vcVerifyProfileResult, didFilePath);

		// vp 최초 검증 시점에 사용 rsakeyId를 설정
		EncryptKeyTypeEnum keyTypeEnum = EncryptKeyTypeEnum.getEnum(vcVerifyProfileResult.getKeyType());

		if (keyTypeEnum == EncryptKeyTypeEnum.ALGORITHM_RSA) {
			vcVerifyParam.setEncryptKeyId(configBean.getSpRsaKeyId());
		}

		// 서비스 검증 설정
		vcVerifyParam.setServiceCode(svcCode);
		// Issuer 서명 검증 옵션
		vcVerifyParam.setCheckVC(configBean.getIssuerCheckVc());
		// setCheckVC(true) 인 경우 설정
		vcVerifyParam.setIssuerProofVerifyCheck(configBean.getIssuerCheckVc());

		VcResult vcResult = null;

		try {
			vcResult = VerifyApi.checkUserProof(vcVerifyParam, false);
		} catch (BlockChainException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.UNKNOWN_ERROR, null, e.getErrorMsg());
		} catch (HttpException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.UNKNOWN_ERROR, null, e.getErrorMsg());
		}

		if (vcResult.getStatus().equals("1")) {
			LOGGER.debug("VP timezone : {}", vcVerifyProfileResult.getTimezone());

			// vp 생 시간 추출
			if (vcResult.getReqVp().getProofs() != null) {// 멀티 서명인 경우
				LOGGER.debug("VP 생성시간 : {}", vcResult.getReqVp().getProofs().get(0).getCreated());
			} else {// 단일 서명인 경우
				LOGGER.debug("VP 생성시간 : {}", vcResult.getReqVp().getProof().getCreated());
			}
		}

		return vcResult.getStatus().equals("1");
	}

	/**
	 * ProofRequest 생성
	 * 
	 * @MethodName : createProofRequest
	 * @param trxInfoSvc 거래 & 서비스정보
	 * @return ProofRequest
	 * @throws SpException
	 */
	private ProofRequest createProofRequest(TrxInfoSvcVO trxInfoSvc) throws SpException {
		String trxcode = trxInfoSvc.getTrxcode();
		
		// restrictionList 생성 Start
		List<Map<String, String>> restrictionList = new ArrayList<Map<String, String>>();
		
		SDKResponse sDKResponse = new SDKResponse();
		
		SDKResponse credentialListSDKResponse = null;
		SDKResponse schemaListSDKResponse = null;
		
		List<Map<String, Object>> credentialList = null;
		List<Map<String, Object>> schemaList = null;
		
		List<String> credentialDataList = new ArrayList<String>();
		
		try {
			String zkpSchemaName = configBean.getZkpSchemaName();
		
			credentialListSDKResponse = new ZKPApi().getCredentialListBySchemaName(iWApiBaseData, zkpSchemaName);
			schemaListSDKResponse = new ZKPApi().getSchemaListBySchemaName(iWApiBaseData, zkpSchemaName);
			
			if (!credentialListSDKResponse.isSuccess() || !schemaListSDKResponse.isSuccess()) {
				throw new SpException(MipErrorEnum.SP_SCHEMA_NAME_NOT_EXIST, trxcode);
			}
			
			credentialList = (List<Map<String, Object>>) credentialListSDKResponse.getResultData();
			schemaList = (List<Map<String, Object>>) schemaListSDKResponse.getResultData();
			
			for (Map<String, Object> credentail : credentialList) {
				for (Map<String, Object> schema : schemaList) {
					if(credentail.get("schemaId").equals(schema.get("id"))) {
						credentialDataList.add(credentail.get("id").toString());
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.UNKNOWN_ERROR, trxcode, "credential");
		}
		
		sDKResponse.setResultData(credentialDataList);
		sDKResponse.setResult(ResultCode.SUCCESS);

		List<String> creDefIdList = (List<String>) sDKResponse.getResultData();

		for (String creDefId : creDefIdList) {
			Map<String, String> restriction = new HashMap<String, String>();

			restriction.put("cred_def_id", creDefId);

			restrictionList.add(restriction);
		}
		// restrictionList 생성 End
		
		// attributes 생성 Start
		String attrListStr = trxInfoSvc.getAttrList();
		List<String> attrList = null;

		try {
			attrList = ConfigBean.gson.fromJson(attrListStr, ArrayList.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, trxcode, "attrList");
		}

		Map<String, AttributeInfo> attributes = new HashMap<String, AttributeInfo>();
		
		try {
			if (!ObjectUtils.isEmpty(attrList)) {
				for (String attr : attrList) {
					AttributeInfo attributeInfo = new AttributeInfo();

					attributeInfo.setName(attr);

					for (Map<String, String> restriction : restrictionList) {
						attributeInfo.addRestriction(restriction);
					}

					attributes.put("attribute_referent_" + (attrList.indexOf(attr) + 1), attributeInfo);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.UNKNOWN_ERROR, trxcode, "attributes");
		}
		// attributes 생성 End
		
		// predicates 생성 Start
		String predListStr = trxInfoSvc.getPredList();
		List<String> predList = null;

		try {
			predList = ConfigBean.gson.fromJson(predListStr, ArrayList.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, trxcode, "predList");
		}

		Map<String, PredicateInfo> predicates = new HashMap<String, PredicateInfo>();

		int index = 1;

		try {
			if (!ObjectUtils.isEmpty(predList)) {
				for (Object obj : predList) {
					Map<String, Object> mapObj = (Map<String, Object>) obj;
					String key = mapObj.keySet().iterator().next();
					Map<String, Object> info = (Map<String, Object>) mapObj.get(key);
					String type = (String) info.get("type");
					Integer value = Integer.parseInt((String) info.get("value"));

					PredicateInfo predicateInfo = new PredicateInfo();

					predicateInfo.setPType(PredicateType.valueOf(type));
					predicateInfo.setName(key);

					if (key.equals("zkpbirth")) {
						LocalDateTime now = LocalDateTime.now();
						LocalDateTime targetDay = now.plusYears(value);

						int year = targetDay.getYear();
						int month = targetDay.getMonthValue();
						int day = targetDay.getDayOfMonth();

						String targetDayStr = String.format("%04d%02d%02d", year, month, day);
						int targetDayInt = Integer.parseInt(targetDayStr);

						predicateInfo.setPValue(targetDayInt);
					} else {
						predicateInfo.setPValue(value);
					}

					for (Map<String, String> restrictionValue : restrictionList) {
						predicateInfo.addRestriction(restrictionValue);
					}

					predicates.put("predicate_referent_" + index, predicateInfo);

					index++;
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.UNKNOWN_ERROR, trxcode, "predicates");
		}
		// predicates 생성 End
		
		// proofRequest 생성 Start
		BigInteger nonce = new BigIntegerUtil().createRandomBigInteger(ZkpConstants.LARGE_NONCE);
		SDKResponse response = new ZKPApi().createProofRequest(null, attributes, predicates, nonce);
		ProofRequest proofRequest = (ProofRequest) response.getResultData();
		// proofRequest 생성 End

		return proofRequest;
	}

}
