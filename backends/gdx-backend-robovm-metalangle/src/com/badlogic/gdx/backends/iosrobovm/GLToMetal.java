
package com.badlogic.gdx.backends.iosrobovm;

import org.robovm.apple.glkit.GLKViewDrawableColorFormat;
import org.robovm.apple.glkit.GLKViewDrawableDepthFormat;
import org.robovm.apple.glkit.GLKViewDrawableMultisample;
import org.robovm.apple.glkit.GLKViewDrawableStencilFormat;

import com.badlogic.gdx.backends.iosrobovm.bindings.metalangle.MGLDrawableColorFormat;
import com.badlogic.gdx.backends.iosrobovm.bindings.metalangle.MGLDrawableDepthFormat;
import com.badlogic.gdx.backends.iosrobovm.bindings.metalangle.MGLDrawableMultisample;
import com.badlogic.gdx.backends.iosrobovm.bindings.metalangle.MGLDrawableStencilFormat;

public class GLToMetal {
	public static MGLDrawableColorFormat from (GLKViewDrawableColorFormat in) {
		switch (in) {
		case RGB565:
			return MGLDrawableColorFormat.RGB565;
		case RGBA8888:
			return MGLDrawableColorFormat.RGBA8888;
		case SRGBA8888:
			return MGLDrawableColorFormat.SRGBA8888;
		default:
			throw new IllegalArgumentException();
		}
	}

	public static MGLDrawableDepthFormat from (GLKViewDrawableDepthFormat in) {
		switch (in) {
		case None:
			return MGLDrawableDepthFormat.None;
		case _16:
			return MGLDrawableDepthFormat._16;
		case _24:
			return MGLDrawableDepthFormat._24;
		default:
			throw new IllegalArgumentException();
		}
	}

	public static MGLDrawableStencilFormat from (GLKViewDrawableStencilFormat in) {
		switch (in) {
		case None:
			return MGLDrawableStencilFormat.None;
		case _8:
			return MGLDrawableStencilFormat._8;
		default:
			throw new IllegalArgumentException();
		}
	}

	public static MGLDrawableMultisample from (GLKViewDrawableMultisample in) {
		switch (in) {
		case None:
			return MGLDrawableMultisample.None;
		case _4X:
			return MGLDrawableMultisample._4X;
		default:
			throw new IllegalArgumentException();
		}
	}
}
