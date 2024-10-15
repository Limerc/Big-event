package com.itheima.validation;

import com.itheima.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {
    /**
     * @param s：将来要校验的数据
     * @param constraintValidatorContext
     * @return：返回true表示校验通过，返回false表示校验失败
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //提供校验规则
        if(s == null)
            return false;
        if(s.equals("已发布") || s.equals("草稿"))
            return true;
        return false;
    }
}
