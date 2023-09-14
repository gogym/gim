package com.gettyio.gim.utils.beanref;

/**
 * @author gogym.ggj
 * @ClassName BeanFieldIndex.java
 * @createTime 2023/07/19/ 16:23:00
 */

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 实体model属性字段的位置顺序 ,从0开始
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface BeanFieldIndex {
    int index();
}
