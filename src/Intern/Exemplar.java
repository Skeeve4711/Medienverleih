package Intern;
import java.util.ArrayList;

public class Exemplar extends Medium {
	enum Status { verliehen, verkauft, entsorgt, auf_lager};
	private ArrayList<Historie> historie;
	private String rueckgabedatum;
	private int dauer;
	private Datum einstellungsdatum;
	private String titel;
	private String artikelnummer;
	private double kaufpreis;
	private int leihdauer;
	private double leihpreis;
	private Datum leihdatum;
	private Status status;
	private Kategorie kategorie;
	private String grund;
	
	public Exemplar(String rueckgabedatum, int dauer, Datum einstellungsdatum,
			String titel, String artikelnummer, double kaufpreis, int leihdauer, 
			double leihpreis, Datum leihdatum, Kategorie kategorie, double originalpreis, int altersfreigabe) {
		this.historie = new ArrayList<>();
		this.rueckgabedatum = rueckgabedatum;
		this.dauer = dauer;
		this.einstellungsdatum = einstellungsdatum;
		this.titel = titel;
		this.artikelnummer = artikelnummer;
		this.kaufpreis = kaufpreis;
		this.leihdauer = leihdauer;
		this.leihpreis = leihpreis;
		this.leihdatum = leihdatum;
		this.status = Status.auf_lager;
		this.kategorie = kategorie;
		this.originalpreis = originalpreis;
		this.altersfreigabe = altersfreigabe;
		this.grund = "";
	}
	
	public ArrayList<Historie> getHistorie() {
		return historie;
	}
	public void addHistorie(Historie historie) {
		this.historie.add(historie);
	}
	public String getRueckgabedatum() {
		return rueckgabedatum;
	}
	public void setRueckgabedatum(String rueckgabedatum) {
		this.rueckgabedatum = rueckgabedatum;
	}
	public int getDauer() {
		return dauer;
	}
	public void setDauer(int dauer) {
		this.dauer = dauer;
	}
	public Datum getEinstellungsdatum() {
		return einstellungsdatum;
	}
	public void setEinstellungsdatum(Datum einstellungsdatum) {
		this.einstellungsdatum = einstellungsdatum;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public String getArtikelnummer() {
		return artikelnummer;
	}
	public void setArtikelnummer(String artikelnummer) {
		this.artikelnummer = artikelnummer;
	}
	public double getKaufpreis() {
		return kaufpreis;
	}
	public void setKaufpreis(double kaufpreis) {
		this.kaufpreis = kaufpreis;
	}
	public int getLeihdauer() {
		return leihdauer;
	}
	public void setLeihdauer(int leihdauer) {
		this.leihdauer = leihdauer;
	}
	public double getLeihpreis() {
		return leihpreis;
	}
	public void setLeihpreis(double leihpreis) {
		this.leihpreis = leihpreis;
	}
	public Datum getLeihdatum() {
		return leihdatum;
	}
	public void setLeihdatum(Datum leihdatum) {
		this.leihdatum = leihdatum;
	}
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public void setStatus(Status status,String grund) {
		this.status = status;
		this.grund = grund;
	}
	
	public Kategorie getKategorie() {
		return this.kategorie;
	}
	
}
