#include <cassert>
#include <string>
#include <iostream>
#include <limits>
#include "tads/HashTableImpl.cpp"

using namespace std;

class Pedido {
    public:
        string contenido;
        bool entregado;
        Pedido() : contenido(""), entregado(false){}
        Pedido(string __contenido) : contenido(__contenido), entregado(false){}
        void entregar() {
            entregado = true;
        } 
};


int hashPiGravityProduct(int id) {
    return id * 3.1416111806 * 9.813255456;
}

int main()
{

    int n = 0;
    cin >> n;
    cin.ignore();

    HashTable<int, Pedido> *pedidos = new HashTable<int, Pedido>(n, hashPiGravityProduct);

    for (int i = 0; i < n; i++)
    {
        string in;
        getline(cin, in);
        int lastSpace = in.find(" ");
        string op = in.substr(0, lastSpace);
        string idStr = in.substr(lastSpace + 1, in.find(" ", lastSpace + 1) -2);
        int id = stoi(idStr);
        if (op == "A")
        {
            string content = in.substr(in.find(" ", lastSpace + 1) + 1);
            pedidos->insert(id, *new Pedido(content));
        }
        else if (op == "E")
        {
            if(pedidos->contains(id)){
                pedidos->get(id).entregar();
            }
        }
        else if (op == "Q")
        {
            if(pedidos->contains(id)){
                if(pedidos->get(id).entregado == true){
                    cout << "Entregado" << endl;
                }
                else {
                    cout << pedidos->get(id).contenido << endl;
                }
            }
            else{
                cout << "Pedido no encontrado" << endl;
            }
        }
    }

    delete pedidos;
}
