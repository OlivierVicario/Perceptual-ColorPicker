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

import javafx.scene.paint.Color;

/**
 *    //constantes ColorSpaceConversion final float minL = 0.0f, maxL = 100.0f;
 * final float mina = -86.18426f, maxa = 98.25467f; final float minb =
 * -107.855545f, maxb = 94.48676f;
 *
 *
 *
 */
/**
 * Basic color space conversion utilities, assuming two degree observer and
 * illuminant D65.
 *
 * @author Jarek Sacha
 * @version $Revision: 1.7 $
 */
public final class ColorSpaceConvertion {

    private static final double X_D65 = 0.950467;
    private static final double Y_D65 = 1.0000;
    private static final double Z_D65 = 1.088969;
    private static final double CIE_EPSILON = 0.008856;
    private static final double CIE_KAPPA = 903.3;
    private static final double CIE_KAPPA_EPSILON = CIE_EPSILON * CIE_KAPPA;

    private ColorSpaceConvertion() {
    }

    /**
     * Conversion from CIE XYZ to sRGB as defined in the IEC 619602-1 standard
     * (http://www.colour.org/tc8-05/Docs/colorspace/61966-2-1.pdf)
     * <pre>
     * r_linear = +3.2406 * X - 1.5372 * Y - 0.4986 * Z;
     * g_linear = -0.9689 * X + 1.8758 * Y + 0.0415 * Z;
     * b_linear = +0.0557 * X - 0.2040 * Y + 1.0570 * Z;
     * r = r_linear > 0.0031308
     *       ? 1.055 * Math.pow(r_linear, (1 / 2.4)) - 0.055
     *       : 12.92 * r_linear;
     * g = g_linear > 0.0031308
     *       ? 1.055 * Math.pow(g_linear, (1 / 2.4)) - 0.055
     *       : 12.92 * g_linear;
     * b = b_linear > 0.0031308
     *       ? 1.055 * Math.pow(b_linear, (1 / 2.4)) - 0.055
     *       : 12.92 * b_linear;
     * R = r * 255
     * G = r * 255
     * B = r * 255
     * <pre>
     *
     * @param xyz input CIE XYZ values.
     * @param rgb output sRGB values in [0, 255] range.
     */
    public static void xyzToRGB(final float[] xyz, final float[] rgb) {
        final double x = xyz[0];
        final double y = xyz[1];
        final double z = xyz[2];

        //  http://www.colour.org/tc8-05/Docs/colorspace/61966-2-1.pdf
        final double r_linear = +3.2406 * x - 1.5372 * y - 0.4986 * z;
        final double g_linear = -0.9689 * x + 1.8758 * y + 0.0415 * z;
        final double b_linear = +0.0557 * x - 0.2040 * y + 1.0570 * z;

        final double r = r_linear > 0.0031308
                ? 1.055 * Math.pow(r_linear, (1 / 2.4)) - 0.055
                : 12.92 * r_linear;

        final double g = g_linear > 0.0031308
                ? 1.055 * Math.pow(g_linear, (1 / 2.4)) - 0.055
                : 12.92 * g_linear;

        final double b = b_linear > 0.0031308
                ? 1.055 * Math.pow(b_linear, (1 / 2.4)) - 0.055
                : 12.92 * b_linear;

        rgb[0] = (float) (r * 255);
        rgb[1] = (float) (g * 255);
        rgb[2] = (float) (b * 255);
    }

    /**
     * Conversion from CIE L*a*b* to CIE XYZ assuming Observer. = 2, Illuminant
     * = D65. Conversion based on formulas provided at
     * http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
     *
     * @param lab input CIE L*a*b* values.
     * @param xyz output CIE XYZ values.
     */
    public static void labToXYZ(final float[] lab, final float[] xyz) {

        final double l = lab[0];
        final double a = lab[1];
        final double b = lab[2];

        final double yr = l > CIE_KAPPA_EPSILON
                ? Math.pow((l + 16) / 116, 3)
                : l / CIE_KAPPA;

        final double fy = yr > CIE_EPSILON
                ? (l + 16) / 116.0
                : (CIE_KAPPA * yr + 16) / 116.0;

        final double fx = a / 500 + fy;
        final double fx3 = fx * fx * fx;

        final double xr = fx3 > CIE_EPSILON
                ? fx3
                : (116 * fx - 16) / CIE_KAPPA;

        final double fz = fy - b / 200.0;
        final double fz3 = fz * fz * fz;
        final double zr = fz3 > CIE_EPSILON
                ? fz3
                : (116 * fz - 16) / CIE_KAPPA;

        xyz[0] = (float) (xr * X_D65);
        xyz[1] = (float) (yr * Y_D65);
        xyz[2] = (float) (zr * Z_D65);
    }

