import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;


public class ZhuTakaokaTests {
	
//	@Ignore
	@Test
	public void readFileTest() throws IOException{
		String textFilename = "text.txt";
		String patternFilename = "pattern.txt";
		doTestingFile(textFilename, patternFilename);
	}
	
	@Ignore
	@Test
	public void readFileTest8_10__500_10() throws IOException{
		String textFilename = "text_500k_10.txt";
		String patternFilename = "pattern_8_10.txt";
		doTestingFile(textFilename, patternFilename);
	}
	
	//TESTE EM LIVROS E WIKIPEDIA-------------------------------------------------------------------------------
	
	@Ignore
	@Test
	public void readFileTestAdventure() throws IOException{
		String textFilename = "The Adventure Girls at K Bar O.txt";
		String pattern = "rebel";
		doTestingTextFile(textFilename, pattern);
	}
	
	@Ignore
	@Test
	public void readFileTestAdventure2() throws IOException{
		String textFilename = "The Adventure Girls at K Bar O.txt";
		String pattern = "telephone";
		doTestingTextFile(textFilename, pattern);
	}
	
	@Ignore
	@Test
	public void readFileTestAdventure3() throws IOException{
		String textFilename = "The Adventure Girls at K Bar O.txt";
		String pattern = "thunderstorm";
		doTestingTextFile(textFilename, pattern);
	}
	
	@Ignore
	@Test
	public void readFileTestAdventure4() throws IOException{
		String textFilename = "The Adventure Girls at K Bar O.txt";
		String pattern = "struggling";
		doTestingTextFile(textFilename, pattern);
	}
	
	@Ignore
	@Test
	public void readFileTestAdventure5() throws IOException{
		String textFilename = "The Adventure Girls at K Bar O.txt";
		String pattern = "For a second none of the riders could do anything but";
		doTestingTextFile(textFilename, pattern);
	}
	
	
	@Ignore
	@Test
	public void readFileTestThrones() throws IOException{
		String textFilename = "AGameOfThrones1.txt";
		String pattern = "idea";
		doTestingTextFile(textFilename, pattern);
	}
	
	
	@Ignore
	@Test
	public void readFileTestThrones2() throws IOException{
		String textFilename = "AGameOfThrones1.txt";
		String pattern = "struggling";
		doTestingTextFile(textFilename, pattern);
	}
	
	@Ignore
	@Test
	public void readFileTestThrones3() throws IOException{
		String textFilename = "AGameOfThrones1.txt";
		String pattern = "You know the one I mean, your bastard";
		doTestingTextFile(textFilename, pattern);
	}
	
	@Ignore
	@Test
	public void readFileTestThrones4() throws IOException{
		String textFilename = "AGameOfThrones1.txt";
		String pattern = "The rising sun sent fingers of light through the pale white mists of dawn. A wide plain spread";
		doTestingTextFile(textFilename, pattern);
	}
	
	@Ignore
	@Test
	public void readFileTestWikipedia() throws IOException{
		String textFilename = "wikipedia.txt";
		String pattern = "idea";
		doTestingTextFile(textFilename, pattern);
	}
	
	@Ignore
	@Test
	public void readFileTestWikipedia2() throws IOException{
		String textFilename = "wikipedia.txt";
		String pattern = "struggling";
		doTestingTextFile(textFilename, pattern);
	}
	
	@Ignore
	@Test
	public void readFileTestWikipedia3() throws IOException{
		String textFilename = "wikipedia.txt";
		String pattern = "establishment";
		doTestingTextFile(textFilename, pattern);
	}
	
	@Ignore
	@Test
	public void readFileTestWikipedia4() throws IOException{
		String textFilename = "wikipedia.txt";
		String pattern = "for the most time";
		doTestingTextFile(textFilename, pattern);
	}
	
	@Ignore
	@Test
	public void readFileTestWikipedia5() throws IOException{
		String textFilename = "wikipedia.txt";
		String pattern = "most territorial authorities cover both urban and rural land";
		doTestingTextFile(textFilename, pattern);
	}
	
	
	//OUTROS TESTES -------------------------------------------------------------------------
	
	@Ignore
	@Test
	public void SimpleTest() throws IOException{
		String text = "abcdefghijklmnopefgh";
		String pattern = "efgh";
		doTesting(text, pattern, true);
	}
	
	@Ignore
	@Test
	public void NotFoundTest() throws IOException{
		String text = "abcdijklmnopqrstuvv";
		String pattern = "efgh";
		doTesting(text, pattern, true);
	}
	
	@Ignore
	@Test
	public void Mismatch2ndfromrightWithoutBDOccurenceCharacterTest() throws IOException{
		String text = "abcdijklefphmnopqrstuvv";
		String pattern = "efgh";
		doTesting(text, pattern, true);
	}
	
	@Ignore
	@Test
	public void Mismatch3rdfromrightWithoutBDOccurenceCharacterTest() throws IOException{
		String text = "abcdijklepghmnopqrstuvv";
		String pattern = "efgh";
		doTesting(text, pattern, true);
	}
	
	@Ignore
	@Test
	public void Mismatch4thfromrightWithoutBDOccurenceCharacterTest() throws IOException{
		String text = "abcdijklpfghmnopqrstuvv";
		String pattern = "efgh";
		doTesting(text, pattern, true);
	}
	
