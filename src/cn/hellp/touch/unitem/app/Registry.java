package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.actuator.IActuator;
import cn.hellp.touch.unitem.actuator.PerformCommandActuator;
import cn.hellp.touch.unitem.actuator.block.SetBlockTypeActuator;
import cn.hellp.touch.unitem.actuator.entity.*;
import cn.hellp.touch.unitem.actuator.entity.player.SetGameModeActuator;
import cn.hellp.touch.unitem.actuator.itemstack.SetItemStackDisplayNameActuator;
import cn.hellp.touch.unitem.actuator.itemstack.SetItemStackTypeActuator;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.auxiliary.GetterSetter;
import cn.hellp.touch.unitem.selector.ConsoleSelector;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.block.GetBlockFromLocationSelector;
import cn.hellp.touch.unitem.selector.block.LookingBlockSelector;
import cn.hellp.touch.unitem.selector.entity.living.LastDamageSourceEntitySelector;
import cn.hellp.touch.unitem.selector.entity.living.NearByLivingEntitySelector;
import cn.hellp.touch.unitem.selector.entity.living.player.CallingPlayerSelector;
import cn.hellp.touch.unitem.selector.entity.living.player.LastDamageSourcePlayerSelector;
import cn.hellp.touch.unitem.selector.entity.living.player.OnlinePlayerSelector;
import cn.hellp.touch.unitem.selector.item.GetItemInHand;
import cn.hellp.touch.unitem.selector.location.LocationOfEntitySelector;
import cn.hellp.touch.unitem.selector.location.LocationToVecSelector;
import cn.hellp.touch.unitem.selector.location.LocationValueSelector;
import cn.hellp.touch.unitem.selector.tools.GetFromIndexSelector;
import cn.hellp.touch.unitem.selector.tools.ToStringSelector;
import cn.hellp.touch.unitem.selector.tools.VectorToLocationSelector;
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
import cn.hellp.touch.unitem.selector.tools.player.PlayerNameGetter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Registry<T> {
    public static final Registry<IActuator> ACTUATOR = new Registry<>();
    public static final Registry<SelectorFactory> SELECTOR = new Registry<>();

    static {
        SELECTOR.register("owner_player", new SelectorFactory((ISelector<?>[] s) -> new CallingPlayerSelector()));
        SELECTOR.register("console", new SelectorFactory((ISelector<?>[] s) -> new ConsoleSelector()));

        IActuator actuator;
        actuator = new KillEntityActuator();
        ACTUATOR.register(actuator.actuatorID(), actuator);
        actuator = new TeleportEntityActuator();
        ACTUATOR.register(actuator.actuatorID(), actuator);
        actuator = new SendMessageActuator();
        ACTUATOR.register(actuator.actuatorID(), actuator);
        actuator = new DamageEntityActuator();
        ACTUATOR.register(actuator.actuatorID(), actuator);
        actuator = new SetBlockTypeActuator();
        ACTUATOR.register(actuator.actuatorID(), actuator);
        actuator = new SetFoodLevelActuator();
        ACTUATOR.register(actuator.actuatorID(), actuator);
        actuator = new SetHealthActuator();
        ACTUATOR.register(actuator.actuatorID(), actuator);
        actuator = new SetItemStackTypeActuator();
        ACTUATOR.register(actuator.actuatorID(), actuator);
        actuator = new SetGameModeActuator();
        ACTUATOR.register(actuator.actuatorID(), actuator);
        actuator = new PerformCommandActuator();
        ACTUATOR.register(actuator.actuatorID(), actuator);
        actuator = new SetItemStackDisplayNameActuator();
        ACTUATOR.register(actuator.actuatorID(), actuator);

        GetterSetter<?> getterSetter;
        getterSetter = new cn.hellp.touch.unitem.selector.tools.player.PlayerCanFlyGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.player.PlayerExpGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.player.PlayerFoodLevelGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.player.PlayerLevelGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.player.PlayerMoneyGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.player.PlayerOpGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.player.PlayerSneakingGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.player.PlayerSprintingGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.player.PlayerTimeGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.entity.living.LivingEntityKillerGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.entity.living.LivingEntityRemainingAirGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.entity.EntityCustomNameGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.entity.EntityFireTicksGetterSetter();
        ACTUATOR.register(actuator.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));
        getterSetter = new cn.hellp.touch.unitem.selector.tools.entity.EntityVelocityGetterSetter();
        ACTUATOR.register(getterSetter.actuatorID(), getterSetter);
        SELECTOR.register(getterSetter.selectorID(), createFactoryFromClass(getterSetter.getClass()));

        Constructor<?> selectorConstructor;
        Constructor<?> selectorConstructor1;
        try {
            SELECTOR.register("getPlayerName", createFactoryFromClass(PlayerNameGetter.class));
            SELECTOR.register("getEntityActiveItem", createFactoryFromClass(cn.hellp.touch.unitem.selector.tools.entity.living.LivingEntityActiveItemGetter.class));
            SELECTOR.register("isOnGround", createFactoryFromClass(cn.hellp.touch.unitem.selector.tools.entity.EntityIsOnGroundGetter.class));
            SELECTOR.register("getEntityLocation", createFactoryFromClass(cn.hellp.touch.unitem.selector.tools.entity.EntityLocationGetter.class));
            SELECTOR.register("getEntityType", createFactoryFromClass(cn.hellp.touch.unitem.selector.tools.entity.EntityTypeGetter.class));
            SELECTOR.register("getEntityUUID", createFactoryFromClass(cn.hellp.touch.unitem.selector.tools.entity.EntityUniqueIdGetter.class));

            selectorConstructor = LastDamageSourceEntitySelector.class.getDeclaredConstructor();
            selectorConstructor1 = LastDamageSourceEntitySelector.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("lastDamageEntity", putConstruct(listOf(selectorConstructor, selectorConstructor1)));

            selectorConstructor = LastDamageSourcePlayerSelector.class.getDeclaredConstructor();
            selectorConstructor1 = LastDamageSourcePlayerSelector.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("lastDamagePlayer", putConstruct(listOf(selectorConstructor, selectorConstructor1)));

            selectorConstructor = NearByLivingEntitySelector.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("nearLivingEntities", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = XOf.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("XOf", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = YOf.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("YOf", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = ZOf.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("ZOf", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = AddNumber.class.getDeclaredConstructor(ISelector.class, ISelector.class);
            SELECTOR.register("add", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = SubNumber.class.getDeclaredConstructor(ISelector.class, ISelector.class);
            SELECTOR.register("sub", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = GetFromIndexSelector.class.getDeclaredConstructor(ISelector.class, ISelector.class);
            SELECTOR.register("getFromIndex", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = LocationOfEntitySelector.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("locationOfEntity", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = GetFoodLevelSelector.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("getFoodLevel", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = GetHealthSelector.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("getHealth", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = GetVelocitySelector.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("getVelocity", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = ToStringSelector.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("toString", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = LocationValueSelector.class.getDeclaredConstructor(ISelector.class, ISelector.class, ISelector.class, ISelector.class);
            selectorConstructor1 = LocationValueSelector.class.getDeclaredConstructor(ISelector.class, ISelector.class, ISelector.class);
            SELECTOR.register("locationOf", putConstruct(listOf(selectorConstructor, selectorConstructor1)));

            selectorConstructor = OnlinePlayerSelector.class.getDeclaredConstructor();
            SELECTOR.register("onlinePlayers", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = LookingBlockSelector.class.getDeclaredConstructor();
            selectorConstructor1 = LookingBlockSelector.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("lookingBlock", putConstruct(listOf(selectorConstructor, selectorConstructor1)));

            selectorConstructor = GetBlockFromLocationSelector.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("getBlockFromLocation", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = GetDirection.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("getDirection", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = GetItemInHand.class.getDeclaredConstructor();
            selectorConstructor1 = GetItemInHand.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("getItemInHand", putConstruct(listOf(selectorConstructor, selectorConstructor1)));

            selectorConstructor = GetBlockType.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("getBlockType", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = GetItemType.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("getItemType", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = VectorToLocationSelector.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("vecToLocation", putConstruct(listOf(selectorConstructor)));

            selectorConstructor = LocationToVecSelector.class.getDeclaredConstructor(ISelector.class);
            SELECTOR.register("locToVector", putConstruct(listOf(selectorConstructor)));
        } catch (Exception ed) {
            throw new ERROR(ed);
        }
    }

    private final Map<String, T> map = new HashMap<>();

    private Registry() {
    }

    private static SelectorFactory putConstruct(Collection<Constructor<?>> constructors) {
        return new SelectorFactory((ISelector<?>[] s) -> {
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() == s.length) {
                    try {
                        return (ISelector<?>) constructor.newInstance((Object[]) s);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            throw new ERROR("Wrong parameter count.");
        });
    }

    private static SelectorFactory createFactoryFromClass(Class<?> klass) {
        final Constructor<?>[] constructors = klass.getDeclaredConstructors();
        return putConstruct(Arrays.asList(constructors));
    }

    @SafeVarargs
    private static <T> List<T> listOf(T... eme) {
        List<T> result = new ArrayList<>();
        Collections.addAll(result, eme);
        return result;
    }

    public void register(String id, T eme) {
        map.putIfAbsent(id, eme);
    }

    public T get(String id) {
        return map.get(id);
    }

    public void update(String id, T newOne) {
        map.replace(id, newOne);
    }

    public void delete(String id) {
        map.remove(id);
    }

    public Collection<T> getAll() {
        return new ArrayList<>(map.values());
    }
}
