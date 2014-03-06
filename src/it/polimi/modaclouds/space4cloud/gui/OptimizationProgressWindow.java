package it.polimi.modaclouds.space4cloud.gui;

import it.polimi.modaclouds.space4clouds.chart.Logger2JFreeChartImage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

public class OptimizationProgressWindow implements PropertyChangeListener{

	private JFrame frmOptimizationProgress;

	JProgressBar progressBar;
	private JPanel upperPanel;
	private JPanel lowerPanel;

	private JPanel vmPanel;	
	private JLabel vmLabel;
	private JPanel costPanel;
	private JLabel costLabel;
	private JPanel constraintPanel;
	private JLabel constraintLabel;


	private Logger2JFreeChartImage costLogger;

	private Logger2JFreeChartImage vmLogger;

	private Logger2JFreeChartImage constraintsLogger;




	/**
	 * Create the application.
	 */
	public OptimizationProgressWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmOptimizationProgress =  new JFrame();
		frmOptimizationProgress.setTitle("Optimization Progress");
		frmOptimizationProgress.setBounds(100, 100, 450, 300);
		frmOptimizationProgress.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOptimizationProgress.getContentPane().setLayout(new BorderLayout(0, 0));

		//Upper panel (for progress bar)
		upperPanel = new JPanel();
		frmOptimizationProgress.getContentPane().add(upperPanel, BorderLayout.NORTH);
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));

		//progress bar
		progressBar = new JProgressBar(0, 100);
		upperPanel.add(progressBar);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);


		//Lower panel (for images)
		lowerPanel = new JPanel();
		frmOptimizationProgress.getContentPane().add(lowerPanel, BorderLayout.CENTER);
		lowerPanel.setLayout(new GridLayout(0, 3, 0, 0));

		//vmPanel for VM image
		vmPanel = new JPanel();
		vmPanel.setBorder(new TitledBorder(null, "Number of VMs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lowerPanel.add(vmPanel);

		//vmLabel for VM image
		vmLabel = new JLabel();
		vmLabel.setIcon(null);
		vmPanel.add(vmLabel);

		//Cost panel for cost image
		costPanel = new JPanel();		
		costPanel.setBorder(new TitledBorder(null, "Cost", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lowerPanel.add(costPanel);

		//Cost label for cost image
		costLabel = new JLabel();
		costLabel.setIcon(null);
		costPanel.add(costLabel);

		//Constraint panel for constraint image
		constraintPanel = new JPanel();		
		constraintPanel.setBorder(new TitledBorder(null, "ViolatedConstraints", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lowerPanel.add(constraintPanel);

		//Constraint label for constraint image
		constraintLabel = new JLabel();
		constraintLabel .setIcon(null);
		constraintPanel.add(constraintLabel );

		//listener to resize images
		frmOptimizationProgress.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void componentResized(ComponentEvent e) {
				updateImages();				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub				
			}
		});

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {		
		if (evt.getPropertyName().equals("progress")) {
			updateProgressBar((int) evt.getNewValue());
			updateImages();
		} else
		//TODO: add evaluation to the list of bound properties and add this class as listener to the evaluation server
		if (evt.getPropertyName().equals("totalNumberOfEvaluations")) {            
			updateImages();
		} else{
			System.out.println("property: "+evt.getPropertyName());
		}
		
	}

	public void setMax(int max) {
		progressBar.setMaximum(max);    	
		frmOptimizationProgress.setVisible(true);
	}

	public void setCostLogger(Logger2JFreeChartImage costLogger) {
		this.costLogger = costLogger;
	}

	public void setVMLogger(Logger2JFreeChartImage vmLogger) {
		this.vmLogger = vmLogger;

	}

	public void setConstraintsLogger(Logger2JFreeChartImage constraintsLogger) {
		this.constraintsLogger = constraintsLogger;
	}

	private void updateProgressBar(int progress){
		progressBar.setValue(progress);            
	}

	private void updateImages(){
		if(costLogger != null ){
			ImageIcon icon;
			try{
				icon = new ImageIcon(costLogger.save2buffer(costPanel.getSize())); 
			}catch (NullPointerException e){
				icon = new ImageIcon();
			}
			costLabel.setIcon(icon);            
			costLabel.setVisible(true);
			costPanel.setPreferredSize(costLabel.getPreferredSize());
		}

		if(vmLogger != null ){
			ImageIcon icon;
			try{
				icon = new ImageIcon(vmLogger.save2buffer(vmPanel.getSize())); 
			}catch (NullPointerException e){
				icon = new ImageIcon();
			}
			vmLabel.setIcon(icon);
			vmLabel.setVisible(true);
			vmPanel.setPreferredSize(vmLabel.getPreferredSize());
		}

		if(constraintsLogger != null ){
			ImageIcon icon;
			try{
				icon = new ImageIcon(constraintsLogger.save2buffer(constraintPanel.getSize())); 
			}catch (NullPointerException e){
				icon = new ImageIcon();
			}
			constraintLabel.setIcon(icon);
			constraintLabel.setVisible(true);
			constraintPanel.setPreferredSize(constraintLabel.getPreferredSize());
		}
	}

}
