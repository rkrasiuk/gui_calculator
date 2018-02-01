/**
 * Program Name:	GUICalculator.java
 * Purpose: 		a class to design a GUI Calculator object
 * Coder:			Roman Krasiuk
 * Date:			Apr 5, 2017
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUICalculator extends JFrame
{
	// class wide scope
	// declaring a Calculator object
	Calculator calc = new Calculator();
	private JMenuBar menuBar;
	private JMenu fileMenu, convertMenu, helpMenu;
	private JMenuItem exitItem, howToUseItem, aboutItem, hexItem, octItem, binItem;
	private JTextField txtField;
	private JPanel panel;
	private JButton[] buttons = new JButton[24];
	private String[] labelsBtns = { "C", "", "%", "+/-", 
								"", "", "", "/",
								"7", "8", "9", "*",
								"4", "5", "6", "-",
								"1", "2", "3", "+",
								"", "0", ".", "="};
	
	//0-arg constructor
	public GUICalculator() 
	{
		// call to the super class
		super("Calculator");
		
		// boiler plate code
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 365);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		/********************  CREATING   ********************/
		// create Font object
		Font font = new Font("SansSerif", Font.PLAIN, 22);
		// create JMenuBar
		menuBar = new JMenuBar();
		// create menus
		fileMenu = new JMenu("File");
		convertMenu = new JMenu("Convert");
		helpMenu = new JMenu("Help");
		// create menuItems
		exitItem = fileMenu.add("Exit"); 
		howToUseItem = helpMenu.add("How To Use");
		aboutItem = helpMenu.add("About"); 
		hexItem = convertMenu.add("Hex");
		octItem = convertMenu.add("Oct");
		binItem = convertMenu.add("Bin");
		// create JTextField
		txtField = new JTextField("0.0");
		// create JPanel
		panel = new JPanel();
		panel.setLayout(new GridLayout(6, 4, 2, 2));
		// create JButtons
		for(int i = 0; i < buttons.length; ++i)
			buttons[i] = new JButton(labelsBtns[i]);
		
		/********************  STYLING   ********************/
		// set font and alignment for the text field
		txtField.setHorizontalAlignment(JTextField.RIGHT);
		txtField.setFont(font);
		txtField.setEditable(false);
		txtField.setBackground(Color.WHITE);
		for(int i = 0; i < buttons.length; ++i)
			buttons[i].setFont(font);
		// adding images for JButtons
		try
		{
			ImageIcon backspace = new ImageIcon(getClass().getResource("backspace.png"));
			ImageIcon squared = new ImageIcon(getClass().getResource("squared.png"));
			ImageIcon squareRoot = new ImageIcon(getClass().getResource("square_root.png"));
			buttons[1].setIcon(backspace);
			buttons[4].setIcon(squared);
			buttons[5].setIcon(squareRoot);
		}
		catch (Exception ex) { JOptionPane.showMessageDialog(panel, ex.getMessage()); }
		// applying system's 'feel and look'
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception ex) { JOptionPane.showMessageDialog(panel, ex.getMessage()); }
		
		/********************  LISTENERS   ********************/
		CalcListener listener = new CalcListener();
		exitItem.addActionListener(listener);
		howToUseItem.addActionListener(listener);
		aboutItem.addActionListener(listener);
		for(int i = 0; i < 24; ++i)
			buttons[i].addActionListener(listener);
		hexItem.addActionListener(listener);
		octItem.addActionListener(listener);
		binItem.addActionListener(listener);
		
		/********************  ADDING   ********************/
		// add menus to the menuBar
		menuBar.add(fileMenu);
		menuBar.add(convertMenu);
		menuBar.add(helpMenu);
		// add buttons to the panel
		for(int i = 0; i < buttons.length; ++i)
			panel.add(buttons[i]);
		
		// set the menu
		this.setJMenuBar(menuBar);
		// add the JTextField
		this.add(txtField, BorderLayout.NORTH);
		// add the JPanel
		this.add(panel);
		// last line
		this.setVisible(true);
	}//end constructor
	
	// INNER CLASS
	/**
	 * Class Name:		CalcListener
	 * Purpose: 		an inner class to make an action listener
	 * Coder:			Roman Krasiuk
	 * Date:			Apr 10, 2017
	 */
	private class CalcListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent ev)
		{
			String value = txtField.getText();
			if (value.equals("0.0"))
				value = "";
			
			// determine which button is pressed
			//exit menuItem
			if (ev.getSource().equals(exitItem)) 
			{
				JOptionPane.showMessageDialog(panel, "Thanks for using GUI Calculator application \n\nRoman Krasiuk", "Exit", JOptionPane.INFORMATION_MESSAGE);
				// closes the window
				System.exit(0);
			}
			//howToUse menuItem
			else if (ev.getSource().equals(howToUseItem))
			{
				JOptionPane.showMessageDialog(howToUseItem, "1. Enter an operand \n2. Enter the operator \n3. Press '=' to get the result", "How To Use", JOptionPane.INFORMATION_MESSAGE);
			}
			//about menuItem
			else if (ev.getSource().equals(aboutItem))
			{
				JOptionPane.showMessageDialog(aboutItem, calc.toString(), "About", JOptionPane.INFORMATION_MESSAGE);
			}
			//'convert to hex' menuItem
			else if (ev.getSource().equals(hexItem))
			{
				try
				{
					txtField.setText(calc.convertHex(value));
				}
				catch (EmptyOperandException ex)
				{
					JOptionPane.showMessageDialog(hexItem, ex.getMessage(), "Convert to hex", JOptionPane.ERROR_MESSAGE);
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(hexItem, ex.getMessage(), "Convert to hex", JOptionPane.ERROR_MESSAGE);
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			//'convert to octal' menuItem
			else if (ev.getSource().equals(octItem))
			{
				try
				{
					txtField.setText(calc.convertOct(value));
				}
				catch (EmptyOperandException ex)
				{
					JOptionPane.showMessageDialog(octItem, ex.getMessage(), "Convert to oct", JOptionPane.ERROR_MESSAGE);
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(octItem, ex.getMessage(), "Convert to oct", JOptionPane.ERROR_MESSAGE);
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			//'convert to binary' menuItem
			else if (ev.getSource().equals(binItem))
			{
				try
				{
					txtField.setText(calc.convertBin(value));
				}
				catch (EmptyOperandException ex)
				{
					JOptionPane.showMessageDialog(binItem, ex.getMessage(), "Convert to bin", JOptionPane.ERROR_MESSAGE);
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(binItem, ex.getMessage(), "Convert to bin", JOptionPane.ERROR_MESSAGE);
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			//clear button
			else if (ev.getSource().equals(buttons[0]))
			{
				calc.clear();
				txtField.setText("0.0");
			}
			//backspace button
			else if (ev.getSource().equals(buttons[1]))
			{
				try
				{
					txtField.setText(calc.backspace(value));
					if(calc.getOperand().equals(""))
						txtField.setText("0.0");
				}
				catch (EmptyOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[1], ex.getMessage());
				}
			}
			//percentage button
			else if (ev.getSource().equals(buttons[2]))
			{
				try
				{
					txtField.setText(calc.findPercentage(value));
				}
				catch (EmptyOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[2], ex.getMessage());
				}
			}
			//'toggle plus minus' button
			else if (ev.getSource().equals(buttons[3]))
			{
				try
				{
					boolean flag;
					if (calc.getOperand().contains("-"))
						flag = false;
					else
						flag = true;
					if(calc.togglePlusMinus(flag).equals("-"))
						txtField.setText("-" + value);
					else 
						txtField.setText(value.substring(1, value.length()));
				}
				catch (EmptyOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[3], ex.getMessage());
				}
			}
			//squared button
			else if (ev.getSource().equals(buttons[4]))
			{
				try
				{
					txtField.setText(calc.findSquared(value));
				}
				catch (EmptyOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[4], ex.getMessage());
				}
			}
			//'square root' button
			else if(ev.getSource().equals(buttons[5]))
			{
				try
				{
					txtField.setText(calc.findSquareRoot(value));
				}
				catch (EmptyOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[5], ex.getMessage());
				}
			}
			// dealing with numbers
			//7
			else if (ev.getSource().equals(buttons[8]))
			{
				try
				{
					calc.buildOperand("7");
					txtField.setText(value + "7");
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[8], ex.getMessage());
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			//8
			else if (ev.getSource().equals(buttons[9]))
			{
				try
				{
					calc.buildOperand("8");
					txtField.setText(value + "8");
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[9], ex.getMessage());
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			//9
			else if (ev.getSource().equals(buttons[10]))
			{
				try
				{
					calc.buildOperand("9");
					txtField.setText(value + "9");
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[10], ex.getMessage());
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			//4
			else if (ev.getSource().equals(buttons[12]))
			{
				try
				{
					calc.buildOperand("4");
					txtField.setText(value + "4");
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[12], ex.getMessage());
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			//5
			else if (ev.getSource().equals(buttons[13]))
			{
				try
				{
					calc.buildOperand("5");
					txtField.setText(value + "5");
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[13], ex.getMessage());
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			//6
			else if (ev.getSource().equals(buttons[14]))
			{
				try
				{
					calc.buildOperand("6");
					txtField.setText(value + "6");
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[14], ex.getMessage());
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			//1
			else if (ev.getSource().equals(buttons[16]))
			{
				try
				{
					calc.buildOperand("1");
					txtField.setText(value + "1");
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[16], ex.getMessage());
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			//2
			else if (ev.getSource().equals(buttons[17]))
			{
				try
				{
					calc.buildOperand("2");
					txtField.setText(value + "2");
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[17], ex.getMessage());
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			//3
			else if (ev.getSource().equals(buttons[18]))
			{
				try
				{
					calc.buildOperand("3");
					txtField.setText(value + "3");
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[18], ex.getMessage());
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			//0
			else if (ev.getSource().equals(buttons[21]))
			{
				try
				{
					calc.buildOperand("0");
					txtField.setText(value + "0");
				}
				catch (LongOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[21], ex.getMessage());
					txtField.setText("0.0");
					calc.setOperand("");
					calc.setDecimalPressed(false);
				}
			}
			// dealing with operators
			//divide
			else if (ev.getSource().equals(buttons[7]) && !calc.getOperand().isEmpty())
			{
				txtField.setText("");
				calc.buildExpression("/");
			}
			//multiply
			else if (ev.getSource().equals(buttons[11]) && !calc.getOperand().isEmpty())
			{
				txtField.setText("");
				calc.buildExpression("*");
			}
			//subtract 
			else if (ev.getSource().equals(buttons[15]) && !calc.getOperand().isEmpty())
			{
				txtField.setText("");
				calc.buildExpression("-");
			}
			//add
			else if (ev.getSource().equals(buttons[19]) && !calc.getOperand().isEmpty())
			{
				txtField.setText("");
				calc.buildExpression("+");
			}
			//equals (calculate)
			else if (ev.getSource().equals(buttons[23]))
			{				
				try 
				{
					txtField.setText("" + calc.calculate());
				}
				catch (EmptyOperandException ex)
				{
					JOptionPane.showMessageDialog(buttons[23], ex.getMessage());
				}
				catch (ArithmeticException ex)
				{
					JOptionPane.showMessageDialog(buttons[23], "Error: division by zero");
					calc.clear();
					txtField.setText("0.0");
				}
			}
			// decimal operator 
			else if (ev.getSource().equals(buttons[22]))
			{
				if(!calc.isDecimalPressed())
				{
					txtField.setText(value + ".");
					calc.setOperand(value + ".");
					calc.setDecimalPressed(true);
				}
			}
		}//end actionPerformed()
		
	}//end INNER CLASS
	
	public static void main(String[] args)
	{
		new GUICalculator();
	}//end main()
	
}//end class
