package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
//		boolean isList = false;
		boolean quit = false;
//		l.importData("todolist.txt");
		Menu.displaymenu();
		do {
			Menu.prompt();
//			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				System.out.println("제목순으로 정렬완료");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬완료.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬완료.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬완료.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
			
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;
			
			case "find_cate":
				String key = sc.nextLine().trim();
				TodoUtil.findCateList(l, key);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;

			case "exit":
				quit = true;
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			case "comp":
				int input;
				
				input = sc.nextInt();
				sc.nextLine();
				TodoUtil.complete(l, input);
				break;
				
			case "ls_comp":
				TodoUtil.listAllCompleted(l);
				break;

			default:
				System.out.println("눈을 똑바로 뜨고 정확히 입력해주세요! (도움이 필요하면 -> help)\n");
				break;
			}
			
//			if(isList) TodoUtil.listAll(l);
		} while (!quit);
//		TodoUtil.saveList(l,"todolist.txt");
	}
}
