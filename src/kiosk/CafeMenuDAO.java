package kiosk;

import java.util.ArrayList;

public interface CafeMenuDAO {
	public abstract ArrayList<CafeMenuVO> selectMenu(int cid);

	public abstract CafeMenuVO selectProduct(int cid, int idx);
	
	public abstract CafeMenuVO selectProductByName(String Pname);
}
