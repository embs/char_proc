public class BruteForce {
  public static void main(String[] args) {
    String text = "abracadabrabr";
    String pattern = "abr";

    int i = 0;
    int j = 0;
    int m = text.length();
    int n = pattern.length();
    int[] occ = new int[100];
    int occI = 0;

    while(i < (m - n + 1)) {
      System.out.println("Arre");
      while((j < n - 1) && text.charAt(i) == pattern.charAt(j)) {
        j += 1;
        i += 1;
      }
      if(j == n-1) {
        System.out.println("Match!");
        occ[occI] = i - n + 1;
        occI += 1;
      }
      i += 1;
      j = 0;
    }

    for(int x = 0; x < occI; x += 1) {
      System.out.println(occ[x]);
    }
  }
}