package oficial.grammarclasses;

import javax.swing.tree.DefaultMutableTreeNode;

import java_cup.runtime.Symbol;

public class Sent_Write extends Sent_node {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sent_Write(Symbol sent_write, Symbol WRITE, Exp exp) {
		super(sent_write);

		add(exp);

		add(new DefaultMutableTreeNode(WRITE));

	}

}
