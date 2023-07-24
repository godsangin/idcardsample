package mip.mva.sp.qrcpm.web;

import org.apache.logging.log4j.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.vo.MipApiDataVO;
import mip.mva.sp.comm.vo.T520VO;
import mip.mva.sp.config.ConfigBean;
import mip.mva.sp.qrcpm.service.QrcpmService;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.qrcpm.web
 * @FileName    : QrcpmController.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : QR-CPM 인터페이스 검증 처리 Controller
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@RestController
@RequestMapping("/qrcpm")
public class QrcpmController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QrcpmController.class);

	/** QR-CPM Service */
	private final QrcpmService qrcpmService;

	/**
	 * 생성자
	 * 
	 * @param qrcpmService QR-CPM Service
	 */
	public QrcpmController(QrcpmService qrcpmService) {
		this.qrcpmService = qrcpmService;
	}

	/**
	 * QR-CPM 시작
	 * 
	 * @MethodName : start
	 * @param t520 QR-CPM 정보
	 * @return QR-CPM 정보
	 * @throws SpException
	 */
	@RequestMapping(value = "/start")
	public MipApiDataVO start(@RequestBody T520VO t520) throws SpException {
		LOGGER.debug("QR-CPM 시작!");

		T520VO data = qrcpmService.start(t520);
		
		MipApiDataVO mipApiData = new MipApiDataVO();
		
		mipApiData.setResult(true);
		mipApiData.setData(Base64Util.encode(ConfigBean.gson.toJson(data)));
		
		return mipApiData;
	}

}
