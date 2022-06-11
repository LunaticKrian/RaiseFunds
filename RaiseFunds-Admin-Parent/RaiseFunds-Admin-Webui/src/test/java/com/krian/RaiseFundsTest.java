package com.krian;

import com.krian.entity.Admin;
import com.krian.mapper.AdminMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

// 指定 Spring 给 Junit 提供的运行器:
@RunWith(SpringJUnit4ClassRunner.class)
// 加载 Spring 配置文件的注解:
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class RaiseFundsTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void testMySqlConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        /*
         * 如果在实际开发中，所有想查看的数据的地方使用system out方式打印，会给项目上线运行带来问题
         * system out本质上是一个IO操作，通常IO操作是比较消耗性能的，如果项目中有许多system out输出，对于性能影响较大
         * 可以通过使用日志系统，通过日志系统批量控制信息！！！
         */
        System.out.println(connection);
    }

    @Test
    public void testLogSout() throws SQLException {
        Connection connection = dataSource.getConnection();

        // 通过反射获取到当前类的类加载器，通过类加载器创建logger对象，打印日志
        Logger logger = LoggerFactory.getLogger(RaiseFundsTest.class);
        logger.debug("----------" + connection.toString() + "----------");
    }

    @Test
    public void testInsertToT_Admin() {
        Admin admin = new Admin(null, "tom", "123456", "汤姆", "tom@qq.com", null);
        int i = adminMapper.insert(admin);
        System.out.println("成功插入数据库表，返回受影响行数：" + i + "行");
    }
}
