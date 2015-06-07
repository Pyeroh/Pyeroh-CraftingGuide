package view.components;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class JHoverList<E> extends JList<E> {

	private static final long serialVersionUID = 885727775916469549L;
	private HoverListCellRenderer renderer;
	
	public JHoverList() {
		super();
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		renderer = new HoverListCellRenderer(this);
		this.setCellRenderer(renderer);
		this.addMouseListener(renderer.getHandler());
		this.addMouseMotionListener(renderer.getHandler());
	}

}
