package com.mark.aoplibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/05/30
 *     desc   : 功能暂未开放注释
 *     version: 1.0
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface NotOpen {
}
