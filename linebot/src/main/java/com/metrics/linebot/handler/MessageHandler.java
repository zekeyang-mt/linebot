package com.metrics.linebot.handler;

import java.io.IOException;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.metrics.linebot.utils.PropertiesReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class MessageHandler {

	private OkHttpClient client = new OkHttpClient();
	Properties prop = PropertiesReader.getProperties();
	String LINE_TOKEN = prop.getProperty("line.bot.channel-token");
	

	public void doReply(JSONObject event, String type) {
		switch (event.getJSONObject("message").getString("type")) {
			case "text":
				if (event.getJSONObject("message").getString("text").contains("關閉")) {
					text(event.getString("replyToken"), type, "AlertSystem will close alert message...");
					// call alert api to stop sending message
				} else if (event.getJSONObject("message").getString("text").contains("轉傳")) {
					text(event.getString("replyToken"), type, "AlertSystem will forward alert message...");
					// call alert api to forward message
				} else {
					text(event.getString("replyToken"), type, "Please respond in accordance to guidelines...");
				}
				break;
			default:
				text(event.getString("replyToken"), type, "AlertSystem only accepts text message...");
				break;
		}
	}
	
	public void doPush(String userId, String type, String text) {
		text(userId, type, text);
	}

	private void text(String receipient, String type, String text) {
		JSONObject body = new JSONObject();
		JSONArray messages = new JSONArray();
		JSONObject message = new JSONObject();
		message.put("type", "text");
		message.put("text", text);
		messages.put(message);
		
		if("reply".equals(type)) {
			body.put("replyToken", receipient);
		} else if("push".equals(type)) {
			body.put("to", receipient);
		} else if("muti".equals(type)) {
			String[] receipients = receipient.split(",");
			body.put("to", receipients);
		}
		
		body.put("messages", messages);
		sendLinePlatform(body, type);
	}

	public void sendLinePlatform(JSONObject json, String type) {
		
		String url = null;
		if("reply".equals(type)) {
			url = "https://api.line.me/v2/bot/message/reply";
		} else if("push".equals(type)) {
			url = "https://api.line.me/v2/bot/message/push";
		} else if("muti".equals(type)) {
			url = "https://api.line.me/v2/bot/message/multicast";
		}
		
		Request request = new Request.Builder().url(url)
				.header("Authorization", "Bearer {" + LINE_TOKEN + "}")
				.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString())).build();
		client.newCall(request).enqueue((Callback) new Callback() {

			public void onResponse(Call call, Response response) throws IOException {
				System.out.println(response.body());
				response.close();
			}

			public void onFailure(Call call, IOException e) {
				System.err.println(e);
			}
		});
	}

}
