/**
 * Choose from 3 to 7 colors from CIE Lab or Lch with different graphic tools.
 *
 * (c) 2018
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 *
 * @author   Olivier Vicario https://www.perceptualcolor.org
 * @modified 01/21/2018
 * @version  1.0.0
 */

package template.tool;

import processing.app.Base;
import processing.app.tools.Tool;
import processing.app.ui.Editor;


// when creating a tool, the name of the main class which implements Tool must
// be the same as the value defined for project.name in your build.properties

public class PerceptualColorPicker implements Tool {
  Base base;
  ColorFrame frame;

  @Override
public String getMenuTitle() {
    return "Perceptual colorpicker";
  }


  @Override
public void init(Base base) {
    // Store a reference to the Processing application itself
    this.base = base;
  }


  @Override
public void run() {
		// Get the currently active Editor to run the Tool on it
		Editor editor = base.getActiveEditor();

		// Fill in author.name, author.url, tool.prettyVersion and
		// project.prettyName in build.properties for them to be auto-replaced
		// here.
		System.out.println("Perceptual colorpicker 1.0.0 by Olivier Vicario https://www.perceptualcolor.org");

		// charge un objet étendant un swing frame
		if (frame != null) {
			frame.setVisible(true);
		} else {
			//frame = new ColorFrame(editor);
			frame = new ColorFrame();
			frame.setVisible(true);
		}
  }
}
