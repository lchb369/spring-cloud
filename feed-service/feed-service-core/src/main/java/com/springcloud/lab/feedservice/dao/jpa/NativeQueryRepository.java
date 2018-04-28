package com.springcloud.lab.feedservice.dao.jpa;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.math.BigInteger;
import java.util.*;

/**
 * 构建复杂查询
 * @author lchb
 */
@Repository
public class NativeQueryRepository{

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据sql查询
     * @param sql
     * @param resultClass
     * @param <T>
     * @return
     */
    public <T> List<T> findBySql(String sql,Class resultClass){
        Query nativeQuery=entityManager.createNativeQuery(sql,resultClass);
        List rows = nativeQuery.getResultList();
        List<T> resultList=new ArrayList<T>();
        for (Object obj : rows) {
            T row=(T)obj;
            resultList.add(row);
        }
        return resultList;
    }

    /**
     * 根据参数查询
     * @param sql
     * @param parameters
     * @param resultClass
     * @param <T>
     * @return
     */
    public <T> List<T> findBySql(String sql,Map<String,Object> parameters,Class resultClass){
        Query nativeQuery=null;
        if(resultClass!=null && !resultClass.equals(Object.class)) {
            nativeQuery = entityManager.createNativeQuery(sql, resultClass);
        }else{
            nativeQuery = entityManager.createNativeQuery(sql);
        }

        Iterator<Map.Entry<String,Object>> entries = parameters.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String,Object> entry = entries.next();
            nativeQuery.setParameter(entry.getKey(),entry.getValue());
        }

        List rows = nativeQuery.getResultList();
        List<T> resultList=new ArrayList<T>();
        for (Object obj : rows) {
            T row=(T)obj;
            resultList.add(row);
        }
        return resultList;
    }


    public Integer findCountBySql(String sql,Map<String,Object> parameters){
        List<Object> list=this.findBySql(sql,parameters,Object.class);
        BigInteger total=(BigInteger)list.get(0);
        return total.intValue();
    }
}