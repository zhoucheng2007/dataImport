package com.util;

/**
 * ���ڽ���-������ת�������ĺ���
 * @author : <a href="pengtao@foresee.com.cn">pengtao</a>
 * @version 1.0  $ 2010-3-3 21:43:11 $
 */

public class CMoneyUtil
{
    public static String[] CHINANUMBER = {"��","Ҽ","��","��","��",
                                          "��","½","��","��","��"};
    public static String CHINATEMKILO = "��";
    public static String CHINAHUNDREDMILLION = "��"; //���λ
    public static String[] CHINAREPEATUNIT = {"Ǫ","��","ʰ",""};

    public static String CHINAUNIT = "Բ";
    public static String CHINATENCENTUNIT = "��";
    public static String CHINACENTUNIT = "��";

    public static String CHINAFULL = "��";


    public static void main(String args[]){
        System.out.println("-----" + getChineseNum("2000000000000000.00"));
        System.out.println("-----" + getChineseNum(new Double(20005.02)));
    }

    /**
     * ����-�����ֵ��ַ�ת�������ĺ��ֵĻ�ұ�ʾ
     * @param arabiaNum
     * @return ���ĺ��ֵĻ�ұ�ʾ
     */
    public static String getChineseNum(String arabiaNum){
        return getMoneyStrCn(arabiaNum);
    }

    /**
     * ����-�����ֵ�Double��ת�������ĺ��ֵĻ�ұ�ʾ
     * @param arabiaNum
     * @return ���ĺ��ֵĻ�ұ�ʾ
     */
    public static String getChineseNum(Double arabiaNum){
        return getMoneyStrCn(arabiaNum);
    }

    public static String getMoneyStrCn(Object moneyObj){
        if(moneyObj == null){
            return "";
        }
        String moneyStr = String.valueOf(moneyObj).trim();
        if(moneyStr.equals("")){
            return "";
        }
        String moneyStrCn = "";
        try{
            Double.parseDouble(moneyStr);
        } catch(NumberFormatException nfe){
            throw new NumberFormatException("���ֹ���Ϊ�Ƿ���ʽ!");
        }

        if(moneyStr.startsWith("-")){
            System.out.println("[ERROR] : Ǯ������Ϊ����");
            return "";
        }
        if(moneyStr.startsWith("+")){
            moneyStr = moneyStr.substring(1);
        }

        int dotIndex = moneyStr.indexOf(".");
        String intStr = "";
        String decimalStr = "";
        if(dotIndex == -1){
            intStr = moneyStr;
        } else{
            if(dotIndex == 0){
                intStr = "";
                decimalStr = moneyStr.substring(1);
            } else{
                intStr = moneyStr.substring(0,dotIndex);
                decimalStr = moneyStr.substring(dotIndex + 1);
            }
        }

        int firstZeroCount = 0;
        for(int i = 0;i < intStr.length();i++){
            //�ж��ַ����� "0" �����  ��: 0045
            if(intStr.charAt(i) == '0'){
                firstZeroCount++;
            } else{
                break;
            }
        }
        intStr = intStr.substring(firstZeroCount);

        String intStrCn = "";
        if(intStr.equals("")){
            intStrCn = CHINANUMBER[0];
        } else{
            String[] tenKiloStrs = split(intStr,8);
            for(int i = 0;i < tenKiloStrs.length;i++){
                String tenKiloStr = tenKiloStrs[i];
                if(i == tenKiloStrs.length - 1){
                    intStrCn = intStrCn + getTenKiloStrCn(tenKiloStr);
                } else{
                    String tenKiloStrNext = tenKiloStrs[i + 1];
                    if(tenKiloStrNext.startsWith("0") && new Integer(tenKiloStrNext).intValue() != 0){
                        intStrCn = intStrCn + getTenKiloStrCn(tenKiloStr) + CHINAHUNDREDMILLION + CHINANUMBER[0];
                    } else{
                        intStrCn = intStrCn + getTenKiloStrCn(tenKiloStr) + CHINAHUNDREDMILLION;
                    }
                }
            }
        }

        String decimalStrCn = "";
        if(decimalStr.length() > 2) decimalStr = decimalStr.substring(0,2);
        if(decimalStr.length() == 1) decimalStr = decimalStr + "0";
        if(!decimalStr.equals("00") && !decimalStr.equals("")){
            if(decimalStr.charAt(0) == '0'){
                if(intStr.equals("") || new Integer(intStr).intValue() == 0){
                    decimalStrCn = "";
                } else
                    decimalStrCn = CHINANUMBER[0];
            } else{
                decimalStrCn = CHINANUMBER[new Integer(String.valueOf(decimalStr.charAt(0))).intValue()] + CHINATENCENTUNIT;
            }
            if(decimalStr.charAt(1) != '0'){
                decimalStrCn = decimalStrCn + CHINANUMBER[new Integer(String.valueOf(decimalStr.charAt(1))).intValue()] + CHINACENTUNIT;
            }
        }

        if((intStr.equals("")) && !decimalStrCn.equals("")){
            moneyStrCn = decimalStrCn + CHINAFULL;
        } else{
            moneyStrCn = intStrCn + CHINAUNIT + decimalStrCn + CHINAFULL;
        }
        return moneyStrCn;
    }


