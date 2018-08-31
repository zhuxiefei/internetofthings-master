package com.shy.iot.wulian.common.response;

/**
 * <p>
 * 状态码
 * </p>
 * ClassName: ActiveUser <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 10:06 <br/>
 * Version: 1.0 <br/>
 */
public interface StatusCode {

	/**
	 * 全局系统异常错误代码
	 */
	String FAILURE = "99999";

	/**
	 * 全局系统成功返回码
	 */
	String SUCCESS = "00000";

	/**
	 * 全局系统无权限返回码
	 */
	String UNAUTHORIZED = "11111";

}
