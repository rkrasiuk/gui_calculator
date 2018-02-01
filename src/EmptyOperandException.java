/**
 * Program Name:	EmptyOperandException.java
 * Purpose: 		an exception class to handle the empty operand issue
 * Coder:			Roman Krasiuk
 * Date:			Apr 5, 2017
 */

public class EmptyOperandException extends Exception
{
	
	// constructor
	public EmptyOperandException()
	{
		super("Error: operand is empty, please enter a value");
	}
	
}
