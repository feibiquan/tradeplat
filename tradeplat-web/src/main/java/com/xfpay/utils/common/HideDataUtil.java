package com.xfpay.utils.common;

/**
 * 身份信息隐藏
 */
public class HideDataUtil {

	/**
     * 前六后四 隐藏银行卡号
     *
     * @param cardNo
     * @return String
     */
    public static String hideCardNo(String cardNo) {
        if (StringUtils.isBlank(cardNo)) {
            return cardNo;
        }

        if (cardNo.length() > 10) {
            //前六后四
            StringBuilder stringBuilder = new StringBuilder();
            return stringBuilder.append(cardNo.substring(0, 6)).append("****")
                    .append(cardNo.substring(cardNo.length() - 4)).toString();
        } else {
            return cardNo;
        }
//            return cardNo.replaceAll("(\\d{6})\\d{10}(\\d{4})","$1*****$2");
    }


    /**
     * 前六后四 隐藏证件号码
     *
     * @param idCard
     * @return String
     */
    public static String hideIdCard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return idCard;
        }
        return idCard.replaceAll("(\\d{4})\\d{10}(\\d{4})","$1*****$2");
    }

    /**
     * 前三后四 隐藏手机号
     *
     * @param phoneNo
     * @return String
     */
    public static String hidePhoneNo(String phoneNo) {
        if (StringUtils.isBlank(phoneNo)) {
            return phoneNo;
        }

        if (phoneNo.length() > 7) {
//        前3后四
            StringBuilder stringBuilder = new StringBuilder();
            return stringBuilder.append(phoneNo.substring(0, 3)).append("****")
                    .append(phoneNo.substring(phoneNo.length() - 4)).toString();
        } else {
            return phoneNo;
        }
//        return phoneNo.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }

    /**
     * 前一后一 隐藏姓名
     *
     * @param name
     * @return String
     */
    public static String hideName(String name) {
        if (StringUtils.isBlank(name)) {
            return name;
        }

        if (name.length() > 2) {
//        前3后四
            StringBuilder stringBuilder = new StringBuilder();
            return stringBuilder.append(name.substring(0, 1)).append("*")
                    .append(name.substring(name.length() - 1)).toString();
        } else if(name.length() == 2){
            StringBuilder stringBuilder = new StringBuilder();
            return stringBuilder.append(name.substring(0, 1)).append("*").toString();
        } else {
            return name;
        }
//        return name.replaceAll("(\\d{1})\\d{1}(\\d{4})","$1*$2");
    }
}
