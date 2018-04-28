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
@ApiModel("Feed保存参数")
@Data
public class FeedRequest {

    @ApiModelProperty("Feed ID,如果大于0更新，否则创建")
    private Long id;

    @ApiModelProperty("UserId用户ID")
    @NotNull(message = "userId不能为空")
    private Integer userId;

    @ApiModelProperty("Feed标题")
    @Size(min=4,max=256,message = "标题长度只能在4-256之间")
    private String title;

    @ApiModelProperty("Feed类型")
    @NotNull(message = "type不能为空")
    @Max(value = 5,message = "type取值不能超过5")
    private Integer type;

    @ApiModelProperty("Feed内容")
    @NotEmpty(message = "内容不能为空")
    private String content;

    @ApiModelProperty("Feed作者名称")
    @NotEmpty(message = "作者名称不能为空")
    private String author;

    @ApiModelProperty("Tag列表，多个用逗号隔开")
    @NotEmpty(message = "标签不能为空")
    private String tags;

}
