package com.badlogic.gdx.physics.box2d.utils;

import java.io.File;

import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;
import com.badlogic.gdx.jnigen.BuildTarget.TargetOs;

public class Box2DBuild {
	public static void main(String[] args) throws Exception {
		String classpath = "bin" + File.pathSeparator + "../../../gdx/bin";
		if (args.length > 0) {
			classpath = args[0];
			for (int x = 1; x < args.length; x++)
				classpath += File.pathSeparator + args[x];
		}

		BuildTarget win32 = BuildTarget.newDefaultTarget(TargetOs.Windows, false);
		BuildTarget win64 = BuildTarget.newDefaultTarget(TargetOs.Windows, true);
		BuildTarget lin32 = BuildTarget.newDefaultTarget(TargetOs.Linux, false);
		BuildTarget lin64 = BuildTarget.newDefaultTarget(TargetOs.Linux, true);
		BuildTarget android = BuildTarget.newDefaultTarget(TargetOs.Android, false);
		BuildTarget mac64 = BuildTarget.newDefaultTarget(TargetOs.MacOsX, true);
		BuildTarget ios = BuildTarget.newDefaultTarget(TargetOs.IOS, false);
		new NativeCodeGenerator().generate("src", classpath, "jni");
		new AntScriptGenerator().generate(new BuildConfig("gdx-box2d"), win32, win64, lin32, lin64, mac64, android, ios);		
	}
}