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
public class RelativePoint3 extends Point3 {
     
    protected Point3 relativeTo;

    public RelativePoint3(float x, float y, float z, Point3 relativeTo) {
        super(x, y, z);
        this.relativeTo = relativeTo;
    }
    
    public RelativePoint3(float x, float y, float z) {
        this(x, y, z, new Point3());
    }
    
    public float getRelativeX() {
        return x;
    }
    
    public float getRelativeY() {
        return y;
    }
    
    public float getRelativeZ() {
        return z;
    }
    
    public void setRelativeTo(Point3 go) {
        this.relativeTo = go;
    }
    
    @Override
    public void setPosition(float x, float y, float z) {
        RelativePoint3 old = clone();
        this.x = x - relativeTo.getX();
        this.y = y - relativeTo.getY();
        this.z = z - relativeTo.getZ();
    }
    
    public void setRelativePosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public float getX() {
        return x + relativeTo.getX();
    }
    
    @Override
    public float getY() {
        return y + relativeTo.getY();
    }
    
    @Override
    public float getZ() {
        return z + relativeTo.getZ();
    }
    
    @Override
    public void rotate(Line3 ax, float deg) {
        Point3 temp = new Point3();
        RotationMatrix.rotPoint(ax, temp, deg);
        setPosition(temp.getX(), temp.getY(), temp.getZ());
    }
    
    @Override
    public RelativePoint3 clone() {
        return new RelativePoint3(x, y, z, relativeTo.clone());
    }
    
    
}
