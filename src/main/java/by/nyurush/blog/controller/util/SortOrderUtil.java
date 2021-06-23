package by.nyurush.blog.controller.util;

import org.springframework.data.domain.Sort;

public class SortOrderUtil {

    public static Sort.Direction getSortOrder(String order) {
        if (order.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (order.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}
