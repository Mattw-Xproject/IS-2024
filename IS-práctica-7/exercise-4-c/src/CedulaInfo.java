public class CedulaInfo {
    private double total;
    private int cant;

    public CedulaInfo(int cant ,double total) {
        this.total = total;
        this.cant = cant;
    }

    public double getTotal() {
        return total;
    }

    public int getCant() {
        return cant;
    }
    public void setTotal(double subtotal) {
        this.total = subtotal;
    }
    public void setCant(int cant) {
        this.cant = cant;
    }
}
