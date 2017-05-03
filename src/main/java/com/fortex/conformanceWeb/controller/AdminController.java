/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fortex.conformanceWeb.form.SolutionForm;
import com.fortex.conformanceWeb.pojo.Solution;
import com.fortex.conformanceWeb.service.AdminService;

/**
 * @author Ivan Huo
 *
 */

@Controller
@RequestMapping("/admin/")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	
	
	@GetMapping("/solutionSearch")
	public String solution(SolutionForm solutionForm){
		return "/solutionSearch";
	}
	
	@PostMapping("/solutionSearch")
	public String search(@Valid SolutionForm solutionForm,BindingResult bindingResult,HttpServletRequest request){
 		 if (bindingResult.hasErrors()) {
	            return "/solutionSearch";
	     }
		String solution = adminService.searchSolution(solutionForm.getTag(), solutionForm.getCode());
		request.setAttribute("tag", solutionForm.getTag());
		request.setAttribute("code", solutionForm.getCode());
		if(null!=solution){		
			request.setAttribute("solution", solution);
		}
		return "/solution";
	}
 	
 	@PostMapping("/solutionSave")
 	public String solutionSave(@Valid SolutionForm form,BindingResult bindingResult){
 		 if (bindingResult.hasErrors()) {
	            return "/solution";
	     }
 		Solution solution = new Solution();
 		solution.setCode(form.getCode());
 		solution.setTag(form.getTag());
 		solution.setSolution(form.getSolution());
 		try{
 			adminService.addSolution(solution);
 		}
 		catch(Exception e){
 			e.printStackTrace();
 			 bindingResult.addError(new FieldError("saveInfo","saveFailed","Save solution failed"));
 			return "/solution";
 		}
 		
 		return "/solutionSuccess";
 	}
 	
 	@PostMapping("/solutionUpdate")
 	public String solutionUpdate(@Valid SolutionForm form,BindingResult bindingResult){
 		if (bindingResult.hasErrors()) {
	            return "/solution";
	    } 		
 		try{
 			adminService.updateSolution(form.getSolution(),form.getTag(),form.getCode());
 		}
 		catch(Exception e){
 			 e.printStackTrace();
 			 bindingResult.addError(new FieldError("saveInfo","saveFailed","Update solution failed"));
 			return "/solution";
 		}
 		
 		return "/solutionSuccess";
 	}
 	
 	
}
