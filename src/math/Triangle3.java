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
public class Triangle3 extends Shape3 {

    public Triangle3(Point3 p1, Point3 p2, Point3 p3) {
        
    }
    
    public Triangle3(Point3 p1, Point3 p2, Point3 p3, Vector3 n) {
        
    }
    
    @Override
    public boolean intersects(Line3 l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Point3 getIntersection(Line3 l) {
        Point3[] linePoints = l.getTwoPoints();
        Point3[] trianglePoints = getPoints();
        
        Vector3 pq = linePoints[1].clone().sub(linePoints[0]).asVector3();
        Vector3 pa = trianglePoints[0].clone().sub(linePoints[0]).asVector3();
        Vector3 pb = trianglePoints[1].clone().sub(linePoints[0]).asVector3();
        Vector3 pc = trianglePoints[2].clone().sub(linePoints[0]).asVector3();

        //Vector3 m = new Vector3(pq, pc);
        Vector3 m = pq.cross(pc);

        float u = pb.dot(m);
        float v = -pa.dot(m);

        if (Math.signum(u) != Math.signum(v)) {
            return null;
        }

        //Vector3 pba = new Vector3(pb, pa);
        Vector3 pba = pb.cross(pa);
        float w = pq.dot(pba);

        if (Math.signum(u) != Math.signum(w)) {
            return null;
        }

        float denom = 1 / (u + v + w);

        // r = ((u * denom) * a) + ((v * denom) * b) + ((w * denom) * c);
        Point3 compA = trianglePoints[0].mul(u * denom);
        Point3 compB = trianglePoints[1].mul(v * denom);
        Point3 compC = trianglePoints[2].mul(w * denom); 
        
        return new Point3(compA.getX() + compB.getX() + compC.getX(), compA.getY() + compB.getY() + compC.getY(), compA.getZ() + compB.getZ() + compC.getZ());
    }

    @Override
    public Point3[] getIntersections(Line3 l) {
        return new Point3[]{getIntersection(l)};
    }

    @Override
    public Sphere getContainingSphere() {
        Point3 c = getCenter();
        float max = 0;
        for(Point3 p : vert) {
            float d = p.dist(c);
            if(d > max)
                max = d;
        }
        return new Sphere(c, max);  
    }
    
}
