package primitives;

import java.math.BigDecimal;
import java.util.List;

/**
 * Util class is used for some internal utilities, e.g. controlling accuracy
 *
 * @author Dan
 */
public abstract class Util {
    // It is binary, equivalent to ~1/1,000,000,000,000 in decimal (12 digits)
    private static final int ACCURACY = -40;
    //public static final int ZERO = 0;

    /**
     * Empty private ctor to hide the public one
     */
    private Util() {
    }

    // double store format (bit level): seee eeee eeee (1.)mmmm � mmmm
    // 1 bit sign, 11 bits exponent, 53 bits (52 stored) normalized mantissa
    // the number is m+2^e where 1<=m<2
    // NB: exponent is stored "normalized" (i.e. always positive by adding 1023)
    private static int getExp(double num) {
        // 1. doubleToRawLongBits: "convert" the stored number to set of bits
        // 2. Shift all 52 bits to the right (removing mantissa)
        // 3. Zero the sign of number bit by mask 0x7FF
        // 4. "De-normalize" the exponent by subtracting 1023
        return (int) ((Double.doubleToRawLongBits(num) >> 52) & 0x7FFL) - 1023;


    }


    /***
     * This functions finds the determinant of Matrix
     * @param mat Two-dimensional array of doubles that simulates a matrix
     * @return The double value of the determinant of the simulated matrix
     */
    private static double determinantOfMatrix(double mat[][]) {
        double ans;
        ans = mat[0][0] * (mat[1][1] * mat[2][2] - mat[2][1] * mat[1][2])
                - mat[0][1] * (mat[1][0] * mat[2][2] - mat[1][2] * mat[2][0])
                + mat[0][2] * (mat[1][0] * mat[2][1] - mat[1][1] * mat[2][0]);
        return ans;
    }

    /***
     * This function finds the solution of system of linear equations using cramer's rule. Receives the coefficient values of the
     * linear equations in a form of simulated matrix and returns a Vector that contains the values of X,Y,Z that will solve the equations.
     * @param coeff mat Two-dimensional array of doubles that simulates a matrix, contains the coefficient values of the equations
     * @return Vector that contains the values of X,Y,Z that will solve the equations.
     */
    public static Vector findSolution(double coeff[][]) {
        // Matrix d using coeff as given in cramer's rule
        double d[][] = {
                {coeff[0][0], coeff[0][1], coeff[0][2]},
                {coeff[1][0], coeff[1][1], coeff[1][2]},
                {coeff[2][0], coeff[2][1], coeff[2][2]},
        };

        // Matrix d1 using coeff as given in cramer's rule
        double d1[][] = {
                {coeff[0][3], coeff[0][1], coeff[0][2]},
                {coeff[1][3], coeff[1][1], coeff[1][2]},
                {coeff[2][3], coeff[2][1], coeff[2][2]},
        };

        // Matrix d2 using coeff as given in cramer's rule
        double d2[][] = {
                {coeff[0][0], coeff[0][3], coeff[0][2]},
                {coeff[1][0], coeff[1][3], coeff[1][2]},
                {coeff[2][0], coeff[2][3], coeff[2][2]},
        };

        // Matrix d3 using coeff as given in cramer's rule
        double d3[][] = {
                {coeff[0][0], coeff[0][1], coeff[0][3]},
                {coeff[1][0], coeff[1][1], coeff[1][3]},
                {coeff[2][0], coeff[2][1], coeff[2][3]},
        };

        // Calculating Determinant of Matrices d, d1, d2, d3
        double det = determinantOfMatrix(d);
        double det1 = determinantOfMatrix(d1);
        double det2 = determinantOfMatrix(d2);
        double det3 = determinantOfMatrix(d3);

        double x = det1 / det;
        double y = det2 / det;
        double z = det3 / det;
        return new Vector(x, y, z);

    }

    /**
     * Checks whether the number is [almost] zero
     *
     * @param number
     * @return true if the number is zero or almost zero, false otherwise
     */
    public static boolean isZero(double number) {
        return getExp(number) < ACCURACY;
    }

    /**
     * Aligns the number to zero if it is almost zero
     *
     * @param number
     * @return 0.0 if the number is very close to zero, the number itself otherwise
     */
    public static double alignZero(double number) {
        return getExp(number) < ACCURACY ? 0.0 : number;
    }

    /**
     * Aligns the number to num of places
     * after the zero if it is almost there
     *
     * @param num the value of the number
     * @return the number round
     */
    public static double alignNumber(double num) {
        double num1 = (Math.round(num * 10d) / 10d);
        return getExp(num1 - num) < ACCURACY ? num1 : num;

    }

    /**
     * Check whether two numbers have the same sign
     *
     * @param n1 1st number
     * @param n2 2nd number
     * @return true if the numbers have the same sign
     */
    public static boolean checkSign(double n1, double n2) {
        return (n1 < 0 && n2 < 0) || (n1 > 0 && n2 > 0);
    }

    /**
     * Provide a real random number in range between min and max
     *
     * @param min
     * @param max
     * @return the random value
     */
    public static double random(double min, double max) {
        return Math.random() * (max - min) + min;
    }



}
