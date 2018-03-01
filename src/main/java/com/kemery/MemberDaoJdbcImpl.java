package com.kemery;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MemberDaoJdbcImpl implements MemberDao {

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Member find(String memid) {
		
		String sql = "SELECT memid, lastname, firstname, middlename, status, memdt, password FROM tblmembers WHERE memid = ?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<Member>() {
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member m = new Member();
				m.setMemid(rs.getString("memid"));
				m.setLastnm(rs.getString("lastname"));
				m.setFirstnm(rs.getString("firstname"));
				m.setMiddlenm(rs.getString("middlename"));
				m.setStatus(rs.getString("status"));
				m.setMemdt(rs.getString("memdt"));
				m.setPassword(rs.getInt("password"));
				return m;
			}
		}, memid);
	}
	
}
