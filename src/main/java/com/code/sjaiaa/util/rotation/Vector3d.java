package com.code.sjaiaa.util.rotation;

/**
 * @author sjaiaa
 * @date 2023/5/20 21:34
 * @discription
 */
public class Vector3d {
    private double x;
    private double y;
    private double z;

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d add(double x,double y,double z){
        return new Vector3d(this.x + x,this.y + y,this.z + z);
    }
    public Vector3d add(Vector3d vector3d){
        return this.add(vector3d.x,vector3d.y,vector3d.z);
    }

    public double lengthSqr(){
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }


    public Vector3d reverse(){
        return new Vector3d(this.x * -1,this.y * -1,this.z * -1);
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
