/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package math;


/**
 *
 * @author Drew
 */
public class Point3 {

    protected float x, y, z;
    
    public Point3() {
        this(0, 0, 0);
    }
    
    public Point3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getZ() {
        return z;
    }
    
    public void setX(float x) {
        this.x = x;
    }
    
    public void setY(float y) {
        this.y = y;
    }
    
    public void setZ(float z) {
        this.z = z;
    }
    
    public void setPosition(float x, float y, float z) {
        Point3 old = clone();
        setX(x);
        setY(y);
        setZ(z);
    }
    
    public Point3 sub(Point3 p) {
        return new Point3(getX()- p.getX(), getY()- p.getY(), getZ()- p.getZ());
    }
    
    public Point3 add(Point3 p) {
        return new Point3(getX()+ p.getX(), getY()+ p.getY(), getZ()+ p.getZ());
    }
    
    public Point3 mul(Point3 p) {
        return new Point3(getX()* p.getX(), getY()* p.getY(), getZ()* p.getZ());
    }
    
    public Point3 mul(float c) {
        return new Point3(getX()* c, getY()* c, getZ()* c);
    }
    
    public Point3 div(Point3 p) {
        return new Point3(getX()/ p.getX(), getY()/ p.getY(), getZ()/ p.getZ());
    }
    
    public Point3 div(float c) {
        return new Point3(getX()/ c, getY()/ c, getZ()/ c);
    }
    
    public float dist(Point3 p){
	return (float) Math.sqrt(Math.pow(getX() - p.getX(), 2) + Math.pow(getY() - p.getY(), 2) + Math.pow(getZ() - p.getZ(), 2));
    }
    
    public void rotate(Line3 ax, float deg){
	RotationMatrix.rotPoint(ax, this, deg);
    }
    
    public Point3 mid(Point3 p) {
        return new Point3((getX() + p.getX())/2, (getY() + p.getY())/2, (getZ() + p.getZ())/2);
    }
    
    public Vector3 asVector3() {
        return new Vector3(x, y, z);
    }
    
    @Override
    public Point3 clone() {
        return new Point3(getX(), getY(), getZ());
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Point3) {
            Point3 p = (Point3)o;
            return p.getX() == getX() && p.getY() == getY() && p.getZ() == getZ();
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
    }
    
}
