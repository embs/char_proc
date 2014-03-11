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
                //Naive.SuffixArray("mississippi");

                //var str = args[0];

                var filename = "biblia.txt";
                string str = System.IO.File.ReadAllText(filename, UTF8Encoding.UTF8);
                //var str = "mississippi";

                List<double> sample = new List<double>();

                var numTestes = 5;

                for (int i = 0; i < numTestes; i++)
                {
                    var strTest = str;
                    var result = Skew.SuffixArrayTime(strTest);
                    GC.Collect();
                    strTest = null;

                    Console.WriteLine("Tempo: " + (double)1000 * result / Stopwatch.Frequency + "ms.");
                    sample.Add((double)1000 * result / Stopwatch.Frequency);
                }

                var media = Statistics.Mean(sample);
                var sd = Statistics.StandardDeviation(sample);

                Console.WriteLine("Texto testado: " + filename + " n = " + str.Length + " Numero de testes: " + numTestes);
                Console.WriteLine("Tempo medio: " + media + "ms.");
                Console.WriteLine("Desvio padrao: " + sd + "ms.");

                //Console.WriteLine("Tempo: " + (double)1000 * result / Stopwatch.Frequency + "ms.");

                //Console.WriteLine("Lista de sufixos da palavra \"" + str + "\" (n = " + str.Length + "):");
                //foreach (var item in result) Console.WriteLine(item);
            }
            catch (Exception e)
            {
                Console.WriteLine("Um erro ocorreu durante a execução do programa. Detalhes do erro:");
                Console.WriteLine(e.StackTrace + e.Message);
            }
            Console.ReadKey();
        }
    }
}
