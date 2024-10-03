package it.starkgui.gui.view;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.starkgui.Detection;
import it.starkgui.common.Theme;

/**
 * View class for {@code Detection} objects.
 *
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.0
 * @since JDK 17
 */
public final class DetectionView {
	/**
	 * Create a new {@code DetectionPanelDecorator} object.
	 * 
	 * @param detection the detection
	 */
	public DetectionView(final Detection detection) {
		this.detection = detection;
		initialize();
	}
	
	/**
	 * Initialize the current object.
	 */
	private void initialize() {
		panel = new JPanel();
		
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
	}

	/**
	 * Return the generated component.
	 * 
	 * @return the generated component
	 */
	public Component getComponent() {
		return panel;
	}
	
	/**
	 * Update the current panel.
	 */
	public void update() {
		this.panel.revalidate();
	}
	
	private JPanel panel;
	private final Detection detection;
}
