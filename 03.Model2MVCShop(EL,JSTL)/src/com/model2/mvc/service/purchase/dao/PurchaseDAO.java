package com.model2.mvc.service.purchase.dao;

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
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.user.dao.UserDao;

public class PurchaseDAO {	
	ProductDAO productDAO = new ProductDAO();
	UserDao userDAO = new UserDao();
	
	public PurchaseDAO() {
			
		
	}

	public void insertPurchase(Purchase purchase) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO TRANSACTION VALUES (seq_product_prod_no.nextval,?,?,?,?,?,?,?,?,sysdate,?,?,?)";

		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setInt(1, purchase.getPurchaseProd().getProdNo()); // 상품번호
		stmt.setString(2, purchase.getBuyer().getUserId()); // 유저ID
		stmt.setString(3, purchase.getPaymentOption()); // 지불방식
		stmt.setString(4, purchase.getReceiverName()); // 받는사람 이름
		stmt.setString(5, purchase.getReceiverPhone()); // 받는사람 번호
		stmt.setString(6, purchase.getDivyAddr());// 배송지주소
		stmt.setString(7, purchase.getDivyRequest()); // 요구사항
		stmt.setString(8, purchase.getTranCode()); // 구매상태코드
		stmt.setString(9, purchase.getDivyDate()); // 배송희망일자
		stmt.setInt(10, purchase.getIsPurchaseCode()); // 구매여부 코드 (1전달)
		stmt.setInt(11, purchase.getQuantity()); // 구매수량

		System.out.println("DB에 insert : " + purchase);

		stmt.executeUpdate();

		con.close();
	}
	
//	public void deletePurchase(int tranNo) throws Exception{
//		Connection con = DBUtil.getConnection();
//		
//		String sql = "DELETE FROM TRANSACTION WHERE TRAN_NO="+tranNo;
//		PreparedStatement stmt = con.prepareStatement(sql);
//		stmt.executeUpdate();
//		con.close();
//		
//	}

	public Purchase findPurchase(int tranNo) throws Exception {
		// 구매번호를 받음
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM TRANSACTION WHERE TRAN_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		Purchase purchase = null;
		while (rs.next()) {
			purchase = new Purchase();
			ProductDAO productDAO = new ProductDAO();
			UserDao userDAO = new UserDao();

			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
			purchase.setBuyer(userDAO.findUser(rs.getString("BUYER_ID")));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DEMAILADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			purchase.setOrderDate(rs.getDate("ORDER_DATA"));
			purchase.setDivyDate(rs.getString("DLVY_DATE"));
			purchase.setIsPurchaseCode(rs.getInt("IS_PURCHASE_CODE"));
			purchase.setQuantity(rs.getInt("QUANTITY"));
			

		}

		con.close();

		return purchase;
	}
	
