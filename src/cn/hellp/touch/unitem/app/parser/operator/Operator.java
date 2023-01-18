package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.CodeNode;

public interface Operator extends CodeNode {

	int getPriority();

	Associative getAssociative();

	int getOperandCount();

	void setChildren(CodeNode... codeNodes);
}

