package com.springcloud.lab.api.dto.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by admin on 2017/7/23.
 */
@ApiModel("User保存参数")
@Data
public class UserParams {

    @ApiModelProperty("User ID,如果大于0更新，否则创建")
    private Integer id;

    @ApiModelProperty("User用户名")
    @NotEmpty(message = "name不能为空")
    @Size(min=1,max=64,message = "name长度只能在2-64之间")
    private String name;

    @ApiModelProperty("User Age")
    @NotNull(message = "age不能为空")
    @Max(value = 100,message = "age取值不能超过100")
    private Integer age;

    @ApiModelProperty("电话号码")
    @NotEmpty(message = "电话号码不能为空")
    private String phone;
}
