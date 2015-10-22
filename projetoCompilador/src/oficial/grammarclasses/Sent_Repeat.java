package oficial.grammarclasses;

import java_cup.runtime.Symbol;

public class Sent_Repeat extends Sent_node {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sent_Repeat(Symbol sent_repeat,
			NonTerminal_Repeat nonTerminal_repeat,
			NonTerminal_Until nonTerminal_until) {
		super(sent_repeat);

		add(nonTerminal_until);

		add(nonTerminal_repeat);

	}
}
