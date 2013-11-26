import java.util.Vector;

public class Sellers {

  public static void usingTable(String text, String pattern, int r) {
    int n = text.length();
    int m = pattern.length();
    int[][] table = new int[m+1][n+1];
    Vector<Integer> occ = new Vector<Integer>();
    for(int i = 0; i < m+1; i++) { // inicializa primeira coluna
      table[i][0] = i;
    }
    for(int i = 1; i < m+1; i++) {
      for(int j = 1; j < n+1; j++) {
        table[i][j] = min(table[i-1][j-1] + phi(text.charAt(j-1), pattern.charAt(i-1)),
          table[i-1][j] +1, table[i][j-1] +1);
        if(i == m && table[i][j] <= r)
          occ.add(new Integer(j));
      }
    }

    print(table);
    System.out.println(occ);
  }

  public static void usingVectors(String text, String pattern, int r) {
    int n = text.length();
    int m = pattern.length();
    int[] s1 = new int[m+1]; // vetor coluna s
    int[] s2 = new int[m+1]; // vetor coluna s'
    Vector<Integer> occ = new Vector<Integer>();
    for(int i = 0; i < m+1; i++) {
      s2[i] = i;
    }
    for(int j = 1; j < n+1; j++) {
      print(s1, s2);
      s1[0] = 0;
      for(int i = 1; i < m+1; i++) {
        s1[i] = min(s2[i-1] + phi(text.charAt(j-1), pattern.charAt(i-1)), s1[i-1] + 1,
          s2[i] +1);
      }
      s2[m] = s1[m];
      if(s1[m] <= r)
        occ.add(new Integer(j));
    }

      print(s1, s2);
    System.out.println(occ);
  }

  public static void main(String[] args) {
    String text = "ABADAC";
    String pattern = "CADA";
    int r = 3;
    // usingVectors(text, pattern, r);
    usingTable(text, pattern, r);
  }

  private static int phi(char a, char b) {
    if(a == b)
      return 0;
    else
      return 1;
  }

  private static int min(int a, int b, int c) {
    return Math.min(Math.min(a,b),c);
  }

  private static void print(int[] arr, int[] arrr) {
    for(int i = 0; i < arr.length; i++) {
      System.out.println(arr[i] + " " + arrr[i]);
    }
    System.out.println();
  }

  private static void print(int[][] table) {
    for(int i = 0; i < table.length; i++) {
      for(int j = 0; j < table[0].length; j++) {
        System.out.print(table[i][j] + " ");
      }
      System.out.println();
    }
  }
}
