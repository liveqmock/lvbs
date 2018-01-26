package com.daishumovie.api.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Contains;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertThat;

/** 
* MineController Tester. 
* 
* @author <Authors name> 
* @since <pre>九月 9, 2017</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MineControllerTest {

        private TestRestTemplate template = new TestRestTemplate();
        private String host = "http://localhost:9090";

        @Before
        public void before() throws Exception {
        }

        @After
        public void after() throws Exception {
        }

        /**
         * Method: getUserInfo()
         */
        @Test
        public void testGetUserInfo() throws Exception {

            HttpHeaders headers = new HttpHeaders();
            headers.add("did", "90cbb3a808764bae98c98c1ca9d3405b");
            headers.add("sessionid", "7dfk884GodkUEfoasjfpqjk]698eeEFdd7fefefJFFHdgeE");
            headers.add("appId", "4");

//    MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();


            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            String forObject = template.postForObject(host + "/v1/my/info", httpEntity, String.class);
            assertThat(forObject, new Contains("status"));
        }


} 
