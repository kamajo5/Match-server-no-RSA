using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Rejestrator
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        bool isActive;
        public MainWindow()
        {
            isActive = false;
            InitializeComponent();
        }

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            isActive = true;
            dzialanie.Content = "Serwer działa";
            bool result = await Rejestrator(isActive);
        }

        private async Task<bool> Rejestrator(bool isActive)
        {
            return await Task.Run(() =>
            {
                while (isActive)
                {
                    GC.Collect();
                    Connecting con = new Connecting();
                    con.load_new_user();
                    con.Generuj_tokeny();

                }
                return isActive;
            });
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            Close();
        }
    }
}
