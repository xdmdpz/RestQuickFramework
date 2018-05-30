package com.yf.common.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by if on 2017/12/6.
 */
public abstract class AbstractEntity  implements Serializable {
    @Transient
    protected boolean isNewRecord = false;

    @JsonIgnore
    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }
}
