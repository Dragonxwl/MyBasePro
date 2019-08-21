package com.xwl.mybasepro.utils;

import android.content.Context;
import android.util.Log;

import com.xwl.mybasepro.config.ACConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.xwl.mybasepro.config.CustomerConfig.MEDIA_SAVE_DIR;

/**
 * Created by Xiang on 2018/6/6.
 */

public class LogUtil {
	private static boolean isDebug = ACConfig.getInstance().getDebug();
	private static final char VERBOSE = 'v';

	private static final char DEBUG = 'd';

	private static final char INFO = 'i';

	private static final char WARN = 'w';

	private static final char ERROR = 'e';

	public static void LogI(String Tag, String message) {
		if (isDebug) {
			Log.i(Tag, message);
		}
	}

	public static void LogD(String Tag, String message) {
		if (isDebug) {
			Log.d(Tag, message);
		}
	}

	public static void LogE(String Tag, String message) {
		if (isDebug) {
			Log.e(Tag, message);
		}
	}

	public static void LogW(String Tag, String message) {
		if (isDebug) {
			Log.w(Tag, message);
		}
	}

	private static String TAG = "LogToFile";

	private static String logPath = MEDIA_SAVE_DIR + "/Logs";//log日志存放路径

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);//日期格式;

	private static Date date;//因为log日志是使用日期命名的，使用静态成员变量主要是为了在整个程序运行期间只存在一个.log文件中;

	public static String fileName = logPath + "/ac_android_log.log";

	/**
	 * 初始化，须在使用之前设置，最好在Application创建时调用
	 *
	 * @param context
	 */
	public static void init(Context context) {
		File error = new File(fileName);
		if (error.exists()) {
//			error.delete();
			if (error.isFile()) {
				if ((error.length() / (1024 * 1024l)) >= 4l) {//大于4M，日志文件重新生成
					error.delete();
				}
			}
		}

		File folder = new File(logPath);
//		fileName = logPath + "/log_" + dateFormat.format(new Date()) + ".log";//log日志名，使用时间命名，保证不重复
		try {
			if (!folder.exists()) {
				folder.mkdirs();
			}
			writeToFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void v_tofile(String tag, String msg) {
		writeToFile(VERBOSE, tag, msg);
		if (msg != null) {
			Log.v(tag, msg);
		}
	}

	public static void d_tofile(String tag, String msg) {
		writeToFile(DEBUG, tag, msg);
		if (msg != null) {
			Log.d(tag, msg);
		}
	}

	public static void i_tofile(String tag, String msg) {
		writeToFile(INFO, tag, msg);
		if (msg != null) {
			Log.i(tag, msg);
		}
	}

	public static void w_tofile(String tag, String msg) {
		writeToFile(WARN, tag, msg);
		if (msg != null) {
			Log.w(tag, msg);
		}
	}

	public static void e_tofile(String tag, String msg) {
		writeToFile(ERROR, tag, msg);
		if (msg != null) {
			Log.e(tag, msg);
		}
	}

	//在没有引擎初始化的时候，现将日志保存下来，之后再写进日志文件
	public static void v_uninit(String tag, String msg) {
		date = new Date();
		String log = dateFormat.format(date) + " " + "v" + " " + tag + " " + msg + "\n";//log日志内容，可以自行定制
		if (msg != null) {
			Log.v(tag, msg);
		}
		String lastUninitLog = ACConfig.getInstance().getUninitLog();
		String nowUninitLog = lastUninitLog + log;
		ACConfig.getInstance().setUninitLog(nowUninitLog);
	}

	public static void d_uninit(String tag, String msg) {
		date = new Date();
		String log = dateFormat.format(date) + " " + "d" + " " + tag + " " + msg + "\n";//log日志内容，可以自行定制
		if (msg != null) {
			Log.d(tag, msg);
		}
		String lastUninitLog = ACConfig.getInstance().getUninitLog();
		String nowUninitLog = lastUninitLog + log;
		ACConfig.getInstance().setUninitLog(nowUninitLog);
	}

	public static void i_uninit(String tag, String msg) {
		date = new Date();
		String log = dateFormat.format(date) + " " + "i" + " " + tag + " " + msg + "\n";//log日志内容，可以自行定制
		if (msg != null) {
			Log.i(tag, msg);
		}
		String lastUninitLog = ACConfig.getInstance().getUninitLog();
		String nowUninitLog = lastUninitLog + log;
		ACConfig.getInstance().setUninitLog(nowUninitLog);
	}

	public static void w_uninit(String tag, String msg) {
		date = new Date();
		String log = dateFormat.format(date) + " " + "w" + " " + tag + " " + msg + "\n";//log日志内容，可以自行定制
		if (msg != null) {
			Log.w(tag, msg);
		}
		String lastUninitLog = ACConfig.getInstance().getUninitLog();
		String nowUninitLog = lastUninitLog + log;
		ACConfig.getInstance().setUninitLog(nowUninitLog);
	}

	public static void e_uninit(String tag, String msg) {
		date = new Date();
		String log = dateFormat.format(date) + " " + "e" + " " + tag + " " + msg + "\n";//log日志内容，可以自行定制
		if (msg != null) {
			Log.e(tag, msg);
		}
		String lastUninitLog = ACConfig.getInstance().getUninitLog();
		String nowUninitLog = lastUninitLog + log;
		ACConfig.getInstance().setUninitLog(nowUninitLog);
	}

	/**
	 * 将log信息写入文件中
	 *
	 * @param type
	 * @param tag
	 * @param msg
	 */
	private static void writeToFile(char type, String tag, String msg) {

		if (null == logPath) {
			Log.e(TAG, "logPath == null ，未初始化LogToFile");
			return;
		}
		date = new Date();
		String log = dateFormat.format(date) + " " + type + " " + tag + " " + msg + "\n";//log日志内容，可以自行定制

		//如果父路径不存在
		File file = new File(logPath);
		if (!file.exists()) {
			file.mkdirs();//创建父路径
		}

		FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
		BufferedWriter bw = null;
		try {

			fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write(log);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();//关闭缓冲流
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将log信息写入文件中
	 */
	private static void writeToFile() {
		//如果父路径不存在
		File file = new File(logPath);
		if (!file.exists()) {
			file.mkdirs();//创建父路径
		}
		String msg = ACConfig.getInstance().getUninitLog();

		FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
		BufferedWriter bw = null;
		try {

			fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write(msg);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();//关闭缓冲流
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			ACConfig.getInstance().setUninitLog("");
		}
	}

	/**
	 * 删除7天前日志
	 *
	 * @param listLogFile
	 */
	public static void DeleteLogs(ArrayList<File> listLogFile) {
		if (listLogFile != null && listLogFile.size() > 0) {
			Date dateNow = new Date();
			for (int i = 0; i < listLogFile.size(); i++) {
				File file = listLogFile.get(i);
				if (file == null) {
					break;
				}
				Date dateFile = new Date(file.lastModified());//文件最后修改时间
				try {
					int betweenDays = daysBetween(dateFile, dateNow);
					if (betweenDays > 7) {
						file.delete();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 60 * 60 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 查找该目录下的所有文件
	 *
	 * @param path
	 */
	private void SearchLogFiles(String path) {
		File files = new File(path);
		//判断文件夹是否存在,如果不存在则创建文件夹
		if (!files.exists()) {
			files.mkdir();
			GetFiles(path, true);
		} else {//存在就查询文件
			GetFiles(path, true);
		}
	}

	public void GetFiles(String Path, boolean IsIterative) {//搜索目录，扩展名，是否进入子文件夹
		File[] files = new File(Path).listFiles();
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f.isFile()) {
					if (!IsIterative)
						break;
				} else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) //忽略点文件（隐藏文件/文件夹）
					GetFiles(f.getPath(), IsIterative);
			}
		}
	}
}
