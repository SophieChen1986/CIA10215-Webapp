package idv.sophie.forum;
import java.util.List;

import idv.sophie.forum.model.ForumPostService;
import idv.sophie.forum.model.ForumPostVO;

public class Test {

	public static void main(String[] args) {
		
		ForumPostService forumPostService = new ForumPostService();
		
		List<ForumPostVO> postList = forumPostService.getAll();
		
		for( ForumPostVO postVO : postList ) {
			System.out.println(postVO);
		}
	}
}
