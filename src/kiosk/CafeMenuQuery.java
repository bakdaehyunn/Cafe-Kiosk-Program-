package kiosk;

public interface CafeMenuQuery {
	// CafeMenu테이블 정보
	public static final String TABLE_NAME = "CAFEMENU";
	public static final String COL_PRODUCT_NAME = "PRODUCT_NAME";
	public static final String COL_CATEGORY_ID = "CATEGORY_ID";
	public static final String COL_PRODUCT_PRICE = "PRODUCT_PRICE";
	public static final String COL_PRODUCT_PICTURE = "PRODUCT_PICTURE";
	public static final String COL_PRODUCT_DESCRIPTION = "PRODUCT_DESCRIPTION";

	// cafeMenu 테이블에 사용할 명령어
	public static final String SQL_SELECT_MENU = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_CATEGORY_ID + " = ?";

	public static final String SQL_SELECT_PRODUCT = "SELECT * FROM ( SELECT ROWNUM AS NUM , " + COL_PRODUCT_NAME + ", "
			+ COL_CATEGORY_ID + ", " + COL_PRODUCT_PRICE + ", " + COL_PRODUCT_PICTURE + ", " + COL_PRODUCT_DESCRIPTION
			+ " FROM " + TABLE_NAME + " WHERE " + COL_CATEGORY_ID + " = ? ) C WHERE C.NUM = ?";

	public static final String SQL_SELECT_PRODUCT_BY_PNAME = "SELECT * FROM " + TABLE_NAME + " WHERE "
			+ COL_PRODUCT_NAME + " = ? ";
}
