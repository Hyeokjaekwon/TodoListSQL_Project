package com.todo.service;

import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {

	public static void createItem(TodoList list) {

		String title, desc;
		Scanner sc = new Scanner(System.in);

		System.out.print("< 항목 추가 >" + "\n제목을 입력해 주세요 =)");

		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.print("이미 동일 제목이 존재합니다 =(");
			return;
		}
		sc.nextLine();
		System.out.print("내용 입력 -> ");
		desc = sc.nextLine().trim();

		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("내용이 추가되었습니다 =) ");
	}

	public static void deleteItem(TodoList l) {

		Scanner sc = new Scanner(System.in);
		System.out.print("< 항목 제거 >" + "\n제거할 항목을 입력해주세요 =) ");
		String title = sc.next();

		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("내용이 삭제되었습니다 =) ");
				break;
			}
		}
	}

	public static void updateItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.print("< 항목 수정 > \n" + "수정할 항목을 입력해주세요 =) ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("제목이 존재하지 않습니다.");
			return;
		}

		System.out.print("원하시는 새로운 제목을 입력해주세요 =) ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("이런,,이미 동일 제목이 존재합니다 =(");
			return;
		}
		sc.nextLine();
		System.out.print("새로운 내용을 입력해주세요 =) ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정 완료~!");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("\n< 전체목록 >\n");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	
	public static void loadList(TodoList l, String filename) {
		int count = 0;
		try {
			File fl = new File(filename);
			if (!fl.exists()) {
				System.out.println("파일이 존재하지 않습니다.");
				return;
			}
			else {
				BufferedReader b = new BufferedReader(new FileReader(filename));
				String s;
				while ((s = b.readLine()) != null) {
					count ++;
					StringTokenizer st = new StringTokenizer(s, "##");
					String title = st.nextToken();
					String desc = st.nextToken();
					TodoItem t = new TodoItem(title, desc);
					l.addItem(t);
				}
				b.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(count+"개의 줄을 읽었습니다.");
	}

	public static void saveList(TodoList l, String filename) {
		try {
			FileWriter f = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				f.write(item.toSaveString());
			}
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("파일 저장 완료\n");
	}
}

