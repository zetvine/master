using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Linq;

namespace ApiDemo.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class StringProcessingController : ControllerBase
    {
        [HttpGet("{input}")]
        public ActionResult<string> Manipulate(string input, string sortType)
        {
            if (input.All(c => c >= 'a' && c <= 'z'))
            {
                string result = ReverseAndMerge(input);
                Dictionary<char, int> frequencyMap = CountFrequency(result);
                string biggestVowelSubstring = GetBiggestVowelSubstring(result);

                int randomIndex;

                return $"Resulting string: {result}\nSymbol frequency in the resulting string:\n" +
                       $"{string.Join("\n", frequencyMap.Select(kvp => $"{kvp.Key}: {kvp.Value}"))}\n" +
                       $"The biggest substring that begins and ends with a vowel is: {biggestVowelSubstring}\n" +
                       $"{ChooseSortingType(result, sortType)}\n" +
                       $"Before: {result}\n" +
                       $"Random index: {randomIndex = GetRandomIndex(result.Length)}\n" +
                       $"After: {RemoveCharAtIndex(result, randomIndex)}";
            }
            else
            {
                string invalidChars = new string(input.Where(c => !(c >= 'a' && c <= 'z')).ToArray());
                return BadRequest("Error: The input string contains invalid characters: " + invalidChars);
            }
        }

        static string ReverseAndMerge(string input)
        {
            int len = input.Length;

            if (len % 2 == 0)
            {
                int mid = len / 2;
                string s1 = ReverseString(input.Substring(0, mid));
                string s2 = ReverseString(input.Substring(mid));
                return s1 + s2;
            }
            else
            {
                string reversed = ReverseString(input);
                return reversed + input;
            }
        }

        static string ReverseString(string s)
        {
            char[] charArray = s.ToCharArray();
            Array.Reverse(charArray);
            return new string(charArray);
        }

        static Dictionary<char, int> CountFrequency(string input)
        {
            Dictionary<char, int> frequencyMap = new Dictionary<char, int>();
            foreach (char c in input)
            {
                if (!frequencyMap.ContainsKey(c))
                {
                    frequencyMap.Add(c, 1);
                }
                else
                {
                    frequencyMap[c]++;
                }
            }
            return frequencyMap;
        }

        static string GetBiggestVowelSubstring(string input)
        {
            string vowels = "aeiouy";
            int firstIndex = -1;
            int lastIndex = -1;
            for (int i = 0; i < input.Length; i++)
            {
                if (firstIndex == -1 && vowels.Contains(input[i]))
                {
                    firstIndex = i;
                }
                if (vowels.Contains(input[i]))
                {
                    lastIndex = i;
                }
            }
            if (firstIndex == -1 || lastIndex == -1)
            {
                return "";
            }
            return input.Substring(firstIndex, lastIndex - firstIndex + 1);
        }

        static string ChooseSortingType(string input, string sortType)
        {
            int sortOption;
            try
            {
                sortOption = int.Parse(sortType);
            }
            catch (FormatException)
            {
                return "Invalid choice: not an integer number";
            }

            if (sortOption == 1)
            {
                string sorted = QuickSort(input);
                return "The sorted string: " + sorted;
            }
            else if (sortOption == 2)
            {
                string sorted = TreeSort(input);
                return "The sorted string: " + sorted;
            }
            else
            {
                return "Invalid sort option selected.";
            }

            return "";
        }

        static string QuickSort(string input)
        {
            if (input.Length <= 1)
            {
                return input;
            }

            int pivotIndex = input.Length / 2;
            char pivot = input[pivotIndex];

            string left = "";
            string right = "";

            for (int i = 0; i < input.Length; i++)
            {
                if (i == pivotIndex)
                {
                    continue;
                }
                if (input[i] < pivot)
                {
                    left += input[i];
                }
                else
                {
                    right += input[i];
                }
            }

            return QuickSort(left) + pivot + QuickSort(right);
        }

        class Node
        {
            public char Value;
            public Node Left;
            public Node Right;

            public Node(char value)
            {
                Value = value;
            }
        }

        static string TreeSort(string input)
        {
            Node root = null;
            foreach (char c in input)
            {
                root = Insert(root, c);
            }

            List<char> sortedList = new List<char>();
            InOrderTraversal(root, sortedList);

            return new string(sortedList.ToArray());
        }

        static Node Insert(Node node, char value)
        {
            if (node == null)
            {
                return new Node(value);
            }

            if (value < node.Value)
            {
                node.Left = Insert(node.Left, value);
            }
            else
            {
                node.Right = Insert(node.Right, value);
            }

            return node;
        }

        static void InOrderTraversal(Node node, List<char> sortedList)
        {
            if (node == null)
            {
                return;
            }

            InOrderTraversal(node.Left, sortedList);
            sortedList.Add(node.Value);
            InOrderTraversal(node.Right, sortedList);
        }

        static int GetRandomIndex(int maxLength)
        {
            int randomIndex = 0;

            using (var client = new HttpClient())
            {
                try
                {
                    var response = client.GetAsync($"https://www.random.org/integers/?num=1&min=0&max={maxLength - 1}&col=1&base=10&format=plain&rnd=new").Result;
                    if (response.IsSuccessStatusCode)
                    {
                        var responseContent = response.Content;
                        string responseString = responseContent.ReadAsStringAsync().Result;
                        randomIndex = int.Parse(responseString);
                    }
                    else
                    {
                        randomIndex = new Random().Next(0, maxLength - 1);
                    }
                }
                catch (HttpRequestException)
                {
                    randomIndex = new Random().Next(0, maxLength - 1);
                }
            }

            return randomIndex;
        }

        static string RemoveCharAtIndex(string input, int index)
        {
            return input.Remove(index, 1);
        }
    }
}

