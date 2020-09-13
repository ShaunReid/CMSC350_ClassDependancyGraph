/*CMSC350 
 8 Mar 2020
 Shaun Reid
 
 The Main class creates the GUI and receives all data from the GUI*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main extends JFrame implements ActionListener {

	static String fileName = "";
	static Graph buildGraph = new Graph();
	static String className = "";
	
	
	
	//Create two labels
	static JLabel inputFileLbl = new JLabel("Input file name:", JLabel.CENTER);
	static JLabel classToRecompileLbl = new JLabel("Class to recompile:", JLabel.CENTER);
	
	//Create two textfields
	static JTextField inputFileTxt = new JTextField(15);
	static JTextField classToRecompileTxt = new JTextField(15);
			
	//Create two buttons
	static JButton buildDirectedGraphBtn = new JButton("Build Directed Graph");
	static JButton topologicalOrderBtn = new JButton("Topological Order");
			
	//Create text area
	static JTextArea recompilationOrderArea = new JTextArea();
	
	
	
	
	public static void main(String[] args) {
		
		buildGUI();
	}
	
	//Build GUI
	public static void buildGUI() {
		
		//Create file panel
		JPanel filePanel = new JPanel(new FlowLayout());
		filePanel.add(inputFileLbl);
		filePanel.add(classToRecompileLbl);
		
		
		//Create class panel
		JPanel classPanel = new JPanel(new FlowLayout());
		classPanel.add(inputFileTxt);
		classPanel.add(classToRecompileTxt);
		
		
		//Create button panel
		JPanel btnPanel = new JPanel(new FlowLayout());
		btnPanel.add(buildDirectedGraphBtn);
		btnPanel.add(topologicalOrderBtn);
		
		//Create panel that holds file and class panel
		JPanel inputPanel = new JPanel(new GridLayout(1, 3));
		inputPanel.setPreferredSize(new Dimension(530, 90));
		inputPanel.setBorder(BorderFactory.createTitledBorder(" "));
		inputPanel.add(filePanel);
		inputPanel.add(classPanel);
		inputPanel.add(btnPanel);
		
		//Create text area panel
		JPanel textAreaPanel = new JPanel();
		textAreaPanel.setPreferredSize(new Dimension(530, 225));
		textAreaPanel.setBorder(BorderFactory.createTitledBorder("Recompilation Order"));
		textAreaPanel.setBackground(Color.white);
		textAreaPanel.add(recompilationOrderArea);
		
		
		
		//Create frame
		final int WIDTH = 560;
		final int HEIGHT = 280;
		
		JFrame genFrame = new JFrame();
		genFrame.setTitle("Class Dependency Graph");
		genFrame.setSize(WIDTH, HEIGHT);
		genFrame.setLocationRelativeTo(null);
		genFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		genFrame.setVisible(true);
		genFrame.setForeground(Color.lightGray);
		
		genFrame.setLayout(new FlowLayout());
		genFrame.add(inputPanel);
		genFrame.add(textAreaPanel);
		
		buildDirectedGraphBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String resultFile = "./Input file/" + getFileName();
				
				try {
					
					File file = new File(resultFile);
					Scanner sc = new Scanner(file);
					
					while (sc.hasNextLine()) {
						
						buildGraph.initializeGraph(sc.nextLine());
						
					}
					
					JOptionPane graphBuilt = new JOptionPane("Graph Built Successfully", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = graphBuilt.createDialog("Message");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				} catch(FileNotFoundException a) {
					System.out.println("File not found");
					JOptionPane fileDidNotOpen = new JOptionPane("File Did Not Open", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = fileDidNotOpen.createDialog("Message");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}
				
				
			}
		});
		
		topologicalOrderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					
					if(buildGraph.classNameCheck(getClassName()) == false) {
						throw new InvalidClassNameException("");
					} else {
					Integer vertex = new Integer(buildGraph.getVertex(getClassName()));
					buildGraph.depthFirstSearch(vertex);
									
					recompilationOrderArea.setText(buildGraph.getRecompileOrder());
					}
				} catch (InvalidClassNameException b) {
					
					JOptionPane invalidClassName = new JOptionPane("Invalid Class Name", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = invalidClassName.createDialog("Message");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				} catch (CycleFoundException c) {
					
					JOptionPane cycleFound = new JOptionPane("Cycle Was Found", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = cycleFound.createDialog("Message");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}
				
			}
		});
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
	
	//Get the file name
	public static String getFileName() {
		fileName = inputFileTxt.getText();
		return fileName;
	}
	
	//Gets the class from the textfield
	public static String getClassName() {
		className = classToRecompileTxt.getText();
		return className;
	}

}
