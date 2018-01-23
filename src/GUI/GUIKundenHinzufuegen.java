package GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

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
	private Connection con;
	private boolean aendern;
	private String nummer;
	private JLabel lblPasswortUngueltig;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIKundenHinzufuegen window = new GUIKundenHinzufuegen(false,null);
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
	public GUIKundenHinzufuegen(boolean aendern,String nummer) {
		this.aendern = aendern;
		this.nummer = nummer;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Kunden Hinzufügen");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-800)/2, (screenSize.height-400)/2, 800, 400);
		frame.getLayeredPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setIcon(new ImageIcon("/home/pascal/Software Engineering/Projekt/Diagramme/GluecklicherKunde.jpg"));
		lblNewLabel.setBounds(0, 0, 1300, 400);
		frame.setLayeredPane(new JLayeredPane());
		frame.getLayeredPane().add(lblNewLabel);
		
		// Fertig stellen Button um Kunden anzulegen
		JButton btnFertigstellen = new JButton("Fertig stellen");
		btnFertigstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					con = SimpleQuery.connect();
					String statement, daten;
					if(aendern) {
						statement = "update Kunden set ";
						daten = getDataUpdate();
					} else {
						statement = "insert into Kunden values(";
						daten = getDataInsert();

					}
					if(daten != null) { // statement nur ausführen, wenn auch Daten vorhanden sind
						statement += daten;
						PreparedStatement pst = con.prepareStatement(statement);
						pst.executeUpdate();
						windowClosing(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
					}
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
				
			}
		});
		btnFertigstellen.setBounds(300, 285, 226, 35);
		frame.getLayeredPane().add(btnFertigstellen, new Integer(1));
		
		textFieldVorname = new JTextField();
		textFieldVorname.setBounds(136, 29, 200, 25);
		frame.getLayeredPane().add(textFieldVorname, new Integer(1));
		textFieldVorname.setColumns(10);
		
		JLabel lblName = new JLabel("Vorname");
		lblName.setBounds(31, 31, 70, 15);
		frame.getLayeredPane().add(lblName, new Integer(1));
		
		textFieldNachname = new JTextField();
		textFieldNachname.setColumns(10);
		textFieldNachname.setBounds(136, 76, 200, 25);
		frame.getLayeredPane().add(textFieldNachname, new Integer(1));
		
		JLabel lblNachname = new JLabel("Nachname");
		lblNachname.setBounds(31, 78, 81, 15);
		frame.getLayeredPane().add(lblNachname, new Integer(1));
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(136, 123, 200, 25);
		frame.getLayeredPane().add(textFieldEmail, new Integer(1));
		textFieldEmail.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setBounds(31, 125, 81, 15);
		frame.getLayeredPane().add(lblEmail, new Integer(1));
		
		textFieldAlter = new JTextField();
		textFieldAlter.setColumns(10);
		textFieldAlter.setBounds(136, 170, 200, 25);
		frame.getLayeredPane().add(textFieldAlter, new Integer(1));
		
		JLabel lblAlter = new JLabel("Alter");
		lblAlter.setBounds(31, 172, 81, 15);
		frame.getLayeredPane().add(lblAlter, new Integer(1));
		
		JLabel lblPostleitzahl = new JLabel("Postleitzahl");
		lblPostleitzahl.setBounds(405, 34, 91, 15);
		frame.getLayeredPane().add(lblPostleitzahl, new Integer(1));
		
		textFieldPLZ = new JTextField();
		textFieldPLZ.setColumns(10);
		textFieldPLZ.setBounds(552, 29, 200, 25);
		frame.getLayeredPane().add(textFieldPLZ, new Integer(1));
		
		JLabel lblOrt = new JLabel("Ort");
		lblOrt.setBounds(405, 81, 91, 15);
		frame.getLayeredPane().add(lblOrt, new Integer(1));
		
		textFieldOrt = new JTextField();
		textFieldOrt.setColumns(10);
		textFieldOrt.setBounds(552, 76, 200, 25);
		frame.getLayeredPane().add(textFieldOrt, new Integer(1));
		
		textFieldStrasse = new JTextField();
		textFieldStrasse.setColumns(10);
		textFieldStrasse.setBounds(552, 123, 200, 25);
		frame.getLayeredPane().add(textFieldStrasse, new Integer(1));
		
		JLabel lblStrae = new JLabel("Straße");
		lblStrae.setBounds(405, 128, 91, 15);
		frame.getLayeredPane().add(lblStrae, new Integer(1));
		
		JLabel lblHausnummer = new JLabel("Hausnummer");
		lblHausnummer.setBounds(405, 175, 106, 15);
		frame.getLayeredPane().add(lblHausnummer, new Integer(1));
		
		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(31, 217, 81, 15);
		frame.getLayeredPane().add(lblPasswort, new Integer(1));
		
		passwordField = new JPasswordField();
		passwordField.setBounds(136, 213, 200, 25);
		frame.getLayeredPane().add(passwordField, new Integer(1));
		
		textFieldHausnummer = new JTextField();
		textFieldHausnummer.setColumns(10);
		textFieldHausnummer.setBounds(552, 170, 200, 25);
		frame.getLayeredPane().add(textFieldHausnummer, new Integer(1));
		
		lblPasswortUngueltig = new JLabel("Passwort ungueltig");
		lblPasswortUngueltig.setForeground(Color.RED);
		lblPasswortUngueltig.setBounds(161, 250, 139, 15);
		frame.getLayeredPane().add(lblPasswortUngueltig, new Integer(1));
		frame.addWindowListener(this);
		lblPasswortUngueltig.setVisible(false);
		
		// Wenn geaendert werden soll, Daten übernehmen
		if(this.aendern && this.nummer != null) {
			try {
				frame.setTitle("Kunden ändern");
				con = SimpleQuery.connect();
				String statement = "Select * from Kunden where kundennummer=" + nummer;
				PreparedStatement pst = con.prepareStatement(statement);
				ResultSet rs = pst.executeQuery();
				rs.next();
				textFelderFuellen(rs);
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		 for (Component c : frame.getLayeredPane().getComponents()) {
		     if (c instanceof JLabel) { 
		        	c.setForeground(Color.BLUE);
		     }
		 }
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
		GUIKundenVerwalten oberflaeche = new GUIKundenVerwalten(false);
		oberflaeche.getFrame().setVisible(true);
	}
	
	// Setzt String aus allen Feldern für Einfuege-Statement zusammen
		public String getDataInsert(){
			lblPasswortUngueltig.setVisible(false);
			// Prüfen, ob alle Textfelder Inhalt besitzen
			 for (Component c : frame.getLayeredPane().getComponents()) {
			     if (c instanceof JTextField) { 
			        if(((JTextField)c).getText().trim().isEmpty()) {
			        	return null;
			        }
			     }
			 }
			String daten = "NULL, \'" + textFieldVorname.getText() + "\',";
			daten += "\'" + textFieldNachname.getText() + "\',";
			String email = textFieldEmail.getText();
            Matcher m = Intern.Person.email_regex.matcher(email);
            if(m.matches()) {
                   daten += "\'" + email + "\',";
            } else {
                   textFieldEmail.setText("Email ungültig");
                   return null;
            }
			daten += textFieldAlter.getText() + ",";
			daten += "0,";
			String passwort = String.valueOf(passwordField.getPassword());
            Matcher p = Intern.Kunde.anforderungen.matcher(passwort);
            if(p.matches()) {
            	daten += "PASSWORD(\'" + passwort + "\'),";
            } else {
            	lblPasswortUngueltig.setVisible(true);
                return null;
            }
			daten += "\'" + textFieldPLZ.getText() + "\',";
			daten += "\'" + textFieldOrt.getText() + "\',";
			daten += "\'" + textFieldStrasse.getText() + "\',";
			daten += "\'" + textFieldHausnummer.getText() + "\'";
			daten += ")";
			return daten;
		}
		
		// Setzt String aus allen Feldern für Update-Statement zusammen
		public String getDataUpdate() {
			lblPasswortUngueltig.setVisible(false);
			// Prüfen, ob alle Textfelder Inhalt besitzen
			 for (Component c : frame.getLayeredPane().getComponents()) {
			     if (c instanceof JTextField) { 
			        if(((JTextField)c).getText().trim().isEmpty()) {
			        	return null;
			        }
			     }
			 }
			String daten = "vorname=\'" + textFieldVorname.getText() + "\',";
			daten += "nachname=\'" + textFieldNachname.getText() + "\',";
			String email = textFieldEmail.getText();
            Matcher m = Intern.Person.email_regex.matcher(email);
            if(m.matches()) {
                   daten += "email=\'" + email + "\',";
            } else {
                   textFieldEmail.setText("Email ungültig");
                   return null;
            }
			daten += "`alter`=" + textFieldAlter.getText() + ",";
			String passwort = String.valueOf(passwordField.getPassword());
            Matcher p = Intern.Kunde.anforderungen.matcher(passwort);
            if(p.matches()) {
            	daten += "passwort=PASSWORD(\'" + passwort + "\'),";
            } else {
            	lblPasswortUngueltig.setVisible(true);
                return null;
            }
			daten += "plz=\'" + textFieldPLZ.getText() + "\',";
			daten += "ort=\'" + textFieldOrt.getText() + "\',";
			daten += "strasse=\'" + textFieldStrasse.getText() + "\',";
			daten += "hausnummer=\'" + textFieldHausnummer.getText() + "\'";
			daten += " where kundennummer=" + nummer;
			return daten;
		}
		
		// Befuellt alle Felder in GUI
		public void textFelderFuellen(ResultSet rs) throws SQLException{
			int index = 2;
			textFieldVorname.setText(rs.getString(index++));
			textFieldNachname.setText(rs.getString(index++));
			textFieldEmail.setText(rs.getString(index++));
			textFieldAlter.setText(rs.getString(index++));
			index++;
			index++;
			textFieldPLZ.setText(rs.getString(index++));
			textFieldOrt.setText(rs.getString(index++));
			textFieldStrasse.setText(rs.getString(index++));
			textFieldHausnummer.setText(rs.getString(index++));
		}
}
