package com.code.sjaiaa.util.rotation;



/**
 * @author sjaiaa
 * @date 2023/1/2 21:52
 * @discription 角度计算工具
 */
public class AngleHelper {

    public enum Type {
        SIN, COS, TAN
    }

    public static double Trigonometric_Radian(Type type, float data) {
        if (type == Type.SIN) {
            return Math.asin(data);
        } else if (type == Type.COS) {
            return Math.acos(data);
        } else if (type == Type.TAN) {
            return Math.atan(data);
        }
        return 0;
    }

    public static double DoubleSin(float angle) {
        return 2 * Math.sin(AngleToRadian(angle)) * Math.cos(AngleToRadian(angle));
    }

    public static double DoubleCos(float angle) {
        return 2 * Math.cos(AngleToRadian(angle)) * Math.cos(AngleToRadian(angle)) - 1;
    }

    public static double TCAS(float angle1, float angle2) {
        double v1 = AngleToRadian(angle1);
        double v2 = AngleToRadian(angle2);
        return Math.sin(v1) * Math.cos(v2) + Math.cos(v1) * Math.sin(v2);
    }




    public static double TCAC(float angle1, float angle2) {
        double v1 = AngleToRadian(angle1);
        double v2 = AngleToRadian(angle2);
        return Math.cos(v1) * Math.cos(v2) - Math.sin(v1) * Math.sin(v2);
    }



    public static double TCAT(float angle1, float angle2) {
        return TCAS(angle1, angle2) / TCAC(angle1, angle2);
    }




    public static double Trigonometric_Angle(Type type, float data) {
        return Trigonometric_Radian(type, data) * 180 / Math.PI;
    }


    public static double RadianToAngle(float data) {
        return data * 180 / Math.PI;
    }


    public static double AngleToRadian(float data) {
        return data * Math.PI / 180f;
    }


    public static double Angle_Fix(double angle) {
        double v = angle;
        while (v < 0) {
            v += 360;
        }
        return v % 360;
    }

    public static double Angle_Fix2(double angle) {
        double v = angle;
        while (v < -180) {
            v += 360;
        }
        return v % 180;
    }

    public static float toFix(float data){
        return Float.parseFloat(String.format("%.2f", data * 100));
    }

}
