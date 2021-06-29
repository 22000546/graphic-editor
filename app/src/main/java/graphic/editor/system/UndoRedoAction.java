package graphic.editor.system;

import java.awt.Graphics;

import graphic.editor.GraphicEditor;

public class UndoRedoAction {

	public static void undo() {
		
		GraphicData g = GraphicEditor.works.remove(GraphicEditor.works.size()-1);
		GraphicEditor.redo.add(g);
		
	}
	
	public static void undoPen() {
		
		GraphicEditor.penStartRedo.add(GraphicEditor.penStartIndex.get(GraphicEditor.penStartIndex.size()-1));
		GraphicEditor.penEndRedo.add(GraphicEditor.penEndIndex.get(GraphicEditor.penEndIndex.size()-1));
		for(int i = GraphicEditor.penEndIndex.get(GraphicEditor.penEndIndex.size()-1); i >= GraphicEditor.penStartIndex.get(GraphicEditor.penStartIndex.size()-1); i --) {
			GraphicEditor.redo.add(GraphicEditor.works.remove(i));
		}
		GraphicEditor.penStartIndex.remove(GraphicEditor.penStartIndex.size()-1);
		GraphicEditor.penEndIndex.remove(GraphicEditor.penEndIndex.size()-1);
	}
	
	public static void undoEraser() {

		GraphicEditor.eraserStartRedo.add(GraphicEditor.eraserStartIndex.get(GraphicEditor.eraserStartIndex.size()-1));
		GraphicEditor.eraserEndRedo.add(GraphicEditor.eraserEndIndex.get(GraphicEditor.eraserEndIndex.size()-1));
		for(int i = GraphicEditor.eraserEndIndex.get(GraphicEditor.eraserEndIndex.size()-1); i >= GraphicEditor.eraserStartIndex.get(GraphicEditor.eraserStartIndex.size()-1); i --) {
			GraphicEditor.redo.add(GraphicEditor.works.remove(i));
		}
		GraphicEditor.eraserStartIndex.remove(GraphicEditor.eraserStartIndex.size()-1);
		GraphicEditor.eraserEndIndex.remove(GraphicEditor.eraserEndIndex.size()-1);
		
	}
	
	public static void redo() {
		
		GraphicEditor.works.add(GraphicEditor.redo.remove(GraphicEditor.redo.size()-1));
		
	}
	
	public static void redoPen() {
		GraphicEditor.penStartIndex.add(GraphicEditor.penStartRedo.get(GraphicEditor.penStartRedo.size()-1));
		GraphicEditor.penEndIndex.add(GraphicEditor.penEndRedo.get(GraphicEditor.penEndRedo.size()-1));
		for(int i = GraphicEditor.penEndRedo.get(GraphicEditor.penEndRedo.size()-1); i >= GraphicEditor.penStartRedo.get(GraphicEditor.penStartRedo.size()-1); i --) {
			GraphicEditor.works.add(GraphicEditor.redo.remove(i));
		}
		GraphicEditor.penStartRedo.remove(GraphicEditor.penStartRedo.size()-1);
		GraphicEditor.penEndRedo.remove(GraphicEditor.penEndRedo.size()-1);
	}
	
	public static void redoEraser() {
		GraphicEditor.eraserStartIndex.add(GraphicEditor.eraserStartRedo.get(GraphicEditor.eraserStartRedo.size()-1));
		GraphicEditor.eraserEndIndex.add(GraphicEditor.eraserEndRedo.get(GraphicEditor.eraserEndRedo.size()-1));
		for(int i = GraphicEditor.eraserEndRedo.get(GraphicEditor.eraserEndRedo.size()-1); i >= GraphicEditor.eraserStartRedo.get(GraphicEditor.eraserStartRedo.size()-1); i --) {
			GraphicEditor.works.add(GraphicEditor.redo.remove(i));
		}
		GraphicEditor.eraserStartRedo.remove(GraphicEditor.eraserStartRedo.size()-1);
		GraphicEditor.eraserEndRedo.remove(GraphicEditor.eraserEndRedo.size()-1);
	}
	
}
