package com.yoj.web.util.verification_code;

import java.awt.image.BufferedImage;

/**
 * {@link BackgroundProducer} is responsible for adding background to an image.
 */
public interface BackgroundProducer
{
	public abstract BufferedImage addBackground(BufferedImage image);
}
