<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>�峹�C�� �j�M����</title>

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
   <tr><td><h3>�峹�C�� �j�M����</h3><h4>( MVC )</h4></td></tr>
</table>

<h3>��Ƭd��:</h3>

<ul>
  <li><a href='listAllPost.jsp'>List</a> ��ܩҦ��峹.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="forum.do" >
        <b>��J�峹�s�� (�p01):</b>
        <input type="text" name="postId" value="${param.postId}"><font color=red>${errorMsgs.postId}</font>
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="�e�X">
    </FORM>
  </li>

  <jsp:useBean id="forumPostSvc" scope="page" class="idv.sophie.forum.model.ForumPostService" />
   
  <li>
     <FORM METHOD="post" ACTION="forum.do" >
       <b>��ܤ峹�s��:</b>
       <select size="1" name="postId">
         <c:forEach var="postVO" items="${forumPostSvc.all}" > 
          <option value="${postVO.postId}">${postVO.postId}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="�e�X">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="forum.do" >
       <b>��ܤ峹���D:</b>
       <select size="1" name="postId">
         <c:forEach var="postVO" items="${forumPostSvc.all}" > 
          <option value="${postVO.postId}">${postVO.postTitle}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="�e�X">
     </FORM>
  </li>
</ul>


<h3>�峹�޲z</h3>

<ul>
  <li><a href='addPost.jsp'>Add</a> �s�W�峹.</li>
</ul>

</body>
</html>