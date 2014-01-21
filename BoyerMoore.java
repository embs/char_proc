import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;


public class BoyerMoore {
	static final int MAX_PATTERN_LENGTH = 256;
	
	class MyBuffer{
		static final int TEXT_BULK_SIZE = 1024;
		Reader reader;
		char buffer1[], 
			 buffer2[], 
			 buffer3[];
		int greatestIndex;
		
		public MyBuffer(Reader reader){
			this.reader = reader;
			greatestIndex = 0;
			buffer1 = new char[MAX_PATTERN_LENGTH];
			buffer2 = new char[TEXT_BULK_SIZE];
			buffer3 = new char[MAX_PATTERN_LENGTH];
		}
		
		public char at(int index){
			if(index >= greatestIndex){
				int remainder = index % MAX_PATTERN_LENGTH*2;
				
			}
			return 0;
		}
		
		public boolean more(){
			return true;
		}
	}
	
	//int badCharTable[] = new int[36];
	//HashMap<Character, Integer> badCharTable = new HashMap<Character, Integer>();
	//int goodSuffixTable[];
	
	
	public HashMap<Character, Integer> buildBadCharacterTable(String pattern){
		HashMap<Character, Integer> badCharTable = new HashMap<Character, Integer>();
		int m = pattern.length();
		for (int i = 0; i < m-1; i++){
			badCharTable.put(pattern.charAt(i), m-1-i);
		}
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
			if(i == -1 || suff[i] == i + 1){ //se sufixo de 'i' for tal que o primeiro caractere de pattern também faz parte do sufixo, ou seja, se suff[i] for prefixo de pattern
				for(; j<m-1-i; ++j){ //
					if(goodSuffixTable[j] == m)
						goodSuffixTable[j] = m-1-i;
				}
			}
		}
		for(i = 0; i<=m-2; ++i)
			goodSuffixTable[m-1-suff[i]] = m-1-i;
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
			//i > leftEnd garante que o caractere no indice 'i' já foi considerado como sufixo de algum outro 'i+u, u>0' posterior
			//sendo assim, existiu um j > i, tal que pattern[j] = pattern[i], tal que j participou de um sufixo de m-1
			//logo, se existir um sufixo de j de tamanho estritamente menor que o sufixo de 'i-LeftEnd' (nao poderia ser igual pois implicaria dizer que o caracteres mais a esquerda, que nao faz parte do sufixo de j, necessariamente teria que ter um correspondente numa posicao relativa a i), logo esse sufixo também existe como sufixo de i
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
		return suff;
	}
	
	public boolean search(String text, String pattern){
		HashMap<Character, Integer> badCharTable = buildBadCharacterTable(pattern);
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
				Integer badCharR = badCharTable.get(text.charAt(i+j));
				if(badCharR == null) badCharR = m;
				j += Math.max(badCharR - m + 1 + i, goodSuffixTable[i]);
			}
		}
		return true;
	}
	
	public static void main(String []args) throws IOException{
		long startingTime = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
		BoyerMoore boyerMoore = new BoyerMoore();
		String text = reader.readLine();
		String pattern = "efgh";
		boyerMoore.search(text, pattern);
		//char charBuffer[] = new char[MAX_PATTERN_LENGTH*2];
		//reader.read(charBuffer, 0 , charBuffer.length);
		//System.out.println(charBuffer);
		System.out.println(text);
		System.out.println("Tempo levado: " + (System.currentTimeMillis()-startingTime)/1000.0 + " segundos");
	}
}
