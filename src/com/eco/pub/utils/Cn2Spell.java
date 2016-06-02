package com.eco.pub.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 *
 * 类描述：汉字转化为英文工具类，依赖于pinyin4j-2.5.0.jar
 *
 * @author Ethan
 * @date 2016年2月26日
 * 
 * 修改描述：
 * @modifier
 */
public class Cn2Spell {
    
    private static Log log = LogFactory.getLog(Cn2Spell.class);
	
	/**
	 * 
	 * 方法描述: 汉字转换位汉语拼音首字母，英文字符不变
	 * 
	 * @param chines
	 * @return String
	 * @author Ethan 2016年2月26日
	 *
	 * 修改描述：
	 * @modifier
	 */
	public static String converterToFirstSpell(String chines) {
		StringBuilder pinyinName = new StringBuilder();
		try{
		    char[] nameChar = chines.toCharArray();
	        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	        for (int i = 0; i < nameChar.length; i++) {
	            if (nameChar[i] > 128) {
	                try {
	                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0));
	                } catch (BadHanyuPinyinOutputFormatCombination e) {
	                    e.printStackTrace();
	                }
	            } else {
	                pinyinName.append(nameChar[i]);
	            }
	        }
        } catch (RuntimeException e) {
            log.error("Cn2Spell---converterToFirstSpell---RuntimeException:", e);
        } catch (Exception e) {
            log.error("Cn2Spell---converterToFirstSpell---Exception:", e);
        } catch (Throwable t) {
            log.error("Cn2Spell---converterToFirstSpell---Throwable:", t);
        } 
		return pinyinName.toString();
	}

	
	/**
	 * 
	 * 汉字转换位汉语拼音，英文字符不变
	 * 
	 * @param chines
	 * @return String
	 * @author Ethan 2016年2月26日
	 *
	 * 修改描述：
	 * @modifier
	 */
	public static String converterToSpell(String chines) {
		StringBuilder pinyinName = new StringBuilder();
		try {
		    char[] nameChar = chines.toCharArray();
	        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	        for (int i = 0; i < nameChar.length; i++) {
	            if (nameChar[i] > 128) {
	                try {
	                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0]);
	                } catch (BadHanyuPinyinOutputFormatCombination e) {
	                    e.printStackTrace();
	                }
	            } else {
	                pinyinName.append(nameChar[i]);
	            }
	        }
        } catch (RuntimeException e) {
            log.error("Cn2Spell---converterToSpell---RuntimeException:", e);
        } catch (Exception e) {
            log.error("Cn2Spell---converterToSpell---Exception:", e);
        } catch (Throwable t) {
            log.error("Cn2Spell---converterToSpell---Throwable:", t);
        } 
		
		return pinyinName.toString();
	}

	public static void main(String[] args) {
		System.out.println(converterToFirstSpell("中华软"));
		System.out.println(converterToSpell("中华软"));
	}
}
