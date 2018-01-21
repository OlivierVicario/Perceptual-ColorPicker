/**
 * Choose from 3 to 7 colors from CIE Lab or Lch with different graphic tools.
 * Copy from Perceptual Colorpicker and paste where you want in your sketch. 
 * You will get the following pattern :
 * //******************** Perceptual ColorPicker colors ********************
 * color[] pcpColors = new color[x];
 * pcpColors[0] =#hhhhhh;
 * pcpColors[1] =#hhhhhh;
 * pcpColors[2] =#hhhhhh;
 *          ...
 * pcpColors[x] =#hhhhhh;
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

import java.util.Calendar;
int largeur = 800;
int hauteur = 800;
int cote = 200;

void setup() {

//******************** Perceptual ColorPicker colors ********************
color[] pcpColors = new color[5];
pcpColors[0] =#dea9e4;
pcpColors[1] =#82c1f9;
pcpColors[2] =#73c9c5;
pcpColors[3] =#bfbd8e;
pcpColors[4] =#f4a7a2;

  size(800, 800);
  noStroke();
  background(255);
  for (int i=0; i<800/cote; i++) {
    for (int j=0; j<800/cote; j++) {
      if((int)random(10)>6){
      fill(pcpColors[int(random(pcpColors.length))]);
      rect(i*cote, j*cote, cote, cote);
      }
    }
  }
  for (int i=0; i<800/cote; i++) {
    for (int j=0; j<800/cote; j++) {
      fill(pcpColors[int(random(pcpColors.length))]);
      int alea=(int)random(11);     
      switch(alea) {
        case(0):
        rect(i*cote, j*cote, cote, cote, cote, 0, 0, 0);       
        break;
        case(1):
        rect(i*cote, j*cote, cote, cote, 0, cote, 0, 0);
        break;
        case(2):
        rect(i*cote, j*cote, cote, cote, 0, 0, cote, 0); 
        break;
        case(3):
        rect(i*cote, j*cote, cote, cote, 0, 0, 0, cote);
        break;
        case(4):
        rect(i*cote, j*cote, cote, cote, cote, 0, cote, 0);        
        break;
        case(5):
        rect(i*cote, j*cote, cote, cote, 0, cote, 0, cote);
        break;
        case(6):
        rect(i*cote, j*cote, cote, cote, cote, cote, cote, 0); 
        break;
        case(7):
        rect(i*cote, j*cote, cote, cote, cote, cote, 0, cote);
        break;
        case(8):
        rect(i*cote, j*cote, cote, cote, cote, 0, cote, cote);
        break;
        case(9):
        rect(i*cote, j*cote, cote, cote, 0, cote, cote, cote);
        break;
        case(10):
        rect(i*cote, j*cote, cote, cote, cote, cote, cote, cote);
        break;
      }
    }
  }
}
void draw() {
}
void keyReleased() {
  if (key == 's' || key == 'S') saveFrame(timestamp()+".png");
}

// timestamp
String timestamp() {
  Calendar now = Calendar.getInstance();
  return String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now);
}