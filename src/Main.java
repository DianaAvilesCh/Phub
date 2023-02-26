import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int Hubs;//cantidad de servidores
    static int capacidad;//capacidad del servidor
    static int cant_Nodos; //cantidad de nodos en el txt
    private static ArrayList<Nodo> Nodos = new ArrayList<Nodo>();//array de nodos del txt

    private static ArrayList<ArrayList<Integer>>  soluciones = new ArrayList<ArrayList<Integer>> ();
    //array de soluciones
    public static void main(String[] args) throws FileNotFoundException {
        cargarDatos();
        permutaciones();
    }

    //Cargar datos de una ruta especifica de txt, convierte esos datos a un array
    //los guarda en la clase Nodo y con el array de la clase los guardo como nodo
    public static void cargarDatos() throws FileNotFoundException {
        List<String> Datos = new ArrayList<>();
        int nodosM[][] = new int[0][];
        Scanner Filast = new Scanner(new File("C:\\Users\\Criss\\Downloads\\Phub ejemplos\\phub_50_5_1.txt"));
        ArrayList<String> temps = new ArrayList<>();
        int n = 0;
        while (Filast.hasNext()) {
            // Va linea por linea
            //if para extraer los 3 primeros datos con info del txt
            if (n < 3) {
                Datos.add(Filast.next());
                n++;
            } else {
                Hubs = Integer.parseInt(Datos.get(1));
                capacidad = Integer.parseInt(Datos.get(2));
                cant_Nodos = Integer.parseInt(Datos.get(0));
                //guarda los nodos en una matriz
                nodosM = new int[cant_Nodos][4];
                for (int i = 0; i < cant_Nodos; i++) {
                    for (int j = 0; j < 4; j++) {
                        nodosM[i][j] = Integer.parseInt(Filast.next());
                    }
                }
            }

        }
//esto se puede borrar
        System.out.println("Hubs: " + Hubs);
        System.out.println("capacidad: " + capacidad);
        System.out.println("cantidad: " + cant_Nodos);
//for que recorre la matriz para guardar la informacion en la clase nodo
        for (int i = 0; i < cant_Nodos; i++) {
            Nodo nodo = new Nodo(nodosM[i][0], nodosM[i][1], nodosM[i][2], nodosM[i][3]);
            Nodos.add(nodo);
            System.out.println(nodosM[i][0] + " " + nodosM[i][1] + " " + nodosM[i][2] + " " + nodosM[i][3]);
        }
        //se puede borrar
        System.out.println(Nodos.get(1).getId());
        System.out.println(Nodos.get(1).getCoordenada_x());
        System.out.println(Nodos.get(1).getCoordenada_y());
        System.out.println(Nodos.get(1).getDemanda());
    }
    //crear diferentes combinaciones de los nodos
    public static void permutaciones() {
        int j = 0;
        int cantidad_combinaciones;
                Scanner teclado = new Scanner(System.in);
        cantidad_combinaciones = Integer.parseInt(teclado.nextLine());
        while (j<cantidad_combinaciones){
            int x=0;
            ArrayList<Integer> lista = new ArrayList<>();
            while(x<cant_Nodos){
                int r=(int) (Math.random()*cant_Nodos+0);
                if(validarCombUni(r,lista)){
                    //borrar
                    System.out.print(r+"-");
                    lista.add(r);
                    x++;
                }
            }
            //borrar
            System.out.println("");
            if(validarCombUniPer(lista,soluciones)){
                soluciones.add(lista);
                System.out.println(soluciones.get(j).get(2));//borrar
                j++;
            }
        }

    }
    public static boolean validarCombUni(int x, ArrayList<Integer> lista){
        for(int nodo : lista){
            if(x == nodo){
                return false;
            }
        }
        return true;
    }
    public static boolean validarCombUniPer(ArrayList<Integer> x, ArrayList<ArrayList<Integer>> lista){
        for(ArrayList<Integer> nodo : lista){
            if(x == nodo){
                return false;
            }
        }
        return true;
    }
}