package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
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
import javax.swing.JLayeredPane;
import javax.swing.JComboBox;

public class GUIHistorie implements WindowListener{

	private JFrame frame;
	public static Connection con;
	private JTable table;
	private JTextField textFieldSuche;
	private JComboBox<String> comboBoxKategorie;
	private JScrollPane scrollPaneMedien;
	private JButton btnAnzeigen;
	private JTable medien;
	public static String kundennummer;
	public static String kategorie;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIHistorie window = new GUIHistorie();
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
	public GUIHistorie() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Historie");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-1300)/2, (screenSize.height-400)/2, 1300, 400);
		frame.getLayeredPane().setLayout(null);
		frame.addWindowListener(this);
		frame.setLayeredPane(new JLayeredPane());
		GUIHistorie.con = SimpleQuery.connect(); // Verbindung zur Datenbank aufbauen
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/home/pascal/Software Engineering/Projekt/Diagramme/Historie.jpg"));
		lblNewLabel.setBounds(0, 0, 1300, 375);
		frame.getLayeredPane().add(lblNewLabel);
		
		JLabel lblNewLabel2 = new JLabel("");
		lblNewLabel2.setIcon(new ImageIcon("/home/pascal/Software Engineering/Projekt/Diagramme/Historie.jpg"));
		lblNewLabel2.setBounds(650, 0, 1300, 375);
		frame.getLayeredPane().add(lblNewLabel2);
		
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
		frame.getLayeredPane().add(btnFertigStellen, new Integer(1));
		
		JScrollPane scrollPaneKunden = new JScrollPane();
		scrollPaneKunden.setBounds(263, 56, 320, 226);
		frame.getLayeredPane().add(scrollPaneKunden, new Integer(1));
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
		lblSuche.setForeground(Color.WHITE);
		lblSuche.setBounds(386, 17, 70, 15);
		frame.getLayeredPane().add(lblSuche, new Integer(1));
		
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
		frame.getLayeredPane().add(textFieldSuche, new Integer(1));
		textFieldSuche.setColumns(10);
		
		comboBoxKategorie = new JComboBox<>();
		comboBoxKategorie.setBounds(91, 29, 117, 25);
		frame.getLayeredPane().add(comboBoxKategorie, new Integer(1));
		comboBoxKategorie.addItem("Filme");
		comboBoxKategorie.addItem("Buecher");
		comboBoxKategorie.addItem("Videospiele");
		
		JLabel lblDauer = new JLabel("Kategorie");
		lblDauer.setBounds(12, 34, 70, 15);
		frame.getLayeredPane().add(lblDauer);
		
		scrollPaneMedien = new JScrollPane();
		scrollPaneMedien.setBounds(595, 56, 685, 226);
		frame.getLayeredPane().add(scrollPaneMedien, new Integer(1));
		
		// Button, um ausgeliehene Medien eines Kunden anzuzeigen
		btnAnzeigen = new JButton("Anzeigen");
		btnAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		          int row = table.getSelectedRow();
		          if(row != -1) {
			          try {
			        	  kategorie = comboBoxKategorie.getSelectedItem().toString();
			        	  String tabellenname = "Historie" + kategorie;
			        	  String statement = "select " + kategorie + ".artikelnummer,"  + kategorie + ".erscheinungsdatum, "   + kategorie + ".titel,";
			        	  statement += tabellenname + ".ausleihdatum,DATEDIFF(IFNULL(" + tabellenname + ".rueckgabedatum,UTC_DATE())," +  tabellenname + ".ausleihdatum)," +  tabellenname + ".rueckgabedatum";
			        	  statement += " from " + kategorie + " join " + tabellenname + " on " + kategorie + ".artikelnummer=" + tabellenname + ".artikelnummer ";
			        	  statement += " where kundennummer=" + table.getModel().getValueAt(row, 0);
			        	  statement += " order by " + tabellenname + ".ausleihdatum";
			        	  PreparedStatement pst = con.prepareStatement(statement);
			        	  ResultSet rs = pst.executeQuery();
			        	  while(!rs.isLast() && rs.next());
			        	  int zeilenanzahl = rs.getRow();
			        	  rs.beforeFirst();
			        	  String data[][];
			        	  if(zeilenanzahl > 0) {
			        		  data = new String[zeilenanzahl][GUIMain.columnNamesHistorie.length];
				  		      int indexA = 0;
				  		      while(rs.next()) {
				  		    	  	for(int i=1;i<GUIMain.columnNamesHistorie.length;i++) {
				  		    	  		if(i == 5) {
				  		    	  			String temp = rs.getString(i);
				  		    	  			if( temp == null) {
				  		    	  				data[indexA][GUIMain.columnNamesHistorie.length-1] = "Verliehen";
				  		    	  				data[indexA][i-1] = rs.getString(i) + " Tage";
				  		    	  			} else if(Integer.parseInt(temp) > 100000){
				  		    	  				data[indexA][GUIMain.columnNamesHistorie.length-1] = "Verkauft";
				  		    	  				data[indexA][i-1] = "Unendlich";
				  		    	  				i++;
				  		    	  				data[indexA][i-1] = "Nie";
				  		    	  			} else {
				  		    	  			data[indexA][GUIMain.columnNamesHistorie.length-1] = "Zurückgegeben";
			  		    	  				data[indexA][i-1] = rs.getString(i) + " Tage";
				  		    	  			}
				  		    	  		} else data[indexA][i-1] = rs.getString(i);
				  		    	  		
				  		    	  	}
				  		    		indexA++;
				  		      }
					  		  medien = new JTable(data,GUIMain.columnNamesHistorie);
					  		  scrollPaneMedien.setViewportView(medien);

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
		frame.getLayeredPane().add(btnAnzeigen, new Integer(1));
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
				GUIRuecknahme.con.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		GUIMain g = new GUIMain();
		g.getFrame().setVisible(true);
	}
}
