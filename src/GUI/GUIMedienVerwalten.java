package GUI;
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
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class GUIMedienVerwalten implements WindowListener{

	private JFrame frame;
	private JTable table;
	private Connection con;

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
	    frame.setBounds((screenSize.width-1300)/2, (screenSize.height-400)/2, 1300, 400);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(this);
		con = SimpleQuery.connect();
		
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
		
		JScrollPane scrollPaneMedien = new JScrollPane();
		scrollPaneMedien.setBounds(290, 87, 978, 245);
		frame.getContentPane().add(scrollPaneMedien);
		
		// Richtige Daten anzeigen
		JComboBox<String> comboBoxKategorie = new JComboBox<>();
		comboBoxKategorie.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				String kategorie = comboBoxKategorie.getSelectedItem().toString();
				PreparedStatement anz = con.prepareStatement("select count(*) from " + kategorie);
				ResultSet anzahl = anz.executeQuery();
				anzahl.next();
				int zeilenanzahl = anzahl.getInt(1);
				PreparedStatement pst = con.prepareStatement("select * from " + kategorie);
			    ResultSet rs = pst.executeQuery();
			    int spaltenanzahl;
			    if(kategorie.equals("Buecher")) {
			    	spaltenanzahl = GUIMain.columnNamesBuecher.length; 
			    } else if (kategorie.equals("Filme")){
			    	spaltenanzahl = GUIMain.columnNamesFilme.length; 
			    } else {
			    	spaltenanzahl = GUIMain.columnNamesVideospiele.length;
			    }
			    String data[][] = new String[zeilenanzahl][spaltenanzahl];
			    int indexA = 0;
			    while(rs.next()) {
			    	for(int i=1;i<spaltenanzahl+1;i++) {
			    		if(i == 4) {
			    			int tmp = rs.getInt(i);
			    			String stat = "";
			    			switch(tmp) {
			    				case 0: stat = "Verliehen"; break;
			    				case 1: stat = "Verkauft";  break;
			    				case 2: stat = "Entsorgt";  break;
			    				case 3: stat = "Auf Lager"; break;
			    				default:
			    			}
			    			data[indexA][i-1] = stat;
			    		} else {
			    			data[indexA][i-1] = rs.getString(i);
			    		}
			    	}
			    	indexA++;
			    }
			    if(kategorie.equals("Filme")) {
			    	table = new JTable(data, GUIMain.columnNamesFilme);
			    } else 	if(kategorie.equals("Buecher")) {
			    	table = new JTable(data, GUIMain.columnNamesBuecher);
			    } else {
			    	table = new JTable(data, GUIMain.columnNamesVideospiele);
			    }
				
				TableColumn column = null;
				for (int i = 0; i < spaltenanzahl-1; i++) {
				    column = table.getColumnModel().getColumn(i);
				    column.setPreferredWidth(120);
				}
				scrollPaneMedien.setViewportView(table);
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		});
		comboBoxKategorie.addItem("Filme");
		comboBoxKategorie.addItem("Buecher");
		comboBoxKategorie.addItem("Videospiele");
		comboBoxKategorie.setBounds(290, 35, 200, 25);
		frame.getContentPane().add(comboBoxKategorie);
		
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
