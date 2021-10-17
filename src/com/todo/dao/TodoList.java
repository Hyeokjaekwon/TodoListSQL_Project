package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;


public class TodoList {
	private List<TodoItem> list;
	Connection conn;
	
	public TodoList() {
		this.conn = DbConnect.getConnection();
		
	}
	
	public boolean isDuplicate(String title) {
		String sql = "SELECT * FROM list WHERE title = ?";
		PreparedStatement pstmt;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date)" + " values (?,?,?,?,?);";
			int records = 0;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String title = st.nextToken();
				String category = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();

				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				int count = pstmt.executeUpdate();
				if (count > 0)
					records++;
				pstmt.close();
			}
			System.out.println(records + "records read!!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category,is_completed, due_time, place,current_date, due_date)"
				+ " values (?,?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setInt(4, t.getIs_completed());
			pstmt.setString(5, t.getDue_time());
			pstmt.setString(6, t.getPlace());
			pstmt.setString(7, t.getCurrent_date());
			pstmt.setString(8, t.getDue_date());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete FROM list WHERE id = ?";
		PreparedStatement pstmt;
		
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
//
//	void editItem(TodoItem t, TodoItem updated) {
//		int index = list.indexOf(t);
//		list.remove(index);
//		list.add(updated);
//	}
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, is_completed =?, due_time=?, place=?, current_date=?, due_date=?" + " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setInt(4, t.getIs_completed());//Is_complete추가
			pstmt.setString(5, t.getDue_time());//due_time
			pstmt.setString(6, t.getPlace());//place추가
			pstmt.setString(7, t.getCurrent_date());
			pstmt.setString(8, t.getDue_date());
			pstmt.setInt(9, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
	
		try {
			stmt = conn.createStatement();
			String sql="SELECT * FROM list ";
			ResultSet rs= stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category =rs.getString("category");
				String title =rs.getString("title");
				String description =rs.getString("memo");
				int is_completed = rs.getInt("is_completed");
				String due_time = rs.getString("due_time");
				String place = rs.getString("place");
				String due_date =rs.getString("due_date");
				String current_date =rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date,is_completed,place,due_time);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String key) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		key = "%" + key + "%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, key);
			pstmt.setString(2, key);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				int is_completed = rs.getInt("is_completed");
				String due_time = rs.getString("due_time");
				String place = rs.getString("place");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date,is_completed,place,due_time);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if (ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String category = rs.getString("category");
				int is_completed = rs.getInt("is_completed");
				String due_time = rs.getString("due_time");
				String place = rs.getString("place");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date,is_completed,place,due_time);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("category"));
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getListCategory(String key) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, key);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String category = rs.getString("category");
				int is_completed = rs.getInt("is_completed");
				String due_time = rs.getString("due_time");
				String place = rs.getString("place");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date,is_completed,place,due_time);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int comp(int key) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		int count = 0;
		
		String sql = "update list set is_completed=1 where id=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, key);

			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		if(count != 0) {
			System.out.println("완료 체크하였습니다.");
		}
		return count;
	}

	public ArrayList<TodoItem> listComp(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		String sql = "Select * from list where is_completed = ?";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int is_completed = rs.getInt("is_completed");
				String due_time = rs.getString("due_time");
				String place = rs.getString("place");
				TodoItem t = new TodoItem(category, title, description, due_date,is_completed,place,due_time);
				t.setCategory(category);
				t.setCurrent_date(current_date);
				t.setDue_date(due_date);
				t.setId(id);
				t.setIs_completed(is_completed);
				list.add(t);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getTodayList(String key) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE due_date = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, key);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String due_time = rs.getString("due_time");
				String place = rs.getString("place");
				TodoItem t = new TodoItem(category, title, description, due_date,is_completed,place,due_time);
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getImportantList(int key) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE importat = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, key);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String due_time = rs.getString("due_time");
				String place = rs.getString("place");
				TodoItem t = new TodoItem(category, title, description, due_date,is_completed,place,due_time);
				t.setCategory(category);
				t.setCurrent_date(current_date);
				t.setDue_date(due_date);
				t.setId(id);
				t.setIs_completed(is_completed);
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListPlace(String location){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE due_date like ? ORDER BY planTime";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+location+"%");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String category = rs.getString("category");
				int is_completed = rs.getInt("is_completed");
				String due_time = rs.getString("planTime");
				String place = rs.getString("location");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date,is_completed,place,due_time);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("[목록]");
		for (TodoItem myitem : list) {
			System.out.println(myitem.toString());
		}
	}
//
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}
//
//	public Boolean isDuplicate(String title) {
//		for (TodoItem item : list) {
//			if (title.equals(item.getTitle()))
//				return true;
//		}
//		return false;
//	}

	public int getSize() {
		return list.size();
	}
}
