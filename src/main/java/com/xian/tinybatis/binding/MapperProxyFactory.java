package com.xian.tinybatis.binding;

import com.xian.tinybatis.sqlSession.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MapperProxyFactory
 *
 * @author lishouxian
 * @since 2020/11/30 09:37
 */
public class  MapperProxyFactory <T>{
    private final Class<T> mapperInterface;
    private Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<Method, MapperMethod>();

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public Map<Method, MapperMethod> getMethodCache() {
        return methodCache;
    }

    @SuppressWarnings("unchecked")
    protected T newInstance(MapperProxy<T> mapperProxy) {
        // 通过 JDK 动态代理创建代理对象
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, (InvocationHandler) mapperProxy);
    }

    public  T newInstance(SqlSession sqlSession) {
        // 创建 MapperProxy 对象，MapperProxy 实现了 InvocationHandler 接口， // 代理逻辑封装在此类中
        final MapperProxy<T> mapperProxy = new MapperProxy<T>(sqlSession, mapperInterface, methodCache);
        return newInstance(mapperProxy);
    }
}