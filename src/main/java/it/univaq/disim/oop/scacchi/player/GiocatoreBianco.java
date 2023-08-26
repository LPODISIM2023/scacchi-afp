package it.univaq.disim.oop.scacchi.player;

import java.util.*;

import it.univaq.disim.oop.scacchi.pezzi.Colore;
import it.univaq.disim.oop.scacchi.pezzi.Pezzo;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;

public class GiocatoreBianco extends Giocatore{

	public GiocatoreBianco(final Scacchiera scacchiera,
						   final Collection<Mossa> mosseStandardLegaliBianco,
						   final Collection<Mossa> mosseStandardLegaliNero) {
		super(scacchiera, mosseStandardLegaliBianco, mosseStandardLegaliNero);
	}

	@Override
	public Collection<Pezzo> getPezziAttivi() {
		return this.scacchiera.getPezziBianchi();
	}

	@Override
	public Colore getColore() {
		return Colore.BIANCO;
	}

	@Override
	public Giocatore getAvversario() {
		return this.scacchiera.giocatoreNero();
	}

}
