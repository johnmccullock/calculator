package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class CalculatorFactory
{
	private static final String DEFAULT_TITLE = "Calculator";
	private static final Insets PANEL_INSETS = new Insets(5, 5, 5, 5);
	private static final Insets TEXT_FIELD_INSETS = new Insets(2, 5, 2, 5);
	
	public CalculatorFactory()
	{
		
		return;
	}
	
	public Calculator create()
	{
		final int FONT_STEP = 2;
		
		Calculator gui = new Calculator();
		gui.setTitle(DEFAULT_TITLE);
		
		JLabel temp = new JLabel();
		Font font = new Font(temp.getFont().getFontName(), temp.getFont().getStyle(), temp.getFont().getSize() + FONT_STEP);
		
		JPanel basePanel = new JPanel();
		basePanel.setLayout(new GridBagLayout());
		basePanel.setBorder(BorderFactory.createEmptyBorder(PANEL_INSETS.top, PANEL_INSETS.left, PANEL_INSETS.bottom, PANEL_INSETS.right));
		
		JTextField display = this.createDisplayField(font);
		
		ActionListener additionListener = this.createOperandButtonListener(gui, Calculator.Keys.ADDITION);
		ActionListener subtractListener = this.createOperandButtonListener(gui, Calculator.Keys.SUBTRACTION);
		ActionListener multiplyListener = this.createOperandButtonListener(gui, Calculator.Keys.MULTIPLY);
		ActionListener divideListener = this.createOperandButtonListener(gui, Calculator.Keys.DIVIDE);
		ActionListener percentListener = this.createCalcButtonListener(gui, display, Calculator.Keys.PERCENTAGE);
		ActionListener clearAllListener = this.createCalcButtonListener(gui, display, Calculator.Keys.CLEAR_ALL);
		ActionListener clearEntryListener = this.createCalcButtonListener(gui, display, Calculator.Keys.CLEAR_ENTRY);
		ActionListener backspaceListener = this.createCalcButtonListener(gui, display, Calculator.Keys.BACKSPACE);
		ActionListener colonListener = this.createCalcButtonListener(gui, display, Calculator.Keys.COLON);
		ActionListener decimalListener = this.createCalcButtonListener(gui, display, Calculator.Keys.DECIMAL);
		ActionListener evaluateListener = this.createCalcButtonListener(gui, display, Calculator.Keys.EVALUATE);
		ActionListener zeroListener = this.createCalcButtonListener(gui, display, Calculator.Keys.ZERO);
		ActionListener oneListener = this.createCalcButtonListener(gui, display, Calculator.Keys.ONE);
		ActionListener twoListener = this.createCalcButtonListener(gui, display, Calculator.Keys.TWO);
		ActionListener threeListener = this.createCalcButtonListener(gui, display, Calculator.Keys.THREE);
		ActionListener fourListener = this.createCalcButtonListener(gui, display, Calculator.Keys.FOUR);
		ActionListener fiveListener = this.createCalcButtonListener(gui, display, Calculator.Keys.FIVE);
		ActionListener sixListener = this.createCalcButtonListener(gui, display, Calculator.Keys.SIX);
		ActionListener sevenListener = this.createCalcButtonListener(gui, display, Calculator.Keys.SEVEN);
		ActionListener eightListener = this.createCalcButtonListener(gui, display, Calculator.Keys.EIGHT);
		ActionListener nineListener = this.createCalcButtonListener(gui, display, Calculator.Keys.NINE);
		
		TreeMap<Calculator.Keys, JButton> buttons = new TreeMap<Calculator.Keys, JButton>();
		for(Calculator.Keys key : Calculator.Keys.values())
		{
			buttons.put(key, this.createCommandButton(key.getCaption(), key.getCommand(), key.getTooltip(), font));
		}
		buttons.get(Calculator.Keys.ZERO).addActionListener(zeroListener);
		buttons.get(Calculator.Keys.ONE).addActionListener(oneListener);
		buttons.get(Calculator.Keys.TWO).addActionListener(twoListener);
		buttons.get(Calculator.Keys.THREE).addActionListener(threeListener);
		buttons.get(Calculator.Keys.FOUR).addActionListener(fourListener);
		buttons.get(Calculator.Keys.FIVE).addActionListener(fiveListener);
		buttons.get(Calculator.Keys.SIX).addActionListener(sixListener);
		buttons.get(Calculator.Keys.SEVEN).addActionListener(sevenListener);
		buttons.get(Calculator.Keys.EIGHT).addActionListener(eightListener);
		buttons.get(Calculator.Keys.NINE).addActionListener(nineListener);
		buttons.get(Calculator.Keys.ADDITION).addActionListener(additionListener);
		buttons.get(Calculator.Keys.SUBTRACTION).addActionListener(subtractListener);
		buttons.get(Calculator.Keys.MULTIPLY).addActionListener(multiplyListener);
		buttons.get(Calculator.Keys.DIVIDE).addActionListener(divideListener);
		buttons.get(Calculator.Keys.PERCENTAGE).addActionListener(percentListener);
		buttons.get(Calculator.Keys.CLEAR_ALL).addActionListener(clearAllListener);
		buttons.get(Calculator.Keys.CLEAR_ENTRY).addActionListener(clearEntryListener);
		buttons.get(Calculator.Keys.BACKSPACE).addActionListener(backspaceListener);
		buttons.get(Calculator.Keys.COLON).addActionListener(colonListener);
		buttons.get(Calculator.Keys.DECIMAL).addActionListener(decimalListener);
		buttons.get(Calculator.Keys.EVALUATE).addActionListener(evaluateListener);
		
		JButton modeButton = this.createCommandButton(gui.getBaseMode().getCaption(), "base", gui.getBaseMode().getTooltip(), font);
		modeButton.addActionListener(this.createBaseModeButton(gui, modeButton));
		
		JPanel calc = new JPanel();
		/*
		 * I gave up trying to do this with GridBagLayout.
		 */
		calc.setLayout(new CalculatorLayoutType1(PANEL_INSETS.left, PANEL_INSETS.top));
		calc.add(display);
		calc.add(buttons.get(Calculator.Keys.SEVEN));
		calc.add(buttons.get(Calculator.Keys.EIGHT));
		calc.add(buttons.get(Calculator.Keys.NINE));
		calc.add(buttons.get(Calculator.Keys.ADDITION));
		calc.add(buttons.get(Calculator.Keys.CLEAR_ALL));
		calc.add(buttons.get(Calculator.Keys.FOUR));
		calc.add(buttons.get(Calculator.Keys.FIVE));
		calc.add(buttons.get(Calculator.Keys.SIX));
		calc.add(buttons.get(Calculator.Keys.SUBTRACTION));
		calc.add(buttons.get(Calculator.Keys.CLEAR_ENTRY));
		calc.add(buttons.get(Calculator.Keys.ONE));
		calc.add(buttons.get(Calculator.Keys.TWO));
		calc.add(buttons.get(Calculator.Keys.THREE));
		calc.add(buttons.get(Calculator.Keys.MULTIPLY));
		calc.add(buttons.get(Calculator.Keys.BACKSPACE));
		calc.add(buttons.get(Calculator.Keys.PERCENTAGE));
		calc.add(buttons.get(Calculator.Keys.ZERO));
		calc.add(buttons.get(Calculator.Keys.DECIMAL));
		calc.add(buttons.get(Calculator.Keys.DIVIDE));
		calc.add(buttons.get(Calculator.Keys.EVALUATE));
		calc.add(modeButton);
		
		basePanel.add(calc, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		
		gui.getRootPane().registerKeyboardAction(zeroListener, Calculator.Keys.ZERO.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_0, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(oneListener, Calculator.Keys.ONE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(twoListener, Calculator.Keys.TWO.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(threeListener, Calculator.Keys.THREE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(fourListener, Calculator.Keys.FOUR.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(fiveListener, Calculator.Keys.FIVE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_5, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(sixListener, Calculator.Keys.SIX.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_6, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(sevenListener, Calculator.Keys.SEVEN.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_7, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(eightListener, Calculator.Keys.EIGHT.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_8, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(nineListener, Calculator.Keys.NINE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_9, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(zeroListener, Calculator.Keys.ZERO.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(oneListener, Calculator.Keys.ONE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(twoListener, Calculator.Keys.TWO.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(threeListener, Calculator.Keys.THREE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(fourListener, Calculator.Keys.FOUR.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(fiveListener, Calculator.Keys.FIVE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(sixListener, Calculator.Keys.SIX.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(sevenListener, Calculator.Keys.SEVEN.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(eightListener, Calculator.Keys.EIGHT.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(nineListener, Calculator.Keys.NINE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		gui.getRootPane().registerKeyboardAction(additionListener, Calculator.Keys.ADDITION.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(subtractListener, Calculator.Keys.SUBTRACTION.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(multiplyListener, Calculator.Keys.MULTIPLY.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(divideListener, Calculator.Keys.DIVIDE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_DIVIDE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(additionListener, Calculator.Keys.ADDITION.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(subtractListener, Calculator.Keys.SUBTRACTION.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(multiplyListener, Calculator.Keys.MULTIPLY.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_ASTERISK, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(multiplyListener, Calculator.Keys.MULTIPLY.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(divideListener, Calculator.Keys.DIVIDE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		gui.getRootPane().registerKeyboardAction(clearAllListener, Calculator.Keys.CLEAR_ALL.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(clearAllListener, Calculator.Keys.CLEAR_ALL.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		gui.getRootPane().registerKeyboardAction(clearEntryListener, Calculator.Keys.CLEAR_ENTRY.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(clearEntryListener, Calculator.Keys.CLEAR_ENTRY.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		gui.getRootPane().registerKeyboardAction(evaluateListener, Calculator.Keys.EVALUATE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(evaluateListener, Calculator.Keys.EVALUATE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		gui.getRootPane().registerKeyboardAction(backspaceListener, Calculator.Keys.BACKSPACE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(backspaceListener, Calculator.Keys.BACKSPACE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_LESS, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		gui.getRootPane().registerKeyboardAction(percentListener, Calculator.Keys.PERCENTAGE.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		gui.getRootPane().registerKeyboardAction(decimalListener, Calculator.Keys.DECIMAL.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_DECIMAL, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		gui.getRootPane().registerKeyboardAction(decimalListener, Calculator.Keys.DECIMAL.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		gui.getRootPane().registerKeyboardAction(colonListener, Calculator.Keys.COLON.getCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_COLON, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		//gui.addWindowListener(this.createGUIWindowListener(generalSettings));
		gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gui.getContentPane().add(basePanel);
		gui.setResizable(true);
		gui.setMinimumSize(new Dimension(250, 400));
		gui.setPreferredSize(new Dimension(250, 400));
		gui.setLocationRelativeTo(null);
		
		return gui;
	}
	
	private JTextField createDisplayField(Font font)
	{
		JTextField field = new JTextField();
		field.setFont(font);
		field.setHorizontalAlignment(JTextField.RIGHT);
		field.setMargin(TEXT_FIELD_INSETS);
		field.setEditable(false);
		return field;
	}
	
 	private JButton createCommandButton(String caption, String command, String tooltip, Font font)
	{
		JButton button = new JButton(caption);
		button.setFont(font);
		if(UIManager.getLookAndFeel().getName().equalsIgnoreCase("nimbus")){
			// found at: https://stackoverflow.com/questions/8764602/how-to-override-nimbus-button-margins-for-a-single-button
			UIDefaults def = new UIDefaults();
			def.put("Button.contentMargins", new Insets(0, 0, 0, 0));
			button.putClientProperty("Nimbus.Overrides", def);
		}else{
			button.setMargin(new Insets(0, 0, 0, 0));
		}
		button.setToolTipText(tooltip);
		button.setActionCommand(command);
		return button;
	}
	
	private ActionListener createCalcButtonListener(Calculator gui, JTextField display, Calculator.Keys key)
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gui.valueEntered(key);
				display.setText(gui.getInput());
				return;
			}
		};
	}
	
	private ActionListener createOperandButtonListener(Calculator gui, Calculator.Keys key)
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gui.valueEntered(key);
				return;
			}
		};
	}
	
	private ActionListener createBaseModeButton(Calculator gui, JButton button)
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				toggleMode(gui, button);
				return;
			}
		};
	}
	
	private ComponentListener createComponentListener(Calculator gui)
	{
		return new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e)
			{
				
				return;
			}
		};
	}
	
	private void toggleMode(Calculator gui, JButton button)
	{
		gui.setBaseMode(gui.getBaseMode().equals(Calculator.Mode.BASE6) ? Calculator.Mode.BASE10 : Calculator.Mode.BASE6);
		button.setText(gui.getBaseMode().getCaption());
		button.setToolTipText(gui.getBaseMode().getTooltip());
		return;
	}
}
