package mip.mva.sp.comm.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonSyntaxException;

import mip.mva.sp.comm.enums.MipErrorEnum;
import mip.mva.sp.comm.exception.SpException;
import mip.mva.sp.comm.service.DirectService;
import mip.mva.sp.comm.service.TrxInfoService;
import mip.mva.sp.comm.util.Base64Util;
import mip.mva.sp.comm.vo.M310VO;
import mip.mva.sp.comm.vo.M320VO;
import mip.mva.sp.comm.vo.M400VO;
import mip.mva.sp.comm.vo.M900VO;
import mip.mva.sp.comm.vo.MipApiDataVO;
import mip.mva.sp.comm.vo.TrxInfoVO;
import mip.mva.sp.comm.vo.VP;
import mip.mva.sp.config.ConfigBean;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.web
 * @FileName    : MipController.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : MIP 검증 Controller
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/mip")
public class MipController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MipController.class);

	/** Direct Service */
	private final DirectService directService;
	/** 거래정보 Service */
	private final TrxInfoService trxInfoService;
	
	/**
	 * 생성자
	 * 
	 * @param directService Direct Service
	 * @param trxInfoService 거래정보 Service
	 */
	public MipController(DirectService directService, TrxInfoService trxInfoService) {
		this.directService = directService;
		this.trxInfoService = trxInfoService;
	}

	/**
	 * Profile 요청
	 * 
	 * @param mipApiData {"data":"Base64로 인코딩된 M310 메시지"}
	 * @return {"result":true, "data":"Base64로 인코딩된 M310 메시지"}
	 * @throws SpException
	 */
	@RequestMapping("/profile")
	public MipApiDataVO getProfile(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("Profile 요청!");

		String data = Base64Util.decode(mipApiData.getData());

		M310VO m310 = null;

		try {
			m310 = ConfigBean.gson.fromJson(data, M310VO.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, null, "m310");
		}

		m310 = directService.getProfile(m310);

		mipApiData.setResult(true);
		mipApiData.setData(Base64Util.encode(ConfigBean.gson.toJson(m310)));

		return mipApiData;
	}

	/**
	 * 이미지 요청
	 * 
	 * @param mipApiData {"data":"Base64로 인코딩된 M320 메시지"}
	 * @return {"result":true, "data":"Base64로 인코딩된 Image"}
	 * @throws SpException
	 */
	@RequestMapping("/image")
	public MipApiDataVO getImage(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("BI Image 요청!");

		String data = Base64Util.decode(mipApiData.getData());

		M320VO m320 = null;

		try {
			m320 = ConfigBean.gson.fromJson(data, M320VO.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, null, "m320");
		}

		String image = directService.getImage(m320);

		mipApiData.setResult(true);
		mipApiData.setData(image);

		return mipApiData;
	}

	/**
	 * VP 검증
	 * 
	 * @param mipApiData {"data":"Base64로 인코딩된 M400 메시지"}
	 * @return {"result":true}
	 * @throws SpException
	 */
	@RequestMapping("/vp")
	public MipApiDataVO verifyVP(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("VP 검증!");

		String data = Base64Util.decode(mipApiData.getData());

		M400VO m400 = null;

		try {
			m400 = ConfigBean.gson.fromJson(data, M400VO.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, null, "m400");
		}

		directService.verifyVP(m400);

		mipApiData.setResult(true);

		return mipApiData;
	}

	/**
	 * 오류 전송
	 * 
	 * @param mipApiData {"data":"Base64로 인코딩된 오류 메시지"}
	 * @return {"result":true}
	 * @throws SpException
	 */
	@RequestMapping("/error")
	public MipApiDataVO sendError(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("오류 전송!");

		String data = Base64Util.decode(mipApiData.getData());

		M900VO m900 = null;

		try {
			m900 = ConfigBean.gson.fromJson(data, M900VO.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, null, "m900");
		}

		directService.sendError(m900);

		mipApiData.setResult(true);

		return mipApiData;
	}

	/**
	 * 거래상태 조회
	 * 
	 * @param mipApiData {"data":"Base64로 인코딩된 TrxInfoVO"}
	 * @return {"result":true, "data":"Base64로 인코딩된 TrxInfoVO"}
	 * @throws SpException
	 */
	@RequestMapping(value = "/trxsts")
	public MipApiDataVO getTrxsts(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("거래상태 조회!");

		String data = Base64Util.decode(mipApiData.getData());

		TrxInfoVO trxInfo = null;

		try {
			trxInfo = ConfigBean.gson.fromJson(data, TrxInfoVO.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, null, "trxInfo");
		}

		trxInfo = trxInfoService.getTrxInfo(trxInfo.getTrxcode());
		
		System.out.println("my privacy==" + directService.getVPData(trxInfo.getVp()));
		
		mipApiData.setResult(true);
		mipApiData.setData(Base64Util.encode(ConfigBean.gson.toJson(trxInfo)));

		return mipApiData;
	}

	/**
	 * VP 재검증
	 * 
	 * @param mipApiData {"data":"Base64로 인코딩된 서비스코드 & VP"}
	 * @return {"result":true}
	 * @throws SpException
	 */
	@RequestMapping(value = "/revp", method = { RequestMethod.POST })
	public MipApiDataVO reVerifyVP(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("VP 재검증!");

		String data = Base64Util.decode(mipApiData.getData());

		String svcCode = null;
		VP vp = null;

		try {
			Map<String, String> dataMap = ConfigBean.gson.fromJson(data, HashMap.class);

			svcCode = dataMap.get("svcCode");
			
			LOGGER.debug("VP : {}", dataMap.get("vp"));

			vp = ConfigBean.gson.fromJson(dataMap.get("vp"), VP.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, null, "svcCode, vp");
		}

		mipApiData.setResult(directService.reVerifyVP(svcCode, vp));

		return mipApiData;
	}
	
	/**
	 * VP data 조회
	 * 
	 * @param mipApiData {"data":"base64로 인코딩된 VP"}
	 * @return {"result":true, "data":"base64로 인코딩된 VP data"}
	 * @throws SpException
	 */
	@RequestMapping(value = "/vpdata", method = { RequestMethod.POST })
	public MipApiDataVO getVPData(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("VP data 조회!");
		
		String data = Base64Util.decode(mipApiData.getData());

		VP vp = null;

		try {
			Map<String, Object> dataMap = ConfigBean.gson.fromJson(data, HashMap.class);

			vp = ConfigBean.gson.fromJson((String) dataMap.get("vp"), VP.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, null, "vp");
		}

		String vpData = directService.getVPData(vp);

		mipApiData.setResult(true);
		mipApiData.setData(Base64Util.encode(vpData));

		return mipApiData;
	}
	
	/**
	 * CI 조회
	 * 
	 * @param mipApiData {"data":"base64로 인코딩된 VP"}
	 * @return {"result":true, "data":"base64로 인코딩된 CI"}
	 * @throws SpException
	 */
	@RequestMapping(value = "/ci", method = { RequestMethod.POST })
	public MipApiDataVO getCI(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("CI 조회!");
		
		String data = Base64Util.decode(mipApiData.getData());

		VP vp = null;

		try {
			Map<String, String> dataMap = ConfigBean.gson.fromJson(data, HashMap.class);

			vp = ConfigBean.gson.fromJson(dataMap.get("vp"), VP.class);
		} catch (JsonSyntaxException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, null, "vp");
		}

		String ci = directService.getCI(vp);

		mipApiData.setResult(true);
		mipApiData.setData(Base64Util.encode(ci));

		return mipApiData;
	}

	/**
	 * join 후처리
	 * 
	 * @param mipApiData {"data": "Base64로 인코딩된 join 후처리 정보"}
	 * @return {"result": 성공여부}
	 * @throws SpException
	 */
	@RequestMapping(value = "/after/join")
	public MipApiDataVO afterJoin(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("join 후처리!");

		String data = Base64Util.decode(mipApiData.getData());

		LOGGER.debug("data : {}", data);

		mipApiData.setResult(true);

		return mipApiData;
	}

	/**
	 * verify 후처리
	 * 
	 * @param mipApiData {"data": "Base64로 인코딩된 verify 후처리 정보"}
	 * @return {"result": 성공여부}
	 * @throws SpException
	 */
	@RequestMapping(value = "/after/verify")
	public MipApiDataVO afterVerify(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("verify 후처리!");

		String data = Base64Util.decode(mipApiData.getData());

		LOGGER.debug("data : {}", data);

		mipApiData.setResult(true);

		return mipApiData;
	}

	/**
	 * profile 후처리
	 * 
	 * @param mipApiData {"data": "Base64로 인코딩된 profile 후처리 정보"}
	 * @return {"result": 성공여부}
	 * @throws SpException
	 */
	@RequestMapping(value = "/after/profile")
	public MipApiDataVO afterProfile(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("profile 후처리!");

		String data = Base64Util.decode(mipApiData.getData());

		LOGGER.debug("data : {}", data);

		mipApiData.setResult(true);

		return mipApiData;
	}

	/**
	 * vp 후처리
	 * 
	 * @param mipApiData {"data": "Base64로 인코딩된 vp 후처리 정보"}
	 * @return {"result": 성공여부}
	 * @throws SpException
	 */
	@RequestMapping(value = "/after/vp")
	public MipApiDataVO afterVp(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("vp 후처리!");

		String data = Base64Util.decode(mipApiData.getData());

		LOGGER.debug("data : {}", data);

		mipApiData.setResult(true);

		return mipApiData;
	}

	/**
	 * error 후처리
	 * 
	 * @param mipApiData {"data": "Base64로 인코딩된 error 후처리 정보"}
	 * @return {"result": 성공여부}
	 * @throws SpException
	 */
	@RequestMapping(value = "/after/error")
	public MipApiDataVO afterError(@RequestBody MipApiDataVO mipApiData) throws SpException {
		LOGGER.debug("error 후처리!");

		String data = Base64Util.decode(mipApiData.getData());

		LOGGER.debug("data : {}", data);

		mipApiData.setResult(true);

		return mipApiData;
	}

}
