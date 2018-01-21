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
PShape t;
PShape u;
PShape v;
PShape w;
float cote = 100;

//******************** Perceptual ColorPicker colors ********************
color[] pcpColors = new color[5];

void setup() {
pcpColors[0] =#eaa3a8;
pcpColors[1] =#96bdbc;
pcpColors[2] =#7fc67c;
pcpColors[3] =#c1b935;
pcpColors[4] =#fca059;
  smooth();  
  size(600, 600);
  background(255);
  boucle();
  boucle();
  boucle();
}
void boucle() {
  for (float i=0; i<600/cote; i++) {
    for (float j=0; j<600/cote; j++) {
      s = createShape();
      s.beginShape();
      s.fill(pcpColors[(int)random(pcpColors.length)]);
      s.noStroke();
      s.vertex(0, 0);
      s.vertex(cote, 0);
      s.vertex(cote, cote);
      s.vertex(0, cote);
      s.endShape();
      t = createShape();
      t.beginShape();
      t.fill(pcpColors[(int)random(pcpColors.length)]);
      t.noStroke();
      t.vertex(0, 0);
      t.vertex(cote/2, cote/2);
      t.vertex(0, cote);
      t.endShape();      
      u = createShape();
      u.beginShape();
      u.fill(pcpColors[(int)random(pcpColors.length)]);
      u.noStroke();
      u.vertex(0, 0);
      u.vertex(cote/2, cote/2);
      u.vertex(cote, 0);
      u.endShape();      
      v= createShape();
      v.beginShape();
      v.fill(pcpColors[(int)random(pcpColors.length)]);
      v.noStroke();
      v.vertex(cote, 0);
      v.vertex(cote/2, cote/2);
      v.vertex(cote, cote);
      v.endShape();      
      w = createShape();
      w.beginShape();
      w.fill(pcpColors[(int)random(pcpColors.length)]);
      w.noStroke();
      w.vertex(0, cote);
      w.vertex(cote/2, cote/2);
      w.vertex(cote, cote);
      w.endShape();      

      //shape(s, i*cote, j*cote);
      int alea=(int) random(4);
      switch(alea) {
      case 0:
        shape(t, i*cote, j*cote);
        break;
      
      case 1:
        shape(u, i*cote, j*cote);
        break;
      
            case 2:
        shape(v, i*cote, j*cote);
        break;
      
            case 3:
        shape(w, i*cote, j*cote);
        break;
      }
      
      
    }
  }
}
void draw() {
}

void keyReleased() {
  if (key == 's' || key == 'S') saveFrame(title()+".png");
}

String title() {
  String s=timestamp();
  return s;
}
// timestamp
String timestamp() {
  Calendar now = Calendar.getInstance();
  return String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now);
}