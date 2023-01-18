package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.selector.ISelector;

public abstract class SingleOperator implements Operator {
	protected CodeNode Child;

	public SingleOperator() {
	}

	@Override
	public abstract ISelector<?> eval(UEnv env);

	@Override
	public abstract int getPriority();

	@Override
	public abstract Associative getAssociative();

	@Override
	public int getOperandCount() {
		return 1;
	}

	@Override
	public void setChildren(CodeNode... codeNodes) {
		if (codeNodes[0] != null) {
			if (getAssociative() == Associative.RIGHT) {
				Child = codeNodes[0];
			} else
				throw new ERROR("Unexpected 'operator' position,expected ';'");
		}
		if (codeNodes.length > 1 && codeNodes[1] != null) {
			if (getAssociative() == Associative.LEFT) {
				Child = codeNodes[1];
			} else
				throw new ERROR("Unexpected 'operator' position,expected ';'");
		}
	}
}