    public static String getTenKiloStrCn(String tenKiloStr){
        if(tenKiloStr == null || tenKiloStr.length() < 0 || tenKiloStr.length() > 8){
            System.out.println("[ERROR] : ��λ�ַ��ȸ�ʽ����");
            return "";
        }
        String tenKiloStrCn = "";
        String[] kiloStrs = split(tenKiloStr,4);
        if(kiloStrs.length == 1){
            tenKiloStrCn = getKiloStrCn(kiloStrs[0]);
        } else if(new Integer(kiloStrs[0]).intValue() == 0){
            tenKiloStrCn = getKiloStrCn(kiloStrs[1]);
        } else{
            if(kiloStrs[1].startsWith("0") && new Integer(kiloStrs[1]).intValue() != 0){
                tenKiloStrCn = getKiloStrCn(kiloStrs[0]) + CHINATEMKILO + CHINANUMBER[0] + getKiloStrCn(kiloStrs[1]);
            } else{
                tenKiloStrCn = getKiloStrCn(kiloStrs[0]) + CHINATEMKILO + getKiloStrCn(kiloStrs[1]);
            }
        }
        return tenKiloStrCn;
    }

    public static String getKiloStrCn(String kiloStr){
        if(kiloStr == null || kiloStr.length() < 0 || kiloStr.length() > 4){
            System.out.println("[ERROR] : ǧλ�ַ��ȸ�ʽ����");
            return "";
        }
        String zeroStr = "";
        for(int i = 0;i < 4 - kiloStr.length();i++){
            zeroStr = zeroStr + "0";
        }
        kiloStr = zeroStr + kiloStr;

        String kiloStrCn = "";
        int lastZeroCount = 0;
        int firstZeroCount = 0;
        for(int i = kiloStr.length() - 1;i >= 0;i--){
            //�ж�ĩβ�� "0" �����  ��: 4500
            if(kiloStr.charAt(i) == '0'){
                lastZeroCount++;
            } else{
                break;
            }
        }
        for(int i = 0;i < kiloStr.length();i++){
            //�ж��ַ����� "0" �����  ��: 0045
            if(kiloStr.charAt(i) == '0'){
                firstZeroCount++;
            } else{
                break;
            }
        }
        if(firstZeroCount == 0 && lastZeroCount == 0 && kiloStr.length() == 4 &&
                kiloStr.charAt(1) == '0' && kiloStr.charAt(2) == '0'){
            kiloStrCn = CHINANUMBER[new Integer(String.valueOf(kiloStr.charAt(0))).intValue()] + CHINAREPEATUNIT[0]
                    + CHINANUMBER[0] + CHINANUMBER[new Integer(String.valueOf(kiloStr.charAt(3))).intValue()];
        } else{
            for(int i = firstZeroCount;i < kiloStr.length() - lastZeroCount;i++){
                int num = new Integer(String.valueOf(kiloStr.charAt(i))).intValue();
                if(num == 0){
                    kiloStrCn = kiloStrCn + CHINANUMBER[num];
                } else{
                    kiloStrCn = kiloStrCn + CHINANUMBER[num] + CHINAREPEATUNIT[i];
                }
            }
        }
        return kiloStrCn;
    }

    public static String[] split(String str,int length){
        int count = str.length() / length;
        int ys = str.length() % length;
        if(ys > 0) count++;
        String[] strs = new String[count];
        if(ys == 0){
            for(int i = 0;i < count;i++){
                strs[i] = str.substring(i * length,(i + 1) * length);
            }
        } else{
            strs[0] = str.substring(0,ys);
            for(int i = 1;i < count;i++){
                strs[i] = str.substring(ys + (i - 1) * length,ys + i * length);
            }
        }

        return strs;
    }
}
