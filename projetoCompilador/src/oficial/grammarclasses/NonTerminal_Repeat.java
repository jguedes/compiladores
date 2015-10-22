package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class NonTerminal_Repeat extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonTerminal_Repeat(Symbol nonTerminal_repeat,
			Sequencia_sent sequencia_sent) {
		super(nonTerminal_repeat);

		add(sequencia_sent);

	}

}
