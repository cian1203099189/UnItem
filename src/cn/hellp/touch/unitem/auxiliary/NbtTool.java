package cn.hellp.touch.unitem.auxiliary;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NbtTool {
    private static final Class<?> craftItemStack;
    private static final Class<?> nbtTagCompound;
    private static final Class<?> itemStackClass;
    public static final boolean later1_17;
    public static final int server_version;

    static {
        Pattern p = Pattern.compile("(.*?)(?<v>[0-2]\\.\\d{1,2}(.\\d)?)(.*)");
        Matcher m = p.matcher(Bukkit.getVersion());
        String minecraft_version = "";
        if (m.find()) {
            minecraft_version = m.group("v");
            int v = Integer.parseInt(minecraft_version.split("\\.")[1]);
            later1_17 = v >= 17;
            server_version=v;
        } else {
            throw new RuntimeException("can 't find minecraft version from Bukkit::getVersion():" + Bukkit.getVersion());
        }

        String pre = minecraft_version.split("\\.")[0];
        String version_package = "v" + pre + "_" + minecraft_version.split("\\.")[1] + "_R";
        int i = 0;

        while(i < 10) {
            try {
                Class.forName("org.bukkit.craftbukkit." + version_package + i + ".inventory.CraftInventoryView");
                version_package = version_package + i;
                break;
            } catch (ClassNotFoundException var9) {
                ++i;
            }
        }

        if(later1_17) {
            try {
                nbtTagCompound=Class.forName("net.minecraft.nbt.NBTTagCompound");
                itemStackClass=Class.forName("net.minecraft.world.item.ItemStack");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                nbtTagCompound=Class.forName("net.minecraft.server."+version_package+".NBTTagCompound");
                itemStackClass=Class.forName("net.minecraft.server."+version_package+".ItemStack");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            craftItemStack=Class.forName("org.bukkit.craftbukkit."+version_package+".inventory.CraftItemStack");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getField(Class<?> findClass,Object obj,String fieldName) {
        try {
            Field field = findClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object callMethod(Class<?> findClass,Object obj,String methodName,Class<?>[] pragmaType,Object... pragma) {
        try {
            Method method = findClass.getDeclaredMethod(methodName,pragmaType);
            method.setAccessible(true);
            return method.invoke(obj,pragma);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemStack setString(ItemStack itemStack,String key,String value) {
        Object compound = getNBTCompound(itemStack);
        Object craftItem = callMethod(craftItemStack,null,"asCraftCopy",new Class[]{ItemStack.class},itemStack);
        callMethod(nbtTagCompound,compound,"setString",new Class[]{String.class,String.class},key,value);
        callMethod(itemStackClass,getField(craftItemStack,craftItem,"handle"),"setTag",new Class[]{nbtTagCompound},compound);
        return (ItemStack) craftItem;
    }

    public static String getString(ItemStack itemStack,String key) {
        if(itemStack==null) {
            return "";
        }
        Object handle = getField(craftItemStack,callMethod(craftItemStack,null,"asCraftCopy",new Class[]{ItemStack.class},itemStack),"handle");
        if(handle==null) {
            return "";
        }
        Object compound = callMethod(itemStackClass,handle,"getTag",new Class[0]);
        if(compound == null) {
            return "";
        }
        return (String) callMethod(nbtTagCompound,compound,"getString",new Class[]{String.class},key);
    }

    public static Object getNBTCompound(ItemStack itemStack) {
        if(itemStack==null) {
            return null;
        }
        Object craftItem = callMethod(craftItemStack,null,"asCraftCopy",new Class[]{ItemStack.class},itemStack);
        Object compound = callMethod(itemStackClass,getField(craftItemStack,craftItem,"handle"),"getTag",new Class[0]);
        if(compound==null) {
            Constructor<?> constructor;
            try {
                constructor = nbtTagCompound.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            try {
                compound=constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return compound;
    }

    public static ItemStack setBoolean(ItemStack itemStack,String key,boolean value) {
        Object compound = getNBTCompound(itemStack);
        Object craftItem = callMethod(craftItemStack,null,"asCraftCopy",new Class[]{ItemStack.class},itemStack);
        callMethod(nbtTagCompound,compound,"setBoolean",new Class[]{String.class,boolean.class},key,value);
        callMethod(itemStackClass,getField(craftItemStack,craftItem,"handle"),"setTag",new Class[]{nbtTagCompound},compound);
        return (ItemStack) craftItem;
    }

    public static boolean getBoolean(ItemStack itemStack,String key) {
        if(itemStack==null) {
            return false;
        }
        Object handle = getField(craftItemStack,callMethod(craftItemStack,null,"asCraftCopy",new Class[]{ItemStack.class},itemStack),"handle");
        if(handle==null) {
            return false;
        }
        Object compound = callMethod(itemStackClass,handle,"getTag",new Class[0]);
        if(compound == null) {
            return false;
        }
        return (boolean) callMethod(nbtTagCompound,compound,"getBoolean",new Class[]{String.class},key);
    }
}
