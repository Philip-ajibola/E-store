package com.semicolon.africa.Estore.utils;

public class HTMLDesigns {

    public static String sendWelcomeMessage(String username){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        .header {\n" +
                "            background-color: #f2f2f2;\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .content {\n" +
                "            margin: 20px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            background-color: #f2f2f2;\n" +
                "            padding: 10px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"header\">\n" +
                "    <h1>Welcome to Our Service</h1>\n" +
                "</div>\n" +
                "<div class=\"content\">\n" +
                "    <p>Hello " + username + " </p>\n" +
                "    <p>Hello Welcome to Bobby's Store The Best Online Store YOu Can Ever Find All Around Lagos</p>\n" +
                "    <p>Thank you for joining our service. We are excited to have you on board.</p>\n" +
                "</div>\n" +
                "<div class=\"footer\">\n" +
                "    <p>&copy; 2024 Your Bobby's Store. All rights reserved.</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";
    }

    public static String sendOrderMessage(String  sellername){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        .header {\n" +
                "            background-color: #f2f2f2;\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .content {\n" +
                "            margin: 20px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            background-color: #f2f2f2;\n" +
                "            padding: 10px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"header\">\n" +
                "    <h1>Welcome to Our Service</h1>\n" +
                "</div>\n" +
                "<div class=\"content\">\n" +
                "    <p>Dear " + sellername + " </p>\n" +
                "    <p>We are pleased to inform you that a new order has been placed for the following item\n</p>\n" +
                "    <button href=\"#\"   style = background-color = blue; color:white; font-weight: bold; border:none > View Order</button>\n" +
                "</div>\n" +
                "<div class=\"footer\">\n" +
                "    <p>&copy; 2024 Your Bobby's Store. All rights reserved.</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";
    }

}
