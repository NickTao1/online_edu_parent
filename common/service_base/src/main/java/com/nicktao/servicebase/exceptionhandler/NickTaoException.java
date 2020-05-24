package com.nicktao.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NickTaoException extends RuntimeException {
    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("异常信息")
    private String msg;
}
