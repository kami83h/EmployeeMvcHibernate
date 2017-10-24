package com.jensen.Controller.managers;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import com.jensen.View.MainWindow;
import com.jensen.View.panels.MainPanel;
import com.jensen.View.panels.TableView;
/**
 * 
 * This Class makes sure that application runs
 * 
 * @author Kami Hassanzadeh
 * @author Gustav Malm
 *
 */
public class ViewManager {

	private MainWindow window;
	private MainPanel panel;
	private TableView tableView;
	ArrayList<JButton> buttonLeft;
	ArrayList<JButton> buttonRight;

	public ViewManager() {
		init();
	}

	private void init() {
		
		this.window = new MainWindow("Employee Register");
		this.panel = new MainPanel();
		this.tableView = new TableView();

	
		setButtonLeft(panel.getButtonLeft());
		setButtonRight(panel.getButtonRight());
	}
	public DefaultTableModel getTableModel(){
		return this.tableView.getModel();
	}

	public void initDefaultGUI() {
		this.panel.addComponents();
		this.window.addPanel(panel.getPanel(),tableView.getPanel());
	}

	public MainWindow getWindow() {
		return window;
	}


	public MainPanel getPanel() {
		return this.panel;
	}

	public void setPanel(MainPanel panel) {
		this.panel = panel;
	}
	
	public ArrayList<JButton> getButtonLeft() {
		return buttonLeft;
	}

	public void setButtonLeft(ArrayList<JButton> arrayList) {
		this.buttonLeft = arrayList;
	}

	public ArrayList<JButton> getButtonRight() {
		return buttonRight;
	}

	public void setButtonRight(ArrayList<JButton> arrayList) {
		this.buttonRight = arrayList;
	}
	
	public String[] getMenyButtonLeft() {
		return panel.getMenyButtonLeft();
	}

	public String[] getMenyButtonRight() {
		return panel.getMenyButtonRight();
	}
}