	@Ignore
	@Test
	public void Mismatch1stfromrightWithBDOccurenceOnFirstCharacterTest() throws IOException{
		String text = "abcdijklefgemnopqrstuvv";
		String pattern = "efgh";
		doTesting(text, pattern, true);
	}
	
	@Ignore
	@Test
	public void Mismatch2ndfromrightWithBDOccurenceOnFirstCharacterTest() throws IOException{
		String text = "abcdijklefehmnopqrstuvv";
		String pattern = "efgh";
		doTesting(text, pattern, true);
	}
	
	@Ignore
	@Test
	public void Mismatch3rdfromrightWithBDOccurenceOnFirstCharacterTest() throws IOException{
		String text = "abcdijkleeghmnopqrstuvv";
		String pattern = "efgh";
		doTesting(text, pattern, true);
	}
	
	@Ignore
	@Test
	public void Mismatch1stfromrightWithBDOccurenceCharacterTest() throws IOException{
		String text = "abcdijklefefhmnopqrstuvv";
		String pattern = "efgh";
		doTesting(text, pattern, true);
	}
	
	@Ignore
	@Test
	public void Mismatch2ndfromrightWithBDOccurenceCharacterTest() throws IOException{
		String text = "abcdijkrtyefefimnopqrstuvv";
		String pattern = "efghi";
		doTesting(text, pattern, true);
	}
	
	//METODOS AUXILIARES ---------------------------------------------------------------------------------------------
	
	public void doTestingTextFile(String textFilename, String pattern) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(textFilename));
		StringBuffer str = new StringBuffer();
		String temp = null;
		while((temp = reader.readLine()) != null){
			str.append(temp);
		}
		String text = str.toString();
		reader.close();
		doTesting(text, pattern, false);
	}
	
	public void doTestingFile(String textFilename, String patternFilename) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(textFilename));
		String text = reader.readLine();
		reader.close();
		reader = new BufferedReader(new FileReader(patternFilename));
		String pattern = reader.readLine();
		reader.close();
		doTesting(text, pattern, true);
	}
	
	public void doTesting(String text, String pattern, boolean printTextAndPattern){
		if(printTextAndPattern){
			System.out.println("Texto: " + text);
			System.out.println("Padrao: " + pattern);
		}
		System.out.println("************************************************************************************************************");
		ZhuTakaoka zhuTakaoka = new ZhuTakaoka();
		BoyerMoore boyerMoore = new BoyerMoore();
		
		long startingTime = System.currentTimeMillis();
		Integer resultsMoore[] = boyerMoore.search(text, pattern);
		double boyerMooreTiming = (System.currentTimeMillis()-startingTime)/1000.0;
		System.out.println("Boyer-Moore::Tempo levado: " + boyerMooreTiming + " segundos");
		System.out.print("Resultados: ");
		System.out.println(Arrays.toString(resultsMoore));
		System.out.println("----------------------------------------------------------------------------------------------------------");
		
		startingTime = System.currentTimeMillis();
		Integer resultsTakaoka[] = zhuTakaoka.search(text, pattern);
		double zhuTakaokaTiming = (System.currentTimeMillis()-startingTime)/1000.0;
		System.out.println("Zhu-Takaoka::Tempo levado: " + zhuTakaokaTiming + " segundos");
		System.out.print("Resultados: ");
		System.out.println(Arrays.toString(resultsTakaoka));
		
		System.out.println("\n\nResumo dos resultados:");
		System.out.println("Boyer-Moore results: " + Arrays.toString(resultsMoore));
		System.out.println("Z-hu-Takaoka results: " + Arrays.toString(resultsTakaoka));
		System.out.println("Boyer-Moore: " + boyerMooreTiming + " segundos:");
		System.out.println("Zhu-Takaoka: " + zhuTakaokaTiming + " segundos:");
		
		
		confrontResults(resultsMoore, resultsTakaoka);
	}
	
	public void confrontResults(Integer[] resultsMoore, Integer[] resultsTakaoka){
		HashSet<Integer> setMoore = new HashSet<Integer>();
		HashSet<Integer> setTakaoka = new HashSet<Integer>();
		setMoore.addAll(Arrays.asList(resultsMoore));
		setTakaoka.addAll(Arrays.asList(resultsTakaoka));
		if(setMoore.size() > resultsMoore.length)
			System.out.println("Algum resultado de Moore foi repetido!!");
		if(setTakaoka.size() > resultsTakaoka.length)
			System.out.println("Algum resultado de Takaoka foi repetido!!");
		
		HashSet<Integer> set1 = setMoore, 
						 set2 = setTakaoka;
		if(setTakaoka.size() < setMoore.size()){
			set1 = setTakaoka;
			set2 = setMoore;
		}

		Iterator<Integer> it = set1.iterator();
		while(it.hasNext()){
			Integer value = it.next();
			if(set2.contains(value)){
				it.remove();
				set2.remove(value);
			}
		}
		
		System.out.println("\nDiferenças de resultados encontrados:");
		System.out.println("Boyer-Moore: " + Arrays.toString(setMoore.toArray()));
		System.out.println("Zhu-Takaoka: " + Arrays.toString(setTakaoka.toArray()));
	}

}
