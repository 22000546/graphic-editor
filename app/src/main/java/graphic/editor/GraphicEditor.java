package graphic.editor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import graphic.editor.tools.*;
import graphic.editor.tools.Rectangle;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

public class GraphicEditor extends JFrame implements MouseListener, MouseMotionListener {

	/*
	 * Basic structure
	 */
	private JFrame frame;
	MyCanvas canvas;
	
	/*
	 * Pop-up frame
	 */
	ShapeFrame subShape;
	LineFrame subLine;
	StrokeFrame subStroke;
	
	/*
	 * Mode
	 */
	static int MODE = 0;
	static final int PEN = 1;
	static final int RECT = 2;
	static final int OVAL = 3;
	static final int LINE = 4;
	static final int POLYLINE = 5;
	static final int TEXT = 6;
	static final int PATTERNS = 7;
	static final int SPRAY = 8;
	static final int ERASER = 9;
	
	/*
	 * Settings
	 */
	static Color color = Color.BLACK;
	static int stroke = 5;
	static boolean fill = false;
	
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
		
		
		canvas = new MyCanvas();
		canvas.setBounds(0, 0, 1060, 650);
		canvas.setBackground(Color.WHITE);
		frame.getContentPane().add(canvas);	
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
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
		
		JMenuItem menuFileExit = new JMenuItem("Exit");
		menuFile.add(menuFileExit);
		
		// Edit Menu
		
		JMenuItem menuEditUndo = new JMenuItem("Undo");
		menuEdit.add(menuEditUndo);
		
		JMenuItem menuEditRedo = new JMenuItem("Redo");
		menuEdit.add(menuEditRedo);
		
		JMenuItem menuEditCopy = new JMenuItem("Copy");
		menuEdit.add(menuEditCopy);
		
		JMenuItem menuEditCut = new JMenuItem("Cut");
		menuEdit.add(menuEditCut);
		
