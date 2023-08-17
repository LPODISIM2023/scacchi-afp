package it.univaq.disim.oop.scacchi.player;

import java.util.Collection;

import it.univaq.disim.oop.scacchi.domain.Colore;
import it.univaq.disim.oop.scacchi.domain.Mossa;
import it.univaq.disim.oop.scacchi.domain.Pezzo;
import it.univaq.disim.oop.scacchi.domain.Re;
import it.univaq.disim.oop.scacchi.domain.Scacchiera;

public abstract class Giocatore {

//	private Integer Id;
//	private String nome;
//	private Colore colore;
//	private Partita partite;
//
//	public Integer getId() {
//		return Id;
//	}
//
//	public void setId(Integer id) {
//		Id = id;
//	}
//
//	public String getNome() {
//		return nome;
//	}
//
//	public void setNome(String nome) {
//		this.nome = nome;
//	}
//
//	public Colore getColore() {
//		return colore;
//	}
//
//	public void setColore(Colore colore) {
//		this.colore = colore;
//	}
//
//	public Partita getPartite() {
//		return partite;
//	}
//
//	public void setPartite(Partita partite) {
//		this.partite = partite;
//	}

	protected final Scacchiera scacchiera;
	protected final Re giocatoreRe;
	protected final Collection<Mossa> mosseLegali;
	
	Giocatore(final Scacchiera scacchiera,
			  final Collection<Mossa> mosseLegali,
			  final Collection<Mossa> mosseAvversario) {
		
		this.scacchiera = scacchiera;
		this.giocatoreRe = stabilireRe();
		this.mosseLegali = mosseLegali;
	}

	private Re stabilireRe() {
		for(final Pezzo pezzo : getPezziAttivi()) {
			if(pezzo.getTipoPezzo().isRe()) {
				return(Re) pezzo;
			}
		}
		throw new RuntimeException("Non dovrebbe arrivare qui!");
	}
	
	public boolean isMossaLegale(final Mossa mossa) {
		return this.mosseLegali.contains(mossa);
	}
	
	//TODO metodi da implementare
	public boolean isInScacco() {
		return false;
	}
	
	public boolean isInScaccoMatto() {
		return false;
	}
	
	public boolean isInStallo() {
		return false;
	}
	
	public boolean isArroccato() {
		return false;
	}
	
	public TransizioneMossa fareMossa(final Mossa mossa) {
		return null;
	}
	
	public abstract Collection<Pezzo> getPezziAttivi();
	public abstract Colore getColore();
	public abstract Giocatore getAvversario();
}







