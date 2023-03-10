package kiosk;

public interface OrderInfoQuery {
	// OrderInfo테이블 정보
	public static final String TABLE_NAME = "ORDERINFO";
	public static final String COL_ORDER_ID = "ORDER_ID ";
	public static final String COL_PRODUCT_NAME = "PRODUCT_NAME";
	public static final String COL_PRODUCT_PRICE = "PRODUCT_PRICE";
	public static final String COL_PRODUCT_QUANTITY = "PRODUCT_QUANTITY";
	public static final String COL_PRODUCT_SIZE = "PRODUCT_SIZE";

	// OrderInfo 테이블에 사용할 명령어
	public static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " VALUES(?,?,?,?,?)";
	public static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + COL_PRODUCT_QUANTITY + " = ? WHERE "
			+ COL_ORDER_ID + " = ? AND " + COL_PRODUCT_NAME + " = ? AND " + COL_PRODUCT_SIZE + " = ? ";
	public static final String SQL_SELECT = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ORDER_ID + " = ?";
	public static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
	public static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ORDER_ID + " = ? AND "
			+ COL_PRODUCT_NAME + " = ? AND " + COL_PRODUCT_SIZE + " = ? ";
	public static final String SQL_DELETE_ALL = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ORDER_ID + " = ? ";
	public static final String SQL_CHECK_OVERLAP = "SELECT COUNT(*)" + " FROM " + TABLE_NAME + " WHERE " + COL_ORDER_ID
			+ " = ? AND " + COL_PRODUCT_NAME + " = ? AND " + COL_PRODUCT_SIZE + " = ? ";
	public static final String SQL_CHECK_QUANTITY = "SELECT " + COL_PRODUCT_QUANTITY + " FROM " + TABLE_NAME + " WHERE "
			+ COL_ORDER_ID + " = ? AND " + COL_PRODUCT_NAME + " = ? AND " + COL_PRODUCT_SIZE + " = ? ";
	public static final String SQL_CHECK_ORDERNUMBER = "SELECT " + COL_ORDER_ID + " FROM ( SELECT " + COL_ORDER_ID
			+ " FROM " + TABLE_NAME + " ORDER BY ROWNUM DESC) WHERE ROWNUM = 1";
}
