package kiosk;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class AddOrderFrame extends JFrame {
	private static AddOrderFrame instance;
	private JPanel contentPane;
	private OrderInfoDAOImple orderInfoDao;

	private CafeMenuDAOImple cafeMenuDao;

	private AddOrderFrame(CafeMenuVO vo) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.count = 0;
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Main.count = 0;
				instance = null;
			}
		});
		cafeMenuDao = CafeMenuDAOImple.getInstance();

		orderInfoDao = OrderInfoDAOImple.getInstance();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		ImageIcon icon = toApplyImage(vo.getProductPicture());
		JLabel imgProduct = new JLabel(icon);
		imgProduct.setBounds(0, 55, 130, 130);
		contentPane.add(imgProduct);

		JLabel lblProductName = new JLabel("New label");
		lblProductName.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductName.setText(vo.getProductName());
		lblProductName.setBounds(0, 0, 434, 51);
		contentPane.add(lblProductName);

		JLabel lblProductDescription = new JLabel();
		lblProductDescription.setText(vo.getProductDescription());
		lblProductDescription.setBounds(142, 55, 292, 41);
		contentPane.add(lblProductDescription);

		SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 1, 100, 1);
		JSpinner spinner = new JSpinner(quantityModel);
		
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Main.count=0;
			}
		});
		
		if (Main.tableNV != null && Main.tableSV != null && Main.tableQV != 0) {
			spinner.setValue(Main.tableQV);
		}

		spinner.setBounds(142, 106, 148, 62);
		contentPane.add(spinner);

		if (vo.getCategoryId() != 3) {
			JButton btnNewButton = new JButton("S");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Main.count = 0;
					int qty = (int) spinner.getValue();
					// 수량 변
					int delResult = orderInfoDao.delete(Main.orderNum, vo.getProductName(), Main.tableSV);
					if (delResult == 1) {
						System.out.println("테이블의 데이터가 삭제 되었습니다.");
					}

					int s = orderInfoDao.checkOverlap(Main.orderNum, vo.getProductName(), "S");
					System.out.println(s);
					if (s == 0) {// 새 주문일 경우
						OrderInfoVO vo2 = new OrderInfoVO(Main.orderNum, vo.getProductName(), vo.getProductPrice(), qty,
								"S");
						int result = orderInfoDao.insert(vo2);
						if (result == 1) {
							Main.selectAllOrderTable();
							instance = null;
							dispose();
						}
					}

					else if (s > 0) {// 주문 수정일 경우
						int oq = orderInfoDao.checkQuantity(Main.orderNum, vo.getProductName(), "S");
						if (oq == 1) {
							System.out.println("수량 확인 : " + oq);
						}
						OrderInfoVO vo2 = new OrderInfoVO(Main.orderNum, vo.getProductName(), vo.getProductPrice(),
								qty + oq, "S");
						int result = orderInfoDao.update(vo2);
						if (result == 1) {
							System.out.println("업데이트 완료");
							Main.selectAllOrderTable();
							Main.tableNV = null;
							Main.tableSV = null;
							Main.tableQV = 0;
							instance = null;
							dispose();
						}
					}
				}
			});
			btnNewButton.setBounds(141, 189, 62, 62);
			contentPane.add(btnNewButton);

			JButton btnNewButton_1 = new JButton("<html><p>M</p><p>(+300)</p></html>");
			btnNewButton_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Main.count = 0;
					int qty = (int) spinner.getValue();
					int delResult = orderInfoDao.delete(Main.orderNum, vo.getProductName(), Main.tableSV);
					if (delResult == 1) {
						System.out.println("테이블의 데이터가 삭제 되었습니다.");
					}

					int s = orderInfoDao.checkOverlap(Main.orderNum, vo.getProductName(), "M");
					System.out.println(s);
					if (s == 0) {// 새 주문일 경우
						OrderInfoVO vo2 = new OrderInfoVO(Main.orderNum, vo.getProductName(), vo.getProductPrice(), qty,
								"M");
						int result = orderInfoDao.insert(vo2);
						if (result == 1) {
							Main.selectAllOrderTable();
							instance = null;
							dispose();
						}
					}

					else if (s > 0) {// 주문 수정일 경우
						int oq = orderInfoDao.checkQuantity(Main.orderNum, vo.getProductName(), "M");
						if (oq == 1) {
							System.out.println("수량 확인 : " + oq);
						}
						OrderInfoVO vo2 = new OrderInfoVO(Main.orderNum, vo.getProductName(), vo.getProductPrice(),
								qty + oq, "M");
						int result = orderInfoDao.update(vo2);
						if (result == 1) {
							System.out.println("업데이트 완료");
							Main.selectAllOrderTable();
							Main.tableNV = null;
							Main.tableSV = null;
							Main.tableQV = 0;
							instance = null;
							dispose();
						}
					}
				}
			});
			btnNewButton_1.setBounds(234, 189, 62, 62);
			contentPane.add(btnNewButton_1);

			JButton btnNewButton_2 = new JButton("<html><p>L</p><p>(+500)</p></html>");
			btnNewButton_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Main.count = 0;
					int qty = (int) spinner.getValue();
					int delResult = orderInfoDao.delete(Main.orderNum, vo.getProductName(), Main.tableSV);
					if (delResult == 1) {
						System.out.println("테이블의 데이터가 삭제 되었습니다.");
					}

					int s = orderInfoDao.checkOverlap(Main.orderNum, vo.getProductName(), "L");
					System.out.println(s);
					if (s == 0) {// 새 주문일 경우
						OrderInfoVO vo2 = new OrderInfoVO(Main.orderNum, vo.getProductName(), vo.getProductPrice(), qty,
								"L");
						int result = orderInfoDao.insert(vo2);
						if (result == 1) {
							Main.selectAllOrderTable();
							instance = null;
							dispose();
						}
					}

					else if (s > 0) {// 주문 수정일 경우
						int oq = orderInfoDao.checkQuantity(Main.orderNum, vo.getProductName(), "L");
						if (oq == 1) {
							System.out.println("수량 확인 : " + oq);
						}
						OrderInfoVO vo2 = new OrderInfoVO(Main.orderNum, vo.getProductName(), vo.getProductPrice(),
								qty + oq, "L");
						int result = orderInfoDao.update(vo2);
						if (result == 1) {
							System.out.println("업데이트 완료");
							Main.selectAllOrderTable();
							Main.tableNV = null;
							Main.tableSV = null;
							Main.tableQV = 0;
							instance = null;
							dispose();
						}
					}
				}
			});
			btnNewButton_2.setBounds(328, 189, 62, 62);
			contentPane.add(btnNewButton_2);
		} else {

			JButton btnNewButton_1_1 = new JButton("추가");
			btnNewButton_1_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Main.count = 0;
					int qty = (int) spinner.getValue();
					int delResult = orderInfoDao.delete(Main.orderNum, vo.getProductName(), Main.tableSV);
					if (delResult == 1) {
						System.out.println("테이블의 데이터가 삭제 되었습니다.");
					}

					int s = orderInfoDao.checkOverlap(Main.orderNum, vo.getProductName(), "기본");
					System.out.println(s);
					if (s == 0) {// 새 주문일 경우
						OrderInfoVO vo2 = new OrderInfoVO(Main.orderNum, vo.getProductName(), vo.getProductPrice(), qty,
								"기본");
						int result = orderInfoDao.insert(vo2);
						if (result == 1) {
							Main.selectAllOrderTable();
							instance = null;
							dispose();

						}
					}

					else if (s > 0) {// 주문 수정일 경우
						int oq = orderInfoDao.checkQuantity(Main.orderNum, vo.getProductName(), "기본");
						if (oq == 1) {
							System.out.println("수량 확인 : " + oq);
						}
						OrderInfoVO vo2 = new OrderInfoVO(Main.orderNum, vo.getProductName(), vo.getProductPrice(),
								qty + oq, "기본");
						int result = orderInfoDao.update(vo2);
						if (result == 1) {
							System.out.println("업데이트 완료");
							Main.selectAllOrderTable();
							Main.tableNV = null;
							Main.tableSV = null;
							Main.tableQV = 0;
							instance = null;
							dispose();
						}
					}
				}
			});
			btnNewButton_1_1.setBounds(328, 106, 62, 62);
			contentPane.add(btnNewButton_1_1);
		}
	}

	public static AddOrderFrame getInstance(CafeMenuVO vo) {
		if (instance == null) {
			instance = new AddOrderFrame(vo);
		}
		return instance;
	}

	private ImageIcon toApplyImage(String filename) {
		String relPath = "images/" + filename;
		ImageIcon icon = new ImageIcon(relPath);
		Image img = icon.getImage().getScaledInstance(143, 143, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(img);
		return changeIcon;
	}

}
