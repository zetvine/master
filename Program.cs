using System;
using System.Collections.Generic;
using System.Linq;

class Program
{
    static void Main(string[] args)
    {
        Console.WriteLine("Введите строку:");
        string input = Console.ReadLine();

        // Проверка входной строки на наличие только букв в нижнем регистре английского алфавита
        if (!IsLowerCaseEnglishAlphabet(input))
        {
            Console.WriteLine("Ошибка: В строке присутствуют неподходящие символы.");
            Console.WriteLine("Неподходящие символы:");
            foreach (char c in input.Where(c => !char.IsLower(c) || c < 'a' || c > 'z'))
            {
                Console.WriteLine(c);
            }
        }
        else
        {
            // Обработка строки в соответствии с условиями задания из "Задания 4"
            string processedString = ProcessString(input);
            Console.WriteLine("Обработанная строка:");
            Console.WriteLine(processedString);

            // Получение информации о количестве повторений каждого символа в обработанной строке
            Dictionary<char, int> charCount = CountCharacters(processedString);
            Console.WriteLine("Информация о повторении символов:");
            foreach (var kvp in charCount)
            {
                Console.WriteLine($"Символ '{kvp.Key}' встречается {kvp.Value} раз(а).");
            }

            // Нахождение самой длинной подстроки, начинающейся и заканчивающейся на гласную
            string longestVowelSubstring = FindLongestVowelSubstring(processedString);
            Console.WriteLine("Самая длинная подстрока с началом и концом из 'aeiouy':");
            Console.WriteLine(longestVowelSubstring);

            // Спрашиваем у пользователя, какой алгоритм сортировки использовать
            Console.WriteLine("Выберите алгоритм сортировки: (1 - Быстрая сортировка, 2 - Сортировка деревом)");
            int sortingAlgorithm = int.Parse(Console.ReadLine());

            // Сортировка обработанной строки в соответствии с выбранным алгоритмом
            string sortedString = string.Empty;
            if (sortingAlgorithm == 1)
            {
                sortedString = QuickSort(processedString);
                Console.WriteLine("Отсортированная обработанная строка (Быстрая сортировка):");
            }
            else if (sortingAlgorithm == 2)
            {
                sortedString = TreeSort(processedString);
                Console.WriteLine("Отсортированная обработанная строка (Сортировка деревом):");
            }
            else
            {
                Console.WriteLine("Ошибка: Неверный выбор алгоритма сортировки.");
            }

            Console.WriteLine(sortedString);
        }
    }

    // Метод для проверки входной строки на наличие только букв в нижнем регистре английского алфавита
    static bool IsLowerCaseEnglishAlphabet(string input)
    {
        return input.All(c => char.IsLower(c) && c >= 'a' && c <= 'z');
    }

    // Метод для обработки входной строки в соответствии с условиями задания из "Задания 4"
    static string ProcessString(string input)
    {
        if (input.Length % 2 == 0)
        {
            int halfLength = input.Length / 2;
            string firstHalfReversed = new string(input.Take(halfLength).Reverse().ToArray());
            string secondHalfReversed = new string(input.Skip(halfLength).Reverse().ToArray());
            return firstHalfReversed + secondHalfReversed;
        }
        else
        {
            string reversedInput = new string(input.Reverse().ToArray());
            return reversedInput + input;
        }
    }

    // Метод для подсчета количества повторений каждого символа в строке
    static Dictionary<char, int> CountCharacters(string input)
    {
        Dictionary<char, int> charCount = new Dictionary<char, int>();
        foreach (char c in input)
        {
            if (charCount.ContainsKey(c))
            {
                charCount[c]++;
            }
            else
            {
                charCount[c] = 1;
            }
        }
        return charCount;
    }

    // Метод для поиска самой длинной подстроки, начинающейся и заканчивающейся на гласную букву из "aeiouy"
    static string FindLongestVowelSubstring(string input)
    {
        string longestSubstring = string.Empty;
        string vowels = "aeiouy";
        for (int i = 0; i < input.Length; i++)
        {
            if (vowels.Contains(input[i]))
            {
                for (int j = i + 1; j < input.Length; j++)
                {
                    if (vowels.Contains(input[j]))
                    {
                        string currentSubstring = input.Substring(i, j - i + 1);
                        if (currentSubstring.Length > longestSubstring.Length)
                        {
                            longestSubstring = currentSubstring;
                        }
                    }
                }
            }
        }
        return longestSubstring;
    }

    // Метод для быстрой сортировки строки
    static string QuickSort(string input)
    {
        char[] arr = input.ToCharArray();
        QuickSort(arr, 0, arr.Length - 1);
        return new string(arr);
    }

    static void QuickSort(char[] arr, int left, int right)
    {
        if (left < right)
        {
            int pivotIndex = Partition(arr, left, right);
            QuickSort(arr, left, pivotIndex - 1);
            QuickSort(arr, pivotIndex + 1, right);
        }
    }

    static int Partition(char[] arr, int left, int right)
    {
        char pivot = arr[right];
        int i = left - 1;
        for (int j = left; j < right; j++)
        {
            if (arr[j] <= pivot)
            {
                i++;
                Swap(arr, i, j);
            }
        }
        Swap(arr, i + 1, right);
        return i + 1;
    }

    static void Swap(char[] arr, int i, int j)
    {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Метод для сортировки строки с использованием сортировки деревом
    static string TreeSort(string input)
    {
        BinaryTree<char> tree = new BinaryTree<char>();
        foreach (char c in input)
        {
            tree.Insert(c);
        }
        return string.Join("", tree.InOrder());
    }
}

class BinaryTree<T> where T : IComparable<T>
{
    private class Node
    {
        public T Data;
        public Node Left, Right;

        public Node(T data)
        {
            Data = data;
        }
    }

    private Node root;

    public void Insert(T data)
    {
        root = Insert(root, data);
    }

    private Node Insert(Node node, T data)
    {
        if (node == null)
        {
            return new Node(data);
        }

        if (data.CompareTo(node.Data) < 0)
        {
            node.Left = Insert(node.Left, data);
        }
        else
        {
            node.Right = Insert(node.Right, data);
        }

        return node;
    }

    public IEnumerable<T> InOrder()
    {
        return InOrder(root);
    }

    private IEnumerable<T> InOrder(Node node)
    {
        if (node != null)
        {
            foreach (var item in InOrder(node.Left))
                yield return item;

            yield return node.Data;

            foreach (var item in InOrder(node.Right))
                yield return item;
        }
    }
}
