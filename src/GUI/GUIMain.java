package GUI;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import javax.swing.JTextArea;
import javax.swing.table.TableColumn;
import javax.swing.JTable; 

public class GUIMain implements WindowListener{

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
	    frame.setBounds((screenSize.width-1300)/2, (screenSize.height-500)/2, 1300, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(this);
		
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
		
		JButton btnNewButton_1 = new JButton("Kundenansicht");
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
		scrollPaneMedien.setBounds(298, 77, 982, 277);
		frame.getContentPane().add(scrollPaneMedien);
		
		JTextArea txtrMedien = new JTextArea();
		txtrMedien.setEditable(false);
		txtrMedien.setText("Medien");
		scrollPaneMedien.setColumnHeaderView(txtrMedien);
		
		this.con = SimpleQuery.connect();
		String[] columnNames = {"Art. Nr.",
				"Kaufpreis", "Altersfreigabe", "Status",
				"Schauspieler","Regie","Filmstudio",
				"Erscheinungsdatum","Titel","Genre",
				"Leihpreis"};
		try {
			PreparedStatement pst = con.prepareStatement("select * from Filme");
		    ResultSet rs = pst.executeQuery();
		    Object data[][] = new Object[100][12]; 
		    int indexA = 0;
		    while(rs.next()) {
		    	for(int i=1;i<12;i++) {
		    		data[indexA][i-1] = rs.getString(i);
		    	}
		    	indexA++;
		    }
			table = new JTable(data, columnNames);
			
			TableColumn column = null;
			for (int i = 0; i < 11; i++) {
			    column = table.getColumnModel().getColumn(i);
			    column.setPreferredWidth(120);
			}
			
			scrollPaneMedien.setViewportView(table);
		} catch(SQLException e) {
			e.printStackTrace();
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
			this.con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}

class SimpleQuery { 
  
 public static Connection connect(){ 

     // Diese Eintraege werden zum 
     // Verbindungsaufbau benoetigt. 
     final String hostname = "localhost"; 
     final String port = "3306"; 
     final String dbname = "Medienverleih"; 
     final String user = "pascal"; 
     final String password = ""; 
	
     Connection con = null; 
     PreparedStatement pst = null;
     
     try { 
	    System.out.println("* Verbindung aufbauen"); 
	    String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname; 
	    con = DriverManager.getConnection(url, user, password); 
	   
	    
     } 
     catch (SQLException sqle) { 
         System.out.println("SQLException: " + sqle.getMessage()); 
         System.out.println("SQLState: " + sqle.getSQLState()); 
         System.out.println("VendorError: " + sqle.getErrorCode()); 
         sqle.printStackTrace(); 
     } 
     return con;
		
  } // ende: public static void main() 
 
} // ende: public class SimpleQuery 
