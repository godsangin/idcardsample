package mip.mva.sp.app2app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Project     : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.app2app.web
 * @FileName    : App2AppViewController.java
 * @Author      : Min Gi Ju
 * @Date        : 2022. 6. 3.
 * @Description : App to App 테스트 페이지 이동 Controller
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@Controller
@RequestMapping("/app2app")
public class App2AppViewController {
	
	/**
	 * Web to App 테스트 페이지 이동
	 * 
	 * @MethodName : web2appView
	 * @return 페이지 URL
	 */
	@GetMapping("/web2appView")
	public String web2appView() {
		return "app2app/web2appView";
	}

}
