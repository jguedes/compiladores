package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class Programa extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Programa(Symbol programa, Sequencia_sent sequencia_sent) {
		super(programa);

		add(sequencia_sent);

	}

}
