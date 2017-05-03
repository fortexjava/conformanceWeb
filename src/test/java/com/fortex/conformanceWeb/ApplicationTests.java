/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb;

 
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fortex.conformanceWeb.pojo.ListServer;
import com.fortex.conformanceWeb.pojo.ListServerData;
import com.fortex.conformanceWeb.pojo.LogonResults;
import com.fortex.conformanceWeb.service.RecordService;
import com.fortex.conformanceWeb.service.UserService;
import com.fortex.conformanceWeb.utils.HttpClientUtils;

/**
 * @author Administrator
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class ApplicationTests {
	
	
	@Autowired	
	UserService userService;
	
	@Autowired	
	RecordService recordService;
	
	
	@Autowired
	@Qualifier("userJdbcTemplate")
	protected JdbcTemplate userJdbcTemplate;
	
	
	@Autowired
	@Qualifier("xRingJdbcTemplate")
	protected JdbcTemplate xRingJdbcTemplate;
	
	@Test
	public void test() throws Exception {
		//System.out.println( new UserDAO().getPasswordByName("BOB01"));
		// System.out.println(userJdbcTemplate.queryForObject("select PasswordHash from LoginTable where login = ?", String.class,new Object[]{"BOB01"}));
		//System.out.println(userService.isUserExist("PFIXQUOTE01", "TEST1234", 3));
		//System.out.println(recordService.isRecordPassed("PFIXQUOTE01", "A"));
//		String hashUserName= GlobalRuntime.hashPwd("PERFIXQUOTE01");
//		String hashPassword= GlobalRuntime.hashPwd("TEST1234");
//		
//		String sql="SELECT loginType,TargetID FROM loginTable lt,systemconfigs sc WHERE lt.LoginHash = ? AND lt.PasswordHash = ? AND lt.domain != sc.DomainClosedAccts";
//		List<Map<String, Object>> result = userJdbcTemplate.queryForList(sql,new Object[]{hashUserName,hashPassword});
//		if(result.size()>0){
//			Map<String,Object> obj = (Map<String,Object>)result.get(0);
//			System.out.println(obj.get("loginType"));
//			System.out.println(obj.get("TargetID"));
//		}
		
		CloseableHttpClient httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        LogonResults result = restTemplate.postForObject("https://192.1.2.248:8801/authenticate/login?name=admin&password=admin",null,LogonResults.class);
        //String result = restTemplate.postForObject("https://192.1.2.248:8801/authenticate/login?name=admin&password=admin",null,String.class);
        String wid = result.getData().getWebconsoleSession();
        ListServer lresult = restTemplate.postForObject( "https://192.1.2.248:8801/server/get/ip?ip=192.1.2.210&webconsoleSession=" + wid, null,ListServer.class);
        System.out.println(lresult.getData().size());
        for(ListServerData data : lresult.getData()){
        	System.out.println(data.getIpAddress() + data.getId());
        }
        
	}
}




