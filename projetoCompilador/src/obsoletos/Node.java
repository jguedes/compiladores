package obsoletos;

import java.util.HashMap;

import java_cup.runtime.Symbol;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class Node {

	private Symbol node;

	// private String nodeValue;

	private HashMap<Integer, Node> childs;

	public Node(Symbol node) {

		this.node = node;
		// nodeValue = "";

	}

	// public String getNodeValue() {
	// return nodeValue;
	// }
	//
	// public void setNodeValue(String nodeValue) {
	// this.nodeValue = nodeValue;
	// }

	public HashMap<Integer, Node> getChilds() {
		if (childs == null)
			childs = new HashMap<Integer, Node>();
		return childs;
	}

	public void setChilds(HashMap<Integer, Node> child) {
		this.childs = child;
	}

	// public String getNodeName() {
	// return nodeName;
	// }
	//
	// public void setNodeName(String nodeName) {
	// this.nodeName = nodeName;
	// }

	@Override
	public String toString() {

		JTree jtree = new JTree();
		jtree.setVisible(true);

		StringBuilder s = new StringBuilder();

		s.append(node);

		s.append(":\"" + node.value + "\"");

		for (Integer key : childs.keySet()) {

			s.append("-> " + childs.get(key).toString());

		}

		return s.toString();
	}

	public DefaultMutableTreeNode toTreeNode() {

		DefaultMutableTreeNode nodeTree = new DefaultMutableTreeNode(node);

		nodeTree.add(new DefaultMutableTreeNode(node.value));

		if (childs != null) {
			for (Integer key : childs.keySet()) {

				nodeTree.add(childs.get(key).toTreeNode());

			}
		}
		return nodeTree;

	}
	//
	// int i = z;
	//
	// StringBuilder s = new StringBuilder();
	// //
	// s.append(nodeName);
	//
	// if (nodeValue.length() > 0) {
	//
	// s.append(":\"" + nodeValue + "\"");
	//
	// }
	// //
	// if (childs != null) {
	//
	// s.append("\n\t");
	//
	// while (i > 0) {
	// s.append("\t");
	// i--;
	// }
	//
	// s.append("-> " + childs.toString(++z));
	// }
	//
	// return s.toString();
	// }
}
