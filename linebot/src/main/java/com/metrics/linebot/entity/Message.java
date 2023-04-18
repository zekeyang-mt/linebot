package com.metrics.linebot.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MESSAGE")
public class Message implements Serializable{

	private static final long serialVersionUID = -5387706567843475294L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="message_id")
	private Integer id;
	
	@Column(name="message_type")
	private String type;
	
	@Column(name="message_text")
	private String text;
}
