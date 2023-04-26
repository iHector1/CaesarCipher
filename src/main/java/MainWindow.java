import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JButton numButton;
    private JTextField numTextField;
    private JTextArea seqTextArea;
    private JTextArea forkTextArea;
    private JTextArea execTextArea;

    public MainWindow() {
        // Configurar la ventana principal
        setTitle("Ventana principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Crear el panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Crear el panel de números
        JPanel numPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel numLabel = new JLabel("Números:");
        numTextField = new JTextField(20);
        numButton = new JButton("Abrir Archivo");
        numButton.addActionListener(e->this.abrirArchivo());
        numPanel.add(numLabel);
        numPanel.add(numTextField);
        numPanel.add(numButton);

        // Crear el panel de secuencias
        JPanel seqPanel = new JPanel(new BorderLayout());
        JLabel seqLabel = new JLabel("Secuencias:");
        seqTextArea = new JTextArea(10, 50);
        seqTextArea.setLineWrap(true);
        seqTextArea.setWrapStyleWord(true);
        JScrollPane seqScrollPane = new JScrollPane(seqTextArea);
        JButton seqButton = new JButton("Ejecutar");
        seqButton.addActionListener(e -> seqTextArea.append("Secuencia agregada\n"));
        seqPanel.add(seqLabel, BorderLayout.NORTH);
        seqPanel.add(seqScrollPane, BorderLayout.CENTER);
        seqPanel.add(seqButton, BorderLayout.SOUTH);

        // Crear el panel de fork
        JPanel forkPanel = new JPanel(new BorderLayout());
        JLabel forkLabel = new JLabel("Fork:");
        forkTextArea = new JTextArea(10, 50);
        forkTextArea.setLineWrap(true);
        forkTextArea.setWrapStyleWord(true);
        JScrollPane forkScrollPane = new JScrollPane(forkTextArea);
        JButton forkButton = new JButton("Ejecutar");
        forkButton.addActionListener(e -> forkTextArea.append("Fork agregado\n"));
        forkPanel.add(forkLabel, BorderLayout.NORTH);
        forkPanel.add(forkScrollPane, BorderLayout.CENTER);
        forkPanel.add(forkButton, BorderLayout.SOUTH);

        // Crear el panel de executor
        JPanel execPanel = new JPanel(new BorderLayout());
        JLabel execLabel = new JLabel("Executor:");
        execTextArea = new JTextArea(10, 50);
        execTextArea.setLineWrap(true);
        execTextArea.setWrapStyleWord(true);
        JScrollPane execScrollPane = new JScrollPane(execTextArea);
        JButton execButton = new JButton("Ejecutar");
        execButton.addActionListener(e -> execTextArea.append("Executor agregado\n"));
        execPanel.add(execLabel, BorderLayout.NORTH);
        execPanel.add(execScrollPane, BorderLayout.CENTER);
        execPanel.add(execButton, BorderLayout.SOUTH);

        // Crear el panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton clearButton = new JButton("Limpiar");
        clearButton.addActionListener(e -> this.limpiar());
        buttonPanel.add(clearButton);

        // Agregar los componentes al panel principal
        mainPanel.add(numPanel, BorderLayout.NORTH);
        mainPanel.add(seqPanel, BorderLayout.WEST);
        mainPanel.add(forkPanel, BorderLayout.CENTER);
        mainPanel.add(execPanel, BorderLayout.EAST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar el panel principal a la ventana principal
        getContentPane().add(mainPanel);
        this.setVisible(true);
    }

    public ActionListener limpiar(){
        numTextField.setText("");
        seqTextArea.setText("");
        forkTextArea.setText("");
        execTextArea.setText("");
        return null;
    }
    public boolean isNumero(){
        if(this.numTextField.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Inserta el numero de desplazamiento");
            return false;
        }else if(!this.numTextField.getText().chars().allMatch(Character::isDigit)){
            JOptionPane.showMessageDialog(this,"No debe contener letras");
            return false;
        }else{
            System.out.println(this.numTextField.getText());
            return true;
        }
    }
    public ActionListener abrirArchivo(){
        if(this.isNumero())
        return null;

        return null;
    }
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}
