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
import javax.swing.JPasswordField;

public class GUIKundenansicht implements WindowListener{

	private JFrame frame;
	private Connection con;
	private JTable table;
	private JTextField textFieldSuche;
	private JPasswordField passwordField;
	private String[] artikelnummern;
	private String kategorie;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIKundenansicht window = new GUIKundenansicht(null,null);
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
	public GUIKundenansicht(String[] artikelnummern,String kategorie) {
		this.artikelnummern = artikelnummern;
		this.kategorie = kategorie;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Bildschirmdimensionen in Pixeln holen
	    frame.setBounds((screenSize.width-1300)/2, (screenSize.height-400)/2, 1300, 400);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(this);
		this.con = SimpleQuery.connect(); // Verbindung zur Datenbank aufbauen
		
		// Schließt den Leihvorgang ab
		JButton btnFertigStellen = new JButton("Fertig Stellen");
		btnFertigStellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() != -1) {
					String passwort = String.valueOf(passwordField.getPassword());
					String statement = "select passwort from Kunden where kundennummer=" + table.getModel().getValueAt(table.getSelectedRow(), 0);
					statement += " and passwort=PASSWORD(\'" + passwort + "\')";
					try {
						PreparedStatement pst = con.prepareStatement(statement);
						ResultSet rs = pst.executeQuery();
						if(rs.next()) {
							String upd = "update " + kategorie + " set status=0 where";
							for(int i=0;i<artikelnummern.length;i++) {
								upd += " artikelnummer=" + artikelnummern[i];
								if(i < artikelnummern.length-1) upd += " or";
							}
							PreparedStatement pst2 = con.prepareStatement(upd); // TODO Historie
							pst2.executeUpdate();
						} else {
							return;
						}
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
			}
		});
		btnFertigStellen.setBounds(29, 317, 200, 25);
		frame.getContentPane().add(btnFertigStellen);
		
		JScrollPane scrollPaneKunden = new JScrollPane();
		scrollPaneKunden.setBounds(242, 56, 1038, 226);
		frame.getContentPane().add(scrollPaneKunden);
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
		
		passwordField = new JPasswordField();
		passwordField.setBounds(91, 99, 117, 25);
		frame.getContentPane().add(passwordField);
		
		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(116, 72, 70, 15);
		frame.getContentPane().add(lblPasswort);
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
