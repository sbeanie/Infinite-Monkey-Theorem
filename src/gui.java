/**
 * Created by Tom on 01/08/14.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class gui
        extends JFrame
        implements ActionListener
{
    public static JPanel panel;
    JLabel InfoLabel;
    JLabel TextLabel;
    JLabel RunsLabel;
    static JTextField textField;
    static JTextField runsField;
    JButton StartButton;
    public static JLabel percentageCompleted;
    public static JLabel lettersPerSecondLabel;
    static double completed;
    public static boolean running = false;
    private gui.Task task;
    public static int runs;
    public long i;

    public gui()
    {
        panel = new JPanel(null);

        this.InfoLabel = new JLabel();
        this.InfoLabel.setLocation(30, 0);
        this.InfoLabel.setSize(400, 50);
        this.InfoLabel.setText("Tom Parker's Maths Exploration Program!");
        Font infoLabelFont = new Font(this.InfoLabel.getFont().getName(), 1, 16);
        this.InfoLabel.setFont(infoLabelFont);
        panel.add(this.InfoLabel);

        this.TextLabel = new JLabel();
        this.TextLabel.setLocation(23, 70);
        this.TextLabel.setSize(100, 48);
        this.TextLabel.setText("Enter Text:");
        panel.add(this.TextLabel);

        this.RunsLabel = new JLabel();
        this.RunsLabel.setLocation(24, 131);
        this.RunsLabel.setSize(100, 50);
        this.RunsLabel.setText("Enter Runs:");
        panel.add(this.RunsLabel);

        textField = new JTextField();
        textField.setLocation(133, 79);
        textField.setSize(100, 30);
        textField.setText("");
        textField.setColumns(10);
        panel.add(textField);

        runsField = new JTextField();
        runsField.setLocation(133, 141);
        runsField.setSize(100, 30);
        runsField.setText("");
        runsField.setColumns(10);
        panel.add(runsField);

        this.StartButton = new JButton();
        this.StartButton.setLocation(265, 98);
        this.StartButton.setSize(100, 50);
        this.StartButton.setBackground(new Color(-65536));
        this.StartButton.setText("START");
        this.StartButton.addActionListener(this);
        panel.add(this.StartButton);

        percentageCompleted = new JLabel();
        percentageCompleted.setLocation(100, 210);
        percentageCompleted.setSize(300, 30);
        percentageCompleted.setText("Percentage Completed: " + completed + "%");
        panel.add(percentageCompleted);

        lettersPerSecondLabel = new JLabel();
        lettersPerSecondLabel.setLocation(100, 180);
        lettersPerSecondLabel.setSize(300, 30);
        lettersPerSecondLabel.setText("Letters/Second Generated: 0");
        panel.add(lettersPerSecondLabel);

        add(panel);
    }

    class Task
            extends SwingWorker<Void, Void>
    {
        Task() {}

        public Void doInBackground()
        {
            System.out.println(Integer.parseInt(gui.runsField.getText()));
            monkey.start(gui.textField.getText(), Integer.parseInt(gui.runsField.getText()));
            new monkey().start();
            gui.running = true;
            for (gui.runs = 1; gui.runs <= Integer.parseInt(gui.runsField.getText()); gui.runs += 1)
            {
                monkey.findWord();
                gui.completed = monkey.runsData / monkey.runs * 100.0D;
                gui.this.i = Math.round(gui.completed);
                try
                {
                    Thread.sleep(5L);
                }
                catch (InterruptedException ignore) {}
                gui.lettersPerSecondLabel.setText("Letters/Second Generated: " + monkey.lettersPerSecond);
                gui.percentageCompleted.setText("Percentage Completed: " + (int)gui.this.i + "%");
            }
            return null;
        }
    }

    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == this.StartButton)
        {
            completed = 0.0D;
            this.task = new gui.Task();
            this.task.execute();
        }
    }

    public static void startGUI()
    {
        gui first = new gui();
        first.setTitle("Maths Exploration!");
        first.setSize(400, 310);
        first.setDefaultCloseOperation(3);
        first.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run() {
                startGUI();
            }
        });
    }
}

