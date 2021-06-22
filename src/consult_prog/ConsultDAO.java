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
	
	//상담정보 삽입쿼리
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
			System.out.println("[에러]insertConsult() 메소드의 SQL 오류 = " + e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
	
	//상담정보 변경쿼리
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
			System.out.println("[에러]updateConsult() 메소드의 SQL 오류 = " + e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
	
	//상담정보 삭제쿼리
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
			System.out.println("[에러]deleteConsult() 메소드의 SQL 오류 = " + e.getMessage());
		} finally {
			close(con, pstmt);
		}
		return rows;
	}
	
	//상담코드로 상담정보 검색, 반환하는 메소드 - 단일행 검색
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
			System.out.println("[에러]selectCodeConsult() 메소드의 SQL 오류 = " + e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return consult;
	}
	
	//이름을 전달받아 해당 이름의 상담정보 검색 후 반환 메소드
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
			System.out.println("[에러]selectNameConsultList() 메소드의 SQL 오류 = " + e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		
		return consultList;
	}
	
	//모든 상담정보 검색 후 반환 메소드
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
			System.out.println("[에러]selectAllConsultList() 메소드의 SQL 오류 = " + e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		return consultList;
	}
	
	//키워드 검색 후 반환 메소드
	public List<ConsultDTO> keywordSearchList(String keyword, String type) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<ConsultDTO> consultList = new ArrayList<ConsultDTO>();
		try {
			con = getConnection();
			
			if(type == "전체검색") {
				sql = "select * from consult where (code like '%'||?||'%' "
						+ "or name like '%'||?||'%' or consultant like '%'||?||'%' "
						+ "or con_date like '%'||?||'%' or con_stat like '%'||?||'%')";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setString(3, keyword);
				pstmt.setString(4, keyword);
				pstmt.setString(5, keyword);
			} else if(type == "코드") {
				sql = "select * from consult where code=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, keyword);
			} else if(type == "상담사") {
				sql = "select * from consult where consultant=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, keyword);
			} else if(type == "이름") {
				sql = "select * from consult where name=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, keyword);
			} else if(type == "상담날짜") {
				sql = "select * from consult where con_date=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, keyword);
			} else if(type == "상담완료") {
				sql = "select * from consult where con_stat='상담완료'";
				pstmt = con.prepareStatement(sql);
			} else {
				sql = "select * from consult where con_stat='상담예약'";
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
			System.out.println("[에러]keywordSearchList() 메소드의 SQL 오류 = " + e.getMessage());
		} finally {
			close(con, pstmt, rs);
		}
		
		return consultList;
	}
}
