package mip.mva.sp.comm.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.util
 * @FileName    : SpringUtil.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : Spring Util
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	/** Context */
	private static ApplicationContext context = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.context = applicationContext;
	}

	/**
	 * Context 조회
	 * 
	 * @MethodName : getContext
	 * @return
	 */
	public static ApplicationContext getContext() {
		return context;
	}

	/**
	 * Bean 조회 with Bean 명칭
	 * 
	 * @MethodName : getBean
	 * @param bean Bean 명칭
	 * @return Bean Object
	 */
	public static Object getBean(String bean) {
		return context.getBean(bean);
	}

	/**
	 * Bean 조회 with Bean 클래스 타입
	 * 
	 * @MethodName : getBean
	 * @param clazz 클새스 타입
	 * @return Bean Object
	 */
	public static Object getBean(Class<?> clazz) {
		return context.getBean(clazz);
	}

	/**
	 * Bean 조회 with Bean 명칭 & Bean 클래스 타입
	 * 
	 * @MethodName : getBean
	 * @param clazz 클새스 타입
	 * @return Bean Object
	 */
	public static <T> T getBean(String bean, Class<T> type) {
		return context.getBean(bean, type);
	}

	/**
	 * Reqeust 조회
	 * 
	 * @MethodName : getServletRequest
	 * @return Request
	 */
	public static HttpServletRequest getServletRequest() {
		ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
		
		return servletRequestAttributes.getRequest();
	}

}
