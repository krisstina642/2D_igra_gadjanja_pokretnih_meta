package PomocneKlase;

import javafx.geometry.Point2D;
import javafx.scene.shape.CubicCurve;

public interface BezjeovaKriva {
    static Point2D findMiddle(CubicCurve curve){
        double middle1X=(curve.getStartX()+curve.getControlX1())/2;
        double middle1Y=(curve.getStartY()+curve.getControlY1())/2;

        double middle2X=(curve.getControlX1()+curve.getControlX2())/2;
        double middle2Y=(curve.getControlY1()+curve.getControlY2())/2;

        double middle3X=(curve.getEndX()+curve.getControlX2())/2;
        double middle3Y=(curve.getEndY()+curve.getControlY2())/2;

        double middle12X=(middle1X+middle2X)/2;
        double middle12Y=(middle1Y+middle2Y)/2;

        double middle23X=(middle3X+middle2X)/2;
        double middle23Y=(middle3Y+middle2Y)/2;

        double middle123X=(middle12X+middle23X)/2;
        double middle123Y=(middle12Y+middle23Y)/2;

        Point2D point =new Point2D(middle123X,middle123Y);
        return point;
    };
}
