package com.KoreaIT.java.ex;

import java.util.Scanner;

import com.KoreaIT.java.ex.controller.ArticleController;
import com.KoreaIT.java.ex.controller.Controller;
import com.KoreaIT.java.ex.controller.MemberController;

public class App {
	
	App() {
	}

	public void start() {
		System.out.println("==���α׷� ����==");

		Scanner sc = new Scanner(System.in);
		
		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);
		Controller controller = null;
		
		articleController.maketestData();
		memberController.maketestData();

		while (true) {
			System.out.print("��ɾ� > ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("��ɾ �Է����ּ���.");
				continue;
			}

			if (command.equals("exit")) {
				break;
			}
			
			String[] cmdDiv = command.split(" ");
			
			String controllerName = cmdDiv[0];
			
			if(cmdDiv.length==1) {
				System.out.println("��ɾ Ȯ�����ּ���.");
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
					System.out.println("�α��� �� �̿����ּ���.");
					continue;
				}
				break;
			}
			
			switch(forLoginCheck) {
			case "member/login":
			case "member/join":
				if(Controller.islogined()) {
					System.out.println("�α׾ƿ� �� �̿����ּ���.");
					continue;
				}
				break;
			}

			controller.doAction(actionMethodName, command);

		}
		System.out.println("==���α׷� ��==");

		sc.close();
	}
}
