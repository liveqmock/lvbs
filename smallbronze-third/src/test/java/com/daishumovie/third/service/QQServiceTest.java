package com.daishumovie.third.service;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.*;
/** 
* QQService Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 11, 2017</pre> 
* @version 1.0 
*/ 
public class QQServiceTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getQQUser(String openId, String openKey, String pf) 
* 
*/ 
@Test
public void testGetQQUser() throws Exception {

        String a ="GET&%2Fv3%2Fuser%2Fget_info&appid%3D123456%26format%3Djson%26openid%3D11111111111111111%26" +
                "openkey%3D2222222222222222%26pf%3Dqzone%26userip%3D112.90.139.30";
        String k = "228bf094169a40a3bd188ba37ebe8723&";
        String sig = Base64.encodeBase64String(HmacUtils.hmacSha1(k,a));

        assertEquals("FdJkiDYwMj5Aj1UG2RUPc83iokk=",sig);

} 


} 
