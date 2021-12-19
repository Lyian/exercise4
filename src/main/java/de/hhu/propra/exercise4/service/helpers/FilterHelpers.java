package de.hhu.propra.exercise4.service.helpers;

import java.util.Locale;

public class FilterHelpers {
    public static boolean filterStringContainsIfNotNull(String filter, String value){
        if(filter != null){
            return value.toLowerCase(Locale.ROOT).contains(filter.toLowerCase(Locale.ROOT));
        }
        return true;
    }

    public static boolean filterSmallerThan(Integer filter, Integer value){
        if(filter != null) return value >= filter;
        return true;
    }

    public static <T> boolean filterEqualsIfNotNull(T filter, T value){
        if(filter != null) return filter == value;
        return true;
    }

    public static boolean filterIsTrueStringToBoolean(String booleanValue, boolean value){
        if(booleanValue != null){
            boolean convertedStringValue = booleanValue.toLowerCase(Locale.ROOT).equals("true");
            return value == convertedStringValue;
        }
        return true;
    }
}
