package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class Exp extends DefaultMutableTreeNode implements IMyType {

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

	public Exp(Symbol exp, Op_Boolean op_boolean) {
		super(exp);

		add(op_boolean);

		setMyType(op_boolean.getMyType());

	}

	public Exp(Symbol exp, Exp_Simple exp_simple) {
		super(exp);

		add(exp_simple);

		setMyType(exp_simple.getMyType());

	}

}
