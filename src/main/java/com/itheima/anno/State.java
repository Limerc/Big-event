package com.itheima.anno;

import com.itheima.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented  //原注解
@Constraint(
        validatedBy = {StateValidation.class}
)//指定谁给注解提供相应的校验规则
// @Target也是原注解，表示可用用在哪些地方(方法、字段、注解、构造器、参数、类型使用)
@Target({ElementType.FIELD})// 因为State只用在属性字段上，所以只需要保存FIELD即可
@Retention(RetentionPolicy.RUNTIME) // 原注解，标识哪个阶段保留（编译时、运行时）
public @interface State {
    //提供校验失败后的提示信息
    String message() default "state参数的值只能是[已发布]或者是[草稿]";
    //指定分组
    Class<?>[] groups() default {};
    //负载 获取到State主键的附加信息（一般用不上）
    Class<? extends Payload>[] payload() default {};
}
