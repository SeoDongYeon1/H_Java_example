package com.KoreaIT.java.ex.controller;

import com.KoreaIT.java.ex.dto.Member;

public abstract class Controller {
	
	public abstract void doAction(String actionMethodName, String command);

	public static Member loginedMember = null;
	
	public static boolean islogined() {
		return loginedMember != null;
	}
}
