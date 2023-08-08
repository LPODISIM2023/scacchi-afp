package it.univaq.disim.oop.scacchi.domain;

import java.util.*;
//import com.google.common.collect.ImmutableMap;

public abstract class Casella {

	protected final int casellaCoordinata;
	
	private static final Map<Integer,CasellaVuota> CASELLE_VUOTE_CACHE = creaCaselleVuote();
	
	private static Map<Integer,CasellaVuota> creaCaselleVuote(){
		final Map<Integer,CasellaVuota> mappaCaselle = new HashMap<Integer,CasellaVuota>();
		
		for(int i = 0; i < 64; i++) {
			mappaCaselle.put(i, new CasellaVuota(i));
		}
		
		//return ImmutableMap.copyOf(mappaCaselle);
		return Collections.unmodifiableMap(mappaCaselle);
	}
	
	public static Casella creaCasella(final int coordinate, final Pezzo pezzo) {
		return pezzo != null ? new CasellaOccupata(coordinate, pezzo) : CASELLE_VUOTE_CACHE.get(coordinate);
	}
	
	private Casella(int coordinata){
		this.casellaCoordinata = coordinata;
	}
	
	public abstract boolean occupata();
	
	public abstract Pezzo getPezzo();
	
	public static final class CasellaVuota extends Casella{
		private CasellaVuota(final int coordinate){
			super(coordinate);
		}
		
		@Override
		public boolean occupata() {
			return false;
		}
		
		@Override
		public Pezzo getPezzo() {
			return null;
		}
	}
	
	public static final class CasellaOccupata extends Casella{
		
		protected final Pezzo pezzoCasella;
		
		private CasellaOccupata(int coordinata, Pezzo pezzo){
			super(coordinata);
			this.pezzoCasella = pezzo;
		}
		
		@Override
		public boolean occupata() {
			return true;
		}
		
		@Override
		public Pezzo getPezzo() {
			return pezzoCasella;
		}
		
	}
}
