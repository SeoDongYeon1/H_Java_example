package com.KoreaIT.java.ex.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	/** ������ ���� ��¥/�ð� ��ȯ Str*/
	public static String getNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
        Date now = new Date();
 
        String nowDate = sdf.format(now);
        
		return nowDate;
	}
}