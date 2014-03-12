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
                Naive.StringTest("mississippi", 101, 1, false);
                Skew.StringTest("mississippi", 101, 1, false);

                Naive.FileTest("biblia.txt", 1, 0, true);
                Skew.FileTest("biblia.txt", 1, 0, true);
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
