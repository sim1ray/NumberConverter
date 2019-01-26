/*
 * BaseConverter class provides methods to convert between decimal, binary, octal,
 * hexadecimal, ASCII characters, color pixel representation, and floating point (IEEE 754).
 */

public class BaseConverter {

    private String decimal;
    private String binary;
    private String octal;
    private String hexadecimal;
    private String characters;
    private String color;
    private String floatDecimal;

    // CONSTRUCTORS
    public BaseConverter() {
        this.decimal = "";
        this.binary = "";
        this.octal = "";
        this.hexadecimal = "";
        this.characters = "";
        this.color = "";
        this.floatDecimal = "";
    }

    public BaseConverter(String decimal, String binary, String octal, String hexadecimal, String characters, String color, String floatDecimal) {
        this.decimal = decimal;
        this.binary = binary;
        this.octal = octal;
        this.hexadecimal = hexadecimal;
        this.characters = characters;
        this.color = color;
        this.floatDecimal = floatDecimal;
    }

    // GETTERS AND SETTERS
    public String getDecimal() {
        return decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
        this.binary = decimalToBaseN(decimal, 2, true);
        this.octal = decimalToBaseN(decimal, 8, true);
        this.hexadecimal = decimalToBaseN(decimal, 16, true);
        this.characters = decimalToChar(decimal);
        this.color = decimal;
        this.floatDecimal = binaryToFloat(this.getBinary(), 127);
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
        this.decimal = baseNToDecimal(binary, 2);
        this.octal = decimalToBaseN(this.getDecimal(), 8, true);
        this.hexadecimal = decimalToBaseN(this.getDecimal(), 16, true);
        this.characters = decimalToChar(this.getDecimal());
        this.color = this.getDecimal();
        this.floatDecimal = binaryToFloat(this.getBinary(), 127);
    }

    public String getOctal() {
        return octal;
    }

    public void setOctal(String octal) {
        this.octal = octal;
        this.decimal = baseNToDecimal(octal, 8);
        this.binary = decimalToBaseN(this.getDecimal(), 2, true);
        this.hexadecimal = decimalToBaseN(this.getDecimal(), 16, true);
        this.characters = decimalToChar(this.binary);
        this.color = this.getDecimal();
        this.floatDecimal = binaryToFloat(this.getBinary(), 127);
    }

    public String getHexadecimal() {
        return hexadecimal;
    }

    public void setHexadecimal(String hexadecimal) {
        this.hexadecimal = hexadecimal;
        this.decimal = baseNToDecimal(hexadecimal, 16);
        this.octal = decimalToBaseN(this.getDecimal(), 8, true);
        this.binary = decimalToBaseN(this.getDecimal(), 2, true);
        this.characters = decimalToChar(this.getDecimal());
        this.color = this.getDecimal();
        this.floatDecimal = binaryToFloat(this.getBinary(), 127);
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
        this.decimal = charToDecimal(characters);
        this.binary = decimalToBaseN(this.getDecimal(), 2, true);
        this.octal = decimalToBaseN(this.getDecimal(), 8, true);
        this.hexadecimal = decimalToBaseN(this.getDecimal(), 16, true);
        this.color = this.getDecimal();
        this.floatDecimal = binaryToFloat(this.getBinary(), 127);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        this.decimal = this.getColor();
        this.binary = decimalToBaseN(this.getDecimal(), 2, true);
        this.octal = decimalToBaseN(this.getDecimal(), 8, true);
        this.hexadecimal = decimalToBaseN(this.getDecimal(), 16, true);
        this.characters = decimalToChar(this.getDecimal());
        this.floatDecimal = binaryToFloat(this.getBinary(), 127);
    }

    public String getFloatDecimal() {
        return floatDecimal;
    }

    public void setFloatDecimal(String floatDecimal) {
        this.floatDecimal = floatDecimal;
        this.binary = floatToBinary(floatDecimal, 127);
        this.decimal = baseNToDecimal(this.getBinary(), 2);
        this.octal = decimalToBaseN(this.getDecimal(), 8, true);
        this.hexadecimal = decimalToBaseN(this.getDecimal(), 16, true);
        this.characters = decimalToChar(this.getDecimal());
        this.color = this.getDecimal();
    }


