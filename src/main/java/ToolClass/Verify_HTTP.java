package ToolClass;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证 HTTP 链接  是否合法
 * @author Administrator
 *
 */
public class Verify_HTTP {

	/**
	 * 判定 Referer 是否为空   同时包含 网址域名
	 * @param request
	 * @return
	 */
	public boolean Islegal(HttpServletRequest request){
		if(request.getHeader("Referer")!=null&&request.getHeader("Referer").contains("http://localhost:8080")){
			return true;
		}else {
			return false;
		}
	}
}
