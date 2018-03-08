package com.kemery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class MemberDaoJdbcImpl implements MemberDao {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private MappingSqlQuery<Member> memberByIdQuery;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		
		this.jdbcTemplate = jdbcTemplate;
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}
	
	
	public void setMemberByIdQuery(MappingSqlQuery<Member> memberByIdQuery) {
	
		this.memberByIdQuery = memberByIdQuery;
	}
	
	
	public Member findByQuery(String memid) {
		
		return memberByIdQuery.findObject(memid);
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
	
	public List<Member> findByStatus(String stat) {
		
		String sql = "SELECT memid, lastname, firstname, middlename, status, memdt, password FROM tblmembers WHERE status = :status";
		return namedParameterJdbcTemplate.query(sql, Collections.singletonMap("status", stat),
				new RowMapper<Member>() {
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
		});
	}
	
	
	public void insert(Member member) {
		PreparedStatementCreatorFactory psCreatorFactory = new PreparedStatementCreatorFactory(
				"insert into account(memid, lastname, firstname, middlename, status, memdt, password) values(?, ?, ?, ?, ?, ?. ?");
		KeyHolder keyHolder = new GeneratedKeyHolder();

		int count = jdbcTemplate.update(
				psCreatorFactory.newPreparedStatementCreator(new Object[] {
						member.getMemid(), member.getLastnm(), member.getFirstnm(), member.getMiddlenm(), member.getStatus(), member.getMemdt(), member.getPassword() }), keyHolder);
		if (count != 1)
			throw new InsertFailedException("Cannot insert account");
//		member.setMemid((String) keyHolder.getKey().longValue());
	}
	
}
