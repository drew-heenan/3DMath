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
public class AxisAlignedCube extends AxisAlignedBox {
    
    private float l;
    
    public AxisAlignedCube(Point3 center, float length) {
        super(center, length, length, length);
        this.l = length;
    }
    
    public float getLength() {
        return l;
    }
    
    @Override
    public boolean contains(Point3 p) {
        float x = p.getX(), y = p.getY(), z = p.getZ(), r = getLength()/2;
        return ((x >= c.getX() - r) && (x <= c.getX() + r)) &&
                ((y >= c.getY() - r) && (y <= c.getY() + r)) &&
                ((z >= c.getZ() - r) && (z <= c.getZ() + r));
    }
    
    
        
    
}
