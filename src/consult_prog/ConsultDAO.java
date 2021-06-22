package consult_prog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultDAO extends JdbcDAO {
	private static ConsultDAO _dao;
	
	private ConsultDAO() {
		// TODO Auto-generated constructor stub
	}
	
	static {
		_dao = new ConsultDAO();
	}
	
	public static ConsultDAO getDAO() {
		return _dao;
	}
	
	//������� ��������
	public int insertConsult(ConsultDTO consult) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rows = 0;
		try {
			con = getConnection();
			
			String sql = "insert into consult values(?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, consult.getCode());
			pstmt.setString(2, consult.getConsultant());
			pstmt.setString(3, consult.getName());
			pstmt.setString(4, consult.getCon_date());
			pstmt.setString(5, consult.getCon_stat());
			
			rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[����]insertConsult() �޼ҵ��� SQL ���� = " + e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
	
	//������� ��������
	public int updateConsult(ConsultDTO consult) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rows = 0;
		try {
			con = getConnection();
			
			String sql = "update consult set consultant=?,name=?,con_date=?,con_stat=? where code=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, consult.getConsultant());
			pstmt.setString(2, consult.getName());
			pstmt.setString(3, consult.getCon_date());
			pstmt.setString(4, consult.getCon_stat());
			pstmt.setString(5, consult.getCode());
			
			rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[����]updateConsult() �޼ҵ��� SQL ���� = " + e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
	
	//������� ��������
	public int deleteConsult(String code) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rows = 0;
		try {
			con = getConnection();
			
			String sql = "delete from consult where code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, code);
			
			rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[����]deleteConsult() �޼ҵ��� SQL ���� = " + e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
	
	//����ڵ�� ������� �˻�, ��ȯ�ϴ� �޼ҵ� - ������ �˻�
	public ConsultDTO selectCodeConsult(String code) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ConsultDTO consult = null;
		try {
			con = getConnection();
			String sql = "select * from consult where code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, code);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				consult = new ConsultDTO();
				consult.setCode(rs.getString("code"));
				consult.setConsultant(rs.getString("consultant"));
				consult.setName(rs.getString("name"));
				consult.setCon_date(rs.getString("con_date").substring(0, 10));
				consult.setCon_stat(rs.getString("con_stat"));
			}
		} catch (SQLException e) {
			System.out.println("[����]selectCodeConsult() �޼ҵ��� SQL ���� = " + e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return consult;
	}
	
	//�̸��� ���޹޾� �ش� �̸��� ������� �˻� �� ��ȯ �޼ҵ�
	public List<ConsultDTO> selectNameConsultList(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ConsultDTO> consultList = new ArrayList<ConsultDTO>();
		try {
			con = getConnection();
			String sql = "select * from consult where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ConsultDTO consult = new ConsultDTO();
				consult.setCode(rs.getString("code"));
				consult.setConsultant(rs.getString("consultant"));
				consult.setName(rs.getString("name"));
				consult.setCon_date(rs.getString("con_date").substring(0, 10));
				consult.setCon_stat(rs.getString("con_stat"));
				consultList.add(consult);
			}
		} catch (SQLException e) {
			System.out.println("[����]selectNameConsultList() �޼ҵ��� SQL ���� = " + e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		
		return consultList;
	}
	
	//��� ������� �˻� �� ��ȯ �޼ҵ�
	public List<ConsultDTO> selectAllConsultList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ConsultDTO> consultList = new ArrayList<ConsultDTO>();
		
		try {
			con = getConnection();
			String sql = "select * from consult order by code";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ConsultDTO consult = new ConsultDTO();
				consult.setCode(rs.getString("code"));
				consult.setConsultant(rs.getString("consultant"));
				consult.setName(rs.getString("name"));
				consult.setCon_date(rs.getString("con_date").substring(0, 10));
				consult.setCon_stat(rs.getString("con_stat"));
				consultList.add(consult);
			}
		} catch (SQLException e) {
			System.out.println("[����]selectAllConsultList() �޼ҵ��� SQL ���� = " + e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return consultList;
	}
	
	//Ű���� �˻� �� ��ȯ �޼ҵ�
	public List<ConsultDTO> keywordSearchList(String keyword, String type) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<ConsultDTO> consultList = new ArrayList<ConsultDTO>();
		try {
			con = getConnection();
			
			if(type == "��ü�˻�") {
				sql = "select * from consult where (code like '%'||?||'%' "
						+ "or name like '%'||?||'%' or consultant like '%'||?||'%' "
						+ "or con_date like '%'||?||'%' or con_stat like '%'||?||'%')";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setString(3, keyword);
				pstmt.setString(4, keyword);
				pstmt.setString(5, keyword);
			} else if(type == "�ڵ�") {
				sql = "select * from consult where code=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, keyword);
			} else if(type == "����") {
				sql = "select * from consult where consultant=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, keyword);
			} else if(type == "�̸�") {
				sql = "select * from consult where name=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, keyword);
			} else if(type == "��㳯¥") {
				sql = "select * from consult where con_date=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, keyword);
			} else if(type == "���Ϸ�") {
				sql = "select * from consult where con_stat='���Ϸ�'";
				pstmt = con.prepareStatement(sql);
			} else {
				sql = "select * from consult where con_stat='��㿹��'";
				pstmt = con.prepareStatement(sql);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ConsultDTO consult = new ConsultDTO();
				consult.setCode(rs.getString("code"));
				consult.setConsultant(rs.getString("consultant"));
				consult.setName(rs.getString("name"));
				consult.setCon_date(rs.getString("con_date").substring(0, 10));
				consult.setCon_stat(rs.getString("con_stat"));
				consultList.add(consult);
			}
		} catch (SQLException e) {
			System.out.println("[����]keywordSearchList() �޼ҵ��� SQL ���� = " + e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		
		return consultList;
	}
}
