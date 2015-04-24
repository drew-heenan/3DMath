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
public class Line3 {
    
    
    public static final Line3 
            X_AXIS = new Line3(new Point3(), new Vector3(1, 0, 0)),
            Y_AXIS = new Line3(new Point3(), new Vector3(0, 1, 0)),
            Z_AXIS = new Line3(new Point3(), new Vector3(0, 0, 1));
    
    private Point3 p;
    private Vector3 vec;
    
    public Line3(Point3 p, Vector3 vec) {
        this.p = p;
        this.vec = vec;
    }
    
    public Line3(Point3 p1, Point3 p2) {
        this.p = p1;
    }
    
    public Vector3 getDirectionVector() {
        return vec.normalize();
    }
    
    public Point3 closestPointTo(Point3 p) {
        Point3[] pts = getTwoPoints();
        Vector3 u = pts[1].sub(pts[0]).asVector3();
        Vector3 v = p.sub(pts[0]).asVector3();
        return pts[0].add(u.mul(u.dot(v)));
    }
    
    public void rotate(Line3 ax, float deg) {
        Point3 v = p.add(vec);
        v.rotate(ax, deg);
        this.vec = v.sub(p).asVector3();
        p.rotate(ax, deg);
    }
    
    public boolean contains(Point3 p) {
        Vector3 dv = getDirectionVector(), nv = p.sub(this.p).asVector3().normalize();
        return dv.equals(nv) || dv.equals(nv.mul(-1));
    }
    
    public Point3[] getTwoPoints() {
        return new Point3[]{p, p.add(vec)};
    }
    
}
