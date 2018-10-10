package com.apap.tutorial5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.service.CarService;
import com.apap.tutorial5.service.DealerService;

/*
 * DealerController
 */
@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer) {
		dealerService.addDealer(dealer);
		return "add";
	}
	
	@RequestMapping("/dealer/view")
	public String viewId(@RequestParam(value="id",required = true) Long id, Model model) {
		DealerModel archive = dealerService.getDealerDetailById(id).get();
		model.addAttribute("dealer",archive);
		return "view-dealer";
	}

	@RequestMapping(value = "dealer/view" , method = RequestMethod.GET)
	private String viewDealer(@RequestParam ("dealerId") long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		
		List<CarModel> archiveListCar =carService.sortByPriceDesc(dealerId);
		dealer.setListCar(archiveListCar);

		model.addAttribute("dealer", dealer);
		return "view-dealer-with-car";
	}
	
	@RequestMapping(value = "/dealer/delete", method = RequestMethod.GET)
	private String deleteDealerById(@RequestParam("id") Long id, Model model) {
			dealerService.deleteDealer(id);
			return "delete";
	}
	
	@RequestMapping(value = "/dealer/update", method = RequestMethod.GET)
	private String updateDealerById(@RequestParam("id") Long id, Model model, @RequestParam("alamat") String alamat, @RequestParam("noTelp") String noTelp) {
			dealerService.updateDealer(id, alamat, noTelp);
			return "update";
	}
	
	@RequestMapping(value = "/dealer/view-all", method = RequestMethod.GET)
	public String viewAllDealer(Model model) {
		List<DealerModel> listAllDealer = dealerService.viewAllDealer();
		model.addAttribute("listAllDealer", listAllDealer);
		return "viewAll";
	}
}
