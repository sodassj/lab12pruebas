package com.tecsup.petclinic.webs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DummyController {

	
	@GetMapping(value = "/dummy")
	public ResponseEntity<String> doNothing() {

		log.info("---");
		
		boolean flag = true;
	
		if (flag) {
			
			System.out.println("Hola");
		}
		
		int i = 0;
		
		while (flag) {
			i = i + 1;
		}
		
		
        int x = 10;
        int y = 2;
        int result = x / y;
		
        log.info("" + result);
        
		return ResponseEntity.ok(null);

	}
	
	
	
}
