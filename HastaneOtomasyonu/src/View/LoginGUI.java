package View;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;

public class LoginGUI extends JFrame {

	private JPanel w_Pane;
	private JTextField fld_hastaTc;
	private JTextField txtHastaneYnetimSistemine;
	private JTextField fld_doctorTc;
	private JPasswordField fld_doctorPass;
	private DBConnection conn = new DBConnection();
	private JPasswordField fld_hastaPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		
		JLabel lblNewLabel = new JLabel();
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		setResizable(false);
		setTitle("Hastane Y\u00F6netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_Pane = new JPanel();
		w_Pane.setBackground(Color.WHITE);
		w_Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_Pane);
		w_Pane.setLayout(null);
		
		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("photo.png")));
		lbl_logo.setBounds(134, 10, 199, 86);
		w_Pane.add(lbl_logo);
		
		JTabbedPane w_tabPane = new JTabbedPane(JTabbedPane.TOP);
		w_tabPane.setBounds(10, 143, 452, 210);
		w_Pane.add(w_tabPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		w_tabPane.addTab("Hasta Giriþi", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("T.C. Numaran\u0131z:");
		lblNewLabel_1.setBounds(40, 16, 140, 34);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		
		JLabel lblNewLabel_1_1 = new JLabel("\u015Eifre:");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(40, 65, 146, 34);
		panel.add(lblNewLabel_1_1);
		
		JButton btn_hastakayýtLogin = new JButton("Kay\u0131t Ol");
		btn_hastakayýtLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
				
			}
		});
		btn_hastakayýtLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_hastakayýtLogin.setBounds(40, 118, 166, 42);
		panel.add(btn_hastakayýtLogin);
		
		JButton btn_hastaLogin = new JButton("Giri\u015F Yap");
		btn_hastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(fld_hastaTc.getText().length() == 0 || fld_hastaPass.getText().length() == 0) {
					Helper.showMsg("fill");
				}else {
					boolean key = true;
					try {
						Connection con = conn.connDB();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						
						while(rs.next()) {
							if(fld_hastaTc.getText().equals(rs.getString("tcno")) && fld_hastaPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("hasta")) {

									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setPassword("password");
									hasta.setTcno(rs.getString("tcno"));
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									HastaGUI hGUI = new HastaGUI(hasta);
									hGUI.setVisible(true);
									dispose();			
									key = false;
								}							
							}
						}
					} catch (SQLException e1) {
					
						e1.printStackTrace();
					}
					if( key ) {
						Helper.showMsg("Böyle bir hasta bulunamadý lütfen kayýt olunuz !");
					}
				}			
			}
		});
		btn_hastaLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_hastaLogin.setBounds(223, 118, 173, 42);
		panel.add(btn_hastaLogin);
		
		fld_hastaTc = new JTextField();
		fld_hastaTc.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 18));
		fld_hastaTc.setColumns(10);
		fld_hastaTc.setBounds(188, 19, 249, 31);
		panel.add(fld_hastaTc);
		
		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setBounds(188, 71, 249, 31);
		panel.add(fld_hastaPass);
		
		JPanel w_doktorLogin = new JPanel();
		w_doktorLogin.setBackground(Color.WHITE);
		w_tabPane.addTab("Personel Giriþi", null, w_doktorLogin, null);
		w_doktorLogin.setLayout(null);
		
		fld_doctorTc = new JTextField();
		fld_doctorTc.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 18));
		fld_doctorTc.setColumns(10);
		fld_doctorTc.setBounds(172, 32, 249, 26);
		w_doktorLogin.add(fld_doctorTc);
		
		JButton btn_personelLogin = new JButton("Giri\u015F Yap");
		btn_personelLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_doctorTc.getText().length() == 0 || fld_doctorPass.getText().length() == 0) {
					Helper.showMsg("fill");
					
				}else {
					
					try {
						Connection con = conn.connDB();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(fld_doctorTc.getText().equals(rs.getString("tcno")) && fld_doctorPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("bashekim")) {

									Bashekim bHekim = new Bashekim();
									bHekim.setId(rs.getInt("id"));
									bHekim.setPassword("password");
									bHekim.setTcno(rs.getString("tcno"));
									bHekim.setName(rs.getString("name"));
									bHekim.setType(rs.getString("type"));
									BashekimGUI bGUI = new BashekimGUI(bHekim);
									bGUI.setVisible(true);
									dispose();
									
								}
								if(rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword("password");
									doctor.setTcno(rs.getString("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								}
							}
							

						}
					} catch (SQLException e1) {
					
						e1.printStackTrace();
					}
				}
		}
		});
		btn_personelLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_personelLogin.setBounds(81, 131, 257, 42);
		w_doktorLogin.add(btn_personelLogin);
		
		JLabel label = new JLabel("T.C Numaran\u0131z:");
		label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		label.setBounds(42, 32, 137, 26);
		w_doktorLogin.add(label);
		
		JLabel label_1 = new JLabel("\u015Eifre:");
		label_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		label_1.setBounds(42, 71, 137, 26);
		w_doktorLogin.add(label_1);
		
		fld_doctorPass = new JPasswordField();
		fld_doctorPass.setBounds(172, 75, 249, 26);
		w_doktorLogin.add(fld_doctorPass);
		
		txtHastaneYnetimSistemine = new JTextField();
		txtHastaneYnetimSistemine.setText("Hastane Y\u00F6netim Sistemine Ho\u015Fgeldiniz");
		txtHastaneYnetimSistemine.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 21));
		txtHastaneYnetimSistemine.setColumns(10);
		txtHastaneYnetimSistemine.setBounds(42, 100, 417, 38);
		w_Pane.add(txtHastaneYnetimSistemine);
	
	
	}
	}