		JMenuItem menuEditPaste = new JMenuItem("Paste");
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
				MODE = PEN;
			}
		});
		
		JButton toolShape = new JButton("");
		toolShape.setBounds(1070, 70, 117, 40);
		toolShape.setIcon(new ImageIcon("./images/shape24.png"));
		frame.getContentPane().add(toolShape);
		toolShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(subShape == null)
					subShape = new ShapeFrame();
				else {
					subShape.dispose();
					subShape = new ShapeFrame();
				}
			}
		});
		
		JButton toolLine = new JButton("");
		toolLine.setBounds(1070, 120, 117, 40);
		toolLine.setIcon(new ImageIcon("./images/line24.png"));
		frame.getContentPane().add(toolLine);
		toolLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subLine = new LineFrame();
			}
		});
		
		JButton toolEraser = new JButton("");
		toolEraser.setBounds(1070, 170, 117, 40);
		toolEraser.setIcon(new ImageIcon("./images/eraser24.png"));
		frame.getContentPane().add(toolEraser);
		toolEraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MODE = 9;
			}
		});
		
		JButton toolText = new JButton("");
		toolText.setBounds(1070, 220, 117, 40);
		toolText.setIcon(new ImageIcon("./images/text24.png"));
		frame.getContentPane().add(toolText);
		toolText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MODE = 6;
			}
		});
		
		JButton toolPattern = new JButton("");
		toolPattern.setBounds(1070, 270, 117, 40);
		toolPattern.setIcon(new ImageIcon("./images/pattern24.png"));
		frame.getContentPane().add(toolPattern);
		toolPattern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MODE = 7;
			}
		});
		
		JButton toolSpray = new JButton("");
		toolSpray.setBounds(1070, 320, 117, 40);
		toolSpray.setIcon(new ImageIcon("./images/spray24.png"));
		frame.getContentPane().add(toolSpray);
		toolSpray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MODE = 8;
			}
		});
		
		/**
		 * Tool Setting Section
		 */
		
		JButton selectStroke = new JButton("굵기");
		selectStroke.setBounds(1070, 450, 117, 40);
		frame.getContentPane().add(selectStroke);
		selectStroke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subStroke = new StrokeFrame();
			}
		});
		
		JButton selectColor = new JButton("색상");
		selectColor.setBounds(1070, 500, 117, 40);
		selectColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = JColorChooser.showDialog(null, "Color Menu", Color.black);
			}
		});
		frame.getContentPane().add(selectColor);
		
		JButton selectStyle = new JButton("스타일");
		selectStyle.setBounds(1070, 550, 117, 40);
		frame.getContentPane().add(selectStyle);
		
		JButton selectFill = new JButton("");
		selectFill.setBounds(1070, 600, 117, 40);
		selectFill.setIcon(new ImageIcon("./images/unfill24.png"));
		selectFill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fill = !fill;
				if(fill == false)
					selectFill.setIcon(new ImageIcon("./images/unfill24.png"));
				else
					selectFill.setIcon(new ImageIcon("./images/fill24.png"));
			}
		});
		frame.getContentPane().add(selectFill);
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		canvas.current = e.getPoint();
		canvas.end = e.getPoint();
		if(MODE == PEN || MODE == POLYLINE || MODE == ERASER || MODE == SPRAY)
			canvas.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//canvas.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		canvas.start = e.getPoint();
		canvas.current = e.getPoint();
		if(MODE == SPRAY)
			canvas.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		canvas.end = e.getPoint() ; 
		if(MODE == RECT || MODE == OVAL || MODE == LINE)
			canvas.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseDoubleClicked(MouseEvent e) {
		
	}
	
	class ShapeFrame extends JFrame {
		
		Container contentPane;
		JLabel menuLabel;
		
		public ShapeFrame() {
			setBounds(1070, 200, 100, 80);
			
			contentPane = getContentPane();
			menuLabel = new JLabel();
			contentPane.add(menuLabel);
			contentPane.add(new MenuPanel());
			
			setVisible(true);
			
		}

		class MenuPanel extends JPanel  {
			public MenuPanel() {
				JButton rect = new JButton("");
				JButton oval = new JButton("");
				rect.setIcon(new ImageIcon("./images/square24.png"));
				oval.setIcon(new ImageIcon("./images/circle24.png"));
				add(rect);
				add(oval);
				rect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GraphicEditor.MODE = GraphicEditor.RECT;
						dispose();
					}
				});
				oval.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GraphicEditor.MODE = GraphicEditor.OVAL;
						dispose();
					}
				});				
			}
		}
		
	}
	
	class LineFrame extends JFrame {
		
		Container contentPane;
		JLabel menuLabel;
		
		public LineFrame() {
			setBounds(1070, 240, 100, 80);
			
			contentPane = getContentPane();
			menuLabel = new JLabel();
			contentPane.add(menuLabel);
			contentPane.add(new MenuPanel());
			
			setVisible(true);
			
		}

		class MenuPanel extends JPanel  {
			public MenuPanel() {
				JButton rect = new JButton("");
				JButton oval = new JButton("");
				rect.setIcon(new ImageIcon("./images/line24.png"));
				oval.setIcon(new ImageIcon("./images/polyline24.png"));
				add(rect);
				add(oval);
				rect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GraphicEditor.MODE = GraphicEditor.LINE;
						dispose();
					}
				});
				oval.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GraphicEditor.MODE = GraphicEditor.POLYLINE;
						dispose();
					}
				});				
			}
		}
	}
	
	
	class StrokeFrame extends JFrame {
		
		Container contentPane;
		JLabel menuLabel;
		
		public StrokeFrame() {
			setBounds(970, 470, 200, 100);
			
			JSlider slider = new JSlider(0, 25, GraphicEditor.stroke);
			JLabel label = new JLabel();
			slider.setMajorTickSpacing(5);
			slider.setMinorTickSpacing(1);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			
			contentPane = getContentPane();			
			contentPane.add(label);
			contentPane.add(slider);
			slider.addChangeListener(new ChangeListener() {
	            public void stateChanged(ChangeEvent e) {
	                 GraphicEditor.stroke = slider.getValue();
	            }
	        });
			
			setVisible(true);
			
		}
		
	}
	
}

class MyCanvas extends Canvas {
	Point start;
	Point end; 
	Point current;
			
	 public void paint(Graphics g) {
		 
		 g.setColor(GraphicEditor.color);
		 
		 Graphics2D g2d = (Graphics2D) g;
		 g2d.setStroke(new BasicStroke(GraphicEditor.stroke));
		 
		 switch(GraphicEditor.MODE) {
		 case GraphicEditor.PEN: 
			 Line.drawLine(g2d, start, end);
			 start = end;
			 break;
		 case GraphicEditor.RECT:
			 Rectangle.drawRectangle(g2d, start, end, GraphicEditor.fill);
			 break;
		 case GraphicEditor.OVAL:
			 Circle.drawCircle(g2d, start, end, GraphicEditor.fill);
			 break;
		 case GraphicEditor.ERASER:
			 Eraser.erase(g2d, current);
			 break;
		 case GraphicEditor.LINE:
			 Line.drawLine(g2d, start, end);
			 break;
		 case GraphicEditor.POLYLINE:
			 //Line.drawPolyline(g2d, start, current);
			 //Line.drawPolyline(g2d, end, end);
			 break;
		 case GraphicEditor.SPRAY:
			 Spray.spray(g2d, current);
			 break;
		 
		 }
			 
		 
		  
	 }
	 
	 @Override		 
	 public void update(Graphics g){
		 paint(g);
	}	


}