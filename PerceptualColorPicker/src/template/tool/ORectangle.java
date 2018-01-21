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
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author olivier
 */
public class ORectangle extends Group {

    public Line ligne;
    public Thumb thumb1;
    public Thumb thumb2;
    public int nbCouleurs;
    public Circle[] circleCarets;

    public ORectangle(double _startX, double _startY, double _endX, double _endY) {

        ligne = new Line(_startX, _startY, _endX, _endY);
        ligne.setStroke(Color.WHITE);
        ligne.setOpacity(0.5);
        this.getChildren().add(ligne);

        thumb1 = new Thumb(_startX, _startY, 5);
        thumb1.localisation = "debut";
        thumb2 = new Thumb(_endX, _endY, 5);
        thumb2.localisation = "fin";
        this.getChildren().add(thumb1);
        this.getChildren().add(thumb2);

        nbCouleurs = 2;
        circleCarets = new Circle[nbCouleurs];
        for (int i = 0; i < nbCouleurs; i++) {
            circleCarets[i] = new Circle();
            this.getChildren().add(circleCarets[i]);
        }

        dessinCarets(_startX, _startY, _endX, _endY);

        this.setFocusTraversable(true);//sinon ca ne tourne pas !

    }
    
    public Point2D rotation(Point2D p, Point2D c, double angle) {
        p = new Point2D(p.getX() - c.getX(), p.getY() - c.getY());
        double newx = p.getX() * Math.cos(angle) - p.getY() * Math.sin(angle);
        double newy = p.getX() * Math.sin(angle) + p.getY() * Math.cos(angle);
        Point2D newPoint = new Point2D(newx, newy);
        return newPoint.add(c);
        //return newPoint;
    }

    public void dessinCarets(double startX, double startY, double endX, double endY) {
        circleCarets[0].setCenterX(startX);
        circleCarets[0].setCenterY(endY);
        circleCarets[0].setRadius(2);
        circleCarets[0].setFill(Color.TRANSPARENT);
        circleCarets[0].setOpacity(0.5);
        circleCarets[0].setStroke(Color.WHITE);

        circleCarets[1].setCenterX(endX);
        circleCarets[1].setCenterY(startY);
        circleCarets[1].setRadius(2);
        circleCarets[1].setFill(Color.TRANSPARENT);
        circleCarets[1].setOpacity(0.5);
        circleCarets[1].setStroke(Color.WHITE);
    }

    class Thumb extends Circle {

        String localisation;

        Thumb(double centerX, double centerY, double radius) {
            super(centerX, centerY, radius);
            setStroke(Color.WHITE);
            setStrokeWidth(2);
            setOpacity(0.5);
            setFill(Color.TRANSPARENT);
            //final DragContext dragContext = new DragContext();

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

            setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
				public void handle(MouseEvent me) {

                }
            });

            setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
				public void handle(MouseEvent me) {
                    if (me.getX() > 0 && me.getX() < 430 && me.getY() > 0 && me.getY() < 430) {
                        setCenterX(me.getX());
                        setCenterY(me.getY());
                        if (localisation == "debut") {
                            ligne.setStartX(getCenterX());
                            ligne.setStartY(getCenterY());
                        } else {
                            ligne.setEndX(getCenterX());
                            ligne.setEndY(getCenterY());
                        }
                        dessinCarets(ligne.getStartX(), ligne.getStartY(), ligne.getEndX(), ligne.getEndY());
                    }
                }
            });

        }
    }
}
