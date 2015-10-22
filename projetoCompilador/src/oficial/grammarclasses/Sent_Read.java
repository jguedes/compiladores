package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

import oficial.GeracaoDeCodigo;

public class Sent_Read extends Sent_node {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private static int i = 1;

	public Sent_Read(Symbol sent_read, Symbol READ, Symbol ID) {
		super(sent_read);

		add(new DefaultMutableTreeNode(ID));

		add(new DefaultMutableTreeNode(READ));

		GeracaoDeCodigo.emitRO_IN(GeracaoDeCodigo.getNomeDoSimbolo(ID));

		// JOptionPane.showMessageDialog(null, i++);

	}

}
