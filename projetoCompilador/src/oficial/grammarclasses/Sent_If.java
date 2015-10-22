package oficial.grammarclasses;

import javax.swing.tree.DefaultMutableTreeNode;

import java_cup.runtime.Symbol;

public class Sent_If extends Sent_node {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sent_If(Symbol sent_if, NonTerminal_If nonTerminal_if,
			NonTerminal_Then nonTerminal_then, Symbol END) {
		super(sent_if);

		add(nonTerminal_if);
		
		add(nonTerminal_then);
		
		add(new DefaultMutableTreeNode(END));
		
	}

	public Sent_If(Symbol sent_if, NonTerminal_If nonTerminal_if,
			NonTerminal_Then nonTerminal_then, NonTerminal_Else nonTerminal_else, Symbol END) {
		super(sent_if);

		add(nonTerminal_if);
		
		add(nonTerminal_then);
		
		add(nonTerminal_else);
		
		add(new DefaultMutableTreeNode(END));
		
	}
	
}
