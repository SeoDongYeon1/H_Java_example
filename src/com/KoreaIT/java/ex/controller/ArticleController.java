package com.KoreaIT.java.ex.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.ex.dto.Article;
import com.KoreaIT.java.ex.util.Util;

public class ArticleController extends Controller{
	private List<Article> articles;
	private Scanner sc;
	
	int lastArticleId = 3;
	
	public ArticleController(Scanner sc) {
		this.articles = new ArrayList<>();
		this.sc = sc;
	}

	@Override
	public void doAction(String actionMethodName, String command) {
		switch (actionMethodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList(command);
			break;
		case "detail":
			showDatail(command);
			break;
		case "delete":
			doDelete(command);
			break;
		case "modify":
			doModify(command);
			break;
		default:
			System.out.println("�������� �ʴ� ��ɾ��Դϴ�.");
			break;
		}
	}
	
	public void showList(String command) {
		if (articles.size() > 0) {
			String searchKeyword = command.substring("article list".length()).trim();

			List<Article> forPrintArticles = articles;

			if (searchKeyword.length() > 0) {
				System.out.println("searchKeyword : " + searchKeyword);
				forPrintArticles = new ArrayList<>();

				for (Article article : articles) {
					if (article.title.contains(searchKeyword)) {
						forPrintArticles.add(article);
					}
				}
				if (forPrintArticles.size() == 0) {
					System.out.println("�˻� ����� �����ϴ�");
					return;
				}
			}
			System.out.println("  ��ȣ  /      ����      /         �ۼ� ��¥         /  ��ȸ��");
			for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
				Article artilce = articles.get(i);
				System.out.printf("   %d   /     %s       /   %s   /   %d \n", artilce.id, artilce.title,
						artilce.regDate, artilce.hit);
			}
		} else {
			System.out.println("�Խñ��� �����ϴ�.");
		}
	}

	public void doWrite() {
		int id = lastArticleId + 1;
		System.out.printf("���� : ");
		String title = sc.nextLine();
		System.out.printf("���� : ");
		String body = sc.nextLine();
		String regDate = Util.getNowDate();
		String updateDate = "";

		Article article = new Article(id, regDate, updateDate, title, body);
		articles.add(article);

		System.out.printf("%d������ �����Ǿ����ϴ�.\n", id);
		lastArticleId++;
	}

	public void showDatail(String command) {
		String[] commandBits = command.split(" ");

		if (commandBits.length < 3) {
			System.out.println("��ɾ Ȯ�����ּ���.");
			return;
		}
		try {
			int id = Integer.parseInt(commandBits[2]);

			Article foundArticle = getArticleById(id);

			if (foundArticle == null) {
				System.out.printf("%d�� �Խù��� �������� �ʽ��ϴ�.\n", id);
			} else {
				foundArticle.IncreaseHit();
				System.out.printf("��ȣ : %d \n", foundArticle.id);
				System.out.printf("���� : %s \n", foundArticle.title);
				System.out.printf("���� : %s \n", foundArticle.body);
				System.out.printf("�ۼ� ��¥ : %s \n", foundArticle.regDate);
				System.out.printf("������ ��¥ : %s \n", foundArticle.updateDate);
				System.out.printf("��ȸ�� : %d \n", foundArticle.hit);
			}
		} catch (NumberFormatException e) {
			System.out.println("article detail (����)�� �Է��ϼ���.");
			return;
		}
	}

	public void doDelete(String command) {
		String[] commandBits = command.split(" ");

		if (commandBits.length < 3) {
			System.out.println("��ɾ Ȯ�����ּ���.");
			return;
		}
		try {
			int id = Integer.parseInt(commandBits[2]);

			int foundIndex = getArticleIndexById(id);

			if (foundIndex == -1) {
				System.out.printf("%d�� �Խñ��� �������� �ʽ��ϴ�.\n", id);
			} else {
				articles.remove(foundIndex);
				System.out.printf("%d�� �Խñ��� �����Ǿ����ϴ�. \n", id);
			}

		} catch (NumberFormatException e) {
			System.out.println("article delete (����)�� �Է��ϼ���.");
			return;
		}
	}

	public void doModify(String command) {
		String[] commandBits = command.split(" ");

		if (commandBits.length < 3) {
			System.out.println("��ɾ Ȯ�����ּ���.");
			return;
		}
		try {
			int id = Integer.parseInt(commandBits[2]);

			Article foundArticle = getArticleById(id);

			if (foundArticle == null) {
				System.out.printf("%d�� �Խù��� �������� �ʽ��ϴ�.\n", id);
			} else {
				System.out.printf("������ ���� : ");
				String title = sc.nextLine();
				System.out.printf("������ ���� : ");
				String body = sc.nextLine();
				String updateDate = Util.getNowDate();

				foundArticle.title = title;
				foundArticle.body = body;
				foundArticle.updateDate = updateDate;
				System.out.printf("%d�� �Խñ��� �����Ǿ����ϴ�.\n", id);
			}

		} catch (NumberFormatException e) {
			System.out.println("article modify (����)�� �Է��ϼ���.");
			return;
		}
	}
	
	private int getArticleIndexById(int id) {
		int i = 0;
		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}
		return -1;
	}

	private Article getArticleById(int id) {
		int index = getArticleIndexById(id);

		if (index != -1) {
			return articles.get(index);
		}
		return null;
	}

	public void maketestData() {
		System.out.println("�׽�Ʈ�� ���� �Խñ� �����Ͱ� �����Ǿ����ϴ�.");
		articles.add(new Article(1, Util.getNowDate(), "", "test1", "test1", 10));
		articles.add(new Article(2, Util.getNowDate(), "", "test2", "test2", 20));
		articles.add(new Article(3, Util.getNowDate(), "", "test3", "test3", 30));
	}


	
}
