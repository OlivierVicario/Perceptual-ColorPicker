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

PShape s;  // The PShape object
PShape s1;
PShape ss0;
PShape ss1;
float cote = 100;


color[] pcpColors = new color[6];
void setup() {
background(255);
//******************** Perceptual ColorPicker colors ********************
color[] pcpColors = new color[5];
pcpColors[0] =#93876a;
pcpColors[1] =#4ca48c;
pcpColors[2] =#5adc87;
pcpColors[3] =#b3e259;
pcpColors[4] =#c5a94b;





  smooth();  
  size(600, 606);
  int a;
  for (float i=cote*-1; i<600/cote; i++) {
    a=0;
    for (float j=0; j<600/cote; j+=sqrt(3)/2) {
      s = createShape();
      s.beginShape();
      s.fill(pcpColors[(int)random(pcpColors.length)]);
      s.noStroke();
      s.vertex(cote/2, 0);
      s.vertex(cote, cote*sqrt(3)/2);
      s.vertex(0, cote*sqrt(3)/2);
      s.endShape();
      
      ss0 = createShape();
      ss0.beginShape();
      ss0.fill(pcpColors[(int)random(pcpColors.length)]);
      ss0.noStroke();
      ss0.vertex(cote/2, 0);
      ss0.vertex(cote/2, (cote*sqrt(3)/2)*2/3);
      ss0.vertex(0, cote*sqrt(3)/2);
      ss0.endShape();

      s1 = createShape();
      s1.beginShape();
      s1.fill(pcpColors[(int)random(pcpColors.length)]);
      s1.noStroke();
      s1.vertex(cote/2, 0);
      s1.vertex(cote, cote*sqrt(3)/2);
      s1.vertex(cote*1.5, 0);
      s1.endShape();
      
      ss1 = createShape();
      ss1.beginShape();
      ss1.fill(pcpColors[(int)random(pcpColors.length)]);
      ss1.noStroke();
      ss1.vertex(cote, 0);
      ss1.vertex(cote/2, (cote*sqrt(3)/2)/3);
      ss1.vertex(cote/2, cote*sqrt(3)/2);
      ss1.endShape();
      
      if (a%2==0) {
        shape(s, i*cote, j*cote);
        shape(ss0, i*cote, j*cote);
        //shape(s1, i*cote, j*cote);
      } else {
        shape(s, cote/2+i*cote, j*cote);
        //shape(s1, cote/2+i*cote, j*cote);
        shape(ss1, i*cote, j*cote);

      }
      a++;
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