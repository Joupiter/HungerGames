package fr.joupi.api.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author VadamDev
 * @since 04/08/2022
 */
// FROM : https://github.com/VadamDev/VIAPI/blob/1.8.x/src/net/vadamdev/viapi/tools/enums/EnumDirection.java
@Getter
@AllArgsConstructor
public enum EnumDirection {

    UP (0),
    NORTH(-180),
    EAST(-90),
    SOUTH(0),
    WEST(90),
    NORTH_EAST(-135),
    NORTH_WEST(135),
    SOUTH_EAST(-45),
    SOUTH_WEST(45),
    DOWN (0);

    private final float yaw;

    public EnumDirection getOpposite() {
        return switch (this) {
            case UP -> DOWN;
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            case NORTH_EAST -> SOUTH_WEST;
            case NORTH_WEST -> SOUTH_EAST;
            case SOUTH_EAST -> NORTH_WEST;
            case SOUTH_WEST -> NORTH_EAST;
            case DOWN -> UP;
        };
    }

    public static EnumDirection getCardinalDirection(float yaw) {
        double rot = (yaw - 180) % 360;

        if (rot < 0)
            rot += 360;

        if (0 <= rot && rot < 67.5)
            return EnumDirection.NORTH;
        else if (67.5 <= rot && rot < 157.5)
            return EnumDirection.EAST;
        else if (157.5 <= rot && rot < 247.5)
            return EnumDirection.SOUTH;
        else if (247.5 <= rot && rot < 337.5)
            return EnumDirection.WEST;
        else if (337.5 <= rot && rot < 360.0)
            return EnumDirection.NORTH;

        return null;
    }

    public static EnumDirection getPreciseDirection(float yaw) {
        double rot = yaw - 180;

        if (rot < 0)
            rot += 360;

        if (22.5 <= rot && rot < 67.5)
            return EnumDirection.NORTH_EAST;
        else if (67.5 <= rot && rot < 112.5)
            return EnumDirection.EAST;
        else if (112.5 <= rot && rot < 157.5)
            return EnumDirection.SOUTH_EAST;
        else if (157.5 <= rot && rot < 202.5)
            return EnumDirection.SOUTH;
        else if (202.5 <= rot && rot < 247.5)
            return EnumDirection.SOUTH_WEST;
        else if (247.5 <= rot && rot < 292.5)
            return EnumDirection.WEST;
        else if (292.5 <= rot && rot < 337.5)
            return EnumDirection.NORTH_WEST;
        else
            return EnumDirection.NORTH;
    }

}