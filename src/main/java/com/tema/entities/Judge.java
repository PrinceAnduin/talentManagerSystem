package com.tema.entities;

import java.util.Date;

import lombok.Data;
@Data
public class Judge {
	private int id;
	private User user;
	private Company company;
	private Date juedgeDate;
	private int judgement;
}
