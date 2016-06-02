package com.eco.pub.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 功能:日期转换的帮助类
 * 
 * @author 郑斌
 * @date 2005-11-29
 */
public class DateUtil {

	/**
	 * 将年月转换成年月日.
	 * 
	 * @param dateStr:只包含年月的日期字符串(例:200511)
	 * @return rtnDateStr:包含年月日的字符串,日期为该月最后一天(例:20051130)
	 */
	public static String getEndOfMth(String dateStr) {
		String yearStr = dateStr.substring(0, 4);
		String monthStr = dateStr.substring(4, 6);
		int yearInt = Integer.parseInt(yearStr);
		int monthInt = Integer.parseInt(monthStr);
		if (yearInt < 1900) {
			throw new RuntimeException("年份不能早于1900年!");
		}
		if (monthInt < 1 || monthInt > 12) {
			throw new RuntimeException("月份不能大于合法范围!");
		}
		Date newDate = new Date(yearInt - 1900, monthInt - 1, 1);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(newDate);
		int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		String rtnDateStr = yearStr + monthStr + dayCount;
		return rtnDateStr;
	}

	/**
	 * 将年月转换成年月日.
	 * 
	 * @param dateStr:只包含年月的日期字符串(例:200511)
	 * @return:包含年月日的字符串,日期为该月第一天(例:20051101)
	 */
	public static String getBgnOfMth(String dateStr) {
		return dateStr + "01";
	}

	/**
	 * 获取输入日期的下一天 返回 8位 like 20050101
	 * 
	 * @param today
	 * @return
	 */
	public static String getNextDay(String day) {
		return getNextDay(day, 1);
	}

