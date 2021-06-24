package graphic.editor;

import javax.swing.*;
import graphic.editor.tools.*;
import java.awt.*;
import java.awt.event.*;

public class GraphicEditor {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicEditor window = new GraphicEditor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GraphicEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/*
		Canvas canvas = new Canvas();
		canvas.setBounds(0, 0, 1060, 650);
		canvas.setBackground(Color.WHITE);
		frame.getContentPane().add(canvas);	
		
		
		/*
		 * Menu Bar
		 * 왼쪽 상단 메뉴바 만들기 
		 * 기본 설정 : File / Edit
		 * File : Save, Load, Print, Exit
		 * Edit : Undo, Redo, Copy, Paste, Cut
		 */
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		JMenu menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);
		
		// File Menu
		
		JMenu menuFileSave = new JMenu("Save");
		menuFile.add(menuFileSave);
		
		JMenu menuFileLoad = new JMenu("Load");
		menuFile.add(menuFileLoad);
		
		JMenu menuFilePrint = new JMenu("Print");
		menuFile.add(menuFilePrint);
		
		JMenu menuFileExit = new JMenu("Exit");
		menuFile.add(menuFileExit);
		
		// Edit Menu
		
		JMenu menuEditUndo = new JMenu("Undo");
		menuEdit.add(menuEditUndo);
		
		JMenu menuEditRedo = new JMenu("Redo");
		menuEdit.add(menuEditRedo);
		
		JMenu menuEditCopy = new JMenu("Copy");
		menuEdit.add(menuEditCopy);
		
		JMenu menuEditCut = new JMenu("Cut");
		menuEdit.add(menuEditCut);
		
		JMenu menuEditPaste = new JMenu("Paste");
		menuEdit.add(menuEditPaste);
		
		
		
		/**
		 * Tool Bar
		 */
		
		JButton toolPen = new JButton("");
		toolPen.setBounds(1070, 20, 117, 40);
		toolPen.setIcon(new ImageIcon("./images/pen24.png"));
		frame.getContentPane().add(toolPen);
		toolPen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton toolShape = new JButton("");
		toolShape.setBounds(1070, 70, 117, 40);
		toolShape.setBackground(Color.DARK_GRAY);
		toolShape.setIcon(new ImageIcon("./images/shape24.png"));
		frame.getContentPane().add(toolShape);
		
		JButton toolLine = new JButton("");
		toolLine.setBounds(1070, 120, 117, 40);
		toolLine.setBackground(Color.DARK_GRAY);
		toolLine.setIcon(new ImageIcon("./images/line24.png"));
		frame.getContentPane().add(toolLine);
		
		JButton toolEraser = new JButton("");
		toolEraser.setBounds(1070, 170, 117, 40);
		toolEraser.setBackground(Color.DARK_GRAY);
		toolEraser.setIcon(new ImageIcon("./images/eraser24.png"));
		frame.getContentPane().add(toolEraser);
		
		JButton toolText = new JButton("");
		toolText.setBounds(1070, 220, 117, 40);
		toolText.setBackground(Color.DARK_GRAY);
		toolText.setIcon(new ImageIcon("./images/text24.png"));
		frame.getContentPane().add(toolText);
		
		JButton toolPattern = new JButton("");
		toolPattern.setBounds(1070, 270, 117, 40);
		toolPattern.setBackground(Color.DARK_GRAY);
		toolPattern.setIcon(new ImageIcon("./images/pattern24.png"));
		frame.getContentPane().add(toolPattern);
		
		Canvas canvasToolBar = new Canvas();
		canvasToolBar.setBackground(Color.DARK_GRAY);
		canvasToolBar.setBounds(1066, 10, 124, 320);
		frame.getContentPane().add(canvasToolBar);
		
		/**
		 * Tool Setting Section
		 */
		
		JButton selectStroke = new JButton("굵기");
		selectStroke.setBounds(1070, 350, 117, 40);
		frame.getContentPane().add(selectStroke);
		
		JButton selectColor = new JButton("색상");
		selectColor.setBounds(1070, 400, 117, 40);
		selectColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(null, "Color Menu", Color.black);
			}
		});
		frame.getContentPane().add(selectColor);
		
		JButton selectStyle = new JButton("스타일");
		selectStyle.setBounds(1070, 450, 117, 40);
		frame.getContentPane().add(selectStyle);
		
		Canvas canvasToolSettingSection = new Canvas();
		canvasToolSettingSection.setBackground(Color.DARK_GRAY);
		canvasToolSettingSection.setBounds(1066, 340, 124, 300);
		frame.getContentPane().add(canvasToolSettingSection);

		
	}
}
