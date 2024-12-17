package com.app.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.error.exception.ResourceAlreadyExistsException;

@RestController
@RequestMapping(path = RequestPath.APPUSER)
public class TestController {
	
	@GetMapping(path = "test")
	public void test(@RequestParam String name,@RequestParam String lastName) {
		System.out.println("You name is: " + name);
		if (true)throw new ResourceAlreadyExistsException("my message");
		Long s= null;
		long m=s+10;
	}

}
