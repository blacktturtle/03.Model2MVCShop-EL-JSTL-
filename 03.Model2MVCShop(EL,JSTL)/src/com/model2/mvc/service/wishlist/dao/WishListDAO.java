package com.model2.mvc.service.wishlist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.domain.WishList;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.user.dao.UserDao;

public class WishListDAO {	
	ProductDAO productDAO = new ProductDAO();
	UserDao userDAO = new UserDao();
	
	public WishListDAO() {
			
		
	}

	public void insertWishList(WishList wishList) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO WISHLIST VALUES (seq_wishlist_wish_no.nextval,?,?,?,?)";

		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, wishList.getWishListBuyer().getUserId()); // 유저ID
		stmt.setInt(2, wishList.getWishListProd().getProdNo()); // 상품번호
		stmt.setString(3, wishList.getWishListProd().getProdName()); // 상품이름
		stmt.setInt(4, wishList.getWishListProd().getPrice()); // 상품가격
				
		System.out.println("DB에 insert : " + wishList);

		stmt.executeUpdate();

		con.close();
	}
	
	public void deleteWishList(int wishNo) throws Exception{
		Connection con = DBUtil.getConnection();
		
		String sql = "DELETE FROM WISHLIST WHERE WISH_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, wishNo);
		stmt.executeQuery();
		con.close();
	}
	

	public WishList findWishList(int prodNo) throws Exception {
		// 구매번호를 받음
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM WISHLIST WHERE PROD_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();
		
		
		WishList wishList = new WishList();
		Product product = new Product();
		User user = new User();
		while (rs.next()) {
			user.setUserId(rs.getString("BUYER_ID"));
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setPrice(rs.getInt("PRICE"));
			wishList.setWishNo(rs.getInt("WISH_NO"));
			wishList.setWishListBuyer(user);
			wishList.setWishListProd(product);
		}
		con.close();

		return wishList;
	}
	
	
	
	public Map<String, Object> getAllWishList(Search search,String userId) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();
		
		String sql = " SELECT wi.*, NVL(tr.IS_PURCHASe_CODE,0) IS_PURCHASE_CODE "
				+ "FROM WISHLIST wi, TRANSACTION tr "
				+ "WHERE wi.PROD_NO = tr.PROD_NO(+) AND wi.BUYER_ID =  '" +userId + "'";
		
		
		System.out.println("ProductDAO::Original SQL :: " + sql);

		// ==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(search);

		ArrayList<WishList> list = new ArrayList<WishList>();
		
		while(rs.next()) {
				WishList wishList = new WishList();
				Product product = new Product();
				User user = new User();
				user.setUserId(rs.getString("BUYER_ID"));
				product.setProdNo(rs.getInt("PROD_NO"));
				product.setProdName(rs.getString("PROD_NAME"));
				product.setPrice(rs.getInt("PRICE"));				
				wishList.setWishNo(rs.getInt("WISH_NO"));
				wishList.setWishListBuyer(user);
				wishList.setWishListProd(product);
				wishList.setWishIsPurchaseCode(rs.getInt("IS_PURCHASE_CODE"));
				list.add(wishList); // 리스트에 추가
		}
		System.out.println("list.size() : " + list.size());
		map.put("totalCount", new Integer(totalCount)); // (게시글 총 갯수를 count로)
		map.put("list", list); // list put
		System.out.println("map().size() : " + map.size()); // count 와 list 두개 들어감

		con.close();

		return map; // count와 list가 담긴 map 리턴
	}

	
	private int getTotalCount(String sql) throws Exception {

		sql = "SELECT COUNT(*) " + "FROM ( " + sql + ") countTable";

		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		int totalCount = 0;
		if (rs.next()) {
			totalCount = rs.getInt(1);
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	}

	// 게시판 currentPage Row 만 return
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}