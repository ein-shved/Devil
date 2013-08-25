package Devil.util;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {
    public int compare (String o1, String o2) {
        return o1.compareTo(o2);
    }
    public boolean equals (String o1, String o2) {
        return o1.equals(o2);
    }
}
