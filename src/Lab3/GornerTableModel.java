package Lab3;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return (int) Math.ceil((to - from) / step) + 1;
    }

    public Object getValueAt(int row, int col) {
        double x = from + step * row;
        if (col == 0) {
            return roundToTwoDecimalPlaces(x);
        } else {
            double result = 0.0;
            for (int i = 0; i < coefficients.length; ++i) {
                result += coefficients[i] * Math.pow(x, coefficients.length - i - 1);
            }

            // Округляем результат
            double roundedResult = roundToTwoDecimalPlaces(result);

            if (col == 1) {
                return roundedResult;
            } else {
                // Преобразуем округленный результат в строку
                String resultStr = String.valueOf(roundedResult);
                // Извлекаем дробную часть
                String fractionalPart = resultStr.contains(".") ?
                        resultStr.substring(resultStr.indexOf('.') + 1) : "0";

                // Проверяем простоту дробной части
                return isPrime(Integer.parseInt(fractionalPart));
            }
        }
    }

    // Метод для проверки, является ли число простым
    public boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }


    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "Значение X";
            case 1:
                return "Значение многочлена";
            default:
                return "Дробная часть простая";
        }
    }

    public Class<?> getColumnClass(int col) {  return col == 2 ? Boolean.class : Double.class;
    }
}