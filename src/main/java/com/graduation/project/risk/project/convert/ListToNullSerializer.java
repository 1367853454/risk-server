package com.graduation.project.risk.project.convert;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ListToNullSerializer  implements ObjectSerializer {

    @Override
    public void write(JSONSerializer jsonSerializer, Object obj, Object objName, Type type, int i) throws IOException {

        if (!(obj instanceof List)) {
            return;
        }

        List list = (List) obj;

        if (list == null || list.size() == 0) {
            jsonSerializer.write(null);
            return;
        }

        jsonSerializer.write(list);
    }
}
