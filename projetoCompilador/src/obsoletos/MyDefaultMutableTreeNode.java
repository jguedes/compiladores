package obsoletos;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class MyDefaultMutableTreeNode extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Symbol op;

	public MyDefaultMutableTreeNode(Symbol symbolNode) {
		super(symbolNode);
	}

	public Symbol getOp() {
		return op;
	}

	public void setOp(Symbol op) {
		this.op = op;
	}

}
