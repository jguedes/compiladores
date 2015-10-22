package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class Sequencia_sent extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sequencia_sent(Symbol sequencia_sent,
			Sequencia_sent _sequencia_sent, Symbol semi, Sent sent) {
		super(sequencia_sent);

		add(_sequencia_sent);

		add(new DefaultMutableTreeNode(semi));

		add(sent);

	}

	public Sequencia_sent(Symbol sequencia_sent, Sent sent) {
		super(sequencia_sent);

		add(sent);

	}

}
