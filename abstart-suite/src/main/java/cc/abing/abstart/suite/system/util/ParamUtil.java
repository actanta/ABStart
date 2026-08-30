package cc.abing.abstart.suite.system.util;

import cc.abing.abstart.suite.system.exception.BizException;
import cc.abing.abstart.suite.system.response.CodeMsg;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.LambdaMeta;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.Collection;
import java.util.Objects;

public class ParamUtil {

    public static <T> void notBlank(T obj, SFunction<T, CharSequence> getter) {
        CharSequence val = getter.apply(obj);
        if (StringUtils.isBlank(val)) {
            throw new BizException(CodeMsg.PARAM_REQUIRE, getterToString(getter));
        }
    }

    public static <T, R> void notNull(T obj, SFunction<T, R> getter) {
        R val = getter.apply(obj);
        if (Objects.isNull(val)) {
            throw new BizException(CodeMsg.PARAM_REQUIRE, getterToString(getter));
        }
    }

    public static <T,R extends Collection<?>> void notEmpty(T obj, SFunction<T,R> getter) {
        R val = getter.apply(obj);
        if (val == null || val.isEmpty()) {
            throw new BizException(CodeMsg.PARAM_REQUIRE, getterToString(getter));
        }
    }


    public static <T,R> String getterToString(SFunction<T, ?> func){
        LambdaMeta meta = LambdaUtils.extract(func);
        String fieldName = PropertyNamer.methodToProperty(meta.getImplMethodName());
        return fieldName;
    }
}
