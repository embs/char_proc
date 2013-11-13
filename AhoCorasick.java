import java.util.Vector;

public class AhoCorasick {
  public static Integer[][] transitionTable(String pattern) {
    Integer m = pattern.length();
    Integer n = 256; // tamanho do alfabeto
    Integer[][] tT = new Integer[m][n];
    for(int i = 0; i < n; i++) { // preenche primeira linha da tabela
      tT[0][i] = 0;
    }
    for(int j = 0; j < m; j++) {
      Integer c = (int) pattern.charAt(j);
      Integer prev = tT[j][c];
      tT[j][c] = j + 1;
      for(int k = 0; k < n && j < m -1; k++) {
        tT[j+1][k] = tT[prev][k];
      }
    }

    return tT;
  }

  public static Object[] matchString(String string, String pattern) {
    Integer[][] tT = transitionTable(pattern);
    Integer s = 0;
    Integer n = string.length();
    Integer m = pattern.length();
    Vector<Integer> occ = new Vector<Integer>();
    for(int i = 0; i < n; i++) {
      s = tT[s][(int) string.charAt(i)];
      if(s == m) {
        occ.add(i - m + 1);
        s = 0;
      }
    }

    return occ.toArray();
  }

  public static void main(String[] args) {
    // Integer[][] table = transitionTable("aba");
    // printTable(table);
    Object[] occ = matchString("abracadabra", "abr");
    for(int i = 0; i < occ.length; i++) {
      System.out.println(occ[i]);
    }
  }

  public static void printTable(Integer[][] table) {
    for(int i = 0; i < table.length; i++) {
      System.out.print(i + ": ");
      for(int j = 0; j < table[0].length; j++) {
        Integer s = table[i][j];
        if(s != 0) {
          System.out.print(" " + (char) j + ", " + table[i][j] + " ");
        }
      }
      System.out.println();
    }
  }
}