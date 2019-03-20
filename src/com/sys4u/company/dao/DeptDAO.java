package com.sys4u.company.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sys4u.company.dto.Dept;
import com.sys4u.company.dto.Pagination;
import com.sys4u.company.exception.DAOException;
import static com.sys4u.company.dao.DataResourceCloser.close;

public class DeptDAO {
	Connection conn;
	
	public DeptDAO(Connection conn) {
		this.conn = conn;
	}
	
	public List<Dept> find(String searchKey, String searchValue, Pagination page) {
		
		StringBuilder sql = new StringBuilder();
		List<Dept> depts = new ArrayList<>();
		
		sql.append("SELECT DEPTNO, DNAME, LOC                               ");
		sql.append("  FROM (SELECT ROWNUM AS RNUM, D.DEPTNO, D.DNAME, D.LOC ");
		sql.append("          FROM DEPT D                                        ");
		sql.append("         WHERE " + searchKey + " LIKE '%' || UPPER(?) || '%'");
		sql.append("         ORDER BY DEPTNO DESC)                               ");
		sql.append(" WHERE RNUM BETWEEN ? AND ?");

		ResultSet rs = null;
		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setString(1, searchValue);
			pstmt.setInt(2, page.getStartRowIndex());
			pstmt.setInt(3, page.getEndRowIndex());
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Dept dept = new Dept();
				dept.setDeptNo(rs.getInt(1)); 
				dept.setdName(rs.getString(2));
				dept.setLoc(rs.getString(3));
				depts.add(dept);
			}
		} catch (Exception e) {
			throw new DAOException();
		}finally {
			close(rs);
		}
		
		return depts;
	}
	
	public int getDeptCount(String searchKey, String searchValue) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(1)");
		sql.append("  FROM DEPT d   ");
		sql.append(" WHERE ? LIKE '%' || UPPER(?) || '%'");
		
		ResultSet rs = null;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setString(1,searchKey);
			pstmt.setString(2, searchValue);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		} finally{
			close(rs);
		}
		return 0;
	}
	
}
