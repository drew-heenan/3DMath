/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package math;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Drew
 */
public class Plane3 extends Shape3 {
    
    protected Vector3 n;
    
    /**
     * Constructs a Plane3 using three Point3 Objects. The points should be ordered counterclockwise around the desired normal.
     * @param p1 A Point3 on the plane.
     * @param p2 A Point3 on the plane.
     * @param p3 A Point3 on the plane.
     */
    public Plane3(Point3 p1, Point3 p2, Point3 p3) {
        vert = new Point3[1];
        vert[0] = p1;
        Vector3 v1 = p2.sub(p1).asVector3();
        Vector3 v2 = p3.sub(p1).asVector3();
        Vector3 n = v1.cross(v2);
        this.n = n.normalize();
    }
    
    public Plane3(Point3 p1, Vector3 n) {
        vert = new Point3[1];
        vert[0] = p1;
        this.n = n;
    }
    
    
    
    /**
     * Checks if the given Point3 is contained within this Plane3.
     * @param p The Point3 to evaluate.
     * @return If the given Point3 is contained within this Plane3.
     */
    public boolean contains(Point3 p) {
        return p.sub(vert[0]).asVector3().dot(getNormal()) == 0;
    }
    
    /**
     * Gets a normal for this Plane3 with a length of 1.
     * @return the normal of the plane.
     */
    @Override
    public Vector3 getNormal() {
        return n;
    }
    
    /**
     * Calculates the point of intersection between the given Line3 and this Plane3.
     * @param l The intersecting line.
     * @return The Point3 of intersection, or null if the Line3 is parallel.
     */
    public Point3 getIntersection(Line3 l) {
        Point3[] pts = l.getTwoPoints();

        Vector3 dR = pts[0].sub(pts[1]).asVector3();

        float ndotdR = getNormal().dot(dR);

        if (Math.abs(ndotdR) < 1e-6f)
            return null;

        float t = -getNormal().dot(pts[0].sub(vert[0]).asVector3()) / ndotdR;
        Point3 M = pts[0].add(dR.mul(t));
        return M;
    }
    
    /**
     * Calculates if the given Line3 intersects this Plane3, it is generally faster than checking for a null output from getIntersection().
     * @param l The intersecting line.
     * @return A boolean for if the Line3 intersects this Plane3 or not.
     */
    public boolean intersects(Line3 l) {
        Point3[] pts = l.getTwoPoints();

        Vector3 dR = pts[0].sub(pts[1]).asVector3();

        float ndotdR = getNormal().dot(dR);

        if (Math.abs(ndotdR) < 1e-6)
            return false;
        return true;
    }

    @Override
    public Point3[] getIntersections(Line3 l) {
        return new Point3[] {getIntersection(l)};
    }

    @Override
    public Sphere getContainingSphere() {
        return new Sphere(new Point3(), Float.MAX_VALUE);
    }
    
    
    
}
