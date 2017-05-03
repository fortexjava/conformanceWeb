/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fortex.conformanceWeb.pojo.ListServer;
import com.fortex.conformanceWeb.pojo.LogonResults;
import com.fortex.conformanceWeb.pojo.RestData;
import com.fortex.conformanceWeb.pojo.User;
import com.fortex.conformanceWeb.service.RecordService;
import com.fortex.conformanceWeb.service.UserService;
import com.fortex.conformanceWeb.utils.HttpClientUtils;

/**
 * @author Ivan Huo
 *
 */
@RestController
@RequestMapping("/track/")
public class TrackRestController {
	
	@Autowired	
	RecordService recordService;
	
	@Autowired	
	UserService userService;
	
	@Autowired
	private Environment env;
	
	@RequestMapping("/getRecordStatus")
	public boolean getRecordStatus(@RequestParam(value = "step") Integer step,HttpServletRequest request){
		User user= userService.getCurrentUser(request);
		return  recordService.isRecordPassed(user.getLoginId(), step);
	}
	
	@RequestMapping("/resetRecord")
	public boolean resetRecord(HttpServletRequest request){
		User user= userService.getCurrentUser(request);			
		return recordService.resetRecord(user.getLoginId(),user.getSenderID());
	}
	 
	@RequestMapping("/chooseUserStep")
	public boolean chooseUserStep( @RequestParam(value = "isAutoPolit") Integer isAutoPolit,
								   @RequestParam(value = "isTrading") Integer isTrading,
								   @RequestParam(value = "step") Integer currentStep, 
								   HttpServletRequest request){
		User user= userService.getCurrentUser(request);
		if(isAutoPolit < 0)
			isAutoPolit = null;
		else{
			if(!changeAutoPolit(user.getSenderID(),isAutoPolit))
				return false;
		}
		if(isTrading < 0)
			isTrading = null;
		else{
			if(!changeTrading(user.getSenderID(),isTrading))
				return false;
		}
		return userService.updateUserStatus(user.getLoginId(), isAutoPolit, isTrading, currentStep);		
	}
	
	
	private boolean changeAutoPolit(String senderId,Integer isAutoPolit){
		CloseableHttpClient httpClient=null;
		try {
			httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient();
		} catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        String webConsole= env.getProperty("webconsole.address");
        String ftsAddress= env.getProperty("fts.address");
        String webConsoleUser= env.getProperty("webconsole.user");
        String webConsolePwd= env.getProperty("webconsole.password");
        LogonResults loginResult = restTemplate.postForObject( webConsole + "/authenticate/login?name="+ webConsoleUser + "&password=" + webConsolePwd,null,LogonResults.class);
        String wid = loginResult.getData().getWebconsoleSession();
        ListServer listResult = restTemplate.postForObject( webConsole + "/server/get/ip?ip=" + ftsAddress + "&webconsoleSession=" + wid, null,ListServer.class);
        if(!listResult.getError().isEmpty() || listResult.getData().size() == 0)
        	return false;
        String serverId = listResult.getData().get(0).getId();
        String autoPolit="yes";
        if(isAutoPolit == 0)
        	autoPolit = "no";
        RestData result = restTemplate.postForObject( webConsole + "/fts/lp/control?serverID=" + serverId+ "&liquidityProvider=" + senderId +"&type=autoPilot&enable="+ autoPolit, null, RestData.class);
        if(result.getError().isEmpty())
        	return true;        
       	return false;
	}
	
	private boolean changeTrading(String senderId,Integer isTrading){
		CloseableHttpClient httpClient=null;
		try {
			httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient();
		} catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        String webConsole= env.getProperty("webconsole.address");
        String ftsAddress= env.getProperty("fts.address");
        String webConsoleUser= env.getProperty("webconsole.user");
        String webConsolePwd= env.getProperty("webconsole.password");
        LogonResults loginResult = restTemplate.postForObject( webConsole + "/authenticate/login?name="+ webConsoleUser + "&password=" + webConsolePwd,null,LogonResults.class);
        String wid = loginResult.getData().getWebconsoleSession();
        ListServer listResult = restTemplate.postForObject( webConsole + "/server/get/ip?ip=" + ftsAddress + "&webconsoleSession=" + wid, null,ListServer.class);
        if(!listResult.getError().isEmpty() || listResult.getData().size() == 0)
        	return false;
        String serverId = listResult.getData().get(0).getId();
        String trading="yes";
        if(isTrading == 0)
        	trading = "no";
        RestData result = restTemplate.postForObject( webConsole + "/fts/lp/control?serverID=" + serverId+ "&liquidityProvider=" + senderId +"&type=trading&enable="+ trading, null, RestData.class);
        if(result.getError().isEmpty())
        	return true;        
       	return false;
	}
}
