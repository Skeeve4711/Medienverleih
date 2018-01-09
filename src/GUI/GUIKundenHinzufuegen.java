package GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.print.attribute.AttributeSet;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.sun.xml.internal.txw2.Document;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUIKundenHinzufuegen implements WindowListener{

	private JFrame frame;
	private JTextField textFieldVorname;
	private JTextField textFieldNachname;
	private JTextField textFieldEmail;
	private JTextField textFieldAlter;
	private JTextField textFieldPLZ;
	private JTextField textFieldOrt;
	private JTextField textFieldStrasse;
	private JPasswordField passwordField;
	private JTextField textFieldHausnummer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIKundenHinzufuegen window = new GUIKundenHinzufuegen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIKundenHinzufuegen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Kunden Hinzufügen");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-800)/2, (screenSize.height-400)/2, 800, 400);
		frame.getContentPane().setLayout(null);
		
		// Fertig stellen Button um Kunden anzulegen
		JButton btnFertigstellen = new JButton("Fertig stellen");
		btnFertigstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowClosing(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING)); // TODO Kunden hinzufuegen
			}
		});
		btnFertigstellen.setBounds(300, 285, 226, 35);
		frame.getContentPane().add(btnFertigstellen);
		
		textFieldVorname = new JTextField();
		textFieldVorname.setBounds(136, 29, 200, 25);
		frame.getContentPane().add(textFieldVorname);
		textFieldVorname.setColumns(10);
		
		JLabel lblName = new JLabel("Vorname");
		lblName.setBounds(31, 31, 70, 15);
		frame.getContentPane().add(lblName);
		
		textFieldNachname = new JTextField();
		textFieldNachname.setColumns(10);
		textFieldNachname.setBounds(136, 76, 200, 25);
		frame.getContentPane().add(textFieldNachname);
		
		JLabel lblNachname = new JLabel("Nachname");
		lblNachname.setBounds(31, 78, 81, 15);
		frame.getContentPane().add(lblNachname);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(136, 123, 200, 25);
		frame.getContentPane().add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setBounds(31, 125, 81, 15);
		frame.getContentPane().add(lblEmail);
		
		textFieldAlter = new JTextField();
		textFieldAlter.setColumns(10);
		textFieldAlter.setBounds(136, 170, 200, 25);
		frame.getContentPane().add(textFieldAlter);
		
		JLabel lblAlter = new JLabel("Alter");
		lblAlter.setBounds(31, 172, 81, 15);
		frame.getContentPane().add(lblAlter);
		
		JLabel lblPostleitzahl = new JLabel("Postleitzahl");
		lblPostleitzahl.setBounds(405, 34, 91, 15);
		frame.getContentPane().add(lblPostleitzahl);
		
		textFieldPLZ = new JTextField();
		textFieldPLZ.setColumns(10);
		textFieldPLZ.setBounds(552, 29, 200, 25);
		frame.getContentPane().add(textFieldPLZ);
		
		JLabel lblOrt = new JLabel("Ort");
		lblOrt.setBounds(405, 81, 91, 15);
		frame.getContentPane().add(lblOrt);
		
		textFieldOrt = new JTextField();
		textFieldOrt.setColumns(10);
		textFieldOrt.setBounds(552, 76, 200, 25);
		frame.getContentPane().add(textFieldOrt);
		
		textFieldStrasse = new JTextField();
		textFieldStrasse.setColumns(10);
		textFieldStrasse.setBounds(552, 123, 200, 25);
		frame.getContentPane().add(textFieldStrasse);
		
		JLabel lblStrae = new JLabel("Straße");
		lblStrae.setBounds(405, 128, 91, 15);
		frame.getContentPane().add(lblStrae);
		
		JLabel lblHausnummer = new JLabel("Hausnummer");
		lblHausnummer.setBounds(405, 175, 106, 15);
		frame.getContentPane().add(lblHausnummer);
		
		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(31, 217, 81, 15);
		frame.getContentPane().add(lblPasswort);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(136, 213, 200, 25);
		frame.getContentPane().add(passwordField);
		
		textFieldHausnummer = new JTextField();
		textFieldHausnummer.setColumns(10);
		textFieldHausnummer.setBounds(552, 170, 200, 25);
		frame.getContentPane().add(textFieldHausnummer);
		frame.addWindowListener(this);
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		frame.dispose();
		GUIKundenVerwalten oberflaeche = new GUIKundenVerwalten();
		oberflaeche.getFrame().setVisible(true);
	}
}
