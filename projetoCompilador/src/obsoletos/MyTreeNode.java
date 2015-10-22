package obsoletos;

import java.util.Stack;

import java_cup.runtime.Symbol;

import javax.swing.tree.MutableTreeNode;

public class MyTreeNode {

	private Stack<MyDefaultMutableTreeNode> stackNodes = new Stack<MyDefaultMutableTreeNode>();

	public void addNonTerminal(Symbol nonTerminalSymbol, int childs) {

		MyDefaultMutableTreeNode node = newNode(nonTerminalSymbol);

		int y = childs;

		int z = stackNodes.size();

		while (childs > 0) {

			node.add((MutableTreeNode) stackNodes.toArray()[z - childs]);

			childs--;

		}

		while (y > 0) {

			stackNodes.pop();

			y--;

		}

		stackNodes.push(node);

	}

	public void addTerminal(java_cup.runtime.Symbol CUP$Parser$result) {

		MyDefaultMutableTreeNode node = newNode(CUP$Parser$result);

		stackNodes.push(node);

	}

	@SuppressWarnings("rawtypes")
	public void addTerminal(java.util.Stack CUP$Parser$stack,
			java_cup.runtime.Symbol CUP$Parser$result) {

		MyDefaultMutableTreeNode node = newNode(CUP$Parser$result);

		node.add(newNode((java_cup.runtime.Symbol) CUP$Parser$stack.peek()));

		stackNodes.push(node);

	}

	private MyDefaultMutableTreeNode newNode(
			java_cup.runtime.Symbol CUP$Parser$result) {

		return null;// new
					// MyDefaultMutableTreeNode(nodeName(CUP$Parser$result));

	}

	@SuppressWarnings("unused")
	private String nodeName(java_cup.runtime.Symbol CUP$Parser$result) {

		return CUP$Parser$result.toString().split("Symbol: ")[1];

	}

	public void visualizarStack() {
		System.out.println("\nStack:\t");
		for (Object obj : stackNodes.toArray()) {

			System.out.print(((MyDefaultMutableTreeNode) obj).toString()
					+ " | ");

		}
		System.out
				.println("\n---------------------------------------------------------------------------\n");
	}

	public MyDefaultMutableTreeNode get() {
		return stackNodes.pop();
	}

}
