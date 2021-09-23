package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println("[To do list 관리 명령어 모음] \n");
        System.out.println("1 add : 항목 추가");
        System.out.println("2 Delete : 항목 삭제");
        System.out.println("3 edit : 항목 수정");
        System.out.println("4 ls : 전체 목록");
        System.out.println("5 ls_name_asc : 제목순 정렬");
        System.out.println("6 ls_name_desc : 제목 역순 정렬");
        System.out.println("7 ls_date : 날짜순 정렬");
        System.out.println("8 exit : 종료");
//        System.out.println("Enter your choice >");
    }
    
    public static void prompt() {
    	System.out.print("\nCommand >> ");
    }
}
