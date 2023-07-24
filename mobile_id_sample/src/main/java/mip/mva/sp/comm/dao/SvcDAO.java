package mip.mva.sp.comm.dao;

import org.apache.ibatis.annotations.Mapper;

import mip.mva.sp.comm.vo.SvcVO;
import mip.mva.sp.comm.vo.TrxInfoSvcVO;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.dao
 * @FileName    : SvcDAO.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 7.
 * @Description : 서비스 DAO
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 7.    Min Gi Ju        최초생성
 */
@Mapper
public interface SvcDAO {

	/**
	 * 서비스 조회
	 * 
	 * @MethodName : selectSvc
	 * @param svcCode
	 * @return 서비스정보
	 * @throws Exception
	 */
	public SvcVO selectSvc(String svcCode) throws Exception;

	/**
	 * 서비스 등록
	 * 
	 * @MethodName : insertSvc
	 * @param svc 서비스정보
	 * @throws Exception
	 */
	public void insertSvc(SvcVO svc) throws Exception;

	/**
	 * 거래 & 서비스정보 조회
	 * 
	 * @MethodName : selectTrxInfoSvc
	 * @param trxcode 거래코드
	 * @return 거래 & 서비스정보
	 * @throws Exception
	 */
	public TrxInfoSvcVO selectTrxInfoSvc(String trxcode) throws Exception;

}
