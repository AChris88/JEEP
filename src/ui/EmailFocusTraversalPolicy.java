package ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;

/**
 * Class to manage the email cleitn's focus traversal policy.
 * @author Slaiy
 *
 */
class EmailFocusTraversalPolicy extends FocusTraversalPolicy {
	Vector<Component> order;

	/**
	 * Constructor setting components. 
	 * @param order order of tab traversal policy.
	 */
	public EmailFocusTraversalPolicy(Vector<Component> order) {
		this.order = new Vector<Component>(order.size());
		this.order.addAll(order);
	}

	/**
	 * Returns the next Component in the Vector.
	 */
	public Component getComponentAfter(Container focusCycleRoot,
			Component aComponent) {
		int idx = (order.indexOf(aComponent) + 1) % order.size();
		return order.get(idx);
	}

	/**
	 * Returns the previous Component in the Vector.
	 */
	public Component getComponentBefore(Container focusCycleRoot,
			Component aComponent) {
		int idx = order.indexOf(aComponent) - 1;
		if (idx < 0) {
			idx = order.size() - 1;
		}
		return order.get(idx);
	}

	/**
	 * Returns the default Component.
	 */
	public Component getDefaultComponent(Container focusCycleRoot) {
		return order.get(0);
	}

	/**
	 * Returns the last Component.
	 */
	public Component getLastComponent(Container focusCycleRoot) {
		return order.lastElement();
	}

	/**
	 * Returns the first Component.
	 */
	public Component getFirstComponent(Container focusCycleRoot) {
		return order.get(0);
	}
}