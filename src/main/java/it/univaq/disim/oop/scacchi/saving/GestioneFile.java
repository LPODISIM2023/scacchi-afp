package it.univaq.disim.oop.scacchi.saving;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.univaq.disim.oop.scacchi.scacchiera.Mossa;

public class GestioneFile {
	
	public void ScriviFile(List<Mossa> mosse, int pezziPresi) {
		
		Iterator<Mossa> listaMosse = mosse.iterator();
		List<String> elencoMosse = new ArrayList<String>();
		
		String st = "" + pezziPresi;
		elencoMosse.add(st);
		
		for(Mossa mossa: mosse) {
			String s = (String) mossa.toString();
			elencoMosse.add(s);
		}
		
		Path file = CreaFile();

		try {
			//Files.write(file, pezziPresi.getBytes(), )
			Files.write(file, elencoMosse, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	public Path CreaFile() {
		
		String cartella = CreaCartella();
		Path percorsoFile = NomeFile(cartella);
		Path pathFile = null;
		
		try {
			pathFile = Files.createFile(percorsoFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return pathFile;
	}
	
	public Path NomeFile(String cartella) {
		
		String nomeFile = "game";
		final String nome = nomeFile;
		String tipoFile = ".txt";
				
		long count = 0;
		try {
			count = Files.list(Paths.get(cartella))      // prende tutti i file dalla cartella
				    .filter(path -> path.getFileName().toString().startsWith(nome))   // controlla quanti file iniziano con "game"
				    .count();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		nomeFile = nomeFile + "(" + count + ")";
		String fullName = cartella + "\\" + nomeFile + tipoFile;
		Path percorsoFile = Paths.get(fullName);
		
		return percorsoFile;
	}
	
	private static String CreaCartella() {
		
		String percorsoCartella = "D:\\Desktop\\scacchi-afp\\file\\"; 
		LocalDate data = java.time.LocalDate.now(); //prende la data corrente 
		File myCartella = new File(percorsoCartella + data); 
		
		if(!myCartella.exists()) {
			try {
				myCartella.mkdir();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		String pathLocation = myCartella.getAbsolutePath();
		
		return pathLocation;
	}
}
