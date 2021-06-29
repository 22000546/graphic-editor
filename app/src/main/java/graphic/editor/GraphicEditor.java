package graphic.editor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import graphic.editor.tools.*;
import graphic.editor.tools.Rectangle;
import graphic.editor.system.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.awt.geom.AffineTransform;

import java.util.ArrayList;


public class GraphicEditor extends JFrame implements MouseListener, MouseMotionListener, Printable {

	/*
	 * Basic structure
	 */
	private JFrame frame;
	Canvas canvas;
	Graphics2D g;
	
	/*
	 * Pop-up frame
	 */
	ShapeFrame subShape;
	LineFrame subLine;
	StrokeFrame subStroke;
	
	/*
	 * Memory
	 */
	
	public static ArrayList<GraphicData> works = new ArrayList<>();
	public static ArrayList<GraphicData> redo = new ArrayList<>();
	public static ArrayList<Integer> penStartIndex = new ArrayList<>();
	public static ArrayList<Integer> penEndIndex = new ArrayList<>();
	public static ArrayList<Integer> penStartRedo = new ArrayList<>();
	public static ArrayList<Integer> penEndRedo = new ArrayList<>();
	public static ArrayList<Integer> eraserStartIndex = new ArrayList<>();
	public static ArrayList<Integer> eraserEndIndex = new ArrayList<>();
	public static ArrayList<Integer> eraserStartRedo = new ArrayList<>();
	public static ArrayList<Integer> eraserEndRedo = new ArrayList<>();
	
	/*
	 * Point
	 */
	Point start; Point end; Point current;
	Point backgroundStart = new Point(0, 25);
	Point backgroundEnd = new Point(1060, 650);
	
	/*
	 * Mode
	 */
	int MODE = 0;
	static final int PEN = 1;
	static final int RECT = 2;
	static final int OVAL = 3;
	static final int LINE = 4;
	static final int POLYLINE = 5;
	static final int TEXT = 6;
	static final int PATTERNS = 7;
	static final int SPRAY = 8;
	static final int ERASER = 9;
	static final int PENDRAGGED = 10;
	
	/*
	 * Settings
	 */
	Color color = Color.BLACK;
	int stroke = 5;
	boolean fill = false;
	
