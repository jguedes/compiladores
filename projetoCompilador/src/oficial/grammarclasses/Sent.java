package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class Sent extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sent(Symbol sent, Sent_node sent_node) {
		super(sent);

		add(sent_node);

	}

}
