package mip.mva.sp.app2app.service;

import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.vo.T530VO;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.app2app.service
 * @FileName    : App2AppService.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 8.
 * @Description : App to App 인터페이스 검증 처리 Service
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 8.    Min Gi Ju        최초생성
 */
public interface App2AppService {

	/**
	 * App to App 시작
	 * 
	 * @MethodName : start
	 * @param t530 App to App 정보
	 * @return App to App 정보 + Base64로 인코딩된 M200 메시지
	 * @throws SpException
	 */
	public T530VO start(T530VO t530) throws SpException;

}
