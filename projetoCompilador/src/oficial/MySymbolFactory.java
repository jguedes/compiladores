package oficial;

import java.util.Stack;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;

import oficial.grammarclasses.*;

public class MySymbolFactory extends ComplexSymbolFactory {

	private Stack<Symbol> stkSymbol;
	private Stack<DefaultMutableTreeNode> stkGrammar;
	private String[] childs;
	private int stkSymbol_Top;

	@Override
	public Symbol newSymbol(String name, int id, Symbol left, Symbol right,
			Object value) {

		ComplexSymbol sym = (ComplexSymbol) super.newSymbol(name, id, left,
				right, value);

		sym.xleft = new Location(left.left, left.right); // initial line

		sym.xright = new Location(right.left, right.right);// end line

		System.out
				.println("--------------------------------------------------------------------------------------------------");

		incluirNaArvoreSintatica(name, sym);

		return sym;
	}

	@SuppressWarnings({ "unused" })
	public void visualizarStack() {

		String aux;

		char charArray[];

		StringBuilder s = null;

		System.out.println("\nStack da Gramática(size= " + stkGrammar.size()
				+ "):");

		for (int i = stkGrammar.size() - 1; i >= 0; i--) {

			if (stkGrammar.toArray()[i] == null)
				continue;

			if (s == null)
				s = new StringBuilder();

			aux = ((DefaultMutableTreeNode) stkGrammar.toArray()[i]).toString();

			charArray = aux.toCharArray();

			// montar linha para imprimir (----------)
			for (char c : charArray) {
				s.append("-");
			}

			System.out.println("\t| " + aux + " |");

			System.out.println("\t| " + s.toString() + " |");

			s = null;

		}

		System.out
				.println("\n---------------------------------------------------------------------------\n");

	}

	private void incluirNaArvoreSintatica(String name, ComplexSymbol sym) {

		if (stkGrammar == null) {

			stkGrammar = new Stack<DefaultMutableTreeNode>();

		}

		switch (name.toLowerCase()) {

		case "programa": {

			programa(sym);

		}
			break;

		case "sequencia_sent": {

			sequencia_sent(sym);

		}

			break;

		case "sent": {

			sent(sym);

		}

			break;

		case "sent_if": {

			sent_if(sym);

		}

			break;

		case "sent_repeat": {// sent_repeat ::= repeat until

			sent_repeat(sym);

		}

			break;

		case "sent_assign": {// sent_assign ::= ID OP_ATRIB exp

			sent_assign(sym);

		}

			break;

		case "sent_read": {// sent_read ::= READ ID

			sent_read(sym);

		}

			break;

		case "sent_write": {// sent_write ::= WRITE exp

			sent_write(sym);

		}

			break;

		case "if": {// if ::= IF exp

			nonTerminal_If(sym);

		}

			break;

		case "then": {// then ::= THEN sequencia_sent

			nonTerminal_Then(sym);

		}

			break;

		case "else": {// else ::= ELSE sequencia_sent

			nonTerminal_Else(sym);

		}

			break;

		case "repeat": {// repeat ::= REPEAT sequencia_sent

			nonTerminal_Repeat(sym);

		}

			break;

		case "until": {// until ::= UNTIL exp

			nonTerminal_Until(sym);

		}

			break;

		case "exp": {

			exp(sym);

		}

			break;

		case "exp_simple": {

			exp_Simple(sym);

		}

			break;

		case "term": {

			term(sym);

		}

			break;

		case "op_factor": {

			op_Factor(sym);

		}

			break;

		case "op_integer": { // op_Integer ::= NUMBER | ID

			op_Integer(sym);

		}

			break;

		case "op_mult_or_quoc": {// op_mult_or_quoc ::= term OP_MULT op_factor |
									// term OP_QUOC op_factor

			op_Mult_Or_Quoc(sym);

		}

			break;

		case "op_add_or_sub": {// op_add_or_sub ::= exp_simple OP_ADD exp_simple
								// | exp_simple OP_SUB exp_simple

			op_Add_Or_Sub(sym);

		}

			break;

		case "op_boolean": {// op_boolean ::= exp_simple OP_MQ exp_simple |
							// exp_simple OP_IGUAL exp_simple

			op_Boolean(sym);

		}

			break;

		default:

			System.out.println("MySymbolFactory.class: Name de sym: " + sym
					+ ".");

			break;
		}

		visualizarStack();

	}

