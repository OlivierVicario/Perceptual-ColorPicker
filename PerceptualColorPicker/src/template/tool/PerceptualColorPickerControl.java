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

import static template.tool.ColorSpaceConvertion.colorToLab;
import static template.tool.ColorSpaceConvertion.colorToLch;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

public class PerceptualColorPickerControl extends AnchorPane implements Initializable {

	// constantes ColorSpaceConversion
	final float minL = 0.0f, maxL = 100.0f;
	final float mina = -86.18426f, maxa = 98.25467f;
	final float minb = -107.855545f, maxb = 94.48676f;
	final float minc = 0.0f, maxc = 133.80948f;
	final float minh = 0.0f, maxh = 360.0f;

	Label outilEnCours;
	Label modeEnCours;
	Label planEnCours;
	Label codeEnCours;
	Rectangle styleEnCours;

	int diviSides;
	WritableImage wi;
	Droite droite = null;
	Quadratique quadratic = null;
	ORectangle orectangle = null;
	Polygone polygone = null;
	Palette palette = null;

	Color c;
	int value = 50;
	String[] codes;

	private String darkStyleUrl = getClass().getResource("darkstyle.css").toExternalForm();
	private String lightStyleUrl = getClass().getResource("lightstyle.css").toExternalForm();

	@FXML
	private Label mSegment;
	@FXML
	private Label mQuadratic;
	@FXML
	private Label mRectangle;
	@FXML
	private Label mPolygon;
	@FXML
	private Circle curseurO;
	@FXML
	private Label ma_b;
	@FXML
	private Label ma_L;
	@FXML
	private Label mb_L;
	@FXML
	private Label mc_H;
	@FXML
	private Label mc_L;
	@FXML
	private Label mH_L;
	@FXML
	private Label mOrtho;
	@FXML
	private Label mContraste;
	@FXML
	private Label mCenter;
	@FXML
	private Label mRgb;
	@FXML
	private Label mLab;
	@FXML
	private Label mLch;
	@FXML
	private Label mWeb;
	@FXML
	private Label labelOrtho;
	@FXML
	private Label mPlusDivision;
	@FXML
	private Label mMoinsDivision;
	@FXML
	private Label labelDivision;
	@FXML
	private Circle curseurC;
	@FXML
	private Canvas canvas;
	@FXML
	private Circle gradient;
	@FXML
	private Pane paneCanvas;
	@FXML
	private HBox hboxPalette;
	@FXML
	private HBox hboxCodes;
	@FXML
	private Label mSnapshot;
	// @FXML
	// private AnchorPane this;
	@FXML
	private VBox vboxSnapshot;
	@FXML
	private Label labelSnapshot;
	@FXML
	private Rectangle buttonDark;
	@FXML
	private Rectangle buttonLight;
	@FXML
	private Label mPalette;
	@FXML
	private Label mCopy;
	@FXML
	private Label mSNAPSHOT;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// this.getScene().getStylesheets().add(darkStyleUrl);
		outilEnCours = mSegment;
		mSegment.setStyle("-fx-text-fill: #fff;");
		modeEnCours = mOrtho;
		mOrtho.setStyle("-fx-text-fill: #fff;");
		planEnCours = ma_b;
		ma_b.setStyle("-fx-text-fill: #fff;");
		codeEnCours = mLab;
		mLab.setStyle("-fx-text-fill: #fff;");
		labelSnapshot.setText("Lab");
		diviSides = 5;
		labelDivision.setText(String.valueOf(diviSides));
		styleEnCours = buttonDark;
		creationOutils();
		choixOutils();
		dessinOrtho(50);

	}

	@FXML
	private void menuOutilsClicked(MouseEvent event) {
		paneCanvas.getChildren().clear();// le parent des outils est le paneCanvas

		mSegment.setStyle("-fx-text-fill: #888;");
		mQuadratic.setStyle("-fx-text-fill: #888;");
		mRectangle.setStyle("-fx-text-fill: #888;");
		mPalette.setStyle("-fx-text-fill: #888;");
		mPolygon.setStyle("-fx-text-fill: #888;");

		Label l = (Label) event.getSource();
		l.setStyle("-fx-text-fill: #fff;");
		outilEnCours = l;

		choixOutils();
		majCouleurs();
	}

	@FXML
	private void menuPlansClicked(MouseEvent event) {
		ma_b.setStyle("-fx-text-fill: #888;");
		ma_L.setStyle("-fx-text-fill: #888;");
		mb_L.setStyle("-fx-text-fill: #888;");
		mc_H.setStyle("-fx-text-fill: #888;");
		mc_L.setStyle("-fx-text-fill: #888;");
		mH_L.setStyle("-fx-text-fill: #888;");
		Label l = (Label) event.getSource();
		l.setStyle("-fx-text-fill: #fff;");
		planEnCours = l;
		if (l == ma_b) {
			labelOrtho.setText("L");
		}
		if (l == ma_L) {
			labelOrtho.setText("b");
		}
		if (l == mb_L) {
			labelOrtho.setText("a");
		}
		if (l == mc_H) {
			labelOrtho.setText("L");
		}
		if (l == mc_L) {
			labelOrtho.setText("H");
		}
		if (l == mH_L) {
			labelOrtho.setText("c");
		}

		if (modeEnCours == mOrtho) {
			dessinOrtho(value);
		} else {
			dessinContraste(value);
		}
		majCouleurs();
	}

	@FXML
	private void menuModesClicked(MouseEvent event) {
		mOrtho.setStyle("-fx-text-fill: #888;");
		mContraste.setStyle("-fx-text-fill: #888;");
		Label l = (Label) event.getSource();
		l.setStyle("-fx-text-fill: #fff;");
		modeEnCours = l;
		if (l == mOrtho) {
			curseurO.setCenterX(810);
			dessinOrtho(50);
		} else {
			curseurC.setCenterX(810);
			dessinContraste(180);
		}
	}

	@FXML
	private void menuCenterClicked(MouseEvent event) {
		if (outilEnCours == mPolygon) {
			polygone.center.setCenterX(215);
			polygone.center.setCenterY(215);
		}
		if (outilEnCours == mPalette) {
			palette.center.setCenterX(215);
			palette.center.setCenterY(215);
		}
	}

	@FXML
	private void menuColorCodeClicked(MouseEvent event) {
		mRgb.setStyle("-fx-text-fill: #888;");
		mLab.setStyle("-fx-text-fill: #888;");
		mLch.setStyle("-fx-text-fill: #888;");
		mWeb.setStyle("-fx-text-fill: #888;");
		Label l = (Label) event.getSource();
		l.setStyle("-fx-text-fill: #fff;");
		codeEnCours = l;
		labelSnapshot.setText(l.getText());
		majCodes();
	}

	@FXML
	private void menuDivisionClicked(MouseEvent event) {
		Label l = (Label) event.getSource();
		if (l == mPlusDivision && diviSides < 7) {
			diviSides++;
		} else if (l == mMoinsDivision && diviSides > 3) {
			diviSides--;
		}
		labelDivision.setText(String.valueOf(diviSides));
		// changer les outils et maj
		paneCanvas.getChildren().clear();// le parent des outils est le paneCanvas
		creationOutils();
		choixOutils();
		majCouleurs();

	}

	@FXML
	private void menuSnapshotClicked(MouseEvent event) {
		WritableImage snapshot;
		if (event.getSource().equals(mSNAPSHOT)) {
			snapshot = this.snapshot(null, null);
		} else {
			snapshot = vboxSnapshot.snapshot(null, null);
		}
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save the snapshot as...");
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		fileChooser.setInitialFileName(timeStamp + ".png");
		ArrayList<String> ext = new ArrayList<String>();
		ext.add(".png");
		fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Snapshot", ext));
		File file = fileChooser.showSaveDialog(this.getScene().getWindow());
		if (file != null) {
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("IOException");
				alert.setContentText(e.getLocalizedMessage());
				alert.showAndWait();
			}
		}

	}

	@FXML
	private void menuCopyClicked(MouseEvent event) {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		String s = "//******************** Perceptual ColorPicker colors ********************" + "\n";
		s = s + "color[] pcpColors = new color[" + codes.length + "];" + "\n";
		for (int i = 0; i < codes.length; i++) {
			s = s + "pcpColors[" + i + "] =" + codes[i] + ";" + "\n";
		}
		content.putString(s);
		clipboard.setContent(content);
	}

	@FXML
	private void curseurOMouseDragged(MouseEvent event) {
		if (modeEnCours == mOrtho) {
			curseurO.setCenterX(event.getX());// Xpos initiale = 810 L = 140
			if (curseurO.getCenterX() < 740) {
				curseurO.setCenterX(740);
			}
			if (curseurO.getCenterX() > 880) {
				curseurO.setCenterX(880);
			}
			value = 0;
			double k = (curseurO.getCenterX() - 740) / (880 - 740);
			if (planEnCours == ma_b) {
				value = (int) Math.round(minL + (maxL - minL) * k);
			}
			if (planEnCours == ma_L) {
				value = (int) Math.round(minb + (maxb - minb) * k);
			}
			if (planEnCours == mb_L) {
				value = (int) Math.round(mina + (maxa - mina) * k);
			}
			if (planEnCours == mc_H) {
				value = (int) Math.round(minL + (maxL - minL) * k);
			}
			if (planEnCours == mc_L) {
				value = (int) Math.round(minh + (maxh - minh) * k);
			}
			if (planEnCours == mH_L) {
				value = (int) Math.round(minc + (maxc - minc) * k);
			}
			dessinOrtho(value);
		}
	}

	@FXML
	private void curseurCMouseDragged(MouseEvent event) {
		if (modeEnCours == mContraste) {
			curseurC.setCenterX(event.getX());// Xpos initiale = 810 L = 140
			if (curseurC.getCenterX() < 740) {
				curseurC.setCenterX(740);
			}
			if (curseurC.getCenterX() > 880) {
				curseurC.setCenterX(880);
			}
			value = 0;
			double k = (curseurC.getCenterX() - 740) / (880 - 740);
			value = (int) Math.round(360 * k);
			dessinContraste(value);
			gradient.setRotate(-value);
		}
	}

	@FXML
	private void styleButtonClicked(MouseEvent event) {
		Rectangle rect = (Rectangle) event.getSource();
		if (rect == buttonDark) {
			this.getScene().getStylesheets().remove(lightStyleUrl);
			if (!this.getScene().getStylesheets().contains(darkStyleUrl)) {
				this.getScene().getStylesheets().add(darkStyleUrl);
			}
			styleEnCours = buttonDark;
		}
		if (rect == buttonLight) {
			this.getScene().getStylesheets().remove(darkStyleUrl);
			if (!this.getScene().getStylesheets().contains(lightStyleUrl)) {
				this.getScene().getStylesheets().add(lightStyleUrl);
			}
			styleEnCours = buttonLight;
		}
		if (modeEnCours == mOrtho) {
			dessinOrtho(value);
		} else {
			dessinContraste(value);
		}

	}

	public PerceptualColorPickerControl() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PerceptualColorPicker.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	void dessinOrtho(int _value) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		if (styleEnCours == buttonLight) {
			gc.setFill(Color.web("#bbb"));
		} else {
			gc.setFill(Color.web("#444"));
		}
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		final int pas = 1;
		float[] lab = new float[3];
		float[] lch = new float[3];
		if (planEnCours == ma_b) {
			lab[0] = _value;
			for (float a = mina; a < maxa; a += pas) {
				for (float b = minb; b < maxb; b += pas) {
					lab[1] = a;
					lab[2] = b;
					dessinLab(lab);
				}
			}
		}
		if (planEnCours == ma_L) {
			lab[2] = _value;
			for (float a = mina; a < maxa; a += pas) {
				for (float L = minL; L < maxL; L += pas) {
					lab[1] = a;
					lab[0] = L;
					dessinLab(lab);
				}
			}
		}
		if (planEnCours == mb_L) {
			lab[1] = _value;
			for (float b = minb; b < maxb; b += pas) {
				for (float L = minL; L < maxL; L += pas) {
					lab[2] = b;
					lab[0] = L;
					dessinLab(lab);
				}
			}
		}
		if (planEnCours == mc_H) {
			lch[0] = _value;
			for (float c = minc; c < maxc; c += pas) {
				for (float H = minh; H < maxh; H += pas) {
					lch[1] = c;
					lch[2] = H;
					dessinLch(lch);
				}
			}
		}
		if (planEnCours == mc_L) {
			lch[2] = _value;
			for (float c = minc; c < maxc; c += pas) {
				for (float L = minL; L < maxL; L += pas) {
					lch[1] = c;
					lch[0] = L;
					dessinLch(lch);
				}
			}
		}
		if (planEnCours == mH_L) {
			lch[1] = _value;
			for (float H = minh; H < maxh; H += pas) {
				for (float L = minL; L < maxL; L += pas) {
					lch[2] = H;
					lch[0] = L;
					dessinLch(lch);
				}
			}
		}
		wi = canvas.snapshot(null, null);
		majCouleurs();
	}

	void dessinContraste(int _value) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		if (styleEnCours == buttonLight) {
			gc.setFill(Color.web("#bbb"));
		} else {
			gc.setFill(Color.web("#444"));
		}
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		final int pas = 1;
		float[] lab = new float[3];
		float[] lch = new float[3];
		if (planEnCours == ma_b) {
			for (float a = mina; a < maxa; a += pas) {
				for (float b = minb; b < maxb; b += pas) {
					lab[1] = a;
					lab[2] = b;
					lab[0] = (float) ((Math.cos(_value * Math.PI / 180) * a + Math.sin(_value * Math.PI / 180) * b) / 2)
							+ 50;
					dessinLab(lab);
				}
			}
		}
		if (planEnCours == ma_L) {
			for (float a = mina; a < maxa; a += pas) {
				for (float L = minL; L < maxL; L += pas) {
					lab[1] = a;
					lab[0] = L;
					lab[2] = (float) ((Math.cos(_value * Math.PI / 180) * L + Math.sin(_value * Math.PI / 180) * a));
					dessinLab(lab);
				}
			}
		}
		if (planEnCours == mb_L) {
			for (float b = minb; b < maxb; b += pas) {
				for (float L = minL; L < maxL; L += pas) {
					lab[2] = b;
					lab[0] = L;
					lab[1] = (float) ((Math.cos(_value * Math.PI / 180) * L + Math.sin(_value * Math.PI / 180) * b));
					dessinLab(lab);
				}
			}
		}
		if (planEnCours == mc_H) {
			for (float c = minc; c < maxc; c += pas) {
				for (float H = minh; H < maxh; H += pas) {
					lch[1] = c;
					lch[2] = H;
					lch[0] = (float) ((Math.cos(_value * Math.PI / 180) * c + Math.sin(_value * Math.PI / 180) * H) / 2)
							+ 50;
					dessinLch(lch);
				}
			}
		}
		if (planEnCours == mc_L) {
			for (float c = minc; c < maxc; c += pas) {
				for (float L = minL; L < maxL; L += pas) {
					lch[1] = c;
					lch[0] = L;
					lch[2] = (float) ((Math.cos(_value * Math.PI / 180) * L + Math.sin(_value * Math.PI / 180) * c));
					dessinLch(lch);
				}
			}
		}
		if (planEnCours == mH_L) {
			for (float H = minh; H < maxh; H += pas) {
				for (float L = minL; L < maxL; L += pas) {
					lch[2] = H;
					lch[0] = L;
					lch[1] = (float) ((Math.cos(_value * Math.PI / 180) * L + Math.sin(_value * Math.PI / 180) * H));
					dessinLch(lch);
				}
			}
		}
		wi = canvas.snapshot(null, null);
		majCouleurs();
	}

	void dessinLab(float[] _lab) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		float[] rgb = new float[3];
		float[] xyz = new float[3];
		ColorSpaceConvertion.labToXYZ(_lab, xyz);
		ColorSpaceConvertion.xyzToRGB(xyz, rgb);
		if ((rgb[0] <= 255) && (rgb[0] >= 0)) {
			if ((rgb[1] <= 255) && (rgb[1] >= 0)) {
				if ((rgb[2] <= 255) && (rgb[2] >= 0)) {
					int x = 0;
					int y = 0;
					if (planEnCours == ma_b) {
						x = (int) (215 + _lab[1] * 2.15);
						y = (int) (215 + _lab[2] * 2.15);
					}
					if (planEnCours == ma_L) {
						x = (int) (215 + _lab[1] * 2.15);
						y = (int) (0 + _lab[0] * 2.15);
					}
					if (planEnCours == mb_L) {
						x = (int) (215 + _lab[2] * 2.15);
						y = (int) (0 + _lab[0] * 2.15);
					}
					gc.setFill(Color.rgb((int) rgb[0], (int) rgb[1], (int) rgb[2]));
					gc.fillRect(x, 430 - y, 3, 3);
				}
			}
		}
	}

	void dessinLch(float[] _lch) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		float[] lab = new float[3];
		float[] rgb = new float[3];
		float[] xyz = new float[3];
		ColorSpaceConvertion.LCHtoLab(_lch, lab);
		ColorSpaceConvertion.labToXYZ(lab, xyz);
		ColorSpaceConvertion.xyzToRGB(xyz, rgb);
		if ((rgb[0] <= 255) && (rgb[0] >= 0)) {
			if ((rgb[1] <= 255) && (rgb[1] >= 0)) {
				if ((rgb[2] <= 255) && (rgb[2] >= 0)) {
					int x = 0;
					int y = 0;
					if (planEnCours == mc_H) {
						x = (int) (0 + _lch[2] * 430 / 360);
						y = (int) (0 + _lch[1] * 430 / 360);
					}
					if (planEnCours == mc_L) {
						x = (int) (0 + _lch[1] * 2.15);
						y = (int) (0 + _lch[0] * 2.15);
					}
					if (planEnCours == mH_L) {
						x = (int) (0 + _lch[2] * 430 / 360);
						y = (int) (0 + _lch[0] * 430 / 360);
					}
					gc.setFill(Color.rgb((int) rgb[0], (int) rgb[1], (int) rgb[2]));
					gc.fillRect(x, 430 - y, 3, 3);
				}
			}
		}
	}

	void majCouleurs() {

		hboxPalette.getChildren().clear();
		Rectangle r;
		double h = hboxPalette.getPrefHeight();
		double l = (hboxPalette.getPrefWidth() - (diviSides - 1) * 5) / diviSides;

		if (outilEnCours == mSegment) {
			c = wi.getPixelReader().getColor((int) droite.ligne.getStartX(), (int) droite.ligne.getStartY());
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
			for (int i = 0; i < droite.circleCarets.length; i++) {
				c = wi.getPixelReader().getColor((int) droite.circleCarets[i].getCenterX(),
						(int) droite.circleCarets[i].getCenterY());
				r = new Rectangle(l, h);
				r.setArcHeight(10);
				r.setArcWidth(10);
				if (c != null) {
					r.setFill(c);
				}
				hboxPalette.getChildren().add(r);
			}
			c = wi.getPixelReader().getColor((int) droite.ligne.getEndX(), (int) droite.ligne.getEndY());
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
		}

		if (outilEnCours == mQuadratic) {
			c = wi.getPixelReader().getColor((int) quadratic.q.getStartX(), (int) quadratic.q.getStartY());
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
			for (int i = 0; i < quadratic.circleCarets.length; i++) {
				c = wi.getPixelReader().getColor((int) quadratic.circleCarets[i].getCenterX(),
						(int) quadratic.circleCarets[i].getCenterY());
				r = new Rectangle(l, h);
				r.setArcHeight(10);
				r.setArcWidth(10);
				if (c != null) {
					r.setFill(c);
				}
				hboxPalette.getChildren().add(r);
			}

			c = wi.getPixelReader().getColor((int) quadratic.q.getEndX(), (int) quadratic.q.getEndY());
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
		}

		if (outilEnCours == mRectangle) {
			c = wi.getPixelReader().getColor((int) orectangle.ligne.getStartX(), (int) orectangle.ligne.getStartY());
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
			for (int i = 0; i < orectangle.circleCarets.length; i++) {
				c = wi.getPixelReader().getColor((int) orectangle.circleCarets[i].getCenterX(),
						(int) orectangle.circleCarets[i].getCenterY());
				r = new Rectangle(l, h);
				r.setArcHeight(10);
				r.setArcWidth(10);
				if (c != null) {
					r.setFill(c);
				}
				hboxPalette.getChildren().add(r);
			}
			c = wi.getPixelReader().getColor((int) orectangle.ligne.getEndX(), (int) orectangle.ligne.getEndY());
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
		}

		if (outilEnCours == mPolygon) {
			c = wi.getPixelReader().getColor((int) Math.round(polygone.peri.getCenterX()),
					(int) Math.round(polygone.peri.getCenterY()));
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
			for (int i = 1; i < polygone.nbSides; i++) {
				int X = (int) Math.round(polygone.circleSommets[i].getCenterX());
				int Y = (int) Math.round(polygone.circleSommets[i].getCenterY());
				c = wi.getPixelReader().getColor(X, Y);
				r = new Rectangle(l, h);
				r.setArcHeight(10);
				r.setArcWidth(10);
				if (c != null) {
					r.setFill(c);
				}
				hboxPalette.getChildren().add(r);
			}
		}

		if (outilEnCours == mPalette) {
			c = wi.getPixelReader().getColor((int) Math.round(palette.peri1.getCenterX()),
					(int) Math.round(palette.peri1.getCenterY()));
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
			c = wi.getPixelReader().getColor((int) Math.round(palette.peri2.getCenterX()),
					(int) Math.round(palette.peri2.getCenterY()));
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
			c = wi.getPixelReader().getColor((int) Math.round(palette.peri3.getCenterX()),
					(int) Math.round(palette.peri3.getCenterY()));
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
			c = wi.getPixelReader().getColor((int) Math.round(palette.c0.getCenterX()),
					(int) Math.round(palette.c0.getCenterY()));
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
			c = wi.getPixelReader().getColor((int) Math.round(palette.c1.getCenterX()),
					(int) Math.round(palette.c1.getCenterY()));
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
			c = wi.getPixelReader().getColor((int) Math.round(palette.c2.getCenterX()),
					(int) Math.round(palette.c2.getCenterY()));
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);
			c = wi.getPixelReader().getColor((int) Math.round(palette.c3.getCenterX()),
					(int) Math.round(palette.c3.getCenterY()));
			r = new Rectangle(l, h);
			r.setArcHeight(10);
			r.setArcWidth(10);
			if (c != null) {
				r.setFill(c);
			}
			hboxPalette.getChildren().add(r);

		}
		majCodes();
	}

	void majCodes() {
		hboxCodes.getChildren().clear();
		double h = hboxCodes.getPrefHeight();
		double l = (hboxPalette.getPrefWidth() - (diviSides - 1) * 5) / diviSides;
		codes = new String[hboxPalette.getChildren().size()];

		for (int i = 0; i < hboxPalette.getChildren().size(); i++) {
			Rectangle rect = (Rectangle) hboxPalette.getChildren().get(i);
			Color c = (Color) rect.getFill();
			codes[i] = "#" + c.toString().substring(2, 8);
			Label l0 = new Label();
			Label l1 = new Label();
			Label l2 = new Label();
			l0.setStyle("-fx-font-size: 12;");
			l1.setStyle("-fx-font-size: 12;");
			l2.setStyle("-fx-font-size: 12;");
			l0.setPrefWidth(l);
			l1.setPrefWidth(l);
			l2.setPrefWidth(l);
			l0.setMinWidth(l);
			l1.setMinWidth(l);
			l2.setMinWidth(l);
			l0.setMaxWidth(l);
			l1.setMaxWidth(l);
			l2.setMaxWidth(l);
			l0.setAlignment(Pos.CENTER);
			l1.setAlignment(Pos.CENTER);
			l2.setAlignment(Pos.CENTER);
			if (!(c.equals(Color.rgb(68, 68, 68)) || c.equals(Color.rgb(187, 187, 187)))) {

				if (codeEnCours == mRgb) {
					l0.setText(String.valueOf((int) Math.round(c.getRed() * 255)));
					l1.setText(String.valueOf((int) Math.round(c.getGreen() * 255)));
					l2.setText(String.valueOf((int) Math.round(c.getBlue() * 255)));
				}
				if (codeEnCours == mLab) {
					float[] lab = colorToLab(c);
					l0.setText(String.valueOf(Math.round(lab[0])));
					l1.setText(String.valueOf(Math.round(lab[1])));
					l2.setText(String.valueOf(Math.round(lab[2])));
				}
				if (codeEnCours == mLch) {
					float[] lch = colorToLch(c);
					l0.setText(String.valueOf(Math.round(lch[0])));
					l1.setText(String.valueOf(Math.round(lch[1])));
					l2.setText(String.valueOf(Math.round(lch[2])));
				}
				if (codeEnCours == mWeb) {
					float[] lch = colorToLch(c);
					l0.setText(c.toString().substring(2, 8));
					l1.setText("");
					l2.setText("");
				}
			} else {

			}
			VBox vbox = new VBox();
			vbox.setPrefSize(h, l);
			vbox.setAlignment(Pos.CENTER);
			vbox.getChildren().addAll(l0, l1, l2);
			hboxCodes.getChildren().add(vbox);
		}

	}

	void creationOutils() {

		InvalidationListener il = (Observable o) -> {
			majCouleurs();
		};
		droite = new Droite(175, 215, 315, 215, diviSides);
		droite.ligne.setId("segment");
		droite.thumb1.centerXProperty().addListener(il);
		droite.thumb1.centerYProperty().addListener(il);
		droite.thumb2.centerXProperty().addListener(il);
		droite.thumb2.centerYProperty().addListener(il);

		quadratic = new Quadratique(175, 215, 215, 175, 315, 215, diviSides);
		quadratic.thumb1.centerXProperty().addListener(il);
		quadratic.thumb1.centerYProperty().addListener(il);
		quadratic.thumb2.centerXProperty().addListener(il);
		quadratic.thumb2.centerYProperty().addListener(il);
		quadratic.thumb3.centerXProperty().addListener(il);
		quadratic.thumb3.centerYProperty().addListener(il);

		orectangle = new ORectangle(215, 215, 315, 275);
		orectangle.thumb1.centerXProperty().addListener(il);
		orectangle.thumb1.centerYProperty().addListener(il);
		orectangle.thumb2.centerXProperty().addListener(il);
		orectangle.thumb2.centerYProperty().addListener(il);
		// orectangle.thumb1.layoutXProperty().addListener(il);

		polygone = new Polygone(215, 215, 275, 215, diviSides);
		polygone.center.centerXProperty().addListener(il);
		polygone.center.centerYProperty().addListener(il);
		polygone.peri.centerXProperty().addListener(il);
		polygone.peri.centerYProperty().addListener(il);

		// palette = new Palette(260, 226, 178, 204, 310, 157, 290, 318);
		palette = new Palette(215, 215, 143, 193, 275, 146, 224, 306);
		palette.center.centerXProperty().addListener(il);
		palette.center.centerYProperty().addListener(il);
		palette.peri1.centerXProperty().addListener(il);
		palette.peri1.centerYProperty().addListener(il);
		palette.peri2.centerXProperty().addListener(il);
		palette.peri2.centerYProperty().addListener(il);
		palette.peri3.centerXProperty().addListener(il);
		palette.peri3.centerYProperty().addListener(il);

		curseurO.centerXProperty().addListener(il);
		curseurC.centerXProperty().addListener(il);
	}

	void choixOutils() {
		if (outilEnCours == mSegment) {
			diviSides = Integer.valueOf(labelDivision.getText());
			paneCanvas.getChildren().add(droite);
			mCenter.setId(null);
		}
		if (outilEnCours == mQuadratic) {
			diviSides = Integer.valueOf(labelDivision.getText());
			paneCanvas.getChildren().add(quadratic);
			mCenter.setId(null);
		}
		if (outilEnCours == mRectangle) {
			diviSides = 4;
			paneCanvas.getChildren().add(orectangle);
			mCenter.setId(null);
		}
		if (outilEnCours == mPolygon) {
			diviSides = Integer.valueOf(labelDivision.getText());
			paneCanvas.getChildren().add(polygone);
			mCenter.setId("centerable");
		}
		if (outilEnCours == mPalette) {
			diviSides = 7;
			paneCanvas.getChildren().add(palette);
			mCenter.setId("centerable");
		}
	}

}
