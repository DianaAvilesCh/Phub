import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int Hubs;//cantidad de servidores
    static int capacidad;//capacidad del servidor
    static int cant_Nodos; //cantidad de nodos en el txt
    static int cantidad_combinaciones = 0;
    private static ArrayList<Nodo> Nodos = new ArrayList<Nodo>();//array de nodos del txt
    private static ArrayList<ArrayList<Nodo>> servidor = new ArrayList<ArrayList<Nodo>>();//array de nodos del txt
    private static ArrayList<ArrayList<ArrayList<Nodo>>> cliente = new ArrayList<ArrayList<ArrayList<Nodo>>>();//array de nodos del txt
    private static ArrayList<ArrayList<Integer>> soluciones = new ArrayList<ArrayList<Integer>>();

    //array de soluciones
    public static void main(String[] args) throws FileNotFoundException {
        cargarDatos();
        permutaciones();
        Servidores();
    }

    //Cargar datos de una ruta especifica de txt, convierte esos datos a un array
    //los guarda en la clase Nodo y con el array de la clase los guardo como nodo
    public static void cargarDatos() throws FileNotFoundException {
        List<String> Datos = new ArrayList<>();
        int[][] nodosM = new int[0][];
        //Scanner Filast = new Scanner(new File("C:\\Users\\Criss\\Downloads\\Phub ejemplos\\phub_50_5_1.txt"));
        Scanner Filast = new Scanner(new File("C:\\Users\\Criss\\Downloads\\Phub ejemplos\\phub_50_5_4.txt"));
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
                nodosM = new int[cant_Nodos][4];//
                for (int i = 0; i < cant_Nodos; i++) {
                    for (int j = 0; j < 4; j++) {
                        nodosM[i][j] = Integer.parseInt(Filast.next());
                    }
                }
            }

        }
        System.out.println("Hubs: " + Hubs);
        System.out.println("capacidad: " + capacidad);
        System.out.println("cantidad: " + cant_Nodos);
