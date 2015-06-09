package view;

import java.util.Random;

import javax.swing.WindowConstants;

import model.interfaces.IItem;

import org.jdesktop.swingx.JXFrame;

public class TestFrame extends JXFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 818148271075948079L;

	public TestFrame() {

		setLayout(null);
		add(new ItemPane(IItem.itemList.get(new Random().nextInt(IItem.itemList.size()))));

		setSize(1000, 500);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

}
