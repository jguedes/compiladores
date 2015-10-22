package oficial.grammarclasses;

import java_cup.runtime.Symbol;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import oficial.GeracaoDeCodigo;

public class Sent_Assign extends Sent_node {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean op_IntegerIsId;
	private String op_IntegerAtual;

	public Sent_Assign(Symbol sent_assign, Symbol ID, Symbol OP_ATRIB, Exp exp) {
		super(sent_assign);

		DefaultMutableTreeNode op_atrib = new DefaultMutableTreeNode(OP_ATRIB);

		op_atrib.add(exp);

		op_atrib.add(new DefaultMutableTreeNode(ID));

		add(op_atrib);

		String var_name = GeracaoDeCodigo.getNomeDoSimbolo(ID);

		// GeracaoDeCodigo.introduzirSimbolo(var_name);

		// ver se exp é um número.

		TreeNode aux;

		int constante;

		aux = exp.getFirstChild();// exp_simple

		if (aux instanceof Exp_Simple) {// pois pode ser também op_boolean

			if (exp_simpleIsNumberOrId(aux)) {// pois exp_simple pode ser uma
												// espressão

				if (op_IntegerIsId) {// atribuição de variável em variável

					GeracaoDeCodigo.emitRM_LD(var_name, op_IntegerAtual);

				} else {// atribuição de constante em variável
					// JOptionPane.showMessageDialog(null, "constante: " +
					// op_IntegerAtual);

					constante = Integer.parseInt(op_IntegerAtual);

					GeracaoDeCodigo.emitRM_LDC(var_name, constante);

				}

			} else if (exp_simpleIsAddOrSub(aux)) {// então é op_add_or_sub

				aux = aux.getChildAt(0);// op_add_or_sub

				aux = aux.getChildAt(0);// operador (+ | -)

				String operador = aux.toString().split("Symbol:")[1].trim();

				// encontrar operando da esquerda
				String var_left = getVariavel(aux.getChildAt(1));

				// encontrar operando da direita
				String var_right = getVariavel(aux.getChildAt(0));

				if (operador.equals("+")) {

					GeracaoDeCodigo.emitRO_ADD(var_name, var_left, var_right);

				} else {
					GeracaoDeCodigo.emitRO_SUB(var_name, var_left, var_right);
				}

			} else if (exp_simpleIsMulOrDiv(aux)) {// então é op_add_or_sub

				// encontrar operando da esquerda

			}
		}

	}

	private String getVariavel(TreeNode childAt) {

		String var_temp = null;

		if (isNumerOrIdentif(childAt)) {// o nó é um número ou identificador.

			if (op_IntegerIsId) {// é identificador?

				var_temp = op_IntegerAtual;

			} else {// é número?

				int constante = Integer.parseInt(op_IntegerAtual);

				var_temp = GeracaoDeCodigo.gerarVariavelTemporaria();

				GeracaoDeCodigo.emitRM_LDC(var_temp, constante);

			}

		} else {// então é uma expressão

			childAt = childAt.getChildAt(0);

			childAt = buscarExpressao(childAt);

			JOptionPane.showMessageDialog(null, childAt);

			TreeNode operador = childAt.getChildAt(0);// pegar o sinal da
														// expressão

			JOptionPane.showMessageDialog(null, operador);

			TreeNode expEsquerda = operador.getChildAt(1);

			TreeNode expDireita = operador.getChildAt(0);

			String s = getVariavel(expEsquerda);

			String t = getVariavel(expDireita);

			String sinal = operador.toString().split("Symbol:")[1].trim();

			var_temp = GeracaoDeCodigo.gerarVariavelTemporaria();

			switch (sinal) {

			case "+":

				GeracaoDeCodigo.emitRO_ADD(var_temp, s, t);

				break;

			case "-":

				GeracaoDeCodigo.emitRO_SUB(var_temp, s, t);

				break;

			case "*":

				GeracaoDeCodigo.emitRO_MUL(var_temp, s, t);

				break;

			case "/":

				GeracaoDeCodigo.emitRO_DIV(var_temp, s, t);

				break;

			default:
				break;
			}
		}

		return var_temp;

	}

	private TreeNode buscarExpressao(TreeNode childAt) {

		if (childAt instanceof Term) {

			childAt = childAt.getChildAt(0);

			if (childAt instanceof Op_Factor) {

				if (childAt.getChildCount() > 1) {

					childAt = childAt.getChildAt(1);

					if (childAt instanceof Exp) {

						childAt = childAt.getChildAt(0);

						if (childAt instanceof Exp_Simple) {

							childAt = childAt.getChildAt(0);

							childAt = buscarExpressao(childAt);

						}

					}

				}

			} else {// childAt é um Op_Mul_or_Div

				childAt = childAt.getChildAt(0);// Term or Op_Factor

				if (childAt instanceof Term) {

					childAt = buscarExpressao(childAt);

				} else {

					if (childAt.getChildCount() > 1) {

						childAt = childAt.getChildAt(1);

						if (childAt instanceof Exp) {

							childAt = childAt.getChildAt(0);

							if (childAt instanceof Exp_Simple) {

								childAt = childAt.getChildAt(0);

								childAt = buscarExpressao(childAt);

							}

						}

					}

				}

			}

		}

		return childAt;
	}

	private boolean isNumerOrIdentif(TreeNode childAt) {

		if (childAt instanceof Exp_Simple) {
			// //JOptionPane.showMessageDialog(null, "TIPO: " + childAt);
			return exp_simpleIsNumberOrId(childAt);
		}

		return false;
	}

	private boolean exp_simpleIsAddOrSub(TreeNode aux) {
		aux = aux.getChildAt(0);// add_or_sub

		return aux instanceof Op_Add_Or_Sub;
	}

	private boolean exp_simpleIsMulOrDiv(TreeNode aux) {

		return false;
	}

	private boolean exp_simpleIsNumberOrId(TreeNode aux) {
		aux = aux.getChildAt(0);// term
		if (aux instanceof Term) {
			return termIsNumberOrId(aux);
		}
		return false;
	}

	private boolean termIsNumberOrId(TreeNode aux) {
		aux = aux.getChildAt(0);// op_factor
		if (aux instanceof Op_Factor) {
			return (op_FactorIsNumberOrId(aux));
		}
		return false;
	}

	private boolean op_FactorIsNumberOrId(TreeNode aux) {
		aux = aux.getChildAt(0);// op_Integer
		if (aux instanceof Op_Integer) {
			aux = aux.getChildAt(0);// NumbeOrId
			op_IntegerIsId(aux.toString().split("Symbol:")[1].trim());
			// JOptionPane.showMessageDialog(null, "TIPO: " + aux);
			return true;
		}
		return false;
	}

	private void op_IntegerIsId(String aux) {

		op_IntegerIsId = false;

		op_IntegerAtual = aux;

		for (char c : aux.toCharArray()) {

			if (!Character.isDigit(c)) {

				op_IntegerIsId = true;

				break;

			}

		}

	}

}
