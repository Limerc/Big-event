package com.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

//lombook 在编译阶段为实体类自动生成setter、getter、toString方法
//引入方法：pom文件中添加注解
@Data
public class User {
    @NotNull
    private Integer id;//主键ID

    private String username;//用户名
    @JsonIgnore//让Spingmvc把当前对象转换为json时，忽略password
    private String password;//密码

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;//昵称

    @NotEmpty
    @Email
    private String email;//邮箱
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
