package com.xywei.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Description 文档映射关系
 * @Author future
 * @DateTime 2019/10/28 11:59
 */
@Document(indexName = "spring_data_es_company", type = "spring_data_es_employee")
public class Employee {

    @Id
    @Field(type = FieldType.Long, store = true)
    private Long id;

    @Field(type = FieldType.text, store = true, analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.text, store = true, analyzer = "ik_max_word")
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
