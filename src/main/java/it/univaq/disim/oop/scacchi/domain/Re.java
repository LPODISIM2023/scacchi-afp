package it.univaq.disim.oop.scacchi.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.domain.Mossa.Attacco;
import it.univaq.disim.oop.scacchi.domain.Mossa.Muovi;

public class Re extends Pezzo {

	private final static int[] MOSSE_POSSIBILI = { -9, -8, -7, -1, 1, 7, 8, 9 };

	Re(int coordinatePezzo, Colore colorePezzo) {
		super(coordinatePezzo, colorePezzo);
	}

	@Override
	public Collection<Mossa> mosseLegali(Scacchiera scacchiera) {

		final List<Mossa> possibiliMosse = new ArrayList<Mossa>();

		for (final int insiemePosizioneCorrente : MOSSE_POSSIBILI) {
			final int coordinateArrivo = this.coordinatePezzo + insiemePosizioneCorrente;
			if (primaColonnaEsclusa(insiemePosizioneCorrente, coordinateArrivo)
					|| ottavaColonnaEsclusa(this.coordinatePezzo, insiemePosizioneCorrente)) {
				continue;
			}
			if (ScacchieraController.casellaDisponibile(coordinateArrivo)) {
				final Casella casellaArrivo = scacchiera.getCasella(coordinateArrivo);
				if (!casellaArrivo.occupata()) {
					possibiliMosse.add(new Muovi(scacchiera, this, coordinateArrivo));
				} else {
					final Pezzo pezzoArrivo = casellaArrivo.getPezzo();
					final Colore colorePezzo = pezzoArrivo.getColorePezzo();
					if (this.colorePezzo != colorePezzo) {
						possibiliMosse.add(new Attacco(scacchiera, this, coordinateArrivo, pezzoArrivo));
					}
				}
			}
		}

		return Collections.unmodifiableList(possibiliMosse);
	}

	private static boolean primaColonnaEsclusa(final int posizioneAttuale, final int possibilePosizione) {
		return ScacchieraController.PRIMA_COLONNA[posizioneAttuale]
				&& (possibilePosizione == -9 || possibilePosizione == -1 || possibilePosizione == 7);
	}

	private static boolean ottavaColonnaEsclusa(final int posizioneAttuale, final int possibilePosizione) {
		return ScacchieraController.OTTAVA_COLONNA[posizioneAttuale]
				&& (possibilePosizione == -7 || possibilePosizione == 1 || possibilePosizione == 9);
	}
}
