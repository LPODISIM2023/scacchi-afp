package it.univaq.disim.oop.scacchi.saving;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrdinaMosse implements Ordinamento{
		
	@Override
	public void ordina() {
		String cartella = "D:\\Desktop\\scacchi-afp\\file\\2023-09-09";
			
		try {
			List<File> listaFile = Files.list(Paths.get(cartella))
					.map(Path::toFile)
					.filter(File::isFile)
					.collect(Collectors.toList());
			
			Collections.sort(listaFile, new CmpBySize());
			
			listaFile.forEach(System.out::println);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		
	}
}

	class CmpBySize implements Comparator<File> {

		@Override
		public int compare(File f1, File f2) {
			if(f1.length() > f2.length()) return -1;
			
			return 0;
				
		}
	}

