package com.streamliners.myecom.fcmsender;

public class MessageBuilder {

    static String FORMAT = "{\n" +
            "  \"to\": \"/topics/admin\",\n" +
            "  \"notification\": {\n" +
            "    \"title\": \"New order received!\",\n" +
            "    \"body\": \"%s ordered %d items worth Rs. %s.\",\n" +
            "    \"priority\": \"high\",\n" +
            "    \"icon\": \"ic_order\",\n" +
            "    \"color\": \"#03DAC5\"\n" +
            "  }\n" +
            "}";

    public static String buildNewOrderMessage(String userName, int noOfItems, int total){
        return String.format(FORMAT, userName, noOfItems, total);
    }

}
