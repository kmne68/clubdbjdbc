package com.kemery;

import java.util.List;

public interface MemberDao {

	public Member find(String memid);
	public Member findByQuery(String memid);
	public List<Member> findByStatus(String status);
}
