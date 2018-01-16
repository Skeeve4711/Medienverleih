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
import javax.swing.JButton;
import javax.swing.JTextField;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class GUIMedienHinzufuegen implements WindowListener{

	
	private boolean aendern;
	private String nummer;
	private String kategorie;
	
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
	private JComboBox<String> comboBoxStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMedienHinzufuegen window = new GUIMedienHinzufuegen(false, null, null);
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
	public GUIMedienHinzufuegen(boolean aendern, String nummer, String kategorie) {
		this.aendern = aendern;
		this.nummer = nummer;
		this.kategorie = kategorie;
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
		
		comboBoxStatus = new JComboBox<>();
		comboBoxStatus.setBounds(187, 271, 200, 25);
		comboBoxStatus.addItem("Verliehen");
		comboBoxStatus.addItem("Verkauft");
		comboBoxStatus.addItem("Entsorgt");
		comboBoxStatus.addItem("Auf Lager");
		comboBoxStatus.setSelectedIndex(3);
		frame.getContentPane().add(comboBoxStatus);
		
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
		
		// Feld. um Kategorie des neuen Mediums auszuwaehlen
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
					String statement, daten;
					if(aendern) {
						statement = "update " + kategorie + " set ";
						daten = getDataUpdate(kategorie);
						if(daten != null) daten += "where artikelnummer=" + nummer;
					} else {
						statement = "insert into " + kategorie + " values(";
						daten = getDataInsert(kategorie);

					}
					if(daten != null) { // statement nur ausführen, wenn auch Daten vorhanden sind
						statement += daten;
						PreparedStatement pst = con.prepareStatement(statement);
						pst.executeUpdate();
					}
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
				windowClosing(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
			}
		});
		btnFertigstellen.setBounds(300, 342, 226, 35);
		frame.getContentPane().add(btnFertigstellen);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(31, 276, 70, 15);
		frame.getContentPane().add(lblStatus);
		
		// Wenn geaendert werden soll, Daten übernehmen
		if(this.aendern && this.nummer != null) {
			try {
				frame.setTitle("Medium ändern");
				con = SimpleQuery.connect();
				String statement = "Select * from " + kategorie + " where artikelnummer=" + nummer;
				PreparedStatement pst = con.prepareStatement(statement);
				ResultSet rs = pst.executeQuery();
				rs.next();
				textFelderFuellen(rs);
				comboBoxKategorie.setEnabled(false);
			}catch(SQLException ex) {
				ex.printStackTrace();
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
		GUIMedienVerwalten oberflaeche = new GUIMedienVerwalten(false, null , null);
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
	
	// Setzt String aus allen Feldern für Einfuege-Statement zusammen
	public String getDataInsert(String kategorie) {
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
		String status = comboBoxStatus.getSelectedItem().toString();
		if(status.equals("Verliehen")) {
			daten += "0" + ",";
		} else 	if(status.equals("Verkauft")) {
			daten += "1" + ",";
		} else 	if(status.equals("Entsorgt")) {
			daten += "2" + ",";
		} else {
			daten += "3" + ",";
		}
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
	
	// Setzt String aus allen Feldern für Update-Statement zusammen
	public String getDataUpdate(String kategorie) {
		// Prüfen, ob alle Textfelder Inhalt besitzen
		 for (Component c : frame.getContentPane().getComponents()) {
		     if (c instanceof JTextField) { 
		        if(((JTextField)c).getText().trim().isEmpty()) {
		        	return null;
		        }
		     }
		 }
		String daten = "artikelnummer=" + textFieldArtikelnummer.getText() + ",";
		daten += "kaufpreis= " + textFieldKaufpreis.getText() + ",";
		daten += "altersfreigabe= " + textFieldAltersfreigabe.getText() + ",";
		daten += "status=";
		String status = comboBoxStatus.getSelectedItem().toString();
		if(status.equals("Verliehen")) {
			daten += "0" + ",";
		} else 	if(status.equals("Verkauft")) {
			daten += "1" + ",";
		} else 	if(status.equals("Entsorgt")) {
			daten += "2" + ",";
		} else {
			daten += "3" + ",";
		}
		if(kategorie.equals("Buecher")) {
			daten += "autor=\'" + textFieldSchauspieler.getText() + "\',"; 
		} else if(kategorie.equals("Filme")){
			daten += "schauspieler=\'" + textFieldSchauspieler.getText() + "\',";
			daten += "regisseur=\'" + textFieldRegisseur.getText() + "\',";
			daten += "filmstudio=\'" + textFieldFilmstudio.getText() + "\',";
		} else {
			daten += "entwickler=\'" + textFieldSchauspieler.getText() + "\',";
			daten += "publisher=\'" + textFieldRegisseur.getText() + "\',";
		}
		daten += "erscheinungsdatum=\'" + textFieldErscheinungsdatum.getText() + "\',";
		daten += "titel=\'" + textFieldTitel.getText() + "\',";
		daten += "genre=\'" + textFieldGenre.getText() + "\',";
		daten += "leihpreis=" + textFieldLeihpreis.getText();
		return daten;
	}
	
	// Befuellt alle Felder in GUI
	public void textFelderFuellen(ResultSet rs) throws SQLException{
		int index = 1;
		textFieldArtikelnummer.setText(rs.getString(index++));
		textFieldKaufpreis.setText(rs.getString(index++));
		textFieldAltersfreigabe.setText(rs.getString(index++));
		int ind = Integer.parseInt(rs.getString(index++));
		System.out.println(ind);
		comboBoxStatus.setSelectedIndex(ind);
		if(this.kategorie.equals("Buecher")) {
			textFieldSchauspieler.setText(rs.getString(index++)); 
		} else {
			textFieldSchauspieler.setText(rs.getString(index++));
			textFieldRegisseur.setText(rs.getString(index++));
			if(kategorie.equals("Filme")) {
				textFieldFilmstudio.setText(rs.getString(index++));
			}
		}
		textFieldErscheinungsdatum.setText(rs.getString(index++));
		textFieldTitel.setText(rs.getString(index++));
		textFieldGenre.setText(rs.getString(index++));
		textFieldLeihpreis.setText(rs.getString(index++));
	}
}
