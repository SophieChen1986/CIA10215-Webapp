package idv.sophie.forum.model;

import java.util.List;

public class ForumPostService {
	
	private ForumPostDAO_Interface dao;
	
	public ForumPostService() {
		dao = new ForumPostDAO_JDBC_Impl();
	}
	
	public List<ForumPostVO> getAll() {
		return dao.getAll();
	}
	
	public ForumPostVO getOnePost(Integer postId) {
		return dao.findByPrimaryKey(postId);
	}
	
	public ForumPostVO addPost(Integer memNo,
			  			String postTitle,
			  			String postContent,
			  			java.sql.Date postTime,
			  			Integer postStatus,
			  			byte[] postImg) {
		
		ForumPostVO postVO = new ForumPostVO();
		postVO.setMemNo(memNo);
		postVO.setPostTitle(postTitle);
		postVO.setPostContent(postContent);
		postVO.setPostTime(postTime);
		postVO.setPostStatus(postStatus);
		postVO.setPostImg(postImg);
		
		dao.insert(postVO);
		
		return postVO;
	}
	
	public void addPost(ForumPostVO postVO) {
		dao.insert(postVO);
	}
	
	public ForumPostVO updatePost(Integer postId,
								  Integer memNo,
								  String postTitle,
								  String postContent,
								  java.sql.Date postTime,
								  Integer postStatus,
								  byte[] postImg) {

		ForumPostVO postVO = new ForumPostVO();
		postVO.setPostId(postId);
		postVO.setMemNo(memNo);
		postVO.setPostTitle(postTitle);
		postVO.setPostContent(postContent);
		postVO.setPostTime(postTime);
		postVO.setPostStatus(postStatus);
		postVO.setPostImg(postImg);
		
		dao.update(postVO);

		return dao.findByPrimaryKey(postId);
	}

	public void deletePost(Integer postId) {
		dao.delete(postId);
	}
}