    /**
     * Conversion from sRGB to CIE XYZ as defined in the IEC 619602-1 standard
     * (http://www.colour.org/tc8-05/Docs/colorspace/61966-2-1.pdf)
     * <pre>
     * r = R / 255;
     * g = G / 255;
     * b = B / 255;
     * r_linear = r > 0.04045
     *          ? Math.pow((r + 0.055) / 1.055, 2.4)
     *          : r / 12.92;
     * g_linear = g > 0.04045
     *          ? Math.pow((g + 0.055) / 1.055, 2.4)
     *          : g / 12.92;
     * b_linear = b > 0.04045
     *          ? Math.pow((b + 0.055) / 1.055, 2.4)
     *          : b / 12.92;
     * X = 0.4124 * r_linear + 0.3576 * g_linear + 0.1805 * b_linear;
     * Y = 0.2126 * r_linear + 0.7152 * g_linear + 0.0722 * b_linear;
     * Z = 0.0193 * r_linear + 0.1192 * g_linear + 0.9505 * b_linear;
     * </pre>
     *
     * @param rgb source sRGB values. Size of array <code>rgb</code> must be at
     * least 3. If size of array <code>rgb</code> larger than three then only
     * first 3 values are used.
     * @param xyz destinaltion CIE XYZ values. Size of array <code>xyz</code>
     * must be at least 3. If size of array <code>xyz</code> larger than three
     * then only first 3 values are used.
     */
    public static void rgbToXYZ(final float[] rgb, final float[] xyz) {
        final double r = rgb[0] / 255;
        final double g = rgb[1] / 255;
        final double b = rgb[2] / 255;

        final double r_linear = r > 0.04045
                ? Math.pow((r + 0.055) / 1.055, 2.4)
                : r / 12.92;

        final double g_linear = g > 0.04045
                ? Math.pow((g + 0.055) / 1.055, 2.4)
                : g / 12.92;

        final double b_linear = b > 0.04045
                ? Math.pow((b + 0.055) / 1.055, 2.4)
                : b / 12.92;

        final double x = 0.4124 * r_linear + 0.3576 * g_linear + 0.1805 * b_linear;
        final double y = 0.2126 * r_linear + 0.7152 * g_linear + 0.0722 * b_linear;
        final double z = 0.0193 * r_linear + 0.1192 * g_linear + 0.9505 * b_linear;

        xyz[0] = (float) x;
        xyz[1] = (float) y;
        xyz[2] = (float) z;
    }

    /**
     * /**
     * Conversion from CIE XYZ to CIE L*a*b* assuming Observer. = 2, Illuminant
     * = D65. Conversion based on formulas provided at
     * http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
     *
     * @param xyz source CIE XYZ values. Size of array <code>xyz</code> must be
     * at least 3. If size of array <code>xyz</code> larger than three then only
     * first 3 values are used.
     * @param lab destinaltion CIE L*a*b* values. Size of array <code>lab</code>
     * must be at least 3. If size of array <code>lab</code> larger than three
     * then only first 3 values are used.
     */
    public static void xyzToLab(final float[] xyz, final float[] lab) {
        final double xr = xyz[0] / X_D65;
        final double yr = xyz[1] / Y_D65;
        final double zr = xyz[2] / Z_D65;

        final double fx = xr > CIE_EPSILON
                ? Math.pow(xr, 1.0 / 3.0)
                : (CIE_KAPPA * xr + 16) / 116.0;

        final double fy = yr > CIE_EPSILON
                ? Math.pow(yr, 1.0 / 3.0)
                : (CIE_KAPPA * yr + 16) / 116.0;

        final double fz = zr > CIE_EPSILON
                ? Math.pow(zr, 1.0 / 3.0)
                : (CIE_KAPPA * zr + 16) / 116.0;

        lab[0] = (float) (116 * fy - 16);
        lab[1] = (float) (500 * (fx - fy));
        lab[2] = (float) (200 * (fy - fz));
    }

