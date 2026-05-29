import java.util.Arrays;

public class RegressaoLinear {
    
    /**
     * Calcula os coeficientes beta0 (intercepto) e beta1 (inclinação) da regressão linear
     * usando o método dos mínimos quadrados
     * 
     * @param x array com os valores da variável independente
     * @param y array com os valores da variável dependente
     * @return array com [beta0, beta1]
     * @throws IllegalArgumentException se os arrays forem nulos ou vazios ou de tamanhos diferentes
     */
    public static double[] calcularCoeficientes(double[] x, double[] y) {
        // Validações
        if (x == null || y == null) {
            throw new IllegalArgumentException("Os arrays não podem ser nulos");
        }
        if (x.length == 0 || y.length == 0) {
            throw new IllegalArgumentException("Os arrays não podem estar vazios");
        }
        if (x.length != y.length) {
            throw new IllegalArgumentException("Os arrays devem ter o mesmo tamanho");
        }
        
        int n = x.length;
        
        // Cálculo das médias
        double somaX = 0, somaY = 0;
        for (int i = 0; i < n; i++) {
            somaX += x[i];
            somaY += y[i];
        }
        double mediaX = somaX / n;
        double mediaY = somaY / n;
        
        // Cálculo de beta1 (inclinação)
        double numerador = 0, denominador = 0;
        for (int i = 0; i < n; i++) {
            double diffX = x[i] - mediaX;
            double diffY = y[i] - mediaY;
            numerador += diffX * diffY;
            denominador += diffX * diffX;
        }
        
        // Verificar se denominador é zero (todos os x são iguais)
        if (Math.abs(denominador) < 1e-10) {
            throw new ArithmeticException("Denominador zero: todos os valores de x são iguais");
        }
        
        double beta1 = numerador / denominador;
        double beta0 = mediaY - beta1 * mediaX;
        
        return new double[]{beta0, beta1};
    }
    
    /**
     * Retorna o valor de y previsto para um dado x usando a equação y = beta0 + beta1*x
     * 
     * @param x valor da variável independente
     * @param beta0 intercepto
     * @param beta1 inclinação
     * @return valor previsto de y
     */
    public static double prever(double x, double beta0, double beta1) {
        return beta0 + beta1 * x;
    }
    
    /**
     * Calcula o coeficiente de determinação R²
     * R² = 1 - (SQR / SQT)
     * onde SQR é a soma dos quadrados dos resíduos e SQT é a soma total dos quadrados
     * 
     * @param yReais array com os valores reais de y
     * @param yPrevistos array com os valores previstos de y
     * @return coeficiente R² (entre 0 e 1)
     * @throws IllegalArgumentException se os arrays forem nulos ou vazios ou de tamanhos diferentes
     */
    public static double calcularR2(double[] yReais, double[] yPrevistos) {
        // Validações
        if (yReais == null || yPrevistos == null) {
            throw new IllegalArgumentException("Os arrays não podem ser nulos");
        }
        if (yReais.length == 0 || yPrevistos.length == 0) {
            throw new IllegalArgumentException("Os arrays não podem estar vazios");
        }
        if (yReais.length != yPrevistos.length) {
            throw new IllegalArgumentException("Os arrays devem ter o mesmo tamanho");
        }
        
        int n = yReais.length;
        
        // Cálculo da média dos valores reais
        double somaY = 0;
        for (double y : yReais) {
            somaY += y;
        }
        double mediaY = somaY / n;
        
        // Cálculo de SQR (Soma dos Quadrados dos Resíduos) e SQT (Soma Total dos Quadrados)
        double SQR = 0; // Soma dos quadrados dos resíduos
        double SQT = 0; // Soma total dos quadrados
        
        for (int i = 0; i < n; i++) {
            double residuo = yReais[i] - yPrevistos[i];
            SQR += residuo * residuo;
            
            double diferenca = yReais[i] - mediaY;
            SQT += diferenca * diferenca;
        }
        
        // Evitar divisão por zero
        if (Math.abs(SQT) < 1e-10) {
            return 1.0; // Se SQT é zero, todos os y são iguais, então R² = 1
        }
        
        double r2 = 1 - (SQR / SQT);
        
        // Garantir que R² esteja no intervalo [0, 1] devido a erros de arredondamento
        return Math.max(0, Math.min(1, r2));
    }
    
    // Método auxiliar para exibir resultados formatados
    public static void exibirResultados(double[] x, double[] y) {
        System.out.println("=== REGRESSÃO LINEAR ===");
        System.out.println("Dados originais:");
        System.out.println("X: " + Arrays.toString(x));
        System.out.println("Y: " + Arrays.toString(y));
        
        double[] coeficientes = calcularCoeficientes(x, y);
        double beta0 = coeficientes[0];
        double beta1 = coeficientes[1];
        
        System.out.printf("\nCoeficientes encontrados:%n");
        System.out.printf("β0 (intercepto) = %.4f%n", beta0);
        System.out.printf("β1 (inclinação) = %.4f%n", beta1);
        System.out.printf("Equação: y = %.4f + %.4f * x%n", beta0, beta1);
        
        // Calcular valores previstos
        double[] yPrevistos = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            yPrevistos[i] = prever(x[i], beta0, beta1);
        }
        
