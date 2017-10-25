package com.jensen.View;

import java.awt.GridLayout;

import javax.swing.*;
/**
 * 
 * This Class makes sure that application runs
 * 
 * @author Kami Hassanzadeh
 * @author Gustav Malm
 *
 */
/* Class MainWindow */
public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = -535055836621390505L;
	/**
	 * Creates a simple Window which acts as a Main Frame for the Application. 
	 * Takes a String as a Parameter
	 * 
	 * @param title Used to set this MainWindow Object's Title
	 */
	public MainWindow(String title) {
		this.setTitle(title);
		init();
	}
	
	private void init() {
		initDefaultGUI();
	}
	
	private void initDefaultGUI() {
		this.setLayout(new GridLayout(2, 0));
	}
	/**
	 * This Method is being used to add two JPanels to the MainWindow Object
	 * 
	 * @param upperPanel Used to add a JPanel to the upper Side of a MainPanel Object [Grid]
	 * @param lowerPanel Used to add a JPanel to the lower Side of a MainPanel Object [Grid]
	 */
	public void addPanel(JPanel panel, JPanel panel1) {
		this.add(panel);
		this.add(panel1);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.pack();
		//this.setLocationRelativeTo(null);
	}
	/**
	 * This Method is being called whenever the Application needs to Update the MainWindow Object
	 */
	public void update() {
		
	}
	/**
	 * This Method is being used whenever the Application needs to close and 
	 * dispose the MainWindow Object [Usually when terminating the Application]
	 */
	public void close() {
		this.setVisible(false); 
		this.dispose();	
	}
	
}
