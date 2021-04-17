package com.gettyio.gim.cluster.constants;

/**
 * @author gogym.ggj
 * @version 1.0.0
 * @ClassName clusterType.java
 * @email gongguojun.ggj@alibaba-inc.com
 * @Description TODO
 * @createTime 2021/02/23/ 10:17:00
 */
public enum clusterType {


    /**
     * 通过中间服务集群
     */
    centralization(1,"中心化集群")
    ;

    private int type;
    private String desc;

    private clusterType(int type,String desc) {
        this.type = type;
        this.desc=desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
