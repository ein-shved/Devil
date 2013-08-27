/* Devil is the complex messager with distributed structure.
 * Copyright (C) 2013  Shvedov Yury
 * 
 * This file is part of Devil.
 *
 * Devil is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Devil is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Devil.  If not, see <http://www.gnu.org/licenses/>.
 */

package Devil.util;

import java.util.Comparator;

/**
 * Comparator for string.
 *
 * @see Comparator
 */
public class StringComparator implements Comparator<String> {
    /**
     * Calls String.CompareTO
     *
     * @see String.compareTo
     * @see Comparator
     */
    public int compare (String o1, String o2) {
        return o1.compareTo(o2);
    }

    /**
     * Calls String.equals
     *
     * @see String.equals
     * @see Comparator
     */
    public boolean equals (String o1, String o2) {
        return o1.equals(o2);
    }
}
