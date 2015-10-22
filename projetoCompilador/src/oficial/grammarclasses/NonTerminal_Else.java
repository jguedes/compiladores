package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class NonTerminal_Else extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonTerminal_Else(Symbol nonTerminal_else,
			Sequencia_sent sequencia_sent) {
		super(nonTerminal_else);

		add(sequencia_sent);

	}

}