	/*
	 * Subframe classes
	 */
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
						MODE = GraphicEditor.RECT;
						dispose();
					}
				});
				oval.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MODE = GraphicEditor.OVAL;
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
						MODE = GraphicEditor.LINE;
						dispose();
					}
				});
				oval.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MODE = GraphicEditor.POLYLINE;
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
			
			JSlider slider = new JSlider(0, 25, stroke);
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
	                 stroke = slider.getValue();
	            }
	        });
			
			setVisible(true);
			
		}
		
	}
	
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
		frame.setVisible(true);
		
		
		canvas = new Canvas();
		canvas.setBounds(0, 0, 1060, 650);
		canvas.setBackground(Color.WHITE);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		frame.getContentPane().add(canvas);	
		g = (Graphics2D) canvas.getGraphics();
		
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
		
		JMenuItem menuFilePrint = new JMenuItem("Print");
		menuFile.add(menuFilePrint);
		menuFilePrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrinterJob job = PrinterJob.getPrinterJob();
				job.setPrintable(null);
				boolean ok = job.printDialog();
				if(ok) {
					try {
						job.print();
					} catch (PrinterException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		JMenuItem menuFileExit = new JMenuItem("Exit");
		menuFile.add(menuFileExit);
		menuFileExit.setAccelerator(KeyStroke.getKeyStroke('q' ,KeyEvent.VK_CONTROL));
		menuFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// Edit Menu
		
		JMenuItem menuEditUndo = new JMenuItem("Undo");
		menuEdit.add(menuEditUndo);
		menuEditUndo.setAccelerator(KeyStroke.getKeyStroke('Z' ,KeyEvent.VK_CONTROL));
		menuEditUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!works.isEmpty()) {
					if(works.get(works.size()-1).getMode() == PEN) {
						penStartRedo.add(redo.size());
						UndoRedoAction.undoPen();
						penEndRedo.add(redo.size()-1);
						g.setColor(Color.WHITE);
						g.fillRect(0, 25, 1060, 650);
						paint();
					} else if(works.get(works.size()-1).getMode() == ERASER) {
						eraserStartRedo.add(redo.size());
						UndoRedoAction.undoEraser();
						eraserEndRedo.add(redo.size()-1);
						g.setColor(Color.WHITE);
						g.fillRect(0, 25, 1060, 650);
						paint();
					} else {
						g.setColor(Color.WHITE);
						g.fillRect(0, 25, 1060, 650);
						UndoRedoAction.undo();
						paint();
					}
				} else {
					JOptionPane.showMessageDialog(null, "실행 취소할 작업이 없습니다.");
				}
			}
		});
		
		JMenuItem menuEditRedo = new JMenuItem("Redo");
		menuEdit.add(menuEditRedo);
		menuEditRedo.setAccelerator(KeyStroke.getKeyStroke('z' ,KeyEvent.VK_CONTROL));
		menuEditRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!redo.isEmpty()) {
					if(redo.get(redo.size()-1).getMode() == PEN) {
						UndoRedoAction.redoPen();
						g.setColor(Color.WHITE);
						g.fillRect(0, 25, 1060, 650);
						paint();
					} else if(redo.get(redo.size()-1).getMode() == ERASER) {
						UndoRedoAction.redoEraser();
						g.setColor(Color.WHITE);
						g.fillRect(0, 25, 1060, 650);
						paint();
					} else {
						g.setColor(Color.WHITE);
						g.fillRect(0, 25, 1060, 650);
						UndoRedoAction.redo();
						paint();
					}
				} else {
					JOptionPane.showMessageDialog(null, "복귀할 작업이 없습니다.");
				}
			}
		});
		
		JMenuItem menuEditCopy = new JMenuItem("Copy");
		menuEdit.add(menuEditCopy);
		
		JMenuItem menuEditCut = new JMenuItem("Cut");
		menuEdit.add(menuEditCut);
		
		JMenuItem menuEditPaste = new JMenuItem("Paste");
		menuEdit.add(menuEditPaste);
		
		JMenuItem menuEditClear = new JMenuItem("Clear");
		menuEdit.add(menuEditClear);
		menuEditClear.setAccelerator(KeyStroke.getKeyStroke('x' ,KeyEvent.VK_CONTROL));
		menuEditClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 25, 1060, 650);
				works.add(new GraphicData(RECT, backgroundStart, backgroundEnd, Color.WHITE, stroke, true));
			}
		});
		
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
				MODE = ERASER;
			}
		});
		
		JButton toolText = new JButton("");
		toolText.setBounds(1070, 220, 117, 40);
		toolText.setIcon(new ImageIcon("./images/text24.png"));
		frame.getContentPane().add(toolText);
		toolText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MODE = TEXT;
				JOptionPane.showMessageDialog(null, "지원하지 않는 기능입니다. ");
			}
		});
		
		JButton toolPattern = new JButton("");
		toolPattern.setBounds(1070, 270, 117, 40);
		toolPattern.setIcon(new ImageIcon("./images/pattern24.png"));
		frame.getContentPane().add(toolPattern);
		toolPattern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MODE = PATTERNS;
				JOptionPane.showMessageDialog(null, "지원하지 않는 기능입니다. ");
			}
		});
		
		JButton toolSpray = new JButton("");
		toolSpray.setBounds(1070, 320, 117, 40);
		toolSpray.setIcon(new ImageIcon("./images/spray24.png"));
		frame.getContentPane().add(toolSpray);
		toolSpray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MODE = SPRAY;
				JOptionPane.showMessageDialog(null, "지원하지 않는 기능입니다. ");
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
		selectStyle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "지원하지 않는 기능입니다. ");
			}
		});
		
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
		current = e.getPoint();
		current.y += 23;
		end = e.getPoint();
		end.y += 23;
		if(MODE == POLYLINE || MODE == SPRAY)
			paint();
		if(MODE == PEN) {
			works.add(new GraphicData(PEN, start, end, color, stroke, fill));
			start = end;
			for(int j = 0; j < penStartIndex.size(); j ++) {
				for(int t = penStartIndex.get(j); t < works.size()-1; t ++) {
					GraphicData w = works.get(t);
					Line.drawLine(g, w.getStart(), w.getEnd(), w.getColor(), w.getStroke());
				}
			}
		}
		if(MODE == ERASER) {
			works.add(new GraphicData(ERASER, start, end, color, stroke, fill));
			start = end;
			for(int j = 0; j < eraserStartIndex.size(); j ++) {
				for(int t = eraserStartIndex.get(j); t < works.size()-1; t ++) {
					GraphicData w = works.get(t);
					Eraser.erase(g, w.getStart(), w.getEnd(), w.getStroke());
				}
			}
		}
		if(MODE == RECT) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 25, 1060, 650);
			paint();
			Rectangle.drawRectangle(g, start, current, color, stroke, fill);
		}
		if(MODE == LINE) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 25, 1060, 650);
			paint();
			Line.drawLine(g, start, end, color, stroke);
		}
		if(MODE == OVAL) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 25, 1060, 650);
			paint();
			Circle.drawCircle(g, start, end, color, stroke, fill);
		}
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
		start = e.getPoint();
		start.y += 23;
		current = e.getPoint();
		current.y += 23;
		if(MODE == SPRAY)
			paint();
		if(MODE == PEN) {
			penStartIndex.add(works.size());
			System.out.println(penStartIndex.get(penStartIndex.size()-1));
		}
		if(MODE == ERASER) {
			eraserStartIndex.add(works.size());
			System.out.println(eraserStartIndex.get(eraserStartIndex.size()-1));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		end = e.getPoint();
		end.y += 23;
		
		if(MODE == RECT || MODE == OVAL || MODE == LINE) {
			works.add(new GraphicData(MODE, start, end, color, stroke, fill));
			paint();
		}
		if(MODE == PEN) {
			penEndIndex.add(works.size()-1);
		}
		if(MODE == ERASER) {
			eraserEndIndex.add(works.size()-1);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * paint method
	 */
	public void paint() {
		
		for(int i = 0; i < works.size(); i ++) {
			
			GraphicData tmp = works.get(i);
			
			switch(tmp.getMode()) {
			 case PEN: 
				 for(int j = 0; j < penStartIndex.size(); j ++) {
					 if(i == penStartIndex.get(j)) {
						 for(int t = penStartIndex.get(j); t < penEndIndex.get(j); t ++) {
							 GraphicData w = works.get(t);
							 Line.drawLine(g, w.getStart(), w.getEnd(), w.getColor(), w.getStroke());
						 }
					 }
				 }			 
				 break;
			 case RECT:
				 Rectangle.drawRectangle(g, tmp.getStart(), tmp.getEnd(), tmp.getColor(), tmp.getStroke(), tmp.getFill());
				 break;
			 case OVAL:
				 Circle.drawCircle(g, tmp.getStart(), tmp.getEnd(), tmp.getColor(), tmp.getStroke(), tmp.getFill());
				 break;
			 case ERASER:
				 for(int j = 0; j < eraserStartIndex.size(); j ++) {
					 if(i == eraserStartIndex.get(j)) {
						 for(int t = eraserStartIndex.get(j); t < eraserEndIndex.get(j); t ++) {
							 GraphicData w = works.get(t);
							 Eraser.erase(g, w.getStart(), w.getEnd(), w.getStroke());
						 }
					 }
				 }
				 break;
			 case LINE:
				 Line.drawLine(g, tmp.getStart(), tmp.getEnd(), tmp.getColor(), tmp.getStroke());
				 break;
			 case GraphicEditor.POLYLINE:
				 //Line.drawPolyline(g2d, start, current);
				 //Line.drawPolyline(g2d, end, end);
				 break;
			 case GraphicEditor.SPRAY:
				 //Spray.spray(g2d, current);
				 break;
			 
			 }
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		
		if(pageIndex > 0) {
			return Printable.NO_SUCH_PAGE;
		}
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		
		paint();
		
		return 0;
	}
	
	
}