package me.skymc.taboolib.itemtool.util;

import com.google.common.collect.Maps;
import io.izzel.taboolib.util.lite.Numbers;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.NumberConversions;

import java.util.Arrays;
import java.util.Map;

/**
 * @Author 坏黑
 * @Since 2018-10-13 16:13
 */
public class Util {

    private static Map<String, Color> colors = Maps.newHashMap();

    static {
        colors.put("WHITE", Color.fromRGB(16777215));
        colors.put("SILVER", Color.fromRGB(12632256));
        colors.put("GRAY", Color.fromRGB(8421504));
        colors.put("BLACK", Color.fromRGB(0));
        colors.put("RED", Color.fromRGB(16711680));
        colors.put("MAROON", Color.fromRGB(8388608));
        colors.put("YELLOW", Color.fromRGB(16776960));
        colors.put("OLIVE", Color.fromRGB(8421376));
        colors.put("LIME", Color.fromRGB(65280));
        colors.put("GREEN", Color.fromRGB(32768));
        colors.put("AQUA", Color.fromRGB(65535));
        colors.put("TEAL", Color.fromRGB(32896));
        colors.put("BLUE", Color.fromRGB(255));
        colors.put("NAVY", Color.fromRGB(128));
        colors.put("FUCHSIA", Color.fromRGB(16711935));
        colors.put("PURPLE", Color.fromRGB(8388736));
        colors.put("ORANGE", Color.fromRGB(16753920));
    }

    public static EntityType asEntityType(String name) {
        EntityType entityType = EntityType.fromName(name);
        return entityType == null ? EntityType.fromId(NumberConversions.toInt(name)) : entityType;
    }

    public static Color[] asColorArray(String name) {
        String[] array = name.split("\\|");
        return Arrays.stream(array).map(Util::asColor).toArray(Color[]::new);
    }

    public static Color randomColor() {
        return Color.fromRGB(Numbers.getRandomInteger(0, 255), Numbers.getRandomInteger(0, 255), Numbers.getRandomInteger(0, 255));
    }

    public static Color asColor(String name) {
        if (name.equalsIgnoreCase("random") || name.equalsIgnoreCase("r")) {
            return randomColor();
        }
        Color color = colors.get(name.toUpperCase());
        if (color != null) {
            return color;
        }
        String[] split = name.split("-");
        return split.length == 3 ? Color.fromRGB(NumberConversions.toInt(split[0]), NumberConversions.toInt(split[1]), NumberConversions.toInt(split[2])) : Color.fromRGB(NumberConversions.toInt(split[0]));
    }

    public static boolean isNumber(Object obj) {
        if (obj instanceof Number) {
            return true;
        }
        try {
            Double.valueOf(obj.toString());
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean isBoolean(Object obj) {
        if (obj instanceof Number) {
            return true;
        }
        try {
            Boolean.valueOf(obj.toString());
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public static void spawnRandomFirework(Location location, int power) {
        Color[] colorsArray = Util.colors.values().toArray(new Color[0]);
        Firework entity = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = entity.getFireworkMeta();
        fireworkMeta.setPower(power);
        fireworkMeta.addEffect(FireworkEffect.builder().flicker(Numbers.getRandom().nextBoolean()).trail(Numbers.getRandom().nextBoolean()).with(FireworkEffect.Type.values()[Numbers.getRandom().nextInt(FireworkEffect.Type.values().length)]).withColor(colorsArray[Numbers.getRandom().nextInt(colorsArray.length)]).build());
        entity.setFireworkMeta(fireworkMeta);
    }
}
