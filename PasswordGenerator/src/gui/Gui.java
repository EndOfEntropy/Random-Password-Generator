/**
 * Graphical User Interface that displays a Random Password Generator and password scoring system
 * User can type in a password or generate a random password
 * 
 * Password requirements:
 * character type = lower or upper case letters, digits and specials characters
 * length
 * 
 * Includes scoring system and password complexity features
 * 
 */
package gui;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Collections;
import pwgen.Password;

/**
 * @author Mickael Grivolat
 */

// Graphic User Interface displaying the random password generator
public class Gui extends JFrame implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	private static ImageIcon logo;
	private static JFrame frame;
	private static JPanel panel1, panel2, panel3, panel4, panel5, center1, center2, center3, center4;
	private static JLabel label1, label2, label3, label4, label5, label6, error;
	private static JPasswordField textField1;
	private static JTextField textField2;
	private static JButton button1;
	private static JCheckBox checkBox0, checkBox1, checkBox2, checkBox3, checkBox4;
	private static JProgressBar bar;
	private static JPopupMenu popup;
	private static JMenuItem copy, paste;
	
	private static char mask;
	private static final int frameW = 800, frameH = 600;
	
	
	Gui() {
		setup();
	}
	
	// Creating instance of all GUI required resources
	private void setup() {
		// Set the frame of the GUI and load the logo
		logo = new ImageIcon("src\\data\\Padlock.png");
		frame = new JFrame();
		frame.setSize(frameW, frameH);
		frame.setLayout(new BorderLayout());
		frame.setTitle("Password Generator");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		frame.setIconImage(logo.getImage());
		frame.addMouseListener(this);
		
		
		// Set the panel components in the frame.
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		center1 = new JPanel();
		center2 = new JPanel();
		center3 = new JPanel();
		center4 = new JPanel();
		
		panel1.setBackground(Color.white);
		panel2.setBackground(new Color(179,255,242)); //Color = Celeste
		panel3.setBackground(new Color(179,255,242));
		panel4.setBackground(new Color(179,255,242));
		panel5.setBackground(new Color(179,255,242));
		center1.setBackground(new Color(173,216,230));
		center2.setBackground(new Color(173,216,230));
		center3.setBackground(new Color(173,216,230));
		center4.setBackground(new Color(173,216,230));
		
		panel2.setPreferredSize(new Dimension(200, 200)); // central panel (panel1) is dynamic, no need to set size
		panel3.setPreferredSize(new Dimension(50, 50));
		panel4.setPreferredSize(new Dimension(25, 25));
		panel5.setPreferredSize(new Dimension(75, 75));
		center1.setPreferredSize(new Dimension(250, 250));
		center2.setPreferredSize(new Dimension(250, 250));
		center3.setPreferredSize(new Dimension(250, 80));
		center4.setPreferredSize(new Dimension(250, 80));
		// Layouts setup
		panel1.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));	// center panel
		panel2.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));	// right panel
		center1.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));	// left-center panel
		center2.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));	// right-center panel
		center3.setLayout(new FlowLayout(FlowLayout.LEADING, 30, 20));	// sub right-center panel 1
		center4.setLayout(new FlowLayout(FlowLayout.LEADING, 30, 20));	// sub right-center panel 2
		
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.EAST);
		frame.add(panel3, BorderLayout.WEST);
		frame.add(panel4, BorderLayout.NORTH);
		frame.add(panel5, BorderLayout.SOUTH);
		panel1.add(center1);
		panel1.add(center2);
		center2.add(center3);
		center2.add(center4);
		
		// Label and text field for password user input
		label1 = new JLabel("Password");
		label1.setFont(new Font("Dialogue", Font.BOLD, 13));
		checkBox0 = new JCheckBox("Hide password");
		checkBox0.setOpaque(false);
		checkBox0.setFocusable(false);
		checkBox0.setSelected(true);
		checkBox0.addActionListener(this);
		textField1 = new JPasswordField(17);
		textField1.putClientProperty("JPasswordField.cutCopyAllowed",true); // Allow Ctrl-c in password field
		textField1.getDocument().addDocumentListener(addDocumentListener1());
		textField1.addMouseListener(this);
		mask = textField1.getEchoChar();
		center1.add(label1);
		center1.add(checkBox0);
		center3.add(textField1);

		// Label fields and Progress bar for the complexity
		label2 = new JLabel("Complexity");
		label3 = new JLabel("Score                              ");
		label4 = new JLabel("");
		label2.setFont(new Font("Dialogue", Font.BOLD, 13));
		label3.setFont(new Font("Dialogue", Font.BOLD, 13));
		label4.setFont(new Font("Dialogue", Font.BOLD, 13));
		bar = new JProgressBar(0, 100);
		bar.setPreferredSize(new Dimension(190, 20));
		bar.setBackground(Color.white);
		bar.setVisible(false);
		center1.add(label2);
		center1.add(label3);
		center3.add(label4);
		center4.add(bar);

		// Button to generate a random password
		button1 = new JButton("Generate password");
		button1.setFont(new Font("Dialogue", Font.BOLD, 13));
		button1.addActionListener(this);
		center1.add(button1);
		
		// Right-side panel setup
		// check boxes for characters options and text field for the length
		checkBox1 = new JCheckBox("lower case letters");
		checkBox2 = new JCheckBox("upper case letters");
		checkBox3 = new JCheckBox("numbers");
		checkBox4 = new JCheckBox("special characters");
		
		label5 = new JLabel("Password requirements:");
		label6 = new JLabel("Length");
		label5.setFont(new Font("Dialogue", Font.BOLD, 13));
		setUnderline(label5);
		textField2 = new JTextField(6);
		textField2.getDocument().addDocumentListener(addDocumentListener2());
		error = new JLabel("Please enter a number > 0");
		error.setForeground(Color.red);
		error.setVisible(false);
		
		checkBox1.setOpaque(false);
		checkBox2.setOpaque(false);
		checkBox3.setOpaque(false);
		checkBox4.setOpaque(false);
		checkBox1.setFocusable(false);
		checkBox2.setFocusable(false);
		checkBox3.setFocusable(false);
		checkBox4.setFocusable(false);
		
		panel2.add(label5);
		panel2.add(checkBox1);
		panel2.add(checkBox2);
		panel2.add(checkBox3);
		panel2.add(checkBox4);		
		panel2.add(label6);
		panel2.add(textField2);
		panel2.add(error);
		
		// add menu items to popup
		popup = new JPopupMenu();
		copy = new JMenuItem("Copy");
		paste = new JMenuItem("Paste");
		popup.add(copy);
		popup.add(paste);
		copy.addActionListener(this);
		paste.addActionListener(this);
		
		// repaint the frame for newly added components
		frame.revalidate();
		frame.repaint();
	}
	
	// Action event handler for (checkBox0) pw hiding, (button1) pw generator and copy/paste menu functionalities
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(copy)) {
			clipboardCopy();
		}
		if(e.getSource().equals(paste)) {
			clipboardPaste();
		}
		
		if(e.getSource().equals(checkBox0)) {
			if(!checkBox0.isSelected()) {
				textField1.setEchoChar((char)0);
			} else {
				textField1.setEchoChar(mask);
			}
		}
		
		if(e.getSource().equals(button1)) {
			
			if(noCheckboxChecked()) {
				textField1.setForeground(Color.black);
				textField1.setText("Select one character type");
				textField1.setVisible(true);
				label4.setVisible(false);
				bar.setVisible(false);
				return;
			} else {
				textField1.setText("");
				label4.setVisible(true);
				bar.setVisible(false);
			}
			
			try {
				Password password = new Password(Integer.parseInt(textField2.getText()), checkBox1.isSelected(),
						checkBox2.isSelected(), checkBox3.isSelected(), checkBox4.isSelected());
				textField1.setForeground(Color.black);
				textField1.setText(password.getPassword());
				textField1.setVisible(true);
				int score = password.calculateScore(password.getPassword());
				label4.setText(password.setComplexity(score));
				setBarColor(score);
			} catch (IllegalArgumentException e2) {
				// do nothing, error messages displayed on the application
			}
		}
	}
	
	// Event manager for textField1 (pw string) to calculate the pw score and complexity
	// Document Listener is called when anything is typed or deleted in a given text field
	private DocumentListener addDocumentListener1() {
		 return new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			//JPasswordField returns a char[] (points to address), use copyValueOf method to get the char[] value
			@Override
			public void changedUpdate(DocumentEvent e) {
				Password pw = new Password(String.copyValueOf(textField1.getPassword())); 
				textField1.setForeground(Color.black);
				int score = pw.calculateScore(pw.getPassword());
				label4.setText(pw.setComplexity(score));
				label4.setVisible(true);
				bar.setVisible(true);
				bar.setValue(score);
				setBarColor(score);
			}
		 };
	}
	
	// Event manager for textField2 (pw length) to manage user input
	// Document Listener is called when anything is typed or deleted in a given text field
	private DocumentListener addDocumentListener2() {
		 return new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if(isNumericAndPositive(textField2.getText())) {
					error.setVisible(false);
					return;
				}
				error.setVisible(true);
			}
		 };
	}
	
	// Event manager for mouse actions
	@Override
	public void mouseClicked(MouseEvent e) {
		// Mouse pressed and released
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		mouseReleased(e);
		
	}
	// isPopupTrigger should be checked in both mousePressedand mouseReleasedfor proper cross-platform functionality.
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.isPopupTrigger()) {
			popup.show(e.getComponent(), e.getX(), e.getY());
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// Invoked when a mouse enters a component
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// Invoked when a mouse exits a component
		
	}
	
	private void clipboardCopy() {
		StringSelection ss = new StringSelection(String.copyValueOf(textField1.getPassword()));
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}
	
	private void clipboardPaste() {
		Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		if (systemClipboard.isDataFlavorAvailable(DataFlavor.stringFlavor))
        {
            try {
				String text = (String)systemClipboard.getData(DataFlavor.stringFlavor);
				textField1.setText(text);
			} catch (Exception e2) {
				//handle exception
			}
        }
	}
	
	// set the progress bar color based on the pw score
	private void setBarColor(int score) {
		if(score < 40) {
			bar.setForeground(new Color(153,255,204));
			return;
		}
		if(score < 60) {
			bar.setForeground(new Color(102,255,178));
			return;
		}
		if(score < 80) {
			bar.setForeground(new Color(51,255,153));
			return;
		}
		bar.setForeground(new Color(0,255,128));
	}
	// checks if a string contains a number
	private boolean isNumeric(String str) {
		if(str == null) {
			return false;
		}
		
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		
		return true;
	}
	// return if a string is a positive number
	private boolean isNumericAndPositive(String str) {
		if(isNumeric(str) && Integer.parseInt(str) > 0) {
			return true;
		}
		return false;
	}
	// return if all pw options are NOT selected (lower or upper case letters, digits and specials characters)
	private boolean noCheckboxChecked() {
		if(!checkBox1.isSelected() && !checkBox2.isSelected() && !checkBox3.isSelected() && !checkBox4.isSelected()) {
			return true;
		}
		return false;
	}
	// set a label to be underlined
	private void setUnderline(JLabel label) {
		Font font = label.getFont();
		font = font.deriveFont(Collections.singletonMap(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON));
		label.setFont(font);
	}
	
	public static void main(String[] args) {
		new Gui();
		
	}





}
