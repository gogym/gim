package com.gettyio.gim.utils;


import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author gogym.ggj
 * @ClassName ColumnUtil.java
 * @createTime 2023/07/06/ 15:30:00
 */
public class ColumnUtil {

    /**
     * 使Function获取序列化能力
     */
    @FunctionalInterface
    public interface SFunction<T, R> extends Function<T, R>, Serializable {
    }

    /**
     * 获取实体类的字段名称
     */
    private static <T> String getFieldName(SFunction<T, ?> fn) {
        SerializedLambda serializedLambda = getSerializedLambda(fn);

        // 从lambda信息取出method、field、class等
        String fieldName = serializedLambda.getImplMethodName().substring("get".length());
        fieldName = fieldName.replaceFirst(fieldName.charAt(0) + "", (fieldName.charAt(0) + "").toLowerCase());
        return fieldName;
    }

    private static <T> SerializedLambda getSerializedLambda(SFunction<T, ?> fn) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(isAccessible);
        return serializedLambda;
    }

    /**
     * 获取 columnName
     */
    public static <T> String columnToString(SFunction<T, ?> column) {
        return getFieldName(column);
    }

    /**
     * 获取 columnNames
     */
    @SafeVarargs
    public static <T> String[] columnToString(SFunction<T, ?>... columns) {
        List<String> list = new ArrayList<>();
        for (SFunction<T, ?> function : columns) {
            String str = columnToString(function);
            list.add(str);
        }

        return list.toArray(new String[0]);
    }


    /**
     * 获取所有的bean字段名称ø
     *
     * @param c
     * @param ignoreFields
     * @return
     */
    public static String[] getAllFieldName(Class<?> c, String... ignoreFields) {
        List<String> list = new ArrayList<>();
        ArrayList<String> stringList = new ArrayList<>();
        if (ignoreFields != null) {
            Collections.addAll(stringList, ignoreFields);
        }

        while (c != null) {
            Field[] fields = c.getDeclaredFields();
            for (Field f : fields) {
                String name = f.getName();
                if (stringList.contains(name)) {
                    continue;
                }
                list.add(name);
            }
            c = c.getSuperclass();
        }
        return list.toArray(new String[0]);
    }

    /**
     * 获取所有属性为空的字段名
     *
     * @param obj
     * @param ignoreFields
     * @return
     */
    public static String[] getAllHasNullFieldName(Object obj, String... ignoreFields) {

        if (obj == null) {
            throw new NullPointerException("obj is null");
        }

        List<String> list = new ArrayList<>();
        ArrayList<String> stringList = new ArrayList<>();
        if (ignoreFields != null) {
            Collections.addAll(stringList, ignoreFields);
        }
        Class<?> c = obj.getClass();
        while (c != null) {
            Field[] fields = c.getDeclaredFields();
            for (Field f : fields) {
                String name = f.getName();
                if (stringList.contains(name) || getFieldValue(obj, f) != null) {
                    continue;
                }
                list.add(name);
            }
            c = c.getSuperclass();
        }
        return list.toArray(new String[0]);
    }


    /**
     * 获取指定检查为空的字段名称
     *
     * @param obj
     * @param checkFields
     * @return
     */
    public static String[] getCheckNullFieldName(Object obj, String... checkFields) {
        if (obj == null || checkFields == null) {
            throw new NullPointerException("obj or checkFields is null");
        }

        List<String> list = new ArrayList<>();
        ArrayList<String> stringList = new ArrayList<>();
        Collections.addAll(stringList, checkFields);

        Class<?> c = obj.getClass();
        while (c != null) {
            Field[] fields = c.getDeclaredFields();
            for (Field f : fields) {
                String name = f.getName();
                if (stringList.contains(name) && getFieldValue(obj, f) == null) {
                    list.add(name);
                }
            }
            c = c.getSuperclass();
        }
        return list.toArray(new String[0]);

    }


    /**
     * 获取字段的值
     *
     * @param obj
     * @param field
     * @return
     * @throws UtilException
     */
    private static Object getFieldValue(Object obj, Field field) throws UtilException {
        if (null == field) {
            return null;
        }
        if (obj instanceof Class) {
            // 静态字段获取时对象为null
            obj = null;
        }

        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        Object result;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            throw new UtilException(e, "IllegalAccess for {}.{}", field.getDeclaringClass(), field.getName());
        }
        return result;
    }

}
