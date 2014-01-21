import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class ZhuTakaoka extends BoyerMoore {

	public HashMap<Character, HashMap<Character, Integer> > build2BadCharactersTable(String pattern){
		HashMap<Character, HashMap<Character, Integer> > badCharTable = new HashMap<Character, HashMap<Character, Integer> >();
//		 HashSet<Character> cache = new HashSet<Character>();
		int m = pattern.length();
		for(int i=1; i<m ; i++){
//			cache.add(pattern.charAt(i));
			HashMap<Character, Integer> ch2List = badCharTable.get(pattern.charAt(i-1));
			if(ch2List == null) {
				ch2List = new HashMap<Character, Integer>();
				badCharTable.put(pattern.charAt(i-1), ch2List);
			}
			ch2List.put(pattern.charAt(i), m-1-i);
		}
		
//		char ch2 = pattern.charAt(0);
//		Iterator<Character> it = cache.iterator();
//		while(it.hasNext()){
//			Character ch1 = it.next();
//			HashMap<Character, Integer> ch2List = badCharTable.get(ch1);
//			if(ch2List == null) {
//				ch2List = new HashMap<Character, Integer>();
//				badCharTable.put(pattern.charAt(ch1), ch2List);
//			}
//			ch2List.put(ch2, m-2);
//		}
		
		return badCharTable;
	}
	
	
	public int shiftBadCharacter(int m, Character ch0, Character ch1, Character ch2, int index, HashMap<Character, HashMap<Character, Integer> > buildBadCharsTable){
		int retorno = 1; //valor default para quanto index = 0, para o qual qualquer combinacao 'ya', tal que 'y' e um caractere qualquer do alfabeto, e 'a' e pattern.charAt(index), levando em conta que o indice de mismatching('index') tambem e 0; nesse contexto, o valor final do shift a ser dado pela heuristica do mau-caractere do zhu takaoka seria negativo, ou no maximo 1, que e tambem o valor de um shift normal em modo brute force, nao interferindo assim no resultado pois deixa a cargo da tabela do bom-sufixo o tamanho do shift final a ser dado pelo algoritmo
		if(index > 0){
			int badCharR = m; //valor padrão do shift quando o par de caracters procurado nao existe no padrao
			HashMap<Character, Integer> ch2List = buildBadCharsTable.get(ch1);
			if(ch2List != null){
				Integer chtemp = ch2List.get(ch2);
				if(chtemp != null) badCharR = chtemp;
			}
			if(badCharR == m && ch2 == ch0)
				badCharR--;
			retorno = badCharR - (m-1 - index);
		}
		return retorno;
		
	}
	
//	public int shiftBadCharacter(int m, Character ch1, Character ch2, int index, HashMap<Character, HashMap<Character, Integer> > buildBadCharsTable){
//		int retorno = 1; //valor default para quanto index = 0, para o qual qualquer combinacao 'ya', tal que 'y' e um caractere qualquer do alfabeto, e 'a' e pattern.charAt(index), levando em conta que o indice de mismatching('index') tambem e 0; nesse contexto, o valor final do shift a ser dado pela heuristica do mau-caractere do zhu takaoka seria negativo, ou no maximo 1, que e tambem o valor de um shift normal em modo brute force, nao interferindo assim no resultado pois deixa a cargo da tabela do bom-sufixo o tamanho do shift final a ser dado pelo algoritmo
//		if(index > 0){
//			int badCharR = m; //valor padrão do shift quando o par de caracters procurado nao existe no padrao
//			HashMap<Character, Integer> ch2List = buildBadCharsTable.get(ch1);
//			if(ch2List != null){
//				Integer chtemp = ch2List.get(ch2);
//				if(chtemp != null) badCharR = chtemp;
//			}
//			retorno = badCharR - (m-1 - index);
//		}
//		return retorno;
//		
//	}
	
	public boolean search(String text, String pattern){
		HashMap<Character, HashMap<Character, Integer> > buildBadCharsTable = build2BadCharactersTable(pattern);
		Character ch0 = text.charAt(0);
		int goodSuffixTable[] = buildGoodSuffixTable(pattern),
			n = text.length(),
			m = pattern.length(),
			i = 0,
			j = 0;
		
		while(j <= n-m){
			for(i = m-1; i >= 0 && pattern.charAt(i) == text.charAt(i+j); i--);
			if(i < 0){
				System.out.println(j);
				j += goodSuffixTable[0];
			}
			else{
				Character ch2 = text.charAt(i+j);
				Character ch1 = text.charAt(i+j-1);
				int shiftBC = shiftBadCharacter(m, ch0, ch1, ch2, i, buildBadCharsTable);
				j += Math.max(shiftBC, goodSuffixTable[i]);
			}
		}
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		long startingTime = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
		ZhuTakaoka zhuTakaoka = new ZhuTakaoka();
		String text = reader.readLine();
		String pattern = "efgh";
		zhuTakaoka.search(text, pattern);
		//char charBuffer[] = new char[MAX_PATTERN_LENGTH*2];
		//reader.read(charBuffer, 0 , charBuffer.length);
		//System.out.println(charBuffer);
		System.out.println(text);
		System.out.println("Tempo levado: " + (System.currentTimeMillis()-startingTime)/1000.0 + " segundos");

	}

}
