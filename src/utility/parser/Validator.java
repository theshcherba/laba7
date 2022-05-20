package utility.parser;

import classesandenums.*;

public class Validator {

    public int checkCoordinateX(double a) {
        if (Coordinates.checkValidСX(a)) return 1;
        return 0;
    }

    public int checkCoordinateY(long b) {
        if (Coordinates.checkValidСY(b)) return 1;
        return 0;
    }

    public int checkName(String name) {
        if (!name.equals("0") && !name.equals("")) return 1;
        return 0;
    }

    public int checkLocationName(String locationName) {
        if (!locationName.equals("0") && !locationName.equals("")) return 1;
        return 0;
    }

    public int checkHeight(int height) {
        if (Person.checkValidHeight(height)) return 1;
        return 0;
    }

    public int checkEyeColor(String eyeColor) {
        try {
            EColor.valueOf(eyeColor);
            return 1;
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    public int checkHairColor(String hairColor) {
        try {
            HColor.valueOf(hairColor);
            return 1;
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    public int checkCountry(String nationality) {
        try {
            Country.valueOf(nationality);
            return 1;
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }
}
