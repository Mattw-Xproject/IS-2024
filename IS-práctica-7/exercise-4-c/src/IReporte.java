import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IReporte extends JFrame {
    private JTextArea reportArea;
    private JRadioButton reportGeneralButton;
    private JRadioButton reportIndividualButton;
    private JLabel cedulaInstructionLabel;
    private JLabel totalInstructionLabel;
    private JTextField cedulaInputField;
    private JButton continuarButton;
    private Map<String, CedulaInfo> subTotalesPorCedula = new HashMap<>();

    public IReporte() {
        setTitle("Reporte de Inventario");
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        reportArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(reportArea);
        reportArea.setVisible(false);
        scrollPane.setVisible(false);
        add(scrollPane);

        reportGeneralButton = new JRadioButton("Reporte General");
        reportIndividualButton = new JRadioButton("Reporte Individual", true);
        ButtonGroup group = new ButtonGroup();
        group.add(reportGeneralButton);
        group.add(reportIndividualButton);
        cedulaInputField = new JTextField(20); // Ajusta el tamaño según sea necesario
        cedulaInputField.setVisible(true); // Inicialmente oculto

        continuarButton = new JButton("Continuar");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Tipo de Reporte:"));
        panel.add(reportIndividualButton);
        panel.add(reportGeneralButton);
        
        cedulaInstructionLabel = new JLabel("Ingrese la cédula del cliente:");

        panel.add(cedulaInstructionLabel);

        panel.add(cedulaInputField);
        panel.add(continuarButton);
        totalInstructionLabel= new JLabel("Totalización: \n" +  "___ equipos\n" +   "________ Bs.\n");
        
        panel.add(totalInstructionLabel); 
        add(panel, BorderLayout.NORTH);

        loadAndDisplayData();
        reportGeneralButton.addActionListener(e -> {
            if (reportGeneralButton.isSelected()) {
                totalInstructionLabel.setText("Totalización: \n" +  "___ equipos \n" +   "________ Bs.\n");
                cedulaInputField.setVisible(false);
                cedulaInstructionLabel.setVisible(false);
                reportArea.setVisible(true);
                scrollPane.setVisible(true);
            } else {
                reportArea.setVisible(false);
                scrollPane.setVisible(false);
                cedulaInputField.setVisible(true); 
                cedulaInstructionLabel.setVisible(true);
            }
        });
        reportIndividualButton.addActionListener(e -> {
            if (reportIndividualButton.isSelected()) {
                totalInstructionLabel.setText("Totalización: \n" +  "___ equipos \n" +   "________ Bs.\n");
                reportArea.setVisible(false);
                scrollPane.setVisible(false);
                cedulaInputField.setVisible(true);
                cedulaInstructionLabel.setVisible(true);
            } else {
                reportArea.setVisible(true);
                scrollPane.setVisible(true);
                cedulaInputField.setVisible(false); 
                cedulaInstructionLabel.setVisible(false);
            }
        });
        continuarButton.addActionListener(e -> {
            if (reportGeneralButton.isSelected()) {
                generarReporteGeneral(totalInstructionLabel);
            } else if (reportIndividualButton.isSelected()) {  
                generarReporteIndividual(totalInstructionLabel); 
            }
        });

        setVisible(true);
    }

    private void loadAndDisplayData() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("inventario.txt"))) {
            String line;
            while ((line = br.readLine())!= null) {
                String[] datos = line.split("#");
                /*sb.append(datos[0]).append(" .... ").append(datos[1]).append(" Unidad(es) .... ").append(datos[2])
                 .append(" Bs. c/u .... Fecha Adq.:").append(datos[3]).append(" .... Fact. #").append(datos[4]).append(" .... CI del Resp.:")
                 .append(datos[5]).append("\n\n");*/
                // Actualiza el subtotal y la cantidad total de productos para la cédula
                CedulaInfo info = subTotalesPorCedula.getOrDefault(datos[5], new CedulaInfo(0, 0));
                info.setTotal(( info.getTotal() + Integer.parseInt(datos[1]) * Double.parseDouble(datos[2])));
                info.setCant(Integer.parseInt(info.getCant() + datos[1]));
                 
                subTotalesPorCedula.put(datos[5], info);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar el reporte.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        reportArea.setText(sb.toString());
    }

    private void generarReporteGeneral(  JLabel label) {
        StringBuilder sb = new StringBuilder();
        sb.append(" C.I. Responsable     ")
                 .append(" Cantidad Equipos     ")
                 .append(" Monta Total (Bs.)")
                 .append("\n\n");
        for (Map.Entry<String, CedulaInfo> entry : subTotalesPorCedula.entrySet()) {
            
            sb.append(entry.getKey()).append("   ..............................   ").append(entry.getValue().getCant())
            .append("   ..............................   ").append(entry.getValue().getTotal()).append("\n");
        } int cantidad=0; Double total=0.0;
        for (Map.Entry<String, CedulaInfo> entry : subTotalesPorCedula.entrySet()) {
            
            cantidad += entry.getValue().getCant();
            total += entry.getValue().getTotal();
        }
        label.setText("Totalización: \n" + cantidad +  " equipos \n" + total +  " Bs.\n");
        reportArea.setText(sb.toString());
    }

    private void generarReporteIndividual( JLabel label) {
        String cedulaBuscada = cedulaInputField.getText().trim(); // Obtiene el texto del JTextField
        if (subTotalesPorCedula.containsKey(cedulaBuscada)) {
            label.setText("Totalización: \n" + subTotalesPorCedula.get(cedulaBuscada).getCant() +  " equipos \n" + subTotalesPorCedula.get(cedulaBuscada).getTotal() +  " Bs.\n");
        } else {
            label.setText("No se encontró información para la cédula " + cedulaBuscada);
        }
    }

}
