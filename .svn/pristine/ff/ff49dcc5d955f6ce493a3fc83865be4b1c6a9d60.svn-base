package com.dwms.common.domain;

import java.util.HashMap;

public class ResponseBo extends HashMap<String, Object> {

	private static final long serialVersionUID = -8713837118340960775L;

	private static final String CODE = "code";
	private static final String MSG = "msg";
	
	// 成功
	private static final Integer SUCCESS = 0;
	// 警告
	private static final Integer WARN = 1;
	// 异常 失败
	private static final Integer FAIL = 500;

	public ResponseBo() {
		put(CODE, SUCCESS);
		put(MSG, "操作成功");
	}

	public static ResponseBo error(Object msg) {
		ResponseBo responseBo = new ResponseBo();
		responseBo.put(CODE, FAIL);
		responseBo.put(MSG, msg);
		return responseBo;
	}

	public static ResponseBo warn(Object msg) {
		ResponseBo responseBo = new ResponseBo();
		responseBo.put(CODE, WARN);
		responseBo.put(MSG, msg);
		return responseBo;
	}

	public static ResponseBo ok(Object msg) {
		ResponseBo responseBo = new ResponseBo();
		responseBo.put(CODE, SUCCESS);
		responseBo.put(MSG, msg);
		return responseBo;
	}

	public static ResponseBo ok() {
		return new ResponseBo();
	}

	public static ResponseBo error() {
		return ResponseBo.error("");
	}

	@Override
	public ResponseBo put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public boolean success() {
		return (int) get(CODE) == SUCCESS;
	}
	
	public Object getMsg() {
		return get(MSG);
	}
}
