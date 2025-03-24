import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; //all these four This is the main class that initializes the GUI and handles user interactions.
import javax.swing.*;

public class ElevatorMachineGUI {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("JKUAT Towers Elevator System");  //Imports required libraries for GUI creation and event handling.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setSize(800, 500);//Creates the main GUI window (JFrame) with the title "JKUAT Towers Elevator System".
        frame.setLayout(new BorderLayout());//Sets the window size and ensures the program closes properly when exited.

        // Header Label
        JLabel headerLabel = new JLabel("Welcome to JKUAT Towers Elevator System", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));// Creates a header label with a bold font and adds it to the top (North) of the GUI.


        frame.add(headerLabel, BorderLayout.NORTH);

        // Input and Button Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 1, 10, 10));//Creates an input panel with a 3-row grid layout.

        JLabel instructionLabel = new JLabel("Enter the floor you wish to go to (0 to 10):", JLabel.CENTER);
        JTextField floorInput = new JTextField();// Displays an instruction label and a text input field where users enter a floor number.
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton submitButton = new JButton("Submit");//Creates "Submit" and "Reset" buttons for user interaction.
        JButton resetButton = new JButton("Reset");

        buttonPanel.add(submitButton);//Adds buttons to the panel.
        buttonPanel.add(resetButton);

        inputPanel.add(instructionLabel);//adds all components to input panel.
        inputPanel.add(floorInput);
        inputPanel.add(buttonPanel);

        frame.add(inputPanel, BorderLayout.CENTER);// Places the input panel in the center of the GUI.

        // Elevator and Animation Panel
        ElevatorPanel elevatorPanel = new ElevatorPanel();//Creates an elevator animation panel
        frame.add(elevatorPanel, BorderLayout.WEST);

        // Action Listener for Submit Button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = floorInput.getText();
                try {
                    int floor = Integer.parseInt(inputText);//Reads the user's floor input and converts it to an integer.

                    // Validate floor input
                    if (floor < 0 || floor > 10) {
                        elevatorPanel.setMessage("Invalid floor! Enter a number between 0 and 10.", Color.RED);
                    } else {
                        // Determine the assigned door
                        String door = "";
                        if (floor <= 5) {
                            door = "Door A (Ground Floor to 5th Floor)";
                        } else if (floor <= 8) {
                            door = "Door B (Ground Floor to 8th Floor)";
                        } else {
                            door = "Door C (Ground Floor to 10th Floor)";
                        }

                        elevatorPanel.setMessage("Assigned Door: " + door, Color.BLACK);
                        elevatorPanel.startAnimation(floor);//Displays the assigned door and starts the elevator animation.
                    }
                } catch (NumberFormatException ex) { 
                    elevatorPanel.setMessage("Invalid input! Please enter a valid number.", Color.RED);
                }//Handles invalid inputs
            }
        });

        // Action Listener for Reset Button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                floorInput.setText("");
                elevatorPanel.resetAnimation();
            }
        });

        // Display the frame
        frame.setVisible(true);
    }
}

// Elevator Panel (Moving Elevator with Stick Figure Inside & Stepping Out)
class ElevatorPanel extends JPanel {
    private int elevatorY = 300; // Elevator position (y-axis)
    private int targetY = 300; // Target position for smooth movement
    private int currentFloor = 0; // Displayed floor number
    private int doorWidth = 30; // Width of elevator doors
    private boolean animating = false;
    private boolean steppingOut = false; // Stick figure stepping out
    private Timer timer;
    private String message = "";

    public ElevatorPanel() {
        this.setPreferredSize(new Dimension(300, 400));
        this.setBackground(Color.LIGHT_GRAY);
        
        // Timer for animation
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (animating) {
                    if (elevatorY > targetY) {
                        elevatorY -= 5; // Move up
                    } else if (elevatorY < targetY) {
                        elevatorY += 5; // Move down
                    }

                    // Update current floor number
                    int calculatedFloor = (300 - elevatorY) / 25;
                    if (calculatedFloor != currentFloor) {
                        currentFloor = calculatedFloor;
                    }

                    // Open/Close elevator doors
                    if (elevatorY == targetY) {
                        if (doorWidth > 0) {
                            doorWidth -= 5; // Open doors
                        } else {
                            steppingOut = true; // Start stepping out animation
                        }
                    } else {
                        doorWidth = 30; // Keep doors closed while moving
                        steppingOut = false;
                    }

                    repaint();

                    if (elevatorY == targetY && doorWidth == 0) {
                        animating = false;
                    }
                }
            }
        });
    }

    public void startAnimation(int floor) {
        animating = true;
        steppingOut = false;
        targetY = 300 - (floor * 25); // Calculate Y position based on floor
        doorWidth = 30; // Reset doors before moving
        timer.start();
    }

    public void resetAnimation() {
        animating = false;
        steppingOut = false;
        elevatorY = 300;
        currentFloor = 0;
        doorWidth = 30;
        message = "";
        repaint();
    }

    public void setMessage(String msg, Color color) {
        this.message = msg;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Elevator Box
        g.setColor(Color.BLUE);
        g.fillRect(200, elevatorY, 60, 80); // Elevator moving up and down

        // Floor Number Display
        g.setColor(Color.WHITE);
        g.fillRect(215, elevatorY + 5, 30, 20); // Display box
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(currentFloor), 225, elevatorY + 20); // Current floor text

        // Elevator Doors (Animated Opening & Closing)
        g.setColor(Color.DARK_GRAY);
        g.fillRect(200, elevatorY, doorWidth, 80); // Left door
        g.fillRect(230 + (30 - doorWidth), elevatorY, doorWidth, 80); // Right door

        // Draw Stick Figure Inside or Stepping Out
        g.setColor(Color.BLACK);
        int stickX = steppingOut ? 180 : 220; // Move left when stepping out

        // Head
        g.fillOval(stickX, elevatorY + 10, 10, 10);

        // Body
        g.drawLine(stickX + 5, elevatorY + 20, stickX + 5, elevatorY + 50);

        // Legs
        g.drawLine(stickX + 5, elevatorY + 50, stickX - 5, elevatorY + 70);
        g.drawLine(stickX + 5, elevatorY + 50, stickX + 15, elevatorY + 70);

        // Arms
        g.drawLine(stickX + 5, elevatorY + 30, stickX - 5, elevatorY + 40);
        g.drawLine(stickX + 5, elevatorY + 30, stickX + 15, elevatorY + 40);

        // Display message if any
        if (!message.isEmpty()) {
            g.setColor(Color.RED);
            g.drawString(message, 50, 380);
        }
    }
}
