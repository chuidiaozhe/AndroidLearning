package financer.kuaishoudan.com.recyclerviewdemo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import financer.kuaishoudan.com.recyclerviewdemo.modle.UserInfoModle;

/**
 * Create by Xiangshifu
 * on 2019/1/15 10:55 AM
 */
public  class Utils {
    private static List<UserInfoModle> userData = new ArrayList<>();

    private static List<UserInfoModle> userInfoModleList = new ArrayList<>();

    static {
        for (int i = 0; i <  100; i++) {
            UserInfoModle userInfoModle = new UserInfoModle("天安门"+i,"地址：地处北京市东城区东长安街，北起天安门，南至正阳门",i+20);
            userData.add(userInfoModle);
        }
    }

    static {
        String adress = "地址：地处北京市东城区东长安街，北起天安门，南至正阳门";
        Random random = new Random();
        for (int i = 0; i <  100; i++) {
            UserInfoModle userInfoModle = new UserInfoModle();
            userInfoModle.setUserName("天安门"+i);

            userInfoModle.setAddress(adress.substring(random.nextInt(adress.length())));
            userInfoModleList.add(userInfoModle);
        }
    }


    public static List<UserInfoModle> getUserData(){
        return  userData;
    }

    public static List<UserInfoModle> getUserInfoModleList(){
        return  userInfoModleList;
    }


}
