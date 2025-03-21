import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class clown extends JFrame {
    public clown() {
        setTitle("Clown");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 1500);
        setLocationRelativeTo(null);

        FacePanel facePanel = new FacePanel();
        add(facePanel);

        Timer timer = new Timer(2000, e -> facePanel.repaint());
        timer.start();

        setVisible(true);
    }

    class FacePanel extends JPanel {
        private BufferedImage head, eyes, face;
        private BufferedImage[] mouths;
        private int selectedMouthIndex;
        private String[] mouthColors = {"blue", "red", "green", "default"};
        private String selectedMouthColor;

        public FacePanel() {
            Random random = new Random();
            mouths = new BufferedImage[4];

            try {
                head = ImageIO.read(new File("head.png"));
                eyes = ImageIO.read(new File("eyes.png"));
                face = ImageIO.read(new File("face.png"));
                mouths[0] = ImageIO.read(new File("blueMouth.png"));
                mouths[1] = ImageIO.read(new File("redMouth.png"));
                mouths[2] = ImageIO.read(new File("greenMouth.png"));
                mouths[3] = ImageIO.read(new File("mouth.png"));
            } catch (IOException e) {
                System.out.println("Error loading images: " + e.getMessage());
            }

            selectedMouthIndex = random.nextInt(mouths.length);
            selectedMouthColor = mouthColors[selectedMouthIndex];
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            if (head != null) g2d.drawImage(head, 0, -80, this);
            if (face != null) g2d.drawImage(face, 0, -80, this);
            if (eyes != null) g2d.drawImage(eyes, 0, -80, this);

            if (mouths[selectedMouthIndex] != null) {
                g2d.drawImage(mouths[selectedMouthIndex], 0, -80, this);
            }

            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.setColor(Color.BLACK);
            int y = 50;
            Text text = new Text("This", 50, y);
            text = new ClownText(text, 50, y += 20);
            text = new InheritedText(text, 50, y += 20);
            text = new ColorText(text, selectedMouthColor, 50, y += 20);
            text = new MouthText(text, 50, y += 20);
            text.draw(g2d);
        }

        @Override
        public Dimension getPreferredSize() {
            if (head != null) {
                return new Dimension(head.getWidth(), head.getHeight());
            }
            return new Dimension(400, 400);
        }
    }

    class Text {
        protected String text;
        protected int x, y;

        public Text(String text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics2D g2d) {
            g2d.drawString(text, x, y);
        }
    }

    class ClownText extends Text {
        public ClownText(Text previous, int x, int y) {
            super(previous.text + " clown", x, y);
        }
    }

    class InheritedText extends Text {
        public InheritedText(Text previous, int x, int y) {
            super(previous.text + " has inherited", x, y);
        }
    }

    class ColorText extends Text {
        public ColorText(Text previous, String color, int x, int y) {
            super(previous.text + " a " + color, x, y);
        }
    }

    class MouthText extends Text {
        public MouthText(Text previous, int x, int y) {
            super(previous.text + " mouth", x, y);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(clown::new);
    }
}
