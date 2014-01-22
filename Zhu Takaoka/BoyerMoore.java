import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


public class BoyerMoore {

//	static final int MAX_PATTERN_LENGTH = 256;
//	class MyBuffer{
//		static final int TEXT_BULK_SIZE = 1024;
//		Reader reader;
//		char buffer1[],
//			 buffer2[],
//			 buffer3[];
//		int greatestIndex;
//
//		public MyBuffer(Reader reader){
//			this.reader = reader;
//			greatestIndex = 0;
//			buffer1 = new char[MAX_PATTERN_LENGTH];
//			buffer2 = new char[TEXT_BULK_SIZE];
//			buffer3 = new char[MAX_PATTERN_LENGTH];
//		}
//
//		public char at(int index){
//			if(index >= greatestIndex){
//				int remainder = index % MAX_PATTERN_LENGTH*2;
//
//			}
//			return 0;
//		}
//
//		public boolean more(){
//			return true;
//		}
//	}

	//-----------------FUNCOES DE DEBUG E PRINT----------------------------------------------------------------------------

	protected void debugBadCharacter(boolean DEBUG, HashMap<Character, Integer> badCharTable){
		if(DEBUG){
			Character ckeys[] = badCharTable.keySet().toArray(new Character[badCharTable.size()]);
			Arrays.sort(ckeys);

			String keys[] = new String[badCharTable.size()];
			for(int i=0; i<keys.length; i++){
				keys[i] = ckeys[i].toString();
			}
			HashMap<String, ArrayList<String>> values = new HashMap<String, ArrayList<String>>();
			for(int i=0; i<keys.length; i++){
				values.put(keys[i], new ArrayList<>(Arrays.asList(badCharTable.get(ckeys[i]).toString())));
			}
			BMZTUtil.printAsTable("BdCh Table", keys, values, 3, 1, null);
		}
	}

	protected void debugSuffix(boolean DEBUG, String title, int[] suff){
		if(DEBUG){
			String keys[] = new String[suff.length];

			for(int k=0; k<suff.length; k++){
				keys[k] = Integer.toString(k);
			}
			HashMap<String, ArrayList<String>> values = new HashMap<String, ArrayList<String>>();
			for(int k=0; k<keys.length; k++){
				values.put(keys[k], new ArrayList<>(Arrays.asList(suff[k]+"")));
			}
			BMZTUtil.printAsTable(title, keys, values, 3, 1, null);
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------

	public HashMap<Character, Integer> buildBadCharacterTable(String pattern){
		HashMap<Character, Integer> badCharTable = new HashMap<Character, Integer>();
		int m = pattern.length();
		for (int i = 0; i < m-1; i++){
			badCharTable.put(pattern.charAt(i), m-1-i);
		}
		debugBadCharacter(BMZTUtil.DEBUG_BAD_CHARACTER_TABLE, badCharTable);
		return badCharTable;
	}

	public int[] buildGoodSuffixTable(String pattern){
		int suff[] = buildSuffixTable(pattern),
			m = pattern.length(),
			goodSuffixTable[] = new int[m],
			i = 0,
			j = 0;

		for(i=0; i<m; i++){
			goodSuffixTable[i] = m;
		}
		for(i=m-1; i>=-1; i--){
			if(i == -1 || suff[i] == i + 1){ //se sufixo de 'i' for tal que o primeiro caractere de pattern tambem faz parte do sufixo, ou seja, se suff[i] for prefixo de pattern
				for(; j<m-1-i; ++j){ //
					if(goodSuffixTable[j] == m)
						goodSuffixTable[j] = m-1-i;
				}
			}
		}
		for(i = 0; i<=m-2; ++i)
			goodSuffixTable[m-1-suff[i]] = m-1-i;
		debugSuffix(BMZTUtil.DEBUG_GOOD_SUFFIX_TABLE, "GsSf Table", goodSuffixTable);
		return goodSuffixTable;
	}

	protected int[] buildSuffixTable(String pattern){
		int m = pattern.length(),
			suff[] = new int[m],
			leftEnd = m-1,
			leftEndAim = 0;
		suff[m-1] = m;
		for(int i = m-2; i>=0; i--){
			//leftEnd corresponde a posicao anterior a um indice 'leftEnd+1', cujo caractere foi considerado parte de sufixo de um i qualquer
			//i > leftEnd garante que o caractere no indice 'i' ja foi considerado como sufixo de algum outro 'i+u, u>0' posterior
			//sendo assim, existiu um j > i, tal que pattern[j] = pattern[i], tal que j participou de um sufixo de m-1
			//logo, se existir um sufixo de j de tamanho estritamente menor que o sufixo de 'i-LeftEnd' (nao poderia ser igual pois implicaria dizer que o caracteres mais a esquerda, que nao faz parte do sufixo de j, necessariamente teria que ter um correspondente numa posicao relativa a i), logo esse sufixo tambem existe como sufixo de i
			if(i > leftEnd && suff[i + m - 1 - leftEndAim] < i - leftEnd)
				suff[i] = suff[i + m  - 1 - leftEndAim];
			else{ //transcorre normalmente o padrao procurando por sufixos. Para isso, leftEnd eh transladado para a esquerda continuamente
				if (i < leftEnd)
					leftEnd = i;
				leftEndAim = i;
				while(leftEnd >= 0 && pattern.charAt(leftEnd) == pattern.charAt(leftEnd + m - 1 - leftEndAim))
					leftEnd--;
				suff[i] = leftEndAim - leftEnd;
			}
		}
		debugSuffix(BMZTUtil.DEBUG_SUFFIX_TABLE, "Sf Table", suff);
		return suff;
	}

	public Integer[] search(String text, String pattern){
		ArrayList<Integer> retorno = new ArrayList<>();
		HashMap<Character, Integer> badCharTable = buildBadCharacterTable(pattern);
		int goodSuffixTable[] = buildGoodSuffixTable(pattern),
			n = text.length(),
			m = pattern.length(),
			i = 0,
			j = 0,
			charInspecs = 0;

		if(BMZTUtil.PRINT_SHIFT)
			System.out.println("Shift final [BadCharacter Shift, GoodSuffix Shift]");
		while(j <= n-m){
			charInspecs++;
			for(i = m-1; i >= 0 && pattern.charAt(i) == text.charAt(i+j); i--);
			if(i < 0){
				retorno.add(j);
				j += goodSuffixTable[0];
				if(BMZTUtil.PRINT_SHIFT)
					System.out.println(goodSuffixTable[0] + " [MATCH]");
			}
			else{
				Integer badCharR = badCharTable.get(text.charAt(i+j));
				if(badCharR == null) badCharR = m;
				int shiftBC = badCharR - m + 1 + i;
				int shiftFinal = Math.max(shiftBC, goodSuffixTable[i]);
				if(BMZTUtil.PRINT_SHIFT)
					System.out.println(shiftFinal + " [" + shiftBC + ", " + goodSuffixTable[i] + "]");
				j += shiftFinal;
			}
		}
		// if(BMZTUtil.PRINT_BENCHMARK)
		// 	System.out.println("Char inspecs ("+n+" chars text, "+m+" chars pattern, Boyer-Moore): " + charInspecs);
		System.out.println();
		return retorno.toArray(new Integer[retorno.size()]);
	}

}
