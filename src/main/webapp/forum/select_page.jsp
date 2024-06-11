<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>文章列表 搜尋頁面</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>文章列表 搜尋頁面</h3><h4>( MVC )</h4></td></tr>
</table>

<h3>資料查詢:</h3>

<ul>
  <li><a href='listAllPost.jsp'>List</a> 顯示所有文章.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="forum.do" >
        <b>輸入文章編號 (如01):</b>
        <input type="text" name="postId" value="${param.postId}"><font color=red>${errorMsgs.postId}</font>
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="forumPostSvc" scope="page" class="idv.sophie.forum.model.ForumPostService" />
   
  <li>
     <FORM METHOD="post" ACTION="forum.do" >
       <b>選擇文章編號:</b>
       <select size="1" name="postId">
         <c:forEach var="postVO" items="${forumPostSvc.all}" > 
          <option value="${postVO.postId}">${postVO.postId}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="forum.do" >
       <b>選擇文章標題:</b>
       <select size="1" name="postId">
         <c:forEach var="postVO" items="${forumPostSvc.all}" > 
          <option value="${postVO.postId}">${postVO.postTitle}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>


<h3>文章管理</h3>

<ul>
  <li><a href='addPost.jsp'>Add</a> 新增文章.</li>
</ul>

</body>
</html>