/*	public Purchase findPurchase2(int prodNo) throws Exception {
		// 상품번호를 받음
		
		Connection con = DBUtil.getConnection();
	
		String sql = "SELECT tr.TRAN_NO,tr.PROD_NO,tr.BUYER_ID,tr.PAYMENT_OPTION,tr.RECEIVER_NAME,tr.RECEIVER_PHONE,tr.DEMAILADDR,tr.DLVY_REQUEST,NVL(tr.TRAN_STATUS_CODE,0) TRAN_STATUS_CODE,tr.ORDER_DATA,tr.DLVY_DATE, tr.IS_PURCHASE_CODE, tr.QUANTITY "
				+ " FROM TRANSACTION tr,PRODUCT pr"
				+ " WHERE tr.prod_no(+) = pr.prod_no AND pr.prod_no= ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Purchase purchase = null;
		while (rs.next()) {
			purchase = new Purchase();
			
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
			purchase.setBuyer(userDAO.findUser(rs.getString("BUYER_ID")));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DEMAILADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			purchase.setOrderDate(rs.getDate("ORDER_DATA"));
			purchase.setDivyDate(rs.getString("DLVY_DATE"));
			purchase.setIsPurchaseCode(rs.getInt("IS_PURCHASE_CODE"));
			purchase.setQuantity(rs.getInt("QUANTITY"));

		}

		con.close();

		return purchase;
	}*/

	
	
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT pr.PROD_NO, pr.PROD_NAME, pr.PRICE, tr.QUANTITY, tr.TRAN_NO, tr.RECEIVER_NAME, tr.RECEIVER_PHONE, tr.TRAN_STATUS_CODE, tr.IS_PURCHASE_CODE " 
				+ " FROM TRANSACTION tr,PRODUCT pr "
				+ " WHERE tr.PROD_NO = pr.PROD_NO AND tr.BUYER_ID= '"+ buyerId + "'";
		sql += " ORDER BY TRAN_NO";
		
		System.out.println("ProductDAO::Original SQL :: " + sql);

		// ==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(search);

		ArrayList<Purchase> list = new ArrayList<Purchase>();
		
		while(rs.next()) {
				Purchase purchase = new Purchase();
				Product product = new Product();
				product.setProdNo(Integer.parseInt(rs.getString("PROD_NO")));
				product.setProdName(rs.getString("PROD_NAME"));
				product.setPrice(Integer.parseInt(rs.getString("PRICE")));
				purchase.setPurchaseProd(product);
				purchase.setTranNo(rs.getInt("TRAN_NO"));
				purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
				purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				purchase.setIsPurchaseCode(rs.getInt("IS_PURCHASE_CODE"));
				purchase.setQuantity(rs.getInt("QUANTITY"));
				

				list.add(purchase); // 리스트에 추가
		}
		System.out.println("list.size() : " + list.size());
		map.put("totalCount", new Integer(totalCount)); // (게시글 총 갯수를 count로)
		map.put("list", list); // list put
		System.out.println("map().size() : " + map.size()); // count 와 list 두개 들어감

		con.close();

		return map; // count와 list가 담긴 map 리턴
	}

	public void updatePurchase(Purchase purchase) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "UPDATE TRANSACTION SET PAYMENT_OPTION=?, RECEIVER_NAME= ?, RECEIVER_PHONE =?, "
				+ "DEMAILADDR=?,DLVY_REQUEST=? ,DLVY_DATE=? ,TRAN_STATUS_CODE=?,IS_PURCHASE_CODE=? WHERE TRAN_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		System.out.println("업데이트에서 희망날짜 : " + CommonUtil.toDateStr2(purchase.getDivyDate()));
		stmt.setString(6, CommonUtil.toDateStr2(purchase.getDivyDate()));
		stmt.setString(7, purchase.getTranCode());
		stmt.setInt(8, purchase.getIsPurchaseCode());
		stmt.setInt(9, purchase.getTranNo());
		stmt.executeUpdate();

		con.close();
	}
	
public Map<String, Object> getDeliveryList(Search search, int prodNo) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM TRANSACTION WHERE PROD_NO =" + prodNo;
		
		System.out.println("ProductDAO::Original SQL :: " + sql);

		// ==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(search);

		ArrayList<Purchase> list = new ArrayList<Purchase>();
		
		while(rs.next()) {
				Purchase purchase = new Purchase();
				purchase.setTranNo(rs.getInt("TRAN_NO"));
				purchase.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
				purchase.setBuyer(userDAO.findUser(rs.getString("BUYER_ID")));
				purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
				purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				purchase.setDivyAddr(rs.getString("DEMAILADDR"));
				purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
				purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				purchase.setOrderDate(rs.getDate("ORDER_DATA"));
				purchase.setDivyDate(rs.getString("DLVY_DATE"));
				purchase.setIsPurchaseCode(rs.getInt("IS_PURCHASE_CODE"));
				purchase.setQuantity(rs.getInt("QUANTITY"));
				

				list.add(purchase); // 리스트에 추가
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
	private String makeCurrentPageSql(String sql, Search search) {
		sql = "SELECT * " + "FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " + " 	FROM (	" + sql
				+ " ) inner_table " + "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ "WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		System.out.println("UserDAO :: make SQL :: " + sql);

		return sql;
	}
}