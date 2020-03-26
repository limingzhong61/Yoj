package com.yoj.utils.verification_code.util;


/**
 * This interface determines if a class can be configured by properties handled
 * by judge manager.
 */
public abstract class Configurable
{
	private Config config = null;

	public Config getConfig()
	{
		return this.config;
	}

	public void setConfig(Config config)
	{
		this.config = config;
	}
}
