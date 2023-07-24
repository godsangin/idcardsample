package mip.mva.sp.comm.dao;

import org.apache.ibatis.annotations.Mapper;

import mip.mva.sp.comm.vo.TrxInfoVO;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.dao
 * @FileName    : TrxInfoDAO.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 7.
 * @Description : 거래정보 DAO
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 7.    Min Gi Ju        최초생성
 */
@Mapper
public interface TrxInfoDAO {

	/**
	 * 거래정보 조회
	 * 
	 * @MethodName : selectTrxInfo
	 * @param trxcode 거래코드
	 * @return 거래정보
	 * @throws Exception
	 */
	public TrxInfoVO selectTrxInfo(String trxcode) throws Exception;

	/**
	 * 거래정보 등록
	 * 
	 * @MethodName : insertTrxInfo
	 * @param trxInfo 거래정보
	 * @throws Exception
	 */
	public void insertTrxInfo(TrxInfoVO trxInfo) throws Exception;

	/**
	 * 거래정보 수정
	 * 
	 * @MethodName : updateTrxInfo
	 * @param trxInfo 거래정보
	 * @throws Exception
	 */
	public void updateTrxInfo(TrxInfoVO trxInfo) throws Exception;

	/**
	 * 거래정보 삭제
	 * 
	 * @MethodName : deleteTrxInfo
	 * @param trxcode 거래코드
	 * @throws Exception
	 */
	public void deleteTrxInfo(String trxcode) throws Exception;

}
