package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

import oficial.GeracaoDeCodigo;

public class Op_Integer extends DefaultMutableTreeNode implements IMyType {

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

	public Op_Integer(Symbol op_integer, Symbol NUMBER_or_ID) {
		super(op_integer);

		add(new DefaultMutableTreeNode(NUMBER_or_ID));

		setMyType(IMyType.INTEGER);

		String dado = GeracaoDeCodigo.getNomeDoSimbolo(NUMBER_or_ID);

		boolean isID = false;

		// ver se é um identificador ou um número
		for (char c : dado.toCharArray()) {

			if (!Character.isDigit(c)) {
				isID = true;
				break;
			}

		}

		// se for um identificador seta um registrador para ele na geração de
		// código.
		if (isID) {

			GeracaoDeCodigo.introduzirSimbolo(dado);

		}

	}

}
