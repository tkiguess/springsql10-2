package com.example.springsql102;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/")
    public String listJobs(Model model){
        model.addAttribute("customers", customerRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String jobForm(Model model){
        model.addAttribute("customer", new Customer());
        return "customerform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Customer customer, BindingResult result){
        if (result.hasErrors()){
            return "customerform";
        }
        customerRepository.save(customer);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model){
        model.addAttribute("customer", customerRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/delete/{id}")
    public String delJob(@PathVariable("id") long id){
        customerRepository.deleteById(id);
        return "redirect:/";
    }
}