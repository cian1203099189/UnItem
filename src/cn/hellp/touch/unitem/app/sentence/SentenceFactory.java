package cn.hellp.touch.unitem.app.sentence;

import cn.hellp.touch.unitem.actuator.IActuator;
import cn.hellp.touch.unitem.actuator.block.SetBlockTypeActuator;
import cn.hellp.touch.unitem.actuator.entity.*;
import cn.hellp.touch.unitem.actuator.itemstack.SetItemStackTypeActuator;
import cn.hellp.touch.unitem.app.ActuatorList;
import cn.hellp.touch.unitem.app.PlaceholderManager;
import cn.hellp.touch.unitem.app.SentenceReader;
import cn.hellp.touch.unitem.app.sentence.builder.*;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.auxiliary.Pair;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.PlaceholderSelector;
import cn.hellp.touch.unitem.selector.ValueSelector;
import cn.hellp.touch.unitem.selector.block.GetBlockFromLocationSelector;
import cn.hellp.touch.unitem.selector.block.LookingBlockSelector;
import cn.hellp.touch.unitem.selector.entity.living.LastDamageSourceEntitySelector;
import cn.hellp.touch.unitem.selector.entity.living.NearByLivingEntitySelector;
import cn.hellp.touch.unitem.selector.entity.living.player.CallingPlayerSelector;
import cn.hellp.touch.unitem.selector.entity.living.player.LastDamageSourcePlayerSelector;
import cn.hellp.touch.unitem.selector.entity.living.player.OnlinePlayerSelector;
import cn.hellp.touch.unitem.selector.item.GetItemInHand;
import cn.hellp.touch.unitem.selector.location.LocationOfEntitySelector;
import cn.hellp.touch.unitem.selector.location.LocationValueSelector;
import cn.hellp.touch.unitem.selector.predicate.Equals;
import cn.hellp.touch.unitem.selector.predicate.Greater;
import cn.hellp.touch.unitem.selector.predicate.Less;
import cn.hellp.touch.unitem.selector.predicate.logical.And;
import cn.hellp.touch.unitem.selector.predicate.logical.Or;
import cn.hellp.touch.unitem.selector.predicate.logical.Un;
import cn.hellp.touch.unitem.selector.tools.*;
import cn.hellp.touch.unitem.selector.tools.block.GetBlockType;
import cn.hellp.touch.unitem.selector.tools.entity.GetDirection;
import cn.hellp.touch.unitem.selector.tools.entity.GetFoodLevelSelector;
import cn.hellp.touch.unitem.selector.tools.entity.GetHealthSelector;
import cn.hellp.touch.unitem.selector.tools.entity.GetVelocitySelector;
import cn.hellp.touch.unitem.selector.tools.item.GetItemType;
import cn.hellp.touch.unitem.selector.tools.location.XOf;
import cn.hellp.touch.unitem.selector.tools.location.YOf;
import cn.hellp.touch.unitem.selector.tools.location.ZOf;
import cn.hellp.touch.unitem.selector.tools.number.AddNumber;
import cn.hellp.touch.unitem.selector.tools.number.DividedNumber;
import cn.hellp.touch.unitem.selector.tools.number.MulNumber;
import cn.hellp.touch.unitem.selector.tools.number.SubNumber;
import cn.hellp.touch.unitem.selector.tools.player.PlayerNameSelector;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceFactory {
    public static final Map<String, ISelector<?>> keywordsMap = new HashMap<>();
    public static final Map<String, List<Constructor<?>>> selectorCreateMap = new HashMap<>();
    public static final Map<String, IActuator> actuatorMap = new HashMap<>();

    static {
        keywordsMap.put("owner_player", new CallingPlayerSelector());

        IActuator actuator;
        actuator = new KillEntityActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);
        actuator = new TeleportEntityActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);
        actuator = new SendMessageActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);
        actuator = new DamageEntityActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);
        actuator = new SetBlockTypeActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);
        actuator = new SetFoodLevelActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);
        actuator = new SetHealthActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);
        actuator = new SetItemStackTypeActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);
        actuator = new SetVelocityActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);

        Constructor<?> selectorConstructor;
        try {
            selectorConstructor = LastDamageSourceEntitySelector.class.getDeclaredConstructor();
            selectorCreateMap.put("lastDamageEntity", listOf(selectorConstructor));

            selectorConstructor = LastDamageSourcePlayerSelector.class.getDeclaredConstructor();
            selectorCreateMap.put("lastDamagePlayer", listOf(selectorConstructor));

            selectorConstructor = NearByLivingEntitySelector.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("nearLivingEntities", listOf(selectorConstructor));

            selectorConstructor = XOf.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("XOf", listOf(selectorConstructor));

            selectorConstructor = YOf.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("YOf", listOf(selectorConstructor));

            selectorConstructor = ZOf.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("ZOf", listOf(selectorConstructor));

            selectorConstructor = AddNumber.class.getDeclaredConstructor(ISelector.class, ISelector.class);
            selectorCreateMap.put("add", listOf(selectorConstructor));

            selectorConstructor = SubNumber.class.getDeclaredConstructor(ISelector.class, ISelector.class);
            selectorCreateMap.put("sub", listOf(selectorConstructor));

            selectorConstructor = GetFromIndexSelector.class.getDeclaredConstructor(ISelector.class, ValueSelector.class);
            selectorCreateMap.put("getFromIndex", listOf(selectorConstructor));

            selectorConstructor = LocationOfEntitySelector.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("locationOfEntity", listOf(selectorConstructor));

            selectorConstructor = GetFoodLevelSelector.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getFoodLevel", listOf(selectorConstructor));

            selectorConstructor = GetHealthSelector.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getHealth", listOf(selectorConstructor));

            selectorConstructor = GetVelocitySelector.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getVelocity", listOf(selectorConstructor));

            selectorConstructor = ToStringSelector.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("toString", listOf(selectorConstructor));

            selectorConstructor = LocationValueSelector.class.getDeclaredConstructor(ISelector.class, ISelector.class, ISelector.class, ISelector.class);
            Constructor<?> selectorConstructor1 = LocationValueSelector.class.getDeclaredConstructor(ISelector.class, ISelector.class, ISelector.class);
            selectorCreateMap.put("locationOf", listOf(selectorConstructor, selectorConstructor1));

            selectorConstructor = OnlinePlayerSelector.class.getDeclaredConstructor();
            selectorCreateMap.put("onlinePlayers", listOf(selectorConstructor));

            selectorConstructor = LookingBlockSelector.class.getDeclaredConstructor();
            selectorCreateMap.put("lookingBlock", listOf(selectorConstructor));

            selectorConstructor = GetBlockFromLocationSelector.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getBlockFromLocation", listOf(selectorConstructor));

            selectorConstructor = GetDirection.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getDirection", listOf(selectorConstructor));

            selectorConstructor = GetItemInHand.class.getDeclaredConstructor();
            selectorConstructor1 = GetItemInHand.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getItemInHand", listOf(selectorConstructor, selectorConstructor1));

            selectorConstructor = GetBlockType.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getBlockType", listOf(selectorConstructor));

            selectorConstructor = GetItemType.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getItemType", listOf(selectorConstructor));

            selectorConstructor = VectorToLocationSelector.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("vecToLocation", listOf(selectorConstructor));

            selectorConstructor = PlayerNameSelector.class.getDeclaredConstructor(ISelector.class);
            selectorConstructor1 = PlayerNameSelector.class.getDeclaredConstructor();
            selectorCreateMap.put("getPlayerName", listOf(selectorConstructor, selectorConstructor1));
        } catch (NoSuchMethodException e) {
            throw new ERROR(e);
        }
    }

    public final List<SentenceBuilder> builderList = new ArrayList<>();
    private final PlaceholderManager manager;
    private final ActuatorList list;
    private final Stack<Pair<SentenceBuilder, Result>> builderStack = new Stack<>();
    private SentenceBuilder last = null;

    public SentenceFactory(PlaceholderManager manager, ActuatorList list) {
        this.manager = manager;
        this.list = list;
        builderList.add(new ActuateBuilder());
        builderList.add(new AssignBuilder());
        builderList.add(new ElseBuilder());
        builderList.add(new ForBuilder());
        builderList.add(new IfBuilder());
        builderList.add(new ScopeBuilder());
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... eme) {
        List<T> result = new ArrayList<>();
        Collections.addAll(result, eme);
        return result;
    }

    public static String[] splitPragma(String s) {
        int pointer = 0;
        int latest = 0;
        int i = 0;
        int b = 0;
        boolean inString = false;
        char strChar = '\"';
        final int length = s.length();
        List<String> result = new ArrayList<>();
        while (pointer < length) {
            char c = s.charAt(pointer);
            if (!inString) {
                if ('(' == c) {
                    ++i;
                } else if (',' == c && i == 0 && b == 0) {
                    result.add(s.substring(latest, pointer));
                    latest = pointer + 1;
                } else if (')' == c) {
                    --i;
                } else if ('{' == c) {
                    ++b;
                } else if ('}' == c) {
                    --b;
                } else if ('\"' == c || '\'' == c) {
                    inString = true;
                    strChar = c;
                }
            } else if (strChar == c) {
                inString = false;
            }
            if (i < 0 || b < 0) {
                throw new ERROR("wrong config of line " + s);
            }
            ++pointer;
        }
        result.add(s.substring(latest, length));
        return result.toArray(new String[0]);
    }

    public static ISelector<?> newInstance(String name, Object[] objs) {
        List<Constructor<?>> constructors = selectorCreateMap.get(name);
        if (constructors == null) {
            throw new ERROR("Unknown selector " + name);
        }
        for (Constructor<?> constructor : constructors) {
            try {
                return (ISelector<?>) constructor.newInstance(objs);
            } catch (Exception ignored) {
            }
        }
        throw new ERROR("can't create selector \"" + name + "\" from " + Arrays.toString(objs));
    }

    public static ValueSelector<?> getLiteral(String s) {
        s = s.trim();
        Pattern string = Pattern.compile("((\"(?<value>(.*)?))\")");
        Pattern string1 = Pattern.compile("((')(?<value>.*)?('))");
        Matcher matcher;
        if ((matcher = string.matcher(s)).matches() || (matcher = string1.matcher(s)).matches()) {
            return new ValueSelector<>(matcher.group("value"));
        }
        if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
            return new ValueSelector<>(Boolean.parseBoolean(s));
        }
        try {
            return new ValueSelector<>(new Number(s));
        } catch (Exception ignored) {
        }
        return new ValueSelector<>(s);
    }

    public static ISelector<?> getValue(String s, PlaceholderManager manager) {
        s = s.trim();
        Pattern pattern = Pattern.compile("\\((?<body>.*)\\)");
        Matcher matcher;
        if ((matcher = pattern.matcher(s)).matches()) {
            s = matcher.group("body");
        }
        Pattern string = Pattern.compile("((\"(?<value>([^\"]*)))\")");
        Pattern string1 = Pattern.compile("(('(?<value>([^\"]*)))')");
        if ((matcher = string.matcher(s)).matches() || (matcher = string1.matcher(s)).matches()) {
            return new ValueSelector<>(matcher.group("value"));
        }
        Pattern placeholder = Pattern.compile("\\$(?<placeholder>(\\w)*)");
        Pattern selector = Pattern.compile("(?<selectorName>\\w*?)\\((?<pragma>.*)\\)");
        Pattern locPattern = Pattern.compile("\\{(?<arrays>.*?)}");
        Pattern add = Pattern.compile("(?<left>[\\s\\S]+?)\\+(?<right>[\\s\\S]+)");
        Pattern sub = Pattern.compile("(?<left>[\\s\\S]+?)-(?<right>[\\s\\S]+)");
        Pattern mul = Pattern.compile("(?<left>[\\s\\S]+?)\\+(?<right>[\\s\\S]+)");
        Pattern divided = Pattern.compile("(?<left>[\\s\\S]+?)\\+(?<right>[\\s\\S]+)");
        Pattern greater = Pattern.compile("(?<left>[\\s\\S]+?)>(?<right>[\\s\\S]+)");
        Pattern less = Pattern.compile("(?<left>[\\s\\S]+?)<(?<right>[\\s\\S]+)");
        Pattern equals = Pattern.compile("(?<left>[\\s\\S]+?)==(?<right>[\\s\\S]+)");
        Pattern un = Pattern.compile("!(?<predicate>[\\s\\S]+)");
        Pattern and = Pattern.compile("(?<left>[\\s\\S]+?)&(?<right>[\\s\\S]+)");
        Pattern or = Pattern.compile("(?<left>[\\s\\S]+?)\\|(?<right>[\\s\\S]+)");
        Pattern greater_equals = Pattern.compile("(?<left>[\\s\\S]+?)>=(?<right>[\\s\\S]+)");
        Pattern less_equals = Pattern.compile("(?<left>[\\s\\S]+?)<=(?<right>[\\s\\S]+)");
        Pattern un_equals = Pattern.compile("(?<left>[\\s\\S]+?)!=(?<right>[\\s\\S]+)");
        if ((matcher = locPattern.matcher(s)).matches()) {
            try {
                ISelector<?>[] arr = handlePragma(matcher.group("arrays"), manager);
                return new ArraySelector(arr);
            } catch (Exception e) {
                throw new ERROR("can't create arrays from " + s, e);
            }
        } else if ((matcher = greater.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"), manager);
            ISelector<?> two = getValue(matcher.group("right"), manager);
            return new Greater(one, two);
        } else if ((matcher = less.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"), manager);
            ISelector<?> two = getValue(matcher.group("right"), manager);
            return new Less(one, two);
        } else if ((matcher = equals.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"), manager);
            ISelector<?> two = getValue(matcher.group("right"), manager);
            return new Equals(one, two);
        } else if ((matcher = and.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"), manager);
            ISelector<?> two = getValue(matcher.group("right"), manager);
            return new And(one, two);
        } else if ((matcher = or.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"), manager);
            ISelector<?> two = getValue(matcher.group("right"), manager);
            return new Or(one, two);
        } else if ((matcher = greater_equals.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"), manager);
            ISelector<?> two = getValue(matcher.group("right"), manager);
            return new Or(new Greater(one, two), new Equals(one, two));
        } else if ((matcher = less_equals.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"), manager);
            ISelector<?> two = getValue(matcher.group("right"), manager);
            return new Or(new Less(one, two), new Equals(one, two));
        } else if ((matcher = un_equals.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"), manager);
            ISelector<?> two = getValue(matcher.group("right"), manager);
            return new Un(new Equals(one, two));
        } else if ((matcher = add.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"), manager);
            ISelector<?> two = getValue(matcher.group("right"), manager);
            return new AddNumber(one, two);
        } else if ((matcher = sub.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"), manager);
            ISelector<?> two = getValue(matcher.group("right"), manager);
            return new SubNumber(one, two);
        }else if ((matcher = mul.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"), manager);
            ISelector<?> two = getValue(matcher.group("right"), manager);
            return new MulNumber(one, two);
        } else if ((matcher = divided.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"), manager);
            ISelector<?> two = getValue(matcher.group("right"), manager);
            return new DividedNumber(one, two);
        } else if ((matcher = un.matcher(s)).matches()) {
            ISelector<?> predicate = getValue(matcher.group("predicate"), manager);
            return new Un(predicate);
        } else if ((matcher = selector.matcher(s)).matches()) {
            return newInstance(matcher.group("selectorName"), handlePragma(matcher.group("pragma"), manager));
        } else if ((matcher = placeholder.matcher(s)).matches()) {
            if(!manager.contains(matcher.group("placeholder"))) {
                throw new ERROR("\""+matcher.group("placeholder")+"\" var not found");
            }
            return new PlaceholderSelector(matcher.group("placeholder"), manager);
        } else if (keywordsMap.containsKey(s)) {
            return keywordsMap.get(s);
        } else {
            return getLiteral(s);
        }
    }

    public static ISelector<?>[] handlePragma(String pragma, PlaceholderManager manager) {
        pragma = pragma.trim();
        if (pragma.isEmpty()) {
            return new ISelector<?>[0];
        }
        String[] raw = splitPragma(pragma);
        ISelector<?>[] result = new ISelector<?>[raw.length];
        for (int i = 0; i < raw.length; i++) {
            String s = raw[i];
            result[i] = getValue(s, manager);
        }
        return result;
    }


    //history version
    /*public @Nullable Sentence create(String raw) {
        if (raw.isEmpty()) {
            return null;
        }
        raw = raw.trim();
        Pattern assign = Pattern.compile("\\$(?<placeholder>\\w+)?=(?<value>.+)");
        Pattern actuate = Pattern.compile("#(?<actuator>\\w+)?\\((?<pragma>.*)\\)");
        Pattern for_ = Pattern.compile("(\\s*)?for(\\s+)(?<placeholder>\\S+?)(\\s+)in(\\s+)range(\\s*)\\((?<range>(.|\\s)*?)\\)(\\s*)\\{(?<body>([\\s\\S]*))}");
        Pattern if_ = Pattern.compile("if(\\s)*\\((?<predicate>(.|\\n)*)\\)(\\s)*(?<body>[\\s\\S]*)");
        Pattern scope = Pattern.compile("\\{(?<body>[\\s\\S]*)}");
        Pattern else_ = Pattern.compile("else(?<body>[\\s\\S]*)");
        Matcher matcher;
        if ((matcher = assign.matcher(raw)).matches()) {
            String name = matcher.group("placeholder");
            AssignSentence sentence = new AssignSentence(manager, list);
            sentence.init(name, getValue(matcher.group("value"),manager));
            return sentence;
        } else if ((matcher = actuate.matcher(raw)).matches()) {
            ActuateSentence sentence = new ActuateSentence(manager, list);
            IActuator actuator = actuatorMap.get(matcher.group("actuator"));
            if (actuator == null) {
                throw new ERROR("Unknown actuator " + matcher.group("actuator"));
            }
            ISelector<?>[] pragma = handlePragma(matcher.group("pragma"),manager);
            sentence.init(actuator, pragma);
            return sentence;
        } else if ((matcher = scope.matcher(raw)).matches()) {
            PlaceholderManager manager1 = manager.clone();
            ActuatorList list1 = splitAndCreateSentences(manager1,matcher.group("body"));
            ScopeActuatorSentence sentence = new ScopeActuatorSentence(manager1,list);
            sentence.init((Object[]) list1.toArray(new CallableActuator[0]));
            return sentence;
        } else if ((matcher = for_.matcher(raw)).matches()) {
            try {
                String placeholder = matcher.group("placeholder");
                ISelector<?>[] range = handlePragma(matcher.group("range"),manager);
                PlaceholderManager manager1 = manager.clone();
                ActuatorList list1 = splitAndCreateSentences(manager1,matcher.group("body"));
                ForActuatorSentence sentence = new ForActuatorSentence(manager1, list);
                sentence.init(placeholder, manager1, range[0], range[1], list1.toArray(new CallableActuator[0]));
                return sentence;
            } catch (Exception e) {
                throw new ERROR(e);
            }
        } else if ((matcher = if_.matcher(raw)).matches()) {
            try {
                ISelector<?> predicate = getValue(matcher.group("predicate"),manager);
                PlaceholderManager manager1 = manager.clone();
                ActuatorList list1 = splitAndCreateSentences(manager1, matcher.group("body"));
                IfActuatorSentence sentence = new IfActuatorSentence(manager1, list);
                sentence.init(new CallableActuator[0],predicate, list1.toArray(new CallableActuator[0]));
                this.sentence.push(sentence);
                return sentence;
            } catch (Exception e) {
                throw new ERROR(e);
            }
        } else if ((matcher = else_.matcher(raw)).matches()) {
            if(!sentence.empty()) {
                PlaceholderManager manager1 = manager.clone();
                ActuatorList list1 = splitAndCreateSentences(manager1, matcher.group("body"));
                IfActuatorSentence ifActuatorSentence = null;
                if (!list1.empty()) {
                    ifActuatorSentence = sentence.pop();
                    ifActuatorSentence.setActuator(list1.toArray(new CallableActuator[0]));
                }
                return ifActuatorSentence;
            } else {
                throw new ERROR("Can't use else without if.");
            }
        }
        throw new ERROR("Unknown sentence " + raw);
    }*/

    public static ActuatorList splitAndCreateSentences(PlaceholderManager manager, String raw) {
        String[] bodies = SentenceReader.splitSentences(raw);
        ActuatorList list1 = new ActuatorList();
        SentenceFactory factory = new SentenceFactory(manager, list1);
        for (String body : bodies) {
            factory.create(body);
        }
        factory.cleanUp();
        return list1;
    }

    public void create(String s) {
        s = s.trim();
        if (s.isEmpty()) {
            return;
        }
        for (SentenceBuilder builder : builderList) {
            if (builder.matches(s)) {
                if (!builderStack.empty()) {
                    Pair<SentenceBuilder, Result> last = builderStack.pop();
                    Result result;
                    if (last.getSecond().hasNeed()) {
                        if ((last.getSecond().need.matcher(s)).matches()) {
                            result = last.getFirst().callback(CallbackReason.COMPLETE, s, manager);
                        } else {
                            result = last.getFirst().callback(CallbackReason.UNMATCHED, s, manager);
                        }
                    } else {
                        result = last.getFirst().callback(CallbackReason.VOID, s, manager);
                    }
                    this.last = last.getFirst();
                    if (result.hasActuator()) {
                        list.push_back(result.actuator);
                    }
                    if (result.type == ResultType.REMATCH) {
                        create(s);
                    }
                    return;
                }
                Result result = builder.create(s, manager, list, last);
                last = builder;
                if (result.hasActuator()) {
                    list.push_back(result.actuator);
                }
                if (result.type == ResultType.UNDONE) {
                    builderStack.push(new Pair<>(builder, result));
                } else if (result.type == ResultType.REMATCH) {
                    continue;
                }
                return;
            }
        }
        throw new ERROR("can't phase sentence " + s);
    }

    public void cleanUp() {
        builderStack.forEach(sentenceBuilderResultPair -> {
            sentenceBuilderResultPair.getFirst().callback(CallbackReason.UNMATCHED, "", manager);
        });
    }
}
