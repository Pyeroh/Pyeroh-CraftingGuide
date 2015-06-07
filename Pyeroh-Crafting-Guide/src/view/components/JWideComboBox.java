package view.components;

import java.awt.Dimension;

import javax.swing.JComboBox;

public class JWideComboBox<E> extends JComboBox<E> {

	private static final long serialVersionUID = 6402589310783400089L;
	private boolean layingOut = false; 

	public void doLayout(){ 
		try{ 
			layingOut = true; 
			super.doLayout(); 
		}finally{ 
			layingOut = false; 
		} 
	} 

	public Dimension getSize(){ 
		Dimension dim = super.getSize(); 
		if(!layingOut) 
			dim.width = Math.max(dim.width, getPreferredSize().width); 
		return dim; 
	} 
}
