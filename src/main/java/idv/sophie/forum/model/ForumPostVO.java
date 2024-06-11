package idv.sophie.forum.model;


import java.sql.Date;
import java.util.Base64;

public class ForumPostVO implements java.io.Serializable {
	private static final long serialVersionUID = 2451452817220201708L;
	
	private Integer postId;
	private Integer memNo;
	private String postTitle;
	private String postContent;
	private Date postTime;
	private Integer postStatus;
	private byte[] postImg;
	private String base64PostImg;
	
	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getMemNo() {
		return memNo;
	}
	
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	
	public String getPostTitle() {
		return postTitle;
	}
	
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	
	public String getPostContent() {
		return postContent;
	}
	
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	
	public Date getPostTime() {
		return postTime;
	}
	
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
	public Integer getPostStatus() {
		return postStatus;
	}
	
	public void setPostStatus(Integer postStatus) {
		this.postStatus = postStatus;
	}
	
	public byte[] getPostImg() {
		return postImg;
	}

	public void setPostImg(byte[] postImg) {
		this.postImg = postImg;
		if ( postImg != null ) {
			base64PostImg = Base64.getEncoder().encodeToString(postImg);
		}
	}
	
	public String getBase64PostImg() {
		if ( base64PostImg != null ) return base64PostImg;
		return null;
	}
	
	@Override
	public String toString() {
	    return "Post{" +
	            "postId=" + postId +
	            ", memNo=" + memNo +
	            ", postTitle='" + postTitle + '\'' +
	            ", postContent='" + postContent + '\'' +
	            ", postTime=" + postTime +
	            ", postStatus=" + postStatus +
	            '}';
	}
}