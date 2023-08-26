package it.univaq.disim.oop.scacchi.pezzi;

import it.univaq.disim.oop.scacchi.gui.Tabella;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;

public class JScacchi {

	public static void main(String[] args) {

		Scacchiera scacchiera = Scacchiera.creaScacchieraStandard();

		System.out.println(scacchiera);

		Tabella tabella = new Tabella();
	}

}