    //CIE-L*ab —> CIE-L*CH°
    public static void LabtoLCH(final float[] lab, final float[] lch) {
        double var_H = Math.atan2(lab[2], lab[1]); //Quadrant by signs
        if (var_H > 0) {
            var_H = (var_H / Math.PI) * 180;
        } else {
            var_H = 360 - (Math.abs(var_H) / Math.PI) * 180;
        }
        lch[0] = lab[0];
        lch[1] = (float) Math.sqrt(Math.pow(lab[1], 2) + Math.pow(lab[2], 2));
        lch[2] = (float) var_H;
    }

    //CIE-L*CH° —>CIE-L*ab//CIE-H° from 0 to 360°
    public static void LCHtoLab(final float[] lch, final float[] lab) {
        lab[0] = lch[0];
        lab[1] = (float) Math.cos((lch[2] * Math.PI / 180.0)) * lch[1];
        lab[2] = (float) Math.sin((lch[2] * Math.PI / 180.0)) * lch[1];

    }

    public static float[] colorToLch(Color c) {
        float[] rgb = new float[3];
        float[] xyz = new float[3];
        float[] lab = new float[3];
        float[] lch = new float[3];
        rgb[0] = (float) c.getRed() * 255;
        rgb[1] = (float) c.getGreen() * 255;
        rgb[2] = (float) c.getBlue() * 255;
        rgbToXYZ(rgb, xyz);
        xyzToLab(xyz, lab);
        LabtoLCH(lab, lch);
        return lch;
    }

    public static float[] colorToLab(Color c) {
        float[] rgb = new float[3];
        float[] xyz = new float[3];
        float[] lab = new float[3];
        rgb[0] = (float) c.getRed() * 255;
        rgb[1] = (float) c.getGreen() * 255;
        rgb[2] = (float) c.getBlue() * 255;
        rgbToXYZ(rgb, xyz);
        xyzToLab(xyz, lab);
        return lab;
    }

    public static Color lchToColor(float[] lch) {
        float[] rgb = new float[3];
        float[] xyz = new float[3];
        float[] lab = new float[3];
        LCHtoLab(lch, lab);
        labToXYZ(lab, xyz);
        xyzToRGB(xyz, rgb);
        Color c = Color.TRANSPARENT;        
        if ((rgb[0] <= 255) && (rgb[0] >= 0)) {
            if ((rgb[1] <= 255) && (rgb[1] >= 0)) {
                if ((rgb[2] <= 255) && (rgb[2] >= 0)) {
                    c = Color.rgb((int) rgb[0], (int) rgb[1], (int) rgb[2]);
                }
            }
        }
        return c;
    }

