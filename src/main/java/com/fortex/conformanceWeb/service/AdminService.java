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
public class AdminService {
	@Autowired
	@Qualifier("xRingJdbcTemplate")
	protected JdbcTemplate xRingJdbcTemplate;
	
	public void addSolution(Solution solution){
		String sql = "insert into Solution values(?,?,?)";
		xRingJdbcTemplate.update(sql, new Object[]{solution.getTag(),solution.getCode(),solution.getSolution()});
	}
	
	public void updateSolution(String solution,int tag,String code){
		String sql="update Solution set solution = ? where tag=? and code = ?";
		xRingJdbcTemplate.update(sql, new Object[]{solution,tag,code});
	}
	
	public String searchSolution(int tag,String code){
		String sql="select solution from Solution where tag=? and code=?";
		List<String> result = xRingJdbcTemplate.queryForList(sql, String.class, new Object[]{tag,code});
		if(null!=result && result.size()>0)
			return result.get(0);
		return null;
	}
	
	
	public Solution searchSolutionObj(int tag,String code){
		String sql="select * from Solution where tag=? and code=?";
		List<Map<String,Object>> solutions= xRingJdbcTemplate.queryForList(sql, new Object[]{tag,code});
		List<Solution> results = mapToObject(solutions);
		if(results.size()>0){
			return results.get(0);
		}
		return null;
	}
	
	
	private List<Solution> mapToObject(List<Map<String,Object>> solutions){
        List<Solution> result=new ArrayList<Solution>();
        Map<String,Object> map=null;        
        if(solutions.size()>0){
            for(int i=0;i<solutions.size();i++){
            	Solution solution=new Solution();
                map=solutions.get(i);
                if(null!= map.get("id")){
                	solution.setId(Integer.valueOf(map.get("id").toString()));                    
                }
                
                if(null!= map.get("tag")){
            		solution.setTag(Integer.valueOf(map.get("tag").toString()));                    
                }
                
                if(null!= map.get("code")){
                	solution.setCode(map.get("code").toString());                    
                }
                
                if(null!= map.get("solution")){
            		solution.setSolution((map.get("solution").toString()));                    
                }
                                                 
                result.add(solution);
            }            
        }
        
        return result;
    }
	
}
