package it.univaq.disim.oop.scacchi.domain;

import java.util.Set;

public class Scacchiera extends Giocatore {

	private Integer Id;
	private Set<Pezzo> scacchiera;
	private Boolean vittoria;
	private Boolean pareggio;
	private Pezzo pezzo;
	private Partita partite;
	private Set<Pezzo> pezzi;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Set<Pezzo> getScacchiera() {
		return scacchiera;
	}

	public void setScacchiera(Set<Pezzo> scacchiera) {
		this.scacchiera = scacchiera;
	}

	public Boolean getVittoria() {
		return vittoria;
	}

	public void setVittoria(Boolean vittoria) {
		this.vittoria = vittoria;
	}

	public Boolean getPareggio() {
		return pareggio;
	}

	public void setPareggio(Boolean pareggio) {
		this.pareggio = pareggio;
	}

	public Pezzo getPezzo() {
		return pezzo;
	}

	public void setPezzo(Pezzo pezzo) {
		this.pezzo = pezzo;
	}

	public Partita getPartite() {
		return partite;
	}

	public void setPartite(Partita partite) {
		this.partite = partite;
	}

	public Set<Pezzo> getPezzi() {
		return pezzi;
	}

	public void setPezzi(Set<Pezzo> pezzi) {
		this.pezzi = pezzi;
	}

}