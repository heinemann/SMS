package ms.heinemann.kontact.sms;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import java.awt.SystemColor;

public class GUI {

	//public static final Document editorPane = null;
	final static JEditorPane editorPane = new JEditorPane();
	private static String Zeichen = ("Zeichen");
	static JLabel lblZeichen = new JLabel(Zeichen);
	private static String AnzahlSMS= ("Anzahl SMS");
	static JLabel lblAnzahlSms = new JLabel(AnzahlSMS);
	static JTextField textPaneAbsender = new JTextField();
	static JTextField textPaneEmpfänger = new JTextField();
	private JFrame frmSmsgatewayAnwendung;
	private static JTextField textFieldDatum;

	/**
	 * Launch the application.
	 */
	public static void GUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					window.frmSmsgatewayAnwendung.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSmsgatewayAnwendung = new JFrame();
		frmSmsgatewayAnwendung.setTitle("SMS-Gateway Anwendung");
		frmSmsgatewayAnwendung.getContentPane().setBackground(SystemColor.window);
		frmSmsgatewayAnwendung.setBounds(100, 100, 450, 300);
		frmSmsgatewayAnwendung.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSmsgatewayAnwendung.getContentPane().setLayout(null);
		
		JRadioButton rdbtnPremiumSms = new JRadioButton("Premium SMS");
		rdbtnPremiumSms.setBackground(SystemColor.window);
		rdbtnPremiumSms.setBounds(12, 178, 131, 26);
		frmSmsgatewayAnwendung.getContentPane().add(rdbtnPremiumSms);
		rdbtnPremiumSms.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
			      // Aufruf der das Programm beendet
				
