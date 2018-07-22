package ToolClass.Time;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 获取 时间
 * @return
 */
public class GetTodayTime {

	/**
	 * 获取当前网络时间(百度时间)
	 * @param format 传入时间格式  如(yyyy-MM-dd hh:mm:ss)
	 * @return
	 */
	public String GetNetworkTodayTime(String format){
		try {
			URL url = new URL("http://www.baidu.com");// 取得资源对象
			URLConnection uc = url.openConnection();// 生成连接对象
			uc.connect();// 发出连接
			long ld = uc.getDate();// 读取网站日期时间
			Date date = new Date(ld);// 转换为标准时间对象
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);// 输出北京时间
			return sdf.format(date.getTime());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 毫秒转成时间
	 * @param Milliseconds  毫秒
	 * @param Date_format   时间格式(yyyy-MM-dd hh:mm:ss)
	 * @return
	 */
	public String Milliseconds_Into_time(long Milliseconds,String Date_format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(Date_format);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTimeInMillis(Milliseconds);
		return dateFormat.format(cal.getTime());
	}

	/**
	 * 将日期转换为毫秒
	 * @param Date       日期(2014-3-7 20:16)
	 * @param Date_format 时间格式(yyyy-MM-dd hh:mm:ss)
	 * @return
	 */
	public long DateConversion_to_Milliseconds(String Date,String Date_format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(Date_format);
		Date date = null;
		try {
			date = dateFormat.parse(Date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}
	/**
	 * 计算两个日期间相隔多少天
	 * @param First       第一个日期参数(2013-9-8)大
	 * @param Second      第二个日期参数(2013-9-7)小
	 * @param Date_format 时间格式(yyyy-MM-dd hh:mm:ss)
	 * @return
	 */
	public long TwoDateIntervalDays(String First,String Second,String Date_format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(Date_format);
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = dateFormat.parse(First);
			date2 = dateFormat.parse(Second);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (date1.getTime() - date2.getTime()) / (24 * 3600 * 1000);
	}

	/**
	 * 日期相加
	 * @param TheStart_date 开始的日期(1950-3-8)
	 * @param Date_Addition 相加的天数(10)
	 * @param Date_format   时间格式(yyyy-MM-dd hh:mm:ss)
	 * @return
	 */
	public String Date_Addition(String TheStart_date,long Date_Addition,String Date_format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(Date_format);
		Date date1;
		java.util.Calendar cal = null;
		try {
			date1 = dateFormat.parse(TheStart_date);
			cal = java.util.Calendar.getInstance();
			long addMill = date1.getTime() + Date_Addition * 24 * 3600 * 1000;
			cal.setTimeInMillis(addMill);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateFormat.format(cal.getTime());
	}


	/**
	 * 验证字符串是否可以转成时间格式
	 * @param Time
	 * @param Date_format  yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public boolean Validation_TimeFormat(String Time,String Date_format){
		SimpleDateFormat format = new SimpleDateFormat(Date_format);
		try {
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(Time);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			e.printStackTrace();
			return false;
		}
	}
}
