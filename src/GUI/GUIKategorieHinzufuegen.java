package GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUIKategorieHinzufuegen implements WindowListener{

	private JFrame frame;
	private JTextField textFieldName;
	private JTextField textFieldTypname;
	private JTextField textFieldTypeigenschaft;
	
	// Listen, um Namen und Eigenschaften der Typen zu speichern
	private ArrayList<String> eigenschaften = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIKategorieHinzufuegen window = new GUIKategorieHinzufuegen();
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
	public GUIKategorieHinzufuegen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Kategorie Hinzufügen");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-560)/2, (screenSize.height-400)/2, 560, 400);
		frame.getContentPane().setLayout(null);
		
		// Fenster schliessen und Kategorie erstellen
		JButton btnFertigstellen = new JButton("Fertig stellen");
		btnFertigstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // TODO Kategorie in Datenbank eintragen
				if(textFieldTypname.getText().trim().length() > 0 && textFieldName.getText().trim().length() > 0) { // Testen, ob Typname vorhanden
					Intern.Typ typ = new Intern.Typ(textFieldTypname.getText(), eigenschaften);
					Intern.Kategorie kategorie = new Intern.Kategorie(textFieldName.getText(),"",typ);
				}
				windowClosing(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
			}
		});
		btnFertigstellen.setBounds(298, 267, 226, 35);
		frame.getContentPane().add(btnFertigstellen);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(324, 39, 216, 216);
		frame.getContentPane().add(textArea);
		
		JLabel lblTypeigenschaften = new JLabel("Typeigenschaften");
		lblTypeigenschaften.setBounds(324, 12, 143, 15);
		frame.getContentPane().add(lblTypeigenschaften);
		
		// Fügt Typeigenschaft der Liste hinzu
		JButton btnTypeigenschaftHinzufuegen = new JButton("Typeigenschaft hinzufügen");
		btnTypeigenschaftHinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldTypeigenschaft.getText().trim().length() > 0) { // Test, ob eigenschaft eingegeben worden
					String eigenschaft = textFieldTypeigenschaft.getText();
					if(!eigenschaften.contains(eigenschaft)){ // Testen, ob Eigenschaft bereits enthalten
						eigenschaften.add(eigenschaft);
						textArea.setText(textArea.getText() + eigenschaft + "\n"); // Eigenschaft in TextArea eintragen
					}
				}
			}
		});
		btnTypeigenschaftHinzufuegen.setBounds(22, 267, 240, 35);
		frame.getContentPane().add(btnTypeigenschaftHinzufuegen);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(22, 26, 54, 15);
		frame.getContentPane().add(lblName);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(135, 21, 170, 25);
		frame.getContentPane().add(textFieldName);
		textFieldName.setColumns(10);
		
		JLabel lblTypname = new JLabel("Typname");
		lblTypname.setBounds(22, 73, 72, 15);
		frame.getContentPane().add(lblTypname);
		
		JLabel lblTypeigenschaft = new JLabel("Typeigenschaft");
		lblTypeigenschaft.setBounds(22, 119, 109, 15);
		frame.getContentPane().add(lblTypeigenschaft);
		
		textFieldTypname = new JTextField();
		textFieldTypname.setColumns(10);
		textFieldTypname.setBounds(135, 68, 170, 25);
		frame.getContentPane().add(textFieldTypname);
		
		textFieldTypeigenschaft = new JTextField();
		textFieldTypeigenschaft.setColumns(10);
		textFieldTypeigenschaft.setBounds(135, 114, 170, 25);
		frame.getContentPane().add(textFieldTypeigenschaft);
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
