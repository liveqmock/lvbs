package com.daishumovie.utils;

/**
 * Created by yang on 2017/5/26.
 */
public class VersionUtil {

    public static int getAppBuild(String versionNum){
        if (versionNum == null || versionNum == "") return 10000;
        if (versionNum.indexOf(".") > 0){
            String[] arg = versionNum.split("[.]");
            if (arg.length == 2){
                return (int) (Integer.valueOf(arg[0]) * 10000 + Float.valueOf("0."+arg[1]) * 10000);
            }else{
                return Integer.valueOf(arg[0]) * 10000;
            }
        }
        return 10000;
    }

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     * @param version
     * @param other
     * @return
     */
    public static int compareVersion(String version, String other) throws Exception {
        if (version == null || other == null) {
            throw new Exception("compareVersion error:illegal params.");
        }
        String[] versionArray1 = version.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = other.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }
}
