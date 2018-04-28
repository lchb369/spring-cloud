package com.springcloud.lab.api.dto.result;

import lombok.Data;
import java.util.List;

@Data
public class FeedResult {

    List<FeedRowResult> list;

    Integer total;
}