package com.metrics.linebot.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/linebot")
@RestController
public class RobotController {

	@Value("${line.bot.channel-secret}")
	private String LINE_SECRET;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@GetMapping("/test")
	public ResponseEntity test() {
		return new ResponseEntity("Hello LineBot", HttpStatus.OK);
	}
	
	@RequestMapping(value="/callback")
	public void callback(@RequestBody String message) {
		System.out.println(message);
	}
	
}