    public static Color labToColor(float[] lab) {
        float[] rgb = new float[3];
        float[] xyz = new float[3];
        labToXYZ(lab, xyz);
        xyzToRGB(xyz, rgb);
        Color c = Color.TRANSPARENT;
        if ((rgb[0] <= 255) && (rgb[0] >= 0)) {
            if ((rgb[1] <= 255) && (rgb[1] >= 0)) {
                if ((rgb[2] <= 255) && (rgb[2] >= 0)) {
                    c = Color.rgb((int) rgb[0], (int) rgb[1], (int) rgb[2]);
                }
            }
        }
        return c;
    }
}
/*min L = 0.0 max L = 255.0
 min a = -78.0 max a = 94.0
 min b = -111.0 max b = 93.0
 public void rgb2lab(int R, int G, int B, int[] lab) {
 //http://www.brucelindbloom.com

 float r, g, b, X, Y, Z, fx, fy, fz, xr, yr, zr;
 float Ls, as, bs;
 float eps = 216.f / 24389.f;
 float k = 24389.f / 27.f;

 float Xr = 0.964221f;  // reference white D50
 float Yr = 1.0f;
 float Zr = 0.825211f;

 // RGB to XYZ
 r = R / 255.f; //R 0..1
 g = G / 255.f; //G 0..1
 b = B / 255.f; //B 0..1

 // assuming sRGB (D65)
 if (r <= 0.04045) {
 r = r / 12;
 } else {
 r = (float) Math.pow((r + 0.055) / 1.055, 2.4);
 }

 if (g <= 0.04045) {
 g = g / 12;
 } else {
 g = (float) Math.pow((g + 0.055) / 1.055, 2.4);
 }

 if (b <= 0.04045) {
 b = b / 12;
 } else {
 b = (float) Math.pow((b + 0.055) / 1.055, 2.4);
 }

 X = 0.436052025f * r + 0.385081593f * g + 0.143087414f * b;
 Y = 0.222491598f * r + 0.71688606f * g + 0.060621486f * b;
 Z = 0.013929122f * r + 0.097097002f * g + 0.71418547f * b;

 // XYZ to Lab
 xr = X / Xr;
 yr = Y / Yr;
 zr = Z / Zr;

 if (xr > eps) {
 fx = (float) Math.pow(xr, 1 / 3.);
 } else {
 fx = (float) ((k * xr + 16.) / 116.);
 }

 if (yr > eps) {
 fy = (float) Math.pow(yr, 1 / 3.);
 } else {
 fy = (float) ((k * yr + 16.) / 116.);
 }

 if (zr > eps) {
 fz = (float) Math.pow(zr, 1 / 3.);
 } else {
 fz = (float) ((k * zr + 16.) / 116);
 }

 Ls = (116 * fy) - 16;
 as = 500 * (fx - fy);
 bs = 200 * (fy - fz);

 lab[0] = (int) (2.55 * Ls + .5);
 lab[1] = (int) (as + .5);
 lab[2] = (int) (bs + .5);
 }

 /*min L = 0.0 max L = 255.0
 min u = -84.0 max u = 175.0
 min v = -125.0 max v = 87.0
 public void rgb2luv(int R, int G, int B, int[] luv) {
 //http://www.brucelindbloom.com

 float rf, gf, bf;
 float r, g, b, X_, Y_, Z_, X, Y, Z, fx, fy, fz, xr, yr, zr;
 float L;
 float eps = 216.f / 24389.f;
 float k = 24389.f / 27.f;

 float Xr = 0.964221f;  // reference white D50
 float Yr = 1.0f;
 float Zr = 0.825211f;

 // RGB to XYZ
 r = R / 255.f; //R 0..1
 g = G / 255.f; //G 0..1
 b = B / 255.f; //B 0..1

 // assuming sRGB (D65)
 if (r <= 0.04045) {
 r = r / 12;
 } else {
 r = (float) Math.pow((r + 0.055) / 1.055, 2.4);
 }

 if (g <= 0.04045) {
 g = g / 12;
 } else {
 g = (float) Math.pow((g + 0.055) / 1.055, 2.4);
 }

 if (b <= 0.04045) {
 b = b / 12;
 } else {
 b = (float) Math.pow((b + 0.055) / 1.055, 2.4);
 }

 X = 0.436052025f * r + 0.385081593f * g + 0.143087414f * b;
 Y = 0.222491598f * r + 0.71688606f * g + 0.060621486f * b;
 Z = 0.013929122f * r + 0.097097002f * g + 0.71418547f * b;

 // XYZ to Luv
 float u, v, u_, v_, ur_, vr_;

 u_ = 4 * X / (X + 15 * Y + 3 * Z);
 v_ = 9 * Y / (X + 15 * Y + 3 * Z);

 ur_ = 4 * Xr / (Xr + 15 * Yr + 3 * Zr);
 vr_ = 9 * Yr / (Xr + 15 * Yr + 3 * Zr);

 yr = Y / Yr;

 if (yr > eps) {
 L = (float) (116 * Math.pow(yr, 1 / 3.) - 16);
 } else {
 L = k * yr;
 }

 u = 13 * L * (u_ - ur_);
 v = 13 * L * (v_ - vr_);

 luv[0] = (int) (2.55 * L + .5);
 luv[1] = (int) (u + .5);
 luv[2] = (int) (v + .5);
 }*/