				SMS.premium();
				GUI.textPaneAbsender.setVisible(true);
				GUI.textPaneAbsender.setText(SMS.getSender());
				GUI.textFieldDatum.setVisible(true);
				System.out.println("Premium SMS"+ SMS.getSMSType());
				
			}
		});
		
		JRadioButton rdbtnStandartSms = new JRadioButton("Standart SMS",true);
		rdbtnStandartSms.setBackground(SystemColor.window);
		rdbtnStandartSms.setBounds(12, 148, 131, 26);
		//rdbtnStandartSms.setFocusable(true);
		frmSmsgatewayAnwendung.getContentPane().add(rdbtnStandartSms);
		rdbtnStandartSms.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
			      // Aufruf der das Programm beendet
				
				SMS.standart();
				GUI.textPaneAbsender.setVisible(false);
				GUI.textFieldDatum.setVisible(false);
				System.out.println("Standart SMS" + SMS.getSMSType());
				
				
			}
		});
		
		//Create a ButtonGroup object, add buttons to the group
		ButtonGroup SMSAuswahlGruppe = new ButtonGroup();
		SMSAuswahlGruppe.add(rdbtnPremiumSms);
		SMSAuswahlGruppe.add(rdbtnStandartSms);
		
		
		//final JEditorPane editorPane = new JEditorPane();
		JScrollPane editorScrollPane = new JScrollPane(GUI.editorPane);
		//editorScrollPane.requestFocusInWindow();
		editorScrollPane.setBounds(164, 12, 232, 200);
		editorScrollPane.setPreferredSize(new Dimension(250, 140));
		editorScrollPane.setMinimumSize(new Dimension(10, 10));
		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		frmSmsgatewayAnwendung.getContentPane().add(editorScrollPane);
		//editorPane.getText(SMS.setSMSNachricht(sMSNachricht));
		TitledBorder titledSMSText = new TitledBorder("SMS-Nachricht");
		editorScrollPane.setBorder(titledSMSText);
		GUI.editorPane.getDocument().addDocumentListener(new GUISmsTextHandler());
		GUI.editorPane.requestFocusInWindow();
		
		
		
		//SMS.setSMSNachricht(editorPane.getText());
		
		
	
		
		
		textPaneEmpfänger.setBounds(12, 12, 131, 42);
		frmSmsgatewayAnwendung.getContentPane().add(textPaneEmpfänger);
		textPaneEmpfänger.setText(SMS.getReciever());
		TitledBorder titledEmpfänger = new TitledBorder("Empfänger");
		textPaneEmpfänger.setBorder(titledEmpfänger);
		textPaneEmpfänger.getDocument().addDocumentListener(new GUIEmpfängerHandler());
		
		
		textPaneAbsender.setBounds(12, 57, 131, 42);
		frmSmsgatewayAnwendung.getContentPane().add(textPaneAbsender);
		textPaneAbsender.setText(SMS.getSender());
		TitledBorder titledSender = new TitledBorder("Absender");
		textPaneAbsender.setBorder(titledSender);
		textPaneAbsender.setVisible(false);
		textPaneAbsender.getDocument().addDocumentListener(new GUISenderHandler());
		
		
		
		
		JButton btnSenden = new JButton("Senden");
		btnSenden.setBounds(22, 216, 116, 25);
		frmSmsgatewayAnwendung.getContentPane().add(btnSenden);
		btnSenden.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
			      // Aufruf der das Programm beendet
				System.out.println(SMS.getSMSNachricht());
				SMS.senden();
			}
		});
		
		
		
		lblZeichen.setBounds(174, 224, 105, 15);
		frmSmsgatewayAnwendung.getContentPane().add(lblZeichen);
		
		
		
		lblAnzahlSms.setBounds(291, 224, 140, 15);
		frmSmsgatewayAnwendung.getContentPane().add(lblAnzahlSms);
		
		textFieldDatum = new JTextField();
		textFieldDatum.setBounds(12, 104, 131, 42);
		frmSmsgatewayAnwendung.getContentPane().add(textFieldDatum);
		textFieldDatum.setColumns(10);
		TitledBorder titledDatum = new TitledBorder("Sendedatum");
		textFieldDatum.setBorder(titledDatum);
		textFieldDatum.setVisible(false);
		
		JMenuBar menuBar = new JMenuBar();
		frmSmsgatewayAnwendung.setJMenuBar(menuBar);
		
		JMenu mnSms = new JMenu("SMS");
		menuBar.add(mnSms);
		
		JMenuItem mntmSenden = new JMenuItem("Senden");
		mnSms.add(mntmSenden);
		
		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mnSms.add(mntmBeenden);
		mntmBeenden.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
			      // Aufruf der das Programm beendet
				System.exit(0);
			}
		});
		
		JMenu mnArchiv = new JMenu("Archiv");
		menuBar.add(mnArchiv);
		
		JMenu mnGateway = new JMenu("Gateway");
		menuBar.add(mnGateway);
		
		final JMenuItem mntmKonfiguration = new JMenuItem("Konfiguration");
		mnGateway.add(mntmKonfiguration);
		mntmKonfiguration.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				// Schreibt Konfiguration
				String s = null; 
				String benutzerString = (String) JOptionPane.showInputDialog( mntmKonfiguration, "Bitte den Benutzernamen eingeben:", s, JOptionPane.PLAIN_MESSAGE, null, null, s);
				String passwortString = (String) JOptionPane.showInputDialog( mntmKonfiguration, "Bitte das Gatewaypasswort eingeben:", s, JOptionPane.PLAIN_MESSAGE, null, null, s);
				System.out.println("Konfiguration" + benutzerString + passwortString);
				Configuration schreibeConfig = new Configuration();
				schreibeConfig.setBenutzer(benutzerString);
				schreibeConfig.setPasswort(passwortString);
				schreibeConfig.schreibeKonfiguration(benutzerString, passwortString);
				
			}
		});
		
		JMenu mnHilfe = new JMenu("Hilfe");
		menuBar.add(mnHilfe);
		
		JMenuItem mntmDokumentation = new JMenuItem("Dokumentation");
		mnHilfe.add(mntmDokumentation);
		
		JMenuItem mntmAktuallisierung = new JMenuItem("Aktuallisierung");
		mnHilfe.add(mntmAktuallisierung);
		
		JMenuItem mntmberDiesesProgramm = new JMenuItem("Über dieses Programm");
		mnHilfe.add(mntmberDiesesProgramm);
	}
}
