/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.string;
import java.util.Collection;

import org.melior.service.exception.ApplicationException;
import org.melior.service.exception.ExceptionType;
import org.springframework.util.StringUtils;

/**
 * Utility functions that scan or modify strings.
 * The functions are {@code null} safe.
 * @author Melior
 * @since 2.0
 */
public interface StringUtil{

  /**
   * Return substring of string.
   * If the string is {@code null} the result is {@code null}.
   * If the length is 0 the result is the empty string.
   * @param string The string
   * @param offset The offset
   * @param length The required length
   * @return The substring or an empty string if the length is 0
   */
  public static String substring(
    final String string,
    final int offset,
    final int length){
    return (string == null) ? string : (length <= 0) ? "" : string.substring(offset, Math.min((offset + length), string.length()));
  }

  /**
   * Return substring of string between opening token and closing token.
   * If the string is {@code null} or the opening token is
   * {@code null} or the closing token is {@code null} the
   * result is {@code null}.
   * @param string The string
   * @param open The opening token
   * @param close The closing token
   * @return The substring or {@code null} if either the opening token or the closing token is not present
   */
  public static String substringBetween(
    final String string,
    final String open,
    final String close){
        int start;
    int end;
    String result;

        if ((string == null) || (open == null) || (close == null)){
      return null;
    }

        result = null;

        start = string.indexOf(open);

        if (start != -1){
            end = string.indexOf(close, start + open.length());

            if (end != -1){
                result = string.substring(start + open.length(), end);
      }

    }

    return result;
  }

  /**
   * Convert string to upper case.
   * If the string is {@code null} the result is {@code null}.
   * @param string The string
   * @return The converted string
   */
  public static String toUpperCase(
    final String string){
    return (string == null) ? string : string.toUpperCase();
  }

  /**
   * Convert string to lower case.
   * If the string is {@code null} the result is {@code null}.
   * @param string The string
   * @return The converted string
   */
  public static String toLowerCase(
    final String string){
    return (string == null) ? string : string.toLowerCase();
  }

  /**
   * Convert delimited string to upper camel case.
   * If the string is {@code null} or the delimiter is {@code null} the result is {@code null}.
   * @param string The string
   * @param delimiter The delimiter
   * @return The converted string
   */
  public static String toUpperCamel(
    final String string,
    final String delimiter){
        String[] components;

        if ((string == null) || (delimiter == null)){
      return null;
    }

        components = string.split("[" + delimiter + "]");

        for (int i = 0; i < components.length; i++){
      components[i] = StringUtils.capitalize(components[i]);
    }

        return join(components);
  }

  /**
   * Convert delimited string to lower camel case.
   * If the string is {@code null} or the delimiter is {@code null} the result is {@code null}.
   * @param string The string
   * @param delimiter The delimiter
   * @return The converted string
   */
  public static String toLowerCamel(
    final String string,
    final String delimiter){
        String[] components;

        if ((string == null) || (delimiter == null)){
      return null;
    }

        components = string.split("[" + delimiter + "]");

        for (int i = 1; i < components.length; i++){
      components[i] = StringUtils.capitalize(components[i]);
    }

        return join(components);
  }

  /**
   * Convert string to character array.
   * If the string is {@code null} the result is {@code null}.
   * @param string The string
   * @return The character array
   */
  public static char[] toCharArray(
    final String string){
    return (string == null) ? null : string.toCharArray();
  }

  /**
   * Trim leading and trailing whitespace from string.
   * If the string is {@code null} the result is {@code null}.
   * @param string The string
   * @return The trimmed string
   */
  public static String trim(
    final String string){
    return (string == null) ? string : string.trim();
  }

  /**
   * Trim leading whitespace from string.
   * If the string is {@code null} the result is {@code null}.
   * @param string The string
   * @return The trimmed string
   */
  public static String trimLeft(
    final String string){
        int length;
    int index;
    char[] value;

        if (string == null){
      return string;
    }

        length = string.length();

        index = 0;

        value = string.toCharArray();

        while ((index < length) && (value[index] <= ' ')){
      index++;
    }

    return (index > 0) ? string.substring(index) : string;
  }

  /**
   * Trim trailing whitespace from string.
   * If the string is {@code null} the result is {@code null}.
   * @param string The string to trim
   * @return The trimmed string
   */
  public static String trimRight(
    final String string){
        int length;
    int index;
    char[] value;

        if (string == null){
      return string;
    }

        length = string.length();

        index = length;

        value = string.toCharArray();

        while ((index > 0) && (value[index - 1] <= ' ')){
      index--;
    }

    return (index < length) ? string.substring(0, index) : string;
  }

