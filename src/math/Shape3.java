/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package math;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author drewheenan
 */
public abstract class Shape3 implements Volume {
    
    protected Point3[] vert;
    
    public abstract boolean intersects(Line3 l);
    
    protected Point3[] aroundNormal(Vector3 n, Point3 p1, Point3 p2, Point3 p3) {
        float v = p2.sub(p1).asVector3().getAngle(n);
        Point3[] verts = new Point3[3];
        n = n.normalize();
        verts[0] = p1;
        verts[1] = p2;
        verts[2] = p3; 
        if(v != 90) {
            try {
                throw new Math3DException("The desired normal must be perpendicular to the defined plane. Got angle: " + v);
            } catch (Math3DException ex) {
                Logger.getLogger(Plane3.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (!getNormal().equals(n)){
            verts[1] = p3;
            verts[2] = p2;
        }
        return verts;
    }
    
    public void rotate(Line3 ax, float deg) {
        for(Point3 p : vert)
            p.rotate(ax, deg);
    }
    
    public boolean isInFrontOf(Point3 p) {
        return p.sub(vert[0]).asVector3().dot(getNormal()) > 0;
    }
    
    public Vector3 getNormal() {
        Vector3 v1 = vert[1].sub(vert[0]).asVector3();
        Vector3 v2 = vert[2].sub(vert[0]).asVector3();
        Vector3 n = v1.cross(v2).normalize();
        return n;
    }
    
    @Override
    public abstract Point3 getIntersection(Line3 l);
    
    public Point3[] getPoints() {
        return vert;
    }
    
    @Override
    public Point3 getCenter() {
        float x = 0, y = 0, z = 0;
        Point3[] ps = getPoints();
        for(Point3 p : ps) {
            x += p.getX();
            y += p.getY();
            z += p.getY();
        }
        x/=ps.length;
        y/=ps.length;
        z/=ps.length;
        return new Point3(x, y, z);
    }
    
    @Override
    public float getVolume() {
        return 0;
    }
    
    @Override
    public float getRadius() {
        float r = 0;
        Point3 c = getCenter();
        for(Point3 p : getPoints()) {
            float d = p.dist(c);
            if(d > r)
                r = d;
        }
        return r;
    }
    
}
