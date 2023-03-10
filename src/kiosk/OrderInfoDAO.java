package kiosk;

import java.util.ArrayList;

public interface OrderInfoDAO {

	public abstract int insert(OrderInfoVO vo);

	public abstract ArrayList<OrderInfoVO> selectAll();

	public abstract ArrayList<OrderInfoVO> select(int orderNum);

	public abstract int update(OrderInfoVO vo);

	public abstract int delete(int orderNum,String name, String size);

	public abstract int deleteAll(int orderNum);

	public abstract int checkOverlap(int orderNum, String pName, String pSize);
	
	public abstract int checkQuantity(int orderNum, String pName, String pSize);

	public abstract int checkOrderNum();
}
