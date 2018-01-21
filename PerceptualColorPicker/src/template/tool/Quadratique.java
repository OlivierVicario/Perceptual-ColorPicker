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

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

/**
 *
 * courbe de bézier quadratique B(t) = ( 1 - t )^2 * P0 + 2 * t * ( 1 - t ) * P1
 * * + t^2 * P2
 *
 */
public class Quadratique extends Group {

    public QuadCurve q;
    public Thumb thumb1;
    public Thumb thumb2;
    public Thumb thumb3;
    public Line l;
    public ArrayList<Point2D> QPoints;
    public int nbCouleurs;
    public Circle[] circleCarets;
    //public QuadBezierCurve2D qbc;

    Quadratique(double p0x, double p0y,
            double pbx, double pby,
            double p2x, double p2y,
            int _nbCouleurs) {

        nbCouleurs = _nbCouleurs;
        double p1x = trouveCoordControl(p0x, pbx, p2x);
        double p1y = trouveCoordControl(p0y, pby, p2y);
        q = new QuadCurve(p0x, p0y, p1x, p1y, p2x, p2y);
        q.setFill(Color.TRANSPARENT);
        q.setOpacity(0.5);
        q.setStroke(Color.WHITE);
        this.getChildren().add(q);

        thumb1 = new Thumb(p0x, p0y, 5);
        thumb1.localisation = "debut";
        thumb2 = new Thumb(p2x, p2y, 5);
        thumb2.localisation = "fin";
        thumb3 = new Thumb((0.25 * q.getStartX()) + (0.5 * q.getControlX()) + (0.25 * q.getEndX()), (0.25 * q.getStartY()) + (0.5 * q.getControlY()) + (0.25 * q.getEndY()), 5);
        thumb3.localisation = "controle";
        this.getChildren().add(thumb1);
        this.getChildren().add(thumb2);
        this.getChildren().add(thumb3);
        nbCouleurs = _nbCouleurs;

        circleCarets = new Circle[nbCouleurs - 2];

        for (int i = 0; i < nbCouleurs - 2; i++) {
            circleCarets[i] = new Circle();
            this.getChildren().add(circleCarets[i]);
        }
        dessinCarets(q.getStartX(), q.getStartY(), q.getControlX(), q.getControlY(), q.getEndX(), q.getEndY(), nbCouleurs);
    }

    class Thumb extends Circle {

        String localisation;

        Thumb(double centerX, double centerY, double radius) {
            super(centerX, centerY, radius);
            setStroke(Color.WHITE);
            setOpacity(0.5);
            setStrokeWidth(2);
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
                            q.setStartX(getCenterX());
                            q.setStartY(getCenterY());
                            thumb3.setCenterX((0.25 * q.getStartX()) + (0.5 * q.getControlX()) + (0.25 * q.getEndX()));
                            thumb3.setCenterY((0.25 * q.getStartY()) + (0.5 * q.getControlY()) + (0.25 * q.getEndY()));
                        } else {
                            if (localisation == "fin") {
                                q.setEndX(getCenterX());
                                q.setEndY(getCenterY());
                                thumb3.setCenterX((0.25 * q.getStartX()) + (0.5 * q.getControlX()) + (0.25 * q.getEndX()));
                                thumb3.setCenterY((0.25 * q.getStartY()) + (0.5 * q.getControlY()) + (0.25 * q.getEndY()));
                            } else {
                                q.setControlX(trouveCoordControl(q.getStartX(), getCenterX(), q.getEndX()));
                                q.setControlY(trouveCoordControl(q.getStartY(), getCenterY(), q.getEndY()));
                                thumb3.setCenterX((0.25 * q.getStartX()) + (0.5 * q.getControlX()) + (0.25 * q.getEndX()));
                                thumb3.setCenterY((0.25 * q.getStartY()) + (0.5 * q.getControlY()) + (0.25 * q.getEndY()));

                            }
                        }

                        dessinCarets(q.getStartX(), q.getStartY(),
                                q.getControlX(), q.getControlY(),
                                q.getEndX(), q.getEndY(),
                                nbCouleurs);
                    }
                }
            });

            setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
				public void handle(MouseEvent me) {

                }
            });

        }
    }

    public double trouveCoordControl(double p0, double pb, double p2) {
        double p1 = (pb - 0.25 * p0 - 0.25 * p2) / 0.5;
        return p1;
    }

    public void dessinCarets(double p0x, double p0y,
            double p1x, double p1y,
            double p2x, double p2y,
            int _nbCouleurs) {
        computeQPoints(p0x, p0y, p1x, p1y, p2x, p2y, nbCouleurs);
        for (int i = 0; i < QPoints.size(); i++) {
            circleCarets[i].setCenterX(QPoints.get(i).getX());
            circleCarets[i].setCenterY(QPoints.get(i).getY());
            circleCarets[i].setRadius(2);
            circleCarets[i].setFill(Color.TRANSPARENT);
            circleCarets[i].setOpacity(0.5);
            circleCarets[i].setStroke(Color.WHITE);
        }

    }

    public void computeQPoints(double p0x, double p0y,
            double p1x, double p1y,
            double p2x, double p2y,
            int nbCouleurs) {

        QPoints = new ArrayList<Point2D>();
        double pas = 1.0 / (nbCouleurs - 1);

        double t = pas;
        for (double i = 0; i < circleCarets.length; i++) {
            double bx = ((1 - t) * (1 - t) * p0x) + (2 * t * (1 - t) * p1x) + (t * t * p2x);
            double by = ((1 - t) * (1 - t) * p0y) + (2 * t * (1 - t) * p1y) + (t * t * p2y);
            QPoints.add(new Point2D(bx, by));
            t += pas;
        }

    }

}
