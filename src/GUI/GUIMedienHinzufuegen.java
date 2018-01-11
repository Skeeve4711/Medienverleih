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
import javax.swing.JList;
import javax.swing.JComboBox;

public class GUIMedienHinzufuegen implements WindowListener{

	private JFrame frame;
	private JTextField textFieldEinstellungsdatum;
	private JTextField textFieldTitel;
	private JTextField textFieldArtikelnummer;
	private JTextField textFieldKaufpreis;
	private JTextField textFieldGenre;
	private JTextField textFieldAltersfreigabe;
	private JTextField textFieldLeihpreis;
	private JTextField textFieldSchauspieler;
	private JTextField textFieldRegisseur;

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
		
		textFieldEinstellungsdatum = new JTextField();
		textFieldEinstellungsdatum.setBounds(187, 26, 200, 25);
		frame.getContentPane().add(textFieldEinstellungsdatum);
		textFieldEinstellungsdatum.setColumns(10);
		
		JLabel lblName = new JLabel("Einstellungsdatum");
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
		
		JLabel lblStrae = new JLabel("Altersfreigabe");
		lblStrae.setBounds(405, 125, 121, 15);
		frame.getContentPane().add(lblStrae);
		
		JLabel lblPasswort = new JLabel("Leihpreis");
		lblPasswort.setBounds(31, 217, 81, 15);
		frame.getContentPane().add(lblPasswort);
		
		textFieldLeihpreis = new JTextField();
		textFieldLeihpreis.setColumns(10);
		textFieldLeihpreis.setBounds(187, 215, 200, 25);
		frame.getContentPane().add(textFieldLeihpreis);
		
		textFieldSchauspieler = new JTextField();
		textFieldSchauspieler.setColumns(10);
		textFieldSchauspieler.setBounds(552, 173, 200, 25);
		frame.getContentPane().add(textFieldSchauspieler);
		
		textFieldRegisseur = new JTextField();
		textFieldRegisseur.setColumns(10);
		textFieldRegisseur.setBounds(552, 215, 200, 25);
		frame.getContentPane().add(textFieldRegisseur);
		
		JLabel lblSchauspieler = new JLabel("Schauspieler");
		lblSchauspieler.setBounds(405, 175, 121, 15);
		frame.getContentPane().add(lblSchauspieler);
		
		JLabel lblRegisseur = new JLabel("Regisseur");
		lblRegisseur.setBounds(405, 217, 121, 15);
		frame.getContentPane().add(lblRegisseur);
		
		JComboBox comboBoxKategorie = new JComboBox();
		comboBoxKategorie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String auswahl = comboBoxKategorie.getSelectedItem().toString();
				if(auswahl == "Film") {
					lblSchauspieler.setText("Schauspieler");
					lblRegisseur.setText("Regisseur");
					textFieldRegisseur.setVisible(true);
					lblRegisseur.setVisible(true);
				} else if (auswahl == "Buch") {
					lblSchauspieler.setText("Autor");
					textFieldRegisseur.setVisible(false);
					lblRegisseur.setVisible(false);
				} else {
					lblSchauspieler.setText("Entwickler");
					lblRegisseur.setText("Publisher");
					textFieldRegisseur.setVisible(true);
					lblRegisseur.setVisible(true);
				}
			}
		});
		comboBoxKategorie.setBounds(552, 26, 200, 25);
		frame.getContentPane().add(comboBoxKategorie);
		frame.addWindowListener(this);
		comboBoxKategorie.addItem("Film");
		comboBoxKategorie.addItem("Buch");
		comboBoxKategorie.addItem("Videospiel");

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
	}
}
