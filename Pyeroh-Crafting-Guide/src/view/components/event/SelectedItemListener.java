/**
 *
 */
package view.components.event;

import java.util.EventListener;

import model.impl.Item;

/**
 * Interface pour le pattern Listener.<br>
 * Listener pour la sélection d'items
 *
 * @author Pyeroh
 *
 */
public interface SelectedItemListener extends EventListener {

	void selectItem(Item item);

}
