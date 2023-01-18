package cn.hellp.touch.unitem.app.parser.statement;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.ParseReader;
import cn.hellp.touch.unitem.app.parser.Parser;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.app.parser.node.LiteralNode;
import cn.hellp.touch.unitem.app.parser.parser.StatementParser;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.selector.ISelector;

import java.util.ArrayList;
import java.util.List;


public class ArgumentNode implements Parser, CodeNode {
	private List<CodeNode> subnodes = new ArrayList<>();
	char first;

	public ArgumentNode(char first) {
		this.first = first;
	}

	@Override
	public CodeNode parser(ParseReader reader) {
		StatementParser parser = new StatementParser();
		while (true) {
			if (!reader.has()) {
				break;
			}
			char c = reader.eatAllSpace();
			if (!reader.has()) {
				break;
			}
			subnodes.add(parser.parseUntil(reader, ',', ';', ')'));

			if (reader.read() == ',') {
				reader.eat();
				continue;
			}
			if (reader.read() == ';')
				if (first == ';') {
					break;
				} else
					throw new ERROR("错误的;");
			if (reader.read() == ')') {
				if (first == '(') {
					reader.eat();
				}
				break;
			}
		}
		return this;
	}

	@Override
	public String toString() {
		String ret = "";
		ret += subnodes.get(0).toString();
		if (subnodes.size() > 1) {
			for (int i = 1; i < subnodes.size(); i++) {
				ret += ',';
				ret += subnodes.get(i).toString();
			}
		}
		return ret;
	}

	public List<CodeNode> getAll() {
		return subnodes;
	}


	@Override
	public ISelector<?> eval(UEnv env) {
		ISelector<?> res = null;
		for (CodeNode subnode : subnodes) {
			res = subnode.eval(env);
		}
		return res;
	}

}
