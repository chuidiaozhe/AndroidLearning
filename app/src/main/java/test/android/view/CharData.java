package test.android.view;

import android.support.annotation.NonNull;

/**
 * Create by Xiangshifu
 * on 2018/7/12 下午3:29
 */
public class CharData implements  Comparable {
     private float data;
    private String labe;
    private String bottomLable;
    private int color;
    private float percent;


    public CharData(float data, int color) {
        this.data = data;
        this.color = color;
    }



    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    public String getLabe() {
        return labe;
    }

    public void setLabe(String labe) {
        this.labe = labe;
    }

    public String getBottomLable() {
        return bottomLable;
    }

    public void setBottomLable(String bottomLable) {
        this.bottomLable = bottomLable;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if(o instanceof  CharData){
            CharData charData = (CharData) o;
             if(charData.getData() > getData()){
                 return  1;
             }else if(charData.getData() == getData()){
                 return 0;
             }else if(charData.getData() < getData()){
                 return -1;
             }
        }
        return 0;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
