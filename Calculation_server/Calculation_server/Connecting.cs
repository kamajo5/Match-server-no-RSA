using MySql.Data.MySqlClient;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using System.Windows;

namespace Calculation_server
{
    class Connecting
    {
        private string connectionString = "datasource=127.0.0.1;port=3306;username=root;password=;database=cloud_database;";
        private List<int> index = new List<int>();
        private List<string> input_data = new List<string>();
        private List<int> option_number = new List<int>();
        private string output_data;

        public void detect_operation()
        {
            index.Clear();
            input_data.Clear();
            option_number.Clear();
            string query = "Select ID, Input_data, Select_option from input_date where is_done = 0 ";
            MySqlConnection databaseConnection = new MySqlConnection(connectionString);
            MySqlCommand commandDatabase = new MySqlCommand(query, databaseConnection);
            MySqlDataReader reader;
            databaseConnection.Open();
            reader = commandDatabase.ExecuteReader();
            if (reader.HasRows)
            {
                while (reader.Read())
                {
                    index.Add(reader.GetInt16(0));
                    input_data.Add(reader.GetString(1));
                    option_number.Add(reader.GetInt16(2));
                }
            }
            databaseConnection.Close();
        }

        public void make_operation()
        {
            output_data = "";
            for (int i = 0; i < index.Count; i++)
            {

                switch (option_number[i])
                {
                    case 1:

                        output_data = ONP(input_data[i]);
                        Update_database(output_data, index[i]);
                        output_data = "";
                        break;
                }
            }
        }

        private void Update_database(string out_data, int idx)
        {
            string query = "Update input_date set Output_data = " + out_data + ", Is_done = 1 where ID = " + idx;
            MySqlConnection databaseConnection = new MySqlConnection(connectionString);
            MySqlCommand commandDatabase = new MySqlCommand(query, databaseConnection);
            MySqlDataReader reader;
            databaseConnection.Open();
            reader = commandDatabase.ExecuteReader();
            databaseConnection.Close();
        }
        private string ONP(string v)
        {
            v = v.Replace(" ","");
            string s = v;
            //Regex rgx = new Regex(@"\+\-\*\/");
            //string s = "3+2*5";
            string[] tab = Regex.Split(s, @"(\D)");
            Stack<string> stack = new Stack<string>();
            List<string> output = new List<string>();
            foreach (var item in tab)
            {
                if (item == "*" || item == "/")
                {
                    if (stack.Peek() == "+" || stack.Peek() == "-")
                        stack.Push(item);
                    else
                    {
                        while (stack.Peek() != "+" && stack.Peek() != "-" && stack.Count != 0)
                        {
                            output.Add(stack.Pop());
                        }
                        stack.Push(item);
                    }
                }
                else if (item == "+" || item == "-")
                {
                    while (stack.Count != 0)
                        output.Add(stack.Pop());
                    stack.Push(item);
                }
                else
                    output.Add(item);
            }
            foreach (var item in stack)
                output.Add(item);
            Stack<double> newStack = new Stack<double>();
            foreach (var item in output)
            {
                if (item == "+" || item == "-" || item == "*" || item == "/")
                {
                    double a = newStack.Pop();
                    double b = newStack.Pop();
                    switch (item)
                    {
                        case "+":
                            newStack.Push((a + b));
                            break;
                        case "-":
                            newStack.Push((a - b));
                            break;
                        case "*":
                            newStack.Push((a * b));
                            break;
                        case "/":
                            newStack.Push((a / b));
                            break;
                    }
                }
                else
                    newStack.Push(double.Parse(item));
            }
            return newStack.Peek().ToString();
        }
    }
}
