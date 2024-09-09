using System;

namespace StringManipulationApp
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Введите строку:");
            string inputString = Console.ReadLine();
            string processedString = ProcessString(inputString);
            Console.WriteLine("Обработанная строка:");
            Console.WriteLine(processedString);
        }

        static string ProcessString(string input)
        {
            if (input.Length % 2 == 0) // если длина строки четная
            {
                int halfLength = input.Length / 2;
                string firstHalf = input.Substring(0, halfLength);
                string secondHalf = input.Substring(halfLength);
                char[] firstHalfArray = firstHalf.ToCharArray();
                Array.Reverse(firstHalfArray);
                char[] secondHalfArray = secondHalf.ToCharArray();
                Array.Reverse(secondHalfArray);
                return new string(firstHalfArray) + new string(secondHalfArray);
            }
            else // если длина строки нечетная
            {
                char[] inputArray = input.ToCharArray();
                Array.Reverse(inputArray);
                return new string(inputArray) + input;
            }
        }
    }
}
