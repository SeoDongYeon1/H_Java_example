package com.KoreaIT.java.ex;

import java.util.Scanner;

import com.KoreaIT.java.ex.controller.ArticleController;
import com.KoreaIT.java.ex.controller.MemberController;

public class App {
	
	App() {
	}

	public void start() {
		System.out.println("==���α׷� ����==");

		Scanner sc = new Scanner(System.in);
		
		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);
		
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
				System.out.println("�������� �ʴ� ��ɾ��Դϴ�.");
			}

		}
		System.out.println("==���α׷� ��==");

		sc.close();
	}
}