  /**
   * Pad string with leading padding up to the specified length.
   * If the string is {@code null} the result is {@code null}.
   * @param string The string
   * @param length The length
   * @param padding The padding
   * @return The padded string
   */
  public static String padLeft(
    final String string,
    final int length,
    final String padding){
        StringBuilder result;

        if (string == null){
      return string;
    }

        result = new StringBuilder(string);

    while (result.length() < length){
      result.insert(0, padding);
    }

    return result.toString();
  }

  /**
   * Pad string with trailing padding up to the specified length.
   * If the string is {@code null} the result is {@code null}.
   * @param string The string
   * @param length The length
   * @param padding The padding
   * @return The padded string
   */
  public static String padRight(
    final String string,
    final int length,
    final String padding){
        StringBuilder result;

        if (string == null){
      return string;
    }

        result = new StringBuilder(string);

    while (result.length() < length){
      result.append(padding);
    }

    return result.toString();
  }

  /**
   * Check whether string has prefix.
   * If the string is {@code null} the result is {@code false}.
   * @param string The string
   * @param prefix The prefix
   * @return true if the string has the prefix, false otherwise
   */
  public static boolean startsWith(
    final String string,
    final String prefix){
    return (string != null) && (string.startsWith(prefix) == true);
  }

  /**
   * Check whether string has post-fix.
   * If the string is {@code null} the result is {@code false}.
   * @param string The string
   * @param postfix The post-fix
   * @return true if the string has the post-fix, false otherwise
   */
  public static boolean endsWith(
    final String string,
    final String postfix){
    return (string != null) && (string.endsWith(postfix) == true);
  }

  /**
   * Check whether strings have equal content.
   * @param string1 The first string
   * @param string2 The second string
   * @return true if the strings have equal content, false otherwise 
   */
  public static boolean contentEqual(
    final String string1,
    final String string2){
    return ((string1 == null) && (string2 == null)) || ((string1 != null) && (string1.contentEquals(string2) == true));
  }

  /**
   * Check whether string contains substring.
   * @param string The string
   * @param substring The substring
   * @return true if the string contains the substring, false otherwise 
   */
  public static boolean contains(
    final String string,
    final String substring){
    return ((string != null) && (string.indexOf(substring) != -1));
  }

  /**
   * Join {@code String} representation of objects together.
   * If the array of objects is {@code null} the result is {@code null}.
   * @param objects The array of objects
   * @return The concatenated string
   */
  @SafeVarargs
  public static <T> String join(
    final T... objects){
        StringBuilder stringBuilder;

        if (objects == null){
      return null;
    }

        stringBuilder = new StringBuilder();

    for (int i = 0; i < objects.length; i++){
      stringBuilder.append(objects[i]);
    }

    return stringBuilder.toString();
  }

  /**
   * Join compact {@code String} representation of objects together.
   * If the array of objects is {@code null} the result is {@code null}.
   * @param objects The array of objects
   * @return The concatenated string
   */
  @SafeVarargs
  public static <T extends CompactString> String joinCompact(
    final T... objects){
        StringBuilder stringBuilder;

        if (objects == null){
      return null;
    }

        stringBuilder = new StringBuilder();

    for (int i = 0; i < objects.length; i++){
      stringBuilder.append(objects[i].toCompactString());
    }

    return stringBuilder.toString();
  }

  /**
   * Join {@code String} representation of objects together.
   * If the list of objects is {@code null} the result is {@code null}.
   * @param objects The list of objects
   * @return The concatenated string
   */
  public static <T> String join(
    final Collection<T> objects){
        StringBuilder stringBuilder;

        if (objects == null){
      return null;
    }

        stringBuilder = new StringBuilder();

    for (T object : objects){
      stringBuilder.append(object);
    }

    return stringBuilder.toString();
  }

  /**
   * Join compact {@code String} representation of objects together.
   * If the list of objects is {@code null} the result is {@code null}.
   * @param objects The list of objects
   * @return The concatenated string
   */
  public static <T extends CompactString> String joinCompact(
    final Collection<T> objects){
        StringBuilder stringBuilder;

        if (objects == null){
      return null;
    }

        stringBuilder = new StringBuilder();

    for (T object : objects){
      stringBuilder.append(object.toCompactString());
    }

    return stringBuilder.toString();
  }

