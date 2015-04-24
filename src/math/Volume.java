/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package math;

/**
 *
 * @author drewheenan
 */
public interface Volume {
    
    public float getRadius();
    
    public float getVolume();
    
    public boolean intersects(Line3 l);
    
    public Point3 getIntersection(Line3 l);
    
    public Point3[] getIntersections(Line3 l);
    
    public Point3 getCenter();
    
    public Sphere getContainingSphere();
    
    public Point3[] getPoints();
    
}
