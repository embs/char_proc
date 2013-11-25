public class EditDistance {

  public static int[][] calculate(String text, String pattern) {
    int m = pattern.length();
    int n = text.length();
    int[][] table = new int[m+1][n+1];
    for(int i = 0; i < m+1; i++) { // inicializa primeira coluna
      table[i][0] = i;
    }
    for(int i = 0; i < n+1; i++) { // inicializa primeira linha
      table[0][i] = i;
    }

    for(int i = 1; i < m+1; i++) {
      for(int j = 1; j < n+1; j++) {
        table[i][j] = min(table[i-1][j-1] + phi(pattern.charAt(i-1), text.charAt(j-1)),
          table[i-1][j] + 1, table[i][j-1] + 1);
      }
    }

    printTable(table);

    return table;
  }

  public static void main(String[] args) {
    calculate("ABADAC", "CADA");
  }

  private static void printTable(int[][] table) {
    for(int i = 0; i < table.length; i++) {
      for(int j = 0; j < table[0].length; j++) {
        System.out.print("" + table[i][j] + " ");
      }
      System.out.println();
    }
  }

  private static int min(int x, int y, int z) {
    return Math.min(Math.min(x,y), z);
  }

  private static int phi(char a, char b) {
    if(a == b)
      return 0;
    else
      return 1;
  }
}
