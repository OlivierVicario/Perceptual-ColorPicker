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

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Thumb extends Circle {

    Thumb() {
        super();
    }

    Thumb(double centerX, double centerY) {
        super(centerX, centerY, 5);
        setStroke(Color.WHITE);
        setStrokeWidth(2);
        setOpacity(0.5);
        setFill(Color.TRANSPARENT);

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
			public void handle(MouseEvent me) {
                setStrokeWidth(4);
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
			public void handle(MouseEvent me) {
                setStrokeWidth(2);
            }
        });

        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
			public void handle(MouseEvent me) {
                if (me.getX() > 0 && me.getX() < 430 && me.getY() > 0 && me.getY() < 430) {
                    setCenterX(me.getX());
                    setCenterY(me.getY());
                }
            }
        });
    }
}
