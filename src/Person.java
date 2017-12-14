
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
	
	public Person(String vorname, String name, String email, int alter, double strafpreis, String ort, String strasse,
			String hausnummer, int plz) {
		super();
		this.vorname = vorname;
		this.name = name;
		this.email = email;
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
