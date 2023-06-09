package com.KoreaIT.java.ex;

import java.util.Scanner;

import com.KoreaIT.java.ex.controller.ArticleController;
import com.KoreaIT.java.ex.controller.Controller;
import com.KoreaIT.java.ex.controller.MemberController;

public class App {
	
	App() {
	}

	public void start() {
		System.out.println("==프로그램 시작==");

		Scanner sc = new Scanner(System.in);
		
		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);
		Controller controller = null;
		
		articleController.maketestData();
		memberController.maketestData();

		while (true) {
			System.out.print("명령어 > ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요.");
				continue;
			}

			if (command.equals("exit")) {
				break;
			}
			
			String[] cmdDiv = command.split(" ");
			
			String controllerName = cmdDiv[0];
			
			if(cmdDiv.length==1) {
				System.out.println("명령어를 확인해주세요.");
				continue;
			}
			String actionMethodName = cmdDiv[1];
			String forLoginCheck = controllerName + "/" + actionMethodName;
			
			
			if(controllerName.equals("article")) {
				controller = articleController;
			}
			else if(controllerName.equals("member")) {
				controller = memberController;
			}
			
			switch (forLoginCheck) {
			case "article/write":
			case "article/delete":
			case "article/modify":
			case "member/logout":
			case "member/profile":
				if(Controller.islogined()==false) {
					System.out.println("로그인 후 이용해주세요.");
					continue;
				}
				break;
			}
			
			switch(forLoginCheck) {
			case "member/login":
			case "member/join":
				if(Controller.islogined()) {
					System.out.println("로그아웃 후 이용해주세요.");
					continue;
				}
				break;
			}

			controller.doAction(actionMethodName, command);

		}
		System.out.println("==프로그램 끝==");

		sc.close();
	}
}