//for que recorre la matriz para guardar la informacion en la clase nodo
        for (int i = 0; i < cant_Nodos; i++) {
            Nodo nodo = new Nodo(nodosM[i][0], nodosM[i][1], nodosM[i][2], nodosM[i][3]);
            Nodos.add(nodo);
        }
        int va = 1;
    }

    //crear diferentes combinaciones de los nodos
    public static void permutaciones() {
        int j = 0;

        Scanner teclado = new Scanner(System.in);
        cantidad_combinaciones = Integer.parseInt(teclado.nextLine());
        while (j < cantidad_combinaciones) {
            int x = 0;
            ArrayList<Integer> lista = new ArrayList<>();
            while (x < cant_Nodos) {
                int r = (int) (Math.random() * cant_Nodos + 1);
                if (validarCombUni(r, lista)) {
                    lista.add(r);
                    x++;
                }
            }
            if (validarCombUniPer(lista, soluciones)) {
                soluciones.add(lista);
                j++;
            }
        }

    }

    public static void Servidores() {
        int j = 0;
        while (j < cantidad_combinaciones) {
            int x = 0;
            int cantidad = cant_Nodos;
            ArrayList<Nodo> lista = new ArrayList<>();
            while (x < Hubs) {
                //r me da la posición del valor que sera mi servidor
                int r = (int) (Math.random() * cantidad + 1);
                //index me devuelve el valor que se encuentra en la posicion r
                int index = soluciones.get(j).get(r - 1);
                //cargo los valores del nodo con el id de index en la clase
                Nodo nodo = new Nodo(Nodos.get(index - 1).getId(), Nodos.get(index - 1).getCoordenada_x(),
                        Nodos.get(index - 1).getCoordenada_y(), Nodos.get(index - 1).getDemanda());
                //agrego el servidor a la lista
                lista.add(nodo);
                //remuevo el servidor para que no se repita
                soluciones.get(j).remove(r - 1);
                x++;
                cantidad--;
            }
            servidor.add(lista);
            j++;
        }

        Clientes();
    }

    public static void Clientes() {
        int j = 0;
        while (j < cantidad_combinaciones) {
            int x = 0;
            int cantidad = cant_Nodos - Hubs;
            //Array para guardar los nodos por servidor
            ArrayList<ArrayList<Nodo>> listaS = new ArrayList<ArrayList<Nodo>>();
            while (x < Hubs) {
                int dem = 0;
                int v = 0;
                //arrays que guar nodos por cliente
                ArrayList<Nodo> lista = new ArrayList<>();
                while (v <= 120 & cantidad > 0) {
                    //r me da la posición del valor que sera un cliente
                    int r = (int) (Math.random() * cantidad + 1);

                    //index me devuelve el valor que se encuentra en la posicion r
                    int index = soluciones.get(j).get(r - 1);

                    //cargo los valores del nodo con el id de index en la clase
                    Nodo nodo = new Nodo(Nodos.get(index - 1).getId(), Nodos.get(index - 1).getCoordenada_x(),
                            Nodos.get(index - 1).getCoordenada_y(), Nodos.get(index - 1).getDemanda());

                    v += Nodos.get(index - 1).getDemanda();
                    if (v <= 120) {
                        //System.out.println("VAL: " + v);
                        //agrego el cliente a la lista
                        lista.add(nodo);
                        //remuevo el servidor para que no se repita
                        soluciones.get(j).remove(r - 1);
                        cantidad--;
                    }
                }
                listaS.add(lista);
                x++;

            }
            cliente.add(listaS);
            j++;

        }
        distancia();
    }

    public static void distancia() {
        int menor = 0;
        int contador= 0 ;
        for (int l = 0; l < cantidad_combinaciones; l++) {
            int sumC = 0;
            for (int lo = 0; lo < Hubs; lo++) {
                int suma = 0;
                int xs = servidor.get(l).get(lo).getCoordenada_x();
                int ys = servidor.get(l).get(lo).getCoordenada_y();

                for (int lox = 0; lox < cliente.get(l).get(lo).size(); lox++) {
                    int xc = cliente.get(l).get(lo).get(lox).getCoordenada_x();
                    int yc = cliente.get(l).get(lo).get(lox).getCoordenada_y();
                    double val = Math.sqrt(Math.pow((xc - xs), 2) + Math.pow((yc - ys), 2));
                    suma += val;
                }
                sumC += suma;
            }
            if (l == 0)
                menor = sumC;
            if (menor > sumC) {
                menor = sumC;
                contador=l;
            }
        }
        System.out.println("MEJOR DISTANCIA --: " + menor);
        System.out.println("COMBINACIÓN "+ (contador+1));
        for(int n = 0; n < Hubs; n++){
            System.out.println("SERVIDOR: "+servidor.get(contador).get(n).getId());
            System.out.print("CLIENTES:");
            for(int g = 0; g<cliente.get(contador).get(n).size();g++){
                System.out.print(" "+cliente.get(contador).get(n).get(g).getId()+" - ");
            }
            System.out.println();
            System.out.println();
        }

    }

    //VALIDAR QUE NO SE REPITAN DATOS EN LOS ARRAYS
    public static boolean validarCombUni(int x, ArrayList<Integer> lista) {
        for (int nodo : lista) {
            if (x == nodo) {
                return false;
            }
        }
        return true;
    }

    public static boolean validarCombUniPer(ArrayList<Integer> x, ArrayList<ArrayList<Integer>> lista) {
        for (ArrayList<Integer> nodo : lista) {
            if (x == nodo) {
                return false;
            }
        }
        return true;
    }

        /*for(int l = 0 ; l<cantidad_combinaciones;l++){
            System.out.println("COMB: "+l);
            for(int lo = 0 ; lo<5;lo++){
                System.out.println("SERV: "+lo);
                System.out.println("ID: "+servidor.get(l).get(lo).getId());
                System.out.println("x: "+servidor.get(l).get(lo).getCoordenada_x());
                System.out.println("y: "+servidor.get(l).get(lo).getCoordenada_y());
                System.out.println("Demand: "+servidor.get(l).get(lo).getDemanda());

            }
        }*/
}