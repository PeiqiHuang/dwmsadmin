package com.dwms.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private DateUtil(){

    }

    public static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simformat = new SimpleDateFormat(dateFormatType);
        return simformat.format(date);
    }

    public static String formatCSTTime(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date d = sdf.parse(date);
        return DateUtil.getDateFormat(d, format);
    }
    
    /**
     * 日期大小比较
     * @return 0:相等;1:fdate比edate大
     * @author zengxiangtao
     * @date 2013-07-01
     */
    public static int compareDate(Date fdate, Date edate) {
        if (fdate.getTime() > edate.getTime()) {
            return 1;
        } else if (fdate.getTime() < edate.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }
}
