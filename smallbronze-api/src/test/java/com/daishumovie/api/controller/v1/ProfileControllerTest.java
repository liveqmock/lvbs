package com.daishumovie.api.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
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
* ProfileController Tester. 
* 
* @author <Authors name> 
* @since <pre>九月 12, 2017</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProfileControllerTest {

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
* Method: topicList(@PathVariable("pageIndex") Integer pageIndex, @RequestHeader(value = "os", required = false) String os, @RequestHeader(value = "appId", required = false) Integer appId) 
* 
*/ 
@Test
public void testTopicList() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getUserInfo() 
* 
*/ 
@Test
public void testGetUserInfo() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getUserInfo(@RequestParam Integer uid) 
* 
*/ 
@Test
public void testGetUserInfoUid() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: myChannelList(@RequestParam Integer pageIndex) 
* 
*/ 
@Test
public void testMyChannelList() throws Exception {

    HttpHeaders headers = new HttpHeaders();
    headers.add("did", "90cbb3a808764bae98c98c1ca9d3405b");
    headers.add("sessionId","7dfk884GodkUEfoasjfpqjk]698eeEFdd7fefefJFFHdgeE");
    headers.add("appId","1000");

    MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
    param.add("uid", 1);

    HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(param, headers);
    String forObject = template.postForObject(host + "/v1/profile/otherChannelList/1", httpEntity, String.class);
    log.info(forObject);
    assertThat(forObject, new Contains("status"));
} 


} 
