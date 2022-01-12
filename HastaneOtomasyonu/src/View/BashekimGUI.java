package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import Model.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import Helper.*;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.JComboBox;

public class BashekimGUI extends JFrame {
	static Bashekim bashekim = new Bashekim();
	Clinic clinic = new Clinic();

	private JPanel w_Pane;
	private JTextField fld_dadsoyad;
	private JTextField fld_dtcno;
	private DefaultTableModel doctorModel = null;
	private Object[] doctorData = null;
	private JPasswordField fld_dPass;
	private JTable table_doctor;
	private JTextField fld_kullanýcýId;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JPopupMenu clinicMenu;
	private JTable table_clinic;
	private JTextField fld_clinicName;
	private JTextField fld_doctorID;
	private JTable table_worker;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BashekimGUI frame = new BashekimGUI(bashekim);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public BashekimGUI(Bashekim bashekim) throws SQLException {

		// Doctor Model
		doctorModel = new DefaultTableModel();

		Object[] colDoctorName = new Object[4];
		colDoctorName[0] = "ID";
		colDoctorName[1] = "Ad Soyad";
		colDoctorName[2] = "TC No";
		colDoctorName[3] = "Þifre";
		doctorModel.setColumnIdentifiers(colDoctorName);
		doctorData = new Object[4];
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);

		}

		// Clinic Model
		clinicModel = new DefaultTableModel();

		Object[] colClinicName = new Object[2];
		colDoctorName[0] = "ID";
		colDoctorName[1] = "Poliklinik Adý";

		clinicModel.setColumnIdentifiers(colClinicName);
		doctorData = new Object[2];

		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);

		}
		// WrokerModel
		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2];
		colWorker[0] = "ID";
		colWorker[1] = "Ad Soyad";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData = new Object[2];

		setResizable(false);
		setTitle("Hastane Y\u00F6netim sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_Pane = new JPanel();
		w_Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_Pane);
		w_Pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoþgeldiniz Sayýn " + bashekim.getName());
		lblNewLabel.setBounds(10, 46, 399, 32);
		w_Pane.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));

		JButton btnNewButton = new JButton("\u00C7\u0131k\u0131\u015F Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(591, 47, 101, 31);
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		w_Pane.add(btnNewButton);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 117, 716, 336);
		w_Pane.add(w_tab);

		JPanel w_clinic = new JPanel();
		w_clinic.setBackground(Color.WHITE);
		w_tab.addTab("Poliklinikler", null, w_clinic, null);
		w_clinic.setLayout(null);
		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setBounds(10, 11, 260, 286);
		w_clinic.add(w_scrollClinic);

		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Güncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);

		updateMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
				Clinic selectClinic = clinic.getFetch(selID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() { // (WindowListener) sildim bunu
					@Override
					public void windowClosed(WindowEvent e) {
						try {
							updateClinicModel();
						} catch (SQLException e1) {
							e1.printStackTrace();

						}

					}

				});

			}

		});
		deleteMenu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
					try {
						if (clinic.deleteClinic(selID)) {
							Helper.showMsg("success");
							updateClinicModel();
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}
			}
		});

		table_clinic = new JTable(clinicModel);
		table_clinic.setComponentPopupMenu(clinicMenu);
		table_clinic.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = table_clinic.rowAtPoint(point);
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);
			}

		});

		w_scrollClinic.setViewportView(table_clinic);
		JLabel lblPoliklinikAd = new JLabel("Poliklinik Ad\u0131 : ");
		lblPoliklinikAd.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblPoliklinikAd.setBounds(280, 11, 150, 24);
		w_clinic.add(lblPoliklinikAd);

		JTextField fld_clinicName = new JTextField(); // JTextField kelimesimi yeni ekledim
		fld_clinicName.setColumns(10);
		fld_clinicName.setBounds(280, 45, 150, 24);
		w_clinic.add(fld_clinicName);

		JButton btn_addClinic = new JButton("Ekle");
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_clinicName.getText().length() == 0) {
					Helper.showMsg("fill");

				} else {
					try {
						if (clinic.addClinic(fld_clinicName.getText())) {
							Helper.showMsg("success");
							fld_clinicName.setText(null);
							updateClinicModel();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();

					}

				}

			}
		});
		btn_addClinic.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		btn_addClinic.setBounds(280, 80, 150, 24);
		w_clinic.add(btn_addClinic);
		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(440, 11, 261, 286);
		w_clinic.add(w_scrollWorker);
		table_worker = new JTable();
		w_scrollWorker.setViewportView(table_worker);
		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(280, 229, 150, 33);
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			select_doctor.addItem(
					new Item(bashekim.getDoctorList().get(i).getId(), bashekim.getDoctorList().get(i).getName()));
		}
		select_doctor.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + " : " + item.getValue());

		});

		w_clinic.add(select_doctor);
		JButton btn_addWorker = new JButton("Ekle");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();
				if (selRow >= 0) {
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					Item doctorItem = (Item) select_doctor.getSelectedItem();

					try {
						boolean control = bashekim.addWorker(doctorItem.getKey(), selClinicID);
						if (control) {
							Helper.showMsg("success");
							DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
							clearModel.setRowCount(0);
							for (int i = 0; i < bashekim.getClinicDoctorList(selClinicID).size(); i++) {
								workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
								workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
								workerModel.addRow(workerData);
							}

							table_worker.setModel(workerModel);
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();

					}

				} else {
					Helper.showMsg("Lütfen bir poliklinik seçiniz !");
				}

			}
		});
		btn_addWorker.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));

		btn_addWorker.setBounds(280, 273, 150, 24);
		w_clinic.add(btn_addWorker);
		JLabel lblPoliklinikAd_1 = new JLabel("Poliklinik Ad\u0131 : ");
		lblPoliklinikAd_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblPoliklinikAd_1.setBounds(280, 115, 150, 24);
		w_clinic.add(lblPoliklinikAd_1);
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(280, 150, 150, 24);
		w_clinic.add(textField_1);
		JButton btn_workerSelect = new JButton("Se\u00E7");
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();
				if (selRow >= 0) {
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < bashekim.getClinicDoctorList(selClinicID).size(); i++) {
							workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
							workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
							workerModel.addRow(workerData);

						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table_worker.setModel(workerModel);
				} else {
					Helper.showMsg("Lütfen bir poliklinik seçiniz !");
				}
			}
		});
		btn_workerSelect.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		btn_workerSelect.setBounds(280, 185, 150, 24);
		w_clinic.add(btn_workerSelect);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		w_tab.addTab("Doktor Yönetimi", null, panel, null);
		panel.setLayout(null);

		JLabel lblAdSoyad = new JLabel("Ad Soyad");
		lblAdSoyad.setBounds(483, 0, 196, 24);
		panel.add(lblAdSoyad);
		lblAdSoyad.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));

		fld_dtcno = new JTextField();
		fld_dtcno.setBounds(483, 139, 196, 24);
		panel.add(fld_dtcno);
		fld_dtcno.setFont(new Font("Yu Gothic Light", Font.PLAIN, 10));
		fld_dtcno.setColumns(10);

		JLabel label_2 = new JLabel("Password");
		label_2.setBounds(483, 60, 86, 17);
		panel.add(label_2);
		label_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));

		fld_dPass = new JPasswordField();
		fld_dPass.setBounds(483, 87, 196, 24);
		panel.add(fld_dPass);

		JButton btn_addDoctor = new JButton("Ekle");
		btn_addDoctor.setBounds(483, 174, 196, 24);
		panel.add(btn_addDoctor);
		btn_addDoctor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (fld_dadsoyad.getText().length() == 0 || fld_dPass.getText().length() == 0
						|| fld_dtcno.getText().length() == 0) {

					Helper.showMsg("fill");

				} else {

					try {

						boolean control = bashekim.addDoctor(fld_dtcno.getText(), fld_dPass.getText(),
								fld_dadsoyad.getText());

						if (control) {

							Helper.showMsg("success");

							fld_dadsoyad.setText(null);
							fld_dtcno.setText(null);
							fld_dPass.setText(null);
							updateDoctorModel();

						}

					} catch (Exception e1) {

						e1.printStackTrace();

					}

				}

			}

		});

		btn_addDoctor.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		JLabel fld_kullanýcýID = new JLabel("Kullan\u0131c\u0131 ID");
		fld_kullanýcýID.setBounds(483, 201, 154, 24);
		panel.add(fld_kullanýcýID);
		fld_kullanýcýID.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		fld_dadsoyad = new JTextField();
		fld_dadsoyad.setBounds(483, 34, 196, 24);
		panel.add(fld_dadsoyad);
		fld_dadsoyad.setColumns(10);

		JButton btn_delDoctor = new JButton("Delete");
		btn_delDoctor.setBounds(483, 267, 196, 30);
		panel.add(btn_delDoctor);
		btn_delDoctor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (fld_doctorID.getText().length() == 0) {

					Helper.showMsg("Lütfen Geçerli Bir Doktor Seçiniz  !");

				} else {

					if (Helper.confirm("sure")) {

						int selectID = Integer.parseInt(fld_doctorID.getText());

						try {
							boolean control = bashekim.deleteDoctor(selectID);

							if (control) {

								Helper.showMsg("success");
								fld_doctorID.setText(null);
								updateDoctorModel();

							}

						} catch (Exception e2) {
							e2.printStackTrace();

						}

					}
				}
			}
		});

		btn_delDoctor.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 0, 465, 299);
		panel.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);

		fld_doctorID = new JTextField();
		fld_doctorID.setBounds(483, 232, 196, 24);
		panel.add(fld_doctorID);
		fld_doctorID.setColumns(10);
		JLabel lbl_tcno = new JLabel("T.C. No :");
		lbl_tcno.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lbl_tcno.setBounds(483, 114, 196, 14);
		panel.add(lbl_tcno);

		table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override

			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_doctorID.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
				} catch (Exception ex) {

				}
			}
		});
		table_doctor.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectID = Integer
							.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					String selectName = table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
					String selectTcno = table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
					String selectPass = table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();

					try {
						boolean control = bashekim.updateDoctor(selectID, selectTcno, selectPass, selectName);

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});
	}

	public void updateDoctorModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);

		}

	}

	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);

		}

	}
}