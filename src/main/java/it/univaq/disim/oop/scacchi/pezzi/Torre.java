package it.univaq.disim.oop.scacchi.pezzi;

import java.util.*;

import com.google.common.collect.ImmutableList;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.player.Colore;
import it.univaq.disim.oop.scacchi.scacchiera.Casella;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa.Attacco;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa.Muovi;

public class Torre extends Pezzo{
	
	private final static int[] MOSSE_POSSIBILI  = {-8, -1, 1, 8 };
	
	public Torre(Colore colorePezzo, int coordinatePezzo) {
		super(TipoPezzo.TORRE, coordinatePezzo, colorePezzo);
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
		return TipoPezzo.TORRE.toString();
	}
	
	private static boolean isPrimaColonnaEsclusa(final int posizioneAttuale, final int possibilePosizione) {
		return ScacchieraController.PRIMA_COLONNA[posizioneAttuale] && (possibilePosizione == -1);
	}
	
	private static boolean isOttavaColonnaEsclusa(final int posizioneAttuale, final int possibilePosizione) {
		return ScacchieraController.OTTAVA_COLONNA[posizioneAttuale] && (possibilePosizione == 1);
	}

	@Override
	public Torre pezzoMosso(Mossa mossa) {
		return new Torre(mossa.getPezzoMosso().getColorePezzo(), mossa.getCoordinateDestinazione());
	}

}