/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fortex.conformanceWeb.pojo.Record;
import com.fortex.conformanceWeb.pojo.Solution;
 
/**
 * @author Administrator
 *
 */
@Service
public class RecordService {
	// private final Logger log = Logger.getLogger(this.getClass());

	public static int MESSAGE_TYPE_SENTTOCLIENT = 1;
	public static int MESSAGE_TYPE_RECEIVED_FROMCLIENT = 0;

	public static int RECORD_STATUS_FAILED = 1;
	public static int RECORD_STATUS_PASSED = 0;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	@Qualifier("xRingJdbcTemplate")
	protected JdbcTemplate xRingJdbcTemplate;

	public boolean isRecordPassed(String username, int step) {
		String sql = "select status from Conformance_Test_Record where (user_name=? ) and step=? and route_type=?";
		try {			
			List<Integer> statuses = xRingJdbcTemplate.queryForList(sql,Integer.class,new Object[] { username, step, MESSAGE_TYPE_SENTTOCLIENT });
			if(statuses.size()>0){
				for(Integer status:statuses){
					if(status != RECORD_STATUS_PASSED)
						return false;
				}
				return true;
			}else
				return false;
		} catch (Exception e1) {
			 e1.printStackTrace();
			 return false;
		}
	}
		 

	public Record getRecord(String username, String senderId, int step,  int routeType) {
		String sql = "select * from Conformance_Test_Record where (user_name=? and sender_id=? ) and step=? and route_type=?";

		List<Map<String,Object>> records= xRingJdbcTemplate.queryForList(sql, new Object[]{username,senderId,step,routeType});
		if(records.size()==0){
//			sql = "select * from Conformance_Test_Record where sender_id=? and step=? and route_type=?";
//			records= xRingJdbcTemplate.queryForList(sql, new Object[]{senderId,step,routeType});
//			if(records.size() == 0)
				return null;
		}
		List<Record> results = mapToObject(records);
		Record record = new Record();
		record.setStatus(RECORD_STATUS_PASSED);
		record.setRouteType(routeType);
		record.setStep(step);
		for(Record rec:results){
			record.setId(rec.getId());
			if(record.getMessage()!=null && record.getMessage().length()>0)
				record.setMessage(record.getMessage() + "" + rec.getMessage());
			else
				record.setMessage(rec.getMessage());
			
			if(record.getReason()!=null && record.getReason().length()>0)
				record.setReason(record.getReason() + "" + rec.getReason());
			else
				record.setReason(rec.getReason());	
			
			if(rec.getStatus() == RECORD_STATUS_FAILED)
				record.setStatus(RECORD_STATUS_FAILED);
			
			if(null!= rec.getTag() && null!=rec.getCode()){
				record.setTag(rec.getTag());			
				record.setCode(rec.getCode());	
				Solution solution = adminService.searchSolutionObj(rec.getTag(), rec.getCode());
				record.setSolution(solution);
			}
		}
		return record;
	}
	
	private List<Record> mapToObject(List<Map<String,Object>> records){
        List<Record> result=new ArrayList<Record>();
        Map<String,Object> map=null;        
        if(records.size()>0){
            for(int i=0;i<records.size();i++){
                Record record=new Record();
                map=records.get(i);
                if(null!= map.get("id")){
                		record.setId(Integer.valueOf(map.get("id").toString()));                    
                }
                
                if(null!= map.get("step")){
            		record.setStep(Integer.valueOf(map.get("step").toString()));                    
                }
                
                if(null!= map.get("route_type")){
            		record.setRouteType(Integer.valueOf(map.get("route_type").toString()));                    
                }
                
                if(null!= map.get("status")){
            		record.setStatus(Integer.valueOf(map.get("status").toString()));                    
                }
                
                if(null!=map.get("user_name")){
                	record.setUserName(map.get("user_name").toString());
                }
                
                if(null!=map.get("message")){
                	record.setMessage(map.get("message").toString());
                }
                
                if(null!=map.get("reason")){
                	record.setReason(map.get("reason").toString());
                }
                 
                if(null!=map.get("tag")){
                	record.setTag( Integer.valueOf(map.get("tag").toString()));
                }
                
                if(null!=map.get("code")){
               	 	record.setCode( map.get("code").toString());
                }
                
                if(null!=map.get("sender_id")){
               	 	record.setSenderId( map.get("sender_id").toString());
                }
                
                result.add(record);
            }            
        }
        
        return result;
    }
	
	
	public boolean resetRecord(String loginId,String senderId){
		String sql = "delete from Conformance_Test_Record where user_name = ? or sender_id = ?";
		try{
			xRingJdbcTemplate.update(sql, new Object[]{loginId,senderId});
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
}
