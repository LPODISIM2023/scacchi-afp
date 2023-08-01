package it.univaq.disim.oop.scacchi.domain;

import java.util.Set;

public class Mossa {

	private Integer Id;
	private Integer IdPezzo;

	private Giocatore mossaGiocatore;
	private Pezzo pezzoMosso;
	private Set<Partita> partite;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getIdPezzo() {
		return IdPezzo;
	}

	public void setIdPezzo(Integer idPezzo) {
		IdPezzo = idPezzo;
	}

	public Giocatore getMossaGiocatore() {
		return mossaGiocatore;
	}

	public void setMossaGiocatore(Giocatore mossaGiocatore) {
		this.mossaGiocatore = mossaGiocatore;
	}

	public Pezzo getPezzoMosso() {
		return pezzoMosso;
	}

	public void setPezzoMosso(Pezzo pezzoMosso) {
		this.pezzoMosso = pezzoMosso;
	}

	public Set<Partita> getPartite() {
		return partite;
	}

	public void setPartite(Set<Partita> partite) {
		this.partite = partite;
	}

	public Scacchiera muovi() {
		return null;
	}

}
