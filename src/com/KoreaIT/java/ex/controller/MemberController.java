package com.KoreaIT.java.ex.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.ex.dto.Member;
import com.KoreaIT.java.ex.util.Util;

public class MemberController extends Controller{
	int lastMemberId = 3;
	private List<Member> members;
	private Scanner sc;
	private Member loginedMember = null;
	
	public MemberController(Scanner sc) {
		this.members = new ArrayList<>();
		this.sc = sc;
	}
	
	@Override
	public void doAction(String actionMethodName, String command) {
		switch (actionMethodName) {
		case "join":
			doJoin();
			break;
		case "list":
			showList();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		case "profile":
			showProfile();
			break;
		default:
			System.out.println("�������� �ʴ� ��ɾ��Դϴ�.");
			break;
		}
	}


	public void doJoin() {
		if(islogined()) {
			System.out.println("�α׾ƿ� �� �̿����ּ���.");
			return;
		}
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
	}
	
	public void showList() {
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
	}
	
	private void doLogin() {
		if(islogined()) {
			System.out.println("�α׾ƿ� �� �̿����ּ���.");
			return;
		}
		System.out.printf("�α��� ���̵� : ");
		String loginId = sc.nextLine();
		System.out.printf("�α��� ��й�ȣ : ");
		String loginPw = sc.nextLine();
		
		Member member = getMemberByLoginId(loginId);
		
		if(member==null) {
			System.out.println("���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���.");
			return;
		}
		
		if(member.loginPw.equals(loginPw)) {
			System.out.printf("�α��� ����! %s�� �ݰ����ϴ�.\n", member.name);
			loginedMember = member;
		}else {
			System.out.println("���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���.");
		}
		
	}
	private void doLogout() {
		if(islogined()==false) {
			System.out.println("�α��� �� �̿����ּ���.");
			return;
		}
		System.out.println("�α׾ƿ� �Ǿ����ϴ�.");
		loginedMember = null;
		
	}
	
	private void showProfile() {
		if(islogined()==false) {
			System.out.println("�α��� �� �̿����ּ���.");
			return;
		}
		System.out.printf("ȸ�� ��ȣ : %d\n", loginedMember.id);
		System.out.printf("���̵� : %s\n", loginedMember.loginId);
		System.out.printf("�̸� : %s\n", loginedMember.name);
		return;
	}
	
	private boolean islogined() {
		return loginedMember != null;
	}
	
	private boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
		
		if (index == -1) {
			return true;
		}
		
		return false;
	}
	
	private int getMemberIndexByLoginId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	private Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
			if (index!=-1) {
				return members.get(index);
		}
		return null;
	}
	
	public void maketestData() {
		System.out.println("�׽�Ʈ�� ���� ȸ�� �����Ͱ� �����Ǿ����ϴ�.");
		members.add(new Member(1, "test1", "test1", "test1", Util.getNowDate(), ""));
		members.add(new Member(2, "test2", "test2", "test2", Util.getNowDate(), ""));
		members.add(new Member(3, "test3", "test2", "test3", Util.getNowDate(), ""));
	}
}


