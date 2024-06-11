package idv.sophie.forum.model;

import java.util.List;

public interface ForumPostDAO_Interface {
	public List<ForumPostVO> getAll();  // 取得所有文章資料
	public ForumPostVO findByPrimaryKey(Integer postId); // 根據id查一筆文章資料
	
	public void insert(ForumPostVO postVO);  // 新增文章
	public void update(ForumPostVO postVO);  // 根據id更新文章內容
	public void delete(Integer postId);      // 刪除文章
}
