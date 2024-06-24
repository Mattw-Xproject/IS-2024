import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ICentro extends JFrame {
    private JTextField descripcionField;
    private JTextField cantidadField;
    private JTextField costoUnitarioField;
    private JTextField fechaField;
    private JTextField facturaField;
    private JTextField cedulaField;
    private JButton guardarButton;
    private JButton reportarButton;
    private JButton salirButton;

    public ICentro() {
        setTitle("Registro y Control de Equipos en Centro de Investigación");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Componentes
        descripcionField = new JTextField(20);
        cantidadField = new JTextField(20);
        costoUnitarioField = new JTextField(20);
        fechaField = new JTextField(20);
        facturaField = new JTextField(20);
        cedulaField = new JTextField(20);
        guardarButton = new JButton("Registrar Data");
        reportarButton = new JButton("Generar Reporte");
        salirButton = new JButton("Salir");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // Agregar componentes al layout
        panel.add(new JLabel("Ingrese Data del Equipo:"));
        panel.add(new JLabel("Descripción:"));
        panel.add(descripcionField);
        panel.add(new JLabel("Cantidad:"));
        panel.add(cantidadField);
        panel.add(new JLabel("Costo Unitario (Bs.):"));
        panel.add(costoUnitarioField);
        panel.add(new JLabel("Fecha de Adquisición: dd/mm/aaaa"));
        panel.add(fechaField);
        panel.add(new JLabel("Nro. de Factura:"));
        panel.add(facturaField);
        panel.add(new JLabel("C.I. del Responsable del equipo:"));
        panel.add(cedulaField);
        panel.add(guardarButton);
        panel.add(reportarButton);
        panel.add(salirButton);
        add(panel);
        // Acciones
        guardarButton.addActionListener(e -> guardarDatos());
        reportarButton.addActionListener(e -> new IReporte().setVisible(true));
        salirButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void guardarDatos() {
        StringBuilder sb = new StringBuilder();
        sb.append(descripcionField.getText()).append("#");
        sb.append(cantidadField.getText()).append("#");
        sb.append(costoUnitarioField.getText()).append("#");
        sb.append(fechaField.getText()).append("#");
        sb.append(facturaField.getText()).append("#");
        sb.append(cedulaField.getText());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("inventario.txt", true))) {
            bw.write(sb.toString());
            bw.newLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ICentro();
    }
}