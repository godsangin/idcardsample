package mip.mva.sp.comm.service;

import java.util.Map;

import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.vo.TrxInfoSvcVO;
import mip.mva.sp.comm.vo.VP;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.service
 * @FileName    : MipDidVpService.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : VP 검증 Service
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
public interface MipDidVpService {

	/**
	 * DID Assertion 생성
	 * 
	 * @MethodName : makeDIDAssertion
	 * @param nonce Nonce
	 * @return DID Assertion
	 * @throws SpException
	 */
	public String makeDIDAssertion(String nonce) throws SpException;

	/**
	 * Profile 요청
	 * 
	 * @MethodName : getProfile
	 * @param trxInfoSvc 거래 & 서비스정보
	 * @return Base64로 인코딩된 Profile
	 * @throws SpException
	 */
	public String getProfile(TrxInfoSvcVO trxInfoSvc) throws SpException;

	/**
	 * VP 검증
	 * 
	 * @MethodName : verifyVP
	 * @param trxcode 거래코드
	 * @param vp VP 정보
	 * @return 검증 결과
	 * @throws SpException
	 */
	public Boolean verifyVP(String trxcode, VP vp) throws SpException;

	/**
	 * VP 재검증
	 * 
	 * @MethodName : reVerifyVP
	 * @param svcCode 서비스코드
	 * @param vp VP 정보
	 * @return 검증 결과
	 * @throws SpException
	 */
	public Boolean reVerifyVP(String svcCode, VP vp) throws SpException;
	
	/**
	 * VP data 조회
	 * 
	 * @MethodName : getVPData
	 * @param vp VP
	 * @throws SpException
	 */
	public String getVPData(VP vp) throws SpException;
	
	/**
	 * Privacy 조회
	 * 
	 * @MethodName : getPrivacy
	 * @param vpData VP data 문자열(JSON)
	 * @throws SpException
	 */
	public Map<String, String> getPrivacy(String vpData) throws SpException;
	
	/**
	 * CI 조회
	 * 
	 * @MethodName : getCI
	 * @param vp VP
	 * @throws SpException
	 */
	public String getCI(VP vp) throws SpException;
	
	/**
	 * 이미지 변환(Hex String to byte Array)
	 * 
	 * @MethodName : transImageHexToByte
	 * @param imageData String
	 * @return byte[] 이미지
	 * @throws SpException
	 */
	public byte[] transImageHexToByte(String imageData) throws SpException;

}
