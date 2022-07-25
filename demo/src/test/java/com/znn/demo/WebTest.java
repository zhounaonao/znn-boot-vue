package com.znn.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.*;

/**
 * @author 周闹闹
 * @version 1.0
 */
// 默认不带web环境 SpringBootTest.WebEnvironment.NONE
// DEFINED_PORT 启动默认端口的web环境
// RANDOM_PORT 随机端口
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// 开启虚拟MVC调用
@AutoConfigureMockMvc
public class WebTest {

    @Test
    void test1(){

    }

    @Test
    void testWeb(@Autowired MockMvc mvc) throws Exception {
        // 定义虚拟请求，访问/test
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/test");
        mvc.perform(builder);
    }

    @Test
    void testWeb2(@Autowired MockMvc mvc) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/test");
        ResultActions actions = mvc.perform(builder);

        // 设置预期值与真实值比较
        StatusResultMatchers status = MockMvcResultMatchers.status();
        // 定义本次的预期值
        ResultMatcher ok = status.isOk();
        // 匹配结果比对
        actions.andExpect(ok);
    }

    @Test
    void testWebJson(@Autowired MockMvc mvc) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/test");
        ResultActions actions = mvc.perform(builder);

        // 设置预期值与真实值比较
        ContentResultMatchers content = MockMvcResultMatchers.content();
        // 定义本次的预期值
        ResultMatcher ok = content.json("{\"msg\":\"操作成功l\",\"code\":200}");
        // 匹配结果比对
        actions.andExpect(ok);
    }

    @Test
    void testWeb4(@Autowired MockMvc mvc) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/test");
        ResultActions actions = mvc.perform(builder);

        HeaderResultMatchers header = MockMvcResultMatchers.header();
        ResultMatcher result = header.string("Content-Type", "application/json");
        actions.andExpect(result);
    }

    @Test
    void testGetById(@Autowired MockMvc mvc) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/test");
        ResultActions actions = mvc.perform(builder);

        StatusResultMatchers status = MockMvcResultMatchers.status();
        ResultMatcher ok = status.isOk();
        actions.andExpect(ok);

        HeaderResultMatchers header = MockMvcResultMatchers.header();
        ResultMatcher contentType = header.string("Content-Type", "application/json");
        actions.andExpect(contentType);

        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultMatcher resultMatcher = content.json("{\"msg\":\"操作成功l\",\"code\":1200}");
        actions.andExpect(resultMatcher);
    }

}
