using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SuffixArray
{
    class Skew
    {
        /// <summary>
        /// Verifica a ordem lexicográfica para pares de caracteres.
        /// </summary>
        /// <param name="a1">Primeiro caractere da string (representado por um inteiro).</param>
        /// <param name="a2">Nome lexicográfico referente ao primeiro caractere.</param>
        /// <param name="b1">Segundo caractere da string (representado por um inteiro).</param>
        /// <param name="b2">Nome lexicográfico referente ao segundo caractere.</param>
        /// <returns>Retorna se a1 é menor que b1 ou, se os dois forem iguais, se a2 é menor ou igual a b2.</returns>
        static bool leq(int a1, int a2, int b1, int b2)
        {
            return (a1 < b1 || a1 == b1 && a2 <= b2);
        }

        /// <summary>
        /// Verifica a ordem lexicográfica para triplas de caracteres.
        /// </summary>
        /// <param name="a1">Primeiro caractere da string (representado por um inteiro).</param>
        /// <param name="a2">Caractere seguinte da string.</param>
        /// <param name="a3">Nome lexicográfico referente aos primeiros caracteres.</param>
        /// <param name="b1">Segundo caractere da string (representado por um inteiro).</param>
        /// <param name="b2">Caractere seguinte da string.</param>
        /// <param name="b3">Nome lexicográfico referente aos segundos caracteres.</param>
        /// <returns>Retorna se a1 é menor que b1 ou, se os dois forem iguais, se a2 é menor a b2, ou se forem iguais novamente, se a3 é igual ou menos a b3.</returns>
        static bool leq(int a1, int a2, int a3, int b1, int b2, int b3)
        {
            return (a1 < b1 || a1 == b1 && leq(a2, a3, b2, b3));
        }

        /// <summary>
        /// Ordena o array a[0...n-1] com as chaves em 0..K a partir de r.
        /// </summary>
        /// <param name="a">Array a ser ordenado.</param>
        /// <param name="r">String ao qual o array se refere.</param>
        /// <param name="n">Tamanho do array.</param>
        /// <param name="K">Número de chaves (caracteres distintos) disponíveis no array.</param>
        /// <returns>O array a[0...n-1] ordenado.</returns>
        static int[] RadixPass(int[] a, int[] r, int n, int K)
        {
            int[] b = new int[a.Length];

            // count occurrences
            int[] c = new int[K + 1]; // counter array

            for (int i = 0; i <= K; i++) c[i] = 0; // reset counters
            for (int i = 0; i < n; i++) c[r[a[i]]]++; // count occurrences

            for (int i = 0, sum = 0; i <= K; i++) // exclusive prefix sums
            {
                int t = c[i];
                c[i] = sum;
                sum += t;
            }

            for (int i = 0; i < n; i++) b[c[r[a[i]]]++] = a[i]; // sort

            return b;
        }

        /// <summary>
        /// Encontra o array de sufixos SA de str[0...n-1] em {1..K}^n combinações possíveis.
        /// </summary>
        /// <param name="str">A string cujo array de sufixos será encontrado. Deve ter str[n] = str[n + 1] = s[n + 2] = 0, com n >= 2.</param>
        /// <param name="SA">O array de strings. Deve ter o mesmo tamanho que str.</param>
        /// <param name="n">Tamanho da string.</param>
        /// <param name="K">Número de chaves (caracteres distintos) da string.</param>
        static void SuffixArray(int[] str, int[] SA, int n, int K)
        {
            int n0 = (n + 2) / 3;
            int n1 = (n + 1) / 3;
            int n2 = n / 3;
            int n02 = n0 + n2;

            int[] s12 = new int[n02 + 3];
            s12[n02] = s12[n02 + 1] = s12[n02 + 2] = 0;
            int[] SA12 = new int[n02 + 3];
            SA12[n02] = SA12[n02 + 1] = SA12[n02 + 2] = 0;
            int[] s0 = new int[n0];
            int[] SA0 = new int[n0];

            // Gera as posições dos sufixos mod 3 = 1 e mod 3 = 2.
            // O " + (n0 - n1)" adiciona um sufixo extra mod 3 = 1 caso n % 3 == 1
            for (int i = 0, j = 0; i < n + (n0 - n1); i++) if (i % 3 != 0) s12[j++] = i;

            // Realiza o radix sort com as triplas mod 3 = 1 e mod 3 =2.
            SA12 = RadixPass(s12, str.Skip(1).ToArray(), n02, K); // s + 1
            s12 = RadixPass(SA12, str.Skip(2).ToArray(), n02, K); // s + 2
            SA12 = RadixPass(s12, str, n02, K);

            // Encontra os nomes lexicográficos das triplas.
            int name = 0;
            int c0 = -1;
            int c1 = -1;
            int c2 = -1;
            for (int i = 0; i < n02; i++)
            {
                if (str[SA12[i]] != c0 || str[SA12[i] + 1] != c1 || str[SA12[i] + 2] != c2)
                {
                    name++;
                    c0 = str[SA12[i]];
                    c1 = str[SA12[i] + 1];
                    c2 = str[SA12[i] + 2];
                }
                if (SA12[i] % 3 == 1)
                {
                    s12[SA12[i] / 3] = name; // Metade esquerda
                }
                else
                {
                    s12[SA12[i] / 3 + n0] = name; // Metade direita
                }
            }

            // Realiza a recursão caso os nomes não sejam únicos.
            if (name < n02)
            {
                SuffixArray(s12, SA12, n02, name);

                // Armazena os nomes únicos usando o array de sufixos.
                for (int i = 0; i < n02; i++) s12[SA12[i]] = i + 1;
            }
            else
            {
                for (int i = 0; i < n02; i++) SA12[s12[i] - 1] = i; // Gera o array de sufixos de s12 diretamente.
            }

            // Ordena os sufixos mod 3 = 0 de SA12 através do primeiro caractere.
            for (int i = 0, j = 0; i < n02; i++) if (SA12[i] < n0) s0[j++] = 3 * SA12[i];
            SA0 = RadixPass(s0, str, n0, K);

            // Faz a junção dos sufixos SA0 ordenados e dos sufixos SA12 ordenados.
            for (int p = 0, t = n0 - n1, k = 0; k < n; k++)
            {
                int i = (SA12[t] < n0 ? SA12[t] * 3 + 1 : (SA12[t] - n0) * 3 + 2);
                int j = SA0[p];

                // A comparação é diferente para sufixos mod 3 = 1 e mod 3 = 2.
                if (SA12[t] < n0 ?
                    leq(str[i], s12[SA12[t] + n0], str[j], s12[j / 3]) :
                    leq(str[i], str[i + 1], s12[SA12[t] - n0 + 1], str[j], str[j + 1], s12[j / 3 + n0]))
                {// Sufixo de SA12 é menor
                    SA[k] = i; t++;
                    if (t == n02)
                        for (k++; p < n0; p++, k++) SA[k] = SA0[p]; // done --- only SA0 suffixes left
                }
                else
                {// Sufixo de SA0 é menor
                    SA[k] = j; p++;
                    if (p == n0) // done --- only SA12 suffixes left
                        for (k++; t < n02; t++, k++) SA[k] = (SA12[t] < n0 ? SA12[t] * 3 + 1 : (SA12[t] - n0) * 3 + 2);
                }
            }
        }

        /// <summary>
        /// Cria o array de sufixos contendo todos os sufixos ordenados da string dada.
        /// </summary>
        /// <param name="str">A string cujo array de strings será criado.</param>
        /// <returns>O array de sufixos da string dada como entrada.</returns>
        static string[] SuffixArray(string str)
        {
            var s = ToIntegerAlphabet(str);         // Transforma a string para ser usada como entrada no algoritmo
            var SA = new int[s.Length];             // Cria o array que servirá pra armazenar as posições dos sufixos do array de sufixos
            var n = str.Length;                     // Obtem o tamanho da string
            var K = str.Distinct().Count();         // Obtem o numero de caracteres distintos que são usados na string (tamanho do alfabeto)

            var clock = new Stopwatch();

            clock.Start();
            SuffixArray(s, SA, n, K);               // Obtem o array de sufixos da string
            clock.Stop();

            Console.WriteLine("Tempo: " + (double)1000 * clock.ElapsedTicks / Stopwatch.Frequency + "ms.");

            var suffixes = new string[str.Length];  // Cria um array de strings aonde serão armazenadas os sufixos do array de sufixos

            for (int i = 0; i < str.Length; i++)    // Para cada sufixo que estão dentro do tamanho da string
            {
                suffixes[i] = str.Substring(SA[i]); // Obtenha a substring relativa ao sufixo e adicione na lista de strings
            }

            return suffixes;
        }

        /// <summary>
        /// Cria o array de sufixos contendo todos os sufixos ordenados da string dada.
        /// </summary>
        /// <param name="str">A string cujo array de strings será criado.</param>
        /// <returns>O array de sufixos da string dada como entrada.</returns>
        static long SuffixArrayTime(string str)
        {
            var s = ToIntegerAlphabet(str);         // Transforma a string para ser usada como entrada no algoritmo
            var SA = new int[s.Length];             // Cria o array que servirá pra armazenar as posições dos sufixos do array de sufixos
            var n = str.Length;                     // Obtem o tamanho da string
            var K = str.Distinct().Count();         // Obtem o numero de caracteres distintos que são usados na string (tamanho do alfabeto)

            var clock = new Stopwatch();

            clock.Start();
            SuffixArray(s, SA, n, K);               // Obtem o array de sufixos da string
            clock.Stop();

            return clock.ElapsedTicks;
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

        public static void StringTest(string str, int numTests = 1, int skipTest = 0, bool printEach = false)
        {
            List<double> sample = new List<double>();
            for (int i = 0; i < numTests; i++)
            {
                var result = SuffixArrayTime(str);
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

        public static void FileTest(string filename, int numTests = 1, int skipTest = 0, bool printEach = false)
        {
            string text = System.IO.File.ReadAllText(filename, UTF8Encoding.UTF8);
            List<double> sample = new List<double>();
            for (int i = 0; i < numTests; i++)
            {
                var result = SuffixArrayTime(text);
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

        public static void SuffixList(string str)
        {
            var result = SuffixArray(str);
            Console.WriteLine("Lista de sufixos da palavra \"" + str + "\" (n = " + str.Length + "):");
            foreach (var item in result) Console.WriteLine(item);
        }
    }
}
