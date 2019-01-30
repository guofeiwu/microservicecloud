package com.mason.springcloud.service;

import com.mason.springcloud.entities.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服务降级
 * 服务降级处理是在客户端实现完成的，与服务端没有关系
 *
 * @author Mason
 * @version v1.0
 * @since 2019/1/30
 */
@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptFeignService> {
    @Override
    public DeptFeignService create(Throwable throwable) {
        return new DeptFeignService() {
            @Override
            public boolean add(Dept dept) {
                return false;
            }

            @Override
            public Dept get(Long id) {
                return new Dept().setDeptno(id)
                        .setDname("该ID：" + id + "没有没有对应的信息,Consumer客户端提供的降级信息,此刻服务Provider已经关闭")
                        .setDb_source("no this database in MySQL");

            }

            @Override
            public List<Dept> list() {
                return null;
            }
        };
    }
}