  /**
   * Join {@code String} representation of objects together with delimiter in between.
   * If the array of objects is {@code null} the result is {@code null}.
   * @param objects The array of objects
   * @param delimiter The delimiter
   * @return The delimited string
   */
  public static <T> String join(
    final T[] objects,
    final String delimiter){
        StringBuilder stringBuilder;

        if (objects == null){
      return null;
    }

        stringBuilder = new StringBuilder();

        for (int i = 0; i < objects.length; i++){

      if (i > 0){
        stringBuilder.append(delimiter);
      }

      stringBuilder.append(objects[i]);
    }

    return stringBuilder.toString();
  }

  /**
   * Join compact {@code String} representation of objects together with delimiter in between.
   * If the array of objects is {@code null} the result is {@code null}.
   * @param objects The array of objects
   * @param delimiter The delimiter
   * @return The delimited string
   */
  public static <T extends CompactString> String joinCompact(
    final T[] objects,
    final String delimiter){
        StringBuilder stringBuilder;

        if (objects == null){
      return null;
    }

        stringBuilder = new StringBuilder();

        for (int i = 0; i < objects.length; i++){

      if (i > 0){
        stringBuilder.append(delimiter);
      }

      stringBuilder.append(objects[i].toCompactString());
    }

    return stringBuilder.toString();
  }

  /**
   * Join {@code String} representation of objects together with delimiter in between.
   * If the list of objects is {@code null} the result is {@code null}.
   * @param objects The list of objects
   * @param delimiter The delimiter
   * @return The delimited string
   */
  public static <T> String join(
    final Collection<T> objects,
    final String delimiter){
        StringBuilder stringBuilder;
    int index;

        if (objects == null){
      return null;
    }

        stringBuilder = new StringBuilder();

        index = 0;

    for (T object : objects){

      if (index > 0){
        stringBuilder.append(delimiter);
      }

      stringBuilder.append(object);

      index++;
    }

    return stringBuilder.toString();
  }

  /**
   * Join compact {@code String} representation of objects together with delimiter in between.
   * If the list of objects is {@code null} the result is {@code null}.
   * @param objects The list of objects
   * @param delimiter The delimiter
   * @return The delimited string
   */
  public static <T extends CompactString> String joinCompact(
    final Collection<T> objects,
    final String delimiter){
        StringBuilder stringBuilder;
    int index;

        if (objects == null){
      return null;
    }

        stringBuilder = new StringBuilder();

        index = 0;

    for (T object : objects){

      if (index > 0){
        stringBuilder.append(delimiter);
      }

      stringBuilder.append(object.toCompactString());

      index++;
    }

    return stringBuilder.toString();
  }

  /**
   * Replace all occurrences of substring in string.  If the string is {@code null}
   * the result is {@code null}.  If the substring is {@code null} or the replacement is
   * {@code null}, the string will remain unchanged.
   * @param string The string
   * @param substring The substring
   * @param replacement The replacement
   * @return The result string
   */
  public static String replaceAll(
    final String string,
    final String substring,
    final String replacement){
        StringBuilder stringBuilder;

        if ((string == null) || (replacement == null)){
      return string;
    }

        if (contentEqual(substring, replacement) == true){
      return string;
    }

        stringBuilder = new StringBuilder(string);
    replaceAll(stringBuilder, substring, replacement);

    return stringBuilder.toString();
  }

  /**
   * Replace all occurrences of substring in {@code StringBuilder}.  If the {@code StringBuilder}
   * is {@code null} the result is {@code null}.  If the substring is {@code null} or the replacement
   * is {@code null}, the {@code StringBuilder} will remain unchanged.
   * @param stringBuilder The string builder
   * @param substring The substring
   * @param replacement The replacement
   * @return The result string
   */
  public static void replaceAll(
    final StringBuilder stringBuilder,
    final String substring,
    final String replacement){
        int index;
    int end;

        if ((substring == null) || (replacement == null)){
      return;
    }

        index = stringBuilder.indexOf(substring);

    while (index != -1){
      end = (index + substring.length());
      stringBuilder.replace(index, end, replacement);
      end = (index + replacement.length());
      index = stringBuilder.indexOf(substring, end);
    }

  }

  /**
   * Require string to not be {@code null} and not be empty.  If the string is
   * {@code null} or is empty then an exception is raised with the message.
   * @param obj The object
   * @param message The message
   */
  public static void requireNonEmpty(
    final String string,
    final String message) throws ApplicationException{

        if ((string == null) || (string.length() == 0)){
            throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, message);
    }

  }

}
