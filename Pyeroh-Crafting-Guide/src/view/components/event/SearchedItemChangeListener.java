package view.components.event;

import java.util.EventListener;

import model.impl.Item;

public interface SearchedItemChangeListener extends EventListener {

	void searchedItemChanged(Item item);

}
