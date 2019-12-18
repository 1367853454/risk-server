package com.graduation.project.risk.common.model;


import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ToString {
    public ToString() {
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static void main(String[] args) {
        List<Integer> integers = new ArrayList(5);
        integers.add(1);
        integers.add(2);
        integers.add(2);
        integers.add(4);
        integers.add(5);

        for(int i = 0; i < integers.size(); ++i) {
            if ((Integer)integers.get(i) % 2 == 0) {
                integers.remove(i);
            }
        }

        System.out.println(integers);
    }
}

