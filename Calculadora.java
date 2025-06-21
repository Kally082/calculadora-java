import java.util.*;

public class Calculadora {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a expressão: ");
        String input = scanner.nextLine();
        try {
            double resultado = calcular(input);
            System.out.println(resultado);
        } catch (Exception e) {
            System.out.println("Entrada inválida: " + e.getMessage());
        }
        scanner.close();
    }

    public static double calcular(String expressao) {
        List<String> tokens = tokenizer(expressao);
        List<String> posfixa = infixaParaPosfixa(tokens);
        return avaliarPosfixa(posfixa);
    }

    private static List<String> tokenizer(String expressao) {
        return Arrays.asList(expressao.trim().split(" "));
    }

    private static List<String> infixaParaPosfixa(List<String> tokens) {
        List<String> saida = new ArrayList<>();
        Stack<String> pilha = new Stack<>();
        Map<String, Integer> precedencia = new HashMap<>();
        precedencia.put("+", 1);
        precedencia.put("-", 1);
        precedencia.put("*", 2);
        precedencia.put("/", 2);

        for (String token : tokens) {
            if (token.matches("\\d+")) { // Se for um número
                saida.add(token);
            } else if (precedencia.containsKey(token)) { // Se for um operador
                while (!pilha.isEmpty() && precedencia.get(token) <= precedencia.get(pilha.peek())) {
                    saida.add(pilha.pop());
                }
                pilha.push(token);
            } else if (token.equals("(")) {
                pilha.push(token);
            } else if (token.equals(")")) {
                while (!pilha.isEmpty() && !pilha.peek().equals("(")) {
                    saida.add(pilha.pop());
                }
                pilha.pop(); // Remove o '('
            }
        }

        while (!pilha.isEmpty()) {
            saida.add(pilha.pop());
        }

        return saida;
    }

    private static double avaliarPosfixa(List<String> posfixa) {
        Stack<Double> pilha = new Stack<>();

        for (String token : posfixa) {
            if (token.matches("\\d+")) { // Se for um número
                pilha.push(Double.parseDouble(token));
            } else {
                double b = pilha.pop();
                double a = pilha.pop();
                double resultado = 0;

                switch (token) {
                    case "+":
                        resultado = a + b;
                        break;
                    case "-":
                        resultado = a - b;
                        break;
                    case "*":
                        resultado = a * b;
                        break;
                    case "/":
                        if (b == 0) throw new ArithmeticException("Divisão por zero");
                        resultado = a / b;
                        break;
                }
                pilha.push(resultado);
            }
        }

        return pilha.pop();
    }
}
