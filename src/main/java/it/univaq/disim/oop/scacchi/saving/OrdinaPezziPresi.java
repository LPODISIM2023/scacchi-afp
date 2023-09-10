package it.univaq.disim.oop.scacchi.saving;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrdinaPezziPresi implements Ordinamento{

	@Override
	public void ordina() {
				
		String cartella = "D:\\Desktop\\scacchi-afp\\file\\2023-09-10";
		
		try {
			List<File> listaFile = Files.list(Paths.get(cartella))
					.map(Path::toFile)
					.filter(File::isFile)
					.collect(Collectors.toList());
			
			Collections.sort(listaFile, new CmpByPezziPresi());
			
			listaFile.forEach(System.out::println);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		
		
	}

}

class CmpByPezziPresi implements Comparator<File> {

	@Override
	public int compare(File f1, File f2) {
		try {
			BufferedReader reader1 = new BufferedReader(new FileReader(f1));
			BufferedReader reader2 = new BufferedReader(new FileReader(f2));
			
			if(reader1.readLine().charAt(0) < reader2.readLine().charAt(0)) return -1;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
			
	}
}