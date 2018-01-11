package com.gxd.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/** 
 * @author gj 
 * @email:gongjing3@asiainfo.com 
 * @version 1.0.0
 * @time：2016年7月27日 下午11:05:43 
 * @Company asiainfo.com
 * 类说明 :通用工具类之按对象中某属性排序 
 */
public class ListUtils {
	/*
	 * 降序
	 */
    public static final String DESC = "desc";  
    /**
     * 升序
     */
    public static final String ASC = "asc";


    /**
     * 根据字段名、类查找它的get方法
     * @param cur_class
     * @param fieldName
     * @return
     */
   public static String getMethodByFieldName(Class cur_class,String fieldName) {
       try {
           Field field = cur_class.getDeclaredField(fieldName);
           field.setAccessible(true);
           Field[] fields=new Field[]{field};
           Method getMethod = null;
           int length = fields.length;
           for(int i = 0;i < length ; i++){
               PropertyDescriptor pd=new PropertyDescriptor(fields[i].getName(),cur_class);
               getMethod = pd.getReadMethod();
               return getMethod.getName();
           }
           if (cur_class.getSuperclass() != null) {
               return getMethodByFieldName(cur_class.getSuperclass(),fieldName);
           }
       } catch (NoSuchFieldException e) {
           if (cur_class.getSuperclass() != null) {
               return getMethodByFieldName(cur_class.getSuperclass(),fieldName);
           }
       } catch (IntrospectionException e) {
           e.printStackTrace();
       }
       return null;
    }

    /**
     * 根据字段名拼凑它的get方法，方法比较丑陋
     * @param fieldName
     * @param fieldName
     * @return
     */
    public static String getGetMethod(String fieldName) {
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        return sb.toString();
    }
    /**
     * 对list中的元素按升序排列. 
     *  
     * @param list 
     *            排序集合 
     * @param field 
     *            排序字段 
     * @return 
     */  
    public static List<?> sort(List<?> list, final String field) {  
        return sort(list, field, null);  
    }  
  
    /** 
     * 对list中的元素进行排序. 
     *  
     * @param list 
     *            排序集合 
     * @param field 
     *            排序字段 
     * @param sort 
     *            排序方式: SortList.DESC(降序) SortList.ASC(升序). 
     * @return 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public static List<?> sort(List<?> list, final String field,  
            final String sort) {  
        Collections.sort(list, new Comparator() {  
            public int compare(Object a, Object b) {
                return compareField(a, b , field, sort);
            }
        });  
        return list;  
    }  
  
    /** 
     * 对list中的元素按fields和sorts进行排序, 
     * fields[i]指定排序字段,sorts[i]指定排序方式.如果sorts[i]为空则默认按升序排列. 
     *  
     * @param list 
     * @param fields 
     * @param sorts 
     * @return 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public static List<?> sort(List<?> list, String[] fields, String[] sorts) {  
        if (fields != null && fields.length > 0) {  
            for (int i = fields.length - 1; i >= 0; i--) {  
                final String field = fields[i];  
                String tmpSort = ASC;  
                if (sorts != null && sorts.length > i && sorts[i] != null) {  
                    tmpSort = sorts[i];  
                }  
                final String sort = tmpSort;  
                Collections.sort(list, new Comparator() {  
                    public int compare(Object a, Object b) {  
                        return compareField(a, b , field, sort);
                    }  
                });  
            }  
        }  
        return list;  
    }  
  
    /** 
     * 默认按正序排列 
     *  
     * @param list 
     * @param method 
     * @return 
     */  
    public static List<?> sortByMethod(List<?> list, final String method) {  
        return sortByMethod(list, method, null);  
    }  
  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public static List<?> sortByMethod(List<?> list, final String method,  
            final String sort) {  
        Collections.sort(list, new Comparator() {  
            public int compare(Object a, Object b) {  
                return compareMethod(a, b, method, sort);
            }  
        });  
        return list;  
    }  
  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public static List<?> sortByMethod(List<?> list, final String methods[],  
            final String sorts[]) {  
        if (methods != null && methods.length > 0) {  
            for (int i = methods.length - 1; i >= 0; i--) {  
                final String method = methods[i];  
                String tmpSort = ASC;  
                if (sorts != null && sorts.length > i && sorts[i] != null) {  
                    tmpSort = sorts[i];  
                }  
                final String sort = tmpSort;  
                Collections.sort(list, new Comparator() {  
                    public int compare(Object a, Object b) {  
                        return compareMethod(a, b, method, sort);
                    }  
                });  
            }  
        }  
        return list;  
    }  
  
