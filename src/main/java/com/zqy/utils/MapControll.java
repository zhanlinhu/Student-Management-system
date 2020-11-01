package com.zqy.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapControll {

    //目标对象
    private Map<String,Object> paramMap = new HashMap<>();

    //私有构造
    private MapControll(){

    }

    public static MapControll getInstance(){
        return new MapControll();
    }

    public MapControll add(String key, Object value){
        paramMap.put(key,value);
        return this;
    }

    public MapControll success(){
        paramMap.put("code",Code.SUCCESS.getCode());  //100几  然后信息是什么
        paramMap.put("msg",Code.SUCCESS.getMsg());
        return this;
    }
    public MapControll error(){
        paramMap.put("code",Code.ERROR.getCode());
        paramMap.put("msg",Code.ERROR.getMsg());
        return this;
    }

    public MapControll error(String msg){
        paramMap.put("code",Code.ERROR.getCode());
        paramMap.put("msg",msg);
        return this;
    }

    public MapControll notEmpty(){
        paramMap.put("code",Code.NOT_EMPTY.getCode());
        paramMap.put("msg",Code.NOT_EMPTY.getMsg());
        return this;
    }
    public MapControll nodata(){
        paramMap.put("code",Code.NODATA.getCode());
        paramMap.put("msg",Code.NODATA.getMsg());
        return this;
    }

    public MapControll page(List<?> list,Integer count){
        paramMap.put("data",list);
        paramMap.put("count",count);
        return this;
    }

    public MapControll put(String key, Object value){
        this.add(key,value);
        return this;
    }

    public MapControll addId(Object value){
        paramMap.put("id",value);
        return this;
    }

    public MapControll add(Map<String,Object> map){
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            paramMap.put(entry.getKey(),entry.getValue());
        }
        return this;
    }

    public MapControll put(Map<String,Object> map){
        return this.add(map);
    }

    public Map<String,Object> getMap(){
        return paramMap;
    }




}
