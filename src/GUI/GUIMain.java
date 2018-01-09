package GUI;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUIMain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMain window = new GUIMain();
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
	public GUIMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Medienverleih");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-560)/2, (screenSize.height-400)/2, 560, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Kunden verwalten Fenster öffnen
		JButton btnKundenVerwalten = new JButton("Kunden verwalten");
		btnKundenVerwalten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIKundenVerwalten verwaltung = new GUIKundenVerwalten();
				verwaltung.getFrame().setVisible(true);
				frame.dispose();
			}
		});
		btnKundenVerwalten.setBounds(24, 30, 226, 35);
		frame.getContentPane().add(btnKundenVerwalten);
		
		JButton btnNewButton = new JButton("Exemplare mit Strafpreisen");
		btnNewButton.setBounds(24, 124, 226, 35);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnVerleihen = new JButton("Verleihen");
		btnVerleihen.setBounds(24, 226, 226, 35);
		frame.getContentPane().add(btnVerleihen);
		
		JButton btnRuecknahme = new JButton("Rücknahme");
		btnRuecknahme.setBounds(24, 273, 226, 35);
		frame.getContentPane().add(btnRuecknahme);
		
		JButton btnVerkaufen = new JButton("Verkaufen");
		btnVerkaufen.setBounds(24, 320, 226, 35);
		frame.getContentPane().add(btnVerkaufen);
		
		JButton btnNewButton_1 = new JButton("???");
		btnNewButton_1.setBounds(24, 77, 226, 35);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnMedienVerwalten = new JButton("Medien verwalten");
		btnMedienVerwalten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				GUIMedienVerwalten medien = new GUIMedienVerwalten();
				medien.getFrame().setVisible(true);
			}
		});
		btnMedienVerwalten.setBounds(298, 30, 226, 35);
		frame.getContentPane().add(btnMedienVerwalten);
		
		JScrollPane scrollPaneMedien = new JScrollPane();
		scrollPaneMedien.setBounds(298, 77, 226, 278);
		frame.getContentPane().add(scrollPaneMedien);
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
}
