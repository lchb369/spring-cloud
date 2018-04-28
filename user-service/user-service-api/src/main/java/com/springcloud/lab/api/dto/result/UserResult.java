package com.springcloud.lab.api.dto.result;

import lombok.Data;

import java.util.List;

/**
 * Created by lchb on 2017-9-14.
 */
@Data
public class UserResult {

    List<UserRow> list;

    Integer total;
}
