/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fortex.conformanceWeb.pojo.Record;
import com.fortex.conformanceWeb.pojo.User;
import com.fortex.conformanceWeb.service.RecordService;
import com.fortex.conformanceWeb.service.UserService;

/**
 * @author Ivan Huo
 *
 */
@Controller
public class TrackController {
	
	
	@Autowired	
	RecordService recordService;
	
	@Autowired	
	UserService userService;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/track/quote")
	public String quote(HttpServletRequest request){
		request.setAttribute("ip", env.getProperty("xring.quote.ip"));
		return "/quote";
	}
	
	
	@GetMapping("/track/trade")
	public String trade(HttpServletRequest request){
		request.setAttribute("ip", env.getProperty("xring.trade.ip"));
		return "/trade";
	}
	
	@GetMapping("/track/viewlogs")
	public String viewlogs(@RequestParam(value = "step") Integer step,HttpServletRequest request){
		User user= userService.getCurrentUser(request);
		Record clientRecord = recordService.getRecord(user.getLoginId(),user.getSenderID(), step,  RecordService.MESSAGE_TYPE_RECEIVED_FROMCLIENT);
		Record sentToClientRecord = recordService.getRecord(user.getLoginId(),user.getSenderID(), step, RecordService.MESSAGE_TYPE_SENTTOCLIENT);
		if(null!=clientRecord && null!=clientRecord.getMessage()){
			clientRecord.setMessage(clientRecord.getMessage().replaceAll("", "\r\n"));
		}
		if(null!=sentToClientRecord && null!=sentToClientRecord.getMessage()){
			sentToClientRecord.setMessage(sentToClientRecord.getMessage().replaceAll("", "\r\n"));
		}
		request.setAttribute("sentToClientRecord", sentToClientRecord);
		request.setAttribute("clientRecord", clientRecord);
		
		return "/viewlogs";
	}
	
 
	
}
