package com.badlogic.gdx.jnigen.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Desu
 */
public class JnigenPlugin implements Plugin<Project> {
	@Override
	public void apply(Project project) {
		project.getTasks().create("jnigen", JnigenTask.class);
	}
}
