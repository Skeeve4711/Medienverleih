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

import javax.swing.JFrame;
import javax.print.attribute.AttributeSet;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.sun.xml.internal.txw2.Document;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JComboBox;

public class GUIMedienHinzufuegen implements WindowListener{

	private JFrame frame;
	private JTextField textFieldErscheinungsdatum;
	private JTextField textFieldTitel;
	private JTextField textFieldArtikelnummer;
	private JTextField textFieldKaufpreis;
	private JTextField textFieldGenre;
	private JTextField textFieldAltersfreigabe;
	private JTextField textFieldLeihpreis;
	private JTextField textFieldSchauspieler;
	private JTextField textFieldRegisseur;
	private Connection con;
	private JTextField textFieldFilmstudio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMedienHinzufuegen window = new GUIMedienHinzufuegen();
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
	public GUIMedienHinzufuegen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Medium Hinzufügen");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-800)/2, (screenSize.height-500)/2, 800, 500);
		frame.getContentPane().setLayout(null);
		
		textFieldSchauspieler = new JTextField();
		textFieldSchauspieler.setColumns(10);
		textFieldSchauspieler.setBounds(552, 173, 200, 25);
		frame.getContentPane().add(textFieldSchauspieler);
		
		textFieldRegisseur = new JTextField();
		textFieldRegisseur.setColumns(10);
		textFieldRegisseur.setBounds(552, 223, 200, 25);
		frame.getContentPane().add(textFieldRegisseur);
		
		JLabel lblSchauspieler = new JLabel("Schauspieler");
		lblSchauspieler.setBounds(405, 175, 121, 15);
		frame.getContentPane().add(lblSchauspieler);
		
		JLabel lblRegisseur = new JLabel("Regisseur");
		lblRegisseur.setBounds(405, 228, 121, 15);
		frame.getContentPane().add(lblRegisseur);
		
		textFieldErscheinungsdatum = new JTextField();
		textFieldErscheinungsdatum.setBounds(187, 26, 200, 25);
		frame.getContentPane().add(textFieldErscheinungsdatum);
		textFieldErscheinungsdatum.setColumns(10);
		
		JLabel lblName = new JLabel("Erscheinungsdatum");
		lblName.setBounds(31, 31, 153, 15);
		frame.getContentPane().add(lblName);
		
		textFieldTitel = new JTextField();
		textFieldTitel.setColumns(10);
		textFieldTitel.setBounds(187, 73, 200, 25);
		frame.getContentPane().add(textFieldTitel);
		
		JLabel lblNachname = new JLabel("Titel");
		lblNachname.setBounds(31, 78, 81, 15);
		frame.getContentPane().add(lblNachname);
		
		textFieldArtikelnummer = new JTextField();
		textFieldArtikelnummer.setBounds(187, 120, 200, 25);
		frame.getContentPane().add(textFieldArtikelnummer);
		textFieldArtikelnummer.setColumns(10);
		
		JLabel lblEmail = new JLabel("Artikelnummer");
		lblEmail.setBounds(31, 125, 138, 15);
		frame.getContentPane().add(lblEmail);
		
		textFieldKaufpreis = new JTextField();
		textFieldKaufpreis.setColumns(10);
		textFieldKaufpreis.setBounds(187, 170, 200, 25);
		frame.getContentPane().add(textFieldKaufpreis);
		
		JLabel lblAlter = new JLabel("Kaufpreis");
		lblAlter.setBounds(31, 172, 81, 15);
		frame.getContentPane().add(lblAlter);
		
		JLabel lblPostleitzahl = new JLabel("Kategorie");
		lblPostleitzahl.setBounds(405, 31, 91, 15);
		frame.getContentPane().add(lblPostleitzahl);
		
		JLabel lblOrt = new JLabel("Genre");
		lblOrt.setBounds(405, 78, 129, 15);
		frame.getContentPane().add(lblOrt);
		
		textFieldGenre = new JTextField();
		textFieldGenre.setColumns(10);
		textFieldGenre.setBounds(552, 73, 200, 25);
		frame.getContentPane().add(textFieldGenre);
		
		textFieldAltersfreigabe = new JTextField();
		textFieldAltersfreigabe.setColumns(10);
		textFieldAltersfreigabe.setBounds(552, 120, 200, 25);
		frame.getContentPane().add(textFieldAltersfreigabe);
		
		JLabel lblStrasse = new JLabel("Altersfreigabe");
		lblStrasse.setBounds(405, 125, 121, 15);
		frame.getContentPane().add(lblStrasse);
		
