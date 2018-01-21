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
             
void setup() {
//******************** Perceptual ColorPicker colors ********************
color[] pcpColors = new color[5];
pcpColors[0] =#da935b;
pcpColors[1] =#898572;
pcpColors[2] =#66af73;
pcpColors[3] =#b0dc57;
pcpColors[4] =#f5ce44;




  
  size(430, 605);
  background(255);
  noStroke();
  int pas = 10;
  int size = 430/pas;
  for (int i=size/2; i<430-size/2; i+=size) {
    for (int j=size/2; j<430-size/2; j+=size) {
      if((int)random(10)>1){  
      fill(pcpColors[(int)random(pcpColors.length)]);
      ellipse(i, j, 430/pas, 430/pas);
      }
    }
  }
  int n = pcpColors.length;
  float width = 430/(n+(n-1)*0.1);
  for(int i=0;i<pcpColors.length;i++){
    fill(pcpColors[i]);
    rect(width*i*1.1,460,width,100,5);
    text(hex(pcpColors[i]),width*i*1.1,580);
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