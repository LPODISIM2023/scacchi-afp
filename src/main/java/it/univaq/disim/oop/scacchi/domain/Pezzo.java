package it.univaq.disim.oop.scacchi.domain;

import java.util.*;
import it.univaq.disim.oop.scacchi.domain.Colore;
import it.univaq.disim.oop.scacchi.domain.Mossa;
import it.univaq.disim.oop.scacchi.domain.Scacchiera;

public abstract class Pezzo {

	protected final int coordinatePezzo;
	protected final Colore colorePezzo;
	protected final boolean primaMossa;

	Pezzo(final int coordinatePezzo, final Colore colorePezzo) {
		this.coordinatePezzo = coordinatePezzo;
		this.colorePezzo = colorePezzo;
		// TODO ci sono altre cose da inserire qui
		this.primaMossa = false;
	}

	public Colore getColorePezzo() {
		return this.colorePezzo;
	}

	public boolean primaMossa() {
		return this.primaMossa;
	}

	// Dice quali mosse "legali" può effetturare un pezzo sulla scacchiera
	public abstract Collection<Mossa> mosseLegali(final Scacchiera scacchiera);

}