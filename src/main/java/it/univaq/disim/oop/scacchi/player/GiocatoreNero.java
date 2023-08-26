package it.univaq.disim.oop.scacchi.player;

import java.util.*;

import it.univaq.disim.oop.scacchi.pezzi.Colore;
import it.univaq.disim.oop.scacchi.pezzi.Pezzo;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;

public class GiocatoreNero extends Giocatore{

	public GiocatoreNero(final Scacchiera scacchiera, 
						 final Collection<Mossa> mosseStandardLegaliBianco,
						 final Collection<Mossa> mosseStandardLegaliNero) {
		super(scacchiera, mosseStandardLegaliNero, mosseStandardLegaliBianco);
	}

	@Override
	public Collection<Pezzo> getPezziAttivi() {
		return this.scacchiera.getPezziNeri();
	}

	@Override
	public Colore getColore() {
		return Colore.NERO;
	}

	@Override
	public Giocatore getAvversario() {
		return this.scacchiera.giocatoreBianco();
	}

}
