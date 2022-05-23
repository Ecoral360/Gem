import org.json.JSONArray;
import org.ascore.executor.Executor;


/*
 * Objectifs:
 *  - Si le code est du G-code valide, alors il doit être du Gem valide
 *  - Un code écrit en Gem doit être le plus possible lisible pour un être humain
 *  - Les numéros de lignes doivent être optionnels
 */

public class Main {

    /*
     * Syntaxe:
     * (...) -> commentaire
     *
     * % -> début d'un programme
     */

    private static final String CODE = """
            %
            O2468
            N0010 (ICI JE METS DES COMMENTAIRES COMME LA DATE)
            N0020 (ICI JE METS DES COMMENTAIRES COMME LA LE NOM D EQUIPE)
            N0030 (ICI JE METS DES COMMENTAIRES COMME LE NOM DE MONTAGE)
            N0040 G00 G17 G20 G40 G80 G90 G94
            N0050 G91 G30  X0. Y0. Z0. T01 M06
            N0060 S1400 M03
            N0070 G90 G54 X1.5 Y.5 (POINT A)
            N0080 G43 Z4. H01
            N0090 M08
            N0100 G81 X1.5 Y.5 Z-0.4253 R.1 F10. (G98 OU G99 SELON PIECE)
            N0110 X2. Y-0.5
            N0120 G80 M09
            N0130 G00 Z4. M05
            N0140 G91 G30 Z0.
            N0150 G91 G30 X0. Y0. T02 M06
            N0160 S1400 M03
            N0170 G90 G54 X1.5 Y.5 (POINT A)
            N0180 G43 Z4. H01
            N0190 M08
            """;

    public static void main(String[] args) {
        Executor executor = new Executor();
        JSONArray compilationResult = executor.compiler(CODE.split("\n"), true);
        if (compilationResult.length() != 0) {
            System.out.println(compilationResult);
            return;
        }
        JSONArray executionResult = executor.executerMain(false);
        System.out.println(executionResult);
    }
}
