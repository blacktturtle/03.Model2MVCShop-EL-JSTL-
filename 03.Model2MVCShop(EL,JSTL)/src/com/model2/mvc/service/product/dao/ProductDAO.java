package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

public class ProductDAO {
	

	public ProductDAO() {
	}

	public void insertProduct(Product product) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO PRODUCT VALUES (seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE)";

		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, product.getProdName()); // ��ǰ��
		stmt.setString(2, product.getProdDetail()); // ��ǰ������
		stmt.setString(3, product.getManuDate().replace("-", "")); // ��ǰ��������
		stmt.setInt(4, product.getPrice()); // ��ǰ����
		stmt.setString(5, product.getFileName()); // �̹�������
		stmt.executeUpdate();

		con.close();
	}

	public Product findProduct(int prodNo) throws Exception {
		
		
		Connection con = DBUtil.getConnection();	
		

		String sql = "SELECT * FROM PRODUCT WHERE PROD_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Product product = null;
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));

		}
		rs.close();
		stmt.close();
		con.close();

		return product;
	}
	
	public Product findProduct2(int tranNo) throws Exception {
		
		
		Connection con = DBUtil.getConnection();	
		

		String sql = "SELECT pr.PROD_NO , pr.PROD_NAME, pr.PROD_DETAIL,pr.MANUFACTURE_DAY,pr.PRICE,pr.IMAGE_FILE,pr.REG_DATE "
				+ " from product pr, transaction tr"
				+ " where pr.prod_no = tr.prod_no AND tr.tran_no=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		Product product = null;
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));

		}
		rs.close();
		stmt.close();
		con.close();

		return product;
	}
	


	public Map<String, Object> getProductList(Search search) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM PRODUCT ";

		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) { // 0 : ��ǰ��ȣ�� ���
				sql += " WHERE PROD_NO LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) { // 1 : ��ǰ����
																											// ���
				sql += " WHERE PROD_NAME LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")) { // 1 : ��ǰ������
																											// ���
				sql += " WHERE PRICE LIKE '%" + search.getSearchKeyword() + "%'";
			}
		}
		sql += "ORDER BY prod_no";

		System.out.println("ProductDAO::Original SQL :: " + sql);

		// ==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);

		// ==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		System.out.println(search);

		List<Product> list = new ArrayList<Product>();

		while (rs.next()) {
			Product product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
			System.out.println("����Ʈ ����Ҷ� ���ڵ� ����" + product);
			list.add(product); // ����Ʈ�� �߰�
		}
		System.out.println("getProductList() :: list.size() : " + list.size());
		map.put("totalCount", new Integer(totalCount)); // (�Խñ� �� ������ count��)
		map.put("list", list); // list put
		System.out.println("getProductList() :: map().size() : " + map.size()); // count �� list �ΰ� ��

		con.close();

		return map; // count�� list�� ��� map ����
	}
	
	 public void updateProduct(Product product) throws Exception {
	

	 
	 
	Connection con = DBUtil.getConnection();	 
	 
	
	 String sql = "update PRODUCT set "
	 		+ "PROD_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=?, IMAGE_FILE=? "
	 		+ "where PROD_NO=?";
	
	 PreparedStatement stmt = con.prepareStatement(sql);
	 stmt.setString(1, product.getProdName());
	 stmt.setString(2, product.getProdDetail());
	 stmt.setString(3, product.getManuDate());
	 stmt.setInt(4, product.getPrice());
	 stmt.setString(5, product.getFileName());
	 stmt.setInt(6, product.getProdNo());
	 stmt.executeUpdate();
	
	 con.close();
	 }
	

	// �Խ��� Page ó���� ���� ��ü Row(totalCount) return
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

	// �Խ��� currentPage Row �� return
	private String makeCurrentPageSql(String sql, Search search) {
		sql = "SELECT * " + "FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " + " 	FROM (	" + sql
				+ " ) inner_table " + "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ "WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		System.out.println("UserDAO :: make SQL :: " + sql);

		return sql;
	}

}