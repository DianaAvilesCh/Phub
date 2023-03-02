public class Cliente {
    private Nodo nodo;
    private int servidor;

    public Cliente() {
    }

    public Nodo getNodo() {
        return nodo;
    }

    public void setNodo(Nodo nodo) {
        this.nodo = nodo;
    }

    public int getServidor() {
        return servidor;
    }

    public void setServidor(int servidor) {
        this.servidor = servidor;
    }

    public Cliente(Nodo nodo, int servidor) {
        this.nodo = nodo;
        this.servidor = servidor;
    }
}