	private void op_Boolean(ComplexSymbol sym) {
		Exp_Simple r_exp_simple = (Exp_Simple) stkGrammar.pop();

		Symbol OP = (Symbol) stkSymbol.elementAt(stkSymbol_Top - 1);

		Exp_Simple l_exp_simple = (Exp_Simple) stkGrammar.pop();

		Op_Boolean op_boolean = new Op_Boolean(sym, l_exp_simple, OP,
				r_exp_simple);

		stkGrammar.push(op_boolean);
	}

	private void op_Add_Or_Sub(ComplexSymbol sym) {
		Exp_Simple r_exp_simple = (Exp_Simple) stkGrammar.pop();

		Symbol OP = (Symbol) stkSymbol.elementAt(stkSymbol_Top - 1);

		Exp_Simple l_exp_simple = (Exp_Simple) stkGrammar.pop();

		Op_Add_Or_Sub op_add_or_sub = new Op_Add_Or_Sub(sym, l_exp_simple, OP,
				r_exp_simple);

		stkGrammar.push(op_add_or_sub);
	}

	private void op_Mult_Or_Quoc(ComplexSymbol sym) {
		Op_Factor op_factor = (Op_Factor) stkGrammar.pop();

		Symbol OP = (Symbol) stkSymbol.elementAt(stkSymbol_Top - 1);

		Term term = (Term) stkGrammar.pop();

		Op_Mult_Or_Quoc op_mult_or_quoc = new Op_Mult_Or_Quoc(sym, term, OP,
				op_factor);

		stkGrammar.push(op_mult_or_quoc);
	}

	private void op_Integer(ComplexSymbol sym) {
		Symbol NUMBER_or_ID = (Symbol) stkSymbol.peek();

		Op_Integer op_Integer = new Op_Integer(sym, NUMBER_or_ID);

		stkGrammar.push(op_Integer);
	}

	private void op_Factor(ComplexSymbol sym) {

		Op_Factor op_factor = null;

		Symbol RPAREN, LPAREN;

		Exp exp;

		Op_Integer op_Integer;

		if (childs.length == 3) {// op_factor ::= LPAREN exp RPAREN

			RPAREN = (Symbol) stkSymbol.peek();

			LPAREN = (Symbol) stkSymbol.elementAt(stkSymbol_Top - 2);

			exp = (Exp) stkGrammar.pop();

			op_factor = new Op_Factor(sym, LPAREN, exp, RPAREN);

		} else if (childs.length == 1) {// op_factor ::= op_Integer

			op_Integer = (Op_Integer) stkGrammar.pop();

			op_factor = new Op_Factor(sym, op_Integer);

		}

		stkGrammar.push(op_factor);
	}

	private void term(ComplexSymbol sym) {
		Term term = null;

		Op_Mult_Or_Quoc op_mult_or_quoc;

		Op_Factor op_factor;

		if (childs[0].equalsIgnoreCase("op_mult_or_quoc")) {// term ::=
															// op_mult_or_quoc

			op_mult_or_quoc = (Op_Mult_Or_Quoc) stkGrammar.pop();

			term = new Term(sym, op_mult_or_quoc);

		} else if (childs[0].equalsIgnoreCase("op_factor")) {// term ::=
																// op_factor

			op_factor = (Op_Factor) stkGrammar.pop();

			term = new Term(sym, op_factor);

		}

		stkGrammar.push(term);
	}

