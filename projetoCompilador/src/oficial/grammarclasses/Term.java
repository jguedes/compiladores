package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class Term extends DefaultMutableTreeNode implements IMyType {

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

	public Term(Symbol term, Op_Mult_Or_Quoc op_mult_or_quoc) {
		super(term);

		add(op_mult_or_quoc);

		setMyType(op_mult_or_quoc.getMyType());

	}

	public Term(Symbol term, Op_Factor op_factor) {
		super(term);

		add(op_factor);

		setMyType(op_factor.getMyType());

	}

}
