import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private Nodo nodo;
    private List<Cliente> clientes;

    public Servidor(Nodo nodo) {
        this.nodo = nodo;
        this.clientes = new ArrayList<>();
    }
}
