package com.xihua.easyctlserver.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited
public @interface UserAuth {
    boolean paramsWithUser() default false;
}
