package com.eco.pub.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

/**
 * 类描述：计算gps坐标距离
 *
 * @author Ethan
 * @date 2016/6/14
 */
public class GpsTool {

    private static Log log = LogFactory.getLog(FileUtils.class);

    private  static double EARTH_RADIUS = 6378137; //WGS84  6378137.0 米

    private static double rad(double d){
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两点之间的距离
     * @param longitude1
     * @param latitude1
     * @param longitude2
     * @param latitude2
     * @return
     */
    public static int getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        int returnDistance = 0;
        try {
            double radLat1 = rad(latitude1);
            double radLat2 = rad(latitude2);
            double a = radLat1 - radLat2;
            double b = rad(longitude1) - rad(longitude2);
            double doubleDistance = 2 * Math.asin(Math.sqrt(Math.pow(
                    Math.sin(a / 2), 2)
                    + Math.cos(radLat1)
                    * Math.cos(radLat2)
                    * Math.pow(Math.sin(b / 2), 2)));
            doubleDistance = doubleDistance * EARTH_RADIUS;
            doubleDistance = Math.round(doubleDistance * 10000) / (double) 10000;
            returnDistance = (int) doubleDistance;
        } catch (Exception e) {
            log.error("getDistance error ...... longitude1=" + longitude1
                    + " latitude1==" + latitude1 + "longitude2=" + longitude2
                    + " latitude2==" + latitude2);
        }

        return returnDistance;
    }


    /**
     * 计算经纬度之间的距离
     * 1.java计算的较为精确, SQL计算能很好利用数据库的分页，但不够精确
     * 通过SQL来计算距离：SQRT(POWER(117-LONGITUDE,2)+POWER(37-LATITUDE,2)) * 140000 DIST
     * 2.然而, SQL和java计算的出来按照距离排序，由近到远是一致的
     * 3.由此可以先用SQL分页查询出距离，这个距离为不精确距离，只是用来排序；取出数据集之后，再用java来计算精确距离
     * @param resList 处理LIST
     * @param paramMap 参数
     * @param longtitudeName 获取参数经度键
     * @param lantitudeName 获取参数纬度键
     */
    public static void reCalculateDistance(List resList, Map paramMap, String longtitudeName, String lantitudeName) {
        double d1 = Double.parseDouble(paramMap.get("LONGITUDE").toString());// 参数经度
        double d2 = Double.parseDouble(paramMap.get("LATITUDE").toString());// 参数维度
        double d3;// 比较经度
        double d4;// 比较维度
        double dist;// 距离/米
        String org_long;
        String org_lan;
        Map temMap;
        for (int i = 0; i < resList.size(); i++) {
            temMap = (Map) resList.get(i);
            org_long = processNull(temMap.get(longtitudeName));
            org_lan = processNull(temMap.get(lantitudeName));
            d3 = Double.parseDouble("".equals(org_long) ? "0" : org_long);
            d4 = Double.parseDouble("".equals(org_lan) ? "0" : org_lan);
            dist = getDistance(d1, d2, d3, d4);// recalculate
            temMap.put("DIST", String.valueOf(dist));
        }
    }


    /**
     *
     * @param s
     * @return
     */
    public static String processNull(Object s) {
        return s == null ? "" : s.toString();
    }
}
