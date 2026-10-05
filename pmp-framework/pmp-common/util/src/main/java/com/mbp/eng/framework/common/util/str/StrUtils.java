package com.mbp.eng.framework.common.util.str;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StrUtils {
    private static final Logger logger = LoggerFactory.getLogger(StrUtils.class);

    private static String pattern = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";

    public static String maxLength(CharSequence str, int maxLength) {
        return cn.hutool.core.util.StrUtil.maxLength(str, maxLength - 3); // -3 的原因,是该方法会补充 ... 恰好
    }

    /**
     * 给定字符串是否以任何一个字符串开始
     * 给定字符串和数组为空都返回 false
     *
     * @param str      给定字符串
     * @param prefixes 需要检测的开始字符串
     * @since 3.0.6
     */
    public static boolean startWithAny(String str, Collection<String> prefixes) {
        if (cn.hutool.core.util.StrUtil.isEmpty(str) || ArrayUtil.isEmpty(prefixes)) {
            return false;
        }

        for (CharSequence suffix : prefixes) {
            if (cn.hutool.core.util.StrUtil.startWith(str, suffix, false)) {
                return true;
            }
        }
        return false;
    }

    public static List<Long> splitToLong(String value, CharSequence separator) {
        long[] longs = cn.hutool.core.util.StrUtil.splitToLong(value, separator);
        return Arrays.stream(longs).boxed().collect(Collectors.toList());
    }

    public static Set<Long> splitToLongSet(String value) {
        return splitToLongSet(value, StrPool.COMMA);
    }

    public static Set<Long> splitToLongSet(String value, CharSequence separator) {
        long[] longs = cn.hutool.core.util.StrUtil.splitToLong(value, separator);
        return Arrays.stream(longs).boxed().collect(Collectors.toSet());
    }

    public static List<Integer> splitToInteger(String value, CharSequence separator) {
        int[] integers = cn.hutool.core.util.StrUtil.splitToInt(value, separator);
        return Arrays.stream(integers).boxed().collect(Collectors.toList());
    }

    /**
     * 移除字符串中,包含指定字符串的行
     *
     * @param content  字符串
     * @param sequence 包含的字符串
     * @return 移除后的字符串
     */
    public static String removeLineContains(String content, String sequence) {
        if (cn.hutool.core.util.StrUtil.isEmpty(content) || cn.hutool.core.util.StrUtil.isEmpty(sequence)) {
            return content;
        }
        return Arrays.stream(content.split("\n"))
                .filter(line -> !line.contains(sequence))
                .collect(Collectors.joining("\n"));
    }

    /**
     * 转小写拼音,字之间以空格分隔,便于调用方按需拼接 / 取首字母 / 拼音搜索
     * <p>
     * 例:「老张」→ "lao zhang"、「ZhangSan」→ "zhangsan"
     * 英文 / 数字 / 符号原样返回,空值返回 null
     * <p>
     * 注意:底层依赖 hutool-extra 的 {@link PinyinUtil},需要业务模块自行引入拼音引擎依赖
     * (pinyin4j / TinyPinyin / Bopomofo4j 任选其一),否则运行时会抛 NoClassDefFoundError
     *
     * @param str 字符串
     * @return 拼音串(保留空格分隔 )
     */
    public static String toPinyin(String str) {
        if (cn.hutool.core.util.StrUtil.isBlank(str)) {
            return null;
        }
        return PinyinUtil.getPinyin(str);
    }

    /**
     * 拼接方法的参数
     * <p>
     * 特殊:排除一些无法序列化的参数,如 ServletRequest、ServletResponse、MultipartFile
     *
     * @param joinPoint 连接点
     * @return 拼接后的参数
     */
    public static String joinMethodArgs(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (ArrayUtil.isEmpty(args)) {
            return "";
        }
        return ArrayUtil.join(args, ",", item -> {
            if (item == null) {
                return "";
            }
            // 讨论可见:https://t.zsxq.com/XUJVk、https://t.zsxq.com/MnKcL
            String clazzName = item.getClass().getName();
            if (cn.hutool.core.util.StrUtil.startWithAny(clazzName, "javax.servlet", "jakarta.servlet", "org.springframework.web")) {
                return "";
            }
            return item;
        });
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 过滤掉中文
     *
     * @param str 待过滤中文的字符串
     * @return 过滤掉中文后字符串
     */
    public static String filterChinese(String str) {
        // 用于返回结果
        String result = str;
        boolean flag = isContainChinese(str);
        if (flag) { // 包含中文
            // 用于拼接过滤中文后的字符
            StringBuffer stringBuffer = new StringBuffer();
            // 用于校验是否为中文
            boolean flag2 = false;
            // 用于临时存储单字符
            char chinese = 0;
            // 5.去除掉文件名中的中文
            // 将字符串转换成char[]
            char[] charArray = str.toCharArray();
            // 过滤到中文及中文字符
            for (int i = 0; i < charArray.length; i++) {
                chinese = charArray[i];
                flag2 = isChinese(chinese);
                if (!flag2) { // 不是中日韩文字及标点符号
                    stringBuffer.append(chinese);
                }
            }
            result = stringBuffer.toString();
        }
        return result;
    }

    /**
     * 判断字符是否为汉字
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 判断 url 是否合法
     */
    public static boolean isUrl(String url) {
        Pattern httpPattern = Pattern.compile(pattern);
        if (httpPattern.matcher(url).matches()) {
            return true;
        }
        return false;
    }

    /**
     * 将字符串中的变量替换为map中的值
     *
     * @param str
     * @param map
     * @return
     */
    public static String resolvedResult(String str, Map<String, Object> map) {
        Matcher matcher = Pattern.compile("\\$\\{\\w+\\}").matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String string = matcher.group();
            Object value = map.get(string.substring(2, string.length() - 1));
            if (value != null) {
                matcher.appendReplacement(stringBuffer, value.toString());
            }
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    /**
     * 将字符串转换成一个InputStream流
     *
     * @param data
     * @return
     */
    public InputStream toConvertInputStream(String data) {
        InputStream inputStream = null;
        try {
            byte[] content = data.getBytes("UTF-8");
            inputStream = new ByteArrayInputStream(content);
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            logger.error(e.getMessage());
        }
        return inputStream;
    }

    /**
     * 合并 InputStream 流
     *
     * @param inputStreamList
     * @return
     * @throws IOException
     */
    public SequenceInputStream createSequenceInputStream(List<InputStream> inputStreamList) throws IOException {
        Enumeration<InputStream> enumeration = Collections.enumeration(inputStreamList);
        SequenceInputStream sequenceInputStream = new SequenceInputStream(enumeration);
        //System.out.println("==========" + toConvertString(sequenceInputStream));
        return sequenceInputStream;
    }
}