package view.components;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.TextLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import sun.font.FontDesignMetrics;

/**
 * Un label avec de l'ombre
 * 
 * @author Pyeroh
 *
 */
public class ShadowLabel extends JLabel {

	private static final long serialVersionUID = 2440262551845259703L;

	private int decalage = 2;

	public ShadowLabel() {
		super("");
	}

	public ShadowLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
	}

	public ShadowLabel(String text) {
		super(text);
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		repaint();
	}

	public int getDecalage() {
		return decalage;
	}

	public void setDecalage(int decalage) {
		this.decalage = decalage;
	}

	public void paint(Graphics g) {
		if (getBorder() != null) {
			getBorder().paintBorder(this, g, 0, 0, getWidth(), getHeight());
		}

		FontMetrics fm = FontDesignMetrics.getMetrics(getFont());
		
		if (getText() != null) {
			if (!getText().isEmpty()) {

				Rectangle r = fm.getStringBounds(getText(), g).getBounds();
				int x = 0;
				int y = 0;

				switch (getHorizontalAlignment()) {
				case SwingConstants.LEADING:
				case SwingConstants.LEFT:
					x = 0;
					break;
				case SwingConstants.CENTER:
					x = (getWidth() - r.width - decalage) / 2;
					break;
				case SwingConstants.RIGHT:
				case SwingConstants.TRAILING:
					x = getWidth() - r.width - decalage;
				default:
					break;
				}

				y = getFont().getSize();
				switch (getVerticalAlignment()) {
				case SwingConstants.TOP:
					break;
				case SwingConstants.CENTER:
					y = ((getHeight() - y - decalage) / 2) + y;
					break;
				case SwingConstants.BOTTOM:
					y = getHeight() - decalage;
					break;
				default:
					break;
				}

				Graphics2D g1 = (Graphics2D) g;
				TextLayout textLayout = new TextLayout(getText(), getFont(),
						g1.getFontRenderContext());
				g1.setPaint(getBackground());
				textLayout.draw(g1, x + decalage, y + decalage);
				g1.setPaint(getForeground());
				textLayout.draw(g1, x, y);
			}
		}
		else
			super.paint(g);

	}

}
