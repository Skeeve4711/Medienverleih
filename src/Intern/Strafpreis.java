package Intern;

public class Strafpreis {

	private double betrag = 5;
	private boolean istAbsolut = false;
	
	public Strafpreis(double betrag, boolean istAbsolut) {
		this.betrag = betrag;
		this.istAbsolut = istAbsolut;
	}

	public double getBetrag() {
		return betrag;
	}

	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}

	public boolean isIstAbsolut() {
		return istAbsolut;
	}

	public void setIstAbsolut(boolean istAbsolut) {
		this.istAbsolut = istAbsolut;
	}
}
