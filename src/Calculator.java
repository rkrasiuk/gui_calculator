/**
 * Program Name:	Calculator.java
 * Purpose: 		a class to be used to create a Calculator object
 * Coder:			Roman Krasiuk
 * Date:			Apr 5, 2017
 */
import java.util.ArrayList;

public class Calculator
{
	// data members
	private String operand;
	private String operator;
	private boolean decimalPressed;
	private ArrayList<String> list;
	
	// 0-arg constructor
	public Calculator() 
	{
		operand = "";
		operator = "";
		decimalPressed = false;
		list = new ArrayList<String>();
	}
	
	// getters and setters
	public String getOperand()
	{
		return operand;
	}

	public void setOperand(String operand)
	{
		this.operand = operand;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public boolean isDecimalPressed()
	{
		return decimalPressed;
	}

	public void setDecimalPressed(boolean decimalPressed)
	{
		this.decimalPressed = decimalPressed;
	}
	
	/*
	* Method Name: 	clear()
	* Purpose:		resets all fields to their default values
	* Accepts:		NOTHING
	* Returns:		NOTHING
	* Date:  Apr 10, 2017
	*/
	public void clear() 
	{
		operand = "";
		operator = "";
		decimalPressed = false;
		list.clear();
	}
	
	/*
	* Method Name: 	backspace()
	* Purpose:		removes the last number or decimal 
	* Accepts:		value of type String
	* Returns:		a "fixed" String
	* Date:  Apr 10, 2017
	*/
	public String backspace(String value) throws EmptyOperandException
	{
		if (value.equals(""))
			throw new EmptyOperandException();
		else
		{
			if (this.operand.charAt(operand.length() - 1) == '.')
				decimalPressed = false;
			if (this.operand.length() == 1)
				operand = "";
			else
				this.operand = value.substring(0, value.length() - 1);
			return value.substring(0, value.length() - 1);
		}
	}
	
	/*
	* Method Name: 	findPercentage()
	* Purpose:		presents the current value as a percentage in decimal format
	* Accepts:		value of type String
	* Returns:		a String representation of a number
	* Date:  Apr 10, 2017
	*/
	public String findPercentage(String value) throws EmptyOperandException
	{
		if (value.equals(""))
			throw new EmptyOperandException();
		else
		{
			double num = Double.parseDouble(value) / 100.0;
			value = "" + num;
			return value;
		}
	}
	
	/*
	* Method Name: 	togglePlusMinus()
	* Purpose:		add or removes "-" from the operand  
	* Accepts:		a flag of type boolean
	* Returns:		a "-" or an empty String
	* Date:  Apr 10, 2017
	*/
	public String togglePlusMinus(boolean flag) throws EmptyOperandException
	{
		if (this.operand.equals(""))
			throw new EmptyOperandException();
		else
		{
			if (flag)
			{
				operand = "-" + operand;
				return "-";
			}
			else 
			{
				operand = operand.substring(1, operand.length());
				return "";
			}
		}
	}
	
	/*
	* Method Name: 	findSquared()
	* Purpose:		raises the current value to the power of two 
	* Accepts:		value of type String
	* Returns:		a String representation of a squared number
	* Date:  Apr 10, 2017
	*/
	public String findSquared(String value) throws EmptyOperandException
	{
		if (this.operand.equals(""))
			throw new EmptyOperandException();
		else
		{
			double num = Double.parseDouble(value);
			value = "" + (num * num);
			return value;
		}
	}
	
	/*
	* Method Name: 	findSquareRoot()
	* Purpose:		finds the square root of the current value 
	* Accepts:		value of type String
	* Returns:		a String representation of a square root of this number
	* Date:  Apr 10, 2017
	*/ 
	public String findSquareRoot(String value) throws EmptyOperandException
	{
		if (this.operand.equals(""))
			throw new EmptyOperandException();
		else
		{
			double num = Double.parseDouble(value);
			num = Math.sqrt(num);
			value = "" + num;
			return value;
		}
	}
	
	/*
	* Method Name: 	buildOperand()
	* Purpose:		concatenates the current value to the operand
	* Accepts:		value of type String
	* Returns:		NOTHING
	* Date:  Apr 10, 2017
	*/  
	public void buildOperand(String value) throws LongOperandException
	{
		if (this.operand.length() == 23)
			throw new LongOperandException();
		else
			this.operand += value;
	}
	
	/*
	* Method Name: 	buildExpression()
	* Purpose:		assigns the value to the operator and adds both operand and operator to the list
	* Accepts:		an operator of type String
	* Returns:		NOTHING
	* Date:  Apr 10, 2017
	*/ 
	public void buildExpression(String value) 
	{
		operator = value;
		list.add(operand);
		list.add(operator);
		operator = "";
		operand = "";
		decimalPressed = false;
	}
	
	/*
	* Method Name: 	calculate()
	* Purpose:		calculates the answer using the values in the list applying BEDMAS
	* Accepts:		NOTHING
	* Returns:		an answer of type double
	* Date:  Apr 10, 2017
	*/ 
	public double calculate() throws EmptyOperandException, ArithmeticException
	{
		if (this.operand.equals(""))
			throw new EmptyOperandException();
		else
		{
			list.add(operand);
			operand = "";
			decimalPressed = false;
			for (int idx = 1; idx < list.size() && (list.contains("*") || list.contains("/")); idx += 2)
			{
				if(list.get(idx).equals("/"))
				{
					if (Double.parseDouble(list.get(idx + 1)) == 0.0)
						throw new ArithmeticException();
					else
					{
						list.set(idx - 1, "" + (Double.parseDouble(list.get(idx - 1)) / Double.parseDouble(list.get(idx + 1))));
						list.remove(idx);
						list.remove(idx);
						idx -= 2;
					}
				}
				else if (list.get(idx).equals("*"))
				{
					list.set(idx - 1, "" + (Double.parseDouble(list.get(idx - 1)) * Double.parseDouble(list.get(idx + 1))));
					list.remove(idx);
					list.remove(idx);
					idx -= 2;
				}
			}
			for (int idx = 1; idx < list.size(); idx += 2)
			{
				if (list.get(idx).equals("-"))
				{
					list.set(idx - 1, "" + (Double.parseDouble(list.get(idx - 1)) - Double.parseDouble(list.get(idx + 1))));
					list.remove(idx);
					list.remove(idx);
					idx -= 2;
				}
				else if (list.get(idx).equals("+"))
				{
					list.set(idx - 1, "" + (Double.parseDouble(list.get(idx - 1)) + Double.parseDouble(list.get(idx + 1))));
					list.remove(idx);
					list.remove(idx);
					idx -= 2;
				}
			}
			double num = Double.parseDouble(list.get(0));
			if (list.get(0).contains("."))
				decimalPressed = true;
			this.list.clear();
			this.operand = "" + num;
			return num;
		}
	} 
	
	//--------------------------------------------
	/*
	* Method Name: 	convertHex()
	* Purpose:		converts the current operand into hexadecimal  
	* Accepts:		value of type String
	* Returns:		a hex representation of a current operand
	* Date:  Apr 10, 2017
	*/ 
	public String convertHex(String value) throws EmptyOperandException, LongOperandException
	{
		if (this.operand.equals(""))
			throw new EmptyOperandException();
		else if (this.operand.length() == 23)
			throw new LongOperandException();
		else
		{
			int num = (int)Math.round(Double.parseDouble(this.operand));
			String hex = "";
			String temp = "";
			while(num > 0)
			{
				if (!(num % 16 > 9))
					temp = "" + (num % 16);
				else
				{
					switch(num % 16)
					{
					case 10: temp = "A";
							break;
					case 11: temp = "B";
							break;
					case 12: temp = "C";
							break;
					case 13: temp = "D";
							break;
					case 14: temp = "E";
							break;
					case 15: temp = "F";
							break;
					}
				}
				hex = temp + hex;
				num = num / 16;
			}
			return "0x" + hex;
		}
	}

	//
	/*public String convertDec(String value) //throws EmptyOperandException
	{return "";}*/
	
	/*
	* Method Name: 	convertOct()
	* Purpose:		converts the current operand into octal  
	* Accepts:		value of type String
	* Returns:		an octal representation of a current operand
	* Date:  Apr 10, 2017
	*/ 
	public String convertOct(String value) throws EmptyOperandException, LongOperandException
	{
		if (this.operand.equals(""))
			throw new EmptyOperandException();
		else if (this.operand.length() == 23)
			throw new LongOperandException();
		else
		{
			int num = (int)Math.round(Double.parseDouble(this.operand));
			String oct = "";
			while (num > 0)
			{
				oct = (num % 8) + oct;
				num /= 8;
			}
			return oct;
		}
	}
	
	/*
	* Method Name: 	convertBin()
	* Purpose:		converts the current operand into binary  
	* Accepts:		value of type String
	* Returns:		a binary representation of a current operand
	* Date:  Apr 10, 2017
	*/  
	public String convertBin(String value) throws EmptyOperandException, LongOperandException
	{
		if (this.operand.equals(""))
			throw new EmptyOperandException();
		else if (this.operand.length() == 23)
			throw new LongOperandException();
		else
		{
			int num = (int)Math.round(Double.parseDouble(this.operand));
			String bin = "";
			while (num > 0)
			{
				if (bin.length() % 5 == 0)
					bin = " " + bin;
				bin = (num % 2) + bin; 
				num /= 2;
			}
			return bin;
		}
	}
	
	/*
	* Method Name: 	toString()
	* Purpose:		OVERIDDEN method  
	* Accepts:		NOTHING
	* Returns:		a String representation of a Calculator object
	* Date:  Apr 10, 2017
	*/ 
	public String toString() { return "Calculator, 2017 \n(c) Roman Krasiuk"; }
}
