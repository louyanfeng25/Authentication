package com.baiyan.auth.common.result;

import lombok.Data;

import java.util.List;

/**
 * @author baiyan
 * @time 2020/11/13 13:23
 */
@Data
public class PageResult<T> extends BaseResult {

    private Long total;

    private List<T> data;

    public PageResult() {
    }

    public static <T> PageResult<T> ok(Page<T> result) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCode(CODE_SUCCESS);
        pageResult.setMessage(MESSAGE_SUCCESS);
        pageResult.setTotal(result.getTotal());
        pageResult.setData(result.getRecords());
        return pageResult;
    }

}