    // Converts number from base 10 to given base(binary, octal, hexadecimal)
    // Returns String representation of number
    public static String decimalToBaseN(String decimal, int base, boolean padWithZeros) {
        String numberBeforeDecimalPoint = splitNumberAtDecimalPoint(decimal)[0];
        String numberAfterDecimalPoint = "0." + splitNumberAtDecimalPoint(decimal)[1];
        int n = Integer.parseInt(numberBeforeDecimalPoint);
        String answer = "";
        int remainder;

        if (n == 0) {
            return "0";
        }

        // Conversion for decimal portion before decimal point
        while (n > 0) {
            remainder = n % base;
            if (remainder >= 10) {
                answer = Character.toString((char) (remainder + 55)) + answer;
            } else {
                answer = remainder + answer;
            }
            n = n / base;
        }

        // Conversion for part after decimal point
        if (numberAfterDecimalPoint.length() > 2) {
            double n2 = Double.parseDouble(numberAfterDecimalPoint);
            answer = answer + ".";
            int length = 0;
            while (length < 23) {     //ensures length is 23 bits (for mantissa calculation in floating point)
                n2 = n2 * 2;
                answer = answer + (int)n2;
                n2 = n2 - (int)n2;
                length++;
            }
        }

        // make string of 32/11/8 bits by left padding with zeros if necessary
        if (padWithZeros) {
            int totalLength = 0;
            if (base == 2) {
                totalLength = 32;
            } else if (base == 8) {
                totalLength = 11;
            } else if (base == 16) {
                totalLength = 8;
            }
            while (answer.length() < totalLength) {
                    answer = "0" + answer;
            }
        }

        return answer;
    }

    // Converts number from given base to base 10 (decimal)
    // Also converts numbers that contain a decimal point
    public static String baseNToDecimal(String number, int base) {
        String numberBeforeDecimalPoint = splitNumberAtDecimalPoint(number)[0];
        String numberAfterDecimalPoint = splitNumberAtDecimalPoint(number)[1];

        int place;
        double decimal = 0;
        int exp = 0;

        for (int i = numberBeforeDecimalPoint.length()-1; i >= 0; i--) {              //iterate from back of String
            place = Character.getNumericValue(numberBeforeDecimalPoint.charAt(i));
            decimal += place*Math.pow(base, exp);
            exp++;
        }
        for (int i = 0; i < numberAfterDecimalPoint.length(); i++) {
            place = Character.getNumericValue(numberAfterDecimalPoint.charAt(i));
            decimal += place*Math.pow(base, -(i+1));
        }

        // return integer string for inputs that don't contain a decimal point
        if (numberAfterDecimalPoint.isEmpty()) {
            return "" + (int)decimal;
        } else {
            return "" + decimal;
        }
    }

    // Converts binary string to floating point decimal (IEEE 754)
    public static String binaryToFloat(String binary, int excessK) {
        while (binary.length() < 32) { // make string of 32 bits by left padding with zeros if necessary
            binary = "0" + binary;
        }

        String answer;
        int sign = Integer.parseInt(binary.substring(0,1));
        if (sign == 1) {
            answer = "-";
        } else {
            answer = "";
        }

        double exponent = Double.parseDouble(baseNToDecimal(binary.substring(1, 9), 2)) - excessK;
        String mantissa = "1." + binary.substring(9);
        String n = baseNToDecimal(mantissa, 2);

        return answer + Double.parseDouble(n)*Math.pow(2, exponent);

    }

    // Converts floating point to binary string based on IEEE 457 standard
    public static String floatToBinary(String number, int excessK) {
        String signBit;
        if (number.substring(0,1).equals("-")) { //set sign bit
            signBit = "1";
        } else {
            signBit = "0";
        }

        number = number.toUpperCase();
        //Split floating point based on location of "E"
        int baseTenExponent = Integer.parseInt(number.substring(number.indexOf('E') +1));
        double n = Double.parseDouble(number.substring(0, number.indexOf('E')));

        // Calculate x for 2^x
        double baseTwoExponent = Math.log(10)/Math.log(2)*baseTenExponent + excessK;
        n = n * Math.pow(2, baseTwoExponent - (int)baseTwoExponent);
        while (n >= 2) {
            n = n/2;
            baseTwoExponent++;
        }

        String binary = decimalToBaseN("" + n, 2, false);
        String mantissa = splitNumberAtDecimalPoint(binary)[1];
        String exponent = decimalToBaseN(""+ (int)baseTwoExponent, 2, false);
        return signBit + exponent + mantissa;
    }

    // Converts given decimal to equivalent character string
    public static String decimalToChar(String decimal) {
        String result = "";
        int d = Integer.parseInt(decimal);
        while (d > 0) {
            int remainder = d % 256;
            d = d/256;
            result = (char)remainder + result;
        }
        return result;
    }

    // Converts given character string to equivalent decimal value
    public static String charToDecimal(String characters) {
        int decimal = 0;
        int exp = 0;
        for (int i = characters.length()-1; i >= 0; i--) {
            decimal += characters.charAt(i)*Math.pow(256, exp);
            exp++;
        }
        return "" + decimal;
    }

    // Helper method to separate digits before the decimal point from those after the decimal point
    public static String[] splitNumberAtDecimalPoint(String number) {

        String numberBeforeDecimalPoint;
        String numberAfterDecimalPoint = "";
        int decimalPlace = number.indexOf('.');
        if (decimalPlace != -1) {
            numberBeforeDecimalPoint = number.substring(0, decimalPlace);
            numberAfterDecimalPoint = number.substring(decimalPlace + 1);
        } else {
            numberBeforeDecimalPoint = number;
        }
        String[] s = {numberBeforeDecimalPoint, numberAfterDecimalPoint};

        return s;
    }
}

