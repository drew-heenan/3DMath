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
 * @author Drew
 */
public class Rect3 extends Shape3 {

    public Rect3(Point3 p1, Point3 p2, Point3 p3) {
        if(p2.sub(p1).asVector3().getAngle(p2.sub(p3).asVector3()) != 90)
            try {
                throw new Math3DException("The given points must form a 90-degree angle.");
            } catch (Math3DException ex) {
                Logger.getLogger(Rect3.class.getName()).log(Level.SEVERE, null, ex);
            }
        vert = new Point3[4];
        vert[0] = p1;
        vert[1] = p2;
        vert[2] = p3;
        vert[3] = vert[1].sub(vert[0].mid(vert[2])).mul(2);
    }

    public Rect3(Point3 p1, Point3 p2, Point3 p3, Vector3 n) {
        if(p2.sub(p1).asVector3().getAngle(p2.sub(p3).asVector3()) != 90)
            try {
                throw new Math3DException("The given points must form a 90-degree angle.");
            } catch (Math3DException ex) {
                Logger.getLogger(Rect3.class.getName()).log(Level.SEVERE, null, ex);
            }
        vert = new Point3[4];
        Point3[] corrected = aroundNormal(n, p1, p2, p3);
        vert[0] = corrected[0];
        vert[1] = corrected[1];
        vert[2] = corrected[2];
        vert[3] = vert[1].sub(vert[0].mid(vert[2])).mul(2);
    }
    
    
    
    @Override
    public boolean intersects(Line3 l) {
        Vector3 dS21 = vert[1].sub(vert[0]).asVector3();
        Vector3 dS31 = vert[2].sub(vert[0]).asVector3();
        Vector3 n = dS21.cross(dS31);
        
        Point3[] pts = l.getTwoPoints();

        Vector3 dR = pts[0].sub(pts[1]).asVector3();

        float ndotdR = n.dot(dR);

        if (Math.abs(ndotdR) < 1e-6f)
            return false;

        float t = -n.dot(pts[0].sub(vert[0]).asVector3()) / ndotdR;
        Vector3 M = pts[0].add(dR.mul(t)).asVector3();
        Vector3 dMS1 = M.sub(vert[0]);
        float u = dMS1.dot(dS21);
        float v = dMS1.dot(dS31);
        return (u >= 0.0f && u <= dS21.dot(dS21)
             && v >= 0.0f && v <= dS31.dot(dS31));
    }
    
    public Point3 getCenter() {
        return new Point3((vert[0].getX() + vert[1].getX() + vert[2].getX() + vert[3].getX())/4, (vert[0].getY() + vert[1].getY() + vert[2].getY() + vert[3].getY())/4, (vert[0].getZ() + vert[1].getZ() + vert[2].getZ() + vert[3].getZ())/4);
    }
    
    @Override
    public Point3 getIntersection(Line3 l) {
        Vector3 dS21 = vert[1].sub(vert[0]).asVector3();
        Vector3 dS31 = vert[2].sub(vert[0]).asVector3();
        Vector3 n = dS21.cross(dS31);
        
        Point3[] pts = l.getTwoPoints();

        Vector3 dR = pts[0].sub(pts[1]).asVector3();

        float ndotdR = n.dot(dR);

        if (Math.abs(ndotdR) < 1e-6f)
            return null;

        float t = -n.dot(pts[0].sub(vert[0]).asVector3()) / ndotdR;
        Vector3 M = pts[0].add(dR.mul(t)).asVector3();
        Vector3 dMS1 = M.sub(vert[0]);
        float u = dMS1.dot(dS21);
        float v = dMS1.dot(dS31);
        if (u >= 0.0f && u <= dS21.dot(dS21)
             && v >= 0.0f && v <= dS31.dot(dS31)) 
            return M;
        return null;
    }

    @Override
    public Point3[] getIntersections(Line3 l) {
        return new Point3[] {getIntersection(l)};
    }

    @Override
    public Sphere getContainingSphere() {
        float rad = 0;
        Point3 c = getCenter();
        for(Point3 p : vert) {
            float d = p.dist(c);
            if(d > rad)
                rad = d;
        }
        return new Sphere(c, rad);
    }
    
}