	/**
	 * 获取输入日期的下 n 天 返回 8位 like 20050101
	 * 
	 * @param day
	 * @param n
	 * @return
	 */
	public static String getNextDay(String day, int n) {
		if (day == null || "".equals(day) || day.length() != 8) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的日期换算.");
		}
		try {
			String sYear = day.substring(0, 4);
			int year = Integer.parseInt(sYear);
			String sMonth = day.substring(4, 6);
			int month = Integer.parseInt(sMonth);
			String sDay = day.substring(6, 8);
			int dday = Integer.parseInt(sDay);
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, dday);
			cal.add(Calendar.DATE, n);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(cal.getTime());

		} catch (RuntimeException e) {
		    throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
        } catch (Exception e) {
			throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);

		}
	}

	/**
	 * 功能：获取输入 月份的下 n 月份 返回 6位 like 200501
	 * 
	 * @author 郑斌
	 * @date 2008-6-30
	 * @param month
	 * 格式：yyyyMM
	 * @param n
	 * @return
	 */
	public static String getNextMonth(String month, int n) {
		if (month == null || "".equals(month) || month.length() != 6) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的月份换算.");
		}
		try {
			String sYear = month.substring(0, 4);
			int year = Integer.parseInt(sYear);
			String sMonth = month.substring(4, 6);
			int mon = Integer.parseInt(sMonth);
			Calendar cal = Calendar.getInstance();
			cal.set(year, mon - 1, 1);
			cal.add(Calendar.MARCH, n);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
			return df.format(cal.getTime());

		} catch (RuntimeException e) {
		    throw new RuntimeException("进行月份运算时输入得参数不符合系统规格." + e);
        } catch (Exception e) {
			throw new RuntimeException("进行月份运算时输入得参数不符合系统规格." + e);

		}
	}

	/**
	 * 功能：获取输入年份的下 n 年
	 * 
	 * @author 郑斌
	 * @date 2008-6-21
	 * @param year 输入年份 如：2008
	 * @param n 下 n 年 如：3
	 * @return 输入年份的下n年 如：2010
	 */
	public static String getNextYear(String year, int n) {
		int yearInt = Integer.parseInt(year);
		int rtnYearInt = yearInt + n;
		return rtnYearInt + "";
	}

	/**
	 * 功能：获取输入半年的下 n 半年
	 * 
	 * @author 郑斌
	 * @date 2008-6-21
	 * @param halfYear 输入半年 如：2008Y1
	 * @param n 下 n 半年 如：3
	 * @return 输入半年的下n半年 如：2009Y2
	 */
	public static String getNextHalfYear(String halfYear, int n) {
		// 当前年
		String year = halfYear.substring(0, 4);
		// 上下半年
		String half = halfYear.substring(5, 6);
		// 通过取下6*n月的方式辅助实现取下n半年
		String tempMonth = year + "01";
		if (half.equals("2")) {
			tempMonth = year + "07";
		}
		String rtnMonth = getNextMonth(tempMonth, 6 * n);
		// 得到年
		String rtnYear = rtnMonth.substring(0, 4);
		String rtnMSign = rtnMonth.substring(4, 6);
		String rtnHalfYear = rtnYear + "Y1";
		if (rtnMSign.equals("07")) {
			rtnHalfYear = rtnYear + "Y2";
		}
		return rtnHalfYear;
	}

	/**
	 * 功能：获取输入季度的下 n 季度
	 * 
	 * @author 郑斌
	 * @date 2008-6-21
	 * @param querter  输入季度 如：2008Q1
	 * @param n 下 n 季度 如：3
	 * @return 输入季度的下n季度 如：2008Q4
	 */
	public static String getNextQuarter(String querter, int n) {
		// 当前年
		String year = querter.substring(0, 4);
		// 当前季度
		String querterSign = querter.substring(5, 6);
		// 通过取下3*n月的方式辅助实现取下n季度
		String tempMonth = year + "01";
		if (querterSign.equals("2")) {
			tempMonth = year + "04";
		} else if (querterSign.equals("3")) {
			tempMonth = year + "07";
		} else if (querterSign.equals("4")) {
			tempMonth = year + "10";
		}
		String rtnMonth = getNextMonth(tempMonth, 3 * n);
		// 得到年
		String rtnYear = rtnMonth.substring(0, 4);
		String rtnQSingn = rtnMonth.substring(4, 6);
		String rtnQuarter = rtnYear + "Q1";
		if (rtnQSingn.equals("04")) {
			rtnQuarter = rtnYear + "Q2";
		} else if (rtnQSingn.equals("07")) {
			rtnQuarter = rtnYear + "Q3";
		} else if (rtnQSingn.equals("10")) {
			rtnQuarter = rtnYear + "Q4";
		}
		return rtnQuarter;
	}

	/**
	 * 功能：获取传入日期字符串的月份
	 * 
	 * @author 郑斌
	 * @date 2008-6-30
	 * @param date 传入日期参数，格式：yyyyMMdd 例如：20080630
	 * @return 参数日期所属月份，格式：yyyyMM 例如：200806
	 */
	public static String getMonthByDate(String date) {
		if (date == null || date.length() != 8) {
			throw new RuntimeException("日期格式不正确，请检查！");
		}
		return date.substring(0, 6);
	}

	/**
	 * 功能：获取传入日期字符串所在季度
	 * 
	 * @author 郑斌
	 * @date 2008-6-30
	 * @param date 传入日期参数，格式：yyyyMMdd 例如：20080630
	 * @return 参数日期所属季度，格式：yyyyQn 例如：2008Q2
	 */
	public static String getQuarterByDate(String date) {
		if (date == null || date.length() != 8) {
			throw new RuntimeException("日期格式不正确，请检查！");
		}
		String year = date.substring(0, 4);
		int mon = Integer.parseInt(date.substring(4, 6));
		String quarter = null;
		if (1 <= mon && mon <= 3) {
			quarter = "Q1";
		} else if (4 <= mon && mon <= 6) {
			quarter = "Q2";
		} else if (7 <= mon && mon <= 9) {
			quarter = "Q3";
		} else if (10 <= mon && mon <= 12) {
			quarter = "Q4";
		} else {
			throw new RuntimeException("日期格式不正确，请检查！");
		}
		return year + quarter;
	}

	/**
	 * 功能：获取传入日期字符串所在半年
	 * 
	 * @author 郑斌
	 * @date 2008-6-30
	 * @param date 传入日期参数，格式：yyyyMMdd 例如：20080630
	 * @return 参数日期所属半年，格式：yyyyYn 例如：2008Y1
	 */
	public static String getHalfYearByDate(String date) {
		if (date == null || date.length() != 8) {
			throw new RuntimeException("日期格式不正确，请检查！");
		}
		String year = date.substring(0, 4);
		int mon = Integer.parseInt(date.substring(4, 6));
		String hYear = null;
		if (1 <= mon && mon <= 6) {
			hYear = "Y1";
		} else if (7 <= mon && mon <= 12) {
			hYear = "Y2";
		} else {
			throw new RuntimeException("日期格式不正确，请检查！");
		}
		return year + hYear;
	}

	/**
	 * 功能：获取传入日期字符串所在年
	 * 
	 * @author 郑斌
	 * @date 2008-6-30
	 * @param date 传入日期参数，格式：yyyyMMdd 例如：20080630
	 * @return 参数日期所属年份，格式：yyyyYn 例如：2008
	 */
	public static String getYearByDate(String date) {
		if (date == null || date.length() != 8) {
			throw new RuntimeException("日期格式不正确，请检查！");
		}
		return date.substring(0, 4);
	}

	/**
	 * 返回星期 0 星期天 6 星期陆
	 * 
	 * @param date 20040101
	 *            
	 * @return
	 */
	public static int getWeekday(String date) {
		if (date == null || date.length() != 8) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的月份换算.");
		}
		String sYear = date.substring(0, 4);
		int year = Integer.parseInt(sYear);
		String sMonth = date.substring(4, 6);
		int mon = Integer.parseInt(sMonth);
		String sDay = date.substring(6, 8);
		int dday = Integer.parseInt(sDay);
		Calendar cal = Calendar.getInstance();
		cal.set(year, mon - 1, dday);
		int weekday = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return weekday;
	}

	/**
	 * 返回星期 0 星期天 6 星期陆
	 * 
	 * @param date 20040101
	 *            
	 * @return
	 */
	public static String getWeekdayName(String date) {
		return getWeekName(getWeekday(date));
	}

	/**
	 * 功能：获取输入日期所在季度的第一个月 比如当前日期是20080301，返回200801
	 * 
	 * @author songhzh
	 * @date 20090514
	 * @param today 格式：yyyyMMdd
	 *            
	 * @return month 格式：yyyyMM
	 */
	public static String getFirstMonthOfQuarter(String date) {
		if (date == null || date.length() != 8) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的月份换算.");
		}
		String mth = "";
		String quater = getQuarterByDate(date);
		String year = quater.substring(0, 4);
		String tmp = quater.substring(4, 6);
		if ("Q1".equals(tmp)) {
			mth = year + "01";
		} else if ("Q2".equals(tmp)) {
			mth = year + "04";
		} else if ("Q3".equals(tmp)) {
			mth = year + "07";
		} else {
			mth = year + "10";
		}

		return mth;
	}

	/**
	 * 功能：换算星期的天数为名称 2006-7-21
	 * 
	 * @param weekday
	 * @return
	 */
	private static String getWeekName(int weekday) {
		String rtnStr = null;
		switch (weekday) {
		case 1:
			rtnStr = "星期一";
			break;
		case 2:
			rtnStr = "星期二";
			break;
		case 3:
			rtnStr = "星期三";
			break;
		case 4:
			rtnStr = "星期四";
			break;
		case 5:
			rtnStr = "星期五";
			break;
		case 6:
			rtnStr = "星期六";
			break;
		case 0:
			rtnStr = "星期日";
			break;
		default:
			throw new RuntimeException("输入日期有误！");
		}
		return rtnStr;
	}

	/**
	 * 功能：返回今天的星期几 2006-7-21
	 * 
	 * @return
	 */
	public static String getWeekdayName() {
		return getWeekdayName(getToday());
	}

	/**
	 * 获取半年中月从到 2008-07-15 于连军
	 * 
	 * @param date
	 * @return
	 */
	public static String getHalfYearFromTo(String date) {
		if (date == null || date.length() != 8) {
			throw new RuntimeException("日期格式不正确，请检查！");
		}
		String halfYearFrom = null;
		String halfYearTo = null;
		String year = date.substring(0, 4);
		String halpYear = getHalfYearByDate(date);
		if (halpYear.substring(4, 6).equals("Y1")) {
			halfYearFrom = year + "01";
			halfYearTo = year + "06";
		} else if (halpYear.substring(4, 6).equals("Y2")) {
			halfYearFrom = year + "07";
			halfYearTo = year + "12";
		}
		return halfYearFrom + halfYearTo;
	}

	/**
	 * 获取季度中月从到 2008-07-15 于连军
	 * 
	 * @param date
	 * @return
	 */
	public static String getQuarterFromTo(String date) {
		if (date == null || date.length() != 8) {
			throw new RuntimeException("日期格式不正确，请检查！");
		}
		String quarterFrom = null;
		String quarterTo = null;
		String year = date.substring(0, 4);
		String quarter = DateUtil.getQuarterByDate(date);
		if (quarter.substring(4, 6).equals("Q1")) {
			quarterFrom = year + "01";
			quarterTo = year + "03";
		} else if (quarter.substring(4, 6).equals("Q2")) {
			quarterFrom = year + "04";
			quarterTo = year + "06";
		} else if (quarter.substring(4, 6).equals("Q3")) {
			quarterFrom = year + "07";
			quarterTo = year + "09";
		} else if (quarter.substring(4, 6).equals("Q4")) {
			quarterFrom = year + "10";
			quarterTo = year + "12";
		}
		return quarterFrom + quarterTo;
	}

	/**
	 * * 功能：获取传入时间区间的去年同期时间区间
	 * 
	 * @author 崔玉华
	 * @date 2008-9-10
	 * @param periodId
	 *            传入日期参数，格式:年度、半年度、季度、月度的时间区间，例如：2008Q1
	 * @return 去年同期时间区间，格式:年度、半年度、季度、月度的时间区间，例如：2007Q1
	 */
	public static String getSamePeriod(String periodId) {
		String samePeriodId = "";

		if (periodId.length() == 4) {
			samePeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4)) - 1);
		} else if (periodId.length() == 6) {
			samePeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4)) - 1) + periodId.substring(4, 6);
		} else if (periodId.length() == 8) {
			samePeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4)) - 1) + periodId.substring(4, 8);
		} else {
			throw new RuntimeException("日期格式不正确，请检查！");
		}

		return samePeriodId;
	}

	/**
	 * * 功能：获取传入时间区间的上期时间区间
	 * 
	 * @author 崔玉华
	 * @date 2008-9-10
	 * @param periodId 传入日期参数，格式:年度、半年度、季度、月度的时间区间，例如：2008Q2
	 * @return 上期时间区间，格式:年度、半年度、季度、月度的时间区间，例如：2008Q1
	 */
	public static String getLastPeriod(String periodId) {
		String lastPeriodId = "";

		if (periodId.length() == 4) {
			lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4)) - 1);
		} else if (periodId.length() == 6) {
			if (periodId.indexOf("Y1") != -1) { // 按半年度
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4)) - 1) + "Y2";
			} else if (periodId.indexOf("Y2") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "Y1";
			} else if (periodId.indexOf("Q1") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4)) - 1) + "Q4";
			} else if (periodId.indexOf("Q2") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "Q1";
			} else if (periodId.indexOf("Q3") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "Q2";
			} else if (periodId.indexOf("Q4") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "Q3";
			} else if (periodId.substring(4, 6).indexOf("01") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4)) - 1) + "12";
			} else if (periodId.substring(4, 6).indexOf("02") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "01";
			} else if (periodId.substring(4, 6).indexOf("03") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "02";
			} else if (periodId.substring(4, 6).indexOf("04") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "03";
			} else if (periodId.substring(4, 6).indexOf("05") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "04";
			} else if (periodId.substring(4, 6).indexOf("06") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "05";
			} else if (periodId.substring(4, 6).indexOf("07") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "06";
			} else if (periodId.substring(4, 6).indexOf("08") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "07";
			} else if (periodId.substring(4, 6).indexOf("09") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "08";
			} else if (periodId.substring(4, 6).indexOf("10") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "09";
			} else if (periodId.substring(4, 6).indexOf("11") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "10";
			} else if (periodId.substring(4, 6).indexOf("12") != -1) {
				lastPeriodId = Integer.toString(Integer.parseInt(periodId.substring(0, 4))) + "11";
			}
		} else {
			throw new RuntimeException("日期格式不正确，请检查！");
		}

		return lastPeriodId;
	}

	/**
	 * 获取输入 区间ID,返回区间名称
	 * 
	 * @param periodId
	 * @return
	 */
	public static String getPeriodName(String periodId) {
		String periodName = "";
		if (periodId.length() == 4) { // 按年度
			periodName = periodId + "年";
		} else if (periodId.length() == 6) { // 按半年度,季度,月度
			if (periodId.indexOf("Y1") != -1) { // 按半年度
				periodName = periodId.substring(0, 4) + "年上半年";
			} else if (periodId.indexOf("Y2") != -1) {
				periodName = periodId.substring(0, 4) + "年下半年";
			} else if (periodId.indexOf("Q1") != -1) {
				periodName = periodId.substring(0, 4) + "年第一季度";
			} else if (periodId.indexOf("Q2") != -1) {
				periodName = periodId.substring(0, 4) + "年第二季度";
			} else if (periodId.indexOf("Q3") != -1) {
				periodName = periodId.substring(0, 4) + "年第三季度";
			} else if (periodId.indexOf("Q4") != -1) {
				periodName = periodId.substring(0, 4) + "年第四季度";
			} else if (periodId.substring(4, 6).indexOf("01") != -1) {
				periodName = periodId.substring(0, 4) + "年01月";
			} else if (periodId.substring(4, 6).indexOf("02") != -1) {
				periodName = periodId.substring(0, 4) + "年02月";
			} else if (periodId.substring(4, 6).indexOf("03") != -1) {
				periodName = periodId.substring(0, 4) + "年03月";
			} else if (periodId.substring(4, 6).indexOf("04") != -1) {
				periodName = periodId.substring(0, 4) + "年04月";
			} else if (periodId.substring(4, 6).indexOf("05") != -1) {
				periodName = periodId.substring(0, 4) + "年05月";
			} else if (periodId.substring(4, 6).indexOf("06") != -1) {
				periodName = periodId.substring(0, 4) + "年06月";
			} else if (periodId.substring(4, 6).indexOf("07") != -1) {
				periodName = periodId.substring(0, 4) + "年07月";
			} else if (periodId.substring(4, 6).indexOf("08") != -1) {
				periodName = periodId.substring(0, 4) + "年08月";
			} else if (periodId.substring(4, 6).indexOf("09") != -1) {
				periodName = periodId.substring(0, 4) + "年09月";
			} else if (periodId.substring(4, 6).indexOf("10") != -1) {
				periodName = periodId.substring(0, 4) + "年10月";
			} else if (periodId.substring(4, 6).indexOf("11") != -1) {
				periodName = periodId.substring(0, 4) + "年11月";
			} else if (periodId.substring(4, 6).indexOf("12") != -1) {
				periodName = periodId.substring(0, 4) + "年12月";
			}
		} else if (periodId.length() == 8) {// 增加日的描述，郑斌 20090423
			if (periodId.substring(6, 8).indexOf("W1") != -1) {
				periodName = periodId.substring(0, 4) + "年" + periodId.substring(4, 6) + "月" + "第一周";
			} else if (periodId.substring(6, 8).indexOf("W2") != -1) {
				periodName = periodId.substring(0, 4) + "年" + periodId.substring(4, 6) + "月" + "第二周";
			} else if (periodId.substring(6, 8).indexOf("W3") != -1) {
				periodName = periodId.substring(0, 4) + "年" + periodId.substring(4, 6) + "月" + "第三周";
			} else if (periodId.substring(6, 8).indexOf("W3") != -1) {
				periodName = periodId.substring(0, 4) + "年" + periodId.substring(4, 6) + "月" + "第四周";
			} else {
				periodName = periodId.substring(0, 4) + "年" + periodId.substring(4, 6) + "月" + periodId.substring(6, 8)
						+ "日";
			}
		}
		return periodName;
	}

	/**
	 * 获取输入两个年月相差月份 返回 int like 2 公式:先取得两个日期的年份和月份，月份差=（第二年份-第一年份）*12 +
	 * 第二月份-第一月份
	 * 
	 * @param monthFrom like 200801
	 *            
	 * @param monthTo like 200807
	 *            
	 * @return 6
	 */
	public static int getDiffMonth(String dateFrom, String dateTo) {
		if (dateFrom == null || "".equals(dateFrom) || dateFrom.length() != 6 || dateTo == null || "".equals(dateTo)
				|| dateTo.length() != 6) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行相差月份计算.");
		}
		int yearFrom = Integer.parseInt(dateFrom.substring(0, 4));
		int yearTO = Integer.parseInt(dateTo.substring(0, 4));

		int monthFrom = Integer.parseInt(dateFrom.substring(4, 6));
		int monthTo = Integer.parseInt(dateTo.substring(4, 6));

		return (yearTO - yearFrom) * 12 + monthTo - monthFrom;
	}

	/**
	 * 获取输入 月份的前 n 月份 返回 6位 like 200501
	 * 
	 * @param month
	 * @param n
	 * @return
	 */
	public static String getPreviousMonth(String month, int n) {
		return getNextMonth(month, -n);
	}

	/**
	 * 获取输入日期的嵌 n 天 返回 8位 like 20050101
	 * 
	 * @param day
	 * @param n
	 * @return
	 */
	public static String getPreviousDay(String day, int n) {
		return getNextDay(day, -n);
	}
	
	/**
	 * 
	 * 方法描述: 获取到秒的时间戳
	 * 
	 * @return long
	 * @author Ethan 2016年5月12日
	 */
	public static long getCurrentTimeSeconds() {
        return System.currentTimeMillis() / 1000;
    }

	/**
	 * 获取当前系统时间 返回 12:12:12
	 * 
	 * @return
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * @param ctm long 系统时间
	 *            
	 * @param format
	 * @return
	 */
	public static String getCurrentTimeMillisAsString(long ctm, String format) {
		Date date = new Date(ctm);
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * 获取当前系统时间 返回 yyyyMMddHHmmssS
	 * 
	 * @return
	 */
	public static String getCurrentTimeMillisAsString() {
		long currTimeM = getCurrentTimeMillis();
		return getCurrentTimeMillisAsString(currTimeM, "yyyyMMddHHmmss");
	}

	/**
	 * 获取当前系统时间 参数 format yyyyMMddHHmmssS 其中的部分 返回与 format格式相同的时间
	 * 
	 * @return
	 */
	public static String getCurrentTimeMillisAsString(String format) {
		long currTimeM = getCurrentTimeMillis();
		return getCurrentTimeMillisAsString(currTimeM, format);
	}

	/**
	 * 功能： 2006-7-20
	 * 
	 * @return hh:mm:ss
	 */
	public static String getNowTime() {
		return getCurrentTimeMillisAsString("HH:mm:ss");
	}

	/**
	 * 功能： 2006-7-20
	 * 
	 * @return yyyymmdd
	 */
	public static String getToday() {
		return getCurrentTimeMillisAsString("yyyyMMdd");
	}

	/**
	 * 功能： 2006-7-20
	 * 
	 * @return yyyymmdd hh:mm:ss
	 */
	public static String getNow() {
		return getCurrentTimeMillisAsString("yyyyMMdd HH:mm:ss");
	}

	/**
	 * 功能：根据传入的时间区间及区间类型返回该时间区间在相应区间类型中的时间区间
	 * 
	 * @author 郑斌
	 * @date 2008-7-11
	 * @param periodId 时间区间，例如：2008Q1，2008M3
	 * @param targetType 目标区间类型，例如：M，H
	 * @return 字符串数字 相应时间区间，例如：{200801,200802,200803}，{2008Y1}
	 */
	public static String[] getPeriodArrByType(String periodId, String targetType) {
		String[] rtnArr = null;
		String year = periodId.substring(0, 4);
		// 传入时间区间的区间类型
		String currentType = getPeriodTypeBuPeriodId(periodId);
		// System.out.println("currentType=" + currentType);
		// 使用int值描述传入时间区间的区间类型的范围大小
		int cuurentTypeInt = typeIntValue(currentType);
		// 使用int值描述目的时间区间的区间类型的范围大小
		int targetTypeInt = typeIntValue(targetType);
		// 如果当前时间区间的时间范围等于目标区间类型的区间范围，则返回自身
		if (cuurentTypeInt == targetTypeInt) {
			// System.out.println("=======11111111");
			rtnArr = new String[] { periodId };
		} else if (cuurentTypeInt < targetTypeInt) {// 如果当前时间区间的时间范围小于目标区间类型的区间范围
			// System.out.println("=======2222222222");
			if (currentType.equals("Y")) {
				// 年以及为最大范围的时间区间类型，故该情况不存在！
			} else if (currentType.equals("H")) {// 当前区间类型为半年，则目标区间类型只能为年
				rtnArr = new String[] { periodId.substring(0, 4) };
			} else if (currentType.equals("Q")) {
				if (targetType.equals("H")) {
					rtnArr = new String[] {
							periodId.substring(0, 4) + "Y" + (Integer.parseInt(periodId.substring(5, 6)) / 3 + 1) };
				} else if (targetType.equals("Y")) {
					rtnArr = new String[] { periodId.substring(0, 4) };
				}
			} else if (currentType.equals("M")) {
				if (targetType.equals("Q")) {
					rtnArr = new String[] { periodId.substring(0, 4) + "Q"
							+ ((Integer.parseInt(periodId.substring(4, 6)) - 1) / 3 + 1) };
				} else if (targetType.equals("H")) {
					rtnArr = new String[] {
							periodId.substring(0, 4) + "Y" + (Integer.parseInt(periodId.substring(4, 6)) / 7 + 1) };
				} else if (targetType.equals("Y")) {
					rtnArr = new String[] { periodId.substring(0, 4) };
				}
			} else if (currentType.equals("D")) {
				if (targetType.equals("M")) {
					rtnArr = new String[] { getMonthByDate(periodId) };
				} else if (targetType.equals("Q")) {
					rtnArr = new String[] { getQuarterByDate(periodId) };
				} else if (targetType.equals("H")) {
					rtnArr = new String[] { getHalfYearByDate(periodId) };
				} else if (targetType.equals("Y")) {
					rtnArr = new String[] { getYearByDate(periodId) };
				}
			}
		} else if (cuurentTypeInt > targetTypeInt) {// 如果当前时间区间的时间范围大于目标区间类型的区间范围
			// System.out.println("=======3333333333333333");
			if (currentType.equals("D")) {
				// 日已经为最小范围的时间区间类型，故该情况不存在！
			} else if (currentType.equals("M")) {
				if (targetType.equals("D")) {
					String monthBegin = getBgnOfMth(periodId);
					String monthEnd = getEndOfMth(periodId);
					rtnArr = new String[Integer.parseInt(monthEnd) - Integer.parseInt(monthBegin) + 1];
					int i = 0;
					// 循环将该月下的所有日期装入字符串数字
					for (String tmpDate = monthBegin; tmpDate.compareTo(monthEnd) <= 0; tmpDate = getNextDay(tmpDate)) {
						rtnArr[i] = tmpDate;
						i++;
					}
				}
			} else if (currentType.equals("Q")) {
				if (targetType.equals("D")) {
					String monB = null;
					String monE = null;
					String monM = null;
					int quarterInt = Integer.parseInt(periodId.substring(5, 6));
					if (quarterInt == 1) {
						monB = "01";
						monM = "02";
						monE = "03";
					} else if (quarterInt == 2) {
						monB = "04";
						monM = "05";
						monE = "06";
					} else if (quarterInt == 3) {
						monB = "07";
						monM = "08";
						monE = "09";
					} else if (quarterInt == 4) {
						monB = "10";
						monM = "11";
						monE = "12";
					}
					String quarterBBegin = getBgnOfMth(periodId.substring(0, 4) + monB);
					String quarterBEnd = getEndOfMth(periodId.substring(0, 4) + monB);
					String quarterMBegin = getBgnOfMth(periodId.substring(0, 4) + monM);
					String quarterMEnd = getEndOfMth(periodId.substring(0, 4) + monM);
					String quarterEBegin = getBgnOfMth(periodId.substring(0, 4) + monE);
					String quarterEEnd = getEndOfMth(periodId.substring(0, 4) + monE);
					rtnArr = new String[Integer.parseInt(quarterEEnd) - Integer.parseInt(quarterEBegin) + 1
							+ Integer.parseInt(quarterMEnd) - Integer.parseInt(quarterMBegin) + 1
							+ Integer.parseInt(quarterBEnd) - Integer.parseInt(quarterBBegin) + 1];
					int i = 0;
					// 循环将该月下的所有日期装入字符串数字
					for (String tmpDate = quarterBBegin; tmpDate
							.compareTo(quarterEEnd) <= 0; tmpDate = getNextDay(tmpDate)) {
						rtnArr[i] = tmpDate;
						i++;
					}
				} else if (targetType.equals("M")) {
					String monB = null;
					String monE = null;
					String monM = null;
					int quarterInt = Integer.parseInt(periodId.substring(5, 6));
					if (quarterInt == 1) {
						monB = "01";
						monM = "02";
						monE = "03";
					} else if (quarterInt == 2) {
						monB = "04";
						monM = "05";
						monE = "06";
					} else if (quarterInt == 3) {
						monB = "07";
						monM = "08";
						monE = "09";
					} else if (quarterInt == 4) {
						monB = "10";
						monM = "11";
						monE = "12";
					}
					rtnArr = new String[] { periodId.substring(0, 4) + monB, periodId.substring(0, 4) + monM,
							periodId.substring(0, 4) + monE };
				}
			} else if (currentType.equals("H")) {
				if (targetType.equals("D")) {
					String beginDate = null;
					String endDate = null;
					// 获取半年的开始日期与结束日期
					if (periodId.substring(5, 6).equals("1")) {// 如果是上半年
						beginDate = periodId.substring(0, 4) + "0101";
						endDate = periodId.substring(0, 4) + "0630";
					} else {// 如果是下半年
						beginDate = periodId.substring(0, 4) + "0701";
						endDate = periodId.substring(0, 4) + "1231";
					}
					StringBuffer sb = new StringBuffer();
					boolean isFirst = true;
					// 从开始日期到结束日期循环，组织日期字符串,以“,”分隔
					for (String tmpDate = beginDate; tmpDate.compareTo(endDate) <= 0; tmpDate = getNextDay(tmpDate)) {
						if (isFirst) {
							sb.append(tmpDate);
							isFirst = false;
						} else {
							sb.append(",").append(tmpDate);
						}
					}
					// 将字符串转换为数组返回
					rtnArr = sb.toString().split(",");
				} else if (targetType.equals("M")) {
					if (periodId.substring(5, 6).equals("1")) {
						rtnArr = new String[] { year + "01", year + "02", year + "03", year + "04", year + "05",
								year + "06" };
					} else {
						rtnArr = new String[] { year + "07", year + "08", year + "09", year + "10", year + "11",
								year + "12" };
					}
				} else if (targetType.equals("Q")) {
					if (periodId.substring(5, 6).equals("1")) {
						rtnArr = new String[] { year + "Q1", year + "Q2" };
					} else {
						rtnArr = new String[] { year + "Q3", year + "Q4" };
					}
				}

			} else if (currentType.equals("Y")) {
				if (targetType.equals("D")) {
					// 获取年的开始日期与结束日期
					String beginDate = periodId.substring(0, 4) + "0101";
					String endDate = periodId.substring(0, 4) + "1231";
					StringBuffer sb = new StringBuffer();
					boolean isFirst = true;
					// 从开始日期到结束日期循环，组织日期字符串,以“,”分隔
					for (String tmpDate = beginDate; tmpDate.compareTo(endDate) <= 0; tmpDate = getNextDay(tmpDate)) {
						if (isFirst) {
							sb.append(tmpDate);
							isFirst = false;
						} else {
							sb.append(",").append(tmpDate);
						}
					}
					// 将字符串转换为数组返回
					rtnArr = sb.toString().split(",");
				} else if (targetType.equals("M")) {
					rtnArr = new String[] { year + "01", year + "02", year + "03", year + "04", year + "05",
							year + "06", year + "07", year + "08", year + "09", year + "10", year + "11", year + "12" };
				} else if (targetType.equals("Q")) {
					rtnArr = new String[] { year + "Q1", year + "Q2", year + "Q3", year + "Q4" };
				} else if (targetType.equals("H")) {
					rtnArr = new String[] { year + "Y1", year + "Y2" };
				}
			}
		}
		return rtnArr;
	}

	/**
	 * 功能：返回区间类型的时间范围，以帮助比较时间范围大小
	 * 
	 * @author 郑斌
	 * @date 2008-7-11
	 * @param periodType
	 * @return
	 */
	private static int typeIntValue(String periodType) {
		int i = 0;
		if (periodType.equals("Y")) {
			i = 360;
		} else if (periodType.equals("H")) {
			i = 180;
		} else if (periodType.equals("Q")) {
			i = 90;
		} else if (periodType.equals("M")) {
			i = 30;
		} else if (periodType.equals("D")) {
			i = 1;
		}
		return i;
	}

	/**
	 * 功能：获取当前时间区间的区间类型
	 * 
	 * @author 郑斌
	 * @date 2008-7-11
	 * @param periodId
	 * @return
	 */
	public static String getPeriodTypeBuPeriodId(String periodId) {
		String rtnType = "";
		if (periodId.length() == 4) {// 如果区间长度为4，则说明是年
			rtnType = "Y";
		} else if (periodId.length() == 6 && periodId.indexOf("Y") != -1) {// 如果区间包含Y，则说明是半年
			rtnType = "H";
		} else if (periodId.length() == 6 && periodId.indexOf("Q") != -1) {// 如果区间包含Y，则说明是季度
			rtnType = "Q";
		} else if (periodId.length() == 6) {
			rtnType = "M";
		} else if (periodId.length() == 8) {
			rtnType = "D";
		}
		return rtnType;
	}

	/**
	 * 功能：获取该月份的天数
	 * 
	 * @author shanzhen
	 * @date 2008-9-26
	 * @param month
	 * @return
	 */
	public static int getDayCountByMonth(String month) {
	    int dayCount = 30;
	    
		String next_month = getNextMonth(month, 1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf.parse(month + "01");
			date2 = sdf.parse(next_month + "01");
			dayCount = (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		return dayCount;
	}
	
	/**
	 * 方法描述：把日期格式化成连续数字串的形式 再拼接上 appendStr(一般是秒)
	 * eg: 2015.12.02 00:00 ==> 201512020000
	 * @param appendStr 
	 * @param dataStr
	 * @return
	 * @date 2016.3.25
	 */
	public static String formatSearchDate(String dateStr, String appendStr) {
		if(dateStr == null) {
			throw new RuntimeException("传入日期为null!");
		}
		return dateStr.replaceAll("\\.|\\:|\\s", "") + appendStr;
	}
	
	/**
	 * 
	 * @Title: 
	 * @Description: 返回yyyyMMddHHmmss格式日期
	 * @return String
	 * @author Ethan 2016年3月29日
	 */
	public static String getCurrDate() {
		return getCurrentTimeMillisAsString("yyyyMMddHHmmss");
	}
}
