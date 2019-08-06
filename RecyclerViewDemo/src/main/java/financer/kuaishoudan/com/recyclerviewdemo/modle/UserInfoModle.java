package financer.kuaishoudan.com.recyclerviewdemo.modle;

/**
 * Create by Xiangshifu
 * on 2019/1/15 10:48 AM
 */
public class UserInfoModle {
    private String userName;
    private String address;
    private int age;

    public UserInfoModle() {
    }

    public UserInfoModle(String userName, String address, int age) {
        this.userName = userName;
        this.address = address;
        this.age = age;
    }

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
