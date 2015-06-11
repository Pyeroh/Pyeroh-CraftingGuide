package view.components;

import java.awt.event.MouseAdapter;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class JHoverList<E> extends JList<E> {

	private static final long serialVersionUID = 885727775916469549L;

	private HoverListCellRenderer renderer;

	private boolean hoverable = false;

	public JHoverList() {
		super();
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		renderer = new HoverListCellRenderer(this);
		this.setCellRenderer(renderer);
		toggleHoverable();
	}

	/**
	 * Change la couleur des cellules au survol, ou non
	 */
	public void toggleHoverable() {
		MouseAdapter handler = renderer.getHandler();
		hoverable = !hoverable;
		if (hoverable) {
			this.addMouseListener(handler);
			this.addMouseMotionListener(handler);
		}
		else {
			this.removeMouseListener(handler);
			this.removeMouseMotionListener(handler);
		}
	}

}
