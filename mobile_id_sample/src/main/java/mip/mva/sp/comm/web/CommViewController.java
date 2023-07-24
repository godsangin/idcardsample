package mip.mva.sp.comm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Project : 모바일 운전면허증 서비스 구축 사업
 * @PackageName : mip.mva.sp.comm.web
 * @FileName : CommViewController.java
 * @Author : Min Gi Ju
 * @Date : 2022. 6. 3.
 * @Description : 공통 페이지 이동 Controller
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2022. 6. 3.    Min Gi Ju        최초생성
 */
@Controller
public class CommViewController {

	/**
	 * Index 페이지 이동
	 * 
	 * @MethodName : index
	 * @return 페이지 URL
	 */
	@GetMapping("/")
	public String index() {
		return "index";
	}

	/**
	 * Main 페이지 이동
	 * 
	 * @MethodName : index
	 * @return 페이지 URL
	 */
	@GetMapping("/main")
	public String main() {
		return "comm/main";
	}

	/**
	 * 재검증 테스트 페이지 이동
	 * 
	 * @MethodName : revpView
	 * @return 페이지 URL
	 */
	@GetMapping("/comm/revpView")
	public String qrmpmView() {
		return "comm/revpView";
	}

	/**
	 * VP 데이터 조회 테스트 페이지 이동
	 * 
	 * @MethodName : vpdataView
	 * @return 페이지 URL
	 */
	@GetMapping("/comm/vpdataView")
	public String vpDataView() {
		return "comm/vpdataView";
	}

	/**
	 * Util 테스트 페이지 이동
	 * 
	 * @MethodName : utilView
	 * @return 페이지 URL
	 */
	@GetMapping("/comm/utilView")
	public String utilView() {
		return "comm/utilView";
	}

}
