package cn.hellp.touch.unitem.app.sentence;

import cn.hellp.touch.unitem.actuator.IActuator;
import cn.hellp.touch.unitem.actuator.block.SetBlockTypeActuator;
import cn.hellp.touch.unitem.actuator.entity.*;
import cn.hellp.touch.unitem.actuator.itemstack.SetItemStackTypeActuator;
import cn.hellp.touch.unitem.app.*;
import cn.hellp.touch.unitem.auxiliary.ERROR;
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
import cn.hellp.touch.unitem.selector.tools.number.SubNumber;
import cn.hellp.touch.unitem.selector.tools.string.ConcatString;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceFactory {
    public static final Map<String, ISelector<?>> keywordsMap = new HashMap<>();
    public static final Map<String,List<Constructor<?>>> selectorCreateMap = new HashMap<>();
    public static final Map<String, IActuator> actuatorMap = new HashMap<>();
    
    @SafeVarargs
    public static <T> List<T> listOf(T... eme) {
        List<T> result = new ArrayList<>();
        Collections.addAll(result,eme);
        return result;
    }

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
        actuator=new SetBlockTypeActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);
        actuator=new SetFoodLevelActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);
        actuator=new SetHealthActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);
        actuator=new SetItemStackTypeActuator();
        actuatorMap.put(actuator.actuatorID(), actuator);
        actuator=new SetVelocityActuator();
        actuatorMap.put(actuator.actuatorID(),actuator);

        Constructor<?> selectorConstructor;
        try {
            selectorConstructor = LastDamageSourceEntitySelector.class.getDeclaredConstructor();
            selectorCreateMap.put("lastDamageEntity", listOf(selectorConstructor));

            selectorConstructor = LastDamageSourcePlayerSelector.class.getDeclaredConstructor();
            selectorCreateMap.put("lastDamagePlayer", listOf(selectorConstructor));

            selectorConstructor = NearByLivingEntitySelector.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("nearLivingEntities", listOf(selectorConstructor));

            selectorConstructor = XOf.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("XOf",  listOf(selectorConstructor));

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

            selectorConstructor = LocationValueSelector.class.getDeclaredConstructor(ISelector.class,ISelector.class,ISelector.class,ISelector.class);
            Constructor<?> selectorConstructor1 = LocationValueSelector.class.getDeclaredConstructor(ISelector.class,ISelector.class,ISelector.class);
            selectorCreateMap.put("locationOf",listOf(selectorConstructor,selectorConstructor1));

            selectorConstructor = OnlinePlayerSelector.class.getDeclaredConstructor();
            selectorCreateMap.put("onlinePlayers",listOf(selectorConstructor));

            selectorConstructor = LookingBlockSelector.class.getDeclaredConstructor();
            selectorCreateMap.put("lookingBlock",listOf(selectorConstructor));

            selectorConstructor = GetBlockFromLocationSelector.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getBlockFromLocation",listOf(selectorConstructor));

            selectorConstructor = GetDirection.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getDirection",listOf(selectorConstructor));

            selectorConstructor = GetItemInHand.class.getDeclaredConstructor();
            selectorConstructor1 = GetItemInHand.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getItemInHand",listOf(selectorConstructor,selectorConstructor1));

            selectorConstructor = GetBlockType.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getBlockType",listOf(selectorConstructor));

            selectorConstructor = GetItemType.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("getItemType",listOf(selectorConstructor));

            selectorConstructor = ConcatString.class.getDeclaredConstructor(ISelector.class,ISelector.class);
            selectorCreateMap.put("concatString",listOf(selectorConstructor));

            selectorConstructor = VectorToLocationSelector.class.getDeclaredConstructor(ISelector.class);
            selectorCreateMap.put("vecToLocation",listOf(selectorConstructor));
        } catch (NoSuchMethodException e) {
            throw new ERROR(e);
        }
    }

    private final PlaceholderManager manager;
    private final ActuatorList list;

    public SentenceFactory(PlaceholderManager manager, ActuatorList list) {
        this.manager = manager;
        this.list = list;
    }

    private static String[] splitPragma(String s) {
        int pointer = 0;
        int latest = 0;
        int i = 0;
        int b = 0;
        final int length = s.length();
        List<String> result = new ArrayList<>();
        while (pointer < length) {
            char c = s.charAt(pointer);
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
            }
            if (i < 0 || b < 0) {
                throw new ERROR("wrong config of line " + s);
            }
            ++pointer;
        }
        result.add(s.substring(latest, length));
        return result.toArray(new String[0]);
    }

    public ValueSelector<?> getLiteral(String s) {
        s = s.trim();
        Pattern string = Pattern.compile("((\"(?<value>(.*)?))\")");
        Pattern string1 = Pattern.compile("((')(?<value>.*)?('))");
        Matcher matcher;
        if ((matcher = string.matcher(s)).matches() || (matcher = string1.matcher(s)).matches()) {
            return new ValueSelector<>(matcher.group("value"));
        }
        try {
            return new ValueSelector<>(Integer.parseInt(s));
        } catch (Exception ignored) {
        }
        try {
            return new ValueSelector<>(Double.parseDouble(s));
        } catch (Exception ignored) {
        }
        try {
            return new ValueSelector<>(Float.parseFloat(s));
        } catch (Exception ignored) {
        }
        try {
            return new ValueSelector<>(Long.parseLong(s));
        } catch (Exception ignored) {
        }
        if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
            return new ValueSelector<>(Boolean.parseBoolean(s));
        }
        return new ValueSelector<>(s);
    }

    public static ISelector<?> newInstance(String name,Object[] objs) {
        List<Constructor<?>> constructors = selectorCreateMap.get(name);
        if(constructors==null) {
            throw new ERROR("Unknown selector "+name);
        }
        for (Constructor<?> constructor : constructors) {
            try {
                return (ISelector<?>) constructor.newInstance((Object[]) objs);
            } catch (Exception ignored) {}
        }
        throw new ERROR("can't create selector \""+name+"\" from "+ Arrays.toString(objs));
    }

    private ISelector<?> getValue(String s) {
        s = s.trim();
        Pattern placeholder = Pattern.compile("\\$(?<placeholder>(\\w)*)");
        Pattern selector = Pattern.compile("(?<selectorName>\\w*)?\\((?<pragma>.*)\\)");
        Pattern actuate = Pattern.compile("#(?<actuator>\\w+)?\\((?<pragma>.*)\\)");
        Pattern locPattern = Pattern.compile("\\{((?<arrays>.*)?)\\}");
        Pattern greater = Pattern.compile("(?<left>[\\s\\S]+)>(?<right>[\\s\\S]+)");
        Pattern less = Pattern.compile("(?<left>[\\s\\S]+)<(?<right>[\\s\\S]+)");
        Pattern equals = Pattern.compile("(?<left>[\\s\\S]+)==(?<right>[\\s\\S]+)");
        Pattern un = Pattern.compile("!(?<predicate>[\\s\\S]+)");
        Matcher matcher;
        if ((matcher = placeholder.matcher(s)).matches()) {
            return new PlaceholderSelector(matcher.group("placeholder"),manager);
        } else if ((matcher = selector.matcher(s)).matches()) {
            return newInstance(matcher.group("selectorName"), handlePragma(matcher.group("pragma")));
        } else if ((matcher = actuate.matcher(s)).matches()) {
            IActuator actuator = actuatorMap.get(matcher.group("actuator"));
            if (actuator != null) {
                return new ActuatorSelector(actuator, handlePragma(matcher.group("pragma")));
            }
        }else if ((matcher = locPattern.matcher(s)).matches()) {
            try {
                ISelector<?>[] arr = handlePragma(matcher.group("arrays"));
                return new ArraySelector<>(arr);
            } catch (Exception e) {
                throw new ERROR("can't create arrays from " + s, e);
            }
        } else if ((matcher=greater.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"));
            ISelector<?> two = getValue(matcher.group("right"));
            return new Greater(one,two);
        } else if ((matcher=less.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"));
            ISelector<?> two = getValue(matcher.group("right"));
            return new Less(one,two);
        } else if ((matcher=equals.matcher(s)).matches()) {
            ISelector<?> one = getValue(matcher.group("left"));
            ISelector<?> two = getValue(matcher.group("right"));
            return new Equals(one,two);
        } else if ((matcher = un.matcher(s)).matches()) {
            ISelector<?> predicate = getValue(matcher.group("predicate"));
            return new Un(predicate);
        }
        if (keywordsMap.containsKey(s)) {
            return keywordsMap.get(s);
        } else {
            return getLiteral(s);
        }
    }

    private ISelector<?>[] handlePragma(String pragma) {
        pragma = pragma.trim();
        if (pragma.isEmpty()) {
            return new ISelector<?>[0];
        }
        String[] raw = splitPragma(pragma);
        ISelector<?>[] result = new ISelector<?>[raw.length];
        for (int i = 0; i < raw.length; i++) {
            String s = raw[i];
            result[i] = getValue(s);
        }
        return result;
    }

    public @Nullable Sentence create(String raw) {
        if (raw.isEmpty()) {
            return null;
        }
        raw = raw.trim();
        Pattern assign = Pattern.compile("\\$(?<placeholder>\\w+)?=(?<value>.+)");
        Pattern actuate = Pattern.compile("#(?<actuator>\\w+)?\\((?<pragma>.*)\\)");
        Pattern for_ = Pattern.compile("(\\s*)?for(\\s+)(?<placeholder>\\S+?)(\\s+)in(\\s+)range(\\s*)\\((?<range>(.|\\s)*?)\\)(\\s*)\\{(?<body>([\\s\\S]*))\\}");
        Matcher matcher;
        if ((matcher = assign.matcher(raw)).matches()) {
            String name = matcher.group("placeholder");
            AssignSentence sentence = new AssignSentence(manager,list);
            sentence.init(name,getValue(matcher.group("value")));
            return sentence;
        } else if ((matcher = actuate.matcher(raw)).matches()) {
            ActuateSentence sentence = new ActuateSentence(manager, list);
            IActuator actuator = actuatorMap.get(matcher.group("actuator"));
            if (actuator == null) {
                throw new ERROR("Unknown actuator " + matcher.group("actuator"));
            }
            ISelector<?>[] pragma = handlePragma(matcher.group("pragma"));
            sentence.init(actuator, pragma);
            return sentence;
        } else if ((matcher=for_.matcher(raw)).matches()) {
            try {
                String placeholder = matcher.group("placeholder");
                ISelector<?>[] range = handlePragma(matcher.group("range"));
                PlaceholderManager manager1 = manager.clone();
                ForActuatorSentence sentence = new ForActuatorSentence(manager1,list);
                String[] bodies = SentenceReader.splitSentences(matcher.group("body"));
                ActuatorList list1 = new ActuatorList();
                SentenceFactory factory = new SentenceFactory(manager1,list1);
                for (String body : bodies) {
                    factory.create(body);
                }
                sentence.init(placeholder,manager1,range[0],range[1], list1.toArray(new CallableActuator[0]));
                return sentence;
            } catch (Exception e) {
                throw new ERROR(e);
            }
        }
        throw new ERROR("Unknown sentence "+raw);
    }
}
