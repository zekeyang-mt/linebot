package com.metrics.linebot.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrics.linebot.handler.MessageHandler;
import com.metrics.linebot.utils.AuthChecker;

@RequestMapping("/linebot")
@RestController
public class RobotController {

	@Autowired
	private HttpServletRequest httpServletRequest;

	
	@GetMapping("/test")
	public ResponseEntity test() {
		return new ResponseEntity("Hello LineBot", HttpStatus.OK);
	}

	@RequestMapping("/callback")
	public void callback(@RequestBody String message) {
		System.out.println(message);
	}

	@PostMapping("/messaging")
	public ResponseEntity messagingAPI(@RequestHeader("X-Line-Signature") String X_Line_Signature,
			@RequestBody String requestBody) throws UnsupportedEncodingException, IOException {
		AuthChecker authChecker = new AuthChecker();
		MessageHandler messageHandler = new MessageHandler();
		System.out.println(requestBody);
			
		if (authChecker.checkFromLine(requestBody, X_Line_Signature)) {
			System.out.println("驗證通過");
			JSONObject object = new JSONObject(requestBody);
			for (int i = 0; i < object.getJSONArray("events").length(); i++) {
				if (object.getJSONArray("events").getJSONObject(i).getString("type").equals("message")) {
					messageHandler.doAction(object.getJSONArray("events").getJSONObject(i));
				}
			}
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		}
		System.out.println("驗證不通過");
		return new ResponseEntity<String>("Not line platform", HttpStatus.BAD_GATEWAY);
	}

}
