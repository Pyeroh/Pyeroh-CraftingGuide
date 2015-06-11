package view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.impl.Item;
import model.impl.Item.ItemData;

public class TestFrame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 818148271075948079L;

	public TestFrame() {
		getContentPane().setLayout(null);
//		getContentPane().add(new ItemPane(IItem.itemList.get(new Random().nextInt(IItem.itemList.size()))));
		getContentPane().add(new ItemPane(Item.getBy("minecraft:redstone", ItemData.ID_AND_META)));

		setSize(1000, 500);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
