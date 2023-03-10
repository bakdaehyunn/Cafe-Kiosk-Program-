package kiosk;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

public class PaymentFrame extends JFrame {

	private JPanel contentPane;
	private JLabel lblPaymentGuide;
	private JLabel lblPaymentStatus;
	private JLabel ImgCardInsertion;
	private int imgWidth = 181;
	private int imgHeight = 119;

	private static PaymentFrame instance;

	public static PaymentFrame getInstance() {
		if (instance == null) {
			instance = new PaymentFrame();
		}
		return instance;
	}

	private PaymentFrame() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.count=0;
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.count=0;
				lblPaymentStatus.setText("결제 진행 중");
				lblPaymentGuide.setText("결제가 완료될 때까지 카드를 빼지 마세요.");
				Timer timer = new Timer(1000, e2 -> {
					lblPaymentStatus.setText("결제 완료");
					lblPaymentGuide.setText("카드를 빼주세요.");
					Timer timer2 = new Timer(3000, e3 -> {
						lblPaymentStatus.setText("주문 완료");
						lblPaymentGuide.setText("주문 번호: " + Main.orderNum);
						ImgCardInsertion.setIcon(Main.toApplyImage("thankyou.png", imgWidth, imgHeight));
						Timer timer3 = new Timer(3000, e4 -> {
							Main.orderNum++;
							Main.selectAllOrderTable();
							OrderSheetFrame ordersheetFrame = OrderSheetFrame.getInstance();
							ordersheetFrame.dispose();
							OrderSheetFrame.instance = null;
							Main.Mainpanel.setVisible(false);
							Main.lblinitialscreen.setVisible(true);

							instance = null;
							dispose();
						});
						timer3.setRepeats(false);
						timer3.start();
					});
					timer2.setRepeats(false);
					timer2.start();
				});
				timer.setRepeats(false);
				timer.start();
			}

		});

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		ImgCardInsertion = new JLabel(Main.toApplyImage("atm.png", imgWidth, imgHeight));
		ImgCardInsertion.setHorizontalAlignment(SwingConstants.CENTER);
		ImgCardInsertion.setBounds(126, 54, 181, 119);
		contentPane.add(ImgCardInsertion);

		lblPaymentStatus = new JLabel("결제 창");
		lblPaymentStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblPaymentStatus.setBounds(160, 10, 110, 28);
		contentPane.add(lblPaymentStatus);

		lblPaymentGuide = new JLabel("카드를 투입해 주십시오.");
		lblPaymentGuide.setHorizontalAlignment(SwingConstants.CENTER);
		lblPaymentGuide.setBounds(74, 200, 280, 37);
		contentPane.add(lblPaymentGuide);
	}

}
