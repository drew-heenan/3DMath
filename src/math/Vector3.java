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
public class Vector3 extends Point3 {

    public Vector3(float x, float y, float z) {
        super(x, y, z);
    }
    
    @Override
    public Vector3 add(Point3 v) {
        return new Vector3(getX() + v.getX(), getY() + v.getY(), getZ() + v.getZ());
    }
    
    @Override
    public Vector3 sub(Point3 v) {
        return new Vector3(getX() - v.getX(), getY() - v.getY(), getZ() - v.getZ());
    }
    
    @Override
    public Vector3 mul(Point3 v) {
        return new Vector3(getX() * v.getX(), getY() * v.getY(), getZ() * v.getZ());
    }
    
    @Override
    public Vector3 div(Point3 v) {
        return new Vector3(getX() / v.getX(), getY() / v.getY(), getZ() / v.getZ());
    }
    
    @Override
    public Vector3 mul(float c) {
        return new Vector3(getX() * c, getY() * c, getZ() * c);
    }
    
    @Override
    public Vector3 div(float c) {
        return new Vector3(getX() / c, getY() / c, getZ() / c);
    }
    
    public Vector3 cross(Vector3 v) {
        return new Vector3((getY() * v.getZ()) - (getZ() * v.getY()), (getZ() * v.getX()) - (getX() * v.getZ()), (getX() * v.getY()) - (getY() * v.getX()));
    }
    
    /**
     * @return the length of this Vector.
     */
    public float length() {
        return (float) Math.sqrt((getX()*getX()) + (getY()*getY()) + (getZ()*getZ()));
    }
        
    /**
     * Returns the dot product of this Vector and a given Vector.
     * @param v The Vector to calculate the dot product with.
     * @return the dot product of this Vector and a given Vector.
     */
    public float dot(Vector3 v) {
        return getX()*v.getX() + getY()*v.getY() + getZ()*v.getZ();
    }
    
    public void rotate(Vector3 v, float deg) {
        RotationMatrix.rotVector(v, this, deg);
    } 
    
    /**
     * Normalizes this Vector.
     */
    public Vector3 normalize() {
        return mul(1/length());
    }
    
    /**
     * Calculates the angle between two Vector3s; Vector3s do not need to be length 1.
     * @param v The Vector3 to compare to this Vector3.
     * @return the angle, in degrees, between the two given Vectors.
     */
    public float getAngle(Vector3 v) {
        return (float) Math.toDegrees(Math.acos(dot(v)/(length() + v.length())));
    }
    
    @Override
    public Vector3 clone() {
        return new Vector3(getX(), getY(), getZ());
    }
    
}
