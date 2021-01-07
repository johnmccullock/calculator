package main;

import java.util.Stack;

import javax.swing.JDialog;

@SuppressWarnings("serial")
public class Calculator extends JDialog
{
	public static enum Keys
	{
		ADDITION("+", "add", "Add"),
		SUBTRACTION("-", "subtract", "Subtract"),
		MULTIPLY("x", "multiply", "Multiply"),
		DIVIDE("/", "divide", "Divide"),
		CLEAR_ALL("C", "clear_all", "Clear all"),
		CLEAR_ENTRY("CE", "clear_entry", "Clear entry"),
		BACKSPACE("<<", "backspace", "Backspace"),
		PERCENTAGE("%", "percentage", "Percentage"),
		COLON(":", "colon", "Colon"),
		DECIMAL(".", "decimal", "Decimal"), 
		EVALUATE("=", "evaluate", "Evaluate"),
		ZERO("0", null, null),
		ONE("1", null, null),
		TWO("2", null, null),
		THREE("3", null, null),
		FOUR("4", null, null),
		FIVE("5", null, null),
		SIX("6", null, null),
		SEVEN("7", null, null),
		EIGHT("8", null, null),
		NINE("9", null, null);
		
		private String mCaption = null;
		private String mCommand = null;
		private String mTooltip = null;
		
		private Keys(String caption, String command, String tooltip)
		{
			this.mCaption = caption;
			this.mCommand = command;
			this.mTooltip = tooltip;
			return;
		}
		
		public String getCaption()
		{
			return this.mCaption;
		}
		
		public String getCommand()
		{
			return this.mCommand;
		}
		
		public String getTooltip()
		{
			return this.mTooltip;
		}
	}
	
	public static enum Mode
	{
		BASE6("Base 6", "Base 6 (minutes)"),
		BASE10("Base 10", "Base 10 (decimal)");
		
		private String mCaption = null;
		private String mTooltip = null;
		
		private Mode(String caption, String tooltip)
		{
			this.mCaption = caption;
			this.mTooltip = tooltip;
			return;
		}
		
		public String getCaption()
		{
			return this.mCaption;
		}
		
		public String getTooltip()
		{
			return this.mTooltip;
		}
	}
	
	private Stack<Double> mMemory = new Stack<Double>();
	private StringBuilder mInput = new StringBuilder();
	private Keys mOperand = null;
	private Mode mCurrentMode = Mode.BASE10;
	
	public Calculator()
	{
		return;
	}
	
	public void valueEntered(Keys key)
	{
		if(key.equals(Keys.ZERO)){
			this.mInput.append("0");
		}else if(key.equals(Keys.ONE)){
			this.mInput.append("1");
		}else if(key.equals(Keys.TWO)){
			this.mInput.append("2");
		}else if(key.equals(Keys.THREE)){
			this.mInput.append("3");
		}else if(key.equals(Keys.FOUR)){
			this.mInput.append("4");
		}else if(key.equals(Keys.FIVE)){
			this.mInput.append("5");
		}else if(key.equals(Keys.SIX)){
			this.mInput.append("6");
		}else if(key.equals(Keys.SEVEN)){
			this.mInput.append("7");
		}else if(key.equals(Keys.EIGHT)){
			this.mInput.append("8");
		}else if(key.equals(Keys.NINE)){
			this.mInput.append("9");
		}else if(key.equals(Keys.DECIMAL)){
			this.mInput.append(".");
		}else if(key.equals(Keys.ADDITION)){
			this.mOperand = key;
			this.pushInputIntoMemory();
		}else if(key.equals(Keys.SUBTRACTION)){
			this.mOperand = key;
			this.pushInputIntoMemory();
		}else if(key.equals(Keys.MULTIPLY)){
			this.mOperand = key;
			this.pushInputIntoMemory();
		}else if(key.equals(Keys.DIVIDE)){
			this.mOperand = key;
			this.pushInputIntoMemory();
		}else if(key.equals(Keys.PERCENTAGE)){
			this.replaceWithPercentage();
		}else if(key.equals(Keys.CLEAR_ALL)){
			this.mMemory.clear();
			this.mOperand = null;
			this.mInput = new StringBuilder();
		}else if(key.equals(Keys.CLEAR_ENTRY)){
			this.mInput = new StringBuilder();
		}else if(key.equals(Keys.BACKSPACE)){
			this.backspace();
		}else if(key.equals(Keys.EVALUATE)){
			this.evaluateExpression();
		}
		return;
	}
	
	private void pushInputIntoMemory()
	{
		if(this.mInput.length() <= 0){
			return;
		}
		try{
			this.mMemory.push(Double.parseDouble(this.mInput.toString()));
			this.mInput = new StringBuilder();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return;
	}
	
	private void replaceWithPercentage()
	{
		if(this.mMemory.isEmpty()){
			return;
		}
		try{
			double first = this.mMemory.peek();
			double second = Double.parseDouble(this.mInput.toString());
			this.mInput = new StringBuilder();
			this.mInput.append(String.valueOf(first * (second / 100.0)));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return;
	}
	
	private void backspace()
	{
		if(this.mInput.length() <= 0){
			return;
		}
		this.mInput.deleteCharAt(this.mInput.length() - 1);
		return;
	}
	
	private void evaluateExpression()
	{
		if(this.mMemory.isEmpty()){
			return;
		}
		
		try{
			double first = this.mMemory.peek();
			double second = Double.parseDouble(this.mInput.toString());
			double value = 0.0;
			if(this.mOperand.equals(Keys.ADDITION)){
				value = first + second;
			}else if(this.mOperand.equals(Keys.SUBTRACTION)){
				value = first - second;
			}else if(this.mOperand.equals(Keys.MULTIPLY)){
				value = first * second;
			}else if(this.mOperand.equals(Keys.DIVIDE)){
				value = first / second;
			}
			
			this.mInput = new StringBuilder();
			this.mInput.append(this.formatForDisplay(value));
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return;
	}
	
	public String getInput()
	{
		if(this.mInput == null || this.mInput.length() == 0){
			return "0";
		}
		return this.mInput.toString();
	}
	
	private String formatForDisplay(double value)
	{
		String results = null;
		try{
			if(value == Double.NEGATIVE_INFINITY){
				return String.valueOf(Double.NEGATIVE_INFINITY);
			}else if(value == Double.POSITIVE_INFINITY){
				return String.valueOf(Double.POSITIVE_INFINITY);
			}else if(value == Double.NaN){
				return String.valueOf(Double.NaN);
			}else if(Double.isInfinite(value)){
				return "Infinity";
			}else if(Double.isNaN(value)){
				return "NaN";
			}
			if(value % 1 > 0.0){
				results = String.valueOf(value);
			}else{
				results = String.valueOf((long)Math.floor(value));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return results;
	}
	
	public void setBaseMode(Mode mode)
	{
		this.mCurrentMode = mode;
		return;
	}
	
	public Mode getBaseMode()
	{
		return this.mCurrentMode;
	}
}