	private void exp_Simple(ComplexSymbol sym) {
		Exp_Simple exp_simple = null;

		Op_Add_Or_Sub op_add_or_sub;

		Term term;

		if (childs[0].equalsIgnoreCase("op_add_or_sub")) {// exp_simple ::=
															// op_add_or_sub

			op_add_or_sub = (Op_Add_Or_Sub) stkGrammar.pop();

			exp_simple = new Exp_Simple(sym, op_add_or_sub);

		} else if (childs[0].equalsIgnoreCase("term")) {// exp_simple
														// ::= term

			term = (Term) stkGrammar.pop();

			exp_simple = new Exp_Simple(sym, term);

		}

		stkGrammar.push(exp_simple);
	}

	private void exp(ComplexSymbol sym) {
		Exp exp = null;

		Op_Boolean op_boolean;

		Exp_Simple exp_simple;

		if (childs[0].equalsIgnoreCase("op_boolean")) {// exp ::= op_boolean

			op_boolean = (Op_Boolean) stkGrammar.pop();

			exp = new Exp(sym, op_boolean);

		} else if (childs[0].equalsIgnoreCase("exp_simple")) {// exp ::=
																// exp_simple

			exp_simple = (Exp_Simple) stkGrammar.pop();

			exp = new Exp(sym, exp_simple);

		}

		stkGrammar.push(exp);
	}

	private void nonTerminal_Until(ComplexSymbol sym) {
		Exp exp = (Exp) stkGrammar.pop();

		stkGrammar.push(new NonTerminal_Until(sym, exp));
	}

	private void nonTerminal_Repeat(ComplexSymbol sym) {
		Sequencia_sent sequencia_sent = (Sequencia_sent) stkGrammar.pop();

		stkGrammar.push(new NonTerminal_Repeat(sym, sequencia_sent));
	}

	private void nonTerminal_Else(ComplexSymbol sym) {
		Sequencia_sent sequencia_sent = (Sequencia_sent) stkGrammar.pop();

		NonTerminal_Else nonTerminal_Else = new NonTerminal_Else(sym,
				sequencia_sent);

		stkGrammar.push(nonTerminal_Else);
	}

	private void nonTerminal_Then(ComplexSymbol sym) {
		Sequencia_sent sequencia_sent = (Sequencia_sent) stkGrammar.pop();

		NonTerminal_Then nonTerminal_Then = new NonTerminal_Then(sym,
				sequencia_sent);

		stkGrammar.push(nonTerminal_Then);
	}

	private void nonTerminal_If(ComplexSymbol sym) {
		Exp exp = (Exp) stkGrammar.pop();

		NonTerminal_If nonTerminal_If = new NonTerminal_If(sym, exp);

		stkGrammar.push(nonTerminal_If);
	}

	private void sent_write(ComplexSymbol sym) {
		Exp exp = (Exp) stkGrammar.pop();

		Symbol WRITE = (Symbol) stkSymbol.elementAt(stkSymbol_Top - 1);// WRITE

		Sent_Write sent_Write = new Sent_Write(sym, WRITE, exp);

		stkGrammar.push(sent_Write);
	}

	private void sent_read(ComplexSymbol sym) {
		Symbol ID = (Symbol) stkSymbol.peek();// ID

		Symbol READ = (Symbol) stkSymbol.elementAt(stkSymbol_Top - 1);// READ

		Sent_Read sent_Read = new Sent_Read(sym, READ, ID);

		stkGrammar.push(sent_Read);
	}

	private void sent_assign(ComplexSymbol sym) {
		Exp exp = (Exp) stkGrammar.pop();

		Symbol OP_ATRIB = (Symbol) stkSymbol.elementAt(stkSymbol_Top - 1);// OP_ATRIB

		Symbol ID = (Symbol) stkSymbol.elementAt(stkSymbol_Top - 2);// ID

		Sent_Assign sent_Assign = new Sent_Assign(sym, ID, OP_ATRIB, exp);

		stkGrammar.push(sent_Assign);
	}

