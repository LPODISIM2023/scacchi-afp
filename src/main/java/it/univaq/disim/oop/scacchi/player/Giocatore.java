package it.univaq.disim.oop.scacchi.player;

import java.util.*;
import com.google.common.collect.ImmutableList;

import it.univaq.disim.oop.scacchi.pezzi.Colore;
import it.univaq.disim.oop.scacchi.pezzi.Pezzo;
import it.univaq.disim.oop.scacchi.pezzi.Re;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;

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
	private final boolean isInScacco;
	
	Giocatore(final Scacchiera scacchiera,
			  final Collection<Mossa> mosseLegali,
			  final Collection<Mossa> mosseAvversario) {
		
		this.scacchiera = scacchiera;
		this.giocatoreRe = stabilireRe();
		this.mosseLegali = mosseLegali;
		this.isInScacco = !Giocatore.calcolaAttaccoSuCasella(this.giocatoreRe.getCoordinatePezzo(), mosseAvversario).isEmpty();
	}
	
	public Re getGiocatoreRe() {
		return this.giocatoreRe;
	}
	
	public Collection<Mossa> getMosseLegali(){
		return this.mosseLegali;
	}
	
	private static Collection<Mossa> calcolaAttaccoSuCasella(int coordinatePezzo, Collection<Mossa> mosse) {
		final List<Mossa> mosseAttacco = new ArrayList<Mossa>();
		for(final Mossa mossa : mosse) {
			if(coordinatePezzo == mossa.getCoordinateDestinazione()) {
				mosseAttacco.add(mossa); 
			}
		}
		
		return ImmutableList.copyOf(mosseAttacco);
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
	
	public boolean isInScacco() {
		return this.isInScacco;
	}

	public boolean isInScaccoMatto() {
		return this.isInScacco && !hasMosseDiFuga();
	}
	
	public boolean isInStallo() {
		return !this.isInScacco && !hasMosseDiFuga(); //non è né in scacco né ha mosse di fuga
	}
	
	private boolean hasMosseDiFuga() { // per verificare le mosse di fuga del re
		for(final Mossa mossa : this.mosseLegali) {
			final TransizioneMossa transizione = fareMossa(mossa);
			if(transizione.getStatoMossa().isFatto()) {
				return true;
			}
		}
		return false;
	}

	public boolean isArroccato() {
		return false;
	}
	
	public TransizioneMossa fareMossa(final Mossa mossa) {
		if(!isMossaLegale(mossa)) {
			return new TransizioneMossa(this.scacchiera, mossa, StatoMossa.MOSSA_ILLEGALE);
		}
		final Scacchiera transizioneScacchiera = mossa.esegui();
		final Collection<Mossa> attacchiRe = Giocatore.calcolaAttaccoSuCasella(transizioneScacchiera.giocatoreAttuale().getAvversario().giocatoreRe.getCoordinatePezzo(), 
				transizioneScacchiera.giocatoreAttuale().getMosseLegali());
		if(!attacchiRe.isEmpty()) {
			return new TransizioneMossa(this.scacchiera, mossa, StatoMossa.LASCIA_GIOCATORE_IN_SCACCO);
		}
		return new TransizioneMossa(transizioneScacchiera, mossa, StatoMossa.FATTO);
	}
	
	public abstract Collection<Pezzo> getPezziAttivi();
	public abstract Colore getColore();
	public abstract Giocatore getAvversario();
}







