package PomocneKlase;

import javafx.scene.Node;
import javafx.scene.shape.*;
import org.apache.batik.parser.PathParser;

import java.util.List;

public class ObradaOblika {

    public static Path stringToPath(final String string){

        SVGPath svgPath = new SVGPath();
        svgPath.setContent(string);

        PathParser parser = new PathParser();
        SVGParser handler = new SVGParser("track");
        parser.setPathHandler(handler);

        parser.parse(string);
        return handler.getPath();
    }

    public static String pathToString(final Path PATH) {
        final StringBuilder fxPath = new StringBuilder();
        for (PathElement element : PATH.getElements()) {
            if (MoveTo.class.equals(element.getClass())) {
                fxPath.append("M ")
                        .append(((MoveTo) element).getX()).append(" ")
                        .append(((MoveTo) element).getY()).append(" ");
            } else if (LineTo.class.equals(element.getClass())) {
                fxPath.append("L ")
                        .append(((LineTo) element).getX()).append(" ")
                        .append(((LineTo) element).getY()).append(" ");
            } else if (CubicCurveTo.class.equals(element.getClass())) {
                fxPath.append("C ")
                        .append(((CubicCurveTo) element).getControlX1()).append(" ")
                        .append(((CubicCurveTo) element).getControlY1()).append(" ")
                        .append(((CubicCurveTo) element).getControlX2()).append(" ")
                        .append(((CubicCurveTo) element).getControlY2()).append(" ")
                        .append(((CubicCurveTo) element).getX()).append(" ")
                        .append(((CubicCurveTo) element).getY()).append(" ");
            } else if (QuadCurveTo.class.equals(element.getClass())) {
                fxPath.append("Q ")
                        .append(((QuadCurveTo) element).getControlX()).append(" ")
                        .append(((QuadCurveTo) element).getControlY()).append(" ")
                        .append(((QuadCurveTo) element).getX()).append(" ")
                        .append(((QuadCurveTo) element).getY()).append(" ");
            } else if (ArcTo.class.equals(element.getClass())) {
                fxPath.append("A ")
                        .append(((ArcTo) element).getRadiusX()).append(" ")
                        .append(((ArcTo) element).getRadiusY()).append(" ")
                        .append(((ArcTo) element).getXAxisRotation()).append(" ")
                        .append(((ArcTo) element).isLargeArcFlag()?1:0).append(" ")
                        .append(((ArcTo) element).isSweepFlag()?1:0).append(" ")
                        .append(((ArcTo) element).getX()).append(" ")
                        .append(((ArcTo) element).getY()).append(" ");

            } else if (HLineTo.class.equals(element.getClass())) {
                fxPath.append("H ")
                        .append(((HLineTo) element).getX()).append(" ");
            } else if (VLineTo.class.equals(element.getClass())) {
                fxPath.append("V ")
                        .append(((VLineTo) element).getY()).append(" ");
            } else if (ClosePath.class.equals(element.getClass())) {
                fxPath.append("Z");
            }
        }
        return fxPath.toString();
    }

    public static String LineToString(final Line LINE) {
        final StringBuilder fxPath = new StringBuilder();
        fxPath.append("M ").append(LINE.getStartX()).append(" ").append(LINE.getStartY()).append(" ")
                .append("L ").append(LINE.getEndX()).append(" ").append(LINE.getEndY());
        return fxPath.toString();
    }

    public static String PolygonToString(final Polygon POLYGON) {
        final StringBuilder fxPath = new StringBuilder();
        final int           size   = POLYGON.getPoints().size();
        if (size % 2 == 0) {
            List<Double> coordinates     = POLYGON.getPoints();
            for (int i = 0 ; i < size ; i += 2) {
                fxPath.append(i == 0 ? "M " : "L ")
                        .append(coordinates.get(i)).append(" ").append(coordinates.get(i + 1)).append(" ");
            }
            fxPath.append("Z");
        }
        return fxPath.toString();
    }

