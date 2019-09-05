package com.jt.pojo;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/7/3.
 */
@Data
@ToString
@Accessors(chain = true)
public class BasePojo implements Serializable{
    private Date created;
    private Date updated;
}
