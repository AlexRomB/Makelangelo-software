package com.marginallyclever.makelangelo.makeArt.imageConverter;

import java.beans.PropertyChangeEvent;

import com.marginallyclever.makelangelo.Translator;
import com.marginallyclever.makelangelo.makeArt.TransformedImage;
import com.marginallyclever.makelangelo.makeArt.imageFilter.Filter_BlackAndWhite;
import com.marginallyclever.makelangelo.turtle.Turtle;


/**
 * create random lines across the image.  Raise and lower the pen to darken the appropriate areas
 * @author Dan Royer
 */
public class Converter_RandomLines extends ImageConverter {
	static protected int numLines = 2500;
	
	@Override
	public String getName() {
		return Translator.get("ConverterRandomLinesName");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("total")) setLineCount((int)evt.getNewValue());
	}
	
	@Override
	public void finish() {
		// The picture might be in color.  Smash it to 255 shades of grey.
		Filter_BlackAndWhite bw = new Filter_BlackAndWhite(255);
		TransformedImage img = bw.filter(myImage);


		double stepSize = 5;
		if (stepSize < 1) stepSize = 1;

		// Color values are from 0...255 inclusive.  255 is white, 0 is black.
		// Lift the pen any time the color value is > level (128 or more).
		double level = 255.0 / 4.0;

		// from top to bottom of the margin area...
		float yBottom = (float)myPaper.getMarginBottom();
		float yTop    = (float)myPaper.getMarginTop()   ;
		float xLeft   = (float)myPaper.getMarginLeft()  ;
		float xRight  = (float)myPaper.getMarginRight() ;
		double dy = yTop - yBottom-1;
		double dx = xRight - xLeft-1;

		turtle = new Turtle();
		turtle.moveTo(0, yTop);

		double startPX = 0; 
		double startPY = yTop;

		int i;
		for(i=0;i<numLines;++i) {
			level = 200.0 * (double)i / (double)numLines;
			double endPX = xLeft   + (Math.random() * dx)+0.5; 
			double endPY = yBottom + (Math.random() * dy)+0.5; 

			convertAlongLine(startPX,startPY,endPX,endPY,stepSize,level,img);
			
			startPX = endPX;
			startPY = endPY;
		}
	}
	

	public int getLineCount() {
		return numLines;
	}
	public void setLineCount(int value) {
		if(value<1) value=1;
		numLines = value;
	}
}


/**
 * This file is part of Makelangelo.
 * <p>
 * Makelangelo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Makelangelo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Makelangelo.  If not, see <http://www.gnu.org/licenses/>.
 */
