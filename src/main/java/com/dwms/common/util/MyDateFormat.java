package com.dwms.common.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

public class MyDateFormat extends DateFormat {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> datePatternList;
	private SimpleDateFormat simformat;
	
	public MyDateFormat(List<String> datePatternList) {
		this.datePatternList = datePatternList;
	}
	
	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo,
			FieldPosition fieldPosition) {
		StringBuffer result = new StringBuffer();
		for(String pattern : datePatternList) {
			simformat = new SimpleDateFormat(pattern);
			
			try {
				result.append(simformat.format(date));
			}  catch(Exception e) {
			}
			if(StringUtils.isEmpty(result.toString())) return result;
		}
		return null;
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		Date date = null;
		for(String pattern : datePatternList) {
			simformat = new SimpleDateFormat(pattern);
			try {
				date = simformat.parse(source);
			}  catch(Exception e) {
			}
			if(null != date) return date;
		}
		return null;
	}
	
}
