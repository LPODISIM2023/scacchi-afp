package it.univaq.disim.oop.scacchi.domain;

public class Pezzo {

	private int id;
	private int idGiocatore;
	private Scacchiera posizione;
	private Boolean vivo;
	private Scacchiera pezzoScacchiera;
	private Mossa pezzoMosso;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdGiocatore() {
		return idGiocatore;
	}

	public void setIdGiocatore(int idGiocatore) {
		this.idGiocatore = idGiocatore;
	}

	public Scacchiera getPosizione() {
		return posizione;
	}

	public void setPosizione(Scacchiera posizione) {
		this.posizione = posizione;
	}

	public Boolean getVivo() {
		return vivo;
	}

	public void setVivo(Boolean vivo) {
		this.vivo = vivo;
	}

	public Scacchiera getPezzoScacchiera() {
		return pezzoScacchiera;
	}

	public void setPezzoScacchiera(Scacchiera pezzoScacchiera) {
		this.pezzoScacchiera = pezzoScacchiera;
	}

	public Mossa getPezzoMosso() {
		return pezzoMosso;
	}

	public void setPezzoMosso(Mossa pezzoMosso) {
		this.pezzoMosso = pezzoMosso;
	}

}