import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class ZhuTakaoka extends BoyerMoore {

	public HashMap<Character, HashMap<Character, Integer> > build2BadCharactersTable(String pattern){
		HashMap<Character, HashMap<Character, Integer> > badCharTable = new HashMap<Character, HashMap<Character, Integer> >();
		int m = pattern.length();
		for(int i=1; i<m ; i++){
			HashMap<Character, Integer> ch2List = badCharTable.get(pattern.charAt(i-1));
			if(ch2List == null) {
				ch2List = new HashMap<Character, Integer>();
				badCharTable.put(pattern.charAt(i-1), ch2List);
			}
			ch2List.put(pattern.charAt(i), m-1-i);
		}

		debug2BadCharacter(BMZTUtil.DEBUG_BAD_CHARACTER_TABLE, badCharTable);
		return badCharTable;
	}

	public int shiftBadCharacter(int m, Character ch0, Character ch1, Character ch2, int index, HashMap<Character, HashMap<Character, Integer> > buildBadCharsTable){
		int retorno = 1; //valor default para quanto index = 0, para o qual qualquer combinacao 'ya', tal que 'y' e um caractere qualquer do alfabeto, e 'a' e pattern.charAt(index), levando em conta que o indice de mismatching('index') tambem e 0; nesse contexto, o valor final do shift a ser dado pela heuristica do mau-caractere do zhu takaoka seria negativo, ou no maximo 1, que e tambem o valor de um shift normal em modo brute force, nao interferindo assim no resultado pois deixa a cargo da tabela do bom-sufixo o tamanho do shift final a ser dado pelo algoritmo
		if(index > 0){
			int badCharR = m; //valor padrao do shift quando o par de caracters procurado nao existe no padrao
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

	public Integer[] search(String text, String pattern){
		ArrayList<Integer> retorno = new ArrayList<>();
		HashMap<Character, HashMap<Character, Integer> > buildBadCharsTable = build2BadCharactersTable(pattern);
		Character ch0 = pattern.charAt(0);
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
				Character ch2 = text.charAt(i+j);
				Character ch1 = text.charAt(i+j-1);
				int shiftBC = shiftBadCharacter(m, ch0, ch1, ch2, i, buildBadCharsTable);
				int shiftFinal = Math.max(shiftBC, goodSuffixTable[i]);
				if(BMZTUtil.PRINT_SHIFT)
					System.out.println(shiftFinal + " [" + shiftBC + ", " + goodSuffixTable[i] + "]");
				j += shiftFinal;
			}
		}
		// if(BMZTUtil.PRINT_BENCHMARK)
		// 	System.out.println("Char inspecs ("+n+" chars text, "+m+" chars pattern, Zhu-Takaoka): " + charInspecs);
		System.out.println();
		return retorno.toArray(new Integer[retorno.size()]);
	}

	public void debug2BadCharacter(boolean DEBUG, HashMap<Character, HashMap<Character, Integer> > badCharTable){
		if(DEBUG){
			Character ckeys[] = badCharTable.keySet().toArray(new Character[badCharTable.size()]);
			Arrays.sort(ckeys);

			HashSet<Character> ch2keysSet = new HashSet<>();
			for(int i=0; i<badCharTable.size(); i++){
				Set<Character> locch2keys = badCharTable.get(ckeys[i]).keySet();
				ch2keysSet.addAll(locch2keys);
			}
			Character ch2keys[] = ch2keysSet.toArray(new Character[ch2keysSet.size()]);
			Arrays.sort(ch2keys);

			String ch2keysStr[] = BMZTUtil.objectsToSTrings(ch2keys);

			HashMap<String, ArrayList<String>> values = new HashMap<String, ArrayList<String>>();
			for(int j=0; j<ch2keysStr.length; j++){
				values.put(ch2keysStr[j], new ArrayList<String>());
			}
			for(int i=0; i<ckeys.length; i++){
				HashMap<Character, Integer> ch2List = badCharTable.get(ckeys[i]);
				for(int j=0; j<ch2keys.length; j++){
					Integer value = ch2List.get(ch2keys[j]);
					if(value != null)
						values.get(ch2keysStr[j]).add(value.toString());
					else
						values.get(ch2keysStr[j]).add("");
				}
			}

			String ch1keysStr[] = BMZTUtil.objectsToSTrings(ckeys);

			BMZTUtil.printAsTable("BdCh Table", ch2keysStr, values, 3, badCharTable.size(), ch1keysStr);
		}
	}

}
