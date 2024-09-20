package it.starkgui.gui.controller;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.starkgui.Detection;
import it.starkgui.common.Theme;


/**
 * Provide to some utilities methods used to visualize a {@code Detection} object.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public final class DetectionPanelDecorator {
	
	/**
	 * Create a new {@code DetectionPanelDecorator} object.
	 * 
	 * @param detection the detection
	 */
	public DetectionPanelDecorator(final Detection detection) {
		this.detection = detection;
	}

	/**
	 * Return the generated panel.
	 * 
	 * @return the generated panel
	 */
	public JPanel getPanel() {
		JPanel panel = new JPanel();
		
		Box verticalBox = Box.createVerticalBox();
		
		for(String parameter : detection.getParameters()) {
			JPanel tmp = new JPanel();
			
			JLabel lblParameter = new JLabel(parameter);
			lblParameter.setForeground(Theme.textColor);
			
			tmp.add(lblParameter);
			
			JLabel lbldetection = new JLabel(detection.get(parameter) + "");
			lbldetection.setForeground(Theme.textColor);
			tmp.add(lbldetection);
			
			verticalBox.add(tmp);
		}
		
		panel.add(verticalBox);
		
		return panel;
	}
	
	private final Detection detection;

}
