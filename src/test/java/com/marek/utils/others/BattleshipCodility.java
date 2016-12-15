package com.marek.utils.others;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by marek.papis on 2016-06-04.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
public class BattleshipCodility {

    Logger log = LoggerFactory.getLogger("BattleshipCodility.class");

    public String solution(int n, String s, String t) {

        //parse ships
        List<Ship> ships = Pattern.compile(",")
                .splitAsStream(s)
                .map(ss -> new Ship(ss))
                .collect(Collectors.toList());

        //parse ships-points
        ships.stream().forEach((v) ->
                Pattern.compile(" ")
                        .splitAsStream(v.getPointString())
                        .forEach(k -> v.addPoint(new Point(k.charAt(0), k.charAt(1))))
        );

        //calc ships sizes
        ships.stream().forEach(v ->
                v.calcSize()
        );

        //parse shots
        List<Point> shots = Pattern.compile(" ")
                .splitAsStream(t)
                .map(tt -> new Point(tt.charAt(0), tt.charAt(1)))
                .collect(Collectors.toList());

        //merge shots with ships
        ships.stream().forEach(
                s1 -> shots.stream().forEach(sh -> s1.shootAtShip(sh))
        );

        log.info("ships" + ships.toString());
        log.info("Shots" + shots.toString());
        return String.valueOf(ships.stream().mapToInt(ss -> ss.getSunk()).sum())
                + "," +
                String.valueOf(ships.stream().filter(sss -> sss.getShots() > 0 && sss.getSunk() == 0).count());
    }

    @Test
    public void shouldWork() {
        solution(4, "1B 2C,2D 4D", "2B 2D 3D 4D 4A");
    }

    @Test
    public void returnCaulculateShipsSunk3Squares() {
        assertThat(solution(3, "1A 1B,2C 2C", "1B")).isEqualTo("0,1");
    }

    @Test
    public void returnCaulculateShipsSunk4Squares() {
        assertThat(solution(4, "1B 2C,2D 4D", "2B 2D 3D 4D 4A")).isEqualTo("1,1");
    }

    class Point {
        final char x; //A
        final char y; //1

        public int xi; //
        public int yi; //

        public Point(char x, char y) {
            this.x = x;
            this.y = y;
            this.xi = (int) x;
            this.yi = (int) y;

        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    class Ship {
        int sunk;
        private List<Point> points = new ArrayList<>();
        private String pointString;
        private int shots;
        private int shipSize;

        public Ship(String points) {
            pointString = points;
        }

        public int getShots() {
            return shots;
        }

        public void setShots(int shots) {
            this.shots = shots;
            if (shots == shipSize)
                sunk = 1;
        }

        public int getSunk() {
            return sunk;
        }

        public void setSunk(int sunk) {
            this.sunk = sunk;
        }

        private void calcSize() {
            if (points.get(0).xi != points.get(1).xi && points.get(0).yi != points.get(1).yi) {
                //square
                shipSize = 4;
            } else if (points.get(0).xi != points.get(1).xi) {
                //x wide
                shipSize = points.get(1).xi - points.get(0).xi + 1;
            } else {
                //y wide
                shipSize = points.get(1).yi - points.get(0).yi + 1;
            }
        }

        public void shootAtShip(Point p) {
            if (p.x >= points.get(0).x &&
                    p.y >= points.get(0).y &&
                    p.x <= points.get(1).x &&
                    p.y <= points.get(1).y
                    )
                setShots(shots + 1);
        }

        public List<Point> getPoints() {
            return points;
        }

        public String getPointString() {
            return pointString;
        }

        public void addPoint(Point p) {
            points.add(p);
        }

        @Override
        public String toString() {
            return "Ship{" +
                    "sunk=" + sunk +
                    ", points=" + points +
                    ", pointString='" + pointString + '\'' +
                    ", shots=" + shots +
                    ", shipSize=" + shipSize +
                    '}';
        }
    }
}
