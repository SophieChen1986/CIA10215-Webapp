<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="idv.sophie.forum.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  //EmpVO empVO = (EmpVO) request.getAttribute("empVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>文章資料 - listOnePost.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>文章資料 - listOneEmp.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>文章編號</th>
		<th>會員編號</th>
		<th>文章標題</th>
		<th>文章內容</th>
		<th>圖片</th>
		<th>發佈時間</th>
		<th>發佈狀態</th>
	</tr>
	<tr>
		<td>${forumPostVO.postId}</td>
		<td>${forumPostVO.memNo}</td>
		<td>${forumPostVO.postTitle}</td>
		<td>${forumPostVO.postContent}</td>
		<c:if test="${forumPostVO.base64PostImg != null}">
			<td><img src="data:image/jpg;base64,${forumPostVO.base64PostImg}" height=120></td>
		</c:if>
		<c:if test="${forumPostVO.base64PostImg == null}">
			<td><img src="images/noImg.jpg" height=120></td>
		</c:if>
		<td>${forumPostVO.postTime}</td>
		<td>${forumPostVO.postStatus}</td>
	</tr>
</table>

</body>
</html>