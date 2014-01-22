public class Benchmark {

  public void benchCharInspecs(BoyerMoore algorithm) {
    String text = BMZTUtil.readFromFile("/home/embs/Code/char_proc/text_500k_10.txt");
    String pattern = BMZTUtil.readFromFile("/home/embs/Code/char_proc/pattern_8_10.txt");
    algorithm.search(text, pattern);
    for(int i = 10; i < 101; i = i + 5) {
      pattern = BMZTUtil.readFromFile(String.format("/home/embs/Code/char_proc/pattern_%d_10.txt", i));
      algorithm.search(text, pattern);
    }
  }

  public void benchExecutionTime(BoyerMoore algorithm) {
    String text = BMZTUtil.readFromFile("/home/embs/Code/char_proc/text_500k_10.txt");
    String pattern = BMZTUtil.readFromFile("/home/embs/Code/char_proc/pattern_8_10.txt");
    int[] alphabetLengths = {15, 20, 30, 50};
    int[] patternLengths = {100, 200, 400};
    for(int alphabetLength : alphabetLengths) {
      text = BMZTUtil.readFromFile(String.format("/home/embs/Code/char_proc/text_1kk_%d.txt", alphabetLength));
      for(int patternLength : patternLengths) {
        pattern = BMZTUtil.readFromFile(String.format("/home/embs/Code/char_proc/pattern_%d_%d.txt", patternLength, alphabetLength));
        long start = System.nanoTime();
        algorithm.search(text, pattern);
        long stop = System.nanoTime();
        long elapsedTime = stop - start;
        double seconds = (double)elapsedTime / 1000000000.0;
        System.out.println(String.format("ExecutionTime (1kk chars text, %d chars pattern, %d chars alphabet, %s): %f",
          patternLength, alphabetLength, algorithm.getClass().toString(), seconds));
      }
    }
  }

  public static void main(String[] args) {
    Benchmark bench = new Benchmark();
    BoyerMoore boyerMoore = new BoyerMoore();
    ZhuTakaoka zhuTakaoka = new ZhuTakaoka();
    /*
     *  Chars inspects benchmark
     */
    // bench.benchCharInspecs(boyerMoore);
    // bench.benchCharInspecs(zhuTakaoka);

    /*
     *  Execution time benchmark
     */
    bench.benchExecutionTime(boyerMoore);
    bench.benchExecutionTime(zhuTakaoka);
  }
}