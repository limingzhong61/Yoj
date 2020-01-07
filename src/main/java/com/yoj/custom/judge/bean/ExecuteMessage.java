package com.yoj.custom.judge.bean;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteMessage {

	private String error;

	private String stdout;

}