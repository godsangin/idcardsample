package mip.mva.sp.qrmpm.web;

import org.apache.logging.log4j.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.vo.MipApiDataVO;
import mip.mva.sp.comm.vo.T510VO;
import mip.mva.sp.config.ConfigBean;
import mip.mva.sp.qrmpm.service.QrmpmService;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.qrmpm.web
 * @FileName    : QrmpmController.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : QR-MPM 인터페이스 검증 처리 Controller
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@RestController
@RequestMapping("/qrmpm")
public class QrmpmController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QrmpmController.class);

	/** QR-MPM Service */
	private final QrmpmService qrmpmService;

	/**
	 * 생성자
	 * 
	 * @param qrmpmService QR-MPM Service
	 */
	public QrmpmController(QrmpmService qrmpmService) {
		this.qrmpmService = qrmpmService;
	}

	/**
	 * QR-MPM 시작
	 * 
	 * @MethodName : start
	 * @param t510 QR-MPM 정보
	 * @return QR-MPM 정보 + Base64로 인코딩된 M200 메시지
	 * @throws SpException
	 */
	@RequestMapping(value = "/start")
	public MipApiDataVO start(@RequestBody T510VO t510) throws SpException {
		LOGGER.debug("QR-MPM 시작!");
		
		T510VO data = qrmpmService.start(t510);
		
		MipApiDataVO mipApiData = new MipApiDataVO();
		
		mipApiData.setResult(true);
		mipApiData.setData(Base64Util.encode(ConfigBean.gson.toJson(data)));
		
		return mipApiData;
	}

}
