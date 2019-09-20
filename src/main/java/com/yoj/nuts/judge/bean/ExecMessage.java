package com.yoj.nuts.judge.bean;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExecMessage {

	private String error;

	private String stdout;

}