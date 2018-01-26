package com.daishumovie.api.controller.v1;

import com.daishumovie.base.model.Response;
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
import org.springframework.web.bind.annotation.RequestParam;

import static org.junit.Assert.assertThat;

/** 
* TopicController Tester. 
* 
* @author <Authors name> 
* @since <pre>九月 7, 2017</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TopicControllerTest {
    private TestRestTemplate template = new TestRestTemplate();
    private String host = "http://localhost:9090";



    @Before
    public void before() throws Exception {

    }

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: publish(@RequestParam String title, @RequestParam String description, @RequestParam Integer categoryId, @RequestParam Integer videoId) 
* 
*/ 
@Test
public void testPublish() throws Exception {


    HttpHeaders headers = new HttpHeaders();
    headers.add("did", "90cbb3a808764bae98c98c1ca9d3405b");
    headers.add("sessionId","7dfk884GodkUEfoasjfpqjk]698eeEFdd7fefefJFFHdgeE");
    headers.add("appId","4");

    MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();

    param.add("title", "情感信用社");
    param.add("description", "真情付出");
    param.add("videoId", "70");
    param.add("channelId", "2");
    param.add("activityId", "2");


    HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(param, headers);
    String forObject = template.postForObject(host + "/v1/topic/publish", httpEntity, String.class);
    assertThat(forObject, new Contains("status"));
}


    /**
     *
     * Method: addComment(@RequestParam Integer topicId, @RequestParam(required = false) Integer pid, @RequestParam  String content, @RequestParam(required = false)  String imgList, @RequestParam(required = false)  Integer videoId)
     *
     */
    @Test
    public void testAddComment() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("did", "90cbb3a808764bae98c98c1ca9d3405b");
        headers.add("sessionId","7dfk884GodkUEfoasjfpqjk]698eeEFdd7fefefJFFHdgeE");
        headers.add("appId","4");

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();

        param.add("topicId", "1");
        param.add("content", "及月初品");
//        param.add("pid", "70");
        param.add("imgList", "[{ \"ori_url\":\"http://small-bronze.oss-cn-shanghai.aliyuncs.com/image/2017/9/8/3484049F948443D080EFE2F444D8128D.jpg\",\"dimension\":\"50x60\"}]");

//        param.add("videoId", "2");



        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(param, headers);
        String forObject = template.postForObject(host + "/v1/topic/comment/add", httpEntity, String.class);
        assertThat(forObject, new Contains("status"));
    }


    @Test
    public void testAddComment2() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("did", "90cbb3a808764bae98c98c1ca9d3405b");
        headers.add("sessionId","7dfk884GodkUEfoasjfpqjk]698eeEFdd7fefefJFFHdgeE");
        headers.add("appId","4");

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();

        param.add("topicId", "1");
        param.add("content", "ze好么");
        param.add("pid", "1");
        param.add("imgList", "[{ \"ori_url\":\"http://small-bronze.oss-cn-shanghai.aliyuncs.com/image/2017/9/8/E41E90F79A3B4F0C9A5179C7B9A5D26D.jpg\",\"dimension\":\"50x60\"}]");
        param.add("videoId", "1");



        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(param, headers);
        String forObject = template.postForObject(host + "/v1/topic/comment/add", httpEntity, String.class);
        assertThat(forObject, new Contains("status"));
    }


} 
