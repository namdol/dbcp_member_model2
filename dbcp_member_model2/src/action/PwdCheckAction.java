package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import vo.MemberVO;

public class PwdCheckAction implements Action {
	
	private String path;
	
	public PwdCheckAction(String path) {
		super();
		this.path = path;
	}

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//pwdcheck.jsp에서 id와 password 가져오기
		String userid=req.getParameter("userid");
		String pwd=req.getParameter("password");
		//아이디랑 비밀번호 확인하는 DAO 메소드
		MemberDAO dao=new MemberDAO();
		//password가 일치하면 해당 사람의 정보를 얻어오기
		MemberVO vo=dao.pwdcheck(userid, pwd);
		if (vo!=null) {
			req.setAttribute("vo", vo);
			return new ActionForward(path,false);
		}else {
			path="view/pwdcheck.jsp?msg=fail";
			return new ActionForward(path, true);
		}
		//얻어온 정보를 request에 담고 
		//페이지 이동
		
		//password가 일치하지 않으면
		//비밀번호 확인 메세지 띄운 후 pwdcheck.jsp로 돌아가기
		
	}

}
