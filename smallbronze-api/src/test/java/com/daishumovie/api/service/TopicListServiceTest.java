package com.daishumovie.api.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/** 
* TopicListService Tester. 
* 
* @author <Authors name> 
* @since <pre>九月 19, 2017</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TopicListServiceTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getTopicByPage(PageInDto pageInfo, Integer appId, Integer channelId, Integer userId, Integer type, boolean isIOS, PageSource page) 
* 
*/ 
@Test
public void testGetTopicByPage() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: wrapTopicDto(SbTopic topic, Integer localUid, Boolean isIOS, PageSource page) 
* 
*/ 
@Test
public void testWrapTopicDto() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getTopicDetail(Integer topicId, Integer appId, boolean isIOS) 
* 
*/ 
@Test
public void testGetTopicDetail() throws Exception { 
//TODO: Test goes here... 
} 



@Autowired
TopicListService topicListService;

/** 
* 
* Method: getNextBucketIndex(Integer uid, Integer baseBucketIndex) 
* 
*/ 
@Test
public void testGetNextBucketIndex() throws Exception {

    int bucket = 20;

    for (int i = 0; i <5 ; i++) {

        Random r = new Random();
        int ran = r.nextInt(2);
        bucket+=ran;


        Integer nextBucketIndex = topicListService.getNextBucketIndex( bucket);
        System.out.print(i+"  -  "+bucket+"   ============   "+nextBucketIndex);
        if(bucket != nextBucketIndex){
            System.out.print("            zzz");
        }
        System.out.println();
    }

}


/** 
* 
* Method: formatNum(Integer num) 
* 
*/ 
@Test
public void testFormatNum() throws Exception {
    Integer nextBucketIndex = topicListService.getNextBucketIndex( 50);
     nextBucketIndex = topicListService.getNextBucketIndex(70);
     nextBucketIndex = topicListService.getNextBucketIndex( 90);
}

} 
