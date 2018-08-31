package com.shy.iot.wulian.common.response;

import com.shy.iot.wulian.common.utils.MessageUtil;

import java.io.Serializable;

/**
 * <p>
 * 返回前台的json数据
 * </p>
 * ClassName: ActiveUser <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 10:06 <br/>
 * Version: 1.0 <br/>
 */
public class Response<T> implements Serializable {

	private static final long serialVersionUID = 4273005680206220420L;
	/**
	 * 返回结果码
	 */
	private String code = StatusCode.SUCCESS;
	
	private String msg = MessageUtil.getMsg(code);
	/**
	 * 返回的数据
	 */
	private T data;

	public Response() {

	}

	public Response(T data){
		this.data = data;
	}

	public Response(String code, T data) {
		this.code = code;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Response [code=");
		builder.append(code);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
