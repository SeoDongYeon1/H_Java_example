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
			System.out.println("존재하지 않는 명령어입니다.");
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
					System.out.println("검색 결과가 없습니다");
					return;
				}
			}
			System.out.println("  번호  /      제목      /    작성자        /       작성 날짜         /  조회수");
			for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
				Article article = articles.get(i);
				System.out.printf("   %d   /     %s       /    %d      / %s   /   %d \n", article.id, article.title, article.memberId,
						article.regDate, article.hit);
			}
		} else {
			System.out.println("게시글이 없습니다.");
		}
	}

	public void doWrite() {
		int id = lastArticleId + 1;
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		String regDate = Util.getNowDate();
		String updateDate = "";

		Article article = new Article(id, regDate, updateDate, title, body, loginedMember.id);
		articles.add(article);

		System.out.printf("%d번글이 생성되었습니다.\n", id);
		lastArticleId++;
	}

	public void showDatail(String command) {
		String[] commandBits = command.split(" ");

		if (commandBits.length < 3) {
			System.out.println("명령어를 확인해주세요.");
			return;
		}
		try {
			int id = Integer.parseInt(commandBits[2]);

			Article foundArticle = getArticleById(id);

			if (foundArticle == null) {
				System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			} else {
				foundArticle.IncreaseHit();
				System.out.printf("번호 : %d \n", foundArticle.id);
				System.out.printf("제목 : %s \n", foundArticle.title);
				System.out.printf("내용 : %s \n", foundArticle.body);
				System.out.printf("작성자 : %d \n", foundArticle.memberId);
				System.out.printf("작성 날짜 : %s \n", foundArticle.regDate);
				System.out.printf("수정된 날짜 : %s \n", foundArticle.updateDate);
				System.out.printf("조회수 : %d \n", foundArticle.hit);
			}
		} catch (NumberFormatException e) {
			System.out.println("article detail (숫자)를 입력하세요.");
			return;
		}
	}

	public void doDelete(String command) {
		String[] commandBits = command.split(" ");

		if (commandBits.length < 3) {
			System.out.println("명령어를 확인해주세요.");
			return;
		}
		try {
			int id = Integer.parseInt(commandBits[2]);

			Article foundArticle = getArticleById(id);
			
			if (foundArticle == null) {
				System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
				return;
			} 
			
			if(loginedMember.id != foundArticle.id) {
				System.out.println("이 게시글에 권한이 없습니다.");
			}
			else {
				articles.remove(foundArticle);
				System.out.printf("%d번 게시글이 삭제되었습니다. \n", id);
			}

		} catch (NumberFormatException e) {
			System.out.println("article delete (숫자)를 입력하세요.");
			return;
		}
	}

	public void doModify(String command) {
		String[] commandBits = command.split(" ");

		if (commandBits.length < 3) {
			System.out.println("명령어를 확인해주세요.");
			return;
		}
		try {
			int id = Integer.parseInt(commandBits[2]);

			Article foundArticle = getArticleById(id);

			if (foundArticle == null) {
				System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			}
			
			if(loginedMember.id != foundArticle.id) {
				System.out.println("이 게시글에 권한이 없습니다.");
			}
			else {
				System.out.printf("수정할 제목 : ");
				String title = sc.nextLine();
				System.out.printf("수정할 내용 : ");
				String body = sc.nextLine();
				String updateDate = Util.getNowDate();

				foundArticle.title = title;
				foundArticle.body = body;
				foundArticle.updateDate = updateDate;
				System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
			}

		} catch (NumberFormatException e) {
			System.out.println("article modify (숫자)를 입력하세요.");
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
		System.out.println("테스트를 위한 게시글 데이터가 생성되었습니다.");
		articles.add(new Article(1, Util.getNowDate(), "", "test1", "test1", 1, 10));
		articles.add(new Article(2, Util.getNowDate(), "", "test2", "test2", 2, 20));
		articles.add(new Article(3, Util.getNowDate(), "", "test3", "test3", 3, 30));
	}


	
}
