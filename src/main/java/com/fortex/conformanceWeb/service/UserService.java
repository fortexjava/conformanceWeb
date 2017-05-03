/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fortex.conformanceWeb.pojo.User;
import com.fortex.conformanceWeb.utils.UUIDGenerator;
import com.fortex.lib.globalservices.GlobalRuntime;

/**
 * @author Administrator
 *
 */

@Service
public class UserService {
	
	public static String USER = "ctus";
	
	public static String ADMIN_USER = "admus";
	
	@Autowired
	@Qualifier("userJdbcTemplate")
	protected JdbcTemplate userJdbcTemplate;
	
	
	@Autowired
	@Qualifier("xRingJdbcTemplate")
	protected JdbcTemplate xRingJdbcTemplate;
	
	@Autowired
	private Environment env;
	
//	public boolean loginValid(String loginId,String password, int loginType,HttpServletRequest request){
//		String hashUserName= GlobalRuntime.hashPwd(loginId);
//		String hashPassword= GlobalRuntime.hashPwd(password);
//		String sql="SELECT count(1) FROM loginTable lt,systemconfigs sc WHERE lt.LoginHash = ? AND lt.PasswordHash = ? AND lt.loginType=? AND lt.domain != sc.DomainClosedAccts";
//		int result = userJdbcTemplate.queryForObject(sql,Integer.class,new Object[]{hashUserName,hashPassword,loginType});
//		if(result>0){
//			sql = "delete from Conformance_Test_Record where user_name = ?";
//			xRingJdbcTemplate.update(sql, new Object[]{loginId});
//			User user = new User();
//			user.setHashLoginId(loginId);
//			user.setHashLoginId(hashUserName);
//			user.setLoginId(loginId);
//			user.setLoginType(loginType);
//			String hashLoginType = GlobalRuntime.hashPwd(String.valueOf(loginType));
//			user.setHasLoginType(hashLoginType);
//			request.getSession().setAttribute(USER, user);
//			return true;
//		}
//		return false;
//	}
	
	public boolean loginValid(String loginId,String password,HttpServletRequest request){
		String hashUserName= GlobalRuntime.hashPwd(loginId);
		String hashPassword= GlobalRuntime.hashPwd(password);
		String sql="SELECT loginType,TargetID FROM loginTable lt,systemconfigs sc WHERE lt.LoginHash = ? AND lt.PasswordHash = ? AND lt.domain != sc.DomainClosedAccts";
		///List<Integer,String> loginTypes = userJdbcTemplate.queryForList(sql,Integer.class,new Object[]{hashUserName,hashPassword});
		List<Map<String, Object>> result = userJdbcTemplate.queryForList(sql,new Object[]{hashUserName,hashPassword});
		if(result.size()>0){
//			sql = "delete from Conformance_Test_Record where user_name = ?";
//			xRingJdbcTemplate.update(sql, new Object[]{loginId});
			
			Map<String,Object> obj = (Map<String,Object>)result.get(0);
			int loginType= (Integer.valueOf(obj.get("loginType").toString()));
			String senderID = obj.get("TargetID").toString();
			
			User user = new User();
			user.setHashLoginId(loginId);
			user.setHashLoginId(hashUserName);
			user.setLoginId(loginId);
			user.setLoginType(loginType);
			user.setSenderID(senderID);
			String hashLoginType = GlobalRuntime.hashPwd(String.valueOf(loginType));
			user.setHasLoginType(hashLoginType);
			request.getSession().setAttribute(USER, user);
			return true;
		}
		return false;
	}
	

	
	public void logout(HttpServletRequest request){
		request.getSession().setAttribute(USER, null);		
	}
	
	public User getCurrentUser(HttpServletRequest request){
		if(null!=request.getSession().getAttribute(USER))
			return (User) request.getSession().getAttribute(USER);
		return null;
	}
	
	public User getCurrentAdmUser(HttpServletRequest request){
		if(null!=request.getSession().getAttribute(ADMIN_USER))
			return (User) request.getSession().getAttribute(ADMIN_USER);
		return null;
	}
	
	
	public boolean adminLoginValid(String loginId,String password,HttpServletRequest request){
		String autUser= env.getProperty("admin.user");
		String autPassword = env.getProperty("admin.password");
		if(loginId.equals(autUser) && password.equals(autPassword)){
			String hashUserName= GlobalRuntime.hashPwd(loginId);
			User user = new User();			
			user.setHashLoginId(hashUserName);
			user.setLoginId(loginId);
			request.getSession().setAttribute(ADMIN_USER, user);
			return true;
		}
		return false;
	}
	
	public boolean updateUserStatus(String loginId,Integer isAutoPolit,Integer isTrading,int currentStep){
		String sql="select count(1) from User_status where user_name = ?";
		int result = xRingJdbcTemplate.queryForObject(sql,Integer.class,new Object[]{loginId});
		if(result > 0){
			sql= "update User_status set is_autopolit=? ,is_trading = ? ,current_step= ? where user_name = ?";
			xRingJdbcTemplate.update(sql, new Object[]{isAutoPolit, isTrading,currentStep,loginId});
			return true;
		}else{
			String id = UUIDGenerator.getUUID();
			sql = "insert into User_status values(?,?,?,?,?)";
			xRingJdbcTemplate.update(sql, new Object[]{id,loginId,isAutoPolit,isTrading,currentStep});
			return true;
		}		
	}
	
}
