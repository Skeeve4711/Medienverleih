package GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class GUIBestaetigenKundenVerwalten implements WindowListener{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIBestaetigenKundenVerwalten window = new GUIBestaetigenKundenVerwalten();
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
	public GUIBestaetigenKundenVerwalten() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Bestaetigung");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-200)/2, (screenSize.height-100)/2, 200, 100);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(this);
		
		JLabel lblSindSieSicher = new JLabel("Sind Sie sicher?");
		lblSindSieSicher.setBounds(37, 12, 116, 15);
		frame.getContentPane().add(lblSindSieSicher);
		
		// Anfrage bestaetigen
		JButton btnJa = new JButton("Ja");
		btnJa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				GUIKundenVerwalten verwaltung = new GUIKundenVerwalten(true);
				verwaltung.getFrame().setVisible(true);
			}
		});
		btnJa.setBounds(12, 35, 75, 25);
		frame.getContentPane().add(btnJa);
		
		// Anfrage ablehnen
		JButton btnNein = new JButton("Nein");
		btnNein.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				GUIKundenVerwalten verwaltung = new GUIKundenVerwalten(false);
				verwaltung.getFrame().setVisible(true);
			}
		});
		btnNein.setBounds(105, 35, 75, 25);
		frame.getContentPane().add(btnNein);
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

}
