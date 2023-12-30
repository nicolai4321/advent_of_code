package advent_of_code.years.year2023.day24;

import java.math.BigDecimal;

import advent_of_code.utils.math.big.BigDec;
import advent_of_code.utils.math.big.BigInt;
import advent_of_code.utils.math.geometry.Geo;
import advent_of_code.utils.math.geometry.Line2D;

public class Stone {
    private final BigInt x;
    private final BigInt y;
    private final BigInt z;
    private final BigInt vx;
    private final BigInt vy;
    private final BigInt vz;
    
    public Stone(BigInt x, BigInt y, BigInt z, BigInt vx, BigInt vy, BigInt vz) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }
    
    public BigDec[] get2DCollisionPoint(Stone stone, BigInt minRaw, BigInt maxRaw) {
        BigInt[] min = { minRaw, minRaw };
        BigInt[] max = { maxRaw, maxRaw };
        
        BigDecimal[] eq0 = get2DEq();
        BigDecimal[] eq1 = stone.get2DEq();
        
        Line2D line0 = new Line2D(new BigDec(eq0[0]), new BigDec(eq0[1]), min, max);
        Line2D line1 = new Line2D(new BigDec(eq1[0]), new BigDec(eq1[1]), min, max);

        BigDec[] point = Geo.getIntersectionPoint(line0, line1);
        
        if (point == null) {
            return null;
        }
        
        BigDec xx = point[0];
        BigDec yy = point[1];
        
        // x positive
        if (BigInt.ZERO.leq(vx) && BigDec.le(xx, new BigDecimal(x))) {
            return null;
        }
        
        if (BigInt.ZERO.leq(stone.getVx()) && BigDec.le(xx, new BigDecimal(stone.getX()))) {
            return null;
        }
        
        // x negative
        if (vx.leq(BigInt.ZERO) && BigDec.le(new BigDecimal(x), xx)) {
            return null;
        }
        
        if (stone.getVx().leq(BigInt.ZERO) && BigDec.le(new BigDecimal(stone.getX()), xx)) {
            return null;
        }
        
        // y positive
        if (BigInt.ZERO.leq(vy) && BigDec.le(yy, new BigDecimal(y))) {
            return null;
        }
        
        if (BigInt.ZERO.leq(stone.getVy()) && BigDec.le(yy, new BigDecimal(stone.getY()))) {
            return null;
        }
        
        // y negative
        if (vy.leq(BigInt.ZERO) && BigDec.le(new BigDecimal(y), yy)) {
            return null;
        }
        
        if (stone.getVy().leq(BigInt.ZERO) && BigDec.le(new BigDecimal(stone.getY()), yy)) {
            return null;
        }
        
        return point;
    }
    
    public BigInt[] timeCollide(int t0, int t1, Stone stone) {
        if (t1 < t0) {
            return null;
        }
        
        BigInt x0 = x.add(vx.mult(t0));
        BigInt y0 = y.add(vy.mult(t0));
        BigInt z0 = z.add(vz.mult(t0));
        
        BigInt x1 = stone.getX().add(stone.getVx().mult(t1));
        BigInt y1 = stone.getY().add(stone.getVy().mult(t1));
        BigInt z1 = stone.getZ().add(stone.getVz().mult(t1));
        
        BigInt deltaX = x1.sub(x0);
        BigInt deltaY = y1.sub(y0);
        BigInt deltaZ = z1.sub(z0);
        BigInt deltaTime = BigInt.sub(t1, t0);

        BigInt newVx = deltaX.div(deltaTime).getInt();
        if (newVx == null) {
            return null;
        }
        
        BigInt newVy = deltaY.div(deltaTime).getInt();
        if (newVy == null) {
            return null;
        }
        
        BigInt newVz = deltaZ.div(deltaTime).getInt();
        if (newVz == null) {
            return null;
        }

        return new BigInt[] { x0.add(newVx.mult(-t0)), y0.add(newVy.mult(-t0)), z0.add(newVz.mult(-t0)), newVx, newVy, newVz };
    }

    public Line2D getInfLine() {
        BigDecimal[] eq0 = get2DEq();
        return new Line2D(new BigDec(eq0[0]), new BigDec(eq0[1]));
    }
    
    public BigDec[] get2DEq() {
        BigDec slope = BigDec.div(vy, vx);
        BigDec b = y.sub(x.mult(slope));
        return new BigDec[] { slope, b };
    }
    
    public BigInt getX() {
        return x;
    }
    
    public BigInt getY() {
        return y;
    }
    
    public BigInt getZ() {
        return z;
    }
    
    public BigInt getVx() {
        return vx;
    }
    
    public BigInt getVy() {
        return vy;
    }
    
    public BigInt getVz() {
        return vz;
    }

    public String toString() {
        return "{(" + x + "," + y+ "," + z + "): (" + vx + "," + vy + "," + vz + ")}";
    }
}
