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
public class AxisAlignedBox implements Volume {
    
    protected Point3[] vertices;
    private float xLength, zLength, height;
    protected Point3 c;
    
    public AxisAlignedBox(Point3 center, float xLength, float zLength, float height) {
        this.xLength = xLength;
        this.height = height;
        this.zLength = zLength;
        this.c = center;
        float x = xLength/2, y = height/2, z = zLength/2;
        vertices = new Point3[] {
            new RelativePoint3(x, y, z, center),
            new RelativePoint3(-x, y, z, center),
            new RelativePoint3(-x, y, -z, center),
            new RelativePoint3(x, y, -z, center),
            
            new RelativePoint3(x, -y, z, center),
            new RelativePoint3(-x, -y, z, center),
            new RelativePoint3(-x, -y, -z, center),
            new RelativePoint3(x, -y, -z, center),
        };
    }
    
    public Point3[] getPoints() {
        return vertices.clone();
    }
    
    public float getXLength() {
        return xLength;
    }
    
    public float getHeight() {
        return height;
    }
    
    public float getZLength() {
        return zLength;
    }
    
    public Point3 getCenter() {
        return c;
    }
    
    public boolean contains(Point3 p) {
        float x = p.getX(), y = p.getY(), z = p.getZ(), xl = xLength/2, zl = zLength/2, hl = height/2;
        return ((x >= c.getX() - xl) && (x <= c.getX() + xl)) &&
                ((y >= c.getY() - hl) && (y <= c.getY() + hl)) &&
                ((z >= c.getZ() - zl) && (z <= c.getZ() + zl));
    }
    
//    public boolean contains(Sphere s) {
//        return (contains(new RelativePoint3(0, s.getRadius(), 0, s.getCenter())) && contains(new RelativePoint3(0, -s.getRadius(), 0, s.getCenter())) && 
//                contains(new RelativePoint3(s.getRadius(), 0, 0, s.getCenter())) && contains(new RelativePoint3(-s.getRadius(), 0, 0, s.getCenter())) &&
//                contains(new RelativePoint3(0, 0, s.getRadius(), s.getCenter())) &&contains(new RelativePoint3(0, 0, -s.getRadius(), s.getCenter())));
//    }
    public boolean contains(Volume v) {
        if(!contains(v.getCenter()))
            return false;
        boolean out = true;
        Point3[] pts = v.getPoints();
        for(Point3 p : pts)
            if(!contains(p)) {
                out = false;
                break;
            }
        return out;
    }
    
    public boolean intersects(Sphere s) {
        return contains(s.getNearestPointTo(getCenter()));
    }
    
    public Rect3[] getSides() {
        return new Rect3[] {
            new Rect3(vertices[0], vertices[1], vertices[5]),
            new Rect3(vertices[1], vertices[3], vertices[7]),
            new Rect3(vertices[3], vertices[2], vertices[6]),
            new Rect3(vertices[2], vertices[0], vertices[4]),
            
            new Rect3(vertices[1], vertices[0], vertices[2]),
            new Rect3(vertices[6], vertices[4], vertices[5])
        };
    }
    
    public boolean intersects(Line3 l) {
        return contains(l.closestPointTo(getCenter())); //TODO: Maybe it will work better by looping and breaking through the sides?
    } 

    @Override
    public float getRadius() {
        float r = 0;
        for(Point3 p : getPoints()) {
            float d = p.dist(c);
            if(d > r)
                r = d;
        }
        return r;
    }

    @Override
    public float getVolume() {
        return xLength*zLength*height;
    }

    @Override
    public Point3 getIntersection(Line3 l) {
        Point3[] out = getIntersections(l);
        if(out.length == 0)
            return null;
        return out[0];
    }

    @Override
    public Point3[] getIntersections(Line3 l) {
        if(!intersects(l))
            return new Point3[0];
        Point3[] out = new Point3[2];
        Rect3[] sides = getSides();
        int i = 0;
        for(Rect3 r : sides) {
            Point3 ri = r.getIntersection(l);
            if(ri != null) {
                out[i] = ri;
                i++;
                if(i == 2)
                    break;
            }
        }
        Point3 p = l.getTwoPoints()[0];
        Point3 a = out[0], b = out[2];
        float d1 = a.dist(p), d2 = b.dist(p);
        if(d1 > d2) {
            out[0] = b;
            out[1] = a;
        }
        return out;
    }

    @Override
    public Sphere getContainingSphere() {
        float rad = 0;
        Point3 c = getCenter();
        for(Point3 p : vertices) {
            float d = p.dist(c);
            if(d > rad)
                rad = d;
        }
        return new Sphere(c, rad);
    }
    
}
