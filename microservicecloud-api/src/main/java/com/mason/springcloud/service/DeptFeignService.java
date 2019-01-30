package com.mason.springcloud.service;

import com.mason.springcloud.entities.Dept;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Mason
 * @version v1.0
 * @since 2019/1/30
 */

//@FeignClient("microservice-dept")
@FeignClient(value = "microservice-dept", fallbackFactory = DeptClientServiceFallbackFactory.class)
public interface DeptFeignService {

    @RequestMapping("/dept/add")
    public boolean add(@RequestBody Dept dept);

    @RequestMapping("/dept/get/{id}")
    public Dept get(@PathVariable(value = "id") Long id);

    @RequestMapping("/dept/list")
    public List<Dept> list();
}
