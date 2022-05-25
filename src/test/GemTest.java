package test;

import org.ascore.executor.Executor;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class GemTest {
    public static String CODE = """
            $gem-style-variable = 2
            #12 = 1 + 1
            #12 -= $gem-style-variable
                        
            @if $my-var > 12 {
                G00 X0 Y0
                @if 0 {
                    G00 X0 Y[#3 * 2]
                }
            } @elif #12 >= -1.5 {
                
            } @else {
                        
            }
            """;

    public static String CODE3 = """
            $my-var = 2
            #12 = 1 + 1
            #12 -= $my-var
            """;


    public static String CODE2 = """
            (DEBUG, Hello World)
            """;

    @Test
    public void testVariables() {
        Executor executor = new Executor();
        executor.debug = true;
        JSONArray compilationResult = executor.compiler(CODE3.split("\n"), true);
        assertEquals("[]", compilationResult.toString());
        JSONArray executionResult = executor.executerMain(false);
        System.out.println("Gcode output:");
        System.out.println(executor.getState().getFinalCode());
        assertFalse(executionResult.toString().contains("\"id\":400"));
    }
}
