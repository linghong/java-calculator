
//Calculator.java
/**
* This class is the homework for Unit6 program problem [7] and the extra credit Options [2]
* The calculator can function as a real math calculator
*
*@author Linghong Chen
*@version Last modified on April 22th, 2014
**/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator 
{
	JButton clear;
	JButton squareroot;
	JButton divide;
	JButton multiply;
	JButton minus;
	JButton plus;
	JButton equal;		
	JButton negate;
	JButton dot;
	JButton [] digitButtons; //for number 0 to 9
	JPanel displayarea;    //display screen	
	JLabel label;  //show the calculation and the input
	double result;         //to hold the number for each step of a calcualtion
	String resultString= ""; //the string of the result
	double number;   // to compute a complete number typed in
	String numberString = ""; // the string of the number
	String operator = "";     // to hold any symbol of +,-,* or /
	String previousOperator;   //to remember the operator when "=" is clicked
	String previousNumberString; //to remember the operator when "=" is clicked
	String lastSymbol;			//record the '=" sysmbol
	
	//main 
	public static void main(String [] args)
	{
		Calculator c = new Calculator();
	}
	
	//construct
	public Calculator()	
	{
		JFrame frame = new JFrame("calculator");	
		frame.setSize(400,600);
		
		
		//display screen
		displayarea = new JPanel();
		displayarea.setBackground(Color.YELLOW);
		displayarea.setSize(400,200);
		frame.add(displayarea,BorderLayout.NORTH);

		label = new JLabel("");
		displayarea.add(label);

		//button area
		JPanel buttonarea =new JPanel();
		buttonarea.setSize(400,400);
		frame.add(buttonarea, BorderLayout.CENTER);

		clear = new JButton("C");
		squareroot = new JButton ("SQRT");
		divide = new JButton("/");
		multiply = new JButton ("*");
		minus = new JButton("-");
		plus = new JButton ("+");
		equal = new JButton ("=");		
		negate = new JButton("+/-");
		dot = new JButton(".");

		clear.addActionListener(new Click());
		squareroot.addActionListener(new Click());
		divide.addActionListener(new Click());
		multiply.addActionListener(new Click());
		minus.addActionListener(new Click());
		plus.addActionListener(new Click());
		equal.addActionListener(new Click());
		negate.addActionListener(new Click());
		dot.addActionListener(new Click());

		digitButtons = new JButton[10];
		for (int i=0; i<10; i++)
		{
			digitButtons[i] = new JButton(Integer.toString(i));
			digitButtons[i].addActionListener(new Click());
		}

		buttonarea.setLayout(new GridLayout(5,4));
		buttonarea.add(clear);
		buttonarea.add(squareroot);
		buttonarea.add(divide);
		buttonarea.add(multiply);
		buttonarea.add(digitButtons[7]);
		buttonarea.add(digitButtons[8]);
		buttonarea.add(digitButtons[9]);
		buttonarea.add(minus);
		buttonarea.add(digitButtons[4]);
		buttonarea.add(digitButtons[5]);
		buttonarea.add(digitButtons[6]);
		buttonarea.add(plus);
		buttonarea.add(digitButtons[1]);
		buttonarea.add(digitButtons[2]);
		buttonarea.add(digitButtons[3]);
		buttonarea.add(equal);
		buttonarea.add(digitButtons[0]);
		buttonarea.add(negate);
		buttonarea.add(dot);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}

	//do math calculation
	public void calculate()
	{	
		if(operator== "+")
		{
			result = result + number;
		}
		else if(operator== "-")	
		{
			result = result - number;
		}
		else if(operator== "*")
		{
			result = result * number;
		}
		else if(operator == "/")
		{
			result = result/number;	
		} 

		number= 0;  //they have been used, so set everything back to empty state
		numberString ="";
		operator = "";		
		label.setText(formate(result));
	}

	public static String formate(double n)     //make the number shows on string without ".0"
	{
		int dotPosition = Double.toString(n).indexOf(".");
		String decimal = Double.toString(n).substring(dotPosition);
		if (Double.parseDouble(decimal) ==0) 
		{
			int intNumber= (int) n;
			return Integer.toString(intNumber);
		}

		else return Double.toString(n);
	}

	public double calculateNumber()
	{
		number = Double.parseDouble(numberString);
		numberString = "";   //the numbersting have been transferred to number, so set it back to empty state
		return number;
	}

	class Click implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//for clear button
			if(e.getSource()==clear) 
			{
				label.setText("0");
				number = 0;
				numberString ="";
				result = 0;
				resultString = "";
				lastSymbol="other";
			}
			
			//for "." button
			else if(e.getSource()==dot)
			{
				if(lastSymbol!="dot") //for example, 53, then click "." 
				{
					numberString = numberString +".";
					label.setText(numberString);
				}

				lastSymbol="dot"; //record".' information, thus it won't add more dots when repeatedly click".""
			}

			//for calculating square root
			else if(e.getSource()==squareroot)
			{
				if(numberString == "")
				{
					result=Math.sqrt(result);
					label.setText(formate(result));
				}
				else
				{
					calculateNumber();
				 	number = Math.sqrt(number);
				 	numberString = Double.toString(number);
				 	label.setText(formate(number));
				}
				lastSymbol="SQRT";
			}

			//for calculating the negate 
			else if (e.getSource()==negate) 
			{
				if(numberString == "")
				{
					result=0- result;
					label.setText(formate(result));
				}
				else 
				{
					calculateNumber();
					number = 0- number;	
					numberString = Double.toString(number);		
					label.setText(formate(number));
				}
				lastSymbol="other";
			}
			
			// when "="" is typed, 
			else if(e.getSource()==equal)
			{				
				//for example, click 123+45=, then click this "=", meaning repeat the same equation
				if (numberString == "" && lastSymbol=="=")  
				{
					operator=previousOperator;
					numberString=previousNumberString;
					calculateNumber();
					calculate();
				}
				
				//for example, click 123+, then click this "=", meaning repeat the same equation
				else if (numberString == "" && lastSymbol =="operator")  
				{
					number =result;
					numberString=resultString;
					previousNumberString=numberString;
					previousOperator=operator;   //remember the operator
					calculate();
				}


				else //if(numberString != "") 
				//example 1, 123, then click this "="
				//example 2, 123+45, then click this "="
				{
					previousNumberString=numberString;  	//remember the numberString before calculate number
					calculateNumber(); 

					if (operator!="") //normal situation, such as 123 + 45, then click this"=""
					{
						previousOperator=operator;   //remember the operator before calculate
						calculate();
					}
				}

				lastSymbol = "=";				//record the '=" sysmbol
			}

			//if clicking a button that is not digit, it is possible a complete number is entered
			//so the calculatoe needs to get the number and also acquire the operator
			else if(e.getSource()==plus|| e.getSource() == minus||e.getSource() == multiply||e.getSource() == divide)  		
			{
				if(numberString != "") 
				{
					if(operator=="" && resultString == null) // for example, 123, then click this operator button
					{
						calculateNumber();
						result = number; 
						resultString = Double.toString(result);
					}
					if(operator=="" && resultString !=null) // for example, click 123+45=67, then click this operator
					{
						result=0;						
						calculateNumber();
						result = number; 
						resultString = Double.toString(result);
					}

					else if(operator!="") 
					//such as: after enter 123+45, then click this operator button 
					{
						calculateNumber();	//calculate the new number(45)
						calculate();  		//calculate 123+45					
					}	
				}			
				
				/*else 
				if numberString == null, it has the following condition:
				1)if lastSymbol=="=", for example, 123+45=, then then click this operator button
				2) lastSymbol=="operator", for example 34+17-, then click this operator button
				only need to get the operator
				*/

				//refill the operator with the newly clicked operator button 
				if(e.getSource() == plus) operator = "+";
				if(e.getSource() == minus) operator ="-";
				if(e.getSource() == multiply) operator = "*";
				if(e.getSource() == divide) operator ="/";			

				lastSymbol="operator";
			}

			else //when typing another digit button, the following method helps to add each digit
			{
				for( int i = 0; i<10;i++)
				{
					if(e.getSource()==digitButtons[i])
					{
						numberString = numberString+Integer.toString(i); //i is the digit numbet just typed
						label.setText(numberString);
						break;
					}
				}
				label.setText(numberString);
				lastSymbol="digit";	
			}
		}
	}
}