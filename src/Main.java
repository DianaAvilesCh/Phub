import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int Hubs;
    static int capacidad;
    static int cant_Nodos;
    private static ArrayList<Nodo> Nodos = new ArrayList<Nodo>();

    public static void main(String[] args) throws FileNotFoundException {
        cargarDatos();
    }

    public static void cargarDatos() throws FileNotFoundException {
        List<String> Datos = new ArrayList<>();
        int nodosM[][] = new int[0][];
        Scanner Filast = new Scanner(new File("C:\\Users\\Criss\\Downloads\\Phub ejemplos\\phub_50_5_1.txt"));
        ArrayList<String> temps = new ArrayList<>();
        int n = 0;
        while (Filast.hasNext()) {
            // Va linea por linea
            if (n < 3) {
                Datos.add(Filast.next());
                n++;
            } else {
                Hubs = Integer.parseInt(Datos.get(1));
                capacidad = Integer.parseInt(Datos.get(2));
                cant_Nodos = Integer.parseInt(Datos.get(0));
                nodosM = new int[cant_Nodos][4];
                for (int i=0; i < cant_Nodos; i++) {
                    for (int j=0; j < 4; j++) {
                        nodosM[i][j] = Integer.parseInt(Filast.next());
                    }
                }
            }

        }

        System.out.println("Hubs: " + Hubs);
        System.out.println("capacidad: " + capacidad);
        System.out.println("cantidad: " + cant_Nodos);

        for (int i=0; i < cant_Nodos; i++) {
            Nodo nodo = new Nodo(nodosM[i][0],nodosM[i][1],nodosM[i][2],nodosM[i][3]);
                Nodos.add(nodo);
            System.out.println(nodosM[i][0]+" "+nodosM[i][1]+" "+nodosM[i][2]+" "+nodosM[i][3]);
        }
        System.out.println(Nodos.get(1).getId());
        System.out.println(Nodos.get(1).getCoordenada_x());
        System.out.println(Nodos.get(1).getCoordenada_y());
        System.out.println(Nodos.get(1).getDemanda());
    }
}