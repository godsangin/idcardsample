package mip.mva.sp.comm.service;

import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.vo.VP;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.service
 * @FileName    : ProxyService.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : Proxy 검증 Service
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
public interface ProxyService {

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
	 * @param trxcode 거래코드
	 * @return Base64로 인코딩된 Profile
	 * @throws SpException
	 */
	public String getProfile(String trxcode) throws SpException;

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
	 * 오류 전송
	 * 
	 * @MethodName : sendError
	 * @param trxcode 거래코드
	 * @param errmsg 오류 메세지
	 * @throws SpException
	 */
	public void sendError(String trxcode, String errmsg) throws SpException;

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

}
