<%@page import="vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	//session 정보에서 아이디만 추출
	MemberVO vo=(MemberVO)session.getAttribute("vo");
	String id=vo.getUserid();
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="../pwdCheck.do">
		<label for="userid">아이디</label>
		<input type="text" name="userid" id="userid" value="<%=id%>" readonly><br>
		<label for="password">비밀번호 확인</label>
		<input type="password" name="password" required="required"><br>
		<input type="submit" value="확인">
	</form>
</body>
</html>