package com.metrics.linebot.handler;

import java.io.IOException;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.metrics.linebot.utils.PropertiesReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageHandler {

	private OkHttpClient client = new OkHttpClient();
	Properties prop = PropertiesReader.getProperties();
	String LINE_TOKEN = prop.getProperty("line.bot.channel-token");

	public void doAction(JSONObject event) {
		switch (event.getJSONObject("message").getString("type")) {
			case "text":
				if (event.getJSONObject("message").getString("text").contains("停止")) {
					text(event.getString("replyToken"), "AlertSystem will stop alert message...");
				} else if (event.getJSONObject("message").getString("text").contains("轉傳")) {
					text(event.getString("replyToken"), "AlertSystem will forward alert message...");
				}
				break;
			default:
				text(event.getString("replyToken"), "本系統僅接受文字訊息");
				break;
		}
	}

	private void text(String replyToken, String text) {
		JSONObject body = new JSONObject();
		JSONArray messages = new JSONArray();
		JSONObject message = new JSONObject();
		message.put("type", "text");
		message.put("text", text);
		messages.put(message);
		body.put("replyToken", replyToken);
		body.put("messages", messages);
		sendLinePlatform(body);
	}

	private void sticker(String replyToken, String packageId, String stickerId) {
		JSONObject body = new JSONObject();
		JSONArray messages = new JSONArray();
		JSONObject message = new JSONObject();
		message.put("type", "sticker");
		message.put("packageId", packageId);
		message.put("stickerId", stickerId);
		messages.put(message);
		body.put("replyToken", replyToken);
		body.put("messages", messages);
		sendLinePlatform(body);
	}

	public void sendLinePlatform(JSONObject json) {
		Request request = new Request.Builder().url("https://api.line.me/v2/bot/message/reply")
				.header("Authorization", "Bearer {" + LINE_TOKEN + "}")
				.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString())).build();
		client.newCall(request).enqueue((Callback) new Callback() {

			public void onResponse(Call call, Response response) throws IOException {
				System.out.println(response.body());
			}

			public void onFailure(Call call, IOException e) {
				System.err.println(e);
			}
		});
	}

}
