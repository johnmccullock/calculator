package main;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class CalculatorLayoutType1 implements LayoutManager
{
	private int mMinWidth = 0;
	private int mMinHeight = 0;
	private int mPreferredWidth = 0;
	private int mPreferredHeight = 0;
	private boolean mSizeUnknown = true;
	private int mVSpace = 0;
	private int mHSpace = 0;
	
	public CalculatorLayoutType1() { return; }
	
	public CalculatorLayoutType1(int hspace, int vspace)
	{
		this.setHorizontalSpacing(hspace);
		this.setVerticalSpacing(vspace);
		return;
	}
	
	public void addLayoutComponent(String name, Component comp) { return; }
	public void removeLayoutComponent(Component comp) { return; }
	
	public void setSizes(Container parent)
	{
		this.mPreferredWidth = 0;
		this.mPreferredHeight = 0;
		this.mMinWidth = 0;
		this.mMinHeight = 0;
		
		if(parent.getComponent(0).isVisible()){
			this.mPreferredWidth += parent.getComponent(0).getPreferredSize().width;
		}
		
		if(parent.getComponent(0).isVisible()){
			this.mPreferredHeight += parent.getComponent(0).getPreferredSize().height;
		}
		if(parent.getComponent(1).isVisible()){
			this.mPreferredHeight += parent.getComponent(1).getPreferredSize().height;
		}
		if(parent.getComponent(6).isVisible()){
			this.mPreferredHeight += parent.getComponent(6).getPreferredSize().height;
		}
		if(parent.getComponent(11).isVisible()){
			this.mPreferredHeight += parent.getComponent(11).getPreferredSize().height;
		}
		if(parent.getComponent(16).isVisible()){
			this.mPreferredHeight += parent.getComponent(16).getPreferredSize().height;
		}
		if(parent.getComponent(21).isVisible()){
			this.mPreferredHeight += parent.getComponent(21).getPreferredSize().height;
		}
		this.mPreferredHeight += (this.mVSpace * 5);
		
		this.mMinWidth = this.mPreferredWidth;
		this.mMinHeight = this.mPreferredHeight;
		return;
	}
	
	public Dimension minimumLayoutSize(Container parent)
	{
		Dimension result = new Dimension(0, 0);
		result.width = this.mMinWidth + parent.getInsets().left + parent.getInsets().right;
		result.height = this.mMinHeight + parent.getInsets().top + parent.getInsets().bottom;
		this.mSizeUnknown = false;
		return result;
	}
	
	public Dimension preferredLayoutSize(Container parent)
	{
		Dimension result = new Dimension(0, 0);
		
		this.setSizes(parent);
		
		result.width = this.mPreferredWidth + parent.getInsets().left + parent.getInsets().right;
		result.height = this.mPreferredHeight + parent.getInsets().top + parent.getInsets().bottom;
		this.mSizeUnknown = false;
		return result;
	}
	
	public void layoutContainer(Container parent)
	{
		if(this.mSizeUnknown){
			this.setSizes(parent);
		}
		
		int twentyPercentWidth = (int)Math.ceil(parent.getWidth() * 0.2);
		int oneSeventhHeight = (int)Math.ceil(parent.getHeight()  * 0.14285);
		int count = parent.getComponentCount();
		int k = 0;
		
		parent.getComponent(k).setBounds(0, 0, (twentyPercentWidth * 5) - this.mHSpace, oneSeventhHeight); // The display.
		
		k++;
		for(int j = 1; j < 5; j++)
		{
			for(int i = 0; i < 5; i++)
			{
				if(k == count){
					return;
				}
				Component c = parent.getComponent(k);
				if(!c.isVisible()){
					continue;
				}
				c.setBounds((twentyPercentWidth * i), 
							(oneSeventhHeight * j) + this.mVSpace, 
							twentyPercentWidth - this.mHSpace, 
							oneSeventhHeight - this.mVSpace);
				k++;
			}
		}
		
		if(k >= count){
			return;
		}
		parent.getComponent(k).setBounds(0, (oneSeventhHeight * 5) + this.mVSpace, (twentyPercentWidth * 5) - this.mHSpace, oneSeventhHeight); // number base button.
		return;
	}
	
	public void setHorizontalSpacing(int space)
	{
		this.mHSpace = space;
		return;
	}
	
	public void setVerticalSpacing(int space)
	{
		this.mVSpace = space;
		return;
	}
}
