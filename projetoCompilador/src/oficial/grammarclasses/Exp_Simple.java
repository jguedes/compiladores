package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

public class Exp_Simple extends DefaultMutableTreeNode implements IMyType {

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

	public Exp_Simple(Symbol exp_simple, Op_Add_Or_Sub op_add_or_sub) {
		super(exp_simple);

		add(op_add_or_sub);

		setMyType(op_add_or_sub.getMyType());

	}

	public Exp_Simple(Symbol exp_simple, Term term) {
		super(exp_simple);

		setMyType(term.getMyType());

		add(term);

	}

}
