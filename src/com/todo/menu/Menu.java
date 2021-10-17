package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println("[To do list 관리 명령어 모음] \n");
        System.out.println("1 add : 항목 추가");
        System.out.println("2 del : 항목 삭제");
        System.out.println("3 edit : 항목 수정");
        System.out.println("4 ls : 전체 목록");
        System.out.println("5 ls_cate : 카테고리 목록");
        System.out.println("6 ls_name_asc : 제목순 정렬");
        System.out.println("7 ls_name_desc : 제목 역순 정렬");
        System.out.println("8 ls_date : 날짜순 정렬");
        System.out.println("9 ls_date_desc : 최신순 정렬");
        System.out.println("10 find : 키워드 검색");
        System.out.println("11 find_cate <키워드> : 카테고리 검색");
        System.out.println("12 comp <번호>: 번호에 해당하는 항목을 완료처리");
        System.out.println("13 ls_comp: 완료처리된 항목들을 모두 출력");
        System.out.println("14 ls_today : 오늘까지 해야하는 목록 출");
        System.out.println("15 ls_location : 매달 내가 있는 장소 확인하기 정 [location yyyy/mm/dd식으로 입력해주세요 =)] ");
        System.out.println("16 exit : 종료");

    }
    
    public static void prompt() {
    	System.out.print("\nCommand >> ");
    }
}
