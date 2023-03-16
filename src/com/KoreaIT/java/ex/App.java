package com.KoreaIT.java.ex;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.ex.dto.Article;
import com.KoreaIT.java.ex.dto.Member;
import com.KoreaIT.java.ex.util.Util;

public class App {
	static List<Article> articles;
	static List<Member> members;
	
	App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void start() {
		System.out.println("==���α׷� ����==");

		maketestData();

		int lastArticleId = 3;
		int lastMemberId = 0;

		Scanner sc = new Scanner(System.in);

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
							continue;
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
			} else if (command.equals("article write")) {
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
			} else if (command.startsWith("article detail")) {
				String[] commandBits = command.split(" ");

				if (commandBits.length < 3) {
					System.out.println("��ɾ Ȯ�����ּ���.");
					continue;
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
					continue;
				}

			} else if (command.startsWith("article delete")) {
				String[] commandBits = command.split(" ");

				if (commandBits.length < 3) {
					System.out.println("��ɾ Ȯ�����ּ���.");
					continue;
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
					continue;
				}
			} else if (command.startsWith("article modify")) {
				String[] commandBits = command.split(" ");

				if (commandBits.length < 3) {
					System.out.println("��ɾ Ȯ�����ּ���.");
					continue;
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
					continue;
				}
			} else if (command.equals("member join")) {
				int id = lastMemberId + 1;
				String loginId = null;
				String loginPw = null;
				while (true) {
					System.out.printf("���̵� : ");
					loginId = sc.nextLine();

					if (isJoinableLoginId(loginId) == false) {
						System.out.println("�̹� ������� ���̵��Դϴ�.");
						continue;
					} else {
						System.out.println("��� ������ ���̵��Դϴ�.");
						break;
					}
				}

				while (true) {
					System.out.printf("��й�ȣ : ");
					loginPw = sc.nextLine();
					System.out.printf("��й�ȣ ��Ȯ��: ");
					String loginPw1 = sc.nextLine();
					if (loginPw.equals(loginPw1)) {
						System.out.println("��й�ȣ�� ��ġ�մϴ�.");
						break;
					} else {
						System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
						continue;
					}
				}

				System.out.printf("�̸� : ");
				String name = sc.nextLine();

				String regDate = Util.getNowDate();
				String updateDate = "";

				Member member = new Member(id, loginId, loginPw, name, regDate, updateDate);
				members.add(member);

				System.out.printf("%s�� ȸ������ �Ǿ����ϴ�.\n", name);
				lastMemberId++;
			} else if (command.equals("member list")) {
				if (members.size() == 0) {
					System.out.println("ȸ���� �������� �ʽ��ϴ�.");
				} else {
					System.out.println("��ȣ / �̸�        / ���̵�     / ��й�ȣ     / ��������     ");
					for (int i = members.size() - 1; i >= 0; i--) {
						Member member = members.get(i);
						System.out.printf("%d   / %s         / %s     / %s     / %s     \n", member.id, member.name,
								member.loginId, member.loginPw, member.regDate);
					}
				}
			} else {
				System.out.println("�������� �ʴ� ��ɾ��Դϴ�.");
			}

		}
		System.out.println("==���α׷� ��==");

		sc.close();
	}

	public static boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return true;
		}

		return false;
	}

	private static int getMemberIndexByLoginId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	private static int getArticleIndexById(int id) {
		int i = 0;
		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public static Article getArticleById(int id) {
		int index = getArticleIndexById(id);

		if (index != -1) {
			return articles.get(index);
		}
		return null;
	}

	static void maketestData() {
		System.out.println("�׽�Ʈ�� ���� �׽�Ʈ �����Ͱ� �����Ǿ����ϴ�.");
		articles.add(new Article(1, Util.getNowDate(), "", "test1", "test1", 10));
		articles.add(new Article(2, Util.getNowDate(), "", "test2", "test2", 20));
		articles.add(new Article(3, Util.getNowDate(), "", "test3", "test3", 30));
	}
}
