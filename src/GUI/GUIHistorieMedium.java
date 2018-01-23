package GUI;


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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import java.awt.Color;
import java.awt.Font;

public class GUIHistorieMedium implements WindowListener{

	private JFrame frame;
	public static Connection con;
	private JTextField textFieldSuche;
	private JScrollPane scrollPaneMedien;
	private JTable medien;
	public static String artikelnummer;
	public static String kategorie;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIHistorieMedium window = new GUIHistorieMedium();
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
	public GUIHistorieMedium() {
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
		GUIHistorieMedium.con = SimpleQuery.connect(); // Verbindung zur Datenbank aufbauen
		
		JLabel lblHistorie = new JLabel("Keine Historie vorhanden");
		lblHistorie.setVisible(false);
		lblHistorie.setFont(new Font("Dialog", Font.BOLD, 30));
		lblHistorie.setForeground(Color.RED);
		lblHistorie.setBounds(436, 8, 484, 36);
		frame.getLayeredPane().add(lblHistorie);
		
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
					GUIMedienVerwalten g = new GUIMedienVerwalten(false, null, null);
					g.getFrame().setVisible(true);
			}
		});
		btnFertigStellen.setBounds(527, 316, 200, 25);
		frame.getLayeredPane().add(btnFertigStellen);
		
	
		
		JLabel lblSuche = new JLabel("Suche");
		lblSuche.setForeground(Color.WHITE);
		lblSuche.setBounds(386, 17, 70, 15);
		frame.getLayeredPane().add(lblSuche);
		
		scrollPaneMedien = new JScrollPane();
		scrollPaneMedien.setBounds(27, 56, 1253, 226);
		frame.getLayeredPane().add(scrollPaneMedien, new Integer(1));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/home/pascal/Software Engineering/Projekt/Diagramme/Historie.jpg"));
		lblNewLabel.setBounds(0, 0, 1300, 375);
		frame.getLayeredPane().add(lblNewLabel);
		
		JLabel lblNewLabel2 = new JLabel("");
		lblNewLabel2.setIcon(new ImageIcon("/home/pascal/Software Engineering/Projekt/Diagramme/Historie.jpg"));
		lblNewLabel2.setBounds(650, 0, 1300, 375);
		frame.getLayeredPane().add(lblNewLabel2);
		

		
	// Baut Historie für ausgewähltes Exemplar auf
		try {
			  String tabellenname = "Historie" + kategorie;
			  String statement = "select " + tabellenname + ".kundennummer,"  + kategorie + ".erscheinungsdatum, "   + kategorie + ".titel,";
			  statement += tabellenname + ".ausleihdatum,DATEDIFF(IFNULL(" + tabellenname + ".rueckgabedatum,UTC_DATE())," +  tabellenname + ".ausleihdatum)," +  tabellenname + ".rueckgabedatum";
			  statement += " from " + kategorie + " join " + tabellenname + " on " + kategorie + ".artikelnummer=" + tabellenname + ".artikelnummer ";
			  statement += " where " + kategorie + ".artikelnummer=" + artikelnummer;
			  statement += " order by " + tabellenname + ".ausleihdatum";
			  PreparedStatement pst = con.prepareStatement(statement);
			  ResultSet rs = pst.executeQuery();
			  while(!rs.isLast() && rs.next());
			  int zeilenanzahl = rs.getRow();
			  rs.beforeFirst();
			  String data[][];
			  if(zeilenanzahl != 0) {
				  data = new String[zeilenanzahl][GUIMain.columnNamesHistorieMedium.length];
			      int indexA = 0;
			      while(rs.next()) {
			    	  	for(int i=1;i<GUIMain.columnNamesHistorieMedium.length;i++) {
			    	  		if(i == 5) {
			    	  			String temp = rs.getString(i);
			    	  			if( temp == null) {
			    	  				data[indexA][GUIMain.columnNamesHistorieMedium.length-1] = "Verliehen";
			    	  				data[indexA][i-1] = rs.getString(i) + " Tage";
			    	  			} else if(Integer.parseInt(temp) > 100000){
			    	  				data[indexA][GUIMain.columnNamesHistorieMedium.length-1] = "Verkauft";
			    	  				data[indexA][i-1] = "Unendlich";
			    	  				i++;
			    	  				data[indexA][i-1] = "Nie";
			    	  			} else {
			    	  				data[indexA][GUIMain.columnNamesHistorieMedium.length-1] = "Zurückgegeben";
			    	  				data[indexA][i-1] = rs.getString(i) + " Tage";
				  			}
				  		} else {
				  			data[indexA][i-1] = rs.getString(i);
				  		}
				  	}
				    	indexA++;
			      }
				  medien = new JTable(data,GUIMain.columnNamesHistorieMedium);
				  scrollPaneMedien.setViewportView(medien);
			  } else {
				  lblHistorie.setVisible(true);
				  lblSuche.setVisible(false);
			  }
		} catch (SQLException e1) {
			  e1.printStackTrace();
		}

		
		// Feld, um in allen Spalten zu suchen
		if(medien != null) {
			textFieldSuche = new JTextField();
			TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(medien.getModel());
			medien.setRowSorter(rowSorter);
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
		}
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
		GUIMedienVerwalten g = new GUIMedienVerwalten(false,null,null);
		g.getFrame().setVisible(true);
	}
}
