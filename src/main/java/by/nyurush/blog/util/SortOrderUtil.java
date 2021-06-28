package by.nyurush.blog.util;

import org.springframework.data.domain.Sort;

public class SortOrderUtil {
    private static final String ASC = "asc";
    private static final String DESC = "desc";

    public static Sort.Direction getSortOrder(String order) {
        if (order.equals(ASC)) {
            return Sort.Direction.ASC;
        } else if (order.equals(DESC)) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}
