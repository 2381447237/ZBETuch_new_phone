package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 作者: zhengbin on 2017/10/24.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class PersonUpdataImg implements Serializable {
    private String personSfz;
    private byte[] personheadImg;

    public PersonUpdataImg() {
        super();
        // TODO Auto-generated constructor stub
    }

    public PersonUpdataImg(String personSfz, byte[] personheadImg) {
        super();
        this.personSfz = personSfz;
        this.personheadImg = personheadImg;
    }

    public String getPersonSfz() {
        return personSfz;
    }

    public void setPersonSfz(String personSfz) {
        this.personSfz = personSfz;
    }

    public byte[] getPersonheadImg() {
        return personheadImg;
    }

    public void setPersonheadImg(byte[] personheadImg) {
        this.personheadImg = personheadImg;
    }

    @Override
    public String toString() {
        return "PersonUpdataImg [personSfz=" + personSfz + ", personheadImg="
                + Arrays.toString(personheadImg) + "]";
    }

}