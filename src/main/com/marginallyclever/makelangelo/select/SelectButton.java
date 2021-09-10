package com.marginallyclever.makelangelo.select;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

/**
 * A button that does nothing until you attach an observer.
 * @author Dan Royer
 * @since 7.24.0
 */
public class SelectButton extends Select {
	private JButton button;

	public SelectButton(AbstractAction action) {
		super();
		button = new JButton(action);
	}
	
	public SelectButton(String labelText) {
		super();
		
		button = new JButton(labelText);
		final Select parent = this;
		button.addActionListener((e) -> {
			notifyPropertyChangeListeners(new PropertyChangeEvent(parent,"click",null,null));
		});

		panel.add(button,BorderLayout.CENTER);
	}
	
	public void doClick() {
		button.doClick();
	}
	
	public void setText(String label) {
		button.setText(label);
	}
	
	public void setEnabled(boolean b) {
		button.setEnabled(b);
	}

	public void setForeground(Color fg) {
		button.setForeground(fg);
	}
}
