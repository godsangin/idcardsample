package mip.mva.sp.push.service;

import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.vo.T540VO;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.push.service
 * @FileName    : PushService.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : 푸시 인터페이스 검증 처리 Service
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
public interface PushService {

	/**
	 * 푸시 시작
	 * 
	 * @MethodName : start
	 * @param t540 푸시 정보
	 * @return 푸시 정보 + Base64로 인코딩된 M200 메시지
	 * @throws SpException
	 */
	public T540VO start(T540VO t540) throws SpException;

}
