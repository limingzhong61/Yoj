package com.yoj.web.pojo.util;

import com.yoj.custom.enums.ExceptionEnum;
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
	// state code, not equals 0 and 200 means server internal error
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

	public static Msg fail(ExceptionEnum exceptionEnum) {
		Msg msg = new Msg();
		msg.setSuccess(false);
		msg.setMsg(exceptionEnum.getMsg());
		msg.setState(exceptionEnum.getState());
		return msg;
	}

	public Msg add(String key, Object value) {
		this.getExtend().put(key, value);
		return this;
	}
}
