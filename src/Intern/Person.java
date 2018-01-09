package Intern;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Person {
	
	protected String vorname;
	protected String name;
	protected String email;
	protected int alter;
	protected double strafpreis;
	protected String ort;
	protected String strasse;
	protected String hausnummer;
	protected int plz;
	private static final Pattern email_regex = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"); 
	
	public Person(String vorname, String name, String email, int alter, double strafpreis, String ort, String strasse,
			String hausnummer, int plz) {
		super();
		this.vorname = vorname;
		this.name = name;
                Matcher m = Person.email_regex.matcher(email);
                if(m.matches()) {
                       this.email = email;
                } else {
                       this.email = "";
                }
		this.alter = alter;
		this.strafpreis = strafpreis;
		this.ort = ort;
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.plz = plz;
	}
	
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAlter() {
		return alter;
	}
	public void setAlter(int alter) {
		this.alter = alter;
	}
	public double getStrafpreis() {
		return strafpreis;
	}
	public void setStrafpreis(double strafpreis) {
		this.strafpreis = strafpreis;
	}
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	public String getHausnummer() {
		return hausnummer;
	}
	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}
	public int getPlz() {
		return plz;
	}
	public void setPlz(int plz) {
		this.plz = plz;
	}
	
}
