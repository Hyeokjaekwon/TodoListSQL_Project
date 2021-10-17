package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private String title;
	private String desc;
	private String category;
	private String current_date;
	private String due_date;
	private int is_completed;
	private int id;
	//장소와 시간 필드 추가 
	private String place;
    private String due_time;

	public TodoItem(String category,String title, String desc, String due_date) {
		this.category = category;
		this.title = title;
		this.desc = desc;
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		this.current_date = f.format(new Date());
        this.due_date = due_date;
	}

	public TodoItem(String category, String title, String desc, String due_date, int is_completed, String place,String due_time){
    	this.category=category;
        this.title=title;
        this.desc=desc;
        this.due_date=due_date;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		this.current_date = f.format(new Date());
        this.place=place;
        this.due_time=due_time;
        this.is_completed = is_completed;
 
    }
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCurrent_date() {
		return current_date;
	}

	public void setCurrent_date(String current_date) {
		this.current_date = current_date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	@Override
//	public String toString() {
//		return id + ". " + "[" + category + "] " + title + " - " + desc + " - " + due_date + " - " + current_date;
//	}
	public String toString() {
    	if(is_completed == 0)
    		return id + "["+category+"]"+" "+title+" / "+desc+" / "+due_date +" / "+ due_time +" / "+ place +" / "+current_date;
    	else
    		return id+ "["+category+"]"+" "+title+"[V]"+" / "+desc+" / "+due_date +" / "+ due_time +" / "+ place +" / "+current_date;
    }

	public String toSaveString() {
		return category +"##"+ title + "##" + desc + "##" + due_date + "##" + due_time+ "##" + place+ "##" +current_date + "\n";
	}


	public void setId(int id) {
		this.id = id;
		
	}

	public int getId() {
		return id;
	}

	public int getIs_completed() {
		return is_completed;
    }
    public void setIs_completed(int is_completed) {
    	this.is_completed = is_completed;
    }

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDue_time() {
		return due_time;
	}

	public void setDue_time(String due_time) {
		this.due_time = due_time;
	}

    
	
	


}
