package com.kemery;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClubDBConfiguration.class);
		
		DataSource dataSource = applicationContext.getBean("dataSource", DataSource.class);
		
		MemberDao memberDao = applicationContext.getBean(MemberDao.class);
		
		Connection connection = dataSource.getConnection();
		
		System.out.println("Club connection closed " + connection.isClosed());
		
		Member m = memberDao.find("Z005");
		if(m == null) {
			System.out.println("Z005 not found");
		} else {
			System.out.println(m.toString());
		}
		
		
		m = memberDao.findByQuery("A043");
		if(m == null) {
			System.out.println("A043 not found");
		} else {
			System.out.println(m.toString());
		}
		
		
		List<Member> members = memberDao.findByStatus("T");
		System.out.println("Number of inactive members = " + members.size());
		for(Member mem : members) {
			System.out.println(mem.toString());
		}
		
		connection.close();

		//System.out.println("Club connection " + connection.isClosed());
		applicationContext.close();
	}

}
