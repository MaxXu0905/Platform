/**
 * author :  lipan
 * filename :  PropsUtils.java
 * create_time : 2014年7月2日 下午5:43:16
 */
package com.ailk.sets.platform.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;

/**
 * 
 * 复制bean属性
 * @author : lipan
 * @create_time : 2014年7月2日 下午5:43:16
 * @desc : 
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public class PropsUtils
{

//    private static Log log = LogFactory.getLog(PropsUtils.class);

    private static final String GET = "get";
    private static final String SET = "set";

    public static void copyProperties(Object dest, Object orig)
    {
        Method[] destMethods = dest.getClass().getDeclaredMethods();
        Method[] origMethods = orig.getClass().getDeclaredMethods();

        // 目标所有的set方法
        Map<String, Method> destMap = array2Map(destMethods, SET);

        // 原始所有的get方法
        Map<String, Method> origMap = array2Map(origMethods, GET);

        for (String name : destMap.keySet())
        {
            // 原始对象如果含有目标对象的变量，那么将原始对象中不为空的值复制到目标对象中
            if (origMap.containsKey(name))
            {
                try
                {
                    // 调用原始对象的get方法，获得对象中该属性的值
                    Object origVal = origMap.get(name).invoke(orig, (Object[]) null);

                    // 原始对象的值不为空
                    if (null != origVal)
                    {
                        // 调用目标对象的set方法，将原始对象中的值set到目标对象中
                        destMap.get(name).invoke(dest, new Object[] { origVal });
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获得变量名称Map
     * 
     * @param methods
     * @return key为变量名称、value为对应的get / set Method
     */
    public static Map<String, Method> array2Map(Method[] methods, String methodType)
    {
        String field = "";
        Map<String, Method> methodMap = new HashMap<String, Method>();
        for (Method method : methods)
        {
            field = method.getName();
            if (field.startsWith(methodType))
            {
                field = field.substring(field.indexOf(methodType) + 3);
                field = field.toLowerCase(Locale.getDefault()).charAt(0) + field.substring(1);
                methodMap.put(field, method);
            }
        }
        return methodMap;
    }

    public static void main(String[] args)
    {
        ActivityRecruitAddress activity1 = new ActivityRecruitAddress();
        activity1.setCity("湖南擦擦擦");// 城市

        ActivityRecruitAddress activity2 = new ActivityRecruitAddress();
        activity2.setCity("db-----湖南");// 城市
        activity2.setCollege("db-----湘潭大学");// 大学
        activity2.setAddress("db-----第一教室");// 详细地址
        activity2.setSeatNumber(0);// 座位数量
        activity2.setCreateDate(DateUtils.getCurrentTimestamp());

        CompanyRecruitActivity activity = new CompanyRecruitActivity();
        activity.setCity("db-----湖南");// 城市
        activity.setCollege("db-----湘潭大学");// 大学
        activity.setAddress("db-----第一教室");// 详细地址
        activity.setSeatNumber(0);// 座位数量
        activity.setSignalStrength(0);// 号强度

        System.out.println(activity2.toString());
        copyProperties(activity2 , activity1);
        System.out.println(activity2.toString());
    }
}