    /** 
     * 判断对象实现的所有接口中是否包含szInterface 
     *  
     * @param clazz 
     * @param szInterface 
     * @return 
     */  
    public static boolean isImplementsOf(Class<?> clazz, Class<?> szInterface) {  
        boolean flag = false;  
  
        Class<?>[] face = clazz.getInterfaces();  
        for (Class<?> c : face) {  
            if (c == szInterface) {  
                flag = true;  
            } else {  
                flag = isImplementsOf(c, szInterface);  
            }  
        }  
  
        if (!flag && null != clazz.getSuperclass()) {  
            return isImplementsOf(clazz.getSuperclass(), szInterface);  
        }  
  
        return flag;  
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static int compareField(Object a, Object b, String field, String sort) {
        int ret = 0;
        try {
            Field f = a.getClass().getDeclaredField(field);
            f.setAccessible(true);
            Class<?> type = f.getType();

            if (type == int.class) {
                ret = ((Integer) f.getInt(a)).compareTo((Integer) f
                        .getInt(b));
            } else if (type == double.class) {
                ret = ((Double) f.getDouble(a)).compareTo((Double) f
                        .getDouble(b));
            } else if (type == long.class) {
                ret = ((Long) f.getLong(a)).compareTo((Long) f
                        .getLong(b));
            } else if (type == float.class) {
                ret = ((Float) f.getFloat(a)).compareTo((Float) f
                        .getFloat(b));
            } else if (type == Date.class) {
                ret = ((Date) f.get(a)).compareTo((Date) f.get(b));
            } else if (isImplementsOf(type, Comparable.class)) {
                ret = ((Comparable) f.get(a)).compareTo((Comparable) f
                        .get(b));
            } else {
                ret = String.valueOf(f.get(a)).compareTo(
                        String.valueOf(f.get(b)));
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (sort != null && sort.toLowerCase().equals(DESC)) {
            return -ret;
        } else {
            return ret;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static int compareMethod(Object a, Object b, String method, String sort) {
        int ret = 0;
        try {
            Method m = a.getClass().getMethod(method);
            m.setAccessible(true);
            Class<?> type = m.getReturnType();
            if (type == int.class) {
                ret = ((Integer) m.invoke(a))
                        .compareTo((Integer) m.invoke(b));
            } else if (type == double.class) {
                ret = ((Double) m.invoke(a)).compareTo((Double) m
                        .invoke(b));
            } else if (type == long.class) {
                ret = ((Long) m.invoke(a)).compareTo((Long) m
                        .invoke(b));
            } else if (type == float.class) {
                ret = ((Float) m.invoke(a)).compareTo((Float) m
                        .invoke(b));
            } else if (type == Date.class) {
                ret = ((Date) m.invoke(a)).compareTo((Date) m
                        .invoke(b));
            } else if (isImplementsOf(type, Comparable.class)) {
                ret = ((Comparable) m.invoke(a))
                        .compareTo((Comparable) m.invoke(b));
            } else {
                ret = String.valueOf(m.invoke(a)).compareTo(
                        String.valueOf(m.invoke(b)));
            }
        } catch (NoSuchMethodException ne) {
            System.out.println(ne);
        } catch (IllegalAccessException ie) {
            System.out.println(ie);
        } catch (InvocationTargetException it) {
            System.out.println(it);
        }

        if (sort != null && sort.toLowerCase().equals(DESC)) {
            return -ret;
        } else {
            return ret;
        }
    }

//    public static void main(String[] args) {
//
//        List<SystemFunction> systemFunctions = new ArrayList<>() ;
//        for (int i = 10; i > 0; i--) {
//            SystemFunction systemFunction = new SystemFunction() ;
//            systemFunction.setId((long)i);
//
//            systemFunctions.add(systemFunction);
//        }
//
//        System.out.println(JsonUtils.toJson(systemFunctions));
//        List<?> newList = ListUtils.sortByMethod(systemFunctions, "getId");
//        System.out.println(JsonUtils.toJson(newList));
//
//    }
}