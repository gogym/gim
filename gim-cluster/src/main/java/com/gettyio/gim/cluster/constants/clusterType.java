package com.gettyio.gim.cluster.constants;

/**
 * @author gogym.ggj
 * @version 1.0.0
 * @ClassName clusterType.java
 * @email gongguojun.ggj@alibaba-inc.com
 * @Description TODO
 * @createTime 2021/02/23/ 10:17:00
 */
public enum ClusterType {


    /**
     * 通过中间服务集群
     */
    CENTRALIZATION(1,"中心化集群")
    ;

    private final int type;
    private final String desc;

    private ClusterType(int type, String desc) {
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
