package mip.mva.sp.comm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import mip.mva.sp.comm.enums.MipErrorEnum;
import mip.mva.sp.comm.exception.SpException;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.util
 * @FileName    : HttpUtil.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : Http Call Util
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
public class HttpUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * Http Call(POST) 실행
	 * 
	 * @MethodName : executeHttpPost
	 * @param url URL
	 * @param param 파라미터
	 * @return 결과
	 * @throws SpException
	 */
	public static String executeHttpPost(String url, Object param) throws SpException {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

		try {
			response = restTemplate.postForEntity(url, param, String.class);
		} catch (RestClientException e) {
			LOGGER.error(e.getMessage(), e);
			
			throw new SpException(MipErrorEnum.UNKNOWN_ERROR, null, e.getMessage());
		}

		return response.getBody();
	}

}
