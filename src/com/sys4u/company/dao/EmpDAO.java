package com.sys4u.company.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sys4u.company.dto.DeptData;
import com.sys4u.company.dto.DtoFactory;
import com.sys4u.company.dto.Employee;
import com.sys4u.company.dto.Pagination;
import com.sys4u.company.exception.DAOException;
import com.sys4u.company.exception.DataDuplicatedException;
import com.sys4u.company.exception.NoSuchResourceException;

import static com.sys4u.company.dao.DataResourceCloser.*;

public class EmpDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmpDAO.class);
	protected final Connection conn;

	public EmpDAO(Connection conn) {
		this.conn = conn;
	}

	public boolean existsName(String empName) throws SQLException {

		String sql = "SELECT 1 FROM DUAL WHERE EXISTS ( SELECT 1 FROM EMP WHERE ENAME=?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, empName);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		}
	}

	public boolean exists(int empno) throws SQLException {
		String sql = "SELECT 1 FROM DUAL WHERE EXISTS (SELECT 1 FROM EMP WHERE EMPNO = ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, empno);
			ResultSet rs = pstmt.executeQuery();

			return rs.next();

		} catch (SQLException e) {
			LOGGER.error("SQL 에러: ", e);
			throw e;
		}
	}

	public int insert(Employee employee) {
		int count = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO EMP(EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO) ");
		sql.append("VALUES(empno_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {

			if (exists(employee.getEmpno())) {
				throw new DataDuplicatedException();
			}

			pstmt.setString(1, employee.getEname());
			pstmt.setString(2, employee.getJob());
			pstmt.setInt(3, employee.getMgr());
			pstmt.setDate(4, employee.getHiredate());
			pstmt.setInt(5, employee.getSal());
			pstmt.setInt(6, employee.getComm());
			pstmt.setInt(7, employee.getDeptno());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error("SQL 에러 ", e);
			throw new DAOException(e);
		}

		return count;
	}

	public int update(Employee employee) {
		StringBuilder sql = new StringBuilder();
		int result = 0;
		sql.append("UPDATE EMP");
		sql.append("   SET ENAME = ?,");
		sql.append("       JOB = ?,");
		sql.append("       MGR=?,");
		sql.append("       HIREDATE=?,");
		sql.append("       SAL=?,");
		sql.append("       COMM=?,");
		sql.append("       DEPTNO=?");
		sql.append(" WHERE EMPNO = ?");

		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setString(1, employee.getEname());
			pstmt.setString(2, employee.getJob());
			pstmt.setInt(3, employee.getMgr());
			pstmt.setDate(4, employee.getHiredate());
			pstmt.setInt(5, employee.getSal());
			pstmt.setInt(6, employee.getComm());
			pstmt.setInt(7, employee.getDeptno());
			pstmt.setInt(8, employee.getEmpno());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			return result = 0;
		}
		return result;
	}

	public List<Employee> find(String searchKey, String searchValue, Pagination page) {
		StringBuilder sql = new StringBuilder();
		List<Employee> employees = new ArrayList<>();
		
		sql.append("SELECT EMPNO, ENAME, JOB, MGR, HIREDATE , SAL , COMM ,DEPTNO , DNAME                           ");
	    sql.append("  FROM (SELECT ROWNUM AS RNUM, E.EMPNO, E.ENAME, E.JOB, E.MGR , E.HIREDATE , E.SAL , E.COMM ,E.DEPTNO , D.DNAME");
	    sql.append("          FROM (SELECT * FROM EMP ORDER BY EMPNO DESC) E, DEPT D                                ");
	    sql.append("         WHERE E.DEPTNO = D.DEPTNO"                          );
	    sql.append("           AND "+ searchKey + " LIKE '%' || UPPER(?) || '%'");
	    sql.append("           AND ROWNUM <= ? ");
	    sql.append("       )  TAB                           ");
	    sql.append(" WHERE TAB.RNUM  >= ?");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setString(1, searchValue);
			pstmt.setInt(2, page.getEndRowIndex());
			pstmt.setInt(3, page.getStartRowIndex());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Employee employee = DtoFactory.fromResultSet(rs, Employee.class);
				employees.add(employee);
			}
		} catch (Exception e) {
			LOGGER.error("SQL 에러 ", e);
			throw new DAOException(e);
		}

		return employees;
	}

	public Employee findDetail(int empno) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT E.EMPNO, E.ENAME, E.JOB, E.MGR, E.HIREDATE, E.SAL, E.COMM, E.DEPTNO , D.DNAME");
		sql.append("  FROM EMP E, DEPT D                                      ");
		sql.append(" WHERE E.DEPTNO = D.DEPTNO                                ");
		sql.append("   AND EMPNO = " + empno);

		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Employee employee = new Employee();

				employee.setEmpno(rs.getInt(1));
				employee.setEname(rs.getString(2));
				employee.setJob(rs.getString(3));
				employee.setMgr(rs.getInt(4));
				employee.setHiredate(rs.getDate(5));
				employee.setSal(rs.getInt(6));
				employee.setComm(rs.getInt(7));
				employee.setDeptno(rs.getInt(8));
				employee.setDname(rs.getString(9));

				return employee;
			}

		} catch (SQLException e) {
			LOGGER.error("SQL 에러 ", e);
			throw new DAOException(e);
		}

		throw new NoSuchResourceException("No Such Resource : empno : " + empno);
	}

	public int getMaxNum() {
		String sql = "SELECT MAX(EMPNO) AS MAXNUM FROM EMP";

		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();) {
			if (rs.next()) {
				return rs.getInt("MAXNUM");
			}
		} catch (SQLException e) {
			LOGGER.error("SQL 에러 ", e);
			throw new DAOException(e);
		}

		return 0;
	}

	public int getToTalCount(String searchKey, String searchValue) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(1)     ");
		sql.append("  FROM EMP E, DEPT D");
		sql.append(" WHERE E.DEPTNO = D.DEPTNO ");
		sql.append("   AND " + searchKey + " LIKE '%' || UPPER(?) || '%'");
		
		ResultSet rs = null;
		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString());) {
			pstmt.setString(1, searchValue);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			close(rs);
		}

		return 0;
	}

	public List<String> getJob() {

		List<String> jobList = new ArrayList<String>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT(JOB) ");
		sql.append("  FROM EMP");

		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet result = pstmt.executeQuery();) {

			while (result.next()) {
				jobList.add(result.getString(1));
			}

		} catch (Exception e) {
			LOGGER.error("SQL 에러 ", e);
			throw new DAOException(e);
		}
		return jobList;
	}

	public List<DeptData> getDept() {

		List<DeptData> deptList = new ArrayList<DeptData>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DEPTNO,DNAME ");
		sql.append("  FROM DEPT");

		try (PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet result = pstmt.executeQuery();) {

			while (result.next()) {
				DeptData deptData = new DeptData();
				deptData.setDeptNo(result.getInt(1));
				deptData.setdName(result.getString(2));
				deptList.add(deptData);
			}

		} catch (Exception e) {
			LOGGER.error("SQL 에러 ", e);
			throw new DAOException(e);
		}
		return deptList;
	}

}
