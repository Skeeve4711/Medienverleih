package Intern;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Kunde extends Person {
	private static int nummer = 0;
	private String passwort;
	private final int kundennummer;
	public static Pattern anforderungen = Pattern.compile("[a-zA-Z0-9]{4,}");
	
	public Kunde(String vorname, String name, String email, int alter, double strafpreis, String ort, String strasse,
			String hausnummer, int plz, String passwort) {
		super(vorname, name, email, alter, strafpreis, ort, strasse, hausnummer, plz);
		if(anforderungenErfuellt(passwort)) {
			this.passwort = passwort;
		} else {
			this.passwort = "1234";
		}
		this.kundennummer = generiereKundenummer();
	}

	private int generiereKundenummer() {
		return ++nummer;
	}

	public String getPasswort() {
		return passwort;
	}

	public boolean setPasswort(String altPasswort, String neupasswort) {
		if(anforderungenErfuellt(neupasswort) && altPasswort.equals(this.passwort)) {
			this.passwort = neupasswort;
			return true;
		}
		return false;
	}

	private boolean anforderungenErfuellt(String passwort) {
		Matcher m = Kunde.anforderungen.matcher(passwort);
		return m.matches();
	}
	
	public void setAnforderungenErfuellt(String s) { // TODO
		Kunde.anforderungen = Pattern.compile(s);
	}

	public int getKundennummer() {
		return kundennummer;
	}
}
