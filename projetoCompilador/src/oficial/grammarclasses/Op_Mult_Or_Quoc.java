package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class Op_Mult_Or_Quoc extends DefaultMutableTreeNode implements IMyType {

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

	public Op_Mult_Or_Quoc(Symbol op_mult_or_quoc, Term term, Symbol OP,
			Op_Factor op_factor) {
		super(op_mult_or_quoc);

		DefaultMutableTreeNode op = new DefaultMutableTreeNode(OP);

		// AnaliseSemamtica.Op_Mult_Or_Quoc(term,op_factor);

		op.add(op_factor);

		op.add(term);

		add(op);
	}

}
