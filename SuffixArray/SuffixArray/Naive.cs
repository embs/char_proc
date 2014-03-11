using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SuffixArray
{
    class Naive
    {

        /// <summary>
        /// Encontra o array de sufixos SA de str[0...n-1] em {1..K}^n combinações possíveis.
        /// </summary>
        /// <param name="str">A string cujo array de sufixos será encontrado. Deve ter str[n] = str[n + 1] = s[n + 2] = 0, com n >= 2.</param>
        /// <param name="SA">O array de strings. Deve ter o mesmo tamanho que str.</param>
        /// <param name="n">Tamanho da string.</param>
        /// <param name="K">Número de chaves (caracteres distintos) da string.</param>
        public static void SuffixArray(int[] str, /*int[] SA,*/ int n/*, int K*/)
        {

            int[] rank = new int[n];

            for (int i = 0; i < n; i++) rank[i] = 0;

            for (int i = 0; i < n; i++)          // Comparar os sufixos
            {
                int rankAtual = 0;
                for (int j = 0; j < n; j++)      // Com relação a outros sufixos
                {
                    if (i != j)
                    {
                        if (MoreThan(i, j, str, n))   // Se a letra for maior (z > a)
                        {
                            rank[j]++;
                        }

                        if (LessThan(i, j, str, n))    // Se a letra for menor (a > z)
                        {
                            rankAtual++;
                        }
                    }
                }
                rank[i] = rankAtual;
            }

            int[] x = { 10, 9, 9 };

            Console.WriteLine(MoreThan(0, 1, x, 3));


        }

        public static string[] SuffixArray(string str)
        {
            var s = ToIntegerAlphabet(str);         // Transforma a string para ser usada como entrada no algoritmo
            var SA = new int[s.Length];             // Cria o array que servirá pra armazenar as posições dos sufixos do array de sufixos
            var n = str.Length;                     // Obtem o tamanho da string
            var K = str.Distinct().Count();         // Obtem o numero de caracteres distintos que são usados na string (tamanho do alfabeto)

            SuffixArray(s, n);               // Obtem o array de sufixos da string

            return null;
        }

        static bool MoreThan(int index1, int index2, int[] str, int n)
        {

            for (int i = index1, j = index2; (i < n) || (j < n); i++, j++)
            {

                if (str[i] > str[j]) return true;
                if (str[i] < str[j]) return false;

            }
            return false;
        }

        static bool LessThan(int index1, int index2, int[] str, int n)
        {

            for (int i = index1, j = index2; (i < n) || (j < n); i++, j++)
            {

                if (str[i] < str[j]) return true;
                if (str[i] > str[j]) return false;

            }
            return false;
        }

        /// <summary>
        /// Transforma uma string em um array com menores inteiros lexicograficamente equivalentes.
        /// </summary>
        /// <param name="str">String a ser transformada.</param>
        /// <returns>Array de inteiros lexicograficamente equivalentes.</returns>
        static int[] ToIntegerAlphabet(string str)
        {
            char[] strSep = str.ToCharArray();                      // Transformando em array de chars
            int[] intStr = new int[str.Length + 3];                 // Criando o array de inteiros (com tres espacos a mais, usados na criação do array de sufixos)

            var sdc = str.Distinct().ToArray();                     // Obtem a lista de caracteres diferentes usados na string
            Array.Sort(sdc);                                        // Ordena a lista de caracteres

            for (int i = 0; i < intStr.Length; i++) intStr[i] = 0;  // Inicializando o array de inteiros

            for (int i = 0; i < strSep.Length; i++)                 // Para cada letra da string
            {
                intStr[i] = Array.IndexOf(sdc, strSep[i]) + 1;      // Coloque o valor inteiro correspondente a ela no array de inteiros
            }

            return intStr;                                          // Retorna o array de inteiros
        }

    }
}