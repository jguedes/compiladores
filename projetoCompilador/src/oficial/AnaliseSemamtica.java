package oficial;

import java.util.Stack;

import java_cup.runtime.Symbol;
import oficial.grammarclasses.*;

public class AnaliseSemamtica {

	private static Stack<String> stkErrosSemanticos;

	public static Stack<String> getStkErrosSemanticos() {
		return stkErrosSemanticos;
	}

	private static void iniciarPilha() {

		if (stkErrosSemanticos == null) {

			System.out.print("\nIniciando pilha de erros semânticos! - ");

			stkErrosSemanticos = new Stack<String>();

			System.out.println("OK!");

		}

	}

	private static void push(String local, String encontrado, String esperado) {

		iniciarPilha();

		stkErrosSemanticos.push("Erro semântico em ["
				+ local.replace("Symbol: ", "") + "]. Tipo encotrado: ["
				+ encontrado + "]. Era esperado tipo: [" + esperado + "]");

		System.out.println(stkErrosSemanticos.peek());

	}

	public static boolean Op_Mult_Or_Quoc(Term term, Op_Factor op_factor) {

		// iniciarPilha();

		if (term.getMyType() == IMyType.BOOLEAN) {

			push(term.toString(), "Booleano", "Inteiro");

			return false;

		}

		if (op_factor.getMyType() == IMyType.BOOLEAN) {

			push(op_factor.toString(), "Booleano", "Inteiro");

			return false;

		}

		return true;

	}

	public static boolean Op_Boolean(Exp_Simple l_exp_simple,
			Exp_Simple r_exp_simple) {

		if (l_exp_simple.getMyType() == IMyType.BOOLEAN) {

			push(l_exp_simple.toString(), "Booleano", "Inteiro");

			return false;

		}

		if (r_exp_simple.getMyType() == IMyType.BOOLEAN) {

			push(r_exp_simple.toString(), "Booleano", "Inteiro");

			return false;

		}

		return true;

	}

	public static boolean Op_Add_Or_Sub(Exp_Simple l_exp_simple,
			Exp_Simple r_exp_simple) {

		if (l_exp_simple.getMyType() == IMyType.BOOLEAN) {

			push(l_exp_simple.toString(), "Booleano", "Inteiro");

			return false;

		}

		if (r_exp_simple.getMyType() == IMyType.BOOLEAN) {

			push(r_exp_simple.toString(), "Booleano", "Inteiro");

			return false;

		}

		return true;

	}

	public static boolean NonTerminal_If(Exp exp) {

		if (exp.getMyType() == IMyType.INTEGER) {

			push(exp.toString(), "Inteiro", "Booleano");

			return false;

		}

		return true;

	}

	public static void NonTerminal_Until(Exp exp) {

		// NonTerminal_If(exp);

	}

	public static boolean NonTerminal_Until(Symbol symbol) {

		return isBoolean(symbol);

	}

	private static boolean isBoolean(Symbol symbol) {

		if (Boolean.class.isInstance(symbol.value)) {

			return true;

		} else {

			push(symbol.toString(), "Inteiro", "Booleano");

			return false;

		}

	}

	private static boolean isInteger(Symbol symbol) {

		if (Integer.class.isInstance(symbol.value)) {

			return true;

		} else {

			push(symbol.toString(), "Booleano", "Inteiro");

			return false;

		}
	}

	public static boolean NonTerminal_If(Symbol symbol) {

		return NonTerminal_Until(symbol);

	}

	public static boolean Sent_Assign(Symbol symbol) {

		return isInteger(symbol);

	}

	public static boolean Op_Boolean(Symbol symbol, Symbol symbol2) {

		return (isInteger(symbol) && isInteger(symbol2));

	}

	public static boolean Op_Add_Or_Sub(Symbol symbol, Symbol symbol2) {

		return Op_Boolean(symbol, symbol2);

	}

	public static boolean Op_Mult_Or_Quoc(Symbol symbol, Symbol symbol2) {

		return Op_Boolean(symbol, symbol2);

	}

}
