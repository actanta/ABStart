package cc.abing.abstart.support.system.util;

import cc.abing.abstart.support.system.exception.BizException;
import cc.abing.abstart.support.system.response.ICodeMsg;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author SuperSuperZ
 * @since 2021/4/1
 */

public class Misc {

    public static boolean isNull(Object obj) {
        return isEmpty(obj);
    }

    public static boolean isNotNull(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else {
            if (obj instanceof String) {
                String str = obj.toString().trim();
                if ("".equals(str)) {
                    return true;
                }
            }

            if (obj.getClass().isArray()) {
                return Array.getLength(obj) == 0;
            } else if (obj instanceof CharSequence) {
                return ((CharSequence)obj).length() == 0;
            } else if (obj instanceof Collection) {
                return ((Collection)obj).isEmpty();
            } else {
                return obj instanceof Map && ((Map) obj).isEmpty();
            }
        }
    }

    public static void assertTrue(Boolean b, ICodeMsg codeMsg) {
        if(isNull(b) || !b){
            throw new BizException(codeMsg);
        }
    }

    public static void assertFalse(Boolean b, ICodeMsg codeMsg) {
        if(b){
            throw new BizException(codeMsg);
        }
    }

    public static void assertNotBlank(Object obj, ICodeMsg codeMsg) {
        if(isEmpty(obj)){
            throw new BizException(codeMsg);
        }
    }


}
