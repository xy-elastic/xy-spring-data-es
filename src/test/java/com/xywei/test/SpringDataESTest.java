package com.xywei.test;

import com.xywei.dao.EmployeeDao;
import com.xywei.domain.Employee;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

/**
 * @Description TODO
 * @Author future
 * @DateTime 2019/10/28 11:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringDataESTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testElasticsearchTemplate() {
        System.out.println(elasticsearchTemplate.getClient());
    }

    @Test
    public void testCreateIndex() {
        elasticsearchTemplate.createIndex(Employee.class);
    }

    /**
     * @Description 测试插入10条数据
     * @Author future
     * @DateTime 2019/10/28 13:45
     **/
    @Test
    public void testInsertDocument() {

        for (int i = 1; i <= 20; i++) {
            Employee employee = new Employee();
            employee.setId(Long.parseLong(i + ""));
            employee.setName("国产00" + i);
            employee.setAddress("中国四川省成都市" + i * i + "号");
            employeeDao.save(employee);
//            employeeDao.delete(employee);
        }

    }

    /**
     * @Description 删除数据操作
     * @Author future
     * @DateTime 2019/10/28 14:02
     **/
    @Test
    public void testDeleteDocument() {
        //删除单个
//        employeeDao.deleteById(1L);
        //删除所有
        employeeDao.deleteAll();
    }

    /**
     * @Description 保证ID一致就是update，原理是先删除，后保存
     * @Author future
     * @DateTime 2019/10/28 14:06
     **/
    @Test
    public void updateDocument() {

        Employee employee = new Employee();
        employee.setId(Long.parseLong(1 + ""));
        employee.setName("更新国产00" + 1);
        employee.setAddress("更新中国四川省成都市" + 1 + "号");
        employeeDao.save(employee);

    }

    /**
     * @Description 测试查询数据
     * @Author future
     * @DateTime 2019/10/28 14:13
     **/
    @Test
    public void testFindDocument() {

        /**
         * @Description 获取所有
         * @Author future
         * @DateTime 2019/10/28 14:20
         **/
        Iterable<Employee> employees = employeeDao.findAll();
        employees.forEach(employee -> {
                    System.out.println(employee);
                }
        );

        System.out.println("--------单个数据--------");
        /**
         * @Description 获取单个
         * @Author future
         * @DateTime 2019/10/28 14:20
         **/
        Optional<Employee> employee = employeeDao.findById(5L);
        System.out.println(employee.get());
    }

    /**
     * @Description 自定义查询, 默认返回10条数据
     * @Author future
     * @DateTime 2019/10/28 15:01
     **/
    @Test
    public void testCustomFind() {
//        List<Employee> employees = employeeDao.findEmployeesByNameLike("%001%");
//        employees.stream().forEach(employee -> System.out.println(employee));

        List<Employee> employees = employeeDao.findEmployeesByNameOrAddress("%001%", "四川");
        employees.forEach(employee -> System.out.println(employee));
    }

    /**
     * @Description 设置分页查询
     * @Author future
     * @DateTime 2019/10/28 15:12
     **/
    @Test
    public void testCustomFindPage() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Employee> employees = employeeDao.findEmployeesByNameLike("%00%", pageable);
        employees.forEach(employee -> System.out.println(employee));
    }

    /**
     * @Description 使用原生的查询
     * @Author future
     * @DateTime 2019/10/28 15:32
     **/
    @Test
    public void testByQuerString() {
        //querybuilder
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery("国四成都").defaultField("address"))
                .withPageable(PageRequest.of(0, 10))
                .build();
        List<Employee> employees = elasticsearchTemplate.queryForList(nativeSearchQuery, Employee.class);
        employees.forEach(employee -> System.out.println(employee));
    }
}
