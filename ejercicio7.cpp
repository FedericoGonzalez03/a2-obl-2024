#include <cassert>
#include <string>
#include <iostream>
#include <limits>

using namespace std;

int main()
{

    int potencia = 0;
    cin >> potencia;
    cin.ignore();

    int potenciaActual = potencia;

    int n = 0;
    cin >> n;
    cin.ignore();

    for (int i = 0; i < n; i++)
    {
        int virus;
        cin >> virus;
        cin.ignore();
        if (virus <= potenciaActual || virus <= potencia)
        {
            potencia += virus;
            potenciaActual += virus;
        }
        else
        {
            potenciaActual -= virus;
        }
    }

    bool puedeDefenderse = potenciaActual >= 0;
    cout << (puedeDefenderse ? "true" : "false") << endl;
}