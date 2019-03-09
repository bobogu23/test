package parameterizetype;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author:ben.gu
 * @Date:2018/10/26 下午5:29
 */
public class TestGetParameterizeType {


    @Test
    public void testGetParameterizeType(){
//        List<String> list= new ArrayList<String>();
//        list.add("ff");
//        ParameterizedType parameterizedType = (ParameterizedType) list.getClass().getGenericSuperclass();
//        Type type = parameterizedType.getActualTypeArguments()[0];
//
//        System.err.println("list type:"+type);
//
//        TypeToken<ArrayList<String>> typeToken = new TypeToken<ArrayList<String>>() {};
//
//         TypeToken<?> genericTypeToken = typeToken.resolveType(List.class.getTypeParameters()[0]);
//        System.out.println("parameterize type:"+genericTypeToken.getType().getTypeName().equals(String.class.getName()));
//
//
//        TypeToken<Map<String,Long>> mapTypeToken = new TypeToken<Map<String,Long>>() {};
//
//        TypeToken<?> genericMapTypeToken = mapTypeToken.resolveType(Map.class.getTypeParameters()[1]);
//        System.out.println("Map parameterize type:"+genericMapTypeToken.getType().getTypeName().equals(Long.class.getName()));
    }
}
