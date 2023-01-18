import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {
    StationIndex testMetro = new StationIndex();
    RouteCalculator testRouteCalculator = new RouteCalculator(testMetro);

    @Override
    protected void setUp() throws Exception {
        Line line1 = new Line(1, "A");
        Station a1 = new Station("a1", line1);
        Station a2 = new Station("a2", line1);
        line1.addStation(a1);
        line1.addStation(a2);
        testMetro.addLine(line1);
        testMetro.addStation(a1);
        testMetro.addStation(a2);

        Line line2 = new Line(2, "B");
        Station b1 = new Station("b1", line2);
        Station b2 = new Station("b2", line2);
        line2.addStation(b1);
        line2.addStation(b2);
        testMetro.addLine(line2);
        testMetro.addStation(b1);
        testMetro.addStation(b2);

        Line line3 = new Line(3, "C");
        Station c1 = new Station("c1", line3);
        Station c2 = new Station("c2", line3);
        line3.addStation(c1);
        line3.addStation(c2);
        testMetro.addLine(line3);
        testMetro.addStation(c1);
        testMetro.addStation(c2);

        List<Station> firstConnection = new ArrayList<>();
        firstConnection.add(a2);
        firstConnection.add(b2);
        testMetro.addConnection(firstConnection);

        List<Station> secondConnection = new ArrayList<>();
        secondConnection.add(a1);
        secondConnection.add(c1);
        testMetro.addConnection(secondConnection);
    }

    public void testCalculateDuration() {
        List<Station> temp = new ArrayList<>();
        temp.add(testMetro.getStation("a1"));
        temp.add(testMetro.getStation("a2"));
        temp.add(testMetro.getStation("b2"));
        temp.add(testMetro.getStation("b1"));
        temp.add(testMetro.getStation("c1"));
        temp.add(testMetro.getStation("c2"));
        double actual = RouteCalculator.calculateDuration(temp);
        double expected = 14.5;
        assertEquals(expected, actual);
    }

    public void testGetRouteOnTheLine() {
        List<Station> actual = testRouteCalculator
                .getShortestRoute(testMetro.getStation("a1"), testMetro.getStation("a2"));
        List<Station> expected = new ArrayList<>();
        expected.add(testMetro.getStation("a1"));
        expected.add(testMetro.getStation("a2"));

        assertEquals(expected, actual);
    }

    public void testGetRouteWithOneConnection() {
        List<Station> actual = testRouteCalculator
                .getShortestRoute(testMetro.getStation("b1"), testMetro.getStation("a1"));
        List<Station> expected = new ArrayList<>();
        expected.add(testMetro.getStation("b1"));
        expected.add(testMetro.getStation("b2"));
        expected.add(testMetro.getStation("a2"));
        expected.add(testMetro.getStation("a1"));

        assertEquals(expected, actual);
    }

    public void testGetRouteWithTwoConnections() {
        List<Station> actual = testRouteCalculator
                .getShortestRoute(testMetro.getStation("b1"), testMetro.getStation("c2"));
        List<Station> expected = new ArrayList<>();
        expected.add(testMetro.getStation("b1"));
        expected.add(testMetro.getStation("b2"));
        expected.add(testMetro.getStation("a2"));
        expected.add(testMetro.getStation("a1"));
        expected.add(testMetro.getStation("c1"));
        expected.add(testMetro.getStation("c2"));

        assertEquals(expected, actual);
    }
}
