package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class Op_Boolean extends DefaultMutableTreeNode implements IMyType {

	private int mytype;

	@Override
	public void setMyType(int myType) {
		this.mytype = myType;

	}

	@Override
	public int getMyType() {
		return mytype;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Op_Boolean(Symbol op_boolean, Exp_Simple l_exp_simple, Symbol OP,
			Exp_Simple r_exp_simple) {
		super(op_boolean);

		DefaultMutableTreeNode op = new DefaultMutableTreeNode(OP);

		// AnaliseSemamtica.Op_Boolean(l_exp_simple, r_exp_simple);

		op.add(r_exp_simple);

		op.add(l_exp_simple);

		add(op);

	}

}
