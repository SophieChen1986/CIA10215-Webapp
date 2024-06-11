package idv.sophie.forum.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ForumPostDAO_JDBC_Impl implements ForumPostDAO_Interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	// String url = "jdbc:mysql://localhost:3306/db01?serverTimezone=Asia/Taipei";
	String url = "jdbc:mysql://localhost:3306/morningcode?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String GET_ALL_STMT = 
			"SELECT post_id, mem_no, post_title, post_content, post_time, post_status, post_img FROM forum_posts";
	private static final String GET_ONE_STMT = 
			"SELECT post_id, mem_no, post_title, post_content, post_time, post_status, post_img FROM forum_posts WHERE post_id = ?";
	private static final String INSERT_STMT = 
			"INSERT INTO forum_posts (mem_no, post_title, post_content, post_time, post_status, post_img) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = 
			"UPDATE forum_posts SET mem_no=?, post_title=?, post_content=?, post_time=?, post_status=? WHERE post_id = ?";
	private static final String UPDATE_WITH_IMG = 
			"UPDATE forum_posts SET mem_no=?, post_title=?, post_content=?, post_time=?, post_status=?, post_img=? WHERE post_id = ?";

	private static final String DELETE = 
			"DELETE FROM forum_posts WHERE post_id = ?";

	@Override
	public void insert(ForumPostVO postVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, postVO.getMemNo());
			pstmt.setString(2, postVO.getPostTitle());
			pstmt.setString(3, postVO.getPostContent());
			pstmt.setDate(4, postVO.getPostTime());
			pstmt.setInt(5, postVO.getPostStatus());
			
			if ( postVO.getPostImg() != null ) {
				pstmt.setBytes(6, postVO.getPostImg());
			} else {
				pstmt.setNull(6, java.sql.Types.BLOB);
			}

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(ForumPostVO postVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			if ( postVO.getPostImg() != null ) {
				pstmt = con.prepareStatement(UPDATE_WITH_IMG);
			}
			else {
				pstmt = con.prepareStatement(UPDATE);
			}

			pstmt.setInt(1, postVO.getMemNo());
			pstmt.setString(2, postVO.getPostTitle());
			pstmt.setString(3, postVO.getPostContent());
			pstmt.setDate(4, postVO.getPostTime());
			pstmt.setInt(5, postVO.getPostStatus());
			
			if ( postVO.getPostImg() != null ) {
				pstmt.setBytes(6, postVO.getPostImg());
				
				pstmt.setInt(7, postVO.getPostId());
			}
			else {
				pstmt.setInt(6, postVO.getPostId());
			}
			
			
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer postId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, postId);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public ForumPostVO findByPrimaryKey(Integer postId) {

		ForumPostVO postVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, postId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				postVO = new ForumPostVO();
				postVO.setPostId(rs.getInt("post_id"));
				postVO.setMemNo(rs.getInt("mem_no"));
				postVO.setPostTitle(rs.getString("post_title"));
				postVO.setPostContent(rs.getString("post_content"));
				postVO.setPostTime(rs.getDate("post_time"));
				postVO.setPostStatus(rs.getInt("post_status"));
				postVO.setPostImg(rs.getBytes("post_img"));
			}

			// 處理資料庫驅動錯誤
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// 處理SQL錯誤
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// 清理JDBC資源 (關閉連線相關資源)
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return postVO;
	}

	@Override
	public List<ForumPostVO> getAll() {
		List<ForumPostVO> list = new ArrayList<ForumPostVO>();
		ForumPostVO postVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				postVO = new ForumPostVO();
				postVO.setPostId(rs.getInt("post_id"));
				postVO.setMemNo(rs.getInt("mem_no"));
				postVO.setPostTitle(rs.getString("post_title"));
				postVO.setPostContent(rs.getString("post_content"));
				postVO.setPostTime(rs.getDate("post_time"));
				postVO.setPostStatus(rs.getInt("post_status"));
				postVO.setPostImg(rs.getBytes("post_img"));
				list.add(postVO);
			}

			// 處理資料庫驅動錯誤
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("無法載入資料庫驅動類別。"
					+ e.getMessage());
			// 處理SQL錯誤
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// 清理JDBC資源 (關閉連線相關資源)
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
}