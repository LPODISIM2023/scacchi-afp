package it.univaq.disim.oop.scacchi.player;

import java.util.*;
import it.univaq.disim.oop.scacchi.domain.Colore;
import it.univaq.disim.oop.scacchi.domain.Mossa;
import it.univaq.disim.oop.scacchi.domain.Pezzo;
import it.univaq.disim.oop.scacchi.domain.Scacchiera;

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
