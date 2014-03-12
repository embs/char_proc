using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SuffixArray
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                //StringTest("mississippi", 101, 1, true);
                //FileTest("biblia.txt", 51, 1, true);
                SuffixList("mississippi");
            }
            catch (Exception e)
            {
                Console.WriteLine("Um erro ocorreu durante a execução do programa. Detalhes do erro:");
                Console.WriteLine(e.StackTrace + e.Message);
            }
            Console.ReadKey();
        }

        static void StringTest(string str, int numTests = 1, int skipTest = 0, bool printEach = false)
        {
            List<double> sample = new List<double>();
            for (int i = 0; i < numTests; i++)
            {
                var result = Skew.SuffixArrayTime(str);
                if (i + 1 > skipTest)
                {
                    if (printEach) Console.WriteLine("Tempo: " + (double)1000 * result / Stopwatch.Frequency + "ms.");
                    sample.Add(result);
                }
            }

            var media = Statistics.Mean(sample);
            var sd = Statistics.StandardDeviation(sample);

            Console.WriteLine("String testada: " + str + " n = " + str.Length + " Numero de testes: " + (numTests - skipTest));
            Console.WriteLine("Tempo medio: " + (double)1000 * media / Stopwatch.Frequency + "ms.");
            Console.WriteLine("Desvio padrao: " + (double)1000 * sd / Stopwatch.Frequency + "ms.");
        }

        static void FileTest(string filename, int numTests = 1, int skipTest = 0, bool printEach = false)
        {
            string text = System.IO.File.ReadAllText(filename, UTF8Encoding.UTF8);
            List<double> sample = new List<double>();
            for (int i = 0; i < numTests; i++)
            {
                var result = Skew.SuffixArrayTime(text);
                if (i + 1 > skipTest)
                {
                    if (printEach) Console.WriteLine("Tempo: " + (double)1000 * result / Stopwatch.Frequency + "ms.");
                    sample.Add(result);
                }
            }

            var media = Statistics.Mean(sample);
            var sd = Statistics.StandardDeviation(sample);

            Console.WriteLine("Texto testado: " + filename + " n = " + text.Length + " Numero de testes: " + (numTests - skipTest));
            Console.WriteLine("Tempo medio: " + (double)1000 * media / Stopwatch.Frequency + "ms.");
            Console.WriteLine("Desvio padrao: " + (double)1000 * sd / Stopwatch.Frequency + "ms.");
        }

        static void SuffixList(string str)
        {
            var result = Skew.SuffixArray(str);
            Console.WriteLine("Lista de sufixos da palavra \"" + str + "\" (n = " + str.Length + "):");
            foreach (var item in result) Console.WriteLine(item);
        }
    }
}
