package Blegh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerificadorSintaxis extends JFrame {
    private ColorearPalabra codeArea = new ColorearPalabra();
    private JTextArea codigoTextArea;
    private JTextArea erroresTextArea;
    private JButton verificarButton;
    
    public VerificadorSintaxis() {
        setTitle("Verificador de Sintaxis");
        setSize(325, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
								
        codigoTextArea = new JTextArea();
        erroresTextArea = new JTextArea();
        verificarButton = new JButton("Verificar Sintaxis");

        setLayout(new BorderLayout());

        /*JPanel panelCodigo = new JPanel(new BorderLayout());
        panelCodigo.add(new JScrollPane(codigoTextArea), BorderLayout.CENTER);
        panelCodigo.setBorder(BorderFactory.createTitledBorder("C�digo"));*/

        JPanel panelErrores = new JPanel(new BorderLayout());
        JScrollPane erroresScrollPane = new JScrollPane(erroresTextArea);
        erroresScrollPane.setPreferredSize(new Dimension(300, getHeight())); 
        panelErrores.add(erroresScrollPane, BorderLayout.CENTER);
        panelErrores.setBorder(BorderFactory.createTitledBorder("Errores"));

        //add(panelCodigo, BorderLayout.CENTER);
        add(panelErrores, BorderLayout.EAST);
        add(verificarButton, BorderLayout.SOUTH);
								
        verificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarSintaxis();
            }
        });
    }
    
    private void verificarSintaxis() {
        String codigo = codeArea.writtenText().trim(); 
        String mensajesError = obtenerMensajesError(codigo);

        if (mensajesError.isEmpty()) {
            int instances = obtenerAgain(codigo);
            String mensaje = obtenerMensaje(codigo);
            String fullMessage = "";
            for (int i = 0; i < instances; i++) {
                fullMessage += ("\n" + mensaje);
            }
            JOptionPane.showMessageDialog(this, fullMessage);
        } else {
            erroresTextArea.setText(mensajesError);
        }
    }

    private String obtenerMensajesError(String codigo) {
        StringBuilder mensajesError = new StringBuilder();
        String[] lineas = codigo.split("\n");
								
        for (String linea : lineas) {
            if (!linea.trim().endsWith("#")) {
                mensajesError.append("Cada l�nea de c�digo debe terminar con '#'.").append("\n");
            }
        }

        // Verificar si la estructura general es correcta
        if (!(codigo.startsWith("inicio#") && codigo.endsWith("out#"))) {
            // Verificar si falta 'inicio#' o 'out#'
            if (!codigo.startsWith("inicio#")) {
                mensajesError.append("Falta la palabra 'inicio#'.\n");
            }
            if (!codigo.endsWith("out#")) {
                mensajesError.append("Falta la palabra 'out#'.\n");
            }
        }
								
        int inicioMensaje = codigo.indexOf("Mensaje(\"");
        int finMensaje = codigo.indexOf("\")#", inicioMensaje);

        while (inicioMensaje != -1 && finMensaje != -1) {
            String mensaje = codigo.substring(inicioMensaje + 9, finMensaje);
            if (mensaje.contains("#")) {
                mensajesError.append("El mensaje no debe contener el car�cter '#'.\n");
            }

            inicioMensaje = codigo.indexOf("Mensaje(\"", finMensaje + 1);
            finMensaje = codigo.indexOf("\")#", inicioMensaje);
        }
								
        if (inicioMensaje == -1 && finMensaje == -1) {
            mensajesError.append("La palabra 'Mensaje' debe estar presente y seguida por un par�ntesis y una cadena entre comillas.\n");
        }

        return mensajesError.toString();
    }


    private String obtenerMensaje(String codigo) {
        int inicioMensaje = codigo.indexOf("Mensaje(\"");
        int finMensaje = codigo.indexOf("\")#", inicioMensaje);

        if (inicioMensaje != -1 && finMensaje != -1) {
            return codigo.substring(inicioMensaje + 9, finMensaje);
        } else {
            return "No se pudo extraer el mensaje.";
        }
    }

    private int obtenerAgain(String codigo) {
        int inicioMensaje = codigo.indexOf("Again(");
        int finMensaje = codigo.indexOf(")#", inicioMensaje);

        if (inicioMensaje != -1 && finMensaje != -1) {
            return Integer.parseInt(codigo.substring(inicioMensaje + 6, finMensaje)) ;
        } else {
            return 1;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VerificadorSintaxis gui = new VerificadorSintaxis();
                gui.setVisible(true);
            }
        });
    }
}
