package com.metrics.linebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.metrics.linebot.controller.RobotController;

@LineMessageHandler
@SpringBootApplication
public class LinebotApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinebotApplication.class, args);
		
		/*
		RobotController rController = new RobotController();
		rController.pushAPI("U1ed6ab0ea07cdca3d199178499d4741d", "push", "重大突發金融事件通報_檢測簡訊({date}) :\n"
				+ "EDM {rule_name}目前報價為{last_price}，\n"
				+ "請檢核是否正確。\n"
				+ "如果想把通報關掉請回復 關閉_{alert_id}。\n"
				+ "如果想轉傳請回復 轉傳_{alert_id}。");
		*/
	}

	
	@EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        System.out.println("event: " + event);
        return new TextMessage(event.getMessage().getText());
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
}
