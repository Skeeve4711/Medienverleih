package GUI;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.sun.corba.se.impl.ior.GenericTaggedComponent;

import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants; 

public class GUIMain implements WindowListener{

	private JFrame frame;
	private JTable table;
	private Connection con;
	private JTextField textFieldSuche;
	private JComboBox<String> comboBoxKategorie;
	private TableRowSorter<TableModel> rowSorter;
	private JLabel lblError;
	
	public static final double STRAFPREISDIGITAL = 0.1;
	public static final double STRAFPREISBUECHER = 0.2;
	
	// Spaltennamen der Tabellen
	static String[] columnNamesFilme = {"Art. Nr.",
			"Kaufpreis", "Altersfreigabe", "Status",
			"Schauspieler", "Regie", "Filmstudio",
			"Erscheinungsdatum", "Titel", "Genre",
			"Leihpreis"};
	static String[] columnNamesBuecher = {"Art. Nr.",
			"Kaufpreis", "Altersfreigabe", "Status",
			"Autor", "Erscheinungsdatum", "Titel",
			"Genre", "Leihpreis"};
	static String[] columnNamesVideospiele = {"Art. Nr.",
			"Kaufpreis", "Altersfreigabe", "Status",
			"Entwickler", "Publisher", "Erscheinungsdatum",
			"Titel", "Genre", "Leihpreis"};
	static String[] columnNamesKunden = {"Kunden Nr.",
			"Vorname", "Nachname", "E-Mail",
			"Alter", "Strafpreis",
			"PLZ", "Ort", "Straße",
			"Haus Nr."};
	static String[] columnNamesHistorie = {"Art. Nr.", "Erscheinungsdatum", "Titel",
				"Leihdatum", "Leihdauer", "Rückgabedatum", "Status"};
	static String[] columnNamesHistorieMedium = {"Kundennummer.", "Erscheinungsdatum", "Titel",
			"Leihdatum", "Leihdauer", "Rückgabedatum", "Status"};
	
	private ArrayList<String> nummern;
	private JTextField textFieldError;
	

	
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
	    frame.setBounds((screenSize.width-1300)/2, (screenSize.height-400)/2, 1300, 400);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(this);
		this.con = SimpleQuery.connect(); // Verbindung zur Datenbank aufbauen
		frame.setLayeredPane(new JLayeredPane());
		
		//Strafpreise bei Start berechnen
		try {
			String statement;
			String [] kategorie = {"Filme","Buecher","Videospiele"};
			statement = "select kundennummer from Kunden";
			PreparedStatement pst = con.prepareStatement(statement);
			ResultSet rs;
			rs = pst.executeQuery();
			GUIRuecknahme.con = con;
			while(rs.next()) {
				  String kundennummer = rs.getString(1);
				  for(int i= 0;i<kategorie.length;i++) {
					  String tabellenname = "Historie" + kategorie[i];
					  GUIRuecknahme.kategorie = kategorie[i];
					  GUIRuecknahme.kundennummer = kundennummer;
					  double strafpreis = GUIRuecknahme.berechneStrafpreis();
					  statement = "update Kunden set strafpreis=strafpreis+" + strafpreis;
					  statement += " where kundennummer="+ kundennummer;
					  pst = con.prepareStatement(statement);
					  pst.executeUpdate();
					  statement = "update " + tabellenname + " set ausleihdatum=date_add(ausleihdatum,interval 1 week)";
					  statement += " where kundennummer=" + kundennummer;
					  statement += " and " + "DATEDIFF(UTC_DATE()," + tabellenname  + ".ausleihdatum)";
					  statement += " > " + tabellenname + ".leihdauer*7 and rueckgabedatum is null";
					  pst = con.prepareStatement(statement);
					  pst.executeUpdate();
				  }
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		// Kunden verwalten Fenster öffnen
		JButton btnKundenVerwalten = new JButton("Kunden verwalten");
		btnKundenVerwalten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIKundenVerwalten verwaltung = new GUIKundenVerwalten(false);
				verwaltung.getFrame().setVisible(true);
				try {
					if(con!= null) con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		btnKundenVerwalten.setBounds(24, 30, 226, 35);
		frame.getContentPane().add(btnKundenVerwalten);
		
		JButton btnVerleihen = new JButton("Verleihen");
		btnVerleihen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] zeilen = table.getSelectedRows();
				lblError.setVisible(false);
				if(zeilen.length > 0) {
					String[] artikelnummern = new String[zeilen.length];
					for(int i=0;i<zeilen.length;i++) {
						if(!table.getModel().getValueAt(zeilen[i], 3).toString().equals("Auf Lager")) {
							lblError.setVisible(true);
							return;
						}
						artikelnummern[i] = table.getModel().getValueAt(zeilen[i], 0).toString();
					}
					String kategorie = comboBoxKategorie.getSelectedItem().toString();
					GUIVerleihen g = new GUIVerleihen(artikelnummern, kategorie);
					g.getFrame().setVisible(true);
					try {
						if(con!= null) con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					frame.dispose();
				}
				
			}
		});
		btnVerleihen.setBounds(24, 226, 226, 35);
		frame.getContentPane().add(btnVerleihen);
		
