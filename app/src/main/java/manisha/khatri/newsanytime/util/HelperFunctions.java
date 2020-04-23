package manisha.khatri.newsanytime.util;

import java.util.ArrayList;
import java.util.List;

public class HelperFunctions {

    public static <T> List<T> convertArrayToList(T array[])
    {
        List<T> list = new ArrayList<>();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }

}
