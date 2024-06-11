<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="idv.sophie.forum.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
	ForumPostService forumPostSvc = new ForumPostService();
    List<ForumPostVO> list = forumPostSvc.getAll(); 
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>所有文章列表 - listAllPost.jsp</title>

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
	/* width: 1000px; */
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

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有文章列表 - listAllPost.jsp</h3>
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
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="forumPostVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
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
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/forum/forum.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="postId"  value="${forumPostVO.postId}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/forum/forum.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="postId"  value="${forumPostVO.postId}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>