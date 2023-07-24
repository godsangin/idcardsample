package mip.mva.sp.comm.service;

import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.vo.M200VO;
import mip.mva.sp.comm.vo.M310VO;
import mip.mva.sp.comm.vo.M320VO;
import mip.mva.sp.comm.vo.M400VO;
import mip.mva.sp.comm.vo.M900VO;
import mip.mva.sp.comm.vo.VP;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.service
 * @FileName    : DirectService.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : Direct 검증 Service
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
public interface DirectService {

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
	public M200VO getM200(String mode, String svcCode, Boolean includeProfile) throws SpException;

	/**
	 * Profile 요청
	 * 
	 * @MethodName : getProfile
	 * @param m310 M310 메세지
	 * @return M310 메세지 + Profile
	 * @throws SpException
	 */
	public M310VO getProfile(M310VO m310) throws SpException;

	/**
	 * 이미지 요청
	 * 
	 * @MethodName : getImage
	 * @param m320 M320 메세지
	 * @return Base64로 인코딩된 Image
	 * @throws SpException
	 */
	public String getImage(M320VO m320) throws SpException;

	/**
	 * VP 검증
	 * 
	 * @MethodName : verifyVP
	 * @param m400 M400메세지
	 * @return 검증 결과
	 * @throws SpException
	 */
	public Boolean verifyVP(M400VO m400) throws SpException;

	/**
	 * 오류 전송
	 * 
	 * @MethodName : sendError
	 * @param m900 M900 메세지
	 * @throws SpException
	 */
	public void sendError(M900VO m900) throws SpException;

	/**
	 * VP 재검증 - 부인방지
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
	 * CI 조회
	 * 
	 * @MethodName : getCI
	 * @param vp VP
	 * @throws SpException
	 */
	public String getCI(VP vp) throws SpException;

}
