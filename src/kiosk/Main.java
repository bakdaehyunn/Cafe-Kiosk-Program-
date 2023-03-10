package kiosk;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class Main {

	private JFrame frame;
	// 메인메뉴화면
	public static JPanel Mainpanel;
	// 초기화면
	public static JLabel lblinitialscreen;

	// 카테고리 선택 버튼
	private JButton btnCoffee;
	private JButton btnBeverage;
	private JButton btnDessert;

	// 메뉴에 적용될 상품 사진, 상품 명 표시할 변수
	private JLabel[] imgProduct = new JLabel[6];
	private int imgWidth = 143;
	private int imgHeight = 143;
	private JLabel[] lblProduct = new JLabel[6];
	private JPanel[] panelMenu = new JPanel[6];

	// 장바구니 수량 변경, 개별 삭제, 전체 삭제 버튼
	private JButton btnReviseOrder;
	private JButton btnDeleteProduct;
	private JButton btnDeleteAll;

	// 싱글톤 패턴
	private CafeMenuDAOImple cafeMenuDao;
	private static OrderInfoDAOImple orderInfoDao;

	// 장바구니 테이블
	private JTable table;
	private static String[] colNames = { "상품명", "크기", "수량", "비고" };
	private static Object[] records = new Object[colNames.length];
	private static DefaultTableModel model;

	// 장바구니 테이블에 포함된 상품 클릭 시 해당 상품의 상품명, 사이즈, 수량 임시로 저장하는 변수
	public static String tableNV;
	public static String tableSV;
	public static int tableQV;

	// 카테고리 선택 시 임시로 카테고리 ID 정보를 가지고 있을 변수
	private int category;

	// 현재 주문번호를 가지고 있을 변수
	public static int orderNum;

	// 시간 제한 카운트 변수
	public static int count;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		cafeMenuDao = CafeMenuDAOImple.getInstance();
		orderInfoDao = OrderInfoDAOImple.getInstance();
		frame = new JFrame();

		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count = 0;
				System.out.println(count);
			}
		});
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = orderInfoDao.deleteAll(orderNum);
				if (result == 1) {
					System.out.println("테이블의 데이터가 초기화되었습니다.");
				}
			}
		});
		frame.setTitle("카페 키오스크");
		frame.setBounds(100, 100, 576, 681);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		// 초기화면에 출력할 이미지의 사이즈를 조정하여 적용
		int initWidth = 560;
		int initHeight = 642;
		lblinitialscreen = new JLabel(toApplyImage("initialscreen_text.jpg", initWidth, initHeight));
		lblinitialscreen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // 초기 화면 클릭 시 메인 주문 화면 변경되는 과정
				
				clearMenu();// 메뉴 칸을 비워 놓는
				selectAllOrderTable();
				orderNum = orderInfoDao.checkOrderNum() + 1; // DB에 저장된 마지막 주문번호 확인 후 현재 주문번호 설정
				category = 1; // 기본적으로 커피 카테고리 부터 출력되도록 커피 카테고리 설정
				applyCategoryMenu(1);// 커피 카테고리의 상품 정보 메뉴에 적용
				lblinitialscreen.setVisible(false); // 초기화면 안보이게 하기
				count = 0;
				Timer m_timer = new Timer();
				TimerTask m_task = new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (count < 20) {
							System.out.println(count);
							if ( count==14 ) {
								System.out.println("잠시후 프로그램이 종료 됩니다.");
							}
							count++;
						} else {
							m_timer.cancel();
							int result = orderInfoDao.deleteAll(orderNum);
							if (result == 1) {
								System.out.println("테이블의 데이터가 초기화되었습니다.");
							}
							PaymentFrame paymentFrame = PaymentFrame.getInstance();
							paymentFrame.dispose();
							OrderSheetFrame ordersheetFrame = OrderSheetFrame.getInstance();
							ordersheetFrame.dispose();
							AddOrderFrame addOrderFrame = AddOrderFrame.getInstance(new CafeMenuVO("",0,0,"",""));
							addOrderFrame.dispose();
							Mainpanel.setVisible(false);
							lblinitialscreen.setVisible(true);
						}
					}
				};
				m_timer.schedule(m_task, 1000, 1000);
				Mainpanel.setVisible(true); // 메인 주문화면 보이게 하기
				btnCoffee.setEnabled(false); // 커피 카테고리 상품이 적용되었음을 표시하기 위해 커피 카테고리 선택버튼 비활성화

			}
		});

		lblinitialscreen.setBounds(0, 0, 560, 642);
		frame.getContentPane().add(lblinitialscreen);

		Mainpanel = new JPanel();
		Mainpanel.setBackground(new Color(205, 133, 63));
		Mainpanel.setBounds(0, 0, 560, 642);
		Mainpanel.setVisible(false);
		frame.getContentPane().add(Mainpanel);
		Mainpanel.setLayout(null);

		JPanel categoryPanel = new JPanel();
		categoryPanel.setBackground(new Color(192, 192, 192));
		categoryPanel.setBounds(0, 0, 560, 43);
		Mainpanel.add(categoryPanel);
		categoryPanel.setLayout(null);

		// 커피 카테고리
		btnCoffee = new JButton("커피");
		btnCoffee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count = 0;
				category = 1;
				applyCategoryMenu(category);
				btnCoffee.setEnabled(false);
				btnBeverage.setEnabled(true);
				btnDessert.setEnabled(true);
			}
		});
		btnCoffee.setBounds(29, 5, 156, 32);
		categoryPanel.add(btnCoffee);

		// 차 에이드 카테고리
		btnBeverage = new JButton("차 / 에이드");
		btnBeverage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// 버튼 클릭 시
				count = 0;
				category = 2;
				applyCategoryMenu(category);// 차 에이드 카테고리 상품을 메뉴에 적용한다.
				btnCoffee.setEnabled(true); // 차 에이드 카테고리 상품이 적용되었음을 표시하기 위해 버튼 비성활성화 , 다른 버튼 활성화
				btnBeverage.setEnabled(false);
				btnDessert.setEnabled(true);
			}
		});
		btnBeverage.setBounds(200, 5, 156, 32);
		categoryPanel.add(btnBeverage);

		btnDessert = new JButton("디저트");
		btnDessert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count = 0;
				category = 3;
				applyCategoryMenu(category);
				btnCoffee.setEnabled(true);
				btnBeverage.setEnabled(true);
				btnDessert.setEnabled(false);
			}
		});
		btnDessert.setBounds(377, 5, 156, 32);
		categoryPanel.add(btnDessert);

		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(new Color(188, 143, 143));
		menuPanel.setBounds(12, 47, 341, 585);
		Mainpanel.add(menuPanel);
		menuPanel.setLayout(null);

		panelMenu[0] = new JPanel();
		panelMenu[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count = 0;
				tableNV = null;
				tableSV = null;
				tableQV = 0;
				CafeMenuVO vo = cafeMenuDao.selectProduct(category, 1);
				AddOrderFrame addorderFrame = AddOrderFrame.getInstance(vo);

				addorderFrame.setVisible(true);
				addorderFrame.setResizable(false);
			}
		});
		panelMenu[0].setBounds(12, 10, 143, 181);
		menuPanel.add(panelMenu[0]);
		panelMenu[0].setLayout(null);

		imgProduct[0] = new JLabel();
		imgProduct[0].setBounds(0, 0, 143, 143);
		panelMenu[0].add(imgProduct[0]);

		lblProduct[0] = new JLabel("New label");
		lblProduct[0].setHorizontalAlignment(SwingConstants.CENTER);
		lblProduct[0].setBounds(0, 142, 143, 39);
		panelMenu[0].add(lblProduct[0]);

		panelMenu[1] = new JPanel();
		panelMenu[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count = 0;
				tableNV = null;
				tableSV = null;
				tableQV = 0;
				CafeMenuVO vo = cafeMenuDao.selectProduct(category, 2);
				AddOrderFrame addorderFrame = AddOrderFrame.getInstance(vo);
				addorderFrame.setVisible(true);
				addorderFrame.setResizable(false);
			}
		});
		panelMenu[1].setBounds(186, 10, 143, 181);
		menuPanel.add(panelMenu[1]);
		panelMenu[1].setLayout(null);

		imgProduct[1] = new JLabel();
		imgProduct[1].setBounds(0, 0, 143, 143);
		panelMenu[1].add(imgProduct[1]);

		lblProduct[1] = new JLabel("New label");
		lblProduct[1].setHorizontalAlignment(SwingConstants.CENTER);
		lblProduct[1].setBounds(0, 142, 143, 39);
		panelMenu[1].add(lblProduct[1]);

		panelMenu[2] = new JPanel();
		panelMenu[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count = 0;
				tableNV = null;
				tableSV = null;
				tableQV = 0;
				CafeMenuVO vo = cafeMenuDao.selectProduct(category, 3);
				AddOrderFrame addorderFrame = AddOrderFrame.getInstance(vo);
				addorderFrame.setVisible(true);
				addorderFrame.setResizable(false);
			}
		});
		panelMenu[2].setBounds(12, 201, 143, 181);
		menuPanel.add(panelMenu[2]);
		panelMenu[2].setLayout(null);

		imgProduct[2] = new JLabel();
		imgProduct[2].setBounds(0, 0, 143, 143);
		panelMenu[2].add(imgProduct[2]);

		lblProduct[2] = new JLabel("New label");
		lblProduct[2].setHorizontalAlignment(SwingConstants.CENTER);
		lblProduct[2].setBounds(0, 142, 143, 39);
		panelMenu[2].add(lblProduct[2]);

		panelMenu[3] = new JPanel();
		panelMenu[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count = 0;
				tableNV = null;
				tableSV = null;
				tableQV = 0;
				CafeMenuVO vo = cafeMenuDao.selectProduct(category, 4);
				AddOrderFrame addorderFrame = AddOrderFrame.getInstance(vo);
				addorderFrame.setVisible(true);
				addorderFrame.setResizable(false);
			}
		});
		panelMenu[3].setBounds(186, 201, 143, 181);
		menuPanel.add(panelMenu[3]);
		panelMenu[3].setLayout(null);

		imgProduct[3] = new JLabel();
		imgProduct[3].setBounds(0, 0, 143, 143);
		panelMenu[3].add(imgProduct[3]);

		lblProduct[3] = new JLabel("New label");
		lblProduct[3].setHorizontalAlignment(SwingConstants.CENTER);
		lblProduct[3].setBounds(0, 142, 143, 39);
		panelMenu[3].add(lblProduct[3]);

		panelMenu[4] = new JPanel();
		panelMenu[4].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count = 0;
				tableNV = null;
				tableSV = null;
				tableQV = 0;
				CafeMenuVO vo = cafeMenuDao.selectProduct(category, 5);
				AddOrderFrame addorderFrame = AddOrderFrame.getInstance(vo);
				addorderFrame.setVisible(true);
				addorderFrame.setResizable(false);
			}
		});
		panelMenu[4].setBounds(12, 394, 143, 181);
		menuPanel.add(panelMenu[4]);
		panelMenu[4].setLayout(null);

		imgProduct[4] = new JLabel();
		imgProduct[4].setBounds(0, 0, 143, 143);
		panelMenu[4].add(imgProduct[4]);

		lblProduct[4] = new JLabel("New label");
		lblProduct[4].setHorizontalAlignment(SwingConstants.CENTER);
		lblProduct[4].setBounds(0, 142, 143, 39);
		panelMenu[4].add(lblProduct[4]);

		panelMenu[5] = new JPanel();
		panelMenu[5].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count = 0;
				tableNV = null;
				tableSV = null;
				tableQV = 0;
				CafeMenuVO vo = cafeMenuDao.selectProduct(category, 6);
				AddOrderFrame addorderFrame = AddOrderFrame.getInstance(vo);
				addorderFrame.setVisible(true);
				addorderFrame.setResizable(false);
			}
		});
		panelMenu[5].setBounds(186, 394, 143, 181);
		menuPanel.add(panelMenu[5]);
		panelMenu[5].setLayout(null);

		imgProduct[5] = new JLabel();
		imgProduct[5].setBounds(0, 0, 143, 143);
		panelMenu[5].add(imgProduct[5]);

		lblProduct[5] = new JLabel("New label");
		lblProduct[5].setHorizontalAlignment(SwingConstants.CENTER);
		lblProduct[5].setBounds(0, 142, 143, 39);
		panelMenu[5].add(lblProduct[5]);

		JButton btnOrder = new JButton("<html><p>주문하기</p><p>(카드결제)</p></html>");
		btnOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count = 0;
				OrderSheetFrame paymentFrame = OrderSheetFrame.getInstance();
				paymentFrame.setVisible(true);
				paymentFrame.setResizable(false);
			}
		});
		btnOrder.setFont(new Font("굴림", Font.PLAIN, 23));
		btnOrder.setBounds(375, 432, 178, 200);
		Mainpanel.add(btnOrder);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count=0;
			}
		});
		scrollPane.setBounds(375, 47, 173, 314);
		Mainpanel.add(scrollPane);

		model = new DefaultTableModel(colNames, 0) {
			public boolean isCellEditable(int row, int column) {
				if (column >= 0) {
					return false;
				} else {
					return true;
				}
			}

		};

		table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count = 0;
				int row = table.getSelectedRow();
				Object Nvalue = table.getValueAt(row, 0);
				Object Svalue = table.getValueAt(row, 1);
				Object Qvalue = table.getValueAt(row, 2);
				tableNV = String.valueOf(Nvalue);
				tableSV = String.valueOf(Svalue);
				tableQV = (int) Qvalue;
				System.out.println(tableNV);
				System.out.println(tableSV);
				System.out.println(tableQV);
			}
		});
		scrollPane.setViewportView(table);

		btnReviseOrder = new JButton("<html><p>수</p><p>정</p></html>");
		btnReviseOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count = 0;
				if (tableNV == null) {
					System.out.println("항목을 클릭해주세요.");
				} else {
					CafeMenuVO vo3 = cafeMenuDao.selectProductByName(tableNV);
					AddOrderFrame addOrderFrame = AddOrderFrame.getInstance(vo3);
					addOrderFrame.setVisible(true);
					addOrderFrame.setResizable(false);
				}
			}
		});
		btnReviseOrder.setBounds(375, 371, 51, 51);
		Mainpanel.add(btnReviseOrder);

		btnDeleteProduct = new JButton("<html><p>삭</p><p>제</p></html>");
		btnDeleteProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				count = 0;
				int result = orderInfoDao.delete(orderNum, tableNV, tableSV);
				selectAllOrderTable();
				if (result == 1) {
					System.out.println("해당 상품이 삭제 되었습니다.");
					tableNV = null;
					tableSV = null;
					tableQV = 0;
				}

			}
		});
		btnDeleteProduct.setBounds(438, 371, 51, 51);
		Mainpanel.add(btnDeleteProduct);

		btnDeleteAll = new JButton("<html><p>초</p><p>기</p><p>화</p></html>");
		btnDeleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count = 0;
				int result = orderInfoDao.deleteAll(orderNum);
				selectAllOrderTable();
				if (result == 1) {
					System.out.println("테이블의 데이터가 초기화되었습니다.");
					tableNV = null;
					tableSV = null;
					tableQV = 0;
				}
			}
		});
		btnDeleteAll.setBounds(497, 371, 51, 51);
		Mainpanel.add(btnDeleteAll);

	}

	private void applyCategoryMenu(int cid) {
		clearMenu();
		ArrayList<CafeMenuVO> list = cafeMenuDao.selectMenu(cid);
		for (int i = 0; i < list.size(); i++) {
			ImageIcon icon = toApplyImage(list.get(i).getProductPicture(), imgWidth, imgHeight);
			String productName = list.get(i).getProductName();
			int productPrice = list.get(i).getProductPrice();
			imgProduct[i].setIcon(icon);
			lblProduct[i].setText("<html><p>" + productName + "</p><p>" + productPrice + "원</p></html>");
			panelMenu[i].setVisible(true);
		}
	}

	public static ImageIcon toApplyImage(String filename, int width, int height) {
		String relPath = "images/" + filename;
		ImageIcon icon = new ImageIcon(relPath);
		Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(img);
		return changeIcon;
	}

	private void clearMenu() {
		for (int i = 0; i < panelMenu.length; i++) {
			panelMenu[i].setVisible(false);
		}
	}

	public static void selectAllOrderTable() {
		ArrayList<OrderInfoVO> list = orderInfoDao.select(Main.orderNum);
		model.setNumRows(0);
		String size = "";
		int sizeExtra = 0;
		for (int i = 0; i < list.size(); i++) {
			size = list.get(i).getProductSize();
			if (size.equals("M")) {
				sizeExtra = 300;
			} else if (size.equals("L")) {
				sizeExtra = 500;
			}
			records[0] = list.get(i).getProductName();
			records[1] = list.get(i).getProductSize();
			records[2] = list.get(i).getProductQuantity();
			records[3] = " (+" + sizeExtra + ")";
			model.addRow(records);
		}
	}
}
