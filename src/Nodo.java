public class Nodo {
    private int id;
    private int coordenada_x;
    private int coordenada_y;
    private int demanda;

    public Nodo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoordenada_x() {
        return coordenada_x;
    }

    public void setCoordenada_x(int coordenada_x) {
        this.coordenada_x = coordenada_x;
    }

    public int getCoordenada_y() {
        return coordenada_y;
    }

    public void setCoordenada_y(int coordenada_y) {
        this.coordenada_y = coordenada_y;
    }

    public int getDemanda() {
        return demanda;
    }

    public void setDemanda(int demanda) {
        this.demanda = demanda;
    }

    public Nodo(int id, int x, int y, int demanda) {
        this.id = id;
        this.coordenada_x = x;
        this.coordenada_y = y;
        this.demanda = demanda;
    }
}
