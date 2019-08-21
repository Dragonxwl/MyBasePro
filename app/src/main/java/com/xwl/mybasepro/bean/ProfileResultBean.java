package com.xwl.mybasepro.bean;

import com.xwl.mybasepro.base.BaseBean;

import java.io.Serializable;

public class ProfileResultBean extends BaseBean implements Serializable {
	public Data data;

	public class Data implements Serializable {
		public int buildIdInReview;
		public VersionInfo versionInfo;
		public ProConfig config;
		public int dbCode = 0;
		public VoiceEvaluateFile voiceEvaluateFile;
	}

	public class EvaluateOptimize implements Serializable {
		public float minimumScore;// 浮点数，取值范围 0.0-5.0，默认值是 0.1
		// 整数，音量值大于这个值算作有效音量回调，取值范围是 0-255，
		// 默认 值平台各异，各个平台有各自的字段，分别加后缀 iPad iPhone windows android，例如 effectiveVolumeThreshold_iPad
		public int effectiveVolumeThreshold_android;

		/******minimumScore 分数低于这个值，只要麦克风正常，
		 * 且音量回调值大于 effectiveVolumeThreshold，就给 minimumScore 分******/
		public int disabled;// 整数，默认是 0，也就是默认打开本功能，当 disabled = 1 的时候，关闭此功能
	}

	public class VersionInfo implements Serializable {
		public int id;
		public int appId;
		public int buildId;
		public String buildVersion;
		public String url;
		public String remark;
		public int forceUpdate;
		public String fileMd5;
	}

	public class VoiceEvaluateFile implements Serializable {
		public String fileUrl;
		public String fileMd5;
		public long version;
	}

	public class ProConfig implements Serializable {
		public int debugCode = 0;
		public float longTimeBackground;
		public int classOverDelay;
		public int remainedStorageInMillion;
		public int volumeMonitorThreshold;
		public int remainedMemoryInMillion;
		public int logDisabled; //  0为需要上传，默认的          1为不要上传，暂时不需要与服务端沟通
		public int amaxToleratedSocketLaunchTime;  //最大的 socket 容忍启动时间，整数，单位是秒
		public int amaxToleratedAgoraLaunchTime;   //最大的 agora 容忍启动时间，整数，单位是秒

		public VoiceEvaluateFile voiceEvaluateFile;
		public float uploadEvaluateResultThreshold; // 分数
		public int appInvalid;

		public EvaluateOptimize evaluateOptimize;

		public int phonicsDisabled;//整数，1 的时候，隐藏phonics入口，0 或者没有这个字段的时候，显示phonics入口

		public int androidIsShowTabSwitch;//整数，0 或者没有这个字段的时候，隐藏android pad UI 设置界面内开关，1 的时候，显示android pad UI设置界面内开关

		public int NASATrialLessonCount;//整数，表示实验课试听课的节数，最小取值是 1，最大是 9。如果没有这个字段，客户端默认这个值是 1，配了之后以这个字段为准

		public String changeValidDivisionTime;// 修改课次（上课时间，阶段）生效分界线时间，默认 18:00

		public int phonicsLockDisabled;// Phonics 切换等级 解锁开关 整数，0 或者没有这个字段，表示支持解锁，1 表示不能解锁

		public String PUCourseOpenDate;//2.8.0新增字段 PU 开课时间
	}
}