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
            // Обработка строки в соответствии с условиями задания из "Задания 2"
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
        }
    }

    // Метод для проверки входной строки на наличие только букв в нижнем регистре английского алфавита
    static bool IsLowerCaseEnglishAlphabet(string input)
    {
        return input.All(c => char.IsLower(c) && c >= 'a' && c <= 'z');
    }

    // Метод для обработки входной строки в соответствии с условиями задания из "Задания 2"
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
}
