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
        private Random random;


        public FacePanel() {
            random = new Random();
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
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            if (head != null) g2d.drawImage(head, 0, -80, this);
            if (face != null) g2d.drawImage(face, 0, -80, this);
            if (eyes != null) g2d.drawImage(eyes, 0, -80, this);

            if (mouths != null && mouths.length > 0) {
                int randomIndex = random.nextInt(mouths.length);
                if (mouths[randomIndex] != null) {
                    g2d.drawImage(mouths[randomIndex], 0, -80, this);
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            if (head != null) {
                return new Dimension(head.getWidth(), head.getHeight());
            }
            return new Dimension(400, 400); 
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new clown());
    }
}