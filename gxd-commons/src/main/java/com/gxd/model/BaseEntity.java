package com.gxd.model;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author:gxd
 * @Description: 实体基类
 * @Date: 11:56 2017/12/27
 */
public abstract class BaseEntity<E extends Serializable> implements Serializable {

    private static final long serialVersionUID = -1975107873497237831L;

    public abstract E getId();

    @Transient
    private Integer offset = 0;

    @Transient
    private Integer limit = 10;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

}