    public static String CubicCurveToString(final CubicCurve CUBIC_CURVE) {
        final StringBuilder fxPath = new StringBuilder();
        fxPath.append("M ").append(CUBIC_CURVE.getStartX()).append(" ").append(CUBIC_CURVE.getStartY()).append(" ")
                .append("C ").append(CUBIC_CURVE.getControlX1()).append(" ").append(CUBIC_CURVE.getControlY1()).append(" ")
                .append(CUBIC_CURVE.getControlX2()).append(" ").append(CUBIC_CURVE.getControlY2()).append(" ")
                .append(CUBIC_CURVE.getEndX()).append(" ").append(CUBIC_CURVE.getEndY());
        return fxPath.toString();
    }
    public static String EllipseToString(final Ellipse ELLIPSE) {
        final StringBuilder fxPath = new StringBuilder();
        final double CENTER_X           = ELLIPSE.getCenterX() == 0 ? ELLIPSE.getRadiusX() : ELLIPSE.getCenterX();
        final double CENTER_Y           = ELLIPSE.getCenterY() == 0 ? ELLIPSE.getRadiusY() : ELLIPSE.getCenterY();
        final double RADIUS_X           = ELLIPSE.getRadiusX();
        final double RADIUS_Y           = ELLIPSE.getRadiusY();
        final double CONTROL_DISTANCE_X = RADIUS_X * 0.5522847498307935;
        final double CONTROL_DISTANCE_Y = RADIUS_Y * 0.5522847498307935;
        // Move to first point
        fxPath.append("M ").append(CENTER_X).append(" ").append(CENTER_Y - RADIUS_Y).append(" ");
        // 1. quadrant
        fxPath.append("C ").append(CENTER_X + CONTROL_DISTANCE_X).append(" ").append(CENTER_Y - RADIUS_Y).append(" ")
                .append(CENTER_X + RADIUS_X).append(" ").append(CENTER_Y - CONTROL_DISTANCE_Y).append(" ")
                .append(CENTER_X + RADIUS_X).append(" ").append(CENTER_Y).append(" ");
        // 2. quadrant
        fxPath.append("C ").append(CENTER_X + RADIUS_X).append(" ").append(CENTER_Y + CONTROL_DISTANCE_Y).append(" ")
                .append(CENTER_X + CONTROL_DISTANCE_X).append(" ").append(CENTER_Y + RADIUS_Y).append(" ")
                .append(CENTER_X).append(" ").append(CENTER_Y + RADIUS_Y).append(" ");
        // 3. quadrant
        fxPath.append("C ").append(CENTER_X - CONTROL_DISTANCE_X).append(" ").append(CENTER_Y + RADIUS_Y).append(" ")
                .append(CENTER_X - RADIUS_X).append(" ").append(CENTER_Y + CONTROL_DISTANCE_Y).append(" ")
                .append(CENTER_X - RADIUS_X).append(" ").append(CENTER_Y).append(" ");
        // 4. quadrant
        fxPath.append("C ").append(CENTER_X - RADIUS_X).append(" ").append(CENTER_Y - CONTROL_DISTANCE_Y).append(" ")
                .append(CENTER_X - CONTROL_DISTANCE_X).append(" ").append(CENTER_Y - RADIUS_Y).append(" ")
                .append(CENTER_X).append(" ").append(CENTER_Y - RADIUS_Y).append(" ");
        // Close path
        fxPath.append("Z");
        return fxPath.toString();
    }

    public static String PolylineToString(final Polyline POLYLINE) {
        final StringBuilder fxPath = new StringBuilder();
        final int           size   = POLYLINE.getPoints().size();
        if (size % 2 == 0) {
            List<Double> coordinates     = POLYLINE.getPoints();
            for (int i = 0 ; i < size ; i += 2) {
                fxPath.append(i == 0 ? "M " : "L ")
                        .append(coordinates.get(i)).append(" ").append(coordinates.get(i + 1)).append(" ");
            }
        }
        return fxPath.toString();
    }

    public static String NodeToString(final Node node) {
        if (node instanceof Polyline) return PolylineToString((Polyline) node);
        if (node instanceof Line) return LineToString((Line) node);
        if (node instanceof Ellipse) return EllipseToString((Ellipse) node);
        if (node instanceof CubicCurve) return CubicCurveToString((CubicCurve) node);
        if (node instanceof Polygon) return PolygonToString((Polygon) node);
       return null;
    }
}
