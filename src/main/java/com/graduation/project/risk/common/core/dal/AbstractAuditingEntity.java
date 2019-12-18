package com.graduation.project.risk.common.core.dal;

import com.graduation.project.risk.common.model.ToString;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@ApiModel
@MappedSuperclass
public abstract class AbstractAuditingEntity extends ToString implements Serializable {

    @CreatedDate
    protected Date   createTime = new Date();

    @LastModifiedDate
    protected Date   updateTime = new Date();

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
