package com.yoj.web.bean.util;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回类
 * 
 * @author lmz
 *
 */
@Setter
@Getter
public class Msg {
	// 状态
	private boolean success;

	private int state;
	// 提示prompt information
	private String msg;
	// 用户返回给浏览器的数据
	private Map<String, Object> extend = new HashMap<String, Object>();

	public static Msg success() {
		Msg msg = new Msg();
		msg.setSuccess(true);
//		msg.setMsg("处理成功!");
		return msg;
	}

	public static Msg fail() {
		Msg msg = new Msg();
		msg.setSuccess(false);
//		msg.setMsg("处理失败!");
		return msg;
	}

	public static Msg fail(String info) {
		Msg msg = new Msg();
		msg.setSuccess(false);
		msg.setMsg(info);
		return msg;
	}

	public Msg add(String key, Object value) {
		this.getExtend().put(key, value);
		return this;
	}
}
