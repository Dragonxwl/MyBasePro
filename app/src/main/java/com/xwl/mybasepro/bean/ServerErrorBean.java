package com.xwl.mybasepro.bean;

import com.xwl.mybasepro.base.BaseBean;

public class ServerErrorBean extends BaseBean {
	public ServerErrorInfo data;

	public class ServerErrorInfo {
		public String desc;
		public long expiredTime;
		public String title;
	}
}
