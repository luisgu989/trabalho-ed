package dominio;

public class Carta {

    private int id;

    public Carta(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Carta: [" + id + "]";
    }

}