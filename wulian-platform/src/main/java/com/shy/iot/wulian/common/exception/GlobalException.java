package com.shy.iot.wulian.common.exception;

import com.shy.iot.wulian.common.utils.MessageUtil;

public class GlobalException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public GlobalException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
	
	public GlobalException(String code) {
        this.code = code;
        this.msg = MessageUtil.getMsg(code);
    }

    private String code;
    private String msg;
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
    

}