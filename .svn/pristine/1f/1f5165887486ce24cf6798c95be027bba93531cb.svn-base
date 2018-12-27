package com.dwms.common.domain;

import java.util.HashMap;

public class ImgResponse extends HashMap<String, Object> {

	private static final long serialVersionUID = -1L;

	public ImgResponse(boolean uploaded, String url, String error) {
		if (uploaded)
			put("uploaded", "true");
		put("url", url);
		HashMap<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("message", error);
		put("error", errorMap);
	}


	public static ImgResponse ok(String url) {
		ImgResponse rs = new ImgResponse(true, url, "");
		return rs;
	}

	public static ImgResponse error(String error) {
		ImgResponse rs = new ImgResponse(false, "", error);
		return rs;
	}

	@Override
	public ImgResponse put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
