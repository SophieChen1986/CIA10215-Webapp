package idv.sophie.forum.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import idv.sophie.forum.model.ForumPostService;
import idv.sophie.forum.model.ForumPostVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
				maxFileSize = 5 * 1024 * 1024,
				maxRequestSize = 5 * 5 * 1024 * 1024)
public class ForumPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("postId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.put("postId", "請輸入文章編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/forum/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer postId = null;
			try {
				postId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.put("postId", "文章編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/forum/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			ForumPostService postService = new ForumPostService();
			ForumPostVO postVO = postService.getOnePost(postId);
			if (postVO == null) {
				errorMsgs.put("postId", "查無資料");
			}
			
			if ( postVO.getPostImg() == null ) {
				
			}
			
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/forum/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("forumPostVO", postVO); // 資料庫取出的empVO物件,存入req
			String url = "/forum/listOnePost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllPost.jsp的請求

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer postId = Integer.valueOf(req.getParameter("postId"));

			/*************************** 2.開始查詢資料 ****************************************/
			ForumPostService postService = new ForumPostService();
			ForumPostVO postVO = postService.getOnePost(postId);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			String param = "?postId=" + postVO.getPostId() + "&memNo=" + postVO.getMemNo() + "&postTitle="
					+ postVO.getPostTitle() + "&postContent= " + postVO.getPostContent() + "&postTime="
					+ postVO.getPostTime() + "&postStatus=" + postVO.getPostStatus();
			String url = "/forum/update_post_input.jsp" + param;
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_post_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自update_post_input.jsp的請求

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer postId = Integer.valueOf(req.getParameter("postId").trim());

			Integer memNo = null;
			try {
				memNo = Integer.valueOf(req.getParameter("memNo").trim());
			} catch (NumberFormatException e) {
				errorMsgs.put("memNo", "會員編號格式錯誤");
			}

			String postTitle = req.getParameter("postTitle");
			if (postTitle == null || postTitle.trim().length() == 0) {
				errorMsgs.put("postTitle", "文章標題: 請勿空白");
			}

			String postContent = req.getParameter("postContent");
			if (postContent == null || postContent.trim().length() == 0) {
				errorMsgs.put("postContent", "文章內容: 請勿空白");
			}

			java.sql.Date postTime = null;
			try {
				postTime = java.sql.Date.valueOf(req.getParameter("postTime").trim());
			} catch (IllegalArgumentException e) {
				errorMsgs.put("postTime", "請輸入日期");
			}

			Integer postStatus = Integer.valueOf(req.getParameter("postStatus"));
			
			Part imageFilePart = req.getPart("upfile1");
			InputStream imgIs = null;
			byte[] imgData = null;
			if (imageFilePart != null && imageFilePart.getSize() > 0) {
				String contentType = imageFilePart.getContentType();
				if ("image/jpeg".equals(contentType) || 
					"image/png".equals(contentType) || 
					"image/gif".equals(contentType)) {
					imgIs = imageFilePart.getInputStream();
					imgData = new byte[imgIs.available()];
					imgIs.read(imgData);
				}
				else {
					errorMsgs.put("upfile1", "檔案格式錯誤!");
				}
			}

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/forum/update_post_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			ForumPostService postService = new ForumPostService();
			ForumPostVO postVO = postService.updatePost(postId, memNo, postTitle, postContent, postTime, postStatus, imgData);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("forumPostVO", postVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/forum/listOnePost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
		}
		
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			Integer memNo = null;
			try {
				memNo = Integer.valueOf(req.getParameter("memNo").trim());
			} catch (NumberFormatException e) {
				errorMsgs.put("memNo", "會員編號格式錯誤");
			}

			String postTitle = req.getParameter("postTitle");
			if (postTitle == null || postTitle.trim().length() == 0) {
				errorMsgs.put("postTitle", "文章標題: 請勿空白");
			}

			String postContent = req.getParameter("postContent");
			if (postContent == null || postContent.trim().length() == 0) {
				errorMsgs.put("postContent", "文章內容: 請勿空白");
			}

			java.sql.Date postTime = null;
			try {
				postTime = java.sql.Date.valueOf(req.getParameter("postTime").trim());
			} catch (IllegalArgumentException e) {
				errorMsgs.put("postTime", "請輸入日期");
			}

			Integer postStatus = Integer.valueOf(req.getParameter("postStatus"));

			Part imageFilePart = req.getPart("upfile1");
			InputStream imgIs = null;
			byte[] imgData = null;
			if (imageFilePart != null && imageFilePart.getSize() > 0) {
				String contentType = imageFilePart.getContentType();
				if ("image/jpeg".equals(contentType) || 
					"image/png".equals(contentType) || 
					"image/gif".equals(contentType)) {
					imgIs = imageFilePart.getInputStream();
					imgData = new byte[imgIs.available()];
					imgIs.read(imgData);
				}
				else {
					errorMsgs.put("upfile1", "檔案格式錯誤!");
				}
			}

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/forum/addPost.jsp");
				failureView.forward(req, res);
				return;
			}
			/*************************** 2.開始新增資料 ***************************************/
			ForumPostService postService = new ForumPostService();

			postService.addPost(memNo, postTitle, postContent, postTime, postStatus, imgData);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/forum/listAllPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer postId = Integer.valueOf(req.getParameter("postId"));

			/*************************** 2.開始刪除資料 ***************************************/
			ForumPostService postSvc = new ForumPostService();
			postSvc.deletePost(postId);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/forum/listAllPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
	}
}
