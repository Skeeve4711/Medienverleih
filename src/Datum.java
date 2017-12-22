
public class Datum {
	int tag;
	int monat;
	int jahr;
	static int tmp[] = {31,28,31,30,31,30,31,31,30,31,30,31};

	
	public Datum(int tag,int monat,int jahr) {
		if(istGueltigesDatum(tag,monat,jahr)) {
			this.tag = tag;
			this.monat = monat;
			this.jahr = jahr;
		} else{

			tag = 0;
		}
	}

	private boolean istGueltigesDatum(int t, int m, int j) {
	     tmp[1] = istSchaltjahr(j) ? 29 : 28;
	     return     m >= 1    && m <= 12
	            && j  >= 1900 && j  <= 2399  // oder mehr
	            && t  >= 1    && t   <= tmp[m-1];
	}

	private boolean istSchaltjahr(int j) {
		return  ((jahr % 4 == 0) && (jahr % 100) != 0) || (jahr % 400 == 0);
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getMonat() {
		return monat;
	}

	public void setMonat(int monat) {
		this.monat = monat;
	}

	public int getJahr() {
		return jahr;
	}

	public void setJahr(int jahr) {
		this.jahr = jahr;
	}
}
