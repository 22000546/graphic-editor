package graphic.editor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JToolBar;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JButton;

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
		
		JButton toolPen = new JButton("펜");
		toolPen.setBounds(1070, 20, 117, 40);
		toolPen.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(toolPen);
		
		JButton toolShape = new JButton("도형");
		toolShape.setBounds(1070, 70, 117, 40);
		toolShape.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(toolShape);
		
		JButton toolLine = new JButton("선");
		toolLine.setBounds(1070, 120, 117, 40);
		toolLine.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(toolLine);
		
		JButton toolEraser = new JButton("지우개");
		toolEraser.setBounds(1070, 170, 117, 40);
		toolEraser.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(toolEraser);
		
		JButton toolText = new JButton("텍스트");
		toolText.setBounds(1070, 220, 117, 40);
		toolText.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(toolText);
		
		JButton toolPattern = new JButton("패턴");
		toolPattern.setBounds(1070, 270, 117, 40);
		toolPattern.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(toolPattern);
		
		Canvas canvasToolBar = new Canvas();
		canvasToolBar.setBackground(Color.DARK_GRAY);
		canvasToolBar.setBounds(1066, 10, 124, 320);
		frame.getContentPane().add(canvasToolBar);
		
		/**
		 * Tool Setting Section
		 */
		
		Canvas canvasToolSettingSection = new Canvas();
		canvasToolSettingSection.setBackground(Color.DARK_GRAY);
		canvasToolSettingSection.setBounds(1066, 340, 124, 300);
		frame.getContentPane().add(canvasToolSettingSection);

		
	}
}
