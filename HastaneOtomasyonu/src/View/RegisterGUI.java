package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Hasta;
import Model.User;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField textField;
	private JTextField fld_tcno1;
	private JPasswordField passwordField;
	private Hasta hasta = new Hasta();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setResizable(false);
		setForeground(Color.WHITE);
		setTitle("Hastane Y\u00F6netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 340);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setForeground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel fld_name = new JLabel("Ad Soyad");
		fld_name.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		fld_name.setBounds(10, 11, 284, 24);
		w_pane.add(fld_name);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(10, 45, 284, 24);
		w_pane.add(textField);
		
		JLabel fld_tcno = new JLabel("T.C. Numaras\u0131");
		fld_tcno.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		fld_tcno.setBounds(10, 80, 284, 24);
		w_pane.add(fld_tcno);
		
		fld_tcno1 = new JTextField();
		fld_tcno1.setColumns(10);
		fld_tcno1.setBounds(10, 115, 284, 24);
		w_pane.add(fld_tcno1);
		
		JLabel fld_pass = new JLabel("\u015Eifre");
		fld_pass.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		fld_pass.setBounds(10, 156, 284, 24);
		w_pane.add(fld_pass);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 191, 284, 24);
		w_pane.add(passwordField);
		
		JButton btn_register = new JButton("Kay\u0131t Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(fld_tcno1.getText().length() == 0 || fld_pass.getText().length() == 0 || fld_name.getText().length() == 0) {
					Helper.showMsg("fill");
				}else {
					try {
						boolean control = hasta.register(fld_tcno1.getText(), fld_pass.getText(),fld_name.getText());
						if(control) {
							Helper.showMsg("success");
							LoginGUI login = new LoginGUI();
							login.setVisible(true);
							dispose();
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btn_register.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		btn_register.setBounds(10, 226, 284, 30);
		w_pane.add(btn_register);
		
		JButton btn_backto = new JButton("Geri D\u00F6n");
		btn_backto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
				
			}
		});
		btn_backto.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		btn_backto.setBounds(10, 262, 284, 30);
		w_pane.add(btn_backto);
	}
}
