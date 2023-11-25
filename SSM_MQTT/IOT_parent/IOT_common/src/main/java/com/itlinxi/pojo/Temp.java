
package com.itlinxi.pojo;

/*
        温度的实体
 */
import java.io.Serializable;

public class Temp implements Serializable {
    private Integer id;  //序号
    private String time;//时间
    private float tempNum;//温度

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getTempNum() {
        return tempNum;
    }

    public void setTempNum(float tempNum) {
        this.tempNum = tempNum;
    }
}
