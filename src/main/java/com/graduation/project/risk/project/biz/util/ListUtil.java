package com.graduation.project.risk.project.biz.util;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    /**
     * list --> List<List>
     *
     * @param list
     * @param size
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> sub(List<T> list, int size) {

        if (list.size() <= size) {
            List<List<T>> lists = new ArrayList<>();
            lists.add(list);
            return lists;
        }

        int number = (list.size() % size) + 1;

        return Lists.partition(list, number);
    }
}
