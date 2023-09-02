package it.univaq.disim.oop.scacchi.pezzi;

import it.univaq.disim.oop.scacchi.gui.Tabella;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;

public class JScacchi {

	public static void main(String[] args) {

		Scacchiera scacchiera = Scacchiera.creaScacchieraStandard();

		System.out.println(scacchiera);

		Tabella.get().mostra();
		
		/*
		if (Tabella.get().getGiocoBoard().giocatoreAttuale().isInScaccoMatto()) {
			System.out.println("fine, " + Tabella.get().getGiocoBoard().giocatoreAttuale() + "è Scacco Matto!");
		}

		if (Tabella.get().getGiocoBoard().giocatoreAttuale().isInStallo()) {
			System.out.println("fine, " + Tabella.get().getGiocoBoard().giocatoreAttuale() + "è in Stallo!");
		}
		*/
		
	}

}
