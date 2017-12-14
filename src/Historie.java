
public class Historie {

	private Datum ausleihdatum;
	private Datum rueckgabedatum;
	private int dauer;
	private int kundennummer;
	
	public Historie(Datum ausleihdatum, Datum rueckgabedatum, int dauer, int kundennummer) {
		this.ausleihdatum = ausleihdatum;
		this.rueckgabedatum = rueckgabedatum;
		this.dauer = dauer;
		this.kundennummer = kundennummer;
	}
	
	public Datum getAusleihdatum() {
		return ausleihdatum;
	}
	public Datum getRueckgabedatum() {
		return rueckgabedatum;
	}
	public int getDauer() {
		return dauer;
	}
	public int getKundennummer() {
		return kundennummer;
	}
	
}
