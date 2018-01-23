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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Color;

public class GUIKundenVerwalten implements WindowListener{

	private JFrame frame;
	private JTextField textFieldSuche;
	private Connection con;
	private JTable table;
	private static String kundennummer;
	private boolean entfernen;
	private JLabel lblKunde ;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIKundenVerwalten window = new GUIKundenVerwalten(false);
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
	public GUIKundenVerwalten(boolean entfernen) {
		this.entfernen = entfernen;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize(){
		frame = new JFrame("Kunden verwalten");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-1300)/2, (screenSize.height-400)/2, 1300, 400);
		frame.getLayeredPane().setLayout(null);
		frame.addWindowListener(this);
		frame.setLayeredPane(new JLayeredPane());
		con = SimpleQuery.connect(); // Verbindung zur Datenbank herstellen
		
		JLabel lblNewLabel2 = new JLabel("");
		lblNewLabel2.setIcon(new ImageIcon("/home/pascal/Software Engineering/Projekt/Diagramme/Wurm.jpg"));
		lblNewLabel2.setBounds(1225, 0, 75, 64);
		frame.getLayeredPane().add(lblNewLabel2, new Integer(1));
		
		lblKunde = new JLabel("Kunde hat noch Sachen ausgeliehen!");
		lblKunde.setVisible(false);
		lblKunde.setForeground(Color.RED);
		lblKunde.setBounds(12, 225, 275, 15);
		frame.getLayeredPane().add(lblKunde, new Integer(1));
		
		if(this.entfernen) {
			try {
				String sql = "select * from HistorieFilme where kundennummer=" + GUIKundenVerwalten.kundennummer;
				sql += " and rueckgabedatum is null";
				PreparedStatement pruef = con.prepareStatement(sql);
				ResultSet rs = pruef.executeQuery();
				boolean gefunden = false;
				if(rs.next()) {
					gefunden = true;
				}
				sql = "select * from HistorieFilme where kundennummer=" + GUIKundenVerwalten.kundennummer;
				sql += " and rueckgabedatum is null";
				pruef = con.prepareStatement(sql);
				rs = pruef.executeQuery();
				if(rs.next()) {
					gefunden = true;
				}
				sql = "select * from HistorieFilme where kundennummer=" + GUIKundenVerwalten.kundennummer;
				sql += " and rueckgabedatum is null";
				pruef = con.prepareStatement(sql);
				rs = pruef.executeQuery();
				if(rs.next()) {
					gefunden = true;
				}
				if(gefunden) {
					lblKunde.setVisible(true);
				} else {
					String statement = "Delete from Kunden where kundennummer=" + GUIKundenVerwalten.kundennummer;
					PreparedStatement pst = con.prepareStatement(statement);
					pst.executeUpdate();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		// Kunden Hinzufuegen Fenster oeffnen
		JButton btnHinzufuegen = new JButton("Hinzufügen");
		btnHinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
					try {
						if(con != null) con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				GUIKundenHinzufuegen hinzufuegen = new GUIKundenHinzufuegen(false,null);
				hinzufuegen.getFrame().setVisible(true);
			}
		});
		btnHinzufuegen.setBounds(24, 30, 226, 35);
		frame.getLayeredPane().add(btnHinzufuegen, new Integer(1));
		
		// Kunden entfernen und Bestaetigung abfragen
		JButton btnEntfernen = new JButton("Entfernen");
		btnEntfernen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int zeile = table.getSelectedRow();
				if(zeile != -1) {
					GUIKundenVerwalten.kundennummer = table.getModel().getValueAt(zeile, 0).toString();
					frame.dispose();
					try {
						if(con != null) con.close();
					} catch (SQLException el) {
						el.printStackTrace();
					}
					GUIBestaetigenKundenVerwalten  bestaetigung = new GUIBestaetigenKundenVerwalten(); 
					bestaetigung.getFrame().setVisible(true);
				}
			}
		});
		btnEntfernen.setBounds(24, 87, 226, 35);
		frame.getLayeredPane().add(btnEntfernen, new Integer(1));
		
		// Ausgewählten Kunden bearbeiten
		JButton btnaendern = new JButton("Ändern");
		btnaendern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int zeile = table.getSelectedRow();
				if(zeile != -1) {
					try {
						con.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					String nummer = table.getModel().getValueAt(zeile, 0).toString();
					GUIKundenHinzufuegen aendern = new GUIKundenHinzufuegen(true, nummer);
					aendern.getFrame().setVisible(true);
					frame.dispose();
				}
			}
		});
		btnaendern.setBounds(24, 144, 226, 35);
		frame.getLayeredPane().add(btnaendern, new Integer(1));
		
		JScrollPane scrollPaneKunden = new JScrollPane();
		scrollPaneKunden.setBounds(290, 84, 990, 248);
		frame.getLayeredPane().add(scrollPaneKunden, new Integer(1));
		
		// Fenster schliessen und zum Hauptfenster zurückkehren
		JButton btnFertigStellen = new JButton("Fertig stellen");
		btnFertigStellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowClosing(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
			}
		});
		btnFertigStellen.setBounds(24, 297, 226, 35);
		frame.getLayeredPane().add(btnFertigStellen, new Integer(1));
		
		JLabel lblSuche = new JLabel("Suche");
		lblSuche.setBounds(354, 40, 70, 15);
		frame.getLayeredPane().add(lblSuche, new Integer(1));
		
		try {
			PreparedStatement anz = con.prepareStatement("select count(*) from Kunden");
			ResultSet anzahl = anz.executeQuery();
			anzahl.next();
			int zeilenanzahl = anzahl.getInt(1);
			PreparedStatement pst = con.prepareStatement("select * from Kunden");
		    ResultSet rs = pst.executeQuery();
		    int spaltenanzahl = GUIMain.columnNamesKunden.length;
		    String data[][] = new String[zeilenanzahl][spaltenanzahl];
		    int indexA = 0;
		    while(rs.next()) {
		    	for(int i=1;i<spaltenanzahl+2;i++) {
		    		if(i < 7) data[indexA][i-1] = rs.getString(i);
		    		else if(i > 7) data[indexA][i-2] = rs.getString(i);
		    	}
		    	indexA++;
		    }	
			TableColumn column = null;
			table = new JTable(data,GUIMain.columnNamesKunden);
			for (int i = 0; i < spaltenanzahl-1; i++) {
			    column = table.getColumnModel().getColumn(i);
			    column.setPreferredWidth(120);
			}
			scrollPaneKunden.setViewportView(table);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		

		// Feld, um in allen Spalten zu suchen
		textFieldSuche = new JTextField();
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);
        textFieldSuche.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textFieldSuche.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textFieldSuche.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Nicht genutzt
            }

        });
		textFieldSuche.setBounds(421, 35, 200, 25);
		frame.getLayeredPane().add(textFieldSuche, new Integer(1));
		textFieldSuche.setColumns(10);
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
		try {
			if(con != null) con.close();
		} catch (SQLException el) {
			el.printStackTrace();
		}
		GUIMain oberflaeche = new GUIMain();
		oberflaeche.getFrame().setVisible(true);
	}
}
