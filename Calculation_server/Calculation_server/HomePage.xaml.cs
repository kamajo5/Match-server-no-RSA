using System;
using System.Globalization;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Forms;

namespace Calculation_server
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {

        bool isActive;
        public MainWindow()
        {
            InitializeComponent();
            isActive = false;
        }

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            isActive = true;
            dzialanie.Content = "Serwer działa";
            bool result = await Calculate(isActive);
        }

        private async void Button_Click_1(object sender, RoutedEventArgs e)
        {
            Close();
        }

        private async Task<bool> Calculate(bool isActive)
        {
            return await Task.Run(() =>
            {

                while (isActive)
                {
                    Connecting con = new Connecting();
                    con.detect_operation();
                    con.make_operation();
                    GC.Collect();
                }
                return isActive;
            });
        }
    }
}
