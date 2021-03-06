/** A class that will actually run the simulation.
 *  The goal of this class is to simulate a universe specified in one of the data files. 
 */
public class NBody {
    public static double readRadius(String path) {
        In in = new In(path);
        int skip = in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String path) {
        In in = new In(path);
        int n = in.readInt();
        double skip2 = in.readDouble();
        Planet[] planets = new Planet[n];
        for (int i = 0; i < n; i += 1) {
            if (in.isEmpty()) {
                break;
            }
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        double universeRadius = readRadius(filename);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-universeRadius, universeRadius);

        for (double t = 0.0; t < T; t += dt) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int i = 0; i < planets.length; i += 1) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i += 1) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p: planets) {
                p.draw();
            }            
            StdDraw.show();
            StdDraw.pause(10);
        }
        printUniverse(planets, universeRadius);
    }

    private static void printUniverse(Planet[] planets, double radius) {
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                          planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }
}