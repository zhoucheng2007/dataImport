package com.blogzhou.common.configuration;

public class TestConfiguration {

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
	}

}
