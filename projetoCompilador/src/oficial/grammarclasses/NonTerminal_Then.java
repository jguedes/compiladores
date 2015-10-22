package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class NonTerminal_Then extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonTerminal_Then(Symbol nonTerminal_then,
			Sequencia_sent sequencia_sent) {
		super(nonTerminal_then);

		add(sequencia_sent);

	}

}
