package com.action.eimoji;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: WebUploader
 * @description: Emoji
 * @author: Dai Yuanchuan
 * @create: 2018-07-11 16:55
 **/
@Controller
@RequestMapping(value = "/emoji")
public class EmojiUtil {

    private EmojiConverter emojiConverter = EmojiConverter.getInstance();

    /**
     * 将emojiStr转为 带有表情的字符
     * @param emojiStr
     * @return
     */
    @RequestMapping(value = "/emojiConverterUnicodeStr")
    public String emojiConverterUnicodeStr(String emojiStr){
        String result = emojiConverter.toUnicode(emojiStr);
        return result;
    }



    public String emojiConverterToAlias(String str){
        System.out.println(str);
        String result=emojiConverter.toAlias(str);
        System.out.println("==================================");
        System.out.println(EmojiParser.parseToHtmlDecimal(str));
        return result;
    }

    /**
     * 带有表情的字符串转换为编码
     * @param str
     * @return
     */
    @RequestMapping(value = "/emojiConverterToAlias")
    public static String emojiConvert1(String str)
            throws UnsupportedEncodingException {
        String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            try {
                matcher.appendReplacement(
                        sb,
                        "[["
                                + URLEncoder.encode(matcher.group(1),
                                "UTF-8") + "]]");
            } catch(UnsupportedEncodingException e) {
                e.printStackTrace();
                throw e;
            }
        }
        matcher.appendTail(sb);
        System.out.println("emojiConvert " + str + " to " + sb.toString()
                + ", len：" + sb.length());
        return sb.toString();
    }


}
