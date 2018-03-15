package com.ysmull.easeshop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.ysmull.easeshop.model.entity.User.ROLE;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Privilege {

    ROLE[] role() default {};

    boolean login() default false;
}
