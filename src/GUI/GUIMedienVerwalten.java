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

public class GUIMedienVerwalten implements WindowListener{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMedienVerwalten window = new GUIMedienVerwalten();
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
	public GUIMedienVerwalten() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Medien verwalten");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-560)/2, (screenSize.height-400)/2, 560, 400);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(this);
		
		// Medium Hinzufuegen Fenster oeffnen
		JButton btnHinzufuegen = new JButton("Hinzufügen");
		btnHinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				GUIMedienHinzufuegen hinzufuegen = new GUIMedienHinzufuegen();
				hinzufuegen.getFrame().setVisible(true);
			}
		});
		btnHinzufuegen.setBounds(24, 30, 226, 35);
		frame.getContentPane().add(btnHinzufuegen);
		
		// Medium entfernen und Bestaetigung abfragen
		JButton btnAusschliessen = new JButton("Ausschließen");
		btnAusschliessen.addActionListener(new ActionListener() { // TODO Rückgabewert
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				GUIBestaetigenMedienVerwalten  bestaetigung = new GUIBestaetigenMedienVerwalten(); // TODO Rückgabewert auswerten
				bestaetigung.getFrame().setVisible(true);
			}
		});
		btnAusschliessen.setBounds(24, 87, 226, 35);
		frame.getContentPane().add(btnAusschliessen);
		
		// Ausgewähltes Medium bearbeiten
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
