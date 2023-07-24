package mip.mva.sp.qrmpm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.qrmpm.web
 * @FileName    : QrmpmViewController.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : QR-MPM 페이지 이동 Controller
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@Controller
@RequestMapping("/qrmpm")
public class QrmpmViewController {

	/**
	 * QR-MPM 테스트 페이지로 이동
	 * 
	 * @MethodName : qrmpmView
	 * @return 페이지 URL
	 */
	@GetMapping("/qrmpmView")
	public String qrmpmView() {
		return "qrmpm/qrmpmView";
	}

}
