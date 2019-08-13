package cn.personal.seckill.validator;

import cn.personal.seckill.util.ValidatorUtil;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-10
 * @Author ZhengTianle
 * Description:
 */
public class isMobileValidator implements ConstraintValidator<IsMobile, String> {

    boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!required && StringUtils.isEmpty(value)) {
            return true;
        }
        return ValidatorUtil.isMobile(value);
    }
}
