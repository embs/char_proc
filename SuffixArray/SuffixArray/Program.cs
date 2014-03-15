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
                //SuffixList("mississippi");
                //Naive.StringTest("banana", 1000001, 1, false);
                //Skew.StringTest("banana", 1000001, 1, false);
                var test = RandomString(4, 100);
                Naive.StringTest(test, 1000001, 1, false);
                Skew.StringTest(test, 1000001, 1, false);
                
                //Skew.FileTest("E.coli", 21, 1, true);
                //Skew.FileTest("shakespeare.txt", 21, 1, true);
            }
            catch (Exception e)
            {
                Console.WriteLine("Um erro ocorreu durante a execução do programa. Detalhes do erro:");
                Console.WriteLine(e.StackTrace + e.Message);
            }
            Console.ReadKey();
        }

        static string RandomString(int chars, int size)
        {
            var alphabet = "abcdefghijklmnopqrstuvwxyz";
            var random = new Random();
            var result = new string(
                Enumerable.Repeat(alphabet.Substring(alphabet.Length - chars, chars), size)
                .Select(s => s[random.Next(s.Length)])
                .ToArray());

            return result;
        }


    }
}
