package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class GUIRuecknahme implements WindowListener{

	private JFrame frame;
	public static Connection con;
	private JTable table;
	private JTextField textFieldSuche;
	private JComboBox<String> comboBoxKategorie;
	private JScrollPane scrollPaneMedien;
	private JButton btnAnzeigen;
	private JButton btnRuecknahme;
	private JTable medien;
	public static String kundennummer;
	public static String kategorie;
	private JLabel lblStrafpreis;
	private JTextField textFieldStrafpreis;
	private JLabel lblLeihpreis;
	private JTextField textFieldLeihpreis;
	private JButton btnBerechneGestamtleihpreis;
	private JRadioButton rdbtnStrafpreisBezahlt;
	private JLabel lblGesamt;
	private JTextField textFieldGesamt;
	private int [] zeilen;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIRuecknahme window = new GUIRuecknahme();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application
	 */
	public GUIRuecknahme() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Rückgabe");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-1300)/2, (screenSize.height-400)/2, 1300, 400);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(this);
		this.con = SimpleQuery.connect(); // Verbindung zur Datenbank aufbauen
		
		// Schließt das Fenster
		JButton btnFertigStellen = new JButton("Schliessen");
		btnFertigStellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						if(con != null) {
							con.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					frame.dispose();
					GUIMain g = new GUIMain();
					g.getFrame().setVisible(true);
		        	g.getError().setForeground(Color.GREEN);;
		        	g.getError().setText("Erfolg");
			}
		});
		btnFertigStellen.setBounds(501, 315, 200, 25);
		frame.getContentPane().add(btnFertigStellen);
		
		JScrollPane scrollPaneKunden = new JScrollPane();
		scrollPaneKunden.setBounds(263, 56, 320, 226);
		frame.getContentPane().add(scrollPaneKunden);
		try {
			PreparedStatement anz = con.prepareStatement("select count(*) from Kunden");
			ResultSet anzahl = anz.executeQuery();
			anzahl.next();
			int zeilenanzahl = anzahl.getInt(1);
			PreparedStatement pst = con.prepareStatement("select kundennummer,vorname,nachname from Kunden");
		    ResultSet rs = pst.executeQuery();
		    int spaltenanzahl = 3;
		    String data[][] = new String[zeilenanzahl][spaltenanzahl];
		    int indexA = 0;
		    while(rs.next()) {
			    for(int i=1;i<spaltenanzahl+1;i++) {
			    	data[indexA][i-1] = rs.getString(i);
			    }
		    	indexA++;
		    }	
			TableColumn column = null;
			String spaltenKunden[] = {"Kunden Nr.","Vorname","Nachname"};
			table = new JTable(data,spaltenKunden);
			for (int i = 0; i < spaltenanzahl; i++) {
			    column = table.getColumnModel().getColumn(i);
			    column.setPreferredWidth(120);
			}
			scrollPaneKunden.setViewportView(table);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		JLabel lblSuche = new JLabel("Suche");
		lblSuche.setBounds(386, 17, 70, 15);
		frame.getContentPane().add(lblSuche);
		
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
		textFieldSuche.setBounds(453, 12, 200, 25);
		frame.getContentPane().add(textFieldSuche);
		textFieldSuche.setColumns(10);
		
		comboBoxKategorie = new JComboBox<>();
		comboBoxKategorie.setBounds(91, 29, 117, 25);
		frame.getContentPane().add(comboBoxKategorie);
		comboBoxKategorie.addItem("Filme");
		comboBoxKategorie.addItem("Buecher");
		comboBoxKategorie.addItem("Videospiele");
		
		JLabel lblDauer = new JLabel("Kategorie");
		lblDauer.setBounds(12, 34, 70, 15);
		frame.getContentPane().add(lblDauer);
		
		scrollPaneMedien = new JScrollPane();
		scrollPaneMedien.setBounds(616, 56, 376, 226);
		frame.getContentPane().add(scrollPaneMedien);
		
		// Button, um ausgeliehene Medien eines Kunden anzuzeigen
		btnAnzeigen = new JButton("Anzeigen");
		btnAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		          int row = table.getSelectedRow();
		          if(row != -1) {
			          try {
			        	  kategorie = comboBoxKategorie.getSelectedItem().toString();
			        	  String tabellenname = "Historie" + kategorie;
			        	  String statement = "select " + kategorie + ".artikelnummer,"  + kategorie + ".titel, "   + kategorie + ".leihpreis";
			        	  statement += "*" + "CEIL(DATEDIFF(UTC_DATE()," + tabellenname  + ".ausleihdatum)/7)";
			        	  statement += " from " + kategorie;
			        	  statement += " join " + tabellenname;
			        	  statement += " on " + kategorie + ".artikelnummer=";
			        	  statement += tabellenname + ".artikelnummer";
			        	  kundennummer = table.getModel().getValueAt(row, 0).toString();
			        	  statement += " where " + tabellenname + ".kundennummer=" + kundennummer;
			        	  statement += " and rueckgabedatum is null";
			        	  PreparedStatement pst = con.prepareStatement(statement);
			        	  ResultSet rs = pst.executeQuery();
			        	  while(!rs.isLast() && rs.next());
			        	  int zeilenanzahl = rs.getRow();
			        	  String [] spalten = {"Art. Nr.", "Titel", "Leihpreis"};
			        	  rs.beforeFirst();
			        	  String data[][];
			        	  if(zeilenanzahl > 0) {
			        		  data = new String[zeilenanzahl][3];
				  		      int indexA = 0;
				  		      while(rs.next()) {
				  		    	  	data[indexA][0] = rs.getString(1);
				  		    	  	data[indexA][1] = rs.getString(2);
				  		    	  	data[indexA][2] = rs.getString(3);
				  		    		indexA++;
				  		      }
					  		  medien = new JTable(data,spalten);
					  		  scrollPaneMedien.setViewportView(medien);
					  		  double strafpreis = berechneStrafpreis();
					  		  textFieldStrafpreis.setText(String.valueOf(strafpreis));
					  		  statement = "update Kunden set strafpreis=" + strafpreis;
					  		  statement += " where kundennummer="+ kundennummer;
					  		  pst = con.prepareStatement(statement);
					  		  pst.executeUpdate();
			  		      } else {
			  		    	  scrollPaneMedien.setViewportView(null);
			  		      }
			          } catch(SQLException el) {
			        	  el.printStackTrace();
			          }
		          }
			}
		});
		btnAnzeigen.setBounds(29, 66, 200, 25);
		frame.getContentPane().add(btnAnzeigen);
		
		JRadioButton rdbtnLeihpreisBezahlt = new JRadioButton("Leihpreis bezahlt");
		rdbtnLeihpreisBezahlt.setBounds(59, 147, 149, 23);
		frame.getContentPane().add(rdbtnLeihpreisBezahlt);
		
		btnRuecknahme = new JButton("Bezahlen");
		btnRuecknahme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldGesamt.getText().isEmpty()) return;
				if(medien != null) {
					zeilen = medien.getSelectedRows();
					boolean strafpreis = rdbtnStrafpreisBezahlt.isSelected();
					boolean leihpreis = rdbtnLeihpreisBezahlt.isSelected();
					if(strafpreis && !leihpreis) {
						String statement = "update Kunden set strafpreis=0 where kundennummer=" + kundennummer;
						try {
							PreparedStatement pst = con.prepareStatement(statement);
							pst.executeUpdate();
							textFieldStrafpreis.setText("0");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						return;
					}
					if(zeilen.length != 0) {
						if(leihpreis && strafpreis) {
							textFieldStrafpreis.setText("0");
							textFieldLeihpreis.setText("0");
							textFieldGesamt.setText("0");
							String statement = "update Kunden set strafpreis=0 where kundennummer=" + kundennummer;
							try {
								PreparedStatement pst = con.prepareStatement(statement);
								pst.executeUpdate();
								textFieldStrafpreis.setText("0");
								statement = "update " + kategorie + " set " + "status=3" + " where ";
								for(int i=0; i<zeilen.length;i++) {
									statement += " artikelnummer=" + medien.getModel().getValueAt(zeilen[i], 0).toString();
									if(i < zeilen.length-1) statement += " or ";
								}
								pst = con.prepareStatement(statement);
								pst.executeUpdate();
								statement = "update Historie" + kategorie + " set rueckgabedatum=UTC_DATE() where ";
								statement += "kundennummer=" + kundennummer + " and rueckgabedatum is null and ";
								statement += "artikelnummer in(";
								for(int i=0;i<zeilen.length;i++) {
									statement += medien.getModel().getValueAt(zeilen[i], 0).toString();
									if(i < zeilen.length-1) statement += ",";
								}
								statement += ")";
								pst = con.prepareStatement(statement);
								pst.executeUpdate();
								btnAnzeigen.doClick();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						} else if(leihpreis && !strafpreis) {
							textFieldLeihpreis.setText("0");
							textFieldGesamt.setText(textFieldStrafpreis.getText());
							try {
								PreparedStatement pst;
								String statement;
								statement = "update " + kategorie + " set " + "status=3" + " where ";
								for(int i=0; i<zeilen.length;i++) {
									statement += " artikelnummer=" + medien.getModel().getValueAt(zeilen[i], 0).toString();
									if(i < zeilen.length-1) statement += " or ";
								}
								pst = con.prepareStatement(statement);
								pst.executeUpdate();
								statement = "update Historie" + kategorie + " set rueckgabedatum=UTC_DATE() where ";
								statement += "kundennummer=" + kundennummer + " and rueckgabedatum is null and ";
								statement += "artikelnummer in(";
								for(int i=0;i<zeilen.length;i++) {
									statement += medien.getModel().getValueAt(zeilen[i], 0).toString();
									if(i < zeilen.length-1) statement += ",";
								}
								statement += ")";
								pst = con.prepareStatement(statement);
								pst.executeUpdate();
								btnAnzeigen.doClick();
							} catch(SQLException el) {
								el.printStackTrace();
							}
						} else {
							try {
								String statement = "update Kunden set strafpreis=strafpreis+" + textFieldLeihpreis.getText().toString();
								statement += " where kundennummer=" + kundennummer;
								PreparedStatement pst;
								pst = con.prepareStatement(statement);
								pst.executeUpdate();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						textFieldLeihpreis.setText("");
						textFieldStrafpreis.setText("");
						textFieldGesamt.setText("");
					}
				}
			}
		});
		btnRuecknahme.setBounds(29, 207, 200, 25);
		frame.getContentPane().add(btnRuecknahme);
		
		lblStrafpreis = new JLabel("Strafpreis");
		lblStrafpreis.setBounds(12, 256, 85, 15);
		frame.getContentPane().add(lblStrafpreis);
		
		textFieldStrafpreis = new JTextField();
		textFieldStrafpreis.setEditable(false);
		textFieldStrafpreis.setBounds(136, 254, 114, 19);
		frame.getContentPane().add(textFieldStrafpreis);
		textFieldStrafpreis.setColumns(10);
		
		lblLeihpreis = new JLabel("Gesamtleihpreis");
		lblLeihpreis.setBounds(12, 290, 139, 15);
		frame.getContentPane().add(lblLeihpreis);
		
		textFieldLeihpreis = new JTextField();
		textFieldLeihpreis.setEditable(false);
		textFieldLeihpreis.setColumns(10);
		textFieldLeihpreis.setBounds(136, 288, 114, 19);
		frame.getContentPane().add(textFieldLeihpreis);
		
		btnBerechneGestamtleihpreis = new JButton("Gestamtleihpreis");
		btnBerechneGestamtleihpreis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(medien != null) {
					int [] zeilen = medien.getSelectedRows();
					if(zeilen.length != 0) {
						double leihpreis=0;
						for(int i=0;i<zeilen.length;i++) {
							leihpreis += Double.parseDouble(medien.getModel().getValueAt(zeilen[i], 2).toString());
						}
						textFieldLeihpreis.setText(String.valueOf(leihpreis));
						double gesamt = leihpreis + Double.parseDouble(textFieldStrafpreis.getText());
						textFieldGesamt.setText(String.valueOf(gesamt));
					}
				}
			}
		});
		btnBerechneGestamtleihpreis.setBounds(29, 114, 200, 25);
		frame.getContentPane().add(btnBerechneGestamtleihpreis);
		
		rdbtnStrafpreisBezahlt = new JRadioButton("Strafpreis bezahlt");
		rdbtnStrafpreisBezahlt.setBounds(59, 176, 170, 23);
		frame.getContentPane().add(rdbtnStrafpreisBezahlt);
		
		lblGesamt = new JLabel("Gesamt");
		lblGesamt.setBounds(12, 325, 139, 15);
		frame.getContentPane().add(lblGesamt);
		
		textFieldGesamt = new JTextField();
		textFieldGesamt.setEditable(false);
		textFieldGesamt.setColumns(10);
		textFieldGesamt.setBounds(136, 321, 114, 19);
		frame.getContentPane().add(textFieldGesamt);
	}
	
	// Berechnet Strafpreis für aktuellen Kunden
	public static double berechneStrafpreis() {
		double strafpreis = 0;
    	String tabellenname = "Historie" + kategorie;
    	String statement = "select " + kategorie + ".kaufpreis," + kategorie + ".artikelnummer";
    	statement += " from " + kategorie;
    	statement += " join " + tabellenname;
    	statement += " on " + kategorie + ".artikelnummer=";
    	statement += tabellenname + ".artikelnummer";
    	statement += " where " + tabellenname + ".kundennummer=" + kundennummer;
    	statement += " and " + "DATEDIFF(UTC_DATE()," + tabellenname  + ".ausleihdatum)";
    	statement += " > " + tabellenname + ".leihdauer*7 and rueckgabedatum is null";
    	try {
    		PreparedStatement pst = con.prepareStatement(statement);
    		ResultSet rs = pst.executeQuery();
    		boolean digital = false;
    		if(kategorie.equals("Filme") || kategorie.equals("Videospiele")) digital = true;
    		while(rs.next()) {
    			if(digital) {
    				strafpreis += GUIMain.STRAFPREISDIGITAL * rs.getDouble(1);
    			} else {
    				strafpreis += GUIMain.STRAFPREISBUECHER * rs.getDouble(1);
    			}
    		}
    		
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
		return strafpreis;
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
		GUIMain g = new GUIMain();
		g.getFrame().setVisible(true);
	}
}
