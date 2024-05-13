#include <cassert>
#include <string>
#include <iostream>
#include <limits>

using namespace std;

int main()
{
    int n = 0;
    cin >> n;
    cin.ignore(); // Elimino el salto de linea de la entrada

    for (int i = 0; i < n; i++)
    {
        string in;
        getline(cin, in);
        int lastSpace = in.find(" ");
        string op = in.substr(0, lastSpace);
        string id = in.substr(lastSpace + 1, in.find(" ", lastSpace + 1));
        if (op == "A")
        {
        }
        else if (op == "E")
        {
        }
        else if (op == "Q")
        {
        }
        cout << op << "-" << id << endl;
    }
}