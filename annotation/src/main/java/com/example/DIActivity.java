package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author DangGui
 * @create 2017/7/22.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface DIActivity {
}
