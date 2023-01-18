package cn.hellp.touch.unitem.app.parser.statement;

import cn.hellp.touch.unitem.app.parser.*;
import cn.hellp.touch.unitem.app.parser.parser.StatementParser;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.selector.ISelector;

import java.util.List;


/**
 * @author khjxiaogu
 * @time 2020年2月19日
 */
public class Parentness implements CodeNode, Parser, Assignable {
	CodeNode inner;

	/**
	 *
	 */
	public Parentness() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ISelector<?> eval(UEnv env) {
		// TODO Auto-generated method stub
		return inner.eval(env);
	}

	@Override
	public CodeNode parser(ParseReader reader) {
		// TODO Auto-generated method stub
		StatementParser sp = new StatementParser();
		inner = sp.parseUntil(reader, ')');
		reader.eat();
		return this;
	}

	@Override
	public String toString() {
		return inner.toString();
	}

	@Override
	public ISelector<?> assign(UEnv env, ISelector<?> val) {
		// TODO Auto-generated method stub
		if (inner instanceof Assignable)
			return ((Assignable) inner).assign(env, val);
		throw new ERROR(inner.toString());
	}

}
