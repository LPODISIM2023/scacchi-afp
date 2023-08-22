package it.univaq.disim.oop.scacchi.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.domain.Mossa.Attacco;
import it.univaq.disim.oop.scacchi.domain.Mossa.Muovi;

public class Alfiere extends Pezzo {

	private final static int[] MOSSE_POSSIBILI  = {-9, -7, 7, 9 };
	
	Alfiere(Colore colorePezzo, int coordinatePezzo) {
		super(TipoPezzo.ALFIERE, coordinatePezzo, colorePezzo);
	}

	@Override
	public Collection<Mossa> calcolaMosseLegali(final Scacchiera scacchiera) {
		
		final List<Mossa> possibiliMosse = new ArrayList<Mossa>();
		
		for(final int insiemePosizioneCorrente: MOSSE_POSSIBILI) {
			int coordinateArrivo = this.coordinatePezzo;	
			while(ScacchieraController.casellaDisponibile(coordinateArrivo)) {
				if(isPrimaColonnaEsclusa(coordinateArrivo, insiemePosizioneCorrente) || isOttavaColonnaEsclusa(coordinateArrivo, insiemePosizioneCorrente)) {
					break;
				}
				coordinateArrivo += insiemePosizioneCorrente;
				if(ScacchieraController.casellaDisponibile(coordinateArrivo)) {
					final Casella casellaArrivo = scacchiera.getCasella(coordinateArrivo);
					if(!casellaArrivo.occupata()) {
						possibiliMosse.add(new Muovi(scacchiera, this, coordinateArrivo));
					}else {
						final Pezzo pezzoArrivo = casellaArrivo.getPezzo();
						final Colore colorePezzo = pezzoArrivo.getColorePezzo();
						if(this.colorePezzo != colorePezzo) {
							possibiliMosse.add(new Attacco(scacchiera, this, coordinateArrivo, pezzoArrivo));
						}
						break;
					}
				}
			}
		}
		
		return Collections.unmodifiableList(possibiliMosse);
	}
	
	@Override
	public String toString() {
		return TipoPezzo.ALFIERE.toString();
	}
	
	private static boolean isPrimaColonnaEsclusa(final int posizioneAttuale, final int possibilePosizione) {
		return ScacchieraController.PRIMA_COLONNA[posizioneAttuale] && (possibilePosizione == -9 || possibilePosizione == 7);
	}
	
	private static boolean isOttavaColonnaEsclusa(final int posizioneAttuale, final int possibilePosizione) {
		return ScacchieraController.OTTAVA_COLONNA[posizioneAttuale] && (possibilePosizione == -7 || possibilePosizione == 9);
	}

	@Override
	public Alfiere pezzoMosso(Mossa mossa) {
		return new Alfiere(mossa.getPezzoMosso().getColorePezzo(), mossa.getCoordinateDestinazione());
	}

}
