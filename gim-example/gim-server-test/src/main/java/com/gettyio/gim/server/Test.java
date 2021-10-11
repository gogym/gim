package com.gettyio.gim.server;

import com.gettyio.gim.utils.HashBlockingMap;
import com.gettyio.gim.utils.expiremap.BaseExpireMap;

import java.util.concurrent.TimeUnit;

public class Test {




    public static void main(String[] args) {



        BaseExpireMap<String,String> baseExpireMap=new BaseExpireMap<String, String>(1, TimeUnit.SECONDS) {
            @Override
            protected void baseExpireEvent(String key, String val) {
                System.out.println("过期:"+key);
            }
        };


        try {
            for (int i=0;i<10;i++){
                baseExpireMap.put("aa"+i,"aa"+i);
            }

            int a=1;
            while (true) {
                Thread.sleep(3000);
                baseExpireMap.put("bb"+a, "bb"+a);
                a++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
