import org.json.JSONArray;
import org.ascore.executeur.Executeur;

public class Main {
    private static final String CODE = """
            """;


    public static void main(String[] args) {
        Executeur executeur = new Executeur();
        JSONArray compilationResult = executeur.compiler(CODE.split("\n"), true);
        if (compilationResult.length() != 0) {
            System.out.println(compilationResult);
            return;
        }
        JSONArray executionResult = executeur.executerMain(false);
        System.out.println(executionResult);
    }
}
