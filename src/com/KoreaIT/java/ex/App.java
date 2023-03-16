package com.KoreaIT.java.ex;

import java.util.Scanner;

import com.KoreaIT.java.ex.controller.ArticleController;
import com.KoreaIT.java.ex.controller.MemberController;

public class App {
	
	App() {
	}

	public void start() {
		System.out.println("==프로그램 시작==");

		Scanner sc = new Scanner(System.in);
		
		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);
		
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

			if (command.startsWith("article list")) {
				articleController.showList(command);
			} else if (command.equals("article write")) {
				articleController.doWrite();
			} else if (command.startsWith("article detail")) {
				articleController.showDatail(command);
			} else if (command.startsWith("article delete")) {
				articleController.doDelete(command);
			} else if (command.startsWith("article modify")) {
				articleController.doModify(command);
			} else if (command.equals("member join")) {
				memberController.doJoin();
			} else if (command.equals("member list")) {
				memberController.showList();
			} else {
				System.out.println("존재하지 않는 명령어입니다.");
			}

		}
		System.out.println("==프로그램 끝==");

		sc.close();
	}
}
