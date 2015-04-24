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
public class Sphere implements Volume {
    
    private Point3 c;
    private float r;
    
    public Sphere(Point3 c, float r) {
        this.c = c;
        this.r = r;
    }
    
    public Point3 getCenter() {
        return c.clone();
    }
    
    @Override
    public float getRadius() {
        return r;
    }
    
    public int getIntersectionCount(Line3 l) {
        Point3[] rs = l.getTwoPoints();
        Point3 r1 = rs[0], r2 = rs[1];
        float dx = r2.getX() - r1.getX();
        float dy = r2.getY() - r1.getY();
        float dz = r2.getZ() - r1.getZ();
        float a = dx*dx + dy*dy + dz*dz;
        float b = 2*dx*(r1.getX() - getCenter().getX()) + 2*dy*(r1.getY() - getCenter().getY()) + 2*dz*(r1.getZ() - getCenter().getZ());
        float c = getCenter().getX()*getCenter().getX() + getCenter().getZ()*getCenter().getZ() + r1.getX()*r1.getX() + r1.getY()*r1.getY() + r1.getZ()*r1.getZ() + -2*(getCenter().getX()*r1.getX() + getCenter().getY()*r1.getY() + getCenter().getZ()*r1.getZ()) - getRadius() * getRadius();
        float d = (b*b) - 4*a*c;
        if(d < 0)
            return 0;
        else if(d == 0)
            return 1;
        else
            return 2;
        
    }
    
    @Override
    public Point3[] getIntersections(Line3 l) {
        if(l.contains(c)) {
            Vector3 lv = l.getDirectionVector().mul(r);
            return new Point3[]{c.add(lv), c.add(lv.mul(-1))};
        }
        Point3 n = l.closestPointTo(c);
        Point3 n2 = getNearestPointTo(n);
        if(n.equals(n2))
            return new Point3[]{n};
        Point3[] ps = l.getTwoPoints();
        Vector3 w = ps[0].sub(getCenter()).asVector3();
        Vector3 ln = l.getDirectionVector();
        float ar = (ln.dot(w));
        float wl = w.length();
        float der = ((ar*ar)-(wl*wl) + (r*r));
        if(der < 0)
            return new Point3[0];
        float a = n.dist(c);   
        float d = (float)Math.sqrt(r*r - a*a);
//        float ll = ln.length();
////        float denom = ll*ll;
////        float pre = -(ln.dot(w));
//        float d = (-(ln.dot(w)) + (float)Math.sqrt(der))/(ll*ll);
        Vector3 v1 = ps[0].sub(c).asVector3();
        Vector3 v2 = n2.sub(c).asVector3();
        Vector3 norm = v1.cross(v2);
        Line3 axOfRot = new Line3(n, norm);
        Point3 base = n.add(n2.sub(n).asVector3().normalize().mul(d));
        Point3[] out = new Point3[]{base.clone(), base.clone()};
        out[0].rotate(axOfRot, -90);
        out[1].rotate(axOfRot, 90);
        return out;
        
    }
    
    /**
     * Finds the nearest point on the outside of the sphere to the given Point3.
     * @param p The Point3 to find the nearest point to.
     * @return The nearest point to the given Point3.
     */
    public Point3 getNearestPointTo(Point3 p) {
        Vector3 v1 = p.sub(getCenter()).asVector3();
        float d = getRadius()/v1.length();
        return v1.mul(d).add(getCenter());
    }

    @Override
    public float getVolume() {
        return (12.5663706144F * r*r*r)/3;
    }

    @Override
    public boolean intersects(Line3 l) {
        Point3[] ps = l.getTwoPoints();
        //http://en.wikipedia.org/wiki/Line%E2%80%93sphere_intersection
        Vector3 w = ps[0].sub(getCenter()).asVector3();
        float a = (l.getDirectionVector().dot(w));
        float wl = w.length();
        return ((a*a)-(wl*wl) + (r*r)) >= 0;
    }

    @Override
    public Point3 getIntersection(Line3 l) {
        Point3[] ints = getIntersections(l);
        if(ints.length == 0)
            return null;
        return ints[0];
    }

    @Override
    public Sphere getContainingSphere() {
        return this;
    }

    @Override
    public Point3[] getPoints() {
        return new Point3[] {
            c.add(new Point3(r, 0, 0)),
            c.add(new Point3(-r, 0, 0)),
            c.add(new Point3(0, r, 0)),
            c.add(new Point3(0, -r, 0)),
            c.add(new Point3(0, 0, r)),
            c.add(new Point3(0, 0, -r))
        };
    }
}
