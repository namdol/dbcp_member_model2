package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import vo.MemberVO;

public class MemberDAO {
	private Connection con=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	private DataSource ds=null;
	
	private Connection getConnection() {
		Context ctx;
		try {
			ctx = new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/MySQL");
			con=ds.getConnection();
		} catch (NamingException e) {		
			e.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return con;
	}
	private void close(Connection con,PreparedStatement pstmt,
						ResultSet rs) {
		try{
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(con!=null) con.close();
		}catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	private void close(Connection con,PreparedStatement pstmt){
		try{		
			if(pstmt!=null) pstmt.close();
			if(con!=null) con.close();
		}catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	//로그인
	public MemberVO isLogin(String id,String pwd) {
		MemberVO vo=null;
		
		try {
			con=getConnection();
			con.setAutoCommit(false);
			String sql="select userid,name from member where userid=? "
					+ ""
					+ "and password=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				vo=new MemberVO();
				vo.setUserid(rs.getString(1));
				vo.setName(rs.getString(2));
			}
			con.commit();
		}catch(Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			close(con,pstmt,rs);
		}
		return vo;
	}	
	
	//비밀번호확인
	public MemberVO pwdcheck(String id, String pwd) {
		MemberVO vo=null;
		try {
			con=getConnection();
			con.setAutoCommit(false);
			String sql="select * from member where userid=? "
					+ ""
					+ "and password=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				vo=new MemberVO();
				vo.setUserid(rs.getString(1));
				vo.setName(rs.getString(3));
				vo.setGender(rs.getString(4));
				vo.setEmail(rs.getString(5));
			}
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;
		
	}
	
	//정보수정
	public int update(MemberVO vo) {
		int result=0;
		try {
			con=getConnection();
			con.setAutoCommit(false);
			String sql="update member set password=?,email=? where userid=?";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1, vo.getPassword());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getUserid());

			result=pstmt.executeUpdate();
			if(result>0) {
				con.commit();
			}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	
	//회원가입
	public int join_member(MemberVO vo) {
		int result=0;
		try {
			con=getConnection();
			con.setAutoCommit(false);
			String sql="insert into member values(?,?,?,?,?)";			;
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, vo.getUserid());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getGender());
			pstmt.setString(5, vo.getEmail());
			result=pstmt.executeUpdate();
			if(result>0)
				con.commit();
		}catch(Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			close(con,pstmt);
		}
		return result;		
	}
	public boolean checkId(String userid) {
boolean flag=false;
		
		try {
			con=getConnection();
			con.setAutoCommit(false);
			String sql="select * from member where userid=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();
			if (rs.next()) {//하나라도 있따면
				flag=true;
			}
			con.commit();
		}catch(Exception e) { 
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			close(con,pstmt,rs);
		}
		
		return flag;
	}
	
	
	//회원탈퇴
	//회원정보수정
	//회원리스트
	
	
}
