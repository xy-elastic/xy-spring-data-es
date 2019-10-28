package com.xywei.dao;

import com.xywei.domain.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Description TODO
 * @Author future
 * @DateTime 2019/10/28 11:59
 */
public interface EmployeeDao extends ElasticsearchRepository<Employee, Long> {
}
