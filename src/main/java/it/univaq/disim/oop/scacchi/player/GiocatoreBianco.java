package it.univaq.disim.oop.scacchi.player;

import java.util.Collection;

import it.univaq.disim.oop.scacchi.domain.Colore;
import it.univaq.disim.oop.scacchi.domain.Mossa;
import it.univaq.disim.oop.scacchi.domain.Pezzo;
import it.univaq.disim.oop.scacchi.domain.Scacchiera;

public class GiocatoreBianco extends Giocatore{

	public GiocatoreBianco(Scacchiera scacchiera,
						   Collection<Mossa> mosseStandardLegaliBianco,
						   Collection<Mossa> mosseStandardLegaliNero) {
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