		JLabel lblPasswort = new JLabel("Leihpreis");
		lblPasswort.setBounds(31, 217, 81, 15);
		frame.getContentPane().add(lblPasswort);
		
		textFieldLeihpreis = new JTextField();
		textFieldLeihpreis.setColumns(10);
		textFieldLeihpreis.setBounds(187, 215, 200, 25);
		frame.getContentPane().add(textFieldLeihpreis);
		
		JLabel lblFilmstudio = new JLabel("Filmstudio");
		lblFilmstudio.setBounds(405, 276, 121, 15);
		frame.getContentPane().add(lblFilmstudio);
		
		textFieldFilmstudio = new JTextField();
		textFieldFilmstudio.setColumns(10);
		textFieldFilmstudio.setBounds(552, 271, 200, 25);
		frame.getContentPane().add(textFieldFilmstudio);
		
		JComboBox<String> comboBoxKategorie = new JComboBox<>();
		comboBoxKategorie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String auswahl = comboBoxKategorie.getSelectedItem().toString();
				if(auswahl == "Filme") {
					lblSchauspieler.setText("Schauspieler");
					lblRegisseur.setText("Regisseur");
					textFieldRegisseur.setVisible(true);
					lblRegisseur.setVisible(true);
					textFieldFilmstudio.setVisible(true);
					lblFilmstudio.setVisible(true);
				} else if (auswahl == "Buecher") {
					lblSchauspieler.setText("Autor");
					textFieldRegisseur.setVisible(false);
					lblRegisseur.setVisible(false);
					textFieldFilmstudio.setVisible(false);
					lblFilmstudio.setVisible(false);
				} else {
					lblSchauspieler.setText("Entwickler");
					lblRegisseur.setText("Publisher");
					textFieldRegisseur.setVisible(true);
					lblRegisseur.setVisible(true);
					textFieldFilmstudio.setVisible(false);
					lblFilmstudio.setVisible(false);
				}
			}
		});
		comboBoxKategorie.setBounds(552, 26, 200, 25);
		frame.getContentPane().add(comboBoxKategorie);
		frame.addWindowListener(this);
		comboBoxKategorie.addItem("Filme");
		comboBoxKategorie.addItem("Buecher");
		comboBoxKategorie.addItem("Videospiele");
		
		
		// Fertig stellen Button um Medien anzulegen
		JButton btnFertigstellen = new JButton("Fertig stellen");
		btnFertigstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					con = SimpleQuery.connect();
					String kategorie = comboBoxKategorie.getSelectedItem().toString();
					String statement = "insert into " + kategorie + " values(";
					String daten = getData(kategorie);
					if(daten != null) { // statement nur ausführen, wenn auch Daten vorhanden sind
						statement += daten;
						System.out.println(statement);
						PreparedStatement pst = con.prepareStatement(statement);
						pst.executeUpdate();
					}
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
				windowClosing(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING)); // TODO Kunden hinzufuegen
			}
		});
		btnFertigstellen.setBounds(300, 342, 226, 35);
		frame.getContentPane().add(btnFertigstellen);
		


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
		GUIMedienVerwalten oberflaeche = new GUIMedienVerwalten();
		oberflaeche.getFrame().setVisible(true);
		try {
			if(con != null) {
				con.close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	// Setzt String aus allen Feldern für Statement zusammen
	public String getData(String kategorie) {
		// Prüfen, ob alle Textfelder Inhalt besitzen
		 for (Component c : frame.getContentPane().getComponents()) {
		     if (c instanceof JTextField) { 
		        if(((JTextField)c).getText().trim().isEmpty()) {
		        	return null;
		        }
		     }
		 }
		String daten = textFieldArtikelnummer.getText() + ",";
		daten += textFieldKaufpreis.getText() + ",";
		daten += textFieldAltersfreigabe.getText() + ",";
		daten += "3" + ",";
		if(kategorie.equals("Buecher")) {
			daten += "\'" + textFieldSchauspieler.getText() + "\',"; 
		} else {
			daten += "\'" + textFieldSchauspieler.getText() + "\',";
			daten += "\'" + textFieldRegisseur.getText() + "\',";
			if(kategorie.equals("Filme")) {
				daten += "\'" + textFieldFilmstudio.getText() + "\',";
			}
		}
		daten += "\'" + textFieldErscheinungsdatum.getText() + "\',";
		daten += "\'" + textFieldTitel.getText() + "\',";
		daten += "\'" + textFieldGenre.getText() + "\',";
		daten += textFieldLeihpreis.getText();
		daten += ")";
		return daten;
	}
}
