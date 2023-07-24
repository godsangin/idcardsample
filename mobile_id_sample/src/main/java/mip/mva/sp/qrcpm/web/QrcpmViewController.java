package mip.mva.sp.qrcpm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.qrcpm.web
 * @FileName    : QrcpmViewController.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : QR-CPM 테스트 페이지 이동 Controller
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@Controller
@RequestMapping("/qrcpm")
public class QrcpmViewController {
	
	/**
	 * QR-CPM 테스트 페이지로 이동
	 * 
	 * @MethodName : qrcpmView
	 * @return 페이지 URL
	 */
	@GetMapping("/qrcpmView")
	public String qrcpmView() {
		return "qrcpm/qrcpmView";
	}

}
