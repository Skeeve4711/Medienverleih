package GUI;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class GUIKundenVerwalten implements WindowListener{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIKundenVerwalten window = new GUIKundenVerwalten();
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
	public GUIKundenVerwalten() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Kunden verwalten");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-560)/2, (screenSize.height-400)/2, 560, 400);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(this);
		
		// Kunden Hinzufuegen Fenster oeffnen
		JButton btnHinzufuegen = new JButton("Hinzufügen");
		btnHinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				GUIKundenHinzufuegen hinzufuegen = new GUIKundenHinzufuegen();
				hinzufuegen.getFrame().setVisible(true);
			}
		});
		btnHinzufuegen.setBounds(24, 30, 226, 35);
		frame.getContentPane().add(btnHinzufuegen);
		
		// Kunden entfernen und Bestaetigung abfragen
		JButton btnEntfernen = new JButton("Entfernen");
		btnEntfernen.addActionListener(new ActionListener() { // TODO Rückgabewert
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				GUIBestaetigenKundenVerwalten  bestaetigung = new GUIBestaetigenKundenVerwalten(); // TODO Rückgabewert auswerten
				bestaetigung.getFrame().setVisible(true);
			}
		});
		btnEntfernen.setBounds(24, 87, 226, 35);
		frame.getContentPane().add(btnEntfernen);
		
		// Ausgewählten Kunden bearbeiten
		JButton btnaendern = new JButton("Ändern");
		btnaendern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnaendern.setBounds(24, 144, 226, 35);
		frame.getContentPane().add(btnaendern);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(291, 30, 249, 306);
		frame.getContentPane().add(scrollPane);
		
		// Fenster schliessen und zum Hauptfenster zurückkehren
		JButton btnFertigStellen = new JButton("Fertig stellen");
		btnFertigStellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowClosing(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
			}
		});
		btnFertigStellen.setBounds(24, 297, 226, 35);
		frame.getContentPane().add(btnFertigStellen);
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
		GUIMain oberflaeche = new GUIMain();
		oberflaeche.getFrame().setVisible(true);
	}
}
