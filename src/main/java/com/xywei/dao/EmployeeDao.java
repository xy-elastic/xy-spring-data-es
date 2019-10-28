package com.xywei.dao;

import com.xywei.domain.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Description TODO
 * @Author future
 * @DateTime 2019/10/28 11:59
 */
public interface EmployeeDao extends ElasticsearchRepository<Employee, Long> {

    List<Employee> findEmployeesByNameLike(String name);

    List<Employee> findEmployeesByNameOrAddress(String name, String address);

    List<Employee> findEmployeesByNameLike(String name, Pageable pageable);
}