	private void sent_repeat(ComplexSymbol sym) {
		NonTerminal_Until nonTerminal_until = (NonTerminal_Until) stkGrammar
				.pop();

		NonTerminal_Repeat nonTerminal_repeat = (NonTerminal_Repeat) stkGrammar
				.pop();

		Sent_Repeat sent_Repeat = new Sent_Repeat(sym, nonTerminal_repeat,
				nonTerminal_until);

		stkGrammar.push(sent_Repeat);
	}

	private void sent_if(ComplexSymbol sym) {
		
		Sent_If sent_If = null;

		NonTerminal_If nonTerminal_if;

		NonTerminal_Then nonTerminal_then;

		NonTerminal_Else nonTerminal_else;

		Symbol END = (Symbol) stkSymbol.peek();// END;

		if (childs.length == 3) {// sent_if ::= if then END

			nonTerminal_then = (NonTerminal_Then) stkGrammar.pop();// then

			nonTerminal_if = (NonTerminal_If) stkGrammar.pop();// if

			sent_If = new Sent_If(sym, nonTerminal_if, nonTerminal_then, END);

		} else if (childs.length == 4) {// sent_if ::= if then else END

			nonTerminal_else = (NonTerminal_Else) stkGrammar.pop();

			nonTerminal_then = (NonTerminal_Then) stkGrammar.pop();// then

			nonTerminal_if = (NonTerminal_If) stkGrammar.pop();// if

			sent_If = new Sent_If(sym, nonTerminal_if, nonTerminal_then,
					nonTerminal_else, END);

		}

		stkGrammar.push(sent_If);
		
	}

	private void sent(ComplexSymbol sym) {
		
		Sent_node sent_node = null;

		if (childs[0].equalsIgnoreCase("sent_if")) {

			sent_node = (Sent_If) stkGrammar.pop();

		} else if (childs[0].equalsIgnoreCase("sent_repeat")) {

			sent_node = (Sent_Repeat) stkGrammar.pop();

		} else if (childs[0].equalsIgnoreCase("sent_assign")) {

			sent_node = (Sent_Assign) stkGrammar.pop();

		} else if (childs[0].equalsIgnoreCase("sent_read")) {

			sent_node = (Sent_Read) stkGrammar.pop();

		} else if (childs[0].equalsIgnoreCase("sent_write")) {

			sent_node = (Sent_Write) stkGrammar.pop();

		}

		Sent sent = new Sent(sym, sent_node);

		stkGrammar.push(sent);
		
	}

	private void sequencia_sent(ComplexSymbol sym) {
		
		Sequencia_sent sequencia_sent = null;

		if (childs.length == 3) { // sequencia_sent ::= sequencia_sent SEMI
									// sent

			Sent sent = (Sent) stkGrammar.pop();

			Sequencia_sent _sequencia_sent = (Sequencia_sent) stkGrammar.pop();

			Symbol semi = (Symbol) stkSymbol.elementAt(stkSymbol_Top - 1);// ID

			sequencia_sent = new Sequencia_sent(sym, _sequencia_sent, semi,
					sent);

		} else if (childs.length == 1) { // sequencia_sent ::= sent

			Sent sent = (Sent) stkGrammar.pop();

			sequencia_sent = new Sequencia_sent(sym, sent);

		}

		stkGrammar.push(sequencia_sent);
		
	}

	private void programa(ComplexSymbol sym) {
		
		Programa programa = new Programa(sym, (Sequencia_sent) stkGrammar.pop());

		stkGrammar.push(programa);

		Parser.setTree(stkGrammar.pop());
		
	}

	public void setStackSymbol(Stack<Symbol> stackSymbol, String childs) {

		this.stkSymbol = stackSymbol;
		
		stkSymbol_Top = stackSymbol.size() - 1;
		
		this.childs = childs.split(" ");

	}

}
