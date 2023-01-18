package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;


public abstract class DoubleOperator implements Operator {
	protected CodeNode left;
	protected CodeNode right;

	public DoubleOperator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract ISelector<?> eval(UEnv env);

	@Override
	public abstract int getPriority();

	@Override
	public Associative getAssociative() {
		return Associative.RIGHT;
	}

	@Override
	public int getOperandCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	public abstract String getToken();

	@Override
	public String toString() {
		return "(" + left.toString() + getToken() + right.toString() + ")";
	}

	@Override
	public void setChildren(CodeNode... codeNodes) {
		// TODO Auto-generated method stub
		if (codeNodes[0] != null) {
			left = codeNodes[0];
		}
		if (codeNodes.length > 1 && codeNodes[1] != null) {
			right = codeNodes[1];
		}
	}

}
