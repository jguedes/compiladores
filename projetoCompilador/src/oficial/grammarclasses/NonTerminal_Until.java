package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class NonTerminal_Until extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonTerminal_Until(Symbol nonTerminal_until, Exp exp) {
		super(nonTerminal_until);

		// AnaliseSemamtica.NonTerminal_Until(exp);

		add(exp);

	}

}
