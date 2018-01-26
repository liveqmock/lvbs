package com.daishumovie.api.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Contains;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThat;

/** 
* UserController Tester. 
* 
* @author <Authors name> 
* @since <pre> 5, 2017</pre>
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {


    private TestRestTemplate template = new TestRestTemplate();
    private String host = "http://localhost:9090";
    HttpEntity<String> httpEntity;


    @Before
    public void before() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("did", "90cbb3a808764bae98c98c1ca9d3405b");
        headers.add("sessionId","123456");
        httpEntity = new HttpEntity<>(headers);
    }

    @Test
    public void testSendCaptcha() {

        String forObject = template.postForObject(host + "/v1/user/sendCaptcha?mobile=15010780830", httpEntity, String.class);
        assertThat(forObject, new Contains("status"));

    }

    /**
     * Method: wechatLogin(@RequestParam String code)
     */
    @Test
    public void testWechatLogin() throws Exception {

        //获取code
        String code = "001Jw00X1gyqwW0wpwZW1cxVZW1Jw008";

        String forObject = template.postForObject(host + "/v1/wxlogin?code=" + code, httpEntity, String.class);
        assertThat(forObject, new Contains("status"));
    }
}
