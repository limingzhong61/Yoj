package com.yoj.web.util.verification_code.impl;

import com.yoj.web.util.verification_code.NoiseProducer;
import com.yoj.web.util.verification_code.util.Configurable;

import java.awt.image.BufferedImage;

/**
 * Imlemention of NoiseProducer that does nothing.
 * 
 * @author Yuxing Wang
 */
public class NoNoise extends Configurable implements NoiseProducer
{
	/**
	 */
	public void makeNoise(BufferedImage image, float factorOne,
			float factorTwo, float factorThree, float factorFour)
	{
		//Do nothing.
	}
}
