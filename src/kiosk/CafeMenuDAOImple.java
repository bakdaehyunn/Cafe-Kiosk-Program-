package kiosk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleDriver;

public class CafeMenuDAOImple implements CafeMenuDAO, OracleConnectionInfo, CafeMenuQuery {
	private static CafeMenuDAOImple instance;

	private CafeMenuDAOImple() {
	}

	public static CafeMenuDAOImple getInstance() {
		if (instance == null) {
			instance = new CafeMenuDAOImple();
		}
		return instance;
	}

	// CafeMenu테이블
	@Override
	public ArrayList<CafeMenuVO> selectMenu(int cid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CafeMenuVO> list = new ArrayList<>();

		try {
			DriverManager.registerDriver(new OracleDriver());
			System.out.println("드라이버 로드 성공");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("DB 연결 성공");

			pstmt = conn.prepareStatement(SQL_SELECT_MENU);
			pstmt.setInt(1, cid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String productName = rs.getString(1);
				int categoryId = rs.getInt(2);
				int productPrice = rs.getInt(3);
				String productPicture = rs.getString(4);
				String productDescription = rs.getString(5);
				list.add(new CafeMenuVO(productName, categoryId, productPrice, productPicture, productDescription));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return list;
	}

	@Override
	public CafeMenuVO selectProduct(int cid, int idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CafeMenuVO vo = null;
		try {
			DriverManager.registerDriver(new OracleDriver());
			System.out.println("드라이버 로드 성공");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("DB 연결 성공");
			pstmt = conn.prepareStatement(SQL_SELECT_PRODUCT);
			pstmt.setInt(1, cid);
			pstmt.setInt(2, idx);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String productName = rs.getString(2);
				int categoryId = rs.getInt(3);
				int productPrice = rs.getInt(4);
				String productPicture = rs.getString(5);
				String productDescription = rs.getString(6);
				vo = new CafeMenuVO(productName, categoryId, productPrice, productPicture, productDescription);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vo;
	}

	@Override
	public CafeMenuVO selectProductByName(String Pname) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CafeMenuVO vo = null;
		try {
			DriverManager.registerDriver(new OracleDriver());
			System.out.println("드라이버 로드 성공");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("DB 연결 성공");
			pstmt = conn.prepareStatement(SQL_SELECT_PRODUCT_BY_PNAME);
			pstmt.setString(1, Pname);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String productName = rs.getString(1);
				int categoryId = rs.getInt(2);
				int productPrice = rs.getInt(3);
				String productPicture = rs.getString(4);
				String productDescription = rs.getString(5);
				vo = new CafeMenuVO(productName, categoryId,  productPrice, productPicture,
						productDescription);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vo;
	}

}
