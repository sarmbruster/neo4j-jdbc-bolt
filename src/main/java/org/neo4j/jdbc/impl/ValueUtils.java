package org.neo4j.jdbc.impl;

import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Stefan Armbruster
 */
public class ValueUtils {

    public static Object valueToObject(Value value) {
        if (value.isBoolean()) {
            return value.javaBoolean();
        } else if (value.isFloat()) {
            return value.javaFloat();
        } else if (value.isFloat()) {
            return value.javaFloat();
        } else if (value.isIdentity()) {
            return value.asIdentity();
        } else if (value.isInteger()) {
            return value.javaInteger();
        } else if (value.isList()) {
            List list = new ArrayList((int) value.size());
            for (long i=0; i < value.size(); i++) {
                list.add(value.get(i));
            }
            return list;
        } else if (value.isMap()) {
            Map map = new HashMap((int) value.size());
            for (String key: value.keys()) {
                map.put(key, value.get(key));
            }
            return map;
        } else if (value.isNode()) {
            return value.asNode();
        } else if (value.isPath()) {
            return value.asPath();
        } else if (value.isRelationship()) {
            return value.asRelationship();
        } else if (value.isString()) {
            return value.javaString();
        }
        throw new IllegalStateException("could not make sense out of Value instance " + value.toString());
    }

    public static Value objectToValue(Object obj) {
        if (obj instanceof String) {
            return new StringValue((String) obj);
        } else {
            throw new IllegalStateException("cannot convert " + obj + " to a Value");
        }
    }
}
