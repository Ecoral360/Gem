package test;

import org.ascore.executor.Executor;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GemTest {
    public static String CODE = """
            @if $abc > 12 {
                G00 X0 Y0
            } @elif $a {
            
            }
            """;

    @Test
    public void testVariables() {
        Executor executor = new Executor();
        JSONArray compilationResult = executor.compiler(CODE.split("\n"), true);
        assertEquals("[]", compilationResult.toString());
        JSONArray executionResult = executor.executerMain(false);
        System.out.println(executor.getState().getFinalCode());
        System.out.println(executionResult);
    }
}
