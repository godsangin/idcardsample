package mip.mva.sp.comm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mip.mva.sp.comm.dao.TrxInfoDAO;
import mip.mva.sp.comm.enums.MipErrorEnum;
import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.service.TrxInfoService;
import mip.mva.sp.comm.vo.TrxInfoVO;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.service.impl
 * @FileName    : TrxInfoServiceImpl.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : 거래정보 ServiceImpl
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@Service
@Transactional
public class TrxInfoServiceImpl implements TrxInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrxInfoServiceImpl.class);
	
	/** 거래정보 DAO */
	private final TrxInfoDAO trxInfoDAO;

	/**
	 * 거래정보 DAO
	 * 
	 * @param trxInfoDAO 거래정보 DAO
	 */
	public TrxInfoServiceImpl(TrxInfoDAO trxInfoDAO) {
		this.trxInfoDAO = trxInfoDAO;
	}

	/**
	 * 거래정보 조회
	 * 
	 * @MethodName : getTrxInfo
	 * @param trxcode 거래코드
	 * @return 거래정보
	 * @throws SpException
	 */
	@Override
	public TrxInfoVO getTrxInfo(String trxcode) throws SpException {
		TrxInfoVO trxInfo = null;

		try {
			trxInfo = trxInfoDAO.selectTrxInfo(trxcode);

			if (trxInfo == null) {
				throw new SpException(MipErrorEnum.SP_TRXCODE_NOT_FOUND, trxcode);
			}
		} catch (SpException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_DB_ERROR, trxcode, "trxInfo select");
		}

		return trxInfo;
	}

	/**
	 * 거래정보 존재 확인
	 *
	 * @MethodName : checkTrxInfoExist
	 * @param trxcode 거래코드
	 * @throws SpException
	 */
	@Override
	public void checkTrxInfoExist(String trxcode) throws SpException {
		try {
			//거래내역 조회 - 중복 확인

			System.out.println("여기지 이놈아!!!!!!!!!!!!!!!");
			if (trxInfoDAO.selectTrxInfo(trxcode) != null) {
				throw new SpException(MipErrorEnum.SP_ALREADY_EXIST_TRXCODE, trxcode);
			}
		} catch (SpException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw e;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_DB_ERROR, trxcode, "trxInfo check");
		}
		System.out.println("아닌데 븅신데??????");
	}

	/**
	 * 거래정보 등록
	 * 
	 * @MethodName : registTrxInfo
	 * @param trxInfo 거래정보
	 * @throws SpException
	 */
	@Override
	public void registTrxInfo(TrxInfoVO trxInfo) throws SpException {
		try {
			trxInfoDAO.insertTrxInfo(trxInfo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_DB_ERROR, trxInfo.getTrxcode(), "trxInfo insert");
		}
	}

	/**
	 * 거래정보 수정
	 * 
	 * @MethodName : modifyTrxInfo
	 * @param trxInfo 거래정보
	 * @throws SpException
	 */
	@Override
	public void modifyTrxInfo(TrxInfoVO trxInfo) throws SpException {
		try {
			trxInfoDAO.updateTrxInfo(trxInfo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_DB_ERROR, trxInfo.getTrxcode(), "trxInfo update");
		}
	}

	/**
	 * 거래정보 삭제
	 * 
	 * @MethodName : removeTrxInfo
	 * @param trxcode 거래코드
	 * @throws SpException
	 */
	@Override
	public void removeTrxInfo(String trxcode) throws SpException {
		try {
			trxInfoDAO.deleteTrxInfo(trxcode);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_DB_ERROR, trxcode, "trxInfo delete");
		}
	}

}
