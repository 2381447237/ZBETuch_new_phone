package com.youli.zbetuch.jingan.utils;

import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者: zhengbin on 2017/11/28.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 身份证验证
 */

public class SfzUtils {

    public static final String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
    public static final String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
    private final static int NEW_CARD_NUMBER_LENGTH = 18;
    private final static int OLD_CARD_NUMBER_LENGTH = 15;
    // 身份证的最小出生日期,1900年1月1日
    private final static Date MINIMAL_BIRTH_DATE = new Date(-2209017600000L);




    /**
     * 不为空时，验证str是否为正确的身份证格式
     *
     * @param view
     * @return
     */
    public static boolean maybeIsIdentityCard(EditText view) {
        boolean flag = true;
        String licenc = view.getText().toString();
//        DebugSetting.toLog("身份证号="+licenc);
        if (!licenc.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            /*
             * { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
             * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
             * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
             * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
             * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
             * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外" }
             *
             *	身份证18位分别代表的含义，身份证15位升级到18位，原来年用2位且没有最后一位，从左到右方分别表示
             *   ①1-2 升级行政区代码
             *   ②3-4 地级行政区划分代码
             *  ③5-6 县区行政区分代码
             *    ④7-10 11-12 13-14 出生年、月、日
             *    ⑤15-17 顺序码，同一地区同年、同月、同日出生人的编号，奇数是男性，偶数是女性
             *   ⑥18 校验码，如果是0-9则用0-9表示，如果是10则用X（罗马数字10）表示
             *   公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位校验码。其含义如下： 　
　           *   1. 地址码：表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 　　
             *   2. 出生日期码：表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日分别用4位、2位、2位数字表示，之间不用分隔符。 　
　           *    3. 顺序码：表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。 　
             * 　校验的计算方式： 　　1. 对前17位数字本体码加权求和 　　公式为：S = Sum(Ai * Wi), i = 0, ... , 16 　　
             *  其中Ai表示第i位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 　　
             *  2. 以11对计算结果取模 　　Y = mod(S, 11) 　　3. 根据模的值得到对应的校验码 　　对应关系为： 　　
             *  Y值：    0 1 2 3 4 5 6 7 8 9 10 　　校验码： 1 0 X 9 8 7 6 5 4 3  2
             *
             */
            String provinces = "11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91";

            Pattern pattern = Pattern.compile("^[1-9]\\d{14}");
            Matcher matcher = pattern.matcher(licenc);
            Pattern pattern2 = Pattern.compile("^[1-9]\\d{16}[\\d,x,X]$");
            Matcher matcher2 = pattern2.matcher(licenc);
            // 粗略判断
            if (!matcher.find() && !matcher2.find()) {
                view.setError("身份证号必须为15或18位数字（最后一位可以为X）");
                flag = false;
            } else {
                // 判断出生地
                if (provinces.indexOf(licenc.substring(0, 2)) == -1) {
                    view.setError("身份证号前两位不正确！");
                    flag = false;
                    return flag;
                }

                // 判断出生日期
                if (licenc.length() == 15) {
                    String birth = "19" + licenc.substring(6, 8) + "-"
                            + licenc.substring(8, 10) + "-"
                            + licenc.substring(10, 12);
                    try {
                        Date birthday = sdf.parse(birth);
                        if (!sdf.format(birthday).equals(birth)) {
                            view.setError("出生日期非法！");
                            flag = false;
                            return flag;
                        }
                        if (birthday.after(new Date())) {
                            view.setError("出生日期不能在今天之后！");
                            flag = false;
                            return flag;
                        }
                        if(!birthday.after(MINIMAL_BIRTH_DATE)){
                            view.setError("身份证的最小出生日期,1900年1月1日！");
                            flag = false;
                            return flag;
                        }
                    } catch (ParseException e) {
                        view.setError("出生日期非法！");
                        flag = false;
                        return flag;
                    }
                } else if (licenc.length() == 18) {
                    String birth = licenc.substring(6, 10) + "-"
                            + licenc.substring(10, 12) + "-"
                            + licenc.substring(12, 14);
                    try {
                        Date birthday = sdf.parse(birth);
                        if (!sdf.format(birthday).equals(birth)) {
                            view.setError("出生日期非法！");
                            flag = false;
                            return flag;
                        }
                        if(!birthday.after(MINIMAL_BIRTH_DATE)){
                            view.setError("身份证的最小出生日期,1900年1月1日！");
                            flag = false;
                            return flag;
                        }
                        if (birthday.after(new Date())) {
                            view.setError("出生日期不能在今天之后！");
                            flag = false;
                            return flag;
                        }//校验身份证最后一位 身份证校验码
                        if(!calculateVerifyCode(licenc) .equals(String.valueOf(licenc.charAt(NEW_CARD_NUMBER_LENGTH - 1)))){
                            view.setError("身份证最后一位校验码有误！");
                            flag = false;
                            return flag;
                        }
                    } catch (ParseException e) {
                        view.setError("出生日期非法！");
                        flag = false;
                    }
                } else {
                    view.setError("身份证号位数不正确，请确认！");
                    flag = false;
                }
            }
            if (!flag) {
                view.requestFocus();
            }
        }else{
            view.setError("请输入您的身份证号");
        }
        return flag;
    }
    /**计算最后一位校验码  加权值%11
     * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
     *      Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
     * （2）计算模 Y = mod(S, 11)
     * （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     * @param cardNumber
     * @return
     */
    private static String calculateVerifyCode(CharSequence cardNumber) {
        int sum = 0;
        for (int i = 0; i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            char ch = cardNumber.charAt(i);
            sum += (ch - '0') * Integer.parseInt(Wi[i]);
        }
        return ValCodeArr[sum % 11];
    }

}
