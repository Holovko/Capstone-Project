package com.holovko.kyivmommap.utils;
import android.os.Bundle;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class CollectionUtils {
    public static Bundle mapToBundle(Map<String, ? extends Parcelable> input) {
        Bundle output = new Bundle();
        for(String key : input.keySet()) {
            output.putParcelable(key, input.get(key));
        }
        return output;
    }

    public static <T extends Parcelable> Map<String, T> mapFromBundle(Bundle input, Class<T> c) {
        Map<String, T> output = new HashMap<String, T>();
        for(String key : input.keySet()) {
            output.put(key, c.cast(input.getParcelable(key)));
        }
        return output;
    }
}
