import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.List;

public class MainWindow extends JFrame {
    private JLabel execLabel;
    private JLabel forkLabel;
    private JLabel seqLabel;
    private JButton numButton;
    private JTextField numTextField;
    private JTextArea seqTextArea;
    private JTextArea forkTextArea;
    private JTextArea execTextArea;
    private String frase;
    private int key;

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
        seqLabel = new JLabel("Secuencial:");
        seqTextArea = new JTextArea(10, 50);
        seqTextArea.setLineWrap(true);
        seqTextArea.setWrapStyleWord(true);
        JScrollPane seqScrollPane = new JScrollPane(seqTextArea);
        JButton seqButton = new JButton("Ejecutar");
        seqButton.addActionListener(e -> this.secuencial());
        seqPanel.add(seqLabel, BorderLayout.NORTH);
        seqPanel.add(seqScrollPane, BorderLayout.CENTER);
        seqPanel.add(seqButton, BorderLayout.SOUTH);

        // Crear el panel de fork
        JPanel forkPanel = new JPanel(new BorderLayout());
        forkLabel = new JLabel("Fork:");
        forkTextArea = new JTextArea(10, 50);
        forkTextArea.setLineWrap(true);
        forkTextArea.setWrapStyleWord(true);
        JScrollPane forkScrollPane = new JScrollPane(forkTextArea);
        JButton forkButton = new JButton("Ejecutar");
        forkButton.addActionListener(e -> this.fork());
        forkPanel.add(forkLabel, BorderLayout.NORTH);
        forkPanel.add(forkScrollPane, BorderLayout.CENTER);
        forkPanel.add(forkButton, BorderLayout.SOUTH);

        // Crear el panel de executor
        JPanel execPanel = new JPanel(new BorderLayout());
        execLabel = new JLabel("Executor:");
        execTextArea = new JTextArea(10, 50);
        execTextArea.setLineWrap(true);
        execTextArea.setWrapStyleWord(true);
        JScrollPane execScrollPane = new JScrollPane(execTextArea);
        JButton execButton = new JButton("Ejecutar");
        execButton.addActionListener(e -> this.executor());
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

        //inicializar variables
        this.key=0;
        this.frase="vacio";
    }

    public ActionListener limpiar(){
        numTextField.setText("");
        seqTextArea.setText("");
        forkTextArea.setText("");
        execTextArea.setText("");
        seqLabel.setText("Secuencial: ");
        forkLabel.setText("Fork: ");
        execLabel.setText("Executor: ");

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
            this.key = Integer.parseInt(this.numTextField.getText());
            return true;
        }
    }
    public ActionListener abrirArchivo(){
        if(!this.isNumero())
            return null;
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtener el archivo seleccionado por el usuario
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Leer el contenido del archivo y guardarlo en una cadena
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(System.lineSeparator());
                }
                String fileContent = stringBuilder.toString();
                this.frase = fileContent;
                System.out.println(fileContent);
                JOptionPane.showMessageDialog(this,"Archivo Cargado");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    private void executor(){
        long tiem1= System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        int fourth = this.frase.length() / 4;
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Collection<Callable<String>> callables = new ArrayList<>();

        callables.add(() -> CaesarCipherT.cipher(this.frase.substring(0, fourth), this.key));
        callables.add(() -> CaesarCipherT.cipher(this.frase.substring(fourth, fourth * 2), this.key));
        callables.add(() -> CaesarCipherT.cipher(this.frase.substring(fourth * 2, fourth * 3), this.key));
        callables.add(() -> CaesarCipherT.cipher(this.frase.substring(fourth * 3), this.key));

        try {
            List<Future<String>> futures = executorService.invokeAll(callables);

            for (Future<String> future : futures) {
                stringBuilder.append(future.get());
            }
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
        long time =System.currentTimeMillis() -tiem1 ;
        this.execLabel.setText("Executor: "+time+" milisegundos");
        this.frase = stringBuilder.toString();
        execTextArea.setText(this.frase);


    }

    private void secuencial(){
        long time1 = System.currentTimeMillis();
        String result = CaesarCipherT.cipher(this.frase,key);
        this.seqTextArea.setText(result);
        long time = System.currentTimeMillis()-time1;
        this.seqLabel.setText("Secuencial: "+time+" milisegundos");

    }

    private void fork(){
        long time1 = System.currentTimeMillis();
        CaesarCipherT caesarCipherThread=new CaesarCipherT(this.frase,this.key);
        ForkJoinPool pool = new ForkJoinPool();
        String encryptFrase = pool.invoke(caesarCipherThread);
        long time2 = System.currentTimeMillis()-time1;
        this.forkLabel.setText("Fork: "+time2+"ms");
        this.forkTextArea.setText(encryptFrase);
        //System.out.println(encryptFrase);

    }
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}
