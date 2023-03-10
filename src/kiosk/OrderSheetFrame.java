package kiosk;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrderSheetFrame extends JFrame {

	private JPanel contentPane;
	private OrderInfoDAOImple orderInfoDao;
	private JTable table;
	private String[] colNames = { "상품명", "크기", "수량", "가격" };
	private Object[] records = new Object[colNames.length];
	private DefaultTableModel model;

	public static OrderSheetFrame instance;

	public static OrderSheetFrame getInstance() {
		if (instance == null) {
			instance = new OrderSheetFrame();
		}
		return instance;
	}

	/**
	 * Create the frame.
	 */
	private OrderSheetFrame() {
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 592);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		orderInfoDao = OrderInfoDAOImple.getInstance();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.count = 0;
			}
		});
		scrollPane.setBounds(47, 47, 337, 336);
		contentPane.add(scrollPane);

		JLabel lblPriceLabel = new JLabel("총 가격");
		lblPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblPriceLabel.setBounds(185, 393, 57, 15);
		contentPane.add(lblPriceLabel);

		JLabel lblTotalPrice = new JLabel("");
		lblTotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalPrice.setBounds(185, 434, 57, 15);
		contentPane.add(lblTotalPrice);
		setLocationRelativeTo(null);

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
		scrollPane.setViewportView(table);
		selectAllOrderSheetTable();
		int totalPrice = calculateTotalPrice();
		String totalP = String.valueOf(totalPrice);
		lblTotalPrice.setText(totalP);

		JButton btnPaymentCancel = new JButton("결제 취소");
		btnPaymentCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.count = 0;
				instance = null;
				dispose();
			}
		});
		btnPaymentCancel.setBounds(47, 459, 149, 46);
		contentPane.add(btnPaymentCancel);

		JButton btnProceedToPayment = new JButton("결제 진행");
		btnProceedToPayment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.count = 0;
				PaymentFrame paymentFrame = PaymentFrame.getInstance();
				paymentFrame.setVisible(true);
				paymentFrame.setResizable(false);

			}
		});
		btnProceedToPayment.setBounds(235, 459, 149, 46);
		contentPane.add(btnProceedToPayment);

		JLabel lblBasket = new JLabel("주문서");
		lblBasket.setHorizontalAlignment(SwingConstants.CENTER);
		lblBasket.setBounds(185, 10, 57, 15);
		contentPane.add(lblBasket);

	}

	private void selectAllOrderSheetTable() {

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
			records[3] = list.get(i).getProductPrice() + " (+" + sizeExtra + ")";
			model.addRow(records);
		}
	}

	private int calculateTotalPrice() {
		ArrayList<OrderInfoVO> list = orderInfoDao.select(Main.orderNum);
		int price = 0;
		int qty = 0;
		String size = "";
		int sizeExtra = 0;
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			size = list.get(i).getProductSize();
			if (size.equals("M")) {
				sizeExtra = 300;
			} else if (size.equals("L")) {
				sizeExtra = 500;
			}
			price = list.get(i).getProductPrice();
			qty = list.get(i).getProductQuantity();
			sum += ((price + sizeExtra) * qty);
		}
		return sum;
	}

}
