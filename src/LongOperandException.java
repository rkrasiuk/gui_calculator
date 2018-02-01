/**
 * Program Name:	LongOperandException.java
 * Purpose: 		an exception class to handle an issue if the operand exceeds the capabilities of JTextField
 * Coder:			Roman Krasiuk
 * Date:			Apr 8, 2017
 */

public class LongOperandException extends Exception
{
	// constructor
	public LongOperandException()
	{
		super("Error: the operand is too long");
	}
}
