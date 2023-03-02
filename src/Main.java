import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int Hubs;//cantidad de servidores
    static int capacidad;//capacidad del servidor
    static int cant_Nodos; //cantidad de nodos en el txt
    static int cantidad_combinaciones = 0;
    private static ArrayList<Nodo> Nodos = new ArrayList<Nodo>();//array de nodos del txt
    //[[cliente1, cliente 2, nodo3, nodo4,nodo5]](nodo1, s1)
    private static ArrayList<ArrayList<Nodo>> servidor = new ArrayList<ArrayList<Nodo>>();//array de nodos del txt
    private static ArrayList<ArrayList<Cliente>> clientes = new ArrayList<ArrayList<Cliente>>();//array de nodos del txt
    private static ArrayList<ArrayList<Integer>> soluciones = new ArrayList<ArrayList<Integer>>();
    //[[2,3,4,5,8][4,5,2,7,8]]
    //array de soluciones
    public static void main(String[] args) throws FileNotFoundException {
        cargarDatos();
        permutaciones();
        Servidores();
    }

    //Cargar datos de una ruta especifica de txt, convierte esos datos a un array
    //los guarda en la clase Nodo
    public static void cargarDatos() {
        //info del servidor (cantidad de nodos, servidores, capacidad)
        List<String> Datos = new ArrayList<>();
        int[][] nodosM = new int[0][];
        //cargar los datos desde el explorador de archivos
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(fc);
        try {
            //obtener URL PATH al seleccionar carpeta
            File file = new File(fc.getSelectedFile().getAbsolutePath());

            //Scanner Filast = new Scanner(new File("C:\\Users\\Criss\\Downloads\\Phub ejemplos\\phub_50_5_1.txt"));
            Scanner Filast = new Scanner(file);
            int n = 0;
            while (Filast.hasNext()) {
                //if para extraer los 3 primeros datos con info del txt
                if (n < 3) {
                    Datos.add(Filast.next());
                    n++;
                    cant_Nodos = Integer.parseInt(Datos.get(0));
                } else {
                    //guarda los nodos en una matriz
                    nodosM = new int[cant_Nodos][4];//
                    for (int i = 0; i < cant_Nodos; i++) {
                        for (int j = 0; j < 4; j++) {
                            nodosM[i][j] = Integer.parseInt(Filast.next());
                        }
                    }
                }
            }
            Hubs = Integer.parseInt(Datos.get(1));
            capacidad = Integer.parseInt(Datos.get(2));

        } catch (NullPointerException e) {
            System.out.println("No se ha seleccionado ningún fichero");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Hubs: " + Hubs);
        System.out.println("capacidad: " + capacidad);
        System.out.println("cantidad: " + cant_Nodos);
//for que recorre la matriz para guardar la informacion en la clase nodo
        for (int i = 0; i < cant_Nodos; i++) {
            Nodo nodo = new Nodo(nodosM[i][0], nodosM[i][1], nodosM[i][2], nodosM[i][3]);
            Nodos.add(nodo);
        }
    }

    //crear diferentes combinaciones de los nodos
    public static void permutaciones() {
        System.out.println("Por favor ingrese la catidad de combinaciones:");
        Scanner teclado = new Scanner(System.in);
        cantidad_combinaciones = Integer.parseInt(teclado.nextLine());

        int j = 0;
        while (j < cantidad_combinaciones) {
            int x = 0;
            ArrayList<Integer> lista = new ArrayList<>();
            while (x < cant_Nodos) {
                int r = (int) (Math.random() * cant_Nodos + 1);//2
                if (validarCombUni(r, lista)) {
                    lista.add(r);//
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
        int j = 0;//[[5servidores][][][][][]]
        while (j < cantidad_combinaciones) {
            int x = 0;
            int cantidad = cant_Nodos;
            ArrayList<Nodo> lista = new ArrayList<>();
            while (x < Hubs) {
                //r me da la posición del valor que sera mi servidor
                int r = (int) (Math.random() * cantidad + 1);//[2][1,6,4]
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
            int cantidad = cant_Nodos - Hubs;//44

            //arrays que guardar nodos por cliente
            ArrayList<Cliente> lista = new ArrayList<Cliente>();
            while (cantidad > 0) {
                int v = 0;//demanda
                //r me da la posición del del servidor [45,20,75,11,8]
                int r = (int) (Math.random() * Hubs + 1);//[5,3,6,7,2]3

                //[[2,4][3,5,6][]]
                //index me devuelve el valor que se encuentra en la posicion soluciones[[4],[5,7,8]]
                int index = soluciones.get(j).get(0);

                //cargo los valores del nodo con el id de index en la clase
                Nodo nodo = new Nodo(Nodos.get(index - 1).getId(), Nodos.get(index - 1).getCoordenada_x(),
                        Nodos.get(index - 1).getCoordenada_y(), Nodos.get(index - 1).getDemanda());

                //conseguir la demanda total que tiene el servidor
                for (Cliente value : lista)//[cl:5,sr:6]
                    if ((r) == value.getServidor()) {
                        v += value.getNodo().getDemanda();//119
                    }
//v
                v += Nodos.get(index - 1).getDemanda();//14
                if (v <= capacidad) {
                    //agrego el cliente a la lista
                    Cliente cliente = new Cliente(nodo, (r));
                    lista.add(cliente);//[(c1,sx), (c2,sx)]
                    //remuevo el servidor para que no se repita
                    soluciones.get(j).remove(0);
                    cantidad--;
                }

            }
            clientes.add(lista);//[[3:6,2:3,4:5][5:28]
            j++;

        }
        distancia();
    }

    public static void distancia() {
        double menor = 0;
        int combinacion = 0;
        for (int l = 0; l < cantidad_combinaciones; l++) {//0
            double sumC = 0;
            for (int lo = 0; lo < Hubs; lo++) { //0 [5,3,7,9,8]
                double suma = 0;
                int xs = servidor.get(l).get(lo).getCoordenada_x();//
                int ys = servidor.get(l).get(lo).getCoordenada_y();

                for (int lox = 0; lox < clientes.get(l).size(); lox++) {//[[cl1:s3],[cl2:s1]]
                    if ((lo + 1) == clientes.get(l).get(lox).getServidor()) {
                        int xc = clientes.get(l).get(lox).getNodo().getCoordenada_x();
                        int yc = clientes.get(l).get(lox).getNodo().getCoordenada_y();
                        double val = Math.sqrt(Math.pow((xc - xs), 2) + Math.pow((yc - ys), 2));
                        suma += val;
                    }
                }
                sumC += suma;//2500-1700
            }
            if (l == 0)
                menor = sumC;
            if (menor > sumC) {
                menor = sumC;
                combinacion = l;
            }
        }

        resultado(menor, combinacion);
    }

    public static void resultado(double menor, int contador) {
        //Formato para redondear decimales
        DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
        separadoresPersonalizados.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00", separadoresPersonalizados);

        System.out.println("MEJOR DISTANCIA: " + df.format(menor));
        System.out.println("COMBINACIÓN: " + (contador + 1));

        for (int n = 0; n < Hubs; n++) {
            double disServ = 0;
            System.out.println("SERVIDOR: " + servidor.get(contador).get(n).getId());
            System.out.print("CLIENTES:");
            int xs = servidor.get(contador).get(n).getCoordenada_x();
            int ys = servidor.get(contador).get(n).getCoordenada_y();
            for (int g = 0; g < clientes.get(contador).size(); g++) {
                if (clientes.get(contador).get(g).getServidor() == (n + 1)) {
                    System.out.print(" " + clientes.get(contador).get(g).getNodo().getId() + " - ");
                    int xc = clientes.get(contador).get(g).getNodo().getCoordenada_x();
                    int yc = clientes.get(contador).get(g).getNodo().getCoordenada_y();
                    double val = Math.sqrt(Math.pow((xc - xs), 2) + Math.pow((yc - ys), 2));
                    disServ += val;
                }
            }
            System.out.println();

            System.out.println("SUMA DE DISTANCIA DEL SERVIDOR: " + df.format(disServ));
            System.out.println();
        }
    }

    //VALIDAR QUE NO SE REPITAN DATOS EN LOS ARRAYS
    public static boolean validarCombUni(int x, ArrayList<Integer> lista) {
        for (int nodo : lista) { //x=4 lista=[1,5,7,4]
            if (x == nodo) {
                return false;
            }
        }
        return true;
    }

    public static boolean validarCombUniPer(ArrayList<Integer> x, ArrayList<ArrayList<Integer>> lista) {
        for (ArrayList<Integer> nodo : lista) {
            if (x == nodo) { //lista x= [2,4,5,7] lista listax= [[2,4,5,7],[4,8,7,9]]
                return false;
            }
        }
        return true;
    }

}