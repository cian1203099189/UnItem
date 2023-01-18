package cn.hellp.touch.unitem.app.parser.statement;

import cn.hellp.touch.unitem.actuator.IActuator;
import cn.hellp.touch.unitem.app.CallableActuator;
import cn.hellp.touch.unitem.app.Registry;
import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.ParseReader;
import cn.hellp.touch.unitem.app.parser.Parser;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.app.parser.operator.Associative;
import cn.hellp.touch.unitem.app.parser.operator.Operator;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.selector.EmptySelector;
import cn.hellp.touch.unitem.selector.ISelector;

import java.util.List;


public class FuncCall implements Parser,CodeNode,Operator {
	CodeNode[] args;
	private String func;

	public FuncCall() {
	}

	@Override
	public ISelector<?> eval(UEnv env) {
		IActuator actuator = Registry.ACTUATOR.get(func);
		if(actuator==null){
			throw new ERROR("Unknown actuator \""+func+"\"");
		}
		ISelector<?>[] args0 = new ISelector[args.length];
		for (int i = 0; i < args0.length; i++) {
			args0[i]=args[i].eval(env);
		}
		new CallableActuator(actuator,args0).call(env.getCaller());
		return new EmptySelector();
	}


	@Override
	public CodeNode parser(ParseReader reader) {
		StringBuilder builder = new StringBuilder();
		char c = reader.read();
		do {
			builder.append(c);
		} while ((c=reader.eat())!='(' && c!=';');
		reader.eat();
		func = builder.toString();
		ArgumentNode an = new ArgumentNode('(');
		an.parser(reader);
		List<CodeNode> cns = an.getAll();
		if (cns.size() != 0) {
			args = cns.toArray(new CodeNode[0]);
		}
		return this;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public Associative getAssociative() {
		return Associative.LEFT;
	}

	@Override
	public int getOperandCount() {
		return 0;
	}

	@Override
	public void setChildren(CodeNode... codeNodes) {
		throw new ERROR("Unexpected call");
	}
}
