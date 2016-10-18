package com.kamesuta.mc.autoinput;

import java.text.DecimalFormat;

public abstract class MetaParser {
	private static final DecimalFormat signformat = new DecimalFormat(".##");

	public static String format(final float f) {
		if (f == 0)
			return "0";

		final String str = signformat.format(f);

		final String cut = ".0";

		int end = str.length();
		int last = cut.length();

		while (end != 0 && last != 0) {
			if (cut.charAt(last - 1) == str.charAt(end - 1))
				end--;
			else
				last--;
		}
		return str.substring(0, end);
	}

	public abstract MetaParser parse(String src, String key, String value);

	public abstract MetaParser reset();

	public abstract String compose();
}