        double r2 = calcularR2(y, yPrevistos);
        System.out.printf("\nCoeficiente de determinação R² = %.4f (%.2f%%)%n", r2, r2 * 100);
    }
}

// Classe de exemplo de uso
class ExemploRegressaoLinear {
    public static void main(String[] args) {
        exemplo1_DadosSimples();
        System.out.println("\n" + "=".repeat(60) + "\n");
        exemplo2_TemperaturaEVendas();
        System.out.println("\n" + "=".repeat(60) + "\n");
        exemplo3_PrevisaoPersonalizada();
    }
    
    // Exemplo 1: Dados simples de horas estudadas vs notas
    static void exemplo1_DadosSimples() {
        System.out.println("EXEMPLO 1: Horas estudadas vs Nota na prova");
        
        double[] horasEstudo = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[] notaProva = {3.5, 4.2, 5.0, 5.8, 6.5, 7.0, 7.8, 8.5, 9.0, 9.5};
        
        RegressaoLinear.exibirResultados(horasEstudo, notaProva);
        
        // Fazer previsões
        System.out.println("\n--- Previsões ---");
        for (int horas = 0; horas <= 12; horas += 2) {
            double[] coef = RegressaoLinear.calcularCoeficientes(horasEstudo, notaProva);
            double notaPrevista = RegressaoLinear.prever(horas, coef[0], coef[1]);
            System.out.printf("Com %d horas de estudo, nota prevista: %.2f%n", horas, notaPrevista);
        }
    }
    
    // Exemplo 2: Temperatura vs Vendas de sorvete
    static void exemplo2_TemperaturaEVendas() {
        System.out.println("EXEMPLO 2: Temperatura vs Vendas de sorvete");
        
        double[] temperatura = {15, 18, 20, 22, 25, 27, 30, 32, 35};
        double[] vendas = {50, 65, 80, 95, 120, 135, 160, 175, 200};
        
        RegressaoLinear.exibirResultados(temperatura, vendas);
        
        // Fazer previsões para novas temperaturas
        System.out.println("\n--- Previsões de vendas ---");
        double[] novasTemperaturas = {28, 33, 38};
        double[] coef = RegressaoLinear.calcularCoeficientes(temperatura, vendas);
        
        for (double temp : novasTemperaturas) {
            double vendaPrevista = RegressaoLinear.prever(temp, coef[0], coef[1]);
            System.out.printf("Com %.0f°C, vendas previstas: %.0f unidades%n", temp, vendaPrevista);
        }
    }
    
    // Exemplo 3: Uso direto dos métodos com cálculo manual
    static void exemplo3_PrevisaoPersonalizada() {
        System.out.println("EXEMPLO 3: Cálculo personalizado");
        
        // Dados: anos de experiência vs salário
        double[] experiencia = {1, 2, 3, 4, 5, 6, 7, 8};
        double[] salario = {2500, 2800, 3100, 3500, 3900, 4300, 4800, 5200};
        
        // Calcular coeficientes
        double[] coeficientes = RegressaoLinear.calcularCoeficientes(experiencia, salario);
        double beta0 = coeficientes[0];
        double beta1 = coeficientes[1];
        
        System.out.printf("Equação: Salário = %.2f + %.2f * Experiência%n", beta0, beta1);
        
        // Calcular valores previstos manualmente
        System.out.println("\nValores reais vs previstos:");
        System.out.println("Exp\tReal\tPrevisto\tResíduo");
        for (int i = 0; i < experiencia.length; i++) {
            double previsto = RegressaoLinear.prever(experiencia[i], beta0, beta1);
            double residuo = salario[i] - previsto;
            System.out.printf("%.0f\t%.0f\t%.0f\t\t%.2f%n", 
                experiencia[i], salario[i], previsto, residuo);
        }
        
        // Calcular R² separadamente
        double[] previstos = new double[experiencia.length];
        for (int i = 0; i < experiencia.length; i++) {
            previstos[i] = RegressaoLinear.prever(experiencia[i], beta0, beta1);
        }
        double r2 = RegressaoLinear.calcularR2(salario, previstos);
        System.out.printf("\nR² = %.4f (%.2f%%) - O modelo explica %.2f%% da variação%n", 
            r2, r2 * 100, r2 * 100);
        
        // Previsão para 10 anos de experiência
        double salarioPrevisto = RegressaoLinear.prever(10, beta0, beta1);
        System.out.printf("\nPrevisão para 10 anos de experiência: R$ %.2f%n", salarioPrevisto);
    }
}