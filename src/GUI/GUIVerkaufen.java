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

public class GUIVerkaufen implements WindowListener{

	private JFrame frame;
	private Connection con;
	private JTable table;
	private JTextField textFieldSuche;
	private JPasswordField passwordField;
	private String[] artikelnummern;
	private String kategorie;
	private JLabel lblAlter;
	private JLabel lblStrafpreis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIVerkaufen window = new GUIVerkaufen(null,null);
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
	public GUIVerkaufen(String[] artikelnummern,String kategorie) {
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
		
		// Schließt den Kaufvorgang ab
		JButton btnFertigStellen = new JButton("Fertig Stellen");
		btnFertigStellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() != -1) {
					String passwort = String.valueOf(passwordField.getPassword());
					String kundennummer = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
					String statement = "select passwort from Kunden where kundennummer=" + kundennummer;
					statement += " and passwort=PASSWORD(\'" + passwort + "\')";
					try {
						String sql = "select `alter` from Kunden where kundennummer=" + kundennummer;
						PreparedStatement pst = con.prepareStatement(sql);
						ResultSet rs = pst.executeQuery();
						rs.next();
						int alter = rs.getInt(1);
						sql = "select altersfreigabe from " + kategorie + " where artikelnummer in(";
						for(int i=0;i<artikelnummern.length;i++) {
							sql += artikelnummern[i];
							if(i < artikelnummern.length-1) sql += ",";
						}
						sql += ")";
						pst = con.prepareStatement(sql);
						boolean fehler = false;
						rs = pst.executeQuery();
						while(rs.next()) {
							if(rs.getInt(1) > alter) {
								fehler = true;
								lblAlter.setVisible(true);
							}
						}
						sql = "select strafpreis from Kunden where kundennummer=" + kundennummer;
						pst = con.prepareStatement(sql);
						rs = pst.executeQuery();
						rs.next();
						if(rs.getDouble(1) > 0) {
							fehler = true;
							lblStrafpreis.setVisible(true);
						}
						if(fehler) return;
						pst = con.prepareStatement(statement);
						rs = pst.executeQuery();
						if(rs.next()) {
							String upd = "update " + kategorie + " set status=1 where";
							for(int i=0;i<artikelnummern.length;i++) {
								upd += " artikelnummer=" + artikelnummern[i];
								if(i < artikelnummern.length-1) upd += " or";
							}
							PreparedStatement pst2 = con.prepareStatement(upd);
							pst2.executeUpdate();
							String historie = "insert into Historie" + kategorie + " values";
							for(int i=0;i<artikelnummern.length;i++) {
								historie += " (" + kundennummer + "," + artikelnummern[i];
								historie += ",UTC_DATE(),-1,2500.1.1)";
								if(i < artikelnummern.length -1) historie += ",";
							}
							PreparedStatement pst3 = con.prepareStatement(historie);
							pst3.executeUpdate();
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
		lblPasswort.setBounds(12, 104, 70, 15);
		frame.getContentPane().add(lblPasswort);
		
		lblAlter = new JLabel("Nicht alt genug!");
		lblAlter.setVisible(false);
		lblAlter.setForeground(Color.RED);
		lblAlter.setBounds(51, 232, 127, 15);
		frame.getContentPane().add(lblAlter);
		
		lblStrafpreis = new JLabel("Strafpreis vorhanden");
		lblStrafpreis.setVisible(false);
		lblStrafpreis.setForeground(Color.RED);
		lblStrafpreis.setBounds(51, 267, 157, 15);
		frame.getContentPane().add(lblStrafpreis);
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
