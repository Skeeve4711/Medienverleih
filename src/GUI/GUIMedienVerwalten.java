package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class GUIMedienVerwalten implements WindowListener{

	private String nummer;
	private String kategorie;
	private boolean bestaetigt;
	private ArrayList<String> nummern;
	private TableRowSorter<TableModel> rowSorter;
	private JLabel lblLager;
	
	private JFrame frame;
	private JTable table;
	private Connection con;
	private JComboBox<String> comboBoxKategorie;
	private JTextField textFieldError;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMedienVerwalten window = new GUIMedienVerwalten(false, null, null);
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
	public GUIMedienVerwalten(boolean bestaetigt, String nummer, String kategorie) {
		this.bestaetigt = bestaetigt;
		this.nummer = nummer;
		this.kategorie = kategorie;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Medien verwalten");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-1300)/2, (screenSize.height-400)/2, 1300, 400);
		frame.getLayeredPane().setLayout(null);
		frame.addWindowListener(this);
		frame.setLayeredPane(new JLayeredPane());
		con = SimpleQuery.connect();
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/home/pascal/Software Engineering/Projekt/Diagramme/Filme.jpg"));
		lblNewLabel.setBounds(0, 0, 1300, 400);
		frame.getLayeredPane().add(lblNewLabel);
		
		JLabel lblNewLabel2 = new JLabel("");
		lblNewLabel2.setIcon(new ImageIcon("/home/pascal/Software Engineering/Projekt/Diagramme/Wurm.jpg"));
		lblNewLabel2.setBounds(1225, 0, 75, 64);
		frame.getLayeredPane().add(lblNewLabel2, new Integer(1));
		
		lblLager = new JLabel("Nicht auf Lager!");
		lblLager.setForeground(Color.RED);
		lblLager.setBounds(57, 261, 149, 15);
		lblLager.setVisible(false);
		frame.getLayeredPane().add(lblLager, new Integer(1));
		
		// Medium Hinzufuegen Fenster oeffnen
		JButton btnHinzufuegen = new JButton("Hinzufügen");
		btnHinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				frame.dispose();
				GUIMedienHinzufuegen hinzufuegen = new GUIMedienHinzufuegen(false, null, null);
				hinzufuegen.getFrame().setVisible(true);
			}
		});
		btnHinzufuegen.setBounds(24, 30, 226, 35);
		frame.getLayeredPane().add(btnHinzufuegen, new Integer(1));
		
		// Medium entfernen und Bestaetigung abfragen
		JButton btnAusschliessen = new JButton("Ausschließen");
		btnAusschliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int zeile = table.getSelectedRow();
						if(zeile != -1) {
							if(table.getModel().getValueAt(zeile, 3).equals("Auf Lager")) {
								frame.dispose();
								try {
									if(con != null) {
										con.close();
									}
								} catch (SQLException ex) {
									ex.printStackTrace();
								}
								String kategorie = comboBoxKategorie.getSelectedItem().toString();
								String nummer = table.getModel().getValueAt(zeile, 0).toString();
								GUIBestaetigenMedienVerwalten bestaetigung = new GUIBestaetigenMedienVerwalten(nummer, kategorie);
								bestaetigung.getFrame().setVisible(true);
						} else {
							lblLager.setVisible(true);
						}
					}
			}
		});
		btnAusschliessen.setBounds(24, 87, 226, 35);
		frame.getLayeredPane().add(btnAusschliessen, new Integer(1));
		
		// Ausgewähltes Medium bearbeiten
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
					String kategorie = comboBoxKategorie.getSelectedItem().toString();
					GUIMedienHinzufuegen aendern = new GUIMedienHinzufuegen(true, nummer, kategorie);
					aendern.getFrame().setVisible(true);
					frame.dispose();
				}
			}
		});
		btnaendern.setBounds(24, 144, 226, 35);
		frame.getLayeredPane().add(btnaendern, new Integer(1));
		
		JScrollPane scrollPaneMedien = new JScrollPane();
		scrollPaneMedien.setBounds(290, 87, 978, 245);
		frame.getLayeredPane().add(scrollPaneMedien, new Integer(1));
		

		
		// Medium ausschliessen
		if(this.bestaetigt) {
			String sql = "update " + kategorie + " set status=2 where artikelnummer=" + this.nummer;
			try {
				PreparedStatement pst = con.prepareStatement(sql);
				pst.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		// Richtige Daten anzeigen
		comboBoxKategorie = new JComboBox<>();
		comboBoxKategorie.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				String kategorie = comboBoxKategorie.getSelectedItem().toString();
				lblNewLabel.setIcon(new ImageIcon("/home/pascal/Software Engineering/Projekt/Diagramme/" + kategorie + ".jpg"));
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
			        			  if( aenderung.equals("Verliehen")) {
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
			    // Suchen ermöglichen
				rowSorter = new TableRowSorter<>(table.getModel());
				table.setRowSorter(rowSorter);
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
		frame.getLayeredPane().add(comboBoxKategorie, new Integer(1));
		
		// Fenster schliessen und zum Hauptfenster zurückkehren
		JButton btnFertigStellen = new JButton("Fertig stellen");
		btnFertigStellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowClosing(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
			}
		});
		btnFertigStellen.setBounds(24, 297, 226, 35);
		frame.getLayeredPane().add(btnFertigStellen, new Integer(1));
		
		// Feld, um in allen Spalten zu suchen
		JTextField textFieldSuche = new JTextField();
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
		textFieldSuche.setBounds(678, 35, 200, 25);
		frame.getLayeredPane().add(textFieldSuche, new Integer(1));
		textFieldSuche.setColumns(10);
		
		JTextField lblSuche = new JTextField("Suche");
		lblSuche.setEditable(false);
		lblSuche.setForeground(Color.BLACK);
		lblSuche.setBounds(607, 40, 45, 15);
		frame.getLayeredPane().add(lblSuche, new Integer(1));
		
		textFieldError = new JTextField();
		textFieldError.setEditable(false);
		textFieldError.setBounds(913, 35, 100, 25);
		frame.getLayeredPane().add(textFieldError);
		textFieldError.setColumns(10);
		
		JButton buttonHistorie = new JButton("Historie anzeigen");
		buttonHistorie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int zeile = table.getSelectedRow();
				if(zeile != -1) {
					GUIHistorieMedium.artikelnummer = table.getModel().getValueAt(zeile, 0).toString();
					GUIHistorieMedium.kategorie = comboBoxKategorie.getSelectedItem().toString();
					try {
						con.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					GUIHistorieMedium historie = new GUIHistorieMedium();
					historie.getFrame().setVisible(true);
					frame.dispose();
				}
				
			}
		});
		buttonHistorie.setBounds(24, 200, 226, 35);
		frame.getLayeredPane().add(buttonHistorie, new Integer(1));
		

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
		try {
			if(con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		oberflaeche.getFrame().setVisible(true);
	}
}