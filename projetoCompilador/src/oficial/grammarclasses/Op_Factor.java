package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class Op_Factor extends DefaultMutableTreeNode implements IMyType {

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

	public Op_Factor(Symbol op_factor, Symbol LPAREN, Exp exp, Symbol RPAREN) {
		super(op_factor);

		add(new DefaultMutableTreeNode(RPAREN));

		add(exp);

		add(new DefaultMutableTreeNode(LPAREN));

		setMyType(exp.getMyType());

	}

	public Op_Factor(Symbol op_factor, Op_Integer op_integer) {
		super(op_factor);

		add(op_integer);

		setMyType(op_integer.getMyType());

	}

}
