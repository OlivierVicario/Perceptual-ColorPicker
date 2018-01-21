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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;

public class ColorFrame extends JFrame {

	public JPanel contentPane;
	public PerceptualColorPickerControl picker;
	private String darkStyleUrl = getClass().getResource("darkstyle.css").toExternalForm();
	// public Editor editor;
	/**
	 * Create the frame.
	 */
	// public ColorFrame(Editor _editor) {
	public ColorFrame() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 100, 768, 666);
		initAndShowGUI();
		// editor = _editor;
	}

	private void initAndShowGUI() {
		// This method is invoked on Swing thread
		final JFXPanel fxPanel = new JFXPanel();
		this.add(fxPanel);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					initFX(fxPanel);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private void initFX(JFXPanel fxPanel) throws Exception {
		// This method is invoked on JavaFX thread
		Scene scene = createScene();
		fxPanel.setScene(scene);
	}

	private Scene createScene() throws Exception {
		// Parent root =
		// FXMLLoader.load(getClass().getResource("/data/FXMLDocument.fxml"));
		Group root = new Group();
		// picker = new PerceptualColorPickerControl(editor);
		picker = new PerceptualColorPickerControl();
		root.getChildren().add(picker);
		Scene s = new Scene(root, 768, 666);
		s.getStylesheets().add(darkStyleUrl);
		return s;
	}

	/**
	 * Launch the application.
	 */

/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ColorFrame frame = new ColorFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

}
