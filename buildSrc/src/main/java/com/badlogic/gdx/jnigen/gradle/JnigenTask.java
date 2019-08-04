package com.badlogic.gdx.jnigen.gradle;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.plugins.JavaPluginConvention;

import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.BuildTarget.TargetOs;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;

/**
 * @author Desu
 */
public class JnigenTask extends DefaultTask {
	static boolean DEBUG = false;
	String sharedLibName = null;
	String temporaryDir = "target";
	String libsDir = "libs";
	String jniDir = "jni";
	
	NativeCodeGeneratorConfig nativeCodeGeneratorConfig = new NativeCodeGeneratorConfig();
	ArrayList<BuildTarget> targets = new ArrayList<BuildTarget>();
	Action<BuildTarget> all = null;

	@TaskAction
	public void run() {
		if(sharedLibName == null)
			throw new RuntimeException("sharedLibName must be defined");

		//Gradle Tasks are executed in the main project working directory. Supply actual subproject path where necessary.
		String subProjectDir = getProject().getProjectDir().getAbsolutePath() + File.separator;
		
		if(DEBUG)
		{
			System.out.println("subProjectDir " + subProjectDir);
			System.out.println("sharedLibName " + sharedLibName);
			System.out.println("nativeCodeGeneratorConfig " + nativeCodeGeneratorConfig);
		}
		
		try
		{
			new NativeCodeGenerator().generate(subProjectDir + nativeCodeGeneratorConfig.sourceDir, nativeCodeGeneratorConfig.classpath,
					subProjectDir + nativeCodeGeneratorConfig.jniDir, nativeCodeGeneratorConfig.includes,
					nativeCodeGeneratorConfig.excludes);
		}
		catch(Exception e)
		{
			throw new RuntimeException("NativeCodeGenerator threw exception", e);
		}
		
		BuildConfig buildConfig = new BuildConfig(sharedLibName, temporaryDir, libsDir, subProjectDir + jniDir);
		new AntScriptGenerator().generate(buildConfig, targets.toArray(new BuildTarget[0]));
	}

	public void nativeCodeGenerator(Action<NativeCodeGeneratorConfig> container) {
		container.execute(nativeCodeGeneratorConfig);
	}

	public void all(Action<BuildTarget> container) {
		this.all = container;
	}

	public void add(TargetOs type, boolean is64Bit) {
		add(type, is64Bit, null);
	}

	public void add(TargetOs type, boolean is64Bit, Action<BuildTarget> container) {
		BuildTarget target = BuildTarget.newDefaultTarget(type, is64Bit);

		if (all != null)
			all.execute(target);
		if(container != null)
			container.execute(target);
		
		targets.add(target);
	}

	class NativeCodeGeneratorConfig {
		String sourceDir = "src";
		String classpath;
		String jniDir = "jni";
		String[] includes = null;
		String[] excludes = null;
		
		public NativeCodeGeneratorConfig() {
			JavaPluginConvention javaPlugin = getProject().getConvention().getPlugin(JavaPluginConvention.class);
		    SourceSetContainer sourceSets = javaPlugin.getSourceSets();
		    SourceSet main = sourceSets.findByName("main");
		    classpath = main.getRuntimeClasspath().getAsPath();
		}
		
		@Override
		public String toString() {
			return "NativeCodeGeneratorConfig[sourceDir=`" + sourceDir + "`, classpath=`" + classpath + "`, jniDir=`"
					+ jniDir + "`, includes=`" + (includes == null ? "null" : Arrays.toString(includes))
					+ "`, excludes=`" + (includes == null ? "null" : Arrays.toString(excludes)) + "`]";
		}
	}
}