		JButton btnRuecknahme = new JButton("Rücknahme");
		btnRuecknahme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(con != null) con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
				GUIRuecknahme medien = new GUIRuecknahme();
				medien.getFrame().setVisible(true);
			}
		});
		btnRuecknahme.setBounds(24, 273, 226, 35);
		frame.getContentPane().add(btnRuecknahme);
		
		JButton btnVerkaufen = new JButton("Verkaufen");
		btnVerkaufen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] zeilen = table.getSelectedRows();
				lblError.setVisible(false);
				if(zeilen.length > 0) {
					String[] artikelnummern = new String[zeilen.length];
					for(int i=0;i<zeilen.length;i++) {
						if(!table.getModel().getValueAt(zeilen[i], 3).toString().equals("Auf Lager")) {
							lblError.setVisible(true);
							return;
						}
						artikelnummern[i] = table.getModel().getValueAt(zeilen[i], 0).toString();
					}
					String kategorie = comboBoxKategorie.getSelectedItem().toString();
					GUIVerkaufen g = new GUIVerkaufen(artikelnummern, kategorie);
					g.getFrame().setVisible(true);
					try {
						if(con!= null) con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					frame.dispose();
				}
			}
		});
		btnVerkaufen.setBounds(24, 320, 226, 35);
		frame.getContentPane().add(btnVerkaufen);
		
		// Medien verwalten Fenster öffnen
		JButton btnMedienVerwalten = new JButton("Medien verwalten");
		btnMedienVerwalten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(con != null) con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
				GUIMedienVerwalten medien = new GUIMedienVerwalten(false, null, null);
				medien.getFrame().setVisible(true);
			}
		});
		btnMedienVerwalten.setBounds(24, 93, 226, 35);
		frame.getContentPane().add(btnMedienVerwalten);
		
		JScrollPane scrollPaneMedien = new JScrollPane();
		scrollPaneMedien.setBounds(298, 77, 982, 277);
		frame.getContentPane().add(scrollPaneMedien);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setIcon(new ImageIcon("/home/pascal/Software Engineering/Projekt/Diagramme/Filme.jpg"));
		lblNewLabel.setBounds(0, 0, 1300, 400);
		frame.getContentPane().add(lblNewLabel);
		
		// Feld zur Auswahl der Kategorie
		comboBoxKategorie = new JComboBox<>();
		comboBoxKategorie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kategorie = comboBoxKategorie.getSelectedItem().toString();
				lblNewLabel.setIcon(new ImageIcon("/home/pascal/Software Engineering/Projekt/Diagramme/" + kategorie + ".jpg"));
				try {
					PreparedStatement anz = con.prepareStatement("select count(*) from " + kategorie);
					ResultSet anzahl = anz.executeQuery();
					anzahl.next();
					int zeilenanzahl = anzahl.getInt(1);
					PreparedStatement pst = con.prepareStatement("select * from " + kategorie);
				    ResultSet rs = pst.executeQuery();
				    int spaltenanzahl;
				    if(kategorie.equals("Buecher")) {
				    	spaltenanzahl = columnNamesBuecher.length; 
				    } else if (kategorie.equals("Filme")){
				    	spaltenanzahl = columnNamesFilme.length; 
				    } else {
				    	spaltenanzahl = columnNamesVideospiele.length;
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
				    	table = new JTable(data, columnNamesFilme);
				    } else 	if(kategorie.equals("Buecher")) {
				    	table = new JTable(data, columnNamesBuecher);
				    } else {
				    	table = new JTable(data, columnNamesVideospiele);
				    }
				    // Listener, um Datenbank über Tabelle zu bearbeiten
					table.getModel().addTableModelListener(new TableModelListener() {
					      public void tableChanged(TableModelEvent e) {
					          int row = e.getFirstRow();
					          int column = e.getColumn();
					          String aenderung = table.getModel().getValueAt(row, column).toString(); 
					          String name;
					          try {
					        	  String sql = "update " + comboBoxKategorie.getSelectedItem().toString();
				        		  if(table.getColumnName(column) == "Art. Nr.") name = "Artikelnummer";
				        		  else if(table.getColumnName(column) == "Regie") name = "Regisseur";
				        		  else if(table.getColumnName(column) == "Status") {
				        			  if(aenderung.equals("Verliehen")) {
				        				  aenderung = "0";
				        			  } else if (aenderung.equals("Verkauft")) {
				        				  aenderung = "1";
				        			  } else if (aenderung.equals("Entsorgt")) {
				        				  aenderung = "2";
				        			  } else if (aenderung.equals("Auf Lager")) {
				        				  aenderung = "3";
				        			  }
				        			  name = "Status";
				        		  } else {
				        			  name = table.getColumnName(column);
				        		  }
				        		  sql += " set " + name + "=" +"\'" + aenderung + "\'";
				        		  sql += " where artikelnummer=" + "\'" + nummern.get(row) + "\'";
					        	  PreparedStatement pst = con.prepareStatement(sql);
					        	  pst.executeUpdate();
					        	  textFieldError.setForeground(Color.GREEN);
					        	  textFieldError.setText("Erfolg");
					          } catch(SQLException el) {
					        	  textFieldError.setForeground(Color.RED);
					        	  textFieldError.setText("Fehler");
					          }
					      }
					    });
					// Enthaelt Artikelnummern
					nummern = new ArrayList<>();
				    for(int i=0;i<zeilenanzahl;i++) {
				    	nummern.add(table.getModel().getValueAt(i, 0).toString());
				    }
					TableColumn column = null;
					// Suchen ermöglichen
					rowSorter = new TableRowSorter<>(table.getModel());
					table.setRowSorter(rowSorter);
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
		comboBoxKategorie.setBounds(565, 35, 200, 25);
		frame.getContentPane().add(comboBoxKategorie);
		
		// Feld, um in allen Spalten zu suchen
		textFieldSuche = new JTextField();
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
		textFieldSuche.setBounds(895, 35, 200, 25);
		frame.getContentPane().add(textFieldSuche);
		textFieldSuche.setColumns(10);
		
		JLabel lblSuche = new JLabel("Suche");
		lblSuche.setForeground(Color.WHITE);
		lblSuche.setBounds(840, 40, 43, 15);
		frame.getContentPane().add(lblSuche);
		
		textFieldError = new JTextField();
		textFieldError.setEditable(false);
		textFieldError.setColumns(10);
		textFieldError.setBounds(1146, 35, 100, 25);
		frame.getContentPane().add(textFieldError);
		
		lblError = new JLabel("Medium ist nicht auf Lager!");
		lblError.setVisible(false);
		lblError.setForeground(Color.RED);
		lblError.setBounds(42, 199, 208, 15);
		frame.getContentPane().add(lblError);
		
		// Öffnet Fenster für die Historie
		JButton buttonHistorie = new JButton("Historie anzeigen");
		buttonHistorie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(con != null) con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
				GUIHistorie historie = new GUIHistorie();
				historie.getFrame().setVisible(true);
			}
		});
		buttonHistorie.setBounds(24, 152, 226, 35);
		frame.getContentPane().add(buttonHistorie);
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
			if(con != null) {
				this.con.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public JTextField getError() {
		return textFieldError;
	}
}


