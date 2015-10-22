package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class NonTerminal_If extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonTerminal_If(Symbol nonTerminal_if, Exp exp) {
		super(nonTerminal_if);

		//AnaliseSemamtica.NonTerminal_If(exp);

		add(exp);
		
	}

}
