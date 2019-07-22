package com.uzak.elasticsearch.springdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @ClassName UserBean
 * @Description TODO
 * @Author liangxiudou
 * @Date 2019/7/22 20:56
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "user", type = "manage")
public class UserBean {
    @Id
    private Long id;
    private String userName;
    private String password;
    private String message;
    private String createTime;
}