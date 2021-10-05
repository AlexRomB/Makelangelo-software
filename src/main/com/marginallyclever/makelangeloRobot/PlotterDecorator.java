package com.marginallyclever.makelangeloRobot;

import com.jogamp.opengl.GL2;

/**
 * MakelangeloRobotDecorator adds features to DrawPanel. An example is the Draw panel shows
 * WYSIWYG progress, while the filters might show conversion while-you-wait
 * updates.
 *
 * @author Dan Royer
 * @since 7.1.4
 */
public abstract interface PlotterDecorator {
	void render(GL2 gl2);